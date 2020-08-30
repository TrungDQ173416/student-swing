package Dialog;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import ConnectService.sqlConnect;
import Controller.UserController;
import Data.UserData;
import Model.User;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.swing.JComboBox;

public class UserDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private Connection connection;
    private JPanel contentPane;
    private JButton button;
    private JLabel mainBanner;
    private JTextField userField;
    private JPasswordField passField;
    private JTextField roleField;
    private JComboBox roleBox;
    private String[] roles;
    private String role = "User";

    private UserData userData;
    private UserController userController;

    private User user;
    private boolean isEdit;

    public UserDialog(UserController userController, boolean isEdit) {
//		setIconImage(Sever.icon.getImage());
        this.userData = userController.getUserData();
        this.userController = userController;
        this.isEdit = isEdit;

        initUI();
        setEvent();

        if (isEdit) {

            user = userController.getUserSelected();

            button.setText("Sửa Tài Khoản");
            mainBanner.setText("SỬA TÀI KHOẢN");
            setTitle("Sửa tài khoản".toUpperCase());

            String username = user.getUserName();
            String password = user.getPassWord();
//			String name = user.getName();
            String role = user.getRole();
            
            userField.setText(username);
			passField.setText(password);
			roleBox.setSelectedItem(role);
        } else {
            setTitle("Thêm tài khoản".toUpperCase());
        }
    }

    private void setEvent() {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                String roleSelect = role;
                
                UserNameValidator userNameValidator = new UserNameValidator();
                
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tài khoản không được trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else if(!userNameValidator.validate(username)){
                    JOptionPane.showMessageDialog(null, "Tài khoản không đúng định dạng!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                else if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Mật khẩu không được trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                else {
                        if (isEdit) {
                            userData.editUser(user, username, password, roleSelect);
                        } else {
                            userData.addUser(username, password, roleSelect);
                        }
                        userController.updateMainPane();
                    dispose();
                }
            }
        });
    }

    private void initUI() {
        contentPane = new JPanel();
        setSize(643, 759);
        setLocationRelativeTo(this);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        mainBanner = new JLabel("THÊM TÀI KHOẢN");
        mainBanner.setFont(new Font("Calibri", Font.PLAIN, 35));
        mainBanner.setBounds(30, 28, 524, 44);
        mainBanner.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(mainBanner);

        JLabel userLabel = new JLabel("Tài khoản:");
        userLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        userLabel.setBounds(75, 85, 108, 19);
        getContentPane().add(userLabel);

        userField = new JTextField();
        userField.setFont(new Font("Calibri", Font.BOLD, 20));
        userField.setBorder(new EmptyBorder(0, 15, 0, 0));
        userField.setBounds(75, 104, 466, 50);
        contentPane.add(userField);

        JLabel passLabel = new JLabel("Mật khẩu:");
        passLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        passLabel.setBounds(75, 178, 108, 19);
        contentPane.add(passLabel);

        passField = new JPasswordField();
        passField.setFont(new Font("Calibri", Font.BOLD, 20));
        passField.setBorder(new EmptyBorder(0, 15, 0, 0));
        passField.setBounds(75, 197, 466, 50);
        contentPane.add(passField);

        JLabel nameLabel = new JLabel("Quyền:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nameLabel.setBounds(75, 263, 108, 19);
        contentPane.add(nameLabel);
        
        roles = new String[]{
            "Manager","Admin","User"
        };
        roleBox = new JComboBox<>(roles);
        roleBox.setSelectedItem("User");
        
        roleBox.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    role = e.getItem().toString();
                }
            }
        });
        

        roleBox.setFont(new Font("Calibri", Font.BOLD, 20));
        roleBox.setBorder(new EmptyBorder(0, 15, 0, 0));
        roleBox.setBounds(75, 285, 466, 50);
        contentPane.add(roleBox);

        button = new JButton("Thêm tài khoản");
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(75, 636, 466, 49);
        getContentPane().add(button);
    }
           public class UserNameValidator {

        private Pattern pattern;
        private static final String USERNAME_PATTERN = "^((?=.d)(?=.[a-z]).{3,15})$";

        public UserNameValidator() {
            pattern = Pattern.compile(USERNAME_PATTERN);
        }

        public boolean validate(final String username) {
            return pattern.matcher(username).matches();
        }
    }
}