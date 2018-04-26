package com.evthai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evthai.R;
import com.evthai.activity.MainActivity;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText editUser;
    private EditText editPass;
    private Button btnLogin;
    private TextView tvRegister;

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
        btnLogin = rootView.findViewById(R.id.btnLogin);
        tvRegister = rootView.findViewById(R.id.tvRegister);

        //Set on click
        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                loginToMain();
                break;
            case R.id.tvRegister:
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.contentContainer, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }

    private void loginToMain() {

        String user = editUser.getText().toString();
        String pass = editPass.getText().toString();

        if (!user.isEmpty() && !pass.isEmpty()){
            Intent intentMain = new Intent(getActivity(), MainActivity.class);
            startActivity(intentMain);
            getActivity().finish();
        }

    }

    private void showToast(String text){
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }
}
