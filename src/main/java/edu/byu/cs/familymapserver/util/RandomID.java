package edu.byu.cs.familymapserver.util;

import java.util.Random;

/**
 * Created by christian on 3/12/17.
 */

/**
 * this class is used to create a random authToken
 */
public class RandomID {
    static Random rand = new Random();
    private String id_chars = "abcdefghijklmnopqrstuvwxyz0123456789";

    private String UUID;

    public RandomID(){
        this.UUID = "didn't work";
    }

    public String genRandomID(int id_length){
        Random rand = new Random();
        char[] id = new char[id_length];
        for(int i = 0; i < id_length; i++){
            id[i] = id_chars.charAt(rand.nextInt(id_chars.length()));
        }
        String new_id = new String(id);
        this.UUID = new_id;
        return UUID;
    }


}
