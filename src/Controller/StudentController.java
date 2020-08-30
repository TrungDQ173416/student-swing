/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Component.StudentPanel;
import ConnectService.PanelListener;
import ConnectService.sqlConnect;
import Data.StudentData;
import Dialog.StudentDialog;
import Model.Student;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import subComponent.ButtonTable;

/**
 *
 * @author 84969
 */
public class StudentController {

    private StudentPanel studentPanel;
    private StudentData studentData;
    private StudentTable studentTable;

    private Student studentSelected;

    public StudentController(StudentPanel studentPanel, StudentData studentData) {
        this.studentData = studentData;
        this.studentPanel = studentPanel;
    }

    public StudentData getStudentData() {
        return this.studentData;
    }

    public Student getStudentSelected() {
        return this.studentSelected;
    }

    public void setViewAndEvent() {

        this.studentTable = new StudentTable();
        studentPanel.getMainPane().add(new JScrollPane(studentTable), BorderLayout.CENTER);

        updateMainPane();
        setAction();
    }

    public void updateMainPane() {

        studentTable.deleteAllRow();
        ArrayList<Student> listStudents = studentData.getListStudents();
        for (Student student : listStudents) {
            String name = student.getName();
            int age = student.getAge();
            String email = student.getEmail();
            String phone = student.getPhone();
            String mssv = student.getMssv();
            String sex = student.getSex();
            String address = student.getAddress();

            Object[] row = {studentTable.getRowCount() + 1, student, mssv, age, sex, address, email, phone};
            studentTable.addRow(row);
        }
    }

    private void setAction() {
          studentPanel.getSearchButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = studentPanel.getNameField().getText();
                String email = studentPanel.getEmailField().getText();
                String mssv = studentPanel.getMssvField().getText();
                String sex = "";
                if (studentPanel.getMaleButton().isSelected()) {
                    sex = "Nam";
                } else if (studentPanel.getFemaleButton().isSelected()) {
                    sex = "Nữ";
                } else {
                    sex = "";
                }
                studentData.getListStudentByQuery(name, email, mssv, sex);
                updateMainPane();
            }

        });

        studentPanel.getResetButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                studentPanel.getNameField().setText("");
                studentPanel.getEmailField().setText("");
                studentPanel.getMssvField().setText("");
                studentPanel.getSexGroup().clearSelection();
            }

        });
        studentPanel.setOnPanelOpenned(new PanelListener() {

            @Override
            public void onPanelOpenned() {
                updateMainPane();
            }
        });

        studentPanel.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createStudentDialog(false);
            }
        });
    }

    private class EditEvent extends AbstractAction {

        private static final long serialVersionUID = 1L;

        private final JTable table;

        private EditEvent(JTable table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
            studentSelected = (Student) table.getModel().getValueAt(selectedRow, 1);
            createStudentDialog(true);

        }
    }

    private class DeleteEvent extends AbstractAction {

        private static final long serialVersionUID = 1L;

        private final JTable table;

        private DeleteEvent(JTable table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {

            int isDelete = JOptionPane.showConfirmDialog(null,
                    "Việc này không thể hoàn tác. Bạn có chắc muốn xóa không?!",
                    "Xóa tài khoản", JOptionPane.YES_NO_OPTION);

            if (isDelete == 0) {
                DefaultTableModel tableModel = (DefaultTableModel) studentTable.getModel();
                int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
                Student student = (Student) table.getModel().getValueAt(selectedRow, 1);
                if (student != null) {
                    studentData.deleteStudent(student);
                    tableModel.removeRow(selectedRow);
                }
            }

        }
    }

    private void createStudentDialog(boolean isEdit) {
        StudentDialog studentDialog = new StudentDialog(this, isEdit);
        studentDialog.setModal(true);
        studentDialog.setVisible(true);
    }

    private class StudentTable extends JTable {

        private static final long serialVersionUID = 1L;

        private DefaultTableModel defaultTableModel;

        public StudentTable() {

            final String[] titles = {"STT", "Tên sinh viên", "Mã sinh viên", "Tuổi", "Giới Tính", "Địa chỉ", "Email", "Phone", ""};

            defaultTableModel = new DefaultTableModel(null, titles) {

                private static final long serialVersionUID = 1L;

                @Override
                public Class<?> getColumnClass(int column) {
                    for (int row = 0; row < getRowCount(); row++) {
                        if (getValueAt(row, column) != null) {
                            return getValueAt(row, column).getClass();
                        }
                    }
                    return String.class;
                }
            };

            this.setModel(defaultTableModel);
            this.setFont(new Font("Calibri", Font.PLAIN, 20));
            this.setRowHeight(40);
            this.setAutoCreateRowSorter(true);

            JTableHeader header = this.getTableHeader();
            header.setReorderingAllowed(false);
            TableCellRenderer rendererFromHeader = header.getDefaultRenderer();

            JLabel headerLabel = (JLabel) rendererFromHeader;
            headerLabel.setHorizontalAlignment(JLabel.CENTER);
            header.setFont(new Font("Calibri", Font.BOLD, 20));

            this.addButtonToTable(Arrays.asList("Sửa", "Xóa"), Arrays.asList(new EditEvent(this), new DeleteEvent(this)), 8);

            int[] positions = {0, 2, 3, 4, 5, 6, 7, 8};
            int[] widths = {50, 150, 50, 100, 300, 150, 150, 150};
            setWidth(positions, widths);
        }

        public void deleteAllRow() {
            for (int index = defaultTableModel.getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
        }

        private void setWidth(int[] positions, int[] widths) {

            TableColumn column;
            for (int index = 0; index < positions.length; index++) {
                column = columnModel.getColumn(positions[index]);
                column.setMinWidth(widths[index]);
                column.setMaxWidth(widths[index]);
                column.setPreferredWidth(widths[index]);
            }
        }

        public void addRow(Object[] rowData) {
            defaultTableModel.addRow(rowData);
        }

//        @Override
//        public boolean isCellEditable(int row, int column) {
//            if (column == 5 || column == 6) {
//                return true;
//            }
//            return false;
//        }
        private void addButtonToTable(List<String> strings, List<Action> actions, int position) {

            ButtonTable buttonTable = new ButtonTable(strings, actions);
            TableColumn column = columnModel.getColumn(position);
            column.setCellRenderer(buttonTable.getButtonsRenderer());
            column.setCellEditor(buttonTable.getButtonEditor(this));
        }
//
//        @Override
//        public Component prepareRenderer(TableCellRenderer renderer, int row,
//                int col) {
//            Component comp = super.prepareRenderer(renderer, row, col);
//            if (col != 8 && col != 9) {
//                if (col != 1 && col != 2 && col != 3 && col != 4) {
//                    ((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
//                } else {
//                    ((JLabel) comp).setHorizontalAlignment(JLabel.LEFT);
//                    ((JLabel) comp).setBorder(new EmptyBorder(0, 10, 0, 0));
//                }
//            }
//            return comp;
//      /*  }
    }

}
