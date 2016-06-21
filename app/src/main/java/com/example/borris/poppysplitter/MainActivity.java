package com.example.borris.poppysplitter;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.UserService;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import org.w3c.dom.Text;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    final String YOUR_SECRET_KEY="B8344199-A98C-413D-FF21-FC62E5CA2D00";
    final String YOUR_APP_ID="6E705321-E2B1-9732-FFC0-9C71CA02AE00";
    static android.app.FragmentManager fm;
    static FriendStore friendsStore;
    static FriendArrayAdapter adapter;
    static ListView lv;
    static Context context;
    static String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getFragmentManager();
        context=getApplicationContext();

        Button addFriendButton = (Button) findViewById(R.id.addFriendButton);
        addFriendButton.setOnClickListener(this);
        addFriendButton.setTag("addfriend");
        Button logout = (Button) findViewById(R.id.logoutview);
        logout.setOnClickListener(this);
        logout.setTag("log");

        lv = (ListView) findViewById(R.id.listy);

        String appVersion = "v1";
        Backendless.initApp(this, YOUR_APP_ID, YOUR_SECRET_KEY, appVersion);


        if (Backendless.UserService.CurrentUser() == null) {


            LoginFrag lf = new LoginFrag();
            lf.show(fm, "Login/Register");

        }else {

            friendsStore = new FriendStore();

            currentUser = Backendless.UserService.CurrentUser().getProperty("name").toString();
            String whereClause = "username = '"+ currentUser+"' ";
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause( whereClause );
            Backendless.Persistence.of( Friends.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Friends>>(){

                @Override
                public void handleResponse( BackendlessCollection<Friends> foundFriends )
                {
                    // all Contact instances have been found

                    List friends = foundFriends.getData();

                            for(int i=0; i<foundFriends.getData().size(); i++){

                               friendsStore.addFriend(foundFriends.getData().get(i).friend);
                                Log.i("hiyajosh", friendsStore.friends.get(i).toString());

                }




                adapter = new FriendArrayAdapter(getApplicationContext(), friendsStore);
                lv.setDivider(null);
                lv.setAdapter(adapter);

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



/*

        String whereClause = "name != '"+Backendless.UserService.CurrentUser().getProperty("name").toString()+"'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        Backendless.Data.of( BackendlessUser.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<BackendlessUser>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<BackendlessUser> users )
            {
                Iterator<BackendlessUser> userIterator = users.getCurrentPage().iterator();

                while( userIterator.hasNext() )
                {
                    BackendlessUser user = userIterator.next();
                    System.out.println( "name - " + user.getProperty("name") );

                }
            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
                System.out.println( "Server reported an error - " + backendlessFault.getMessage() );
            }
        } );


        //String whereClause = "name = 'doshsquash'";
       // BackendlessDataQuery dataQuery = new BackendlessDataQuery();
      //  dataQuery.setWhereClause( whereClause );
       // BackendlessCollection<BackendlessUser> result = Backendless.Persistence.of( BackendlessUser.class ).find( dataQuery );

       // System.out.println("namesies - "+result.toString());


        BackendlessUser user = new BackendlessUser();
        user.setProperty( "name", "doshsquash" );
        user.setPassword( "Dunnpie7" );

        Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser registeredUser )
            {
                // user has been registered and now can login
            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        } );

        Backendless.Persistence.save( new Object( 50, 1, 2, "josh", "sarah" ), new BackendlessCallback<Object>()
        {
            @Override
            public void handleResponse( Object object )
            {
                Toast t = Toast.makeText(getApplicationContext(), "saved object with amount: " +object.amount, Toast.LENGTH_SHORT);
                t.show();

            }
        } );


*/




    @Override
    public void onClick(View v) {

        if(v.getTag().equals("log")) {


                Backendless.UserService.logout(new AsyncCallback<Void>() {
                      @Override
                        public void handleResponse(Void response) {
                            Toast t = Toast.makeText(getApplicationContext(), "logged out", Toast.LENGTH_SHORT);
                            t.show();
                          if(Backendless.UserService.CurrentUser()==null){

                              LoginFrag lf = new LoginFrag();
                              lf.show(fm, "Login/Register");

                          }
                         }

                         @Override
                          public void handleFault(BackendlessFault fault) {
                             Toast t = Toast.makeText(getApplicationContext(), "logged out NUT", Toast.LENGTH_SHORT);
                             t.show();
                         }
                     });

        }
        else {

                Intent addFriends = new Intent(this, AddFriends.class);
                startActivity(addFriends);

        }

    }

    public static void loadFriendObjects(){


        friendsStore = new FriendStore();

        currentUser = Backendless.UserService.CurrentUser().getProperty("name").toString();
        String whereClause = "username = '"+ currentUser+"'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        Backendless.Persistence.of( Friends.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Friends>>(){

            @Override
            public void handleResponse( BackendlessCollection<Friends> foundFriends )
            {
                // all Contact instances have been found

                List friends = foundFriends.getData();

                for(int i=0; i<foundFriends.getData().size(); i++){

                    friendsStore.addFriend(foundFriends.getData().get(i).friend);
                    Log.i("hiyajosh", friendsStore.friends.get(i).toString());

                }




                adapter = new FriendArrayAdapter(context, friendsStore);
                lv.setDivider(null);
                lv.setAdapter(adapter);

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
