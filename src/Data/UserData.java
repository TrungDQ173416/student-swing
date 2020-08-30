/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import ConnectService.sqlConnect;
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
public class UserData {

    private ArrayList<User> listUsers;
    private Connection connection;

    public UserData() {
        try {
            this.connection = sqlConnect.getConnection();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserData.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listUsers = new ArrayList<>();
    }

    public ArrayList<User> getListUsers() {
        return this.listUsers;
    }

    public void getData() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM dbo.[User]");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");
                User user = new User(id, username, password, role);
                listUsers.add(user);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editPassword(User user, String password) {
        try {

            PreparedStatement preStatement = connection.prepareStatement("UPDATE dbo.[User] "
                    + "SET password = ?  WHERE username = ?");
            password = sqlConnect.createMD5Password(password);
            preStatement.setString(1, password);
            preStatement.setString(2, user.getUserName());

            preStatement.executeUpdate();
            preStatement.close();

            user.setPassWord(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editUser(User user, String username, String password, String role) {
        int isEdit = 1;
        for (int i = 0; i < listUsers.size(); i++) {
            if (username.equals(listUsers.get(i).getUserName())) {
                isEdit++;
            }
        }
        if (isEdit == 1) {
            try {
                PreparedStatement preStatement = connection.prepareStatement("UPDATE dbo.[user] "
                        + "SET username = ?, password = ?, role = ? WHERE id = ?");
                password = sqlConnect.createMD5Password(password);
                preStatement.setString(1, username);
                preStatement.setString(2, password);
                preStatement.setString(3, role);
                preStatement.setInt(4, user.getId());

                preStatement.executeUpdate();
                preStatement.close();

                user.setUserName(username);
                user.setPassWord(password);
                user.setRole(role);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tên tài khoản đã tồn tại!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
        isEdit = 1;

    }

    public void addUser(String username, String password, String role) {
        int isAdd = 1;
        for (int i = 0; i < listUsers.size(); i++) {
            if (username.equals(listUsers.get(i).getUserName())) {
                isAdd = 0;
            }
        }
        if (isAdd == 1) {
            try {

                PreparedStatement preStatement = connection.prepareStatement("INSERT INTO dbo.[User] ( username, password, role ) VALUES  (" + "?,?,?)");
                password = sqlConnect.createMD5Password(password);
                preStatement.setString(1, username);
                preStatement.setString(2, password);
                preStatement.setString(3, role);

                preStatement.executeUpdate();
                preStatement.close();

                User user = new User(username, password, role);
                listUsers.add(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Tên tài khoản đã tồn tại!",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void deleteUser(User user) {

        try {

            PreparedStatement preStatement = connection.prepareStatement("DELETE FROM dbo.[User] WHERE id = ?");
            preStatement.setInt(1, user.getId());
            preStatement.executeUpdate();
            preStatement.close();
            listUsers.remove(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
