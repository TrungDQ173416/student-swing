package Dialog;

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
import Controller.ScoreController;
import Data.ScoreData;
import Model.Score;
import Model.Student;
import Model.Subject;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

public class ScoreDialog extends JDialog {

    private static final long serialVersionUID = 1L;
    private Connection connection;
    private JPanel contentPane;
    private JButton button;
    private JLabel mainBanner;
    private JComboBox subjectComboBox;
    private JTextField midScoreText;
    private JTextField lastScoreText;
    private JComboBox hockiText;
    private JTextField gradesText;
    private JLabel hockiLabel;

    private ScoreData scoreData;
    private ScoreController scoreController;

    private Score score;
    private Subject subjectSelected;
    private String hocKiSelected;
    private Student studentSelected;
    private ArrayList<Subject> listSubjects;
    private boolean isEdit;

    public ScoreDialog(ScoreController scoreController, boolean isEdit) {
//		setIconImage(Sever.icon.getImage());
        this.scoreData = scoreController.getScoreData();
        this.scoreController = scoreController;
        this.isEdit = isEdit;
        scoreData.getDataListSubject();
        this.listSubjects = scoreData.getListSubjects();
        this.studentSelected = scoreController.getStudentSelected();
        initUI();
        setEvent();

        if (isEdit) {
            score = scoreController.getScoreSelected();
            button.setText("Cập nhật");
            mainBanner.setText("SỬA ĐIỂM SINH VIÊN");
            contentPane.remove(hockiText);
            contentPane.remove(hockiLabel);
            String name = score.getName();
            float mid_score = score.getMidScore();
            float last_score = score.getLastScore();
            subjectComboBox.addItem(name);
            subjectComboBox.disable();
            midScoreText.setText(String.valueOf(mid_score));
            lastScoreText.setText(String.valueOf(last_score));
        } else {
            setTitle("Thêm sinh viên".toUpperCase());
        }
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void setEvent() {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                float mid_score = Float.valueOf(midScoreText.getText());
                float last_score = Float.valueOf(lastScoreText.getText());
                if(midScoreText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Điểm giữa kì không được bỏ trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                if(mid_score > 10 || mid_score < 0){
                    JOptionPane.showMessageDialog(null, "Điểm giữa kì phải nằm trong khoảng 0-10!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                      if(lastScoreText.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Điểm cuối kì không được bỏ trống!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                if(last_score > 10 || last_score < 0){
                    JOptionPane.showMessageDialog(null, "Điểm giữa kì phải nằm trong khoảng 0-10!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                }
                if (isEdit) {
                    scoreData.editScore(mid_score, last_score);
                } else {
                    if(hocKiSelected == null){
                         JOptionPane.showMessageDialog(null, "Vui lòng chọn học kì!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                          scoreData.createScore(studentSelected.getId(), mid_score, last_score, hocKiSelected);
                    }
                }
                scoreController.updateScorePane();
                dispose();

            }
        });
    }

    private void initUI() {
        contentPane = new JPanel();
        setSize(650, 500);
        setTitle("Điểm sinh viên".toUpperCase());
        setLocationRelativeTo(this);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        mainBanner = new JLabel("Nhập điểm cho sinh viên");
        mainBanner.setFont(new Font("Calibri", Font.PLAIN, 35));
        mainBanner.setBounds(30, 28, 524, 44);
        mainBanner.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(mainBanner);

        JLabel nameLabel = new JLabel("Tên học phần:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nameLabel.setBounds(75, 85, 108, 19);
        getContentPane().add(nameLabel);

        subjectComboBox = new JComboBox();
        subjectComboBox.addItem(new Item(null, "Chọn học phần"));
        for (Subject subject : listSubjects) {
            subjectComboBox.addItem(new Item(subject, subject.getName()));
        }
        subjectComboBox.setPrototypeDisplayValue(" Danh sách rỗng ");
        subjectComboBox.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            Item item = (Item) c.getSelectedItem();
            subjectSelected = item.subject;
            scoreData.setSubjectSelected(subjectSelected);
        });
        subjectComboBox.setFont(new Font("Calibri", Font.BOLD, 22));
        subjectComboBox.setBounds(75, 115, 466, 35);
        subjectComboBox.setBackground(Color.white);
        contentPane.add(subjectComboBox);

        JLabel Label = new JLabel("Điểm giữa kì:");
        Label.setFont(new Font("Tahoma", Font.BOLD, 15));
        Label.setBounds(75, 178, 150, 19);
        contentPane.add(Label);

        midScoreText = new JTextField();
        midScoreText.setFont(new Font("Calibri", Font.BOLD, 20));
        midScoreText.setBorder(new EmptyBorder(0, 15, 0, 0));
        midScoreText.setBounds(240, 178, 100, 35);
        contentPane.add(midScoreText);

        JLabel lastScoreLabel = new JLabel("Điểm cuối kì:");
        lastScoreLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        lastScoreLabel.setBounds(75, 228, 150, 19);
        contentPane.add(lastScoreLabel);

        lastScoreText = new JTextField();
        lastScoreText.setFont(new Font("Calibri", Font.BOLD, 20));
        lastScoreText.setBorder(new EmptyBorder(0, 15, 0, 0));
        lastScoreText.setBounds(240, 228, 100, 35);
        contentPane.add(lastScoreText);

        hockiLabel = new JLabel("Học kì:");
        hockiLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        hockiLabel.setBounds(75, 278, 150, 19);
        contentPane.add(hockiLabel);

        String[] listHocKi = {"20171", "20172", "20173", "20181", "20182", "20183", "20191", "20192", "20193", "20201", "20202", "20203", "20211", "20212", "20213", "20221", "20222", "20223", "20231", "20232", "20233",};

        hockiText = new JComboBox<>(listHocKi);
        hockiText.setFont(new Font("Calibri", Font.BOLD, 20));
        hockiText.setBorder(new EmptyBorder(0, 15, 0, 0));
        hockiText.setBounds(240, 278, 100, 35);
        contentPane.add(hockiText);

        hockiText.addActionListener(e -> {
            JComboBox c = (JComboBox) e.getSource();
            hocKiSelected = String.valueOf(c.getSelectedItem());
        });

        button = new JButton("Nhập điểm");
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        button.setBounds(75, 350, 466, 49);
        getContentPane().add(button);

    }

    class Item {

        private Subject subject;
        private String description;

        public Item(Subject subject, String description) {
            this.subject = subject;
            this.description = description;
        }

        public Subject getSubject() {
            return subject;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }
}
