package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import components.*;

/**
 * The DepositPages class provides a panel for the user to deposit money.
 * Title : DepositPages.java
 * Description:
 * The class provides a panel for the user to deposit money.
 */
public class DepositPages extends JPanel {
    public DepositPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        JSONArray taskData = UserSession.getInstance().getCurrentTask();

        // Create column names
        String[] columnNames = {"Id", "taskName", "Finished", "SetTime", "HandleTime" ,"Pay", "Setter", "Handler"};

        Object[][] data = TaskDatatoTable(taskData, columnNames);
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                // use checkbox for the "taskFinished" column
                switch (column) {
                    case 2:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing in the "taskFinished" column
                if (column == 2) {
                    // If the task is finished, don't allow editing
                    JSONObject task = taskData.getJSONObject(row);
                    return !task.getBoolean("taskFinished");
                }
                return false;
            }
        };

        int[] columnWidths = {50, 150, 70, 150, 150, 50, 100, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.setFillsViewportHeight(false); // Disable auto resizing
        table.setPreferredScrollableViewportSize(new Dimension(800, 250));
        // Disable column resizing
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        JLabel lblTitle = new JLabel("Deposit Page");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        bgPanel.add(lblTitle, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridy += 1; 
        gbc.weightx = 1; 
        gbc.weighty = 1; 
        bgPanel.add(scrollPane, gbc);

        JButton btnSave = new JButton("Save");
        btnSave.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnSave, gbc);

        JButton btnReturn = Tools.BackButton(this, new ChildPages());
        btnReturn.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnReturn, gbc);

        JButton btnExit = Tools.ExitButton();
        btnExit.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnExit, gbc);
        
        btnSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JSONObject CU = UserSession.getInstance().getCurrentUser();
                JSONArray CI = UserSession.getInstance().getCurrentInfo();
                String relativesPwd = "";
                int flag = 0;
                for (int i = 0; i < CI.size(); i++)
                {
                    JSONObject userJson = CI.getJSONObject(i);
                    if (userJson.getString("username").equals(CU.getString("relatives")))
                    {
                        relativesPwd = userJson.getString("password");
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0)
                {
                    JOptionPane.showMessageDialog(null, "The relative does not exist.");
                    return;
                }  
                while (true) {
                    String input = JOptionPane.showInputDialog("Please enter the password of your realatives");
                    if (input == null) { return; } // User clicked the cancel button
                    if (input.equals(relativesPwd)) { 
                        // Update the taskData array with the new values from the table
                        for (int i = 0; i < table.getRowCount(); i++) {
                            JSONObject task = taskData.getJSONObject(i);
                            if (!task.getBoolean("taskFinished")) {
                                if ((Boolean) table.getValueAt(i, 2) == true) {
                                    // Update the task with the new values from the table
                                    JSONObject currentUser = UserSession.getInstance().getCurrentUser();
                                    JSONObject accountType = currentUser.getJSONObject("accountType");
                                    task.put("taskHandleTime", Tools.getCurrentTime());
                                    task.put("taskFinished", true);
                                    task.put("taskHandler", currentUser.getString("username"));
                                    accountType.put("current", accountType.getInteger("current") + task.getInteger("taskPay"));
                                    currentUser.put("accountType", accountType);
                                    UserSession.getInstance().setCurrentUser(currentUser);
                                    Tools.SaveCU2CI();

                                    
                                    // Add transaction record
                                    JSONArray transaction = UserSession.getInstance().getCurrentTransaction();
                                    Transaction newTransaction = new Transaction();
                                    newTransaction.setTransactionId(String.valueOf(transaction.size() + 1));
                                    newTransaction.setTransactionTime(Tools.getCurrentTime());
                                    newTransaction.setTransactionAmount(task.getInteger("taskPay"));
                                    newTransaction.setTransactionDescription("Task reward");
                                    newTransaction.setTransactionMemberName(currentUser.getString("username"));
                                    transaction.add(Transaction.TransactionData2Json(newTransaction));
                                    UserSession.getInstance().setCurrentTransaction(transaction);
                                }
                            }
                            else { continue; }
                        }
                        UserSession.getInstance().setCurrentTask(taskData);
                        // Write the updated taskData array back to the file
                        Tools.SaveTaskInfo();
                        Tools.SaveUserInfo();
                        Tools.SaveTransactionInfo();
        
                        // Update the table model with the new data
                        Object[][] data=TaskDatatoTable(taskData, columnNames);
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        model.setRowCount(0); // Clear the current rows
                        for (Object[] row : data) {
                            model.addRow(row);
                        }
                        table.repaint();
                        break;
                    }
                    else { JOptionPane.showMessageDialog(null, "The password is incorrect."); }
                }
            }
        });

    }

    /**
     * Convert taskData to a 2D array for the table
     * @param taskData
     * @param columnNames
     * @return
     */
    public static Object[][] TaskDatatoTable(JSONArray taskData, String[] columnNames) {
        // Create data for the table
        Object[][] data = new Object[taskData.size()][columnNames.length];
        for (int i = 0; i < taskData.size(); i++) {
            JSONObject task = taskData.getJSONObject(i);
            data[i][0] = task.getString("taskId");
            data[i][1] = task.getString("taskName");
            data[i][2] = task.getBoolean("taskFinished");
            data[i][3] = task.getString("taskSetTime");
            data[i][4] = task.getString("taskHandleTime");
            data[i][5] = task.getInteger("taskPay");
            data[i][6] = task.getString("taskSetter");
            data[i][7] = task.getString("taskHandler");
        }
        return data;
    }

}
