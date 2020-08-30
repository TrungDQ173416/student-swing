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
import Controller.SubjectController;
import Data.SubjectData;
import Model.Subject;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;
import javax.swing.JComboBox;

public class SubjectDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private Connection connection;
    private JPanel contentPane;
    private JButton button;
    private JLabel mainBanner;
    private JTextField nameField;
    private JTextField tinField;
    private JTextField hesoField;

    private SubjectData subjectData;
    private SubjectController subjectController;

    private Subject subject;
    private boolean isEdit;

    public SubjectDialog(SubjectController subjectController, boolean isEdit) {
//		setIconImage(Sever.icon.getImage());
        this.subjectData = subjectController.getSubjectData();
        this.subjectController = subjectController;
        this.isEdit = isEdit;

        initUI();
        setEvent();

        if (isEdit) {

            subject = subjectController.getSubjectSelected();

            button.setText("Cập nhật");
            mainBanner.setText("SỬA THÔNG TIN MÔN HỌC");
            setTitle("Sửa môn học".toUpperCase());

            String name = subject.getName();
            int tin = subject.getTin();
//			String name = user.getName();
            float heso = subject.getHeso();

            nameField.setText(name);
            tinField.setText(String.valueOf(tin));
            hesoField.setText(String.valueOf(heso));
        } else {
            setTitle("Thêm Môn Học".toUpperCase());
        }
    }

    private void setEvent() {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                String name = nameField.getText();
                String tinText = tinField.getText();
                String hesoText = hesoField.getText();

                NameValidator nameValidator = new NameValidator();
                NumberValidator numberValidator = new NumberValidator();

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tên môn học không được trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else if (!nameValidator.validate(name)) {
                    JOptionPane.showMessageDialog(null, "Tên môn học không đúng định dạng!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else if (tinText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Số tín chỉ không được trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else if (!numberValidator.validate(tinText)) {
                    JOptionPane.showMessageDialog(null, "Số tín chỉ không đúng định dạng! Vui lòng nhập chữ số",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else if (hesoText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Hệ số không được trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else if (Float.valueOf(hesoText) > 1 || Float.valueOf(hesoText) < 0) {
                    JOptionPane.showMessageDialog(null, "Hệ số phải nằm trong khoảng từ 0 -1!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else {
                    int tin = Integer.parseInt(tinText);
                    float heso = Float.valueOf(hesoText);
                    if (isEdit) {
                        subjectData.editSubject(subject, name, tin, heso);
                    } else {
                        subjectData.addSubject(name, tin, heso);
                    }
                    subjectController.updateMainPane();
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

        mainBanner = new JLabel("THÊM MÔN HỌC");
        mainBanner.setFont(new Font("Calibri", Font.PLAIN, 35));
        mainBanner.setBounds(30, 28, 524, 44);
        mainBanner.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(mainBanner);

        JLabel userLabel = new JLabel("Tên môn học:");
        userLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        userLabel.setBounds(75, 85, 108, 19);
        getContentPane().add(userLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Calibri", Font.BOLD, 20));
        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        nameField.setBounds(75, 115, 466, 35);
        contentPane.add(nameField);

        JLabel tinLabel = new JLabel("Số tín chỉ:");
        tinLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        tinLabel.setBounds(75, 178, 108, 19);
        contentPane.add(tinLabel);

        tinField = new JTextField();
        tinField.setFont(new Font("Calibri", Font.BOLD, 20));
        tinField.setBorder(new EmptyBorder(0, 15, 0, 0));
        tinField.setBounds(175, 178, 150, 35);
        contentPane.add(tinField);

        JLabel hesoLabel = new JLabel("Hệ số:");
        hesoLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        hesoLabel.setBounds(75, 263, 108, 19);
        contentPane.add(hesoLabel);

        hesoField = new JTextField();
        hesoField.setFont(new Font("Calibri", Font.BOLD, 20));
        hesoField.setBorder(new EmptyBorder(0, 15, 0, 0));
        hesoField.setBounds(175, 263, 150, 35);
        contentPane.add(hesoField);

        button = new JButton("Thêm");
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
}
