/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component;

import ConnectService.sqlConnect;
import Controller.ScoreController;
import Data.ScoreData;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import subComponent.MenuItemPane;

/**
 *
 * @author 84969
 */
public class ScorePanel extends MenuItemPane {

    private JButton searchButton;
    private JButton resetButton;
    private JButton addButton;
    private JPanel scorePane;
    private JPanel searchPanel;
    private JPanel listStudentPanel;
    private JPanel listScoreStudentPanel;
    private JPanel totalScorePanel;
    private JPanel gpaPanel;
    private JPanel cpaPanel;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField mssvField;
    private ButtonGroup sexGroup;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;

    private JLabel nameText;
    private JLabel ageText;
    private JLabel emailText;
    private JLabel addressText;
    private JLabel phoneText;
    private JLabel mssvText;
    private JLabel sexText;
    private JLabel cpaText;
    private JLabel hocLucText;

    public JTextField getNameField() {
        return this.nameField;
    }

    public JTextField getEmailField() {
        return this.emailField;
    }

    public JTextField getMssvField() {
        return this.mssvField;
    }

    public ButtonGroup getSexGroup() {
        return this.sexGroup;
    }

    public JRadioButton getFemaleButton() {
        return this.femaleButton;
    }

    public JRadioButton getMaleButton() {
        return this.maleButton;
    }

    public JPanel scorePane() {
        return this.scorePane;
    }

    public JButton getResetButton() {
        return this.resetButton;
    }

    public JButton getSearchButton() {
        return this.searchButton;
    }

    public JPanel getListStudentPanel() {
        return this.listStudentPanel;
    }

    public JPanel getListScoreStudentPanel() {
        return this.listScoreStudentPanel;
    }

    public JLabel getNameText() {
        return this.nameText;
    }

    public JLabel getAgeText() {
        return this.ageText;
    }

    public JLabel getEmailText() {
        return this.emailText;
    }

    public JLabel getPhoneText() {
        return this.phoneText;
    }

    public JLabel getMssvText() {
        return this.mssvText;
    }

    public JLabel getSexText() {
        return this.sexText;
    }

    public JLabel getAddressText() {
        return this.addressText;
    }
    
      public JPanel getGpaPanel() {
        return this.gpaPanel;
    }
    
     public JLabel getCpaText() {
        return this.cpaText;
    }
     
        public JLabel getHocLucText() {
        return this.hocLucText;
    }


    public JButton getAddButton() {
        return this.addButton;
    }

    public ScorePanel() {
        this.setLayout(new GridLayout(1, 2, 5, 5));

        initSearchUI();
        initScoreStudentUI();
        add(searchPanel);
        add(scorePane);
    }

