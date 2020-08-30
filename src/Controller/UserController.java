/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Component.UserPanel;
import ConnectService.PanelListener;
import ConnectService.sqlConnect;
import Data.UserData;
import Dialog.UserDialog;
import Model.User;
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
public class UserController {

    private UserPanel userPanel;
    private UserData userData;
    private UserTable userTable;

    private User userSelected;

    public UserController(UserPanel userPanel, UserData userData) {
        this.userData = userData;
        this.userPanel = userPanel;
    }

    public UserData getUserData() {
        return this.userData;
    }

    public User getUserSelected() {
        return this.userSelected;
    }

    public void setViewAndEvent() {

        this.userTable = new UserTable();
        userPanel.getMainPane().add(new JScrollPane(userTable), BorderLayout.CENTER);

        updateMainPane();
        setAction();
    }

    public void updateMainPane() {

        userTable.deleteAllRow();
        ArrayList<User> listUsers = userData.getListUsers();
        for (User user : listUsers) {
            String password = user.getPassWord();
            String role = user.getRole();
            Object[] row = {userTable.getRowCount() + 1, user, password, role};
            userTable.addRow(row);
        }
    }

    private void setAction() {

//        userPanel.getDeleteButton().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                int[] selectedRows = userTable.getSelectedRows();
//
//                if (selectedRows.length > 0) {
//
//                    int isDelete = JOptionPane.showConfirmDialog(null,
//                            "Việc này không thể hoàn tác. Bạn có chắc muốn xóa không?!",
//                            "Xóa tài khoản", JOptionPane.YES_NO_OPTION);
//
//                    if (isDelete == 0) {
//
//                        for (int index = selectedRows.length - 1; index >= 0; index--) {
//
//                            DefaultTableModel tableModel = (DefaultTableModel) userTable.getModel();
//                            User user = (User) tableModel.getValueAt(selectedRows[index], 1);
//
//                            if (user.getId() == sqlConnect.user.getId()) {
//                                JOptionPane.showMessageDialog(null, "Bạn không thể xóa tài khoản của chính bạn!",
//                                        "Lỗi", JOptionPane.WARNING_MESSAGE);
//                            } else {
//                                userData.deleteUser(user);
//                                tableModel.removeRow(selectedRows[index]);
//                            }
//                        }
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "Bạn chưa chọn tài khoản để xóa!",
//                            "Lỗi", JOptionPane.WARNING_MESSAGE);
//                }
//            }
//        });
        userPanel.setOnPanelOpenned(new PanelListener() {

            @Override
            public void onPanelOpenned() {
                updateMainPane();
            }
        });

        userPanel.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUserDialog(false);
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
            userSelected = (User) table.getModel().getValueAt(selectedRow, 1);
            createUserDialog(true);

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

//                    for (int index = selectedRows.length - 1; index >= 0; index--) {              
                DefaultTableModel tableModel = (DefaultTableModel) userTable.getModel();
                 int selectedRow = table.convertRowIndexToModel(table.getEditingRow());
                User user = (User) table.getModel().getValueAt(selectedRow, 1);
//
               if(sqlConnect.user != null){
                    if (user.getUserName().equals(sqlConnect.user.getUserName())) {
                    JOptionPane.showMessageDialog(null, "Bạn không thể xóa tài khoản của chính bạn!",
                            "Lỗi", JOptionPane.WARNING_MESSAGE);
                } else {
                    userData.deleteUser(user);
                    tableModel.removeRow(selectedRow);
                }
               }
               else{
                    userData.deleteUser(user);
                    tableModel.removeRow(selectedRow);
               }
//                    }
            }

        }
    }

    private void createUserDialog(boolean isEdit) {
        UserDialog userDialog = new UserDialog(this, isEdit);
        userDialog.setModal(true);
        userDialog.setVisible(true);
    }

    private class UserTable extends JTable {

        private static final long serialVersionUID = 1L;

        private DefaultTableModel defaultTableModel;

        public UserTable() {

            final String[] titles = {"STT", "Tên tài khoản", "Mật khẩu", "Quyền", "Chọn"};

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

            this.addButtonToTable(Arrays.asList("Sửa", "Xóa"), Arrays.asList(new EditEvent(this), new DeleteEvent(this)), 4);

            int[] positions = {0, 2, 3, 4};
            int[] widths = {50, 500, 130, 150};
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
