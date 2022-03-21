package com.volunteerlog.volunteerlog2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginDialogController {

    @FXML
    private Hyperlink createAccountLink;

    @FXML
    private Label header;

    @FXML
    private Text helpText;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordFieldVisible;

    @FXML
    private TextField usernameField;

    private boolean signIn;

    public void initialize(){
        signIn = true;
        passwordFieldVisible.setVisible(false);
        createAccountLink.setVisited(false);
        createAccountLink.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                if(signIn){
                    signIn = false;
                    header.setText("Create Account");
                    helpText.setText("Already have an account?");
                    createAccountLink.setText("Sign in");
                    loginButton.setText("Create Account");
                    passwordField.setVisible(false);
                    passwordFieldVisible.setVisible(true);
                    usernameField.setText("");
                    passwordFieldVisible.setText("");
                    passwordField.setText("");
                    Stage stage = (Stage)loginButton.getScene().getWindow();
                    stage.setTitle("Create Account");
                }else{
                    signIn = true;
                    header.setText("Sign In");
                    helpText.setText("New to Volunteer Log?");
                    createAccountLink.setText("Create an account");
                    loginButton.setText("Sign In");
                    passwordField.setVisible(true);
                    passwordFieldVisible.setVisible(false);
                    usernameField.setText("");
                    passwordFieldVisible.setText("");
                    passwordField.setText("");
                    Stage stage = (Stage)loginButton.getScene().getWindow();
                    stage.setTitle("Sign In");
                }
                createAccountLink.setVisited(false);
            }

        });

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent arg0) {
                if(signIn){
                    if(App.loginManager.validate(usernameField.getText(), passwordField.getText())){
                        App.saveManager.setFilePath("saves/" + usernameField.getText() + ".json");
                        App.saveManager.load();
                        App.mtabController.setFilePath("saves/" + usernameField.getText() + ".milestones");
                        App.mtabController.load();

                        App.mainStage.show();
                        Stage stage = (Stage)loginButton.getScene().getWindow();
                        stage.close();
                    }else{

                    }
                }
                else{
                    if(!(usernameField.getText().equals("")) && !(App.loginManager.contains(usernameField.getText())) && !(passwordFieldVisible.getText().equals(""))){
                        App.loginManager.addUser(usernameField.getText(), passwordFieldVisible.getText());
                        App.loginManager.save();
                        App.saveManager.setFilePath("saves/" + usernameField.getText() + ".json");
                        App.mtabController.setFilePath("saves/" + usernameField.getText() + ".milestones");

                        App.mainStage.show();
                        Stage stage = (Stage)loginButton.getScene().getWindow();
                        stage.close();
                    }
                }
            }

        });
    }

    public LoginDialogController(){

    }
}
