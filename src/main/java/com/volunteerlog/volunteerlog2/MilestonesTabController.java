package com.volunteerlog.volunteerlog2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;

import java.util.Hashtable;

public class MilestonesTabController {

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private TextField hoursNeededField;

    @FXML
    private PieChart pieChart;

    @FXML
    private TextField reqHoursField;

    @FXML
    private TextField totalHoursField;

    public void initialize(){
        App.mtabController = this;
        pieChart.setTitle("Time Spent Volunteering");
        updateCharts();

        totalHoursField.setEditable(false);
    }

    public MilestonesTabController(){

    }

    public void updateCharts(){
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
        for(Entry e : App.entries){
            String key = e.getFields().getString("orgName");
            if(!ht.containsKey(key)){
                ht.put(key, e.getTotalHours());
            }else{
                ht.put(key, ht.get(key) + e.getTotalHours());
            }
        }

        int totalHours = 0;

        for(String key : ht.keySet()){
            if(ht.get(key) <= 0){
                continue;
            }
            if(key.equals("")){
                pieChartData.add(new PieChart.Data("Untitled" + " - " + ht.get(key) + " hours", ht.get(key)));
            }else{
                pieChartData.add(new PieChart.Data(key + " - " + ht.get(key) + " hours", ht.get(key)));
            }
            totalHours += ht.get(key);
        }
        pieChart.layout();
        pieChart.setData(pieChartData);
        pieChart.layout();
        totalHoursField.setText(Integer.toString(totalHours));
    }
}
