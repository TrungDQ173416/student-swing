/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConnectService;

import Model.User;
import java.awt.Font;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author 84969
 */
public class sqlConnect {
     private static Connection con = null;
     public static User user;
     
    public static final ImageIcon icon = new ImageIcon(sqlConnect.class.getResource("/image/usermain.png"));
     
     public static Connection getConnection() throws ClassNotFoundException
     {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String dbURL = "jdbc:sqlserver://localhost:1433;user=sa;password=123456;database=project1";
			con = DriverManager.getConnection(dbURL);
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Gặp vấn đề khi kết nối dữ liệu!", 
					"Lỗi", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
                return con;
	}
     public static String createMD5Password(String password) {	
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] hashInBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder stringBuilder = new StringBuilder();
			for (byte b : hashInBytes) {
				stringBuilder.append(String.format("%02x", b));
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}    
}
