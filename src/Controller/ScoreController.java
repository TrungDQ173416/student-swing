/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Component.ScorePanel;
import ConnectService.sqlConnect;
import Data.ScoreData;
import Dialog.ScoreDialog;
import Model.Result;
import Model.Score;
import Model.Student;
import Model.Subject;
import java.awt.BorderLayout;
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
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import subComponent.ButtonTable;

/**
 *
 * @author 84969
 */
public class ScoreController {

    private ScorePanel scorePanel;
    private ScoreData scoreData;
    private StudentTable studentTable;
    private ScoreTable scoreTable;
    private ResultTable resultTable;

    private Student studentSelected;
    private Score scoreSelected;

    public ScoreController(ScorePanel scorePanel, ScoreData scoreData) {
        this.scoreData = scoreData;
        this.scorePanel = scorePanel;
        setViewAndEvent();

        timer.start();
    }
            Timer timer = new Timer(200, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateMainPane();
            }
        });

    public ScoreData getScoreData() {
        return this.scoreData;
    }

    public Student getStudentSelected() {
        return this.studentSelected;
    }

    public Score getScoreSelected() {
        return this.scoreSelected;
    }

    public void setViewAndEvent() {

        this.studentTable = new StudentTable();
        this.scoreTable = new ScoreTable();
        this.resultTable = new ResultTable();
        scorePanel.getListStudentPanel().add(new JScrollPane(studentTable), BorderLayout.CENTER);
        scorePanel.getListScoreStudentPanel().add(new JScrollPane(scoreTable), BorderLayout.CENTER);
        scorePanel.getGpaPanel().add(new JScrollPane(resultTable), BorderLayout.CENTER);
        updateMainPane();
        setAction();
    }

    public void updateMainPane() {

        studentTable.deleteAllRow();
        scoreData.getData();
        ArrayList<Student> listStudents = scoreData.getListStudents();
        for (Student student : listStudents) {
            String name = student.getName();
            int age = student.getAge();
            String email = student.getEmail();
            String phone = student.getPhone();
            String mssv = student.getMssv();
            String sex = student.getSex();
            String address = student.getAddress();

            Object[] row = {studentTable.getRowCount() + 1, student, mssv, age, sex, email};
            studentTable.addRow(row);
        }
    }

    public void updateScorePane() {
        scoreTable.deleteAllRow();
        scoreData.getDataListSubject();
        scoreData.getDataScore(studentSelected.getId());
        ArrayList<Score> listScores = scoreData.getListScores();
        for (Score score : listScores) {
            String name = score.getName();
            int tin = score.getTin();
            float heso = score.getHeSo();
            float mid_score = score.getMidScore();
            float last_score = score.getLastScore();
            String grades = score.getGrades();
            String hocki = score.getHocKi();

            Object[] row = {scoreTable.getRowCount() + 1, hocki, score, tin, heso, mid_score, last_score, grades};
            scoreTable.addRow(row);
        }
    }

    public void updateGpaPane() {
        resultTable.deleteAllRow();
        scoreData.getDataGpa(studentSelected.getId());
        ArrayList<Result> listGpas = scoreData.getListGpa();
        for (Result result : listGpas) {
            float gpa = result.getGpa();
            String hocki = result.getHocKi();
            String hocluc = result.getHocLuc();
            int tcQua = result.getTcQua();
            int tcNo = result.getTcNo();
            String status = result.getStatus();

            Object[] row = {resultTable.getRowCount() + 1, hocki,tcQua,tcNo, gpa, hocluc,status};
            resultTable.addRow(row);
        }
    }

    private void setAction() {
        scorePanel.getSearchButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String name = scorePanel.getNameField().getText();
                String email = scorePanel.getEmailField().getText();
                String mssv = scorePanel.getMssvField().getText();
                String sex = "";
                if (scorePanel.getMaleButton().isSelected()) {
                    sex = "Nam";
                } else if (scorePanel.getFemaleButton().isSelected()) {
                    sex = "Nữ";
                } else {
                    sex = "";
                }
                scoreData.getListStudentByQuery(name, email, mssv, sex);
                updateMainPane();
            }

        });

        scorePanel.getResetButton().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                scorePanel.getNameField().setText("");
                scorePanel.getEmailField().setText("");
                scorePanel.getMssvField().setText("");
                scorePanel.getSexGroup().clearSelection();
            }

        });
        if (scorePanel.getAddButton().getActionListeners().length < 1) {
            scorePanel.getAddButton().addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (studentSelected == null) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên cần nhập điểm!",
                                "Lỗi", JOptionPane.WARNING_MESSAGE);
                    } else {
                        createScoreDialog(false);
                        updateGpaPane();
                    }
                }
            });
        };
        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    studentSelected = (Student) studentTable.getModel().getValueAt(selectedRow, 1);
                }
                if (studentSelected != null) {
                    scorePanel.getNameText().setText(studentSelected.getName());
                    scorePanel.getAgeText().setText(String.valueOf(studentSelected.getAge()));
                    scorePanel.getEmailText().setText(studentSelected.getEmail());
                    scorePanel.getPhoneText().setText(studentSelected.getPhone());
                    scorePanel.getSexText().setText(studentSelected.getSex());
                    scorePanel.getMssvText().setText(studentSelected.getMssv());
                    scorePanel.getAddressText().setText(studentSelected.getAddress());
                    scorePanel.getCpaText().setText(String.valueOf(studentSelected.getCpa()));
                    scorePanel.getHocLucText().setText(studentSelected.getHocLuc());
                    scoreData.getDataScore(studentSelected.getId());
                    updateScorePane();
                    updateGpaPane();
                }
            }
        });
    }

    private void createScoreDialog(boolean isEdit) {
        ScoreDialog scoreDialog = new ScoreDialog(this, isEdit);
        scoreDialog.setModal(true);
        scoreDialog.setVisible(true);
        return;
    }

    private class StudentTable extends JTable {

        private static final long serialVersionUID = 1L;

        private DefaultTableModel defaultTableModel;

        public StudentTable() {

            final String[] titles = {"STT", "Tên sinh viên", "Mã sinh viên", "Tuổi", "Giới Tính", "Email"};

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

            int[] positions = {0, 2, 3, 4, 5};
            int[] widths = {50, 120, 50, 100, 230};
            setWidth(positions, widths);
        }

        public void deleteAllRow() {
            for (int index = defaultTableModel.getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
        }

//        private void addButtonToTable(List<String> strings, List<Action> actions, int position) {
//
//            ButtonTable buttonTable = new ButtonTable(strings, actions);
//            TableColumn column = columnModel.getColumn(position);
//            column.setCellRenderer(buttonTable.getButtonsRenderer());
//            column.setCellEditor(buttonTable.getButtonEditor(this));
//        }
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

    private class EditEvent extends AbstractAction {

        private static final long serialVersionUID = 1L;

        private final JTable table;

        private EditEvent(JTable table) {
            this.table = table;
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
            scoreSelected = (Score) table.getModel().getValueAt(selectedRow, 2);
            scoreData.setScoreSelected(scoreSelected);
            scoreData.setStudentSelected(studentSelected.getId());
            createScoreDialog(true);

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
                DefaultTableModel tableModel = (DefaultTableModel) scoreTable.getModel();
                int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
                Score score = (Score) table.getModel().getValueAt(selectedRow, 2);
                if (score != null) {
                    scoreData.deleteScore(score, studentSelected.getId());
                    tableModel.removeRow(selectedRow);
                }
            }

        }
    }

    private class ScoreTable extends JTable {

        private static final long serialVersionUID = 1L;

        private DefaultTableModel defaultTableModel;

        public ScoreTable() {

            final String[] titles = {"STT", "Học kì", "Tên học phân", "Số tín", "Hệ số", "GK", "CK", "Chữ", ""};

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
            if (sqlConnect.user.getRole().equals("Manager") || sqlConnect.user.getRole().equals("Admin")) {
                this.addButtonToTable(Arrays.asList("Sửa", "Xóa"), Arrays.asList(new EditEvent(this), new DeleteEvent(this)), 8);
                int[] positions = {0, 1, 3, 4, 5, 6, 7, 8};
                int[] widths = {50, 80, 50, 50, 50, 50, 50, 150};
                setWidth(positions, widths);
            } else {
                int[] positions = {0, 1, 3, 4, 5, 6, 7};
                int[] widths = {50, 80, 50, 50, 80, 80, 80};
                setWidth(positions, widths);
            }

        }

        public void deleteAllRow() {
            for (int index = defaultTableModel.getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
        }

        private void addButtonToTable(List<String> strings, List<Action> actions, int position) {

            ButtonTable buttonTable = new ButtonTable(strings, actions);
            TableColumn column = columnModel.getColumn(position);
            column.setCellRenderer(buttonTable.getButtonsRenderer());
            column.setCellEditor(buttonTable.getButtonEditor(this));
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
    }

    private class ResultTable extends JTable {

        private static final long serialVersionUID = 1L;

        private DefaultTableModel defaultTableModel;

        public ResultTable() {

            final String[] titles = {"STT", "Học kì","Tín chỉ qua","Tín chỉ nợ", "Điểm Gpa", "Học lực","Trạng thái"};

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
            int[] positions = {0, 2, 3,4,5,6};
            int[] widths = {50, 100, 100,80,80,250};
            setWidth(positions, widths);

        }

        public void deleteAllRow() {
            for (int index = defaultTableModel.getRowCount() - 1; index >= 0; index--) {
                defaultTableModel.removeRow(index);
            }
        }

        private void addButtonToTable(List<String> strings, List<Action> actions, int position) {

            ButtonTable buttonTable = new ButtonTable(strings, actions);
            TableColumn column = columnModel.getColumn(position);
            column.setCellRenderer(buttonTable.getButtonsRenderer());
            column.setCellEditor(buttonTable.getButtonEditor(this));
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
    }

}
