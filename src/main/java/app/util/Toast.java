package app.util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public final class Toast {

    public static void makeText(Stage ownerStage,String toastMsg,int toastDelay,int fadeInDeleay,int fadeOutDelay)
    {
        Stage toastStage = new Stage();
        toastStage.initOwner(ownerStage);
        toastStage.setResizable(false);
        toastStage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text(toastMsg);
        text.setFont(Font.font("Tahoma Bold",40));
        text.setFill(Color.color(118./255.,255./255.,3./255.));

        StackPane root = new StackPane(text);
        root.setStyle("-fx-background-color: #212121; -fx-background-radius: 90;-fx-padding: 50px");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        toastStage.setScene(scene);
        toastStage.show();

        Timeline fadeInTimeLine = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(fadeInDeleay),new KeyValue(toastStage.getScene().getRoot().opacityProperty(),1));
        fadeInTimeLine.getKeyFrames().add(fadeInKey1);
        fadeInTimeLine.setOnFinished((ae)->
        {
            new Thread(()->{
                try {
                    Thread.sleep(toastDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(fadeOutDelay));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeInTimeLine.setOnFinished((aeb)->toastStage.close());
                fadeOutTimeline.play();

            }).start();
        });

        fadeInTimeLine.play();
    }
}
