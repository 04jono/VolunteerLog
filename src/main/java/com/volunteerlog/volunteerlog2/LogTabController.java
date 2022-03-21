package com.volunteerlog.volunteerlog2;

import java.util.ArrayList;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LogTabController {

    private final int tabFactor = 20;

    private SearchDialogController searchController;

    @FXML
    private VBox logLeftVBox;

    @FXML
    private GridPane logRightGridPane;

    @FXML
    private Button logbuttonNew;

    @FXML
    private Label pageNumLabel;

    @FXML
    private Button rightPage;

    @FXML
    private Button leftPage;

    @FXML
    private Button saveButton;

    @FXML
    private Button searchButton;

    @FXML
    private TextArea searchParameters;

    @FXML
    private Button clearButton;

    private ArrayList<Entry> viewable;

    public void initialize(){
        searchParameters.setEditable(false);
        App.ltabController = this;
        App.startIndexSearch = 0;
        App.orgNameSearch = "";
        viewable = new ArrayList<Entry>();
        //updateView();
        logbuttonNew.setGraphic(Constants.addNewImgView);
        searchButton.setGraphic(Constants.searchImgView1);
        logbuttonNew.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                Entry e = new Entry();
                App.entries.add(e);
                updateView();
                e.click();
            }

        });
        saveButton.setGraphic(Constants.saveImgView);
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                App.saveManager.save();
            }

        });

        SearchDialogController searchController = null;

        searchButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                openSearch();
            }
        });

        clearButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                App.orgNameSearch = "";
                App.dateSearch = "";
                updateView();
            }
        });

        pageNumLabel.setText(String.format("%03d", App.startIndexSearch+1) + " - " + String.format("%03d", App.startIndexSearch+tabFactor));

        leftPage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                if(App.startIndexSearch >= tabFactor){
                    App.startIndexSearch -= tabFactor;
                    pageNumLabel.setText(String.format("%03d", App.startIndexSearch+1) + " - " + String.format("%03d", App.startIndexSearch+tabFactor));
                    updateView();
                }
            }
        });

        rightPage.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                if(App.startIndexSearch < viewable.size()){
                    App.startIndexSearch += tabFactor;
                    pageNumLabel.setText(String.format("%03d", App.startIndexSearch+1) + " - " + String.format("%03d", App.startIndexSearch+tabFactor));
                    updateView();
                }
            }
        });
                
    }

    public LogTabController(){

    }

    public void clearUI(){ viewable.clear(); }
    public void addUI(Entry entry){ viewable.add(entry); }

    public void updateView(){
        clearUI();
        int startIndex = App.startIndexSearch;
        String orgName = App.orgNameSearch;
        String date = App.dateSearch;

        if(App.orgNameSearch.equals("") && App.dateSearch.equals("")){
            clearButton.setVisible(false);
            searchParameters.setDisable(true);
        }else{
            clearButton.setVisible(true);
            searchParameters.setDisable(false);
        }
        String parameters = App.orgNameSearch + "\n" + App.dateSearch;
        searchParameters.setText(parameters);
        //Update based on active search
        for(Entry entry : App.entries){
            //Neither
            if(App.orgNameSearch.equals("") && App.dateSearch.equals("")){
                addUI(entry);
            }
            //Name Only
            else if(entry.getFields().getString("orgName").toLowerCase().equals(orgName.toLowerCase()) && App.dateSearch.equals("")){
                addUI(entry);
            }
            //Date Only
            else if(App.orgNameSearch.equals("") && LocalDate.parse(entry.getFields().getString("entryDate"), App.formatter).equals(LocalDate.parse(date, App.formatter))){
                addUI(entry);
            }
            else if(entry.getFields().getString("orgName").toLowerCase().equals(orgName.toLowerCase()) && LocalDate.parse(entry.getFields().getString("entryDate"), App.formatter).equals(LocalDate.parse(date, App.formatter))){
                addUI(entry);
            }
        }
        logRightGridPane.getChildren().clear();
        for(int i = startIndex; i < startIndex+tabFactor; i++){
            if(i >= viewable.size()){
                break;
            }
            logRightGridPane.add(viewable.get(i), 0, logRightGridPane.getChildren().size());
        }

    }

    public void openSearch(){
        //Open Search
        Stage dialog = new Stage();
        Parent root = null;
        Scene dialogScene = null;
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchDialog.fxml"));

        //Pass to Controller
        try{
            root = loader.load();
            dialogScene = new Scene(root);
            searchController = loader.getController();
            searchController.setStage(dialog);
        } catch (IOException e){
            e.printStackTrace();
        }

        dialog.setTitle("Search");
        dialog.setScene(dialogScene);
        dialog.initOwner(App.mainStage);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
