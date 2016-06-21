package com.example.borris.poppysplitter;

import android.app.DialogFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Borris on 6/21/2016.
 */
public class ObjectPopUpOption extends DialogFragment implements View.OnClickListener {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.object_option_popup, container, false);



        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(true);
        return rootView;
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onStop(){
        ObjectArrayAdapter.a.setBackgroundColor(Color.WHITE);
        super.onStop();
    }

}