package com.volunteerlog.volunteerlog2;

import com.itextpdf.layout.element.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

public class MilestonesTabController {


    @FXML
    private SplitPane pieSplit;

    @FXML
    private Button deleteButton;

    @FXML
    private SplitPane totalSplit;

    @FXML
    private VBox mtabVBox;

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

    private String filePath;

    public void initialize(){
        App.mtabController = this;
        pieChart.setTitle("Time Spent Volunteering");
        pieChart.setLegendVisible(false);
        barChart.setTitle("Months Volunteered");
        barChart.setLegendVisible(false);
        barChart.setAnimated(false);
        xAxis.setLabel("Months");
        yAxis.setLabel("Entries");
        viewSetting = -1;
        updateCharts();

        totalHoursField.setEditable(false);
        reqHoursField.setEditable(false);
        hoursNeededField.setEditable(false);
        hourField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> { if (!newValue.matches("\\d*")) { hourField.setText(newValue.replaceAll("[^\\d]", ""));}});

        deleteButton.setOnAction(e -> {
            Milestone selectedItem = milestoneTable.getSelectionModel().getSelectedItem();
            milestoneTable.getItems().remove(selectedItem);
            updateCharts();
            save();
        });
    }

    public MilestonesTabController(){

    }

    public void updateCharts(){

        mtabVBox.layout();
        totalSplit.layout();
        pieSplit.layout();

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
            int year = LocalDate.parse(e.getFields().getString("entryDate"), App.formatter).getYear();
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

        //Milestone Tool
        yearChoiceBox.getItems().clear();
        yearChoiceBox.getItems().add("All time");
        for(Integer i : array){
            yearChoiceBox.getItems().add(i.toString());
        }

        reqHoursField.setText("0");
        for(Milestone m : milestoneTable.getItems()){
            reqHoursField.setText("0");
            if(viewSetting == -1 && new Milestone("All time", "").equals(m)){
                reqHoursField.setText(m.getHours());
                break;
            }
            if(new Milestone(Integer.toString(viewSetting), "").equals(m)){
                reqHoursField.setText(m.getHours());
                break;
            }
        }


        //Pie Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
        for(Entry e : App.entries){
            if(viewSetting != -1 && LocalDate.parse(e.getFields().getString("entryDate"), App.formatter).getYear() != viewSetting){
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
        pieChart.setData(pieChartData);

        totalHoursField.setText(Integer.toString(totalHours));

        //Hours Needed
        int hoursNeeded = Integer.parseInt(reqHoursField.getText()) - Integer.parseInt(totalHoursField.getText());
        if(hoursNeeded <= 0){
            hoursNeededField.setText("0");
        }else{
            hoursNeededField.setText(Integer.toString(hoursNeeded));
        }


        //Bar Graph
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        String[] monthLabels = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
        int[] numbers = new int[12];
        for(int i = 0; i < 12; i++){
            numbers[i] = 0;
        }
        for(Entry e : App.entries){
            if(viewSetting != -1 && LocalDate.parse(e.getFields().getString("entryDate"), App.formatter).getYear() != viewSetting){
                continue;
            }
            int ind = LocalDate.parse(e.getFields().getString("entryDate"), App.formatter).getMonthValue()-1;
            numbers[ind]++;
        }
        for(int i = 0; i < 12; i++){
            series.getData().add(new XYChart.Data<>(monthLabels[i], numbers[i]));
        }
        barChart.getData().clear();
        barChart.getData().add(series);

        mtabVBox.layout();
        totalSplit.layout();
        pieSplit.layout();
    }

    @FXML
    protected void addMilestone(ActionEvent actionEvent) {
        ObservableList<Milestone> data = milestoneTable.getItems();
        if(yearChoiceBox.getValue() == null)
            return;
        if(yearChoiceBox.getValue().equals("") || hourField.getText().equals(""))
            return;
        Milestone m = new Milestone(yearChoiceBox.getValue(), hourField.getText());
        if(!milestoneTable.getItems().contains(m)){
            data.add(m);
            updateCharts();
            hourField.setText("");
        }

        save();
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

    public void save(){
        try{
            JSONArray saveArray = new JSONArray();
            PrintWriter pw = new PrintWriter(new PrintWriter(filePath));
            for(Milestone m : milestoneTable.getItems()){
                JSONObject newObj = new JSONObject();
                newObj.put(m.getScope(), m.getHours());
                saveArray.put(newObj);
            }
            pw.println(saveArray.toString(4));
            pw.close();
        }catch(Exception e){
            System.out.println("COULD NOT SAVE");
        }
    }

    public void load(){
        try{
            InputStream inputStream = new FileInputStream(filePath);
            JSONTokener jstokener = new JSONTokener(inputStream);
            JSONArray loadArray = new JSONArray(jstokener);
            for(int i = 0; i < loadArray.length();i++){
                JSONObject obj = loadArray.getJSONObject(i);
                String scope = "";
                String hours = "";
                for(String year : obj.keySet()){
                    scope = year;
                    hours = obj.getString(year);
                }
                Milestone m = new Milestone(scope,hours);
                milestoneTable.getItems().add(m);
            }
        }catch(Exception e){
            System.out.println("COULD NOT LOAD");
        }
    }

    public void setFilePath(String fp){
        filePath = fp;
    }
}
