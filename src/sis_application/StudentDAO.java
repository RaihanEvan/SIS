package sis_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StudentDAO {  //Handles DB Operations, CRUD Operations, SQL query executions
	
	//JDBC CONNECTION
    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/students", "root", "");
            System.out.println("DB Connected");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    //READ OPERATION
    public List<Student> getAllStudents() {
    	ObservableList<Student> studentList = FXCollections.observableArrayList();
//        List<Student> studentList = new ArrayList<>();
        try{
        	Connection conn = getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM student");
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                Student student = new Student(id, name, email, phone);
                
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    //CREATE OPERATION
    public boolean insertStudent(Student student) {
        String query = "INSERT INTO student (id, name, email, phone) VALUES (?, ?, ?, ?)";
        try{
        	Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, student.getId());
            stmt.setString(2, student.getName());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPhone());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //UPDATE OPERATION
    public boolean updateStudent(Student student) {
        String query = "UPDATE student SET name = ?, email = ?, phone = ? WHERE id = ?";
        try{
        	Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setInt(4, student.getId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //DELETE OPERATION
    public boolean deleteStudent(int id) {
        String query = "DELETE FROM student WHERE id = ?";
        try{
        	Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    //Duplicate student check
    public boolean checkStudentExists(int studentId) {
        try{
        	Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM student WHERE id = ?");
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
