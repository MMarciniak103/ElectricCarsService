package app.controllers;

import app.dao.GenericHibernateDao;
import app.entity.UserEntity;
import app.service.UserService;
import app.util.DialogUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXPasswordField pswdTxt;

    @FXML
    private JFXPasswordField pswdConfirmTxt;

    @FXML
    private JFXButton registrationBtn;

    @FXML
    private JFXButton backBtn;

    @FXML
    private ImageView pswdConfirmIcon;

    @FXML
    private ImageView usernameIcon;

    @FXML
    private ImageView pswdIcon;

    private StringProperty username;
    private StringProperty pswd;
    private StringProperty pswdConfirmation;

    private GenericHibernateDao dao;
    private UserService userService;


    private boolean registrationStatus;

    @FXML
    void initialize() {
        assert usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert pswdTxt != null : "fx:id=\"pswdTxt\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert pswdConfirmTxt != null : "fx:id=\"pswdConfirmTxt\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert registrationBtn != null : "fx:id=\"registrationBtn\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert backBtn != null : "fx:id=\"backBtn\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert pswdConfirmIcon != null : "fx:id=\"pswdConfirmIcon\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert usernameIcon != null : "fx:id=\"usernameIcon\" was not injected: check your FXML file 'registrationForm.fxml'.";
        assert pswdIcon != null : "fx:id=\"pswdIcon\" was not injected: check your FXML file 'registrationForm.fxml'.";

        username = new SimpleStringProperty();
        pswd = new SimpleStringProperty();
        pswdConfirmation = new SimpleStringProperty();

        registrationStatus = false;

        usernameIcon.setVisible(false);
        pswdIcon.setVisible(false);
        pswdConfirmIcon.setVisible(false);

        username.bind(usernameTxt.textProperty());
        pswd.bind(pswdTxt.textProperty());
        pswdConfirmation.bind(pswdConfirmTxt.textProperty());

        dao = new GenericHibernateDao();
        dao.setClazz(UserEntity.class);

        userService = new UserService();
        userService.setDao(dao);

        pswd.addListener(event -> passwordsCheck());
        pswdConfirmation.addListener(event -> passwordsCheck());
    }

    private void passwordsCheck() {
        if(pswd.get().equals(pswdConfirmation.get()) && !pswd.get().equals("")){
            pswdIcon.setVisible(true);
            pswdConfirmIcon.setVisible(true);
        }
    }


    @FXML
    void backToLoginPage(ActionEvent event) {
        Stage stage = (Stage) registrationBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void register(ActionEvent event) {

        UserEntity user = userService.findByName(username.get());
        if(user != null){
            usernameIcon.setVisible(false);
            DialogUtils.invalidUsernameDialog();
        }
        else{
            usernameIcon.setVisible(true);
            if(pswd.get().equals(pswdConfirmation.get())) {

                UserEntity tempUser = new UserEntity();
                tempUser.setLogin(username.get());
                tempUser.setPassword(pswd.get());
                tempUser.setRole(UserEntity.Role.NORMAL);

                userService.create(tempUser);

                registrationStatus = true;

                Stage stage = (Stage) registrationBtn.getScene().getWindow();
                stage.close();


            }
            else{
                DialogUtils.differentPasswordsDialog();
            }
        }

    }

    public boolean registrationStatus() {
        return registrationStatus;
    }
}