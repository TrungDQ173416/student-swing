/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Component.LoginComponent;
import Component.MainComponent;
import ConnectService.sqlConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Main.main;
import Model.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import static javax.swing.text.html.CSS.Attribute.COLOR;

/**
 *
 * @author 84969
 */
public class LoginController {

    private final LoginComponent loginPane;
    private Connection con;
    private PreparedStatement preStatement;
    private ResultSet result;

    public LoginController(LoginComponent loginPane) {
        this.loginPane = loginPane;
    }

    public void setEvent(JFrame frame, JPanel contentPane) {

        loginPane.getForgotButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Để lấy lại mật khẩu hãy liên hệ administrator",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        loginPane.getLoginButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String username = loginPane.getUserField().getText();
                String password = new String(loginPane.getPassField().getPassword());
                if (username.equals("")) {
                    loginPane.getUserField().setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));
                    JOptionPane.showMessageDialog(null, "Tên tài khoản không được bỏ trống!!",
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    loginPane.getUserField().setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(114, 137, 218)));
                } else if (password.equals("")) {
                    loginPane.getPassField().setBorder(new MatteBorder(1, 1, 1, 1, Color.RED));
                    JOptionPane.showMessageDialog(null, "Mật khẩu không được bỏ trống!!",
                            "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    loginPane.getPassField().setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(114, 137, 218)));
                } else {
                    try {
                        con = sqlConnect.getConnection();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    String sqlQuery = "SELECT * FROM dbo.[User] WHERE username = ? AND password = ?";
                    try {
                        preStatement = con.prepareStatement(sqlQuery);

                        preStatement.setString(1, username);
			password = sqlConnect.createMD5Password(password);
                        preStatement.setString(2, password);
                        result = preStatement.executeQuery();

                        if (result.next()) {
                            int id = result.getInt("id");
                            String userName = result.getString("username");
                            String passWord = result.getString("password");
                            String role = result.getString("role");
                            User user = new User(id, userName, passWord, role);
                            frame.setSize(1800, 1000);
                            frame.setLocationRelativeTo(null);
//                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                            sqlConnect.user = user;
                            contentPane.removeAll();

                            MainComponent mainPane = new MainComponent();
                            contentPane.add(mainPane, BorderLayout.CENTER);

                            MainController mainController = new MainController(mainPane, frame, contentPane);
                            mainController.setEvent();

                            contentPane.revalidate();
                            contentPane.repaint();

                            result.close();
                            preStatement.close();

                        } else {
                            JOptionPane.showMessageDialog(null, "Tài khoản hoặc mật khẩu chưa đúng!",
                                    "Lỗi", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        loginPane.getUserField().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent key) {
                if (key.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginPane.getLoginButton().doClick();
                }
            }
        });

        loginPane.getPassField().addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent key) {
                if (key.getKeyChar() == KeyEvent.VK_ENTER) {
                    loginPane.getLoginButton().doClick();
                }
            }
        });
    }

    private static class ActionListenerImpl implements ActionListener {

        public ActionListenerImpl() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "Để lấy lại mật khẩu hãy liên hệ administrator",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
