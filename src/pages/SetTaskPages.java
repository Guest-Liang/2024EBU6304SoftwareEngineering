package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.alibaba.fastjson.*;

import components.*;

/**
 * The SetTaskPages class provides a panel for the user to set a task.
 * Title : SetTaskPages.java
 * Description:
 * The class provides a panel for the user to set a task.
 * @version 0.1.0
 */
public class SetTaskPages extends JPanel {
    public SetTaskPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String newTaskId = String.valueOf(UserSession.getInstance().getCurrentTask().size()+1);
        JLabel taskIdLabel = new JLabel("TaskId:"+newTaskId);
        Tools.setLabelProperties(taskIdLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        bgPanel.add(taskIdLabel, gbc);

        JLabel taskNameLabel = new JLabel("TaskName:");
        Tools.setLabelProperties(taskNameLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        bgPanel.add(taskNameLabel, gbc);
        JTextField taskNameField = new JTextField();
        gbc.gridx = 1;
        taskNameField.setPreferredSize(new Dimension(200, 30));
        bgPanel.add(taskNameField, gbc);

        JLabel taskPayLabel = new JLabel("TaskPay:");
        Tools.setLabelProperties(taskPayLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        bgPanel.add(taskPayLabel, gbc);
        JTextField taskPayField = new JTextField();
        gbc.gridx = 1;
        taskPayField.setPreferredSize(new Dimension(200, 30));
        bgPanel.add(taskPayField, gbc);


        JButton btnExit = Tools.ExitButton();
        gbc.gridx = 0;
        gbc.gridy = 3;        
        bgPanel.add(btnExit, gbc);

        JButton btnSave = new JButton("Save");
        gbc.gridx = 1;
        bgPanel.add(btnSave, gbc);

        JButton btnBack = Tools.BackButton(this, new ParentPages());
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        bgPanel.add(btnBack, gbc);


        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (CheckSetTask(taskNameField, taskPayField)) {
                    Task task = new Task();
                    task.setTaskName(taskNameField.getText());
                    task.setTaskPay(Integer.parseInt(taskPayField.getText()));
                    task.setTaskFinished(false);
                    task.setTaskHandleTime("");
                    task.setTaskSetTime(Tools.getCurrentTime());
                    task.setTaskId(newTaskId);
                    task.setTaskSetter(UserSession.getInstance().getCurrentUser().getString("username"));
                    task.setTaskHandler("");

                    JSONObject taskJson = Task.TaskData2Json(task);
                    UserSession.getInstance().getCurrentTask().add(taskJson);
                    Tools.SaveTaskInfo();

                    JSONObject cu = UserSession.getInstance().getCurrentUser();
                    cu.put("balance", cu.getInteger("balance") - Integer.parseInt(taskPayField.getText()));
                    Tools.SaveCU2CI();
                    Tools.SaveUserInfo();

                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionId(String.valueOf(UserSession.getInstance().getCurrentTransaction().size()+1));
                    newTransaction.setTransactionTime(Tools.getCurrentTime());
                    newTransaction.setTransactionDescription("Spend of set task ("+taskNameField.getText()+")");
                    newTransaction.setTransactionAmount(-Integer.parseInt(taskPayField.getText()));
                    newTransaction.setTransactionMemberName(UserSession.getInstance().getCurrentUser().getString("username"));
                    JSONObject newTransactionJson = Transaction.TransactionData2Json(newTransaction);
                    UserSession.getInstance().getCurrentTransaction().add(newTransactionJson);
                    Tools.SaveTransactionInfo();

                    taskNameField.setText("");
                    taskPayField.setText("");
                    String newTaskId = String.valueOf(UserSession.getInstance().getCurrentTask().size()+1);
                    taskIdLabel.setText("TaskId:"+newTaskId);
                    return;
                }
            }
        });

    }

    public static boolean CheckSetTask(JTextField taskNameField, JTextField taskPayField) {
        String taskName = taskNameField.getText();
        String taskPay = taskPayField.getText();

        if (taskName.isEmpty() || taskPay.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the fields!");
            return false;
        }

        if (taskName.startsWith(" ")) {
            JOptionPane.showMessageDialog(null, "Username cannot start with a space!");
            return false;
        }

        try {
            int pay = Integer.parseInt(taskPay);
            if (pay <= 0) { 
                JOptionPane.showMessageDialog(null, "Pay must be greater than 0");
                return false;
            }
            int currentUserBalance = UserSession.getInstance().getCurrentUser().getInteger("balance");
            if (pay > currentUserBalance) {
                JOptionPane.showMessageDialog(null, "You don't have enough balance\nYour balance is " + currentUserBalance);
                return false;
            }
        } catch (NumberFormatException e) { 
            JOptionPane.showMessageDialog(null, "Pay must be a int number!");
            return false;
        }

        return true;
    }
}
