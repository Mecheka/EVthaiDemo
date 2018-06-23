package com.evthai.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.evthai.R;
import com.evthai.activity.MainActivity;
import com.evthai.manager.http.HttpManager;
import com.evthai.manager.http.UserManager;
import com.evthai.model.jsonModel.LoginDataModel;
import com.evthai.model.jsonModel.RegisterDataModel;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText editUser;
    private EditText editPass;
    private EditText editName;
    private EditText editLast;
    private EditText editPhone;
    private Button btnLogin;
    private ProgressDialog singupDialog;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        // Find View
        editUser = rootView.findViewById(R.id.editUser);
        editPass = rootView.findViewById(R.id.editPass);
        editName = rootView.findViewById(R.id.editName);
        editLast = rootView.findViewById(R.id.editLast);
        editPhone = rootView.findViewById(R.id.editPhone);
        btnLogin = rootView.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToMain();
            }
        });

    }

    private void loginToMain() {

        singupDialog = new ProgressDialog(getActivity());
        singupDialog.setTitle("Sing up");
        singupDialog.setMessage("Sing up...");
        singupDialog.setCancelable(true);
        singupDialog.setIndeterminate(false);
        singupDialog.show();

        final String user = editUser.getText().toString();
        final String pass = editPass.getText().toString();
        String name = editName.getText().toString();
        String last = editLast.getText().toString();
        String phone = editPhone.getText().toString();

        if (!checkEmpty(user, pass, name, last, phone)) {
            Call<ResponseBody> call = HttpManager.getInstance().getService().registerRequest(new RegisterDataModel(pass, name, last, user, phone));
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonResult = new JSONObject(response.body().string());
                            if (jsonResult.getString("status").equals("Fail")) {
                                singupDialog.dismiss();
                                showToast(jsonResult.getString("detail"));
                            } else {
                                loginMain(pass, user);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            singupDialog.dismiss();
                            Log.d("Error Debug :", response.errorBody().string());
                            showToast(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("Error Debug :", t.getMessage());
                    singupDialog.dismiss();
                    showToast(t.getMessage());
                }
            });
        }

    }

    private void loginMain(String pass, String user) {
        singupDialog.dismiss();
        Call<ResponseBody> callLogin = HttpManager.getInstance().getService().loginRequest(new LoginDataModel(pass, user));
        callLogin.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {

                        JSONObject jsonResults = new JSONObject(response.body().string());
                        if (jsonResults.getString("status").equals("OK")) {
                            singupDialog.dismiss();
                            UserManager.getInstance().setToken(jsonResults.getString("token"));
                            Intent intentMain = new Intent(getActivity(), MainActivity.class);
                            intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentMain);
                            getActivity().finish();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        singupDialog.dismiss();
                        showToast(response.errorBody().string());
                        Log.d("Error retrofit :", response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                singupDialog.dismiss();
                showToast(t.getMessage());
                Log.d("Error retrofit :", t.getMessage());

            }
        });
    }

    private boolean checkEmpty(String user, String pass, String name, String last, String phone) {
        if (user.isEmpty()) {
            singupDialog.dismiss();
            showToast("กรุณาใส่ชื่อบัญชีของท่าน");
            return true;
        }
        if (isValidEmail(user) == false) {
            singupDialog.dismiss();
            showToast("อีเมลของท่านผิดรูปแบบกรุณาใส่อีเมลที่ถูกต้อง");
            return true;
        }
        if (pass.isEmpty()) {
            singupDialog.dismiss();
            showToast("กรุณาใส่รหัสผ่านของท่าน");
            return true;
        }
        if (pass.length() <= 5) {
            singupDialog.dismiss();
            showToast("รหัสผ่านของท่านต้องมากกว่า 6 ตัว");
            return true;
        }
        if (name.isEmpty()) {
            singupDialog.dismiss();
            showToast("กรุณาใส่ชื่อของท่าน");
            return true;
        }
        if (last.isEmpty()) {
            singupDialog.dismiss();
            showToast("กรุณาใส่นามสกุลของท่าน");
            return true;
        }
        if (phone.isEmpty()) {
            singupDialog.dismiss();
            showToast("กรุณาใส่เบอร์มือถือของท่าน");
            return true;
        }
        if (phone.length() <= 9) {
            singupDialog.dismiss();
            showToast("เบอร์มือถือของท่านต้องเท่ากับ 10 ตัว");
            return true;
        }

        return false;
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

}
