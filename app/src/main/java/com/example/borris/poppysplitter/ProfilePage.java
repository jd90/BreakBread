package com.example.borris.poppysplitter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.List;

/**
 * Created by Borris on 6/14/2016.
 */
public class ProfilePage extends Activity implements View.OnClickListener{

    static ObjectStore objectsYou;
    static ObjectStore objectsThem;
    static String currentUser;
    static String friendName;
    static double total=0;
    Button addObject;
    static ListView lv1;
    static ListView lv2;
    static TextView friendTotal;
    static Context context;
    static FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        fm = getFragmentManager();

        objectsYou = new ObjectStore();
        objectsThem = new ObjectStore();
        context= getApplicationContext();
        addObject = (Button) findViewById(R.id.addObject);
        addObject.setOnClickListener(this);
        friendName = getIntent().getStringExtra("friendName");
        currentUser = getIntent().getStringExtra("currentUser");

        TextView titleFriend = (TextView) findViewById(R.id.friendName);
        titleFriend.setText(friendName);
        friendTotal = (TextView) findViewById(R.id.friendTotal);

        lv1 = (ListView) findViewById(R.id.list1);
        lv2 = (ListView) findViewById(R.id.list2);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View clickView,
                                    int position, long id) {

                objectsYou.objects.get(position);
                System.out.println("item click listener working");

                ObjectPopUpOption objOptionsPopup = new ObjectPopUpOption();
                objOptionsPopup.show(getFragmentManager(), "objectoptions");


            }
        });
       //lv2.setOnItemClickListener(this);

        String whereClause = "(createdBy = '"+currentUser+"' or createdBy = '"+friendName+"') AND (sentTo = '"+friendName+"' or sentTo = '"+currentUser+"')";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        //BackendlessCollection<Object> result = Backendless.Persistence.of( Object.class ).find( dataQuery );
        Backendless.Persistence.of( Object.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Object>>(){

            @Override
            public void handleResponse( BackendlessCollection<Object> result )
            {

                List<Object> retrievedObjs = result.getData();

                for(int i=0; i<retrievedObjs.size(); i++){

                    if(retrievedObjs.get(i).createdBy.equals(currentUser)){
                        objectsYou.addObject(retrievedObjs.get(i));
                        total += retrievedObjs.get(i).amount;
                    }else{
                        objectsThem.addObject(retrievedObjs.get(i));
                        total -= retrievedObjs.get(i).amount;
                    }
                }

                ObjectArrayAdapter adapter1 = new ObjectArrayAdapter(getApplicationContext(), objectsYou);
                ObjectArrayAdapter adapter2 = new ObjectArrayAdapter(getApplicationContext(), objectsThem);
                lv1.setAdapter(adapter1);
                lv2.setAdapter(adapter2);

                TextView friendTotal = (TextView) findViewById(R.id.friendTotal);
                total = Math.round(total);
                if(total<0) {
                    friendTotal.setText("you owe "+friendName+": £" + total*-1);
                }else{
                    if(total>0) {
                        friendTotal.setText(friendName + " owes you: £" + total);
                    }else{

                        friendTotal.setText("account is even");
                    }
                }

            }
            @Override
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
                System.out.println("errorroror "+fault.toString());

            }
        });



    }


    @Override
    public void onClick(View v) {

        AddObject popup = new AddObject();
        popup.show(getFragmentManager(), "fragment");






    }
    public static void loadObjects(){

        objectsYou.objects.clear();
        objectsThem.objects.clear();

        String whereClause = "(createdBy = '"+currentUser+"' or createdBy = '"+friendName+"') AND (sentTo = '"+friendName+"' or sentTo = '"+currentUser+"')";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause( whereClause );
        //BackendlessCollection<Object> result = Backendless.Persistence.of( Object.class ).find( dataQuery );
        Backendless.Persistence.of( Object.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Object>>(){

            @Override
            public void handleResponse( BackendlessCollection<Object> result )
            {

                List<Object> retrievedObjs = result.getData();

                for(int i=0; i<retrievedObjs.size(); i++){

                    if(retrievedObjs.get(i).createdBy.equals(currentUser)){
                        objectsYou.addObject(retrievedObjs.get(i));
                        total += retrievedObjs.get(i).amount;
                    }else{
                        objectsThem.addObject(retrievedObjs.get(i));
                        total -= retrievedObjs.get(i).amount;
                    }
                }

                ObjectArrayAdapter adapter1 = new ObjectArrayAdapter(context, objectsYou);
                ObjectArrayAdapter adapter2 = new ObjectArrayAdapter(context, objectsThem);
                lv1.setAdapter(adapter1);
                lv2.setAdapter(adapter2);

                total = Math.round(total);
                if(total<0) {
                    friendTotal.setText("you owe "+friendName+": £" + total*-1);
                }else{
                    if(total>0) {
                        friendTotal.setText(friendName + " owes you: £" + total);
                    }else{

                        friendTotal.setText("account is even");
                    }
                }

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