package com.volunteerlog.volunteerlog2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class MainSceneController {

    @FXML
    private AnchorPane anchorFrame;

    @FXML
    private Tab tabCalendar;

    @FXML
    private Tab tabLog;

    @FXML
    private Tab tabMilestones;

    @FXML
    private TabPane tabPane;

    public void initialize(){
        tabLog.setGraphic(Constants.logImgView);
        tabMilestones.setGraphic(Constants.milestonesImgView);
        tabCalendar.setGraphic(Constants.calendarImgView);

        tabCalendar.setOnSelectionChanged(e -> {
            App.ctabController.update();
        });
    }
    public MainSceneController(){

    }
}
