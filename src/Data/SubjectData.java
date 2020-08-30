/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import ConnectService.sqlConnect;
import Model.Subject;
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
public class SubjectData {

    private ArrayList<Subject> listSubjects;
    private Connection connection;

    public SubjectData() {
        try {
            this.connection = sqlConnect.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubjectData.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listSubjects = new ArrayList<>();
    }

    public ArrayList<Subject> getListSubjects() {
        return this.listSubjects;
    }

    public void getData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Subjects");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int tin = resultSet.getInt("tin");
                Float heso = resultSet.getFloat("heso");
                Subject subject = new Subject(id,name,tin,heso);
                listSubjects.add(subject);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editSubject(Subject subject,String name, int tin, float heso) {
             try {

            PreparedStatement preStatement = connection.prepareStatement("UPDATE Subjects "
                    + "SET name = ?, tin = ?, heso = ? WHERE id = ?");
            preStatement.setString(1, name);
            preStatement.setInt(2, tin);
            preStatement.setFloat(3, heso);
            preStatement.setInt(4, subject.getId());

            preStatement.executeUpdate();
            preStatement.close();
            
            subject.setName(name);
            subject.setHeso(heso);
            subject.setTin(tin);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSubject(String name, int tin, float heso) {
            try {
                PreparedStatement preStatement = connection.prepareStatement("INSERT INTO Subjects ( name, tin, heso ) VALUES  (" + "?,?,?)");
                preStatement.setString(1, name);
                preStatement.setInt(2, tin);
                preStatement.setFloat(3, heso);

                preStatement.executeUpdate();
                preStatement.close();

                Subject subject = new Subject(name, tin, heso);
                listSubjects.add(subject);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
//
    public void deleteSubject(Subject subject) {

        try {

            PreparedStatement preStatement = connection.prepareStatement("DELETE FROM dbo.Student_Subject_Relations WHERE subject_id = ? DELETE dbo.Subjects WHERE id = ?");
            preStatement.setInt(1, subject.getId());
            preStatement.setInt(2, subject.getId());
            preStatement.executeUpdate();
            preStatement.close();
            listSubjects.remove(subject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
