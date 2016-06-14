package com.fooyemeet2.Activities.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fooyemeet2.Activities.Requests.AsynctaskRegister;
import com.fooyemeet2.R;


/**
 * Created by Mehdi on 07/03/2016.
 */
public class FragmentRegister extends Fragment {

    EditText name = null;
    EditText username = null;
    EditText email = null;
    EditText password = null;
    EditText city = null;
    Button register = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_sign_up, container, false);

        register = (Button) rootView.findViewById(R.id.button_register2);
        name = (EditText) rootView.findViewById(R.id.nameEdit);
        username = (EditText) rootView.findViewById(R.id.usernameEdit);
        city = (EditText) rootView.findViewById(R.id.city);
        email = (EditText) rootView.findViewById(R.id.emailEdit);
        password = (EditText) rootView.findViewById(R.id.PasswordEdit);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namestr = name.getText().toString();
                String usernamestr = username.getText().toString();
                String citystr = city.getText().toString();
                String mailstr = email.getText().toString();
                String passwordstr = password.getText().toString();
                if (namestr.isEmpty() || passwordstr.isEmpty() || usernamestr.isEmpty() || citystr.isEmpty() || mailstr.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill every field", Toast.LENGTH_SHORT).show();
                } else {
                    AsynctaskRegister async = new AsynctaskRegister(getActivity());
                    async.execute(namestr, usernamestr, passwordstr, mailstr, citystr);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_login, new FragmentProfileTab()).commit();
                }
            }
        });
        return rootView;
    }

}

