/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Component.LoginComponent;
import Controller.LoginController;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author 84969
 */
public class main extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * @param args the command line arguments
     */
    public main() {
        this.setResizable(false);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        this.setContentPane(contentPane);

        LoginComponent loginPane = new LoginComponent();
        contentPane.add(loginPane, BorderLayout.CENTER);

	LoginController loginController = new LoginController(loginPane);
	loginController.setEvent(this, contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Phần mềm quản lý điểm sinh viên".toUpperCase());
        setSize(600, 550);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //------------add content, layout----------------//
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                main ex = new main();

            }
        });
    }

}
