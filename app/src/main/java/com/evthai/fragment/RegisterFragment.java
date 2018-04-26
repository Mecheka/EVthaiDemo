package com.evthai.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evthai.R;
import com.evthai.activity.MainActivity;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private EditText editUser;
    private EditText editPass;
    private EditText editName;
    private EditText editLast;
    private EditText editEmail;
    private EditText editPhone;
    private Button btnLogin;

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
        editEmail = rootView.findViewById(R.id.editEmail);
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

        String user = editUser.getText().toString();
        String pass = editPass.getText().toString();
        String name = editName.getText().toString();
        String last = editLast.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();

        if (!checkEmpty(user, pass, name, last, email, phone)) {
            Intent intentMain = new Intent(getActivity(), MainActivity.class);
            startActivity(intentMain);
            getActivity().finish();
        }

    }

    private boolean checkEmpty(String user, String pass, String name, String last, String email, String phone) {
        if (user.isEmpty()) {
            return true;
        }
        if (pass.isEmpty()) {
            return true;
        }
        if (name.isEmpty()) {
            return true;
        }
        if (last.isEmpty()) {
            return true;
        }
        if (email.isEmpty()) {
            return true;
        }
        if (phone.isEmpty()) {
            return true;
        }
        if (isValidEmail(email) == false) {
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
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
