package com.volunteerlog.volunteerlog2;

import java.io.FileInputStream;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;

public class LoginManager {
    private JSONObject keys;
    LoginManager(){
        load("secret/keys.json");
    }

    public void load(String filePath){
        try{
            App.entries.clear();
            InputStream inputStream = new FileInputStream(filePath);
            JSONTokener jstokener = new JSONTokener(inputStream);
            keys = new JSONObject(jstokener);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean validate(String user, String pass){
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
}
