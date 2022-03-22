package com.volunteerlog.volunteerlog2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginManager {
    private JSONObject keys;
    private String filePath = "secret/keys.json";
    LoginManager(){
        load();
    }

    public void load(){
        try{
            App.entries.clear();
            InputStream inputStream = new FileInputStream(filePath);
            JSONTokener jstokener = new JSONTokener(inputStream);
            keys = new JSONObject(jstokener);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            PrintWriter pw = new PrintWriter(new PrintWriter(filePath));
            pw.println(keys.toString(4));
            pw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean validate(String user, String pass){
        //Check if username has the right value
        if(keys.keySet().contains(user)){
            if(keys.getString(user).equals(pass)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public boolean contains(String user){
        return keys.keySet().contains(user);
    }

    public void addUser(String user, String password){
        keys.put(user, password);
    }
}
