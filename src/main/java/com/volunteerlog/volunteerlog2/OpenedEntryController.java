package com.volunteerlog.volunteerlog2;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.json.JSONObject;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OpenedEntryController {

    private Entry entry;

    private JSONObject fields;

    @FXML
    private TextArea accomplishArea;

    @FXML
    private Button cancelButton;

    @FXML
    private DatePicker date1;

    @FXML
    private DatePicker date2;

    @FXML
    private DatePicker date3;

    @FXML
    private DatePicker date4;

    @FXML
    private DatePicker date5;

    @FXML
    private DatePicker entryDate;

    @FXML
    private Button finishButton;

    @FXML
    private TextField firstName;

    @FXML
    private TextField gradYear;

    @FXML
    private TextField hour1;

    @FXML
    private TextField hour2;

    @FXML
    private TextField hour3;

    @FXML
    private TextField hour4;

    @FXML
    private TextField hour5;

    @FXML
    private TextField lastName;

    @FXML
    private TextArea learnArea;

    @FXML
    private TextField orgName;

    @FXML
    private TextField phoneNum;

    @FXML
    private TextArea prepareArea;

    @FXML
    private Button printButton;

    @FXML
    private TextField supervisorEmail;

    @FXML
    private TextField supervisorName;

    @FXML
    private TextField supervisorPhone;

    @FXML
    private TextField supervisorTitle;

    public void initialize(){
        finishButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                onFinish();
            }

        });

        printButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                onPrint();
            }

        });
    }
    public OpenedEntryController(){

    }

    public void onFinish(){
        Stage stage = (Stage)finishButton.getScene().getWindow();
        stage.close();

        fields.put("accomplishArea", accomplishArea.getText());
        if(date1.getValue() != null) fields.put("date1", date1.getValue().toString());
        if(date2.getValue() != null) fields.put("date2", date2.getValue().toString());
        if(date3.getValue() != null) fields.put("date3", date3.getValue().toString());
        if(date4.getValue() != null) fields.put("date4", date4.getValue().toString());
        if(date5.getValue() != null) fields.put("date5", date5.getValue().toString());
        if(entryDate.getValue() != null) fields.put("entryDate", entryDate.getValue().toString());
        fields.put("firstName", firstName.getText());
        fields.put("gradYear", gradYear.getText());
        fields.put("hour1", hour1.getText());
        fields.put("hour2", hour2.getText());
        fields.put("hour3", hour3.getText());
        fields.put("hour4", hour4.getText());
        fields.put("hour5", hour5.getText());
        fields.put("lastName", lastName.getText());
        fields.put("learnArea", learnArea.getText());
        fields.put("orgName", orgName.getText());
        fields.put("phoneNum", phoneNum.getText());
        fields.put("prepareArea", prepareArea.getText());
        fields.put("supervisorEmail", supervisorEmail.getText());
        fields.put("supervisorName", supervisorName.getText());
        fields.put("supervisorPhone", supervisorPhone.getText());
        fields.put("supervisorTitle", supervisorTitle.getText());

        entry.setFields(fields);
        entry.updateButton();
        App.ltabController.updateView();
    }

    public void onPrint(){
        onFinish();
        App.pdfManager.saveToPDF(entry, "New-Entry" + ".pdf");
    }

    //Entry management
    public void setEntry(Entry e){
        entry = e;
        fields = entry.getFields();
    }

    public void populate(){
        accomplishArea.setText(fields.getString("accomplishArea"));
        try{date1.setValue(LocalDate.parse(fields.getString("date1")));}catch(DateTimeParseException e){};
        try{date2.setValue(LocalDate.parse(fields.getString("date2")));}catch(DateTimeParseException e){};
        try{date3.setValue(LocalDate.parse(fields.getString("date3")));}catch(DateTimeParseException e){};
        try{date4.setValue(LocalDate.parse(fields.getString("date4")));}catch(DateTimeParseException e){};
        try{date5.setValue(LocalDate.parse(fields.getString("date5")));}catch(DateTimeParseException e){};
        try{entryDate.setValue(LocalDate.parse(fields.getString("entryDate")));}catch(DateTimeParseException e){};
        firstName.setText(fields.getString("firstName"));
        gradYear.setText(fields.getString("gradYear"));
        hour1.setText(fields.getString("hour1"));
        hour2.setText(fields.getString("hour2"));
        hour3.setText(fields.getString("hour3"));
        hour4.setText(fields.getString("hour4"));
        hour5.setText(fields.getString("hour5"));
        lastName.setText(fields.getString("lastName"));
        learnArea.setText(fields.getString("learnArea"));
        orgName.setText(fields.getString("orgName"));
        phoneNum.setText(fields.getString("phoneNum"));
        prepareArea.setText(fields.getString("prepareArea"));
        supervisorEmail.setText(fields.getString("supervisorEmail"));
        supervisorName.setText(fields.getString("supervisorName"));
        supervisorPhone.setText(fields.getString("supervisorPhone"));
        supervisorTitle.setText(fields.getString("supervisorTitle"));

    }

}
