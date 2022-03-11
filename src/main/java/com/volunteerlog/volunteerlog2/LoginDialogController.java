package com.volunteerlog.volunteerlog2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginDialogController {

    @FXML
    private Hyperlink createAccountLink;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    public void initialize(){
        createAccountLink.setVisited(false);
        createAccountLink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                System.out.println("Create Account");
                createAccountLink.setVisited(false);
            }

        });

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                if(App.loginManager.validate(usernameField.getText(), passwordField.getText())){
                    App.saveManager.setFilePath("saves/" + usernameField.getText() + ".json");
                    App.saveManager.load();
                    App.mainStage.show();
                    Stage stage = (Stage)loginButton.getScene().getWindow();
                    stage.close();
                }else{

                }
            }

        });
    }

    public LoginDialogController(){

    }
}
