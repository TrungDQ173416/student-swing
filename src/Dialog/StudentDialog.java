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
import Controller.StudentController;
import Data.ScoreData;
import Data.StudentData;
import Model.Student;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class StudentDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private Connection connection;
    private JPanel contentPane;
    private JButton button;
    private JLabel mainBanner;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField mssvField;
    private JTextField ageField;
    private String sexField;
    private JTextField addressField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;

    private StudentData studentData;
    private StudentController studentController;

    private Student student;
    private boolean isEdit;

    public StudentDialog(StudentController studentController, boolean isEdit) {
//		setIconImage(Sever.icon.getImage());
        this.studentData = studentController.getStudentData();
        this.studentController = studentController;
        this.isEdit = isEdit;

        initUI();
        setEvent();

        if (isEdit) {

            student = studentController.getStudentSelected();

            button.setText("C???p nh???t");
            mainBanner.setText("S???A TH??NG TIN SINH VI??N");
            setTitle("S???a Th??ng Tin Sinh Vi??n".toUpperCase());

            String name = student.getName();
            String email = student.getEmail();
            String phone = student.getPhone();
            String mssv = student.getMssv();
            int age = student.getAge();
            String sex = student.getSex();
            String address = student.getAddress();

            nameField.setText(name);
            emailField.setText(email);
            phoneField.setText(phone);
            mssvField.setText(mssv);
            ageField.setText(String.valueOf(age));
            addressField.setText(address);
            if (sex.equals("Name")) {
                maleButton.setSelected(true);
            } else {
                femaleButton.setSelected(true);
            }

        } else {
            setTitle("Th??m sinh vi??n".toUpperCase());
        }
    }

    private void setEvent() {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String mssv = mssvField.getText();
                String address = addressField.getText();
                String sex = "";

                NameValidator validator = new NameValidator();
                NumberValidator numberValidator = new NumberValidator();
                EmailValidator emailValidator = new EmailValidator();
                PhoneValidator phoneValidator = new PhoneValidator();

                if (maleButton.isSelected()) {
                    sex = "Nam";
                } else {
                    sex = "N???";
                }

                String ageString = ageField.getText();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "T??n sinh vi??n kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                }  else if (mssv.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "M?? s??? sinh vi??n kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (!numberValidator.validate(mssv)) {
                    JOptionPane.showMessageDialog(null, "M?? s??? sinh vi??n kh??ng ????ng ?????nh d???ng! Vui l??ng nh???p 6 s???",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (!maleButton.isSelected() && !femaleButton.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Gi???i kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (ageString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tu???i kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (!numberValidator.validate(ageString)) {
                    JOptionPane.showMessageDialog(null, "Tu???i kh??ng ????ng ?????nh d???ng! Vui l??ng nh???p s???",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (email.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (!emailValidator.validate(email)) {
                        JOptionPane.showMessageDialog(null, "Email kh??ng ????ng ?????nh!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "S??? ??i???n tho???i kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (!phoneValidator.validate(phone)) {
                    JOptionPane.showMessageDialog(null, "S??? ??i???n tho???i kh??ng ????ng ?????nh d???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else if (address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "?????a ch??? kh??ng ???????c tr???ng!",
                            "L???i", JOptionPane.WARNING_MESSAGE);
                } else {
                    int age = Integer.parseInt(ageString);
                    if (isEdit) {
                        studentData.editStudent(student, name, email, phone, mssv, age, sex, address);
                    } else {
                        studentData.addStudent(name, email, phone, mssv, age, sex, address);
                    }
                    studentController.updateMainPane();
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

        mainBanner = new JLabel("TH??M SINH VI??N");
        mainBanner.setFont(new Font("Calibri", Font.PLAIN, 35));
        mainBanner.setBounds(30, 28, 524, 44);
        mainBanner.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(mainBanner);

        JLabel nameLabel = new JLabel("H??? v?? t??n:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nameLabel.setBounds(75, 85, 108, 19);
        getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Calibri", Font.BOLD, 20));
        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        nameField.setBounds(75, 115, 466, 35);
        contentPane.add(nameField);

        JLabel mssvLabel = new JLabel("M?? s??? sinh vi??n:");
        mssvLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        mssvLabel.setBounds(75, 178, 150, 19);
        contentPane.add(mssvLabel);

        mssvField = new JTextField();
        mssvField.setFont(new Font("Calibri", Font.BOLD, 20));
        mssvField.setBorder(new EmptyBorder(0, 15, 0, 0));
        mssvField.setBounds(75, 210, 466, 35);
        contentPane.add(mssvField);

        JLabel sexLabel = new JLabel("Gi???i t??nh:");
        sexLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        sexLabel.setBounds(75, 263, 108, 19);
        contentPane.add(sexLabel);

        ButtonGroup sexGroup = new ButtonGroup();
        maleButton = new JRadioButton("Nam");
        femaleButton = new JRadioButton("N???");
        sexGroup.add(maleButton);
        sexGroup.add(femaleButton);
        maleButton.setBounds(170, 263, 80, 19);
        femaleButton.setBounds(270, 263, 100, 19);
        contentPane.add(maleButton);
        contentPane.add(femaleButton);

        JLabel ageLabel = new JLabel("Tu???i: ");
        ageLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        ageLabel.setBounds(75, 300, 108, 19);
        contentPane.add(ageLabel);

        ageField = new JTextField();
        ageField.setFont(new Font("Calibri", Font.BOLD, 20));
        ageField.setBorder(new EmptyBorder(0, 15, 0, 0));
        ageField.setBounds(175, 300, 100, 35);
        contentPane.add(ageField);

        JLabel addressLabel = new JLabel("?????a ch???: ");
        addressLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        addressLabel.setBounds(75, 340, 108, 35);
        contentPane.add(addressLabel);

        addressField = new JTextField();
        addressField.setFont(new Font("Calibri", Font.BOLD, 20));
        addressField.setBorder(new EmptyBorder(0, 15, 0, 0));
        addressField.setBounds(75, 380, 466, 35);
        contentPane.add(addressField);

        JLabel emailLabel = new JLabel("Email: ");
        emailLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        emailLabel.setBounds(75, 420, 108, 35);
        contentPane.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Calibri", Font.BOLD, 20));
        emailField.setBorder(new EmptyBorder(0, 15, 0, 0));
        emailField.setBounds(75, 460, 466, 35);
        contentPane.add(emailField);

        JLabel phoneLabel = new JLabel("S??? ??i???n tho???i:");
        phoneLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        phoneLabel.setBounds(75, 500, 150, 19);
        contentPane.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setFont(new Font("Calibri", Font.BOLD, 20));
        phoneField.setBorder(new EmptyBorder(0, 15, 0, 0));
        phoneField.setBounds(75, 540, 466, 35);
        contentPane.add(phoneField);

        button = new JButton("Th??m sinh vi??n");
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(75, 636, 466, 49);
        getContentPane().add(button);

    }

    public class NameValidator {

        private Pattern pattern;
        private static final String USERNAME_PATTERN = "^[a-z0-9._-]{3,15}$";

        public NameValidator() {
            pattern = Pattern.compile(USERNAME_PATTERN);
        }

        public boolean validate(final String username) {
            return pattern.matcher(username).matches();
        }
    }

    public class NumberValidator {

        private Pattern pattern;
        private static final String USERNAME_PATTERN = "^(-?\\d+\\.\\d+)$|^(-?\\d+)$";

        public NumberValidator() {
            pattern = Pattern.compile(USERNAME_PATTERN);
        }

        public boolean validate(final String username) {
            return pattern.matcher(username).matches();
        }
    }

    public class EmailValidator {

        private Pattern pattern;
        private static final String USERNAME_PATTERN = "^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$";

        public EmailValidator() {
            pattern = Pattern.compile(USERNAME_PATTERN);
        }

        public boolean validate(final String username) {
            return pattern.matcher(username).matches();
        }
    }
    
       public class PhoneValidator {

        private Pattern pattern;
        private static final String USERNAME_PATTERN = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

        public PhoneValidator() {
            pattern = Pattern.compile(USERNAME_PATTERN);
        }

        public boolean validate(final String username) {
            return pattern.matcher(username).matches();
        }
    }
}
