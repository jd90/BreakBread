package com.example.borris.poppysplitter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

/**
 * Created by Borris on 6/19/2016.
 */
public class FriendArrayAdapter extends ArrayAdapter<Object> implements View.OnClickListener {

    Context context;
    FriendStore friendStore;
    List<String> friends;

    public FriendArrayAdapter(Context context, FriendStore f) {
        super(context, R.layout.friend_list_object, (List) f.friends);

        this.context=context;
        this.friendStore=f;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.friend_list_object, parent, false);

        friends = friendStore.friends;
        Button frenObj = (Button) convertView.findViewById(R.id.friendobject);
        frenObj.setText(friends.get(position));
        frenObj.setTag(position);
        frenObj.setOnClickListener(this);

        return convertView;
    }


    @Override
    public void onClick(View v) {


        Button b = (Button)v;
        String friendName = b.getText().toString();

        Intent profilePage = new Intent(context, ProfilePage.class);
        profilePage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        profilePage.putExtra("friendName", friendName);
        profilePage.putExtra("currentUser", MainActivity.currentUser);
        context.startActivity(profilePage);


    }
}
