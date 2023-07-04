package sis_application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class SceneFXMLController implements Initializable {
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnInsert;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<Student, String> colEmail;

    @FXML
    private TableColumn<Student, Integer> colId;

    @FXML
    private TableColumn<Student, String> colName;

    @FXML
    private TableColumn<Student, String> colPhone;

    @FXML
    private TableView<Student> tbStudent;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private Label lblStatus;

    private StudentDAO studentDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentDAO = new StudentDAO();
        showStudent();
    }

    @FXML
    void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnInsert) {
            insertRecord();
        } else if (event.getSource() == btnUpdate) {
            updateRecord();
        } else if (event.getSource() == btnDelete) {
            deleteRecord();
        }
    }

    private void insertRecord() {
        int id = Integer.parseInt(tfId.getText());
        String name = tfName.getText();
        String email = tfEmail.getText();
        String phone = tfPhone.getText();
        Student student = new Student(id, name, email, phone);
        boolean success = studentDAO.insertStudent(student);
        if (success) {
            showStudent();
            lblStatus.setText("Data Inserted Successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Insert Error", "Failed to insert data");
        }
    }

    private void updateRecord() {
        int id = Integer.parseInt(tfId.getText());
        String name = tfName.getText();
        String email = tfEmail.getText();
        String phone = tfPhone.getText();
        Student student = new Student(id, name, email, phone);
        boolean success = studentDAO.updateStudent(student);
        if (success) {
            showStudent();
            lblStatus.setText("Data Updated Successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Update Error", "No record found with ID: " + id);
        }
    }

    private void deleteRecord() {
        int id = Integer.parseInt(tfId.getText());
        boolean success = studentDAO.deleteStudent(id);
        if (success) {
            showStudent();
            lblStatus.setText("Data Deleted Successfully");
        } else {
            showAlert(Alert.AlertType.ERROR, "Delete Error", "No record found with ID: " + id);
        }
    }

    private void showStudent() {
        ObservableList<Student> list = FXCollections.observableArrayList(studentDAO.getAllStudents());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        tbStudent.setItems(list);
    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        Student student = tbStudent.getSelectionModel().getSelectedItem();
        if (student != null) {
            tfId.setText(String.valueOf(student.getId()));
            tfName.setText(student.getName());
            tfEmail.setText(student.getEmail());
            tfPhone.setText(student.getPhone());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
