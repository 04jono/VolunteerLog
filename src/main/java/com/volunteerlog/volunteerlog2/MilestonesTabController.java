package com.volunteerlog.volunteerlog2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;

public class MilestonesTabController {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private MenuBar viewMenu;

    @FXML
    private TextField hoursNeededField;

    @FXML
    private PieChart pieChart;

    @FXML
    private TextField reqHoursField;

    @FXML
    private TextField totalHoursField;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private int viewSetting;
    public void initialize(){
        App.mtabController = this;
        pieChart.setTitle("Time Spent Volunteering");
        barChart.setTitle("Months Volunteered");
        xAxis.setLabel("Months");
        yAxis.setLabel("Entries");
        viewSetting = -1;
        updateCharts();

        totalHoursField.setEditable(false);
    }

    public MilestonesTabController(){

    }

    public void updateCharts(){
        //View Menu
        viewMenu.getMenus().clear();
        ArrayList<Integer> array = new ArrayList<>();
        for(Entry e : App.entries){
            int year = LocalDate.parse(e.getFields().getString("entryDate")).getYear();
            if(){
                ht.put(key, 0);
            }else{
                ht.put(key, 0);
            }
        }

        //Pie Chart
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

        //Bar Graph
        //LocalDate.parse(fields.getString("entryDate"))
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String[] monthLabels = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int[] numbers = new int[12];
        for(int i = 0; i < 12; i++){
            numbers[i] = 0;
        }
        for(Entry e : App.entries){
            int ind = LocalDate.parse(e.getFields().getString("entryDate")).getMonthValue()-1;
            numbers[ind]++;
        }
        for(int i = 0; i < 12; i++){
            series.getData().add(new XYChart.Data<>(monthLabels[i], numbers[i]));
        }
        barChart.layout();
        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.layout();
    }
}
