package com.lw.lw1_2;

import javafx.fxml.FXML;
import javafx.scene.control.PopupControl;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

class View {
    @FXML private Pane pane;

    // Метод отрисовки узла, помещения текста в этот узел и отрисовка линии, ведущей к родительскому узлу
    void paintNode(int x, int y, int text, int height, int weight, int xEnd, int yEnd) {
        Circle circle = new Circle();
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setRadius(20);
        circle.setFill(Color.AQUAMARINE);

        Text t = new Text(x-15, y+5, Integer.toString(text));
        t.setFont(new Font(20));
        t.setTextAlignment(TextAlignment.LEFT);

        Line line = new Line();
        line.setStartX(x);
        line.setStartY(y);
        line.setEndX(xEnd);
        line.setEndY(yEnd);
        line.setStrokeWidth(5);
        line.setStroke(Color.BLUE);

        pane.setPrefHeight(height);
        pane.setPrefWidth(weight);

        pane.getChildren().add(line);
        pane.getChildren().add(circle);
        pane.getChildren().add(t);
    }

    // Метод отрисовки окружности, выделяющей узел пр ивызове метода "returnValueNode"
    void paintOutline (int x, int y) {
        Circle outline = new Circle();
        outline.setCenterX(x);
        outline.setCenterY(y);
        outline.setRadius(20);
        outline.setFill(Color.TRANSPARENT);
        outline.setStroke(Color.RED);
        outline.setStrokeWidth(5);

        pane.getChildren().add(outline);
    }

    // Метод очистки области отрисовки дерева
    public void clearPane() {
        pane.getChildren().clear();
    }
}