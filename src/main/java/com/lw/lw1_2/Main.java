package com.lw.lw1_2;

import java.io.IOException;
import java.util.Objects;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Бинарное дерево в массиве");
        this.primaryStage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResourceAsStream("binary-tree.png"))));
        initRoot();
    }

    // Метод инициализации основного родительского окна
    public void initRoot() {
        try {
            FXMLLoader root = new FXMLLoader();
            root.setLocation(Main.class.getResource("sample.fxml"));
            SplitPane page = root.load();

            primaryStage.setScene(new Scene(page));
            primaryStage.setResizable(false);
            primaryStage.show();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод инициализации дополнительного окна "О программе"
    public void initAbout() {
        try {
            FXMLLoader about = new FXMLLoader();
            about.setLocation(Main.class.getResource("About.fxml"));
            Pane page = about.load();

            Stage stageAbout = new Stage();
            stageAbout.setTitle("О программе");
            stageAbout.initModality(Modality.APPLICATION_MODAL);
            stageAbout.initOwner(primaryStage);
            stageAbout.setScene(new Scene(page));
            stageAbout.setResizable(false);
            stageAbout.showAndWait();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}