package app.controllers;

import app.dao.GenericHibernateDao;
import app.entity.UserEntity;
import app.googleMaps.Browser;
import app.service.UserService;
import app.util.Toast;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController {

    @FXML
    public AnchorPane loginScreen;
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

    private GenericHibernateDao hibernateDao;

    private UserService userService;

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

        hibernateDao = new GenericHibernateDao();
        hibernateDao.setClazz(UserEntity.class);

        userService = new UserService();
        userService.setDao(hibernateDao);

        login.bind(usernameTxt.textProperty());
        password.bind(passwordTxt.textProperty());


    }

    // TODO
    @FXML
    void loginProcedure(ActionEvent event) {
        UserEntity user = userService.findUser(login.get(), password.get());

        if(user != null){

            System.out.println("Login Succesfull");
            System.out.println(user);
            UserEntity.Role userRole = user.getRole();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        else{
            System.out.println("Login unsuccesfull "+login.get()+" "+password.get());

        }
    }

    @FXML
    void registerForm(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/registrationForm.fxml"));
            Parent root = loader.load();

            RegistrationController registrationController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.showAndWait();


            if(registrationController.registrationStatus()){
                // TODO
//                String toastMsg = "Registration Succesfull";
//                int toastMsgTime = 2500;
//                int fadeInTime = 500;
//                int fadeOutTime = 500;
//
//                Stage loginStage = (Stage) loginScreen.getScene().getWindow();
//                Toast.makeText(null,toastMsg,toastMsgTime,fadeInTime,fadeOutTime);
            }


        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

}
