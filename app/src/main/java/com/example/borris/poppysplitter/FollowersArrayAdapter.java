package com.example.borris.poppysplitter;

/**
 * Created by Borris on 6/16/2016.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.lang.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Borris on 05/04/2016.
 */

public class FollowersArrayAdapter extends ArrayAdapter<String> implements View.OnClickListener {

    private final Context context;
    private ArrayList<String> usernames;
    String user;
    int pos;
    CheckBox c;TextView t;

    public FollowersArrayAdapter(Context context, ArrayList<String> g) {
        super(context, R.layout.friend_list_item, (List) g);

        this.context = context;
        this.usernames = g;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row_view = inflater.inflate(R.layout.friend_list_item, parent, false);

        pos=position;

        t = (TextView) row_view.findViewById(R.id.title);
        c = (CheckBox) row_view.findViewById(R.id.check);
        LinearLayout l = (LinearLayout) row_view.findViewById(R.id.container);

        String user = usernames.get(position);
        Log.i("78789", "" + user);

        t.setText(user);


        for(int i =0; i<AddFriends.friends.size(); i++){

            if(AddFriends.friends.get(i).friend.equals(user)){
                c.setChecked(true);
            }

        }

        //List<String> friends = ParseUser.getCurrentUser().getList("followers");


        //if (a.contains(user)) {
      //      Log.i("78789", "match" + user);
     //       c.setChecked(true);
    //    }

        c.setOnClickListener(this);
        l.setTag(position);



        return row_view;
    }

    @Override
    public void onClick(View v) {


        Log.i("7878user", "hi");

        LinearLayout l = (LinearLayout) v.getParent();
        int position = Integer.parseInt(l.getTag().toString());

        CheckBox check = (CheckBox) v;
        if(!check.isChecked()) {//this shouldnt be !not - why is it working like this??
            check.setChecked(false);
            Log.i("7878user", usernames.get(position));

            Friends f= new Friends("S", "S");
            for(int i =0; i<AddFriends.friends.size();i++){
                if(usernames.get(position).equals(AddFriends.friends.get(i).friend)){
                    System.out.println("yaboyyaman: " +usernames.get(position));
                    f=AddFriends.friends.get(i);
                }

            }

            //Friends f = new Friends(Backendless.UserService.CurrentUser().getProperty("name").toString(), usernames.get(position));
            Backendless.Persistence.of( Friends.class ).remove( f, new AsyncCallback<Long>()
            {
                public void handleResponse( Long response )
                {
                    // Contact has been deleted. The response is a time in milliseconds when the object was deleted
                    System.out.println("aaaaaaa");
                }
                public void handleFault( BackendlessFault fault )
                {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    System.out.println("bbbbbbb: "+fault.toString());
                }
            } );

        }else{
            check.setChecked(true);

            user = usernames.get(position);
            Backendless.Persistence.save(new Friends(Backendless.UserService.CurrentUser().getProperty("name").toString(), user), new AsyncCallback<Friends>() {
                @Override
                public void handleResponse(Friends response) {

                    Toast t = Toast.makeText(getContext(), "added: "+user+" to friends", Toast.LENGTH_SHORT);
                    t.show();

                }

                @Override
                public void handleFault(BackendlessFault fault) {

                }
            });


            Log.i("7878user", usernames.get(position));
            //ParseUser.getCurrentUser().getList("followers").add(usernames.get(position));


        }

    }





}
