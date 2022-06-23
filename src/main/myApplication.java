package main;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Future functionality option:
 * Include how many of each part are associated with a product and deduct that from the available list of parts.
 */

public class myApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
