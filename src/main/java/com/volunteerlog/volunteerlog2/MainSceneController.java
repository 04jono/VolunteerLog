package com.volunteerlog.volunteerlog2;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.util.Timer;

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
        tabMilestones.setOnSelectionChanged(e -> {
            if(tabMilestones.isSelected()){
                //Update Milestones with animation
                Timer t = new java.util.Timer();
                t.schedule(new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                App.mtabController.updateCharts();
                            }
                        });
                        t.cancel();
                    }
                }, 50);
            }
        });
    }
    public MainSceneController(){

    }
}
