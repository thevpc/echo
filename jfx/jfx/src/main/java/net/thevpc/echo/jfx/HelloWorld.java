/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
 
public class HelloWorld extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Hello World!");
//        Button btn = new Button();
//        btn.setText("Say 'Hello World'");
//        btn.setOnAction(new EventHandler<ActionEvent>() {
// 
//            @Override
//            public void handle(ActionEvent event) {
//                System.out.println("Hello World!");
//            }
//        });
//        
//        StackPane root = new StackPane();
//        root.getChildren().add(btn);
//        primaryStage.setScene(new Scene(root, 300, 250));
//        primaryStage.show();
//    }
    public void start(final Stage stage) throws Exception {
        BorderPane menuPane = new BorderPane();
        BorderPane borderPane = new BorderPane();
        menuPane.setCenter(borderPane);
        stage.setScene(new Scene(menuPane,600,400));
        ToolBarGroup toolBarGroup = new ToolBarGroup();
        ToolBar toolBar = new ToolBar();
        toolBar.getItems().add(new Button("text"));
        toolBarGroup.getChildren().add(toolBar);
        HBox.setHgrow(toolBar, Priority.ALWAYS);
        ToolBarGroup statusBarGroup = new ToolBarGroup();
        borderPane.setTop(toolBarGroup);
        borderPane.setBottom(statusBarGroup);
        stage.show();
    }
    public void start2(final Stage stage) throws Exception {
        
        
        
        javafx.scene.image.Image ii;
//        ii.getHeight()
        final SplitPane rootPane = new SplitPane();
        rootPane.setOrientation(Orientation.VERTICAL);

        final FlowPane dockedArea = new FlowPane();
        dockedArea.getChildren().add(new Label("Some docked content"));

        final FlowPane centerArea = new FlowPane();
        final Button undockButton = new Button("Undock");
        centerArea.getChildren().add(undockButton);

        rootPane.getItems().addAll(centerArea, dockedArea);

        stage.setScene(new Scene(rootPane, 300, 300));
        stage.show();

        final Dialog dialog = new Dialog(stage);
        undockButton.disableProperty().bind(dialog.showingProperty());
        undockButton.setOnAction(actionEvent -> {
            rootPane.getItems().remove(dockedArea);

            dialog.setOnHidden(windowEvent -> {
                rootPane.getItems().add(dockedArea);
            });
            dialog.setContent(dockedArea);
            dialog.show(stage);
        });
    }

    private class Dialog extends Popup {

        private BorderPane root;

        private Dialog(Window parent) {
            root = new BorderPane();
            root.setPrefSize(200, 200);
            root.setStyle("-fx-border-width: 1; -fx-border-color: gray");
            root.setTop(buildTitleBar());
            setX(parent.getX() + 50);
            setY(parent.getY() + 50);
            getContent().add(root);
        }

        public void setContent(Node content) {
            root.setCenter(content);
        }

        private Node buildTitleBar() {
            BorderPane pane = new BorderPane();
            pane.setStyle("-fx-background-color: burlywood; -fx-padding: 5");

            final Delta dragDelta = new Delta();
            pane.setOnMousePressed(mouseEvent -> {
                dragDelta.x = getX() - mouseEvent.getScreenX();
                dragDelta.y = getY() - mouseEvent.getScreenY();
            });
            pane.setOnMouseDragged(mouseEvent -> {
                setX(mouseEvent.getScreenX() + dragDelta.x);
                setY(mouseEvent.getScreenY() + dragDelta.y);
            });

            Label title = new Label("My Dialog");
            title.setStyle("-fx-text-fill: midnightblue;");
            pane.setLeft(title);

            Button closeButton = new Button("X");
            closeButton.setOnAction(actionEvent -> hide());
            pane.setRight(closeButton);

            return pane;
        }
    }

    private static class Delta {

        double x, y;
    }
}