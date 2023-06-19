package sis_application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SceneFXMLController implements Initializable{
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
    void handleButtonAction(ActionEvent event) {
		System.out.println("Button is clicked");
		Label label = new Label();
		label.setText("Hello Java");
		if(event.getSource() == btnInsert) {
			insertRecord();
		}else if(event.getSource() == btnUpdate) {
			updateRecord();
		}else if(event.getSource() == btnDelete) {
			deleteRecord();
		}
    }
	
    public void initialize(URL url, ResourceBundle rb) {
    	showStudent();
    }
    
    public Connection getConnection() {
    	Connection conn = null;
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver"); 
    		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root","");
    		System.out.println("DB Connected");
        	return conn;
    	}catch(Exception e) {
    		System.out.println("Error: "+e.getMessage());
    		return null;
    	}
    }
    	public ObservableList<Student> getStudentList(){
    		ObservableList<Student> studentList = FXCollections.observableArrayList();
    		Connection conn = getConnection();
    		String query = "SELECT * FROM student";
    		Statement st;
    		ResultSet rs;
    		try {
    			st= conn.createStatement();
    			rs = st.executeQuery(query);
    			Student student;
    			while(rs.next()) {
    				student = new Student(rs.getInt("id"),rs.getString("name"),rs.getString("email"),rs.getString("phone"));
    				studentList.add(student);
    			}
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    		return studentList;
    	}
    	public void showStudent() {
    		ObservableList<Student> list = getStudentList();
    		colId.setCellValueFactory(new PropertyValueFactory<Student,Integer>("id"));
    		colName.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
    		colEmail.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
    		colPhone.setCellValueFactory(new PropertyValueFactory<Student,String>("phone"));
    		
    		tbStudent.setItems(list);
    	}
    
    	private void insertRecord(){
    		String query = "INSERT INTO student VALUES (" + tfId.getText() + ",'" + tfName.getText() + "','" + tfEmail.getText() +"','" + tfPhone.getText() +"')";
    		executeQuery(query);
    		showStudent();
    	}
    	private void updateRecord(){
    		String query = "UPDATE student SET name = '" + tfName.getText() + "', email = '" + tfEmail.getText() +"', phone = '" + tfPhone.getText() +"' WHERE id = " + tfId.getText() + "";
    		executeQuery(query);
    		showStudent();
    	}
    	private void deleteRecord(){
    		String query = "DELETE FROM student WHERE id = " + tfId.getText() + "";
    		executeQuery(query);
    		showStudent();
    	}
    	private void executeQuery(String query) {
    		Connection conn = getConnection();
    		Statement st;
    		try {
    			st = conn.createStatement();
    			st.executeUpdate(query);
    		}catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
        @FXML
        private void handleMouseAction(MouseEvent event) {
        	Student student = tbStudent.getSelectionModel().getSelectedItem();
        	tfId.setText(""+ student.getId());
        	tfName.setText(student.getName());
        	tfEmail.setText(""+ student.getEmail());
        	tfPhone.setText(""+ student.getPhone());

        }
}
	
