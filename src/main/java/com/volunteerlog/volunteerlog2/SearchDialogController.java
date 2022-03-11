package com.volunteerlog.volunteerlog2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchDialogController {

    private Stage stage;
    
    @FXML
    private RadioButton dateRadio;

    @FXML
    private DatePicker dateSearchField;

    @FXML
    private RadioButton nameRadio;

    @FXML
    private TextField nameSearchField;

    @FXML
    private Button searchFinishButton;

    public void initialize(){
        searchFinishButton.setGraphic(Constants.searchImgView2);
        nameSearchField.editableProperty().bind(nameRadio.selectedProperty());
        nameSearchField.disableProperty().bind(nameRadio.selectedProperty().not());
        dateSearchField.editableProperty().bind(dateRadio.selectedProperty());
        dateSearchField.disableProperty().bind(dateRadio.selectedProperty().not());

        searchFinishButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent arg0) {
                if(dateSearchField.getValue() == null){
                    search(nameSearchField.getText(), "");
                    
                }else{
                    search(nameSearchField.getText(), dateSearchField.getValue().toString());
                }
                stage.close();
            }
            
        });
        
    }
    public SearchDialogController(){

    }

    public void search(String orgName, String date){
        App.orgNameSearch = orgName;
        App.dateSearch = date;
        App.ltabController.updateView();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}
