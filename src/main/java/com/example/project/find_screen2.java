package com.example.project;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.Vector;


public class find_screen2 extends find_screen {
    @FXML
    private Label nameLabel, locationLabel, servicesLabel;
    @FXML
    private ListView<String> listHospitals;
    @FXML
    private DatePicker datePicker;
    private List<String> itemList = new ArrayList<>();

    private Stage stage;
    private String selectedHospital;
    private Scene scene;
    private Parent root;
    Database db = new Database();
    @FXML
    void hospitalsNear() {
        this.db = new Database("Hospitals.txt");
        Vector<Integer> insuranceIndexes = getInsuranceIndexes();
        Vector<Integer> problemIndexes = getProblemIndex();
        insuranceIndexes.retainAll(problemIndexes);
        itemList.clear();
        insuranceIndexes.retainAll(problemIndexes);
        for (int index : insuranceIndexes) {
            String line = db.printLineByIndex(index);
            itemList.add(line);
        }
        listHospitals.getItems().addAll(itemList);
        listHospitals.getSelectionModel().selectedItemProperty().addListener((new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                String selectedItem = listHospitals.getSelectionModel().getSelectedItem();
                Database db = new Database();

                int selectedHospitalIndex = -1;

                if (selectedItem != null) {
                    selectedHospitalIndex = db.getIndexByValue("Hospitals.txt", selectedItem);
                }
                nameLabel.setText(selectedItem);
                db = new Database("Service.txt");
                String service = db.printLineByIndex(selectedHospitalIndex);
                db = new Database("HospitalLoc.txt");
                String line = db.printLineByIndex(selectedHospitalIndex);
                servicesLabel.setText(service);
                locationLabel.setText(line);
            }
        }));

    }

    @FXML
    void btnBackInfo(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("find_screen.fxml"));
        root = loader.load();
        find_screen Find_screen = loader.getController();
        Find_screen.loadToComboBox();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnNear(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("find_screenFinal.fxml"));
        root = loader.load();
        find_screenFinal finalScreen = loader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        System.out.print(insuranceIndexes);
        finalScreen.setareaIndex(areaIndex);
        finalScreen.setLabels();
        System.out.print(getUserData());
        System.out.print(getareaIndex());
        finalScreen.setUserData(userData);
        stage.show();}

    @FXML
    void btnAdmit(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        LocalDate selectedDate = datePicker.getValue();
        String dateStr;
        if (selectedDate != null) {
            dateStr = selectedDate.toString();
        } else {
            selectedDate = LocalDate.now();
            dateStr = selectedDate.toString();
        }
        System.out.print(getUserData());
        userData.add(dateStr);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("find_screenFinal.fxml"));
        root = loader.load();
        find_screenFinal finalScreen = loader.getController();
        finalScreen.setUserData(userData);
        finalScreen.setLabels();
        selectedHospital = listHospitals.getSelectionModel().getSelectedItem();
        if (selectedHospital != null) {db.storeHospitalToFile(selectedHospital, userData);}
        finalScreen.setSelectedHospital(selectedHospital);
        stage.setScene(scene);
        stage.show();}
    public void setInsuranceIndexes(Vector<Integer> insuranceIndexes) {this.insuranceIndexes = insuranceIndexes;}
    public void setSelectedHospital(String selectedHospital) {
        this.selectedHospital = selectedHospital;
    }
}
