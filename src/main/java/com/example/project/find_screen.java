package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Vector;

public class find_screen{

    @FXML
    private TextField tfLast, tfMid, tfFirst, tfAge;

    @FXML
    private ComboBox<String> cboInsurance, cboProblem, cboArea;

    private Stage stage;
    private Scene scene;
    private Parent root;

    public Vector<String> userData = new Vector<>();
    public Vector<Integer> insuranceIndexes = new Vector<>();
    public Vector<Integer> problemIndex = new Vector<>();

    public int areaIndex;


    Database db = new Database();
    @FXML
    private void initialize() {

    }
    @FXML
    void btnSubmit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("find_screen2.fxml"));
        root = loader.load();
        find_screen2 find_screen2 = loader.getController();
        getData();
        find_screen2.setUserData(userData);
        find_screen2.setInsuranceIndexes(insuranceIndexes);
        find_screen2.setProblemIndexes(problemIndex);
        find_screen2.setareaIndex(areaIndex);
        find_screen2.hospitalsNear();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void btnBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loadToComboBox() {
        db = new Database("Insurance.txt");
        db.loadtocombobox(cboInsurance);
        db = new Database("Area.txt");
        db.loadtocombobox(cboArea);
        db = new Database("Problems.txt");
        db.loadtocombobox(cboProblem);
        cboInsurance.getSelectionModel().selectFirst();
        cboArea.getSelectionModel().selectFirst();
        cboProblem.getSelectionModel().selectFirst();

    }

    public void getData() {
        String firstName = tfFirst.getText().trim();
        String middleName = tfMid.getText().trim();
        String lastName = tfLast.getText().trim();
        String age = tfAge.getText().trim();

        // Check if first name is empty, set default value
        if (firstName.isEmpty()) {
            firstName = "Jay";
        }
        // Check if last name is empty, set default value
        if (lastName.isEmpty()) {
            lastName = "Gamao";
        }
        // Check if middle name is empty, set default value
        if (middleName.isEmpty()) {
            middleName = "M";
        }
        // Check if age is empty, set default value
        if (age.isEmpty()) {
            age = "19";
        }
        userData.add(lastName);
        userData.add(firstName);
        userData.add(middleName);
        userData.add(age);

        String problem = cboProblem.getValue();
        userData.add(problem);
        String insurance = cboInsurance.getValue();
        userData.add(insurance);
        String area = cboArea.getValue();
        setUserData(userData);
        Database db = new Database();
        getIndexesFromDataFile("Problems.txt", problem);
        getIndexesFromDataFile("Insurance.txt", insurance);
        areaIndex = db.getIndexByValue("Area.txt", area);
        System.out.println(area);

        setareaIndex(areaIndex);
        Vector<Integer> problemIndex = db.getIndexesOfMatchingLines("Service.txt", problem);
        setProblemIndexes(problemIndex);
        System.out.print(problemIndex);
        Vector<Integer> insuranceIndexes = db.getIndexesOfMatchingLines("HospitalInsurance.txt", insurance);
        setInsuranceIndexes(insuranceIndexes);
        System.out.println(areaIndex);


    }

    public void getIndexesFromDataFile(String filename, String searchString) {
        Vector<Integer> indexes = db.getIndexesOfMatchingLines(filename, searchString);

        if (!indexes.isEmpty()) {
            if (filename.equals("Services.txt")) {
                problemIndex.addAll(indexes);
            } else if (filename.equals("HospitalInsurance.txt")) {
                insuranceIndexes.addAll(indexes);
            }
        }
    }

    public void setProblemIndexes(Vector<Integer> ProblemIndexes) {
        this.problemIndex = ProblemIndexes;
    }
    public void setareaIndex(int areaIndex) {
        this.areaIndex = areaIndex;
    }

    public int getareaIndex() {
        return areaIndex;

    }
    public void setUserData(Vector<String> userData) {
        this.userData = userData;
    }
    public void setInsuranceIndexes(Vector<Integer> insuranceIndexes) {
        this.insuranceIndexes = insuranceIndexes;
    }

    public Vector<Integer> getInsuranceIndexes() {
        return insuranceIndexes;}
    public Vector<Integer> getProblemIndex() {
        return problemIndex;
    } public Vector<String> getUserData() {
        return userData;}
}
