package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class find_screenFinal extends find_screen2 {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label lblHospitalName;

    @FXML
    private Label lblLocation;

    @FXML
    private Label lblServices;

    @FXML
    private DatePicker datePicker;

    Database db = new Database();
    @FXML
    private void initialize() {

    }
    @FXML
    public void setLabels(){
        int areaIndex = getareaIndex();
        System.out.print(areaIndex);
        db = new Database("HospitalLoc.txt");
        String location = db.printLineByIndex(areaIndex);
        lblLocation.setText(location);
        db = new Database("Hospitals.txt");
        String hospital = db.printLineByIndex(areaIndex);
        lblHospitalName.setText(hospital);
        db = new Database("Service.txt");
        String services = db.printLineByIndex(areaIndex);
        lblServices.setText(services);
        System.out.print(getUserData());

    }
    @FXML
    void btnAdmit(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        LocalDate selectedDate = datePicker.getValue();
        String dateStr;
        if (selectedDate != null) {
            dateStr = selectedDate.toString();
        } else {
            selectedDate = LocalDate.now();
            dateStr = selectedDate.toString();
        }
        userData.add(dateStr);

        db = new Database("Hospitals.txt");
        int index = getareaIndex();
        String selectedHospital = db.printLineByIndex(index);
        if (selectedHospital != null) {
            db.storeHospitalToFile(selectedHospital, userData);
            System.out.print(userData);}

        stage.show();}
    @FXML
    void btnBackNear(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("find_screen.fxml"));
        root = loader.load();
        find_screen Find_screen = loader.getController();
        Find_screen.loadToComboBox();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();}}