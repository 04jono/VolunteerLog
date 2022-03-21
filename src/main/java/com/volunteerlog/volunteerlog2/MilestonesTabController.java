package com.volunteerlog.volunteerlog2;

import com.itextpdf.layout.element.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class MilestonesTabController {

    @FXML
    private ChoiceBox<String> yearChoiceBox;

    @FXML
    private TextField hourField;

    @FXML
    private Button addMilestoneButton;

    @FXML
    private TableView<Milestone> milestoneTable;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Menu viewMenu;

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
        barChart.setLegendVisible(false);
        xAxis.setLabel("Months");
        yAxis.setLabel("Entries");
        viewSetting = -1;
        updateCharts();

        totalHoursField.setEditable(false);
        reqHoursField.setEditable(false);
        hoursNeededField.setEditable(false);
    }

    public MilestonesTabController(){

    }

    public void updateCharts(){
        //View Menu
        viewMenu.getItems().clear();
        MenuItem allTime = new MenuItem("All time");
        allTime.setOnAction((event)->{
            viewSetting = -1;
            updateCharts();
        });
        viewMenu.getItems().add(allTime);
        ArrayList<Integer> array = new ArrayList<>();
        for(Entry e : App.entries){
            int year = LocalDate.parse(e.getFields().getString("entryDate")).getYear();
            if(array.contains(year)){
                continue;
            }else{
                array.add(year);
            }
        }
        Collections.sort(array);
        for(Integer i : array){
            MenuItem item = new MenuItem(Integer.toString(i));
            item.setOnAction((event) -> {
                viewSetting = (int)i;
                updateCharts();
            });
            viewMenu.getItems().add(item);
        }

        yearChoiceBox.getItems().clear();
        yearChoiceBox.getItems().add("All time");
        for(Integer i : array){
            yearChoiceBox.getItems().add(i.toString());
        }


        //Pie Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
        for(Entry e : App.entries){
            if(viewSetting != -1 && LocalDate.parse(e.getFields().getString("entryDate")).getYear() != viewSetting){
                continue;
            }
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
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String[] monthLabels = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int[] numbers = new int[12];
        for(int i = 0; i < 12; i++){
            numbers[i] = 0;
        }
        for(Entry e : App.entries){
            if(viewSetting != -1 && LocalDate.parse(e.getFields().getString("entryDate")).getYear() != viewSetting){
                continue;
            }
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

    @FXML
    protected void addMilestone(ActionEvent actionEvent) {
        ObservableList<Milestone> data = milestoneTable.getItems();
        if(yearChoiceBox.getValue().equals("") || hourField.getText().equals(""))
            return;
        Milestone m = new Milestone(yearChoiceBox.getValue(), hourField.getText());
        if(!milestoneTable.getItems().contains(m))
            data.add(m);
    }

    public class Milestone{
        private final SimpleStringProperty scope = new SimpleStringProperty("");
        private final SimpleStringProperty hours = new SimpleStringProperty("");

        public Milestone(String sc, String hr) {
            setScope(sc);
            setHours(hr);
        }
        public String getScope(){
            return scope.get();
        }
        public void setScope(String s){
            scope.set(s);
        }
        public String getHours(){
            return hours.get();
        }
        public void setHours(String s){
            hours.set(s);
        }

        @Override
        public boolean equals(Object obj) {
            Milestone milestone = (Milestone)obj;
            return this.getScope().equals(milestone.getScope());
        }
    }
}
