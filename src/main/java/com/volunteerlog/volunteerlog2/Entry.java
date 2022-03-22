package com.volunteerlog.volunteerlog2;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.*;


public class Entry extends Button
{
    private int totalHours;

    private OpenedEntryController controller;
    private ContextMenu contextMenu;
    //Fields
    private JSONObject fields;

    Entry(){
        //Add all fields
        fields = new JSONObject();
        fields.put("accomplishArea", "");
        fields.put("date1", "");
        fields.put("date2", "");
        fields.put("date3", "");
        fields.put("date4", "");
        fields.put("date5", "");
        fields.put("entryDate", "");
        fields.put("firstName", "");
        fields.put("gradYear", "");
        fields.put("hour1", "");
        fields.put("hour2", "");
        fields.put("hour3", "");
        fields.put("hour4", "");
        fields.put("hour5", "");
        fields.put("lastName", "");
        fields.put("learnArea", "");
        fields.put("orgName", "");
        fields.put("phoneNum", "");
        fields.put("prepareArea", "");
        fields.put("supervisorEmail", "");
        fields.put("supervisorName", "");
        fields.put("supervisorPhone", "");
        fields.put("supervisorTitle", "");

        this.setId(Long.toString(createHash()));
        this.fields.put("entryDate", LocalDate.now().format(App.formatter));
        updateButton();
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        this.getStyleClass().add("entry-button");
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                if(arg0.getButton() == MouseButton.PRIMARY)
                    click();
            }

        });

        //Add context menu
        contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit");
        MenuItem print = new MenuItem("Print");
        MenuItem delete = new MenuItem("Delete");

        edit.setOnAction((event)-> {
            click();
        });

        print.setOnAction((event) -> {
            print();
        });

        delete.setOnAction((event) -> {
            remove();
        });
        contextMenu.getItems().addAll(edit, print, delete);

        this.setContextMenu(contextMenu);
    }
    public void click(){
        //Opened Entry
        Stage dialog = new Stage();
        Parent root = null;
        Scene dialogScene = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("OpenedEntry.fxml"));

        //Pass to Controller
        try {
            root = loader.load();
            dialogScene = new Scene(root);
            controller = loader.getController();
            controller.setEntry(this);
            controller.populate();
        } catch (IOException e) {
            e.printStackTrace();
            
        }

        dialog.setTitle("Edit Entry");
        dialog.setScene(dialogScene);
        dialog.initOwner(App.mainStage);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();

    }

    public long createHash(){
        return System.currentTimeMillis() * 13;
    }

    public void updateButton(){
        if(fields.get("orgName").equals("")){
            this.setText(fields.get("entryDate").toString() + " | " + "Untitled");
        }
        else{
            this.setText(fields.get("entryDate").toString() + " | " + fields.get("orgName"));
        }
    }

    public JSONObject getFields(){
        return fields;
    }

    public void setFields(JSONObject f){
        fields = f;
        updateButton();
    }

    public void print(){
        //Save to PDF
        App.pdfManager.saveToPDF(this, "New-Entry" + ".pdf");
    }

    public void remove(){
        App.entries.remove(this);
        App.ltabController.updateView();
    }

    public int getTotalHours(){
        //Add up total hours
        int total = 0;
        try{total += Integer.parseInt(this.getFields().getString("hour1"));}catch(Exception e){}
        try{total += Integer.parseInt(this.getFields().getString("hour2"));}catch(Exception e){}
        try{total += Integer.parseInt(this.getFields().getString("hour3"));}catch(Exception e){}
        try{total += Integer.parseInt(this.getFields().getString("hour4"));}catch(Exception e){}
        try{total += Integer.parseInt(this.getFields().getString("hour5"));}catch(Exception e){}


        return total;
    }
}
