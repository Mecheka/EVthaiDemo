package com.evthai.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.evthai.R;
import com.evthai.model.charger.Location;

import java.util.ArrayList;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProfileFragment extends Fragment implements View.OnClickListener {

    private EditText editUser;
    private EditText editPass;
    private EditText editCharger;
    private EditText editName;
    private EditText editLast;
    private EditText editEmail;
    private EditText editPhone;
    private Button btnSave;
    private ArrayList<Location> locationList = new ArrayList<>();

    public UpdateProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_updateprofile, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        // Fine View
        editUser = rootView.findViewById(R.id.editUser);
        editPass = rootView.findViewById(R.id.editPass);
        editCharger = rootView.findViewById(R.id.editCharger);
        editName = rootView.findViewById(R.id.editName);
        editLast = rootView.findViewById(R.id.editLast);
        editEmail = rootView.findViewById(R.id.editEmail);
        editPhone = rootView.findViewById(R.id.editPhone);
        btnSave = rootView.findViewById(R.id.btnSave);

        // Set OnClick
        btnSave.setOnClickListener(this);

    }

    private void saveProfile() {
        String user = editUser.getText().toString();
        String pass = editPass.getText().toString();
        String idCard = editCharger.getText().toString();
        String name = editName.getText().toString();
        String last = editLast.getText().toString();
        String email = editEmail.getText().toString();
        String phone = editPhone.getText().toString();

        if (!checkEmpty(user, pass, idCard, name, last, email, phone)) {
            getFragmentManager().popBackStack();
        }
    }

    private boolean checkEmpty(String user, String pass, String idCard, String name, String last, String email, String phone) {
        if (user.isEmpty()) {
            return true;
        }
        if (pass.isEmpty()) {
            return true;
        }
        if (idCard.isEmpty()) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSave:
                saveProfile();
                break;
        }
    }

}
