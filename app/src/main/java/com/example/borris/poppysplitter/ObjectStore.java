package com.example.borris.poppysplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 6/14/2016.
 */
public class ObjectStore {

    List<Object> objects;

    public ObjectStore(){
        objects = new ArrayList<>();
    }

    public void addObject(Object object){
        objects.add(object);
    }

    public void removeObject(Object object){
        objects.remove(object);
    }

    public int size(){
        return objects.size();
    }

}
