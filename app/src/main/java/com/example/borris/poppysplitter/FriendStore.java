package com.example.borris.poppysplitter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Borris on 6/14/2016.
 */
public class FriendStore {

    List<String> friends;

    public FriendStore(){
        friends = new ArrayList<>();
    }

    public void addFriend(String friend){
        friends.add(friend);
    }

    public void removeFriend(String friend){
        friends.remove(friend);
    }

    public int size(){
        return friends.size();
    }

}
