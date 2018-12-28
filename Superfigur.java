package Labb4;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Superfigur extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane bp = new BorderPane();
        ToolBar toolBar = new ToolBar();
        Button knapp = new Button("Se medlemmar");
        Button knapp2 = new Button("Lägg till medlem");
        Button knapp3 = new Button("Sök");

        toolBar.getItems().addAll(knapp, knapp2, knapp3);

        ObservableList<String> observableList = FXCollections.observableArrayList("Superman", "Iron Man", "Spider Man");
        ListView<String> listView = new ListView<>(observableList);

        VBox vbox = new VBox();
        VBox vbox2 = new VBox();

        Text filter = new Text("Filter");
        vbox2.getChildren().addAll(filter);

        Text info = new Text("Information");
        vbox.getChildren().addAll(info);

        bp.setCenter(listView);
        bp.setTop(toolBar);
        bp.setLeft(vbox);
        bp.setRight(vbox2);

        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
