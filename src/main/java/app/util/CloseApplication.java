package app.util;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CloseApplication {

    public static void cloeApplication(){
        Optional<ButtonType> result = DialogUtils.confirmationDialog();
        if(result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }
}
