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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import subComponent.MenuItemPane;

/**
 *
 * @author 84969
 */
public class SubjectPanel extends MenuItemPane {

    private static final long serialVersionUID = 1L;

    private JButton addButton;
    private JButton searchButton;
    private JButton deleteButton;

    private JPanel mainPane;
    private JPanel searchPane;

    public JButton getDeleteButton() {
        return this.deleteButton;
    }
    
    public JButton getSearchButton()
    {
        return this.searchButton;
    }

    public JPanel getMainPane() {
        return this.mainPane;
    }

    public JButton getAddButton() {
        return this.addButton;
    }

    public SubjectPanel() {
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
        searchPane.add(searchField,BorderLayout.NORTH);
        
         JPanel menuButton = new JPanel();
        final ImageIcon addIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/add.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        final ImageIcon searchIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/search.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        final ImageIcon deleteIcon = new ImageIcon(new ImageIcon(UserPanel.class.getResource("/image/delete.png")).getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        
        
        searchButton = new JButton("Tìm kiếm", searchIcon);
        searchButton.setVerticalTextPosition(JButton.CENTER);
        searchButton.setFont(new Font("Calibri", Font.PLAIN, 25));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(70,166,255));
        menuButton.add(searchButton);
        
        
        addButton = new JButton("Thêm", addIcon);
        addButton.setVerticalTextPosition(JButton.CENTER);
        addButton.setFont(new Font("Calibri", Font.PLAIN, 25));
        addButton.setForeground(Color.WHITE);
        addButton.setBackground(new Color(70,166,255));
        menuButton.add(addButton);

//        deleteButton = new JButton("Xoá", deleteIcon);
//        deleteButton.setVerticalTextPosition(JButton.CENTER);
//        deleteButton.setForeground(Color.WHITE);
//        deleteButton.setBackground(new Color(70,166,255));
//        deleteButton.setFont(new Font("Calibri", Font.PLAIN, 25));
//        menuButton.add(deleteButton);
        searchPane.add(menuButton,BorderLayout.SOUTH);
    }

    public void initMainUI() {
        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.setBackground(Color.WHITE);
        mainPane.setBorder(customBorder("Danh Sách Môn Học"));
    }

    public TitledBorder customBorder(String name) {
        TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 2),
                name, TitledBorder.CENTER, TitledBorder.TOP);
        titleBorder.setTitleFont(new Font("Calibri", Font.BOLD, 20));
        return titleBorder;
    }
}


