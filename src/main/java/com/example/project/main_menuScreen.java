package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class main_menuScreen extends find_screenFinal {
    private Stage stage;
    private Scene scene;
    private Parent root;

    Database db = new Database();


    public void btnExit(ActionEvent event) {
        System.exit(0);
    }

    public void btnFind(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("find_screen.fxml"));
        root = loader.load();
        find_screen Find_screen = loader.getController();
        Find_screen.loadToComboBox();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
 
    public void btnView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view_screen.fxml"));
        root = loader.load();
        view_controller View = loader.getController();
        View.loadToComboBox();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }}



