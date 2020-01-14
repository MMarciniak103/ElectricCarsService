package app.util;

import javafx.scene.control.Alert;

public final class  DialogUtils {

    public static void invalidUsernameDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Invalid Username");
        alert.setContentText("This username is already taken, please choose different one");
        alert.showAndWait();
    }

    public static void differentPasswordsDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Uncorrect password");
        alert.setContentText("Passwords values are different. Please oonfirm your password!");
        alert.showAndWait();
    }
}
