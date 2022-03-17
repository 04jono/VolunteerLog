package com.volunteerlog.volunteerlog2;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

    //Current search
    public static String orgNameSearch;
    public static String dateSearch;
    public static int startIndexSearch;

    public static CalendarTabController ctabController;
    public static LogTabController ltabController;
    public static MilestonesTabController mtabController;

    public static Stage mainStage;

    //Managers
    public static SaveManager saveManager;
    public static PDFManager pdfManager;
    public static LoginManager loginManager;

    public static ArrayList<Entry> entries;
    public static void main(String[] args){
        orgNameSearch = "";
        dateSearch = "";
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        entries = new ArrayList<Entry>();
        saveManager = new SaveManager();
        pdfManager = new PDFManager(getHostServices());
        loginManager = new LoginManager();



        Parent root;
        try{
            mainStage = primaryStage;
            root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("EntryStyle.css").toExternalForm());
            scene.getStylesheets().add(getClass().getResource("CalendarNodeStyle.css").toExternalForm());
            primaryStage.setResizable(false);
            primaryStage.setTitle("Volunteer Log");
            primaryStage.setScene(scene);
            primaryStage.hide();
            

        }catch(IOException e){
            System.out.println("Error!");
            e.printStackTrace();

        }

        //Opened Login
        Stage dialog = new Stage();
        Parent parent = null;
        Scene dialogScene = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDialog.fxml"));
        try {
            parent = loader.load();
            dialogScene = new Scene(parent);
        } catch (IOException e) {
            e.printStackTrace();
            
        }

        dialog.setTitle("Sign In");
        dialog.setScene(dialogScene);
        dialog.initOwner(primaryStage);
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
}
