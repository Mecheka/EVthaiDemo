package com.evthai.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evthai.R;
import com.evthai.activity.MainActivity;
import com.evthai.manager.http.HttpManager;
import com.evthai.manager.http.UserManager;
import com.evthai.model.jsonModel.LoginDataModel;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String SPF_LOGIN = "vidslogin";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String SPG_TOKEN = "tokenperferences";
    private static final String TOKEN = "token";

    private EditText editUser;
    private EditText editPass;
    private CheckBox checkRememberMe;
    private Button btnLogin;
    private TextView tvRegister;
    private ProgressDialog loginDialog;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        //Find view
        editUser = rootView.findViewById(R.id.editUser);
        editPass = rootView.findViewById(R.id.editPass);
        checkRememberMe = rootView.findViewById(R.id.checkRememberMe);
        btnLogin = rootView.findViewById(R.id.btnLogin);
        tvRegister = rootView.findViewById(R.id.tvRegister);

        SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_LOGIN, Context.MODE_PRIVATE);
        editUser.setText(loginPreferences.getString(USERNAME, ""));
        editPass.setText(loginPreferences.getString(PASSWORD, ""));

        //Set on click
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }

    private void loginToMain() {

        loginDialog = new ProgressDialog(getActivity());
        loginDialog.setTitle("Login");
        loginDialog.setMessage("Login...");
        loginDialog.setCancelable(true);
        loginDialog.setIndeterminate(false);
        loginDialog.show();

        final String email = editUser.getText().toString();
        final String pass = editPass.getText().toString();

        if (email.isEmpty()) {
            loginDialog.dismiss();
            showToast("กรุณาใส่บัณชีผู้ใช้ของท่าน");
            return;
        }
        if (isValidEmail(email) == false) {
            loginDialog.dismiss();
            showToast("อีเมลของท่านผิดรูปแบบกรุณาใส่อีเมลที่ถูกต้อง");
            return;
        }
        if (pass.isEmpty()) {
            loginDialog.dismiss();
            showToast("กรุณาใส่รหัสผ่านของท่าน");
            return;
        }
        if (pass.length() <= 5){
            loginDialog.dismiss();
            showToast("รหัสผ่านของท่านต้องมากกว่า 6 ตัว");
            return;
        }

        Call<ResponseBody> call = HttpManager.getInstance().getService().loginRequest(new LoginDataModel(pass, email));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("status").equals("OK")){
                            if (checkRememberMe.isChecked()){
                                SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_LOGIN, Context.MODE_PRIVATE);
                                loginPreferences.edit().putString(USERNAME, email).putString(PASSWORD, pass).apply();
                            }else {
                                SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_LOGIN, Context.MODE_PRIVATE);
                                loginPreferences.edit().clear().apply();
                            }
                            loginDialog.dismiss();
                            String tokenJson = jsonResults.getString("token");
                            UserManager.getInstance().setToken(tokenJson);
                            SharedPreferences tokenPreferences = getActivity().getSharedPreferences(SPG_TOKEN, Context.MODE_PRIVATE);
                            tokenPreferences.edit().putString(TOKEN, tokenJson).apply();
                            Intent intentMain = new Intent(getActivity(), MainActivity.class);
                            intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentMain);
                            getActivity().finish();
                        }else {
                            loginDialog.dismiss();
                            showToast(jsonResults.getString("detail"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        loginDialog.dismiss();
                        Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                        Log.d("Error retrofit :", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loginDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error retrofit :", t.getMessage());
            }
        });

    }

    private boolean isValidEmail(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    private void showToast(String text) {
        KToast.errorToast(getActivity(), text, Gravity.BOTTOM, KToast.LENGTH_AUTO);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                loginToMain();
                break;
            case R.id.tvRegister:
                getFragmentManager().beginTransaction()
                        .replace(R.id.contentContainer, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
