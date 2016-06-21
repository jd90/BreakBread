package com.example.borris.poppysplitter;

/**
 * Created by Borris on 6/19/2016.
 */

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.ArrayList;


public class LoginFrag extends DialogFragment implements View.OnClickListener {

    EditText username;
    EditText password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.login_popup, container, false);

        Button login =(Button) rootView.findViewById(R.id.login);
        Button register =(Button) rootView.findViewById(R.id.register);

        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);


        login.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setTag("login");
        register.setTag("register");

        this.setCancelable(false);
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (v instanceof Button) {

            Button b = (Button) v;

            String sUsername = username.getText().toString();
            String sPassword = password.getText().toString();

            if (b.getTag().equals("register")) {


                BackendlessUser user = new BackendlessUser();
                user.setProperty( "name", sUsername );
                user.setPassword( sPassword );

                Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
                {
                    public void handleResponse( BackendlessUser registeredUser )
                    {
                        // user has been registered and now can login
                        Toast t = Toast.makeText(getActivity(), "aye, registered, now login", Toast.LENGTH_SHORT);
                        t.show();
                    }

                    public void handleFault( BackendlessFault fault )
                    {
                        // an error has occurred, the error code can be retrieved with fault.getCode()
                        Toast t = Toast.makeText(getActivity(), "naw pal, register didna happen: "+fault.toString(), Toast.LENGTH_SHORT);
                        t.show();
                    }
                } );

                dismiss();

            } else {


                Backendless.UserService.login(sUsername, sPassword, new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {
                        Toast t = Toast.makeText(getActivity(), "aye, logged in", Toast.LENGTH_SHORT);
                        t.show();

                        MainActivity.loadFriendObjects();
                        dismiss();
                    }

                    public void handleFault(BackendlessFault fault) {
                        Toast t = Toast.makeText(getActivity(), "nut, didna login: "+fault.toString(), Toast.LENGTH_SHORT);
                        t.show();
                        Log.i("faultyyy", "nut, didna login: "+fault.toString());
                    }
                }, true );

            }
        }
    }


}