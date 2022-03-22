package com.volunteerlog.volunteerlog2;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import org.json.*;

public class SaveManager{
    String filePath;
    SaveManager(){
    }
    
    public void save(){
        //Save to JSON
        try{
            JSONArray saveArray = new JSONArray();
            PrintWriter pw = new PrintWriter(new PrintWriter(filePath));
            for(Entry e : App.entries){
                saveArray.put(e.getFields());
            }
            pw.println(saveArray.toString(4));
            pw.close();
            App.ltabController.updateView();
            App.mtabController.updateCharts();
        }catch(Exception e){
            System.out.println("COULD NOT SAVE");
        }
        App.ltabController.updateView();
    }

    public void load(){
        //Load from JSON
        try{
            App.entries.clear();
            InputStream inputStream = new FileInputStream(filePath);
            JSONTokener jstokener = new JSONTokener(inputStream);
            JSONArray loadArray = new JSONArray(jstokener);
            for(int i = 0; i < loadArray.length();i++){
                Entry e = new Entry();
                e.setFields(loadArray.getJSONObject(i));
                App.entries.add(e);
            }
            App.ltabController.updateView();
            App.mtabController.updateCharts();
        }catch(Exception e){
            System.out.println("COULD NOT LOAD");
        }
    }

    public void setFilePath(String fp){
        filePath = fp;
    }
}
