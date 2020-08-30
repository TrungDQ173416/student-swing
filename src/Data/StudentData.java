/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import ConnectService.sqlConnect;
import Model.Student;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 84969
 */
public class StudentData {

    private ArrayList<Student> listStudents;
    private Connection connection;

    public StudentData() {
        try {
            this.connection = sqlConnect.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentData.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listStudents = new ArrayList<>();
    }

    public ArrayList<Student> getListStudents() {
        return this.listStudents;
    }

    public void getData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.Students");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String mssv = resultSet.getString("mssv");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                float cpa = resultSet.getFloat("cpa");
                String hocluc = resultSet.getString("hocluc");

                Student student = new Student(id, name, age, email, phone, mssv, sex, address, cpa, hocluc);
                listStudents.add(student);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListStudentByQuery(String nameQuery, String emailQuery, String mssvQuery, String sexQuery) {
        this.listStudents.clear();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.Students WHERE name LIKE N'%" + nameQuery + "%' AND email LIKE N'%" + emailQuery + "%' AND mssv LIKE '%" + mssvQuery + "%' AND sex LIKE N'%" + sexQuery + "%'");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                String mssv = resultSet.getString("mssv");
                String sex = resultSet.getString("sex");
                String address = resultSet.getString("address");
                float cpa = resultSet.getFloat("cpa");
                String hocluc = resultSet.getString("hocluc");

                Student student = new Student(id, name, age, email, phone, mssv, sex, address, cpa, hocluc);
                listStudents.add(student);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editStudent(Student student, String name, String email, String phone, String mssv, int age, String sex, String address) {
        try {
            PreparedStatement preStatement = connection.prepareStatement("UPDATE Students "
                    + "SET name = ?, mssv = ?, email = ?, phone = ?, age = ?, sex = ?, address = ? WHERE id = ?");
            preStatement.setString(1, name);
            preStatement.setString(2, mssv);
            preStatement.setString(3, email);
            preStatement.setString(4, phone);
            preStatement.setInt(5, age);
            preStatement.setString(6, sex);
            preStatement.setString(7, address);
            preStatement.setInt(8, student.getId());

            preStatement.executeUpdate();
            preStatement.close();

            student.setName(name);
            student.setMssv(mssv);
            student.setAddress(address);
            student.setAge(age);
            student.setPhone(phone);
            student.setSex(sex);
            student.setEmail(email);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
         try {
            PreparedStatement preStatement = connection.prepareStatement("UPDATE db[Users] "
                    + "SET username = ? WHERE username = ?");
            preStatement.setString(1, mssv);
            preStatement.setString(2, mssv);

            preStatement.executeUpdate();
            preStatement.close();

  

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addStudent(String name, String email, String phone, String mssv, int age, String sex, String address) {
        int isAdd = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.Students where mssv = " + mssv);
            if (resultSet.next()) {
                isAdd = 1;
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isAdd == 1) {
            JOptionPane.showMessageDialog(null, "Mã số sinh viên đã tồn tại!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                float cpa = 0;
                String hocluc = "";
                PreparedStatement preStatement = connection.prepareStatement("INSERT INTO Students ( name, age, email, phone, mssv,sex, address,cpa,hocluc ) VALUES  (" + "?,?,?,?,?,?,?,?,?)");
                preStatement.setString(1, name);
                preStatement.setInt(2, age);
                preStatement.setString(3, email);
                preStatement.setString(4, phone);
                preStatement.setString(5, mssv);
                preStatement.setString(6, sex);
                preStatement.setString(7, address);
                preStatement.setFloat(8, cpa);
                preStatement.setString(9, hocluc);
                preStatement.executeUpdate();
                preStatement.close();
                Student student = new Student(name, age, email, phone, mssv, sex, address, cpa, hocluc);
                listStudents.add(student);
            } catch (Exception e) {
                e.printStackTrace();
            }   
              try {

                PreparedStatement preStatement = connection.prepareStatement("INSERT INTO dbo.[User] ( username, password, role ) VALUES  (" + "?,?,?)");
                String password = sqlConnect.createMD5Password(mssv);
                preStatement.setString(1, mssv);
                preStatement.setString(2, password);
                preStatement.setString(3, "User");
                preStatement.executeUpdate();
                preStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteStudent(Student student) {

        try {

            PreparedStatement preStatement = connection.prepareStatement("DELETE dbo.Student_Subject_Relations WHERE student_id = ? DELETE dbo.Students WHERE id = ? DELETE FROM dbo.[User] WHERE username = ?");
            preStatement.setInt(1, student.getId());
            preStatement.setInt(2, student.getId());
            preStatement.setString(3, student.getMssv());
            preStatement.executeUpdate();
            preStatement.close();
            listStudents.remove(student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
