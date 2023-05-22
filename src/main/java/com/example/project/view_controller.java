package com.example.project;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;




import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class view_controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public Database db = new Database();

    @FXML
    private ImageView imageView, imageView2;
    @FXML
    private ComboBox<String> cboInfo;
    @FXML
    private ToggleButton adminMode;
    @FXML
    private Button btnEditInfo;
    @FXML
    private Button btnEditServices;
    @FXML
    private TableView<Database> adminTable;
    private boolean adminModeEnabled = false;
    private String adminUsername;
    private String adminPassword;
    @FXML
    private Button btnChangePass;

    @FXML
    private Tab tabAdmin;
    @FXML
    private Label labelHospital;

    @FXML
    private Label labelInfo;
    @FXML
    private Label labelLocation, labelHospital1;

    @FXML
    private ListView<String> lvServices;
    @FXML
    private TableView<Database> table;
    @FXML
    private TableColumn<Database, String> columnDate;

    @FXML
    private TableColumn<Database, String> columnInsurance;

    @FXML
    private TableColumn<Database, String> columnName;

    @FXML
    private TableColumn<Database, String> columnProblem;

    @FXML
    private TableColumn<Database, String> columnAge;
    ObservableList<Database> list = FXCollections.observableArrayList();
    private ObservableList<Database> adminList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        tabAdmin.setDisable(true);
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
        columnProblem.setCellValueFactory(new PropertyValueFactory<>("problems"));
        columnInsurance.setCellValueFactory(new PropertyValueFactory<>("insurance"));
        columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnAge.setCellFactory(TextFieldTableCell.forTableColumn());
        columnProblem.setCellFactory(TextFieldTableCell.forTableColumn());
        columnInsurance.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDate.setCellFactory(TextFieldTableCell.forTableColumn());
        table.setEditable(false);
        table.setItems(list);
    }
    @FXML
    void btnChangePass(ActionEvent event) {
        if (!adminModeEnabled) {
            showErrorDialog("Admin mode is not enabled.", "Change Password Failed");
            return;
        }
        TextInputDialog usernameDialog = new TextInputDialog();
        usernameDialog.setHeaderText(null);
        usernameDialog.setContentText("Enter admin username:");
        usernameDialog.getDialogPane().setMinWidth(300);
        usernameDialog.initOwner(btnChangePass.getScene().getWindow());
        imageView.setVisible(false);
        imageView2.setVisible(true);
        Optional<String> usernameResult = usernameDialog.showAndWait();
        usernameResult.ifPresent(username -> {
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setHeaderText(null);
            passwordDialog.setContentText("Enter current admin password:");
            passwordDialog.getDialogPane().setMinWidth(300);
            passwordDialog.initOwner(btnChangePass.getScene().getWindow());

            Optional<String> passwordResult = passwordDialog.showAndWait();
            passwordResult.ifPresent(password -> {
                if (authenticateAdmin(username, password)) {
                    TextInputDialog newPasswordDialog = new TextInputDialog();
                    newPasswordDialog.setHeaderText(null);
                    newPasswordDialog.setContentText("Enter new admin password: Must Contain 1 Uppercase");
                    newPasswordDialog.getDialogPane().setMinWidth(300);
                    newPasswordDialog.initOwner(btnChangePass.getScene().getWindow());
                    Optional<String> newPasswordResult = newPasswordDialog.showAndWait();
                    newPasswordResult.ifPresent(newPassword -> {
                        if (isValidPassword(newPassword)) {
                            // Update the admin password
                            updateAdminPassword(newPassword);
                            showErrorDialog("Admin password changed successfully.", "Change Password");
                        } else {
                            showErrorDialog("Invalid password. Password must meet the requirements.", "Change Password Failed");
                        }
                    });
                } else {
                    showErrorDialog("Invalid admin credentials.", "Change Password Failed");
                }
            });
        });
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z]).*$";
        return password.matches(passwordPattern);
    }

    private void updateAdminPassword(String newPassword) {
        try {
            File adminFile = new File("admin.txt");
            File tempFile = new File("admin_temp.txt");

            Scanner scanner = new Scanner(adminFile);
            String existingUsername = scanner.nextLine();
            scanner.close();

            PrintWriter writer = new PrintWriter(tempFile);
            writer.println(existingUsername);
            writer.println(newPassword);
            writer.close();

            if (adminFile.delete()) {
                if (!tempFile.renameTo(adminFile)) {
                    showErrorDialog("Failed to update admin password.", "Error");
                }
            } else {
                showErrorDialog("Failed to update admin password.", "Error");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showErrorDialog("Failed to update admin password.", "Error");
        }
    }


    @FXML
    private void toggleAdminMode(ActionEvent event) {
        ToggleButton adminToggleButton = (ToggleButton) event.getSource();

        if (adminToggleButton.isSelected()) {
            String username = showInputDialog("Enter admin username:");
            String password = showInputDialog("Enter admin password:");

            if (authenticateAdmin(username, password)) {
                adminModeEnabled = true;
                btnEditInfo.setVisible(true);
                btnEditServices.setVisible(true);
                tabAdmin.setDisable(false);


            } else {
                adminToggleButton.setSelected(false);
                showErrorDialog("Invalid username or password.", "Login Failed");
                tabAdmin.setDisable(true);

            }
        } else {
            adminModeEnabled = false;
            tabAdmin.setDisable(true);

            btnEditInfo.setVisible(false);
            btnEditServices.setVisible(false);

        }
    }

    private String showInputDialog(String prompt) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText(null);
        dialog.setContentText(prompt);
        dialog.getDialogPane().setMinWidth(300);
        dialog.initOwner(adminMode.getScene().getWindow());
        dialog.showAndWait();
        return dialog.getResult();
    }

    private void showErrorDialog(String message, String title) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle(title);
        alert.initOwner(adminMode.getScene().getWindow());
        alert.showAndWait();
    }

    private boolean authenticateAdmin(String username, String password) {
        try {
            File adminFile = new File("admin.txt");
            Scanner scanner = new Scanner(adminFile);
            String storedUsername = scanner.nextLine();
            String storedPassword = scanner.nextLine();
            scanner.close();

            return storedUsername.equals(username) && storedPassword.equals(password);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    @FXML
    void btnEditInfo(ActionEvent event) {
        int selectedIndex = cboInfo.getSelectionModel().getSelectedIndex();
        String hospitalInfo = db.readHospitalInfo(selectedIndex);
        TextInputDialog dialog = new TextInputDialog(hospitalInfo);
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new hospital info:");
        dialog.getDialogPane().setMinWidth(1000);
        dialog.initOwner(btnEditInfo.getScene().getWindow());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newInfo -> {
            String updatedInfo = newInfo.trim();
            db.updateHospitalInfo(selectedIndex, updatedInfo);
            labelInfo.setText(updatedInfo);
        });
    }

    @FXML
    void btnEditServices(ActionEvent event) {
        int selectedIndex = cboInfo.getSelectionModel().getSelectedIndex();
        String readServices = db.readServices(selectedIndex);
        TextInputDialog dialog = new TextInputDialog(readServices);
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new services (separated by commas):");
        dialog.getDialogPane().setMinWidth(1000);
        dialog.initOwner(btnEditServices.getScene().getWindow());

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newServices -> {
            String updatedServices = newServices.trim();
            db.updateServices(selectedIndex, updatedServices);
            lvServices.getItems().clear();
            lvServices.getItems().addAll(updatedServices.split(","));
        });
    }

    public void btnBack(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("main_menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loadToComboBox() {
        db = new Database("HospitalLoc.txt");
        db.loadtocombobox(cboInfo);
        cboInfo.getSelectionModel().selectFirst();
        labels();
    }

    public void labels() {
        cboInfo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            labels();
        });
        int selectedIndex = cboInfo.getSelectionModel().getSelectedIndex();
        db = new Database("HospitalLoc.txt");
        String line = db.printLineByIndex(selectedIndex);
        labelLocation.setText(line);
        db = new Database("Hospitals.txt");
        String line2 = db.printLineByIndex(selectedIndex);
        labelHospital.setText(line2);
        labelHospital1.setText(line2);
        db = new Database("Service.txt");

        String services = db.printLineByIndex(selectedIndex);
        String[] serviceItems = services.split(",");

        lvServices.getItems().clear();
        lvServices.getItems().addAll(serviceItems);
        db = new Database();
        String hospitalInfo = db.readHospitalInfo(selectedIndex);
        labelInfo.setText(hospitalInfo);
        db.setSelectedIndex(selectedIndex);
        String fileName;
        switch (selectedIndex) {
            case 0:
                fileName = "MayoClinic.txt";
                break;
            case 1:
                fileName = "ClevelandClinic.txt";
                break;
            case 2:
                fileName = "MassGeneral.txt";
                break;
            case 3:
                fileName = "JohnsHopkins.txt";
                break;
            case 4:
                fileName = "Universitätsmedizin Berlin - Berlin, Germany.txt";
                break;
            case 5:
                fileName = "TorontoGeneral.txt";
                break;
            case 6:
                fileName = "SingaporeGeneral.txt";
                break;
            case 7:
                fileName = "KarolinskaUniversity.txt";
                break;
            case 8:
                fileName = "KingsCollege.txt";
                break;
            case 9:
                fileName = "BumrungradInternational.txt";
                break;
            case 10:
                fileName = "DavaoDoc.txt";
                break;
            case 11:
                fileName = "SPC.txt";
                break;
            case 12:
                fileName = "Adventist.txt";
                break;
            default:
                fileName = "";
                break;
        }


        if (!fileName.isEmpty()) {
            list.clear();
            db.readDataFromFile(fileName, list);
            table.setItems(list);
        }

    }
}