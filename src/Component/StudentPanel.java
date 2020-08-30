/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import common.RoundButton;
import java.awt.Font;
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
import javax.swing.border.TitledBorder;
import subComponent.MenuItemPane;

/**
 *
 * @author 84969
 */
public class StudentPanel extends MenuItemPane {

    private static final long serialVersionUID = 1L;

    private JButton addButton;
    private JButton resetButton;
    private JButton searchButton;

    private JPanel mainPane;
    private JPanel searchPane;

    private JTextField nameField;
    private JTextField emailField;
    private JTextField mssvField;
    private ButtonGroup sexGroup;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;

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

    public JButton getResetButton() {
        return this.resetButton;
    }

    public JButton getSearchButton() {
        return this.searchButton;
    }

    public JPanel getMainPane() {
        return this.mainPane;
    }

    public JButton getAddButton() {
        return this.addButton;
    }

    public StudentPanel() {
        this.setLayout(new BorderLayout(10, 10));

        initSearchUI();
        initMainUI();
        add(searchPane, BorderLayout.NORTH);
        add(mainPane, BorderLayout.CENTER);
    }

    public void initSearchUI() {

        searchPane = new JPanel();
        searchPane.setLayout(new BorderLayout());
//        searchPane.setBackground(Color.WHITE);

        JPanel searchField = new JPanel();
        searchField.setBorder(customBorder("Tìm kiếm"));
        searchField.setBackground(Color.WHITE);
        searchPane.add(searchField, BorderLayout.NORTH);

        searchField.setLayout(null);
        Dimension dimension = new Dimension(200, 200);
        searchField.setPreferredSize(dimension);

        JLabel nameLabel = new JLabel("Họ Và Tên:");
        nameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        nameLabel.setBounds(10, 25, 150, 20);
        searchField.add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Calibri", Font.BOLD, 15));
//        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        nameField.setBounds(10, 55, 500, 30);
        searchField.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        emailLabel.setBounds(650, 25, 500, 20);
        searchField.add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(new Font("Calibri", Font.BOLD, 15));
//        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        emailField.setBounds(650, 55, 500, 30);
        searchField.add(emailField);

        JLabel mssvLabel = new JLabel("Mã số sinh viên:");
        mssvLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        mssvLabel.setBounds(10, 100, 150, 20);
        searchField.add(mssvLabel);

        mssvField = new JTextField();
        mssvField.setFont(new Font("Calibri", Font.BOLD, 15));
//        nameField.setBorder(new EmptyBorder(0, 15, 0, 0));
        mssvField.setBounds(10, 130, 500, 30);
        searchField.add(mssvField);

        JLabel sexLabel = new JLabel("Giới tính:");
        sexLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        sexLabel.setBounds(650, 100, 108, 19);
        searchField.add(sexLabel);

        sexGroup = new ButtonGroup();
        maleButton = new JRadioButton("Nam");
        femaleButton = new JRadioButton("Nữ");
        sexGroup.add(maleButton);
        sexGroup.add(femaleButton);
        maleButton.setBounds(650, 130, 80, 30);
        femaleButton.setBounds(750, 130, 100, 30);
        searchField.add(maleButton);
        searchField.add(femaleButton);

        JPanel menuButton = new JPanel();
        final ImageIcon addIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/add.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        final ImageIcon searchIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/search.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        final ImageIcon deleteIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/delete.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));

        resetButton = new JButton("Làm mới");
        resetButton.setVerticalTextPosition(JButton.CENTER);
        resetButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        resetButton.setForeground(Color.WHITE);
        resetButton.setBackground(new Color(70, 166, 255));
        menuButton.add(resetButton);

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
        searchButton.setVerticalTextPosition(JButton.CENTER);
        searchButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(70, 166, 255));
        menuButton.add(searchButton);

        addButton = new JButton("Thêm", addIcon);
        addButton.setVerticalTextPosition(JButton.CENTER);
        addButton.setFont(new Font("Calibri", Font.PLAIN, 20));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(70, 166, 255));
        menuButton.add(addButton);

//        deleteButton = new JButton("Xoá", deleteIcon);
//        deleteButton.setVerticalTextPosition(JButton.CENTER);
//        deleteButton.setForeground(Color.WHITE);
//        deleteButton.setBackground(new Color(70,166,255));
//        deleteButton.setFont(new Font("Calibri", Font.PLAIN, 25));
//        menuButton.add(deleteButton);
        searchPane.add(menuButton, BorderLayout.SOUTH);
    }

    public void initMainUI() {
        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.setBackground(Color.WHITE);
        mainPane.setBorder(customBorder("Danh Sách Sinh Viên"));
    }

    public TitledBorder customBorder(String name) {
        TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                name, TitledBorder.CENTER, TitledBorder.TOP);
        titleBorder.setTitleFont(new Font("Calibri", Font.BOLD, 20));
        return titleBorder;
    }
}
