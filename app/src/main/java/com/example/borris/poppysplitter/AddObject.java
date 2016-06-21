package com.example.borris.poppysplitter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.BackendlessCallback;

/**
 * Created by Borris on 6/20/2016.
 */
public class AddObject extends DialogFragment implements View.OnClickListener {

    EditText amount;
    EditText desc;
    Button add;

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.add_object_popup, container, false);

            getDialog().setTitle("Add Transaction");

            amount = (EditText) rootView.findViewById(R.id.amount);
            desc = (EditText) rootView.findViewById(R.id.desc);

            add = (Button) rootView.findViewById(R.id.add);
            add.setOnClickListener(this);

            return rootView;
        }

    @Override
    public void onClick(View v) {

        String amountString = amount.getText().toString();
        String descString =  desc.getText().toString();

        Backendless.Persistence.save( new Object( Double.parseDouble(amountString), descString, 1, ProfilePage.currentUser, ProfilePage.friendName  ), new BackendlessCallback<Object>()
        {
            @Override
            public void handleResponse( Object object )
            {
                ProfilePage.loadObjects();
                Toast t = Toast.makeText(MainActivity.context, "saved object with amount: "+object.amount, Toast.LENGTH_SHORT);
                t.show();
            }
        } );

        dismiss();


    }
}