    public void initSearchUI() {

        // Tìm kiếm sinh viên
        searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
//        searchPane.setBackground(Color.WHITE);

        JPanel searchField = new JPanel();
        searchField.setLayout(null);
        Dimension dimension = new Dimension(250, 250);
        searchField.setPreferredSize(dimension);
        searchField.setBorder(customBorder("Tìm kiếm"));
        searchField.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Họ Và Tên:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nameLabel.setBounds(10, 25, 150, 20);
        searchField.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Calibri", Font.BOLD, 15));
//        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        nameField.setBounds(10, 55, 250, 30);
        searchField.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        emailLabel.setBounds(400, 25, 150, 20);
        searchField.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Calibri", Font.BOLD, 15));
//        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        emailField.setBounds(400, 55, 250, 30);
        searchField.add(emailField);

        JLabel mssvLabel = new JLabel("Mã số sinh viên:");
        mssvLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        mssvLabel.setBounds(10, 100, 150, 20);
        searchField.add(mssvLabel);

        mssvField = new JTextField();
        mssvField.setFont(new Font("Calibri", Font.BOLD, 15));
//        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        mssvField.setBounds(10, 130, 250, 30);
        searchField.add(mssvField);

        JLabel sexLabel = new JLabel("Giới tính:");
        sexLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        sexLabel.setBounds(400, 100, 108, 19);
        searchField.add(sexLabel);

        sexGroup = new ButtonGroup();
        maleButton = new JRadioButton("Nam");
        femaleButton = new JRadioButton("Nữ");
        sexGroup.add(maleButton);
        sexGroup.add(femaleButton);
        maleButton.setBounds(400, 130, 80, 30);
        femaleButton.setBounds(500, 130, 100, 30);
        searchField.add(maleButton);
        searchField.add(femaleButton);

        searchPanel.add(searchField, BorderLayout.NORTH);

        JPanel menuButton = new JPanel();
        final ImageIcon addIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/add.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        final ImageIcon searchIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/search.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        final ImageIcon deleteIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/delete.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

        resetButton = new JButton("Làm mới");
        resetButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(70, 166, 255));
        resetButton.setBounds(200, 200, 150, 30);
        searchField.add(resetButton);

        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                emailField.setText("");
                mssvField.setText("");
                maleButton.setSelected(false);
                femaleButton.setSelected(false);
            }
        });

        searchButton = new JButton("Tìm kiếm", searchIcon);
        searchButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(70, 166, 255));
        searchButton.setBounds(400, 200, 150, 30);
        searchField.add(searchButton);

        // Danh sách sinh viên
        listStudentPanel = new JPanel();
        listStudentPanel.setLayout(new BorderLayout());
        listStudentPanel.setBackground(Color.WHITE);
        listStudentPanel.setBorder(customBorder("Danh Sách Sinh Viên"));
        searchPanel.add(listStudentPanel, BorderLayout.CENTER);
    }

    public void initScoreStudentUI() {
        scorePane = new JPanel();
        scorePane.setLayout(new BorderLayout());
        scorePane.setBackground(Color.WHITE);

        // Thông tin chi tiết sinh viên
        JPanel studentDetail = new JPanel();
        studentDetail.setLayout(null);
        Dimension dimensionStudent = new Dimension(250, 250);
        studentDetail.setPreferredSize(dimensionStudent);
        studentDetail.setBorder(customBorder("Thông tin sinh viên"));
        studentDetail.setBackground(Color.WHITE);
        scorePane.add(studentDetail, BorderLayout.NORTH);

        JLabel nameTextLabel = new JLabel("Họ và tên:");
        nameTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        nameTextLabel.setBounds(15, 30, 150, 30);
        studentDetail.add(nameTextLabel);

        nameText = new JLabel("");
        nameText.setFont(new Font("Tahoma", Font.BOLD, 18));
        nameText.setBounds(150, 30, 250, 30);
        studentDetail.add(nameText);

        JLabel ageTextLabel = new JLabel("Tuổi:");
        ageTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        ageTextLabel.setBounds(420, 30, 50, 30);
        studentDetail.add(ageTextLabel);

        ageText = new JLabel("");
        ageText.setFont(new Font("Tahoma", Font.BOLD, 18));
        ageText.setBounds(480, 30, 50, 30);
        studentDetail.add(ageText);

        JLabel mssvTextLabel = new JLabel("Mã sinh viên:");
        mssvTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        mssvTextLabel.setBounds(15, 70, 150, 30);
        studentDetail.add(mssvTextLabel);

        mssvText = new JLabel("");
        mssvText.setFont(new Font("Tahoma", Font.BOLD, 18));
        mssvText.setBounds(150, 70, 250, 30);
        studentDetail.add(mssvText);

        JLabel emailTextLabel = new JLabel("Email:");
        emailTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        emailTextLabel.setBounds(400, 70, 80, 30);
        studentDetail.add(emailTextLabel);

        emailText = new JLabel("");
        emailText.setFont(new Font("Tahoma", Font.BOLD, 18));
        emailText.setBounds(490, 70, 250, 30);
        studentDetail.add(emailText);

        JLabel phoneTextLabel = new JLabel("Số điện thoại:");
        phoneTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        phoneTextLabel.setBounds(15, 110, 150, 30);
        studentDetail.add(phoneTextLabel);

        phoneText = new JLabel("");
        phoneText.setFont(new Font("Tahoma", Font.BOLD, 18));
        phoneText.setBounds(150, 110, 250, 30);
        studentDetail.add(phoneText);

        JLabel sexTextLabel = new JLabel("Giới tính:");
        sexTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        sexTextLabel.setBounds(400, 110, 150, 30);
        studentDetail.add(sexTextLabel);

        sexText = new JLabel("");
        sexText.setFont(new Font("Tahoma", Font.BOLD, 18));
        sexText.setBounds(490, 110, 100, 30);
        studentDetail.add(sexText);

        JLabel addressTextLabel = new JLabel("Địa chỉ:");
        addressTextLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        addressTextLabel.setBounds(15, 150, 100, 30);
        studentDetail.add(addressTextLabel);

        addressText = new JLabel("");
        addressText.setFont(new Font("Tahoma", Font.BOLD, 18));
        addressText.setBounds(120, 150, 500, 30);
        studentDetail.add(addressText);

        addButton = new JButton("Nhập điểm");
        addButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(70, 166, 255));
        addButton.setBounds(570, 210, 130, 30);
        if (sqlConnect.user.getRole().equals("Manager") || sqlConnect.user.getRole().equals("Admin")) {
            studentDetail.add(addButton);
        }

        listScoreStudentPanel = new JPanel();
        listScoreStudentPanel.setLayout(new BorderLayout());
        listScoreStudentPanel.setBackground(Color.WHITE);
        listScoreStudentPanel.setBorder(customBorder("Danh Sách Điểm Sinh Viên"));
        scorePane.add(listScoreStudentPanel, BorderLayout.CENTER);

        totalScorePanel = new JPanel();
        totalScorePanel.setLayout(new BorderLayout());
        totalScorePanel.setBackground(Color.WHITE);
        Dimension dimensionScore = new Dimension(180, 180);
        totalScorePanel.setPreferredSize(dimensionScore);
        totalScorePanel.setBorder(customBorder("Tổng điểm"));
        scorePane.add(totalScorePanel, BorderLayout.SOUTH);
        
        gpaPanel = new JPanel();
        gpaPanel.setBackground(Color.WHITE);
        gpaPanel.setLayout(new BorderLayout());
        Dimension dimensiongpa = new Dimension(500, 120);
        gpaPanel.setPreferredSize(dimensiongpa);
        totalScorePanel.add(gpaPanel,BorderLayout.NORTH);
        
        cpaPanel = new JPanel();
        cpaPanel.setBackground(Color.WHITE);
        totalScorePanel.add(cpaPanel,BorderLayout.SOUTH);
        
        JLabel cpa = new JLabel("CPA :");
        cpa.setFont(new Font("Tahoma", Font.BOLD, 18));
        cpa.setBounds(0, 10, 30, 30);
        cpaPanel.add(cpa);       
        
        cpaText = new JLabel("");
        cpaText.setFont(new Font("Tahoma", Font.BOLD, 18));
        cpaText.setBounds(35, 10, 50, 30);
        cpaPanel.add(cpaText); 
        
        JLabel hocluc = new JLabel("Học lực :");
        hocluc.setFont(new Font("Tahoma", Font.BOLD, 18));
        hocluc.setBounds(100, 0, 30, 30);
        cpaPanel.add(hocluc);       
        
        hocLucText = new JLabel("");
        hocLucText.setFont(new Font("Tahoma", Font.BOLD, 18));
        hocLucText.setBounds(130, 0, 50, 30);
        cpaPanel.add(hocLucText); 
    }

    public TitledBorder customBorder(String name) {
        TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2), name, TitledBorder.CENTER, TitledBorder.TOP);
        titleBorder.setTitleFont(new Font("Calibri", Font.BOLD, 20));
        return titleBorder;
    }
}
