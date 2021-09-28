package com.lw.lw1_2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class Controller extends View {
    @FXML private TextField text1index;
    @FXML private TextField text2value;
    @FXML private RadioButton rb4change;
    @FXML private RadioButton rb3return;
    @FXML private RadioButton rb2delNode;
    @FXML private RadioButton rb1addNode;
    @FXML private ToggleGroup rbGroup;

    private final Main main = new Main();
    private final Model model = new Model();

    // Действия на клик кнопки
    public void click(MouseEvent mouseEvent) {
        clearPane();
        this.result();
    }

    // Метод отвечающий за построение дерева. Методу место в Model.java
    void calcXY (int [] arr) {

        int ElementsLL = (arr.length + 1) / 2;
        int NumbersL = 1 + (int) Math.round(Math.log(ElementsLL)/Math.log(2));

        int H = Model.diameter * (NumbersL * 2 + 1);
        int W = Model.diameter * (ElementsLL * 2 + 1);

        int iter = 0;

        while (ElementsLL != 1) {
            int y = (int) ((NumbersL * 2 -0.5) * Model.diameter);
            int x = W;

            for (int i = ElementsLL*2 - 2; i > (ElementsLL - 2); i--) {

                if (i == ElementsLL*2 - 2) {
                    // x отступаю на (iter + 0.5)* diameter

                    x = (int) (x - Model.diameter * (Math.pow(2, iter) + 0.5));
                }
                else {
                    // x отступаю на iter * 2 * diameter

                    x = (int) (x - Model.diameter * (Math.pow(2, iter + 1)));
                }


                if (arr[i] != Model.stdValue) {

                    int xParent;
                    int yParent = y - Model.diameter*2;

                    if (i % 2 == 0) {
                        xParent = (int) (x - Model.diameter * (Math.pow(2, iter + 1)) / 2);
                    }
                    else {
                        xParent = (int) (x + Model.diameter * (Math.pow(2, iter + 1)) / 2);
                    }

                    paintNode(x,y,arr[i], H, W, xParent, yParent);

                }
            }

            iter +=1;

            ElementsLL /=2;
            NumbersL -=1;
        }

        paintNode(W/2, (int) (1.5*Model.diameter), arr[0], H, W, W/2, Model.diameter);

    }

    // Действие меню "Файл", подменю "Добавить массив (.txt)"
    public void txtToArray(ActionEvent actionEvent) throws IOException {
        clearPane();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Выберите текстовый файл, содержащий бинарное дерево в массиве");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if(selectedFile != null) {
            String stringArray = new String(Files.readAllBytes(Paths.get(String.valueOf(selectedFile))));

            model.txtToArr(stringArray);
            this.calcXY(model.getExample());

        }
        else {
            System.out.println("File is not valid!");
        }
    }

    // Действие меню "Файл", подменю "Сохранить массив (.txt)"
    public void ArrayToTxt(ActionEvent actionEvent) {
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Выберите место сохранения документа");

            File selectedDirectory = chooser.showDialog(null);

            FileWriter file = new FileWriter(selectedDirectory + "/" + "Array.txt");

            file.write(model.getArrayTree());
            file.close();

        }
        catch (IOException e) {
            System.out.println("Ошибка: " + e);
        }
    }

    // Действие меню "Помощь", подменю "О программе"
    public void about(ActionEvent actionEvent) {
        main.initAbout();
    }

    // Метод, отвечающий за реализацию предусмотренных методов через UI
    private void result() {
        Toggle selectedToggle = rbGroup.getSelectedToggle();
        model.setIndex(Integer.parseInt(text1index.getText()));

        if (rb1addNode.equals(selectedToggle)) {
            model.setValue(Integer.parseInt(text2value.getText()));
            model.addNode(model.getExample(), model.getIndex(), model.getValue());
            this.calcXY(model.getExample());

        } else if (rb2delNode.equals(selectedToggle)) {
            model.delNode(model.getExample(), model.getIndex());
            this.calcXY(model.getExample());

        } else if (rb3return.equals(selectedToggle)) {
            model.returnValueNode(model.getExample(), model.getIndex());
            this.calcXY(model.getExample());
            paintOutline(model.getCoordinatesX(), model.getCoordinatesY());

        } else if (rb4change.equals(selectedToggle)) {
            model.setValue(Integer.parseInt(text2value.getText()));
            model.changeValueNode(model.getExample(), model.getIndex(), model.getValue());
            this.calcXY(model.getExample());
        }
    }
}
