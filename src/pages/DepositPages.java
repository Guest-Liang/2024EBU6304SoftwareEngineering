package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;

import java.awt.*;
import java.awt.event.*;

import components.*;
import javax.swing.table.DefaultTableModel;

public class DepositPages extends JPanel {
    public DepositPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        // Read data from task.json
        JSONArray taskData = Tools.ReadFromFile("data/task.json");

        // Create column names
        String[] columnNames = {"taskname", "taskid", "taskfinished", "tasksettime", "taskhandletime" ,"taskpay", "tasksetter", "taskhandler"};

        Object[][] data = TaskDatatoTable(taskData, columnNames);
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int column) {
                // use checkbox for the "taskfinished" column
                switch (column) {
                    case 2:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Only allow editing in the "taskfinished" column
                if (column == 2) {
                    // If the task is finished, don't allow editing
                    JSONObject task = taskData.getJSONObject(row);
                    return !task.getBoolean("taskfinished");
                }
                return false;
            }
        };

        table.setFillsViewportHeight(false);
        table.setPreferredScrollableViewportSize(new Dimension(800, 250));
        JScrollPane scrollPane = new JScrollPane(table);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.weightx = 1; 
        gbc.weighty = 1; 
        gbc.gridwidth = 3;
        bgPanel.add(scrollPane, gbc);

        JButton btnSave = new JButton("Save");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnSave, gbc);

        JButton btnReturn = Tools.BackButton(this, new ChildPages());
        gbc.gridx = 1;
        gbc.gridy = 1;
        bgPanel.add(btnReturn, gbc);

        JButton btnExit = Tools.ExitButton();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnExit, gbc);
        
        btnSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Update the taskData array with the new values from the table
                for (int i = 0; i < table.getRowCount(); i++) {
                    JSONObject task = taskData.getJSONObject(i);
                    if (!task.getBoolean("taskfinished")) {
                        if ((Boolean) table.getValueAt(i, 2) == true) {
                            // Update the task with the new values from the table
                            task.put("taskhandletime", Tools.getCurrentTime());
                            task.put("taskfinished", true);
                            task.put("taskhandler", UserSession.getInstance().getCurrentUser().getString("username"));
                            JSONObject currentUser = UserSession.getInstance().getCurrentUser();
                            currentUser.put("balance", currentUser.getInteger("balance") + task.getInteger("taskpay"));
                            UserSession.getInstance().setCurrentUser(currentUser);
                        }
                    }
                    else { continue; }
                }
                UserSession.getInstance().setCurrentTask(taskData);
                // Write the updated taskData array back to the file
                Tools.SaveTaskInfo();
                Tools.SaveUserInfo();

                Object[][] data=TaskDatatoTable(taskData, columnNames);
                // Update the table model with the new data
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0); // Clear the current rows
                for (Object[] row : data) {
                    model.addRow(row);
                }
                table.repaint();
            }
        });

    }

    public static Object[][] TaskDatatoTable(JSONArray taskData, String[] columnNames) {
        // Create data for the table
        Object[][] data = new Object[taskData.size()][columnNames.length];
        for (int i = 0; i < taskData.size(); i++) {
            JSONObject task = taskData.getJSONObject(i);
            data[i][0] = task.getString("taskname");
            data[i][1] = task.getString("taskid");
            data[i][2] = task.getBoolean("taskfinished");
            data[i][3] = task.getString("tasksettime");
            data[i][4] = task.getString("taskhandletime");
            data[i][5] = task.getInteger("taskpay");
            data[i][6] = task.getString("tasksetter");
            data[i][7] = task.getString("taskhandler");
        }
        return data;
    }

}
