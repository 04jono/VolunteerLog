package com.volunteerlog.volunteerlog2;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class CalendarTabController {
    private class CalendarDay extends AnchorPane {
        //Smallest unit of calendar, CalendarDay
        private LocalDate date;

        private ArrayList<Button> buttonList;
        
        public CalendarDay(Node... children){
            super(children);


        }

        public LocalDate getDate(){
            return this.date;
        }
        public void setDate(LocalDate date){
            this.date = date;
        }

    }

    @FXML
    private GridPane calendarGridPane;

    @FXML
    private VBox calendarTab;

    @FXML
    private Button nextButton;

    @FXML
    private Button prevButton;
    
    @FXML
    private Label yearMonthLabel;

    private CalendarDay[] calendarDays = new CalendarDay[42];

    private YearMonth currYearMonth;


    public void initialize(){
        currYearMonth = YearMonth.from(LocalDate.now());
        yearMonthLabel.setText(currYearMonth.format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.US)));
        int counter = 0;
        for(int row = 0; row < 6; row++){
            for(int col = 0; col < 7; col++){
                CalendarDay day = new CalendarDay();
                day.setPrefSize(200, 200);
                calendarGridPane.add(day, col, row);
                calendarDays[counter] = day;
                counter++;
            }
        }
        update();

        prevButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                prevMonth();
            }

        });

        nextButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                nextMonth();
            }

        });

        App.ctabController = this;
    }

    public CalendarTabController(){

    }

    public void update(){
        yearMonthLabel.setText(currYearMonth.format(DateTimeFormatter.ofPattern("MMM yyyy", Locale.US)));
        LocalDate startDate = LocalDate.of(currYearMonth.getYear(), currYearMonth.getMonthValue(), 1);
        LocalDate currDay = startDate;
        int dayCounter = -1;
        for(CalendarDay pane : calendarDays){
            dayCounter++;
            if(pane.getChildren().size() != 0){
                pane.getChildren().clear(); //clear
                pane.getStyleClass().clear();
            }
            if(currDay.getDayOfWeek().getValue()%7 != dayCounter%7){
                continue; //line up with correct day of week
            }

            if(currDay.getMonthValue() != startDate.getMonthValue()){
                continue; //skip if wrong month
            }

            Text dayString = new Text(String.valueOf(currDay.getDayOfMonth()));
            dayString.setFont(Font.font(null, FontWeight.BOLD, 18));
            pane.setDate(currDay);
            pane.setTopAnchor(dayString, 3.0);
            pane.setLeftAnchor(dayString, 5.0);
            pane.getChildren().add(dayString);

            //Alternate colors for ribbons
            Color[] colors = {Color.LIGHTBLUE, Color.LIGHTPINK, Color.LIGHTSEAGREEN};
            double[] anchors = {30.0, 50.0, 70.0};
            for(Entry e : App.entries){
                if(pane.getDate().isEqual(LocalDate.parse(e.getFields().getString("entryDate"), App.formatter))){

                    if(pane.getChildren().size() >= 4){
                        continue;
                    }
                    Label label = new Label(e.getFields().getString("orgName"));
                    if(e.getFields().getString("orgName").equals("")){
                        label.setText("Untitled");
                    }
                    label.setAlignment(Pos.CENTER);
                    label.setBackground(new Background(new BackgroundFill(colors[pane.getChildren().size()-1], CornerRadii.EMPTY, Insets.EMPTY)));
                    pane.setTopAnchor(label, anchors[pane.getChildren().size()-1]);
                    pane.setLeftAnchor(label, 2.0);
                    pane.setRightAnchor(label, 2.0);
                    pane.getChildren().add(label);
                }
            }

            pane.getStyleClass().add("calendar-node");
            currDay = currDay.plusDays(1); //increment

        }
        
    }

    private void prevMonth(){
        currYearMonth = currYearMonth.minusMonths(1);
        update();
    }

    private void nextMonth(){
        currYearMonth = currYearMonth.plusMonths(1);
        update();
    }
}
