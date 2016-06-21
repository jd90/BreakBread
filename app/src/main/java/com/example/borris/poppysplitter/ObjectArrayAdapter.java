package com.example.borris.poppysplitter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Borris on 6/14/2016.
 */
public class ObjectArrayAdapter extends ArrayAdapter<Object> implements View.OnClickListener {

    Context context;
    ObjectStore objectStore;
    static View a;

    public ObjectArrayAdapter(Context context, ObjectStore o) {
        super(context, R.layout.object_object, (List<Object>) o.objects);

        this.context=context;
        this.objectStore=o;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.object_object, parent, false);

        LinearLayout container = (LinearLayout) convertView.findViewById(R.id.container);
        container.setOnClickListener(this);

        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        TextView date = (TextView) convertView.findViewById(R.id.status);
        TextView status = (TextView) convertView.findViewById(R.id.date);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);


        Object obj = objectStore.objects.get(position);
        if(obj.createdBy.equals(ProfilePage.currentUser)){
            container.setTag("YOU");
        }else{

            container.setTag("THEM");
        }
        amount.setText("£"+String.valueOf(obj.amount));
        desc.setText(obj.desc);
        date.setText(String.valueOf(obj.created));
        if(obj.status==1){
            status.setText("UNPAID");
        }
        if(obj.status==2){
            status.setText("PAID");
        }

        return convertView;
    }

    @Override
    public void onClick(View v) {

        a=v;
        v.setBackgroundColor(Color.BLUE);

        if(v.getTag().equals("YOU")){
            //objectsYou.objects.get(position);
            System.out.println("item click listener working");

            ObjectPopUpOption objOptionsPopup = new ObjectPopUpOption();
            objOptionsPopup.show(ProfilePage.fm, "objectoptions");

            objOptionsPopup.setCancelable(true);
        }else{
            //objectsYou.objects.get(position);
            System.out.println("item click listener working");

            ObjectPopUpOption2 objOptionsPopup2 = new ObjectPopUpOption2();
            objOptionsPopup2.show(ProfilePage.fm, "objectoptions");

            objOptionsPopup2.setCancelable(true);
        }
    }

}
