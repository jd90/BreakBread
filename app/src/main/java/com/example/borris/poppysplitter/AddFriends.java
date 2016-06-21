package com.example.borris.poppysplitter;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Borris on 6/16/2016.
 */
public class AddFriends extends ListActivity {


        ArrayList<String> usernames;
        FollowersArrayAdapter adapter;
        ListView lView;
        static List<Friends> friends;
        // Required empty public constructor
        public AddFriends() {
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.friends_feed);

            usernames = new ArrayList<>();





            if(Backendless.UserService.CurrentUser() != null) {

                String whereClause = "name != '" + Backendless.UserService.CurrentUser().getProperty("name").toString() + "'";
                BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                dataQuery.setWhereClause(whereClause);
                Backendless.Data.of(BackendlessUser.class).find(dataQuery, new AsyncCallback<BackendlessCollection<BackendlessUser>>() {
                    @Override
                    public void handleResponse(BackendlessCollection<BackendlessUser> users) {
                        Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();
                        System.out.println("testtest HERE1");
                        while (userIterator.hasNext()) {
                            BackendlessUser user = userIterator.next();
                            usernames.add(user.getProperty("name").toString());



                            String whereClause = "username = '"+ Backendless.UserService.CurrentUser().getProperty("name").toString()+"' ";
                            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                            dataQuery.setWhereClause( whereClause );
                            Backendless.Persistence.of( Friends.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Friends>>(){

                                @Override
                                public void handleResponse( BackendlessCollection<Friends> foundFriends )
                                {
                                    // all Contact instances have been found

                                    friends = foundFriends.getData();

                                    adapter = new FollowersArrayAdapter(getApplicationContext(), usernames);
                                    getListView().setDivider(null);
                                    setListAdapter(adapter);

                                }
                                @Override
                                public void handleFault( BackendlessFault fault )
                                {
                                    // an error has occurred, the error code can be retrieved with fault.getCode()
                                    System.out.println("errorroror "+fault.toString());

                                }
                            });


                        }
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {
                        Toast t = Toast.makeText(getApplicationContext(), "error retrieving users: " + backendlessFault.getMessage(), Toast.LENGTH_SHORT);
                        t.show();

                    }
                });

            }
            else{

                Toast t = Toast.makeText(getApplicationContext(), "Equals null pal", Toast.LENGTH_SHORT);
                t.show();

            }



        }

    @Override
    public void onStop(){
        MainActivity.loadFriendObjects();
        super.onStop();
    }




    }