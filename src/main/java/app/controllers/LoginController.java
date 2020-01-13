package app.controllers;

import app.dao.UserDao;
import app.entity.UserEntity;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXButton registerBtn;

    private UserDao userDao;

    private StringProperty login;
    private StringProperty password;


    @FXML
    void initialize() {
        assert usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'loginScreen.fxml'.";
        assert passwordTxt != null : "fx:id=\"passwordTxt\" was not injected: check your FXML file 'loginScreen.fxml'.";
        assert loginBtn != null : "fx:id=\"loginBtn\" was not injected: check your FXML file 'loginScreen.fxml'.";
        assert registerBtn != null : "fx:id=\"registerBtn\" was not injected: check your FXML file 'loginScreen.fxml'.";

        login = new SimpleStringProperty();
        password = new SimpleStringProperty();

        userDao = new UserDao();

        login.bind(usernameTxt.textProperty());
        password.bind(passwordTxt.textProperty());

    }

    // TODO
    @FXML
    void loginProcedure(ActionEvent event) {
        UserEntity user = userDao.findUser(login.get(), password.get());
        if(user != null){

            System.out.println("Login Succesfull");
            System.out.println(user);
        }
        else{
            System.out.println("Login unsuccesfull "+login.get()+" "+password.get());
        }
    }

    @FXML
    void registerForm(ActionEvent event) {
        
    }

}
