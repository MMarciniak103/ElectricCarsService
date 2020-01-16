package app.googleMaps;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import java.net.URL;

public class Browser extends Pane {

    private double lat;
    private double lon;

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();

    public Browser(){

        final URL urlGoogleMaps = getClass().getResource("/html/googleMap.html");
        webEngine.load(urlGoogleMaps.toExternalForm());
        webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                System.out.println(event.toString());
            }
        });

        getChildren().add(webView);

        final TextField latitude = new TextField(""+51.110568);
        final TextField longitude = new TextField(""+17.035038);
        Button update = new Button("Update");
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lat = Double.parseDouble(latitude.getText());
                lon = Double.parseDouble(longitude.getText());

                System.out.printf("%.2f %.2f%n",lat,lon);

                webEngine.executeScript(""+
                        "window.lat = "+lat+";"+
                        "window.lon = "+lon+";"+
                        "document.goToLocation(window.lat,window.lon);");
            }
        });

        HBox toolbar = new HBox();
        toolbar.getChildren().addAll(latitude,longitude,update);

        getChildren().addAll(toolbar);
    }

}
