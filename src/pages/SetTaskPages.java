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
 */
public class SetTaskPages extends JPanel {
    /**
     * Constructor of the SetTaskPages class, initializes the panel layout, 
     * adds a background image, sets the user session, adds various buttons and labels, and adds action listeners to the buttons.
     * It creates a panel for the user to set a task.
     */
    public SetTaskPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        JSONObject cu = UserSession.getInstance().getCurrentUser();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitle = new JLabel("Setting Task Page");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(lblTitle, gbc);

        String newTaskId = String.valueOf(UserSession.getInstance().getCurrentTask().size()+1);
        JLabel taskIdLabel = new JLabel("TaskId:" + newTaskId);
        Tools.setLabelProperties(taskIdLabel);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(taskIdLabel, gbc);

        JLabel taskNameLabel = new JLabel("TaskName:");
        Tools.setLabelProperties(taskNameLabel);
        gbc.gridy += 1;
        bgPanel.add(taskNameLabel, gbc);
        JTextField taskNameField = new JTextField();
        gbc.gridx += 1;
        taskNameField.setPreferredSize(new Dimension(200, 30));
        bgPanel.add(taskNameField, gbc);

        JLabel taskPayLabel = new JLabel("TaskPay:");
        Tools.setLabelProperties(taskPayLabel);
        gbc.gridx = 0;
        gbc.gridy += 1;
        bgPanel.add(taskPayLabel, gbc);
        JTextField taskPayField = new JTextField();
        gbc.gridx += 1;
        taskPayField.setPreferredSize(new Dimension(200, 30));
        bgPanel.add(taskPayField, gbc);


        JButton btnExit = Tools.ExitButton();
        btnExit.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx = 0;
        gbc.gridy += 1;        
        bgPanel.add(btnExit, gbc);

        JButton btnSave = new JButton("Save");
        btnSave.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnSave, gbc);

        JButton btnBack = Tools.BackButton(this, new ParentPages());
        btnBack.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 2;
        bgPanel.add(btnBack, gbc);

        /**
         * When the Save button is clicked, check if the task name and pay are valid,
         * create a new task object, add the task to the current user's task list,
         * update the current user's account balance, save the task information,
         * update the current user's information, create a new transaction object,
         * add the transaction to the current user's transaction list, save the transaction information,
         * clear the task name and pay fields, and update the task ID label.
         */
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
                    task.setTaskSetter(cu.getString("username"));
                    task.setTaskHandler("");

                    JSONObject taskJson = Task.TaskData2Json(task);
                    UserSession.getInstance().getCurrentTask().add(taskJson);
                    Tools.SaveTaskInfo();

                    // JSONObject cu = UserSession.getInstance().getCurrentUser();
                    JSONObject at = cu.getJSONObject("accountType");
                    at.put("current", at.getInteger("current") - Integer.parseInt(taskPayField.getText()));
                    cu.put("accountType", at);
                    Tools.SaveCU2CI();
                    Tools.SaveUserInfo();

                    Transaction newTransaction = new Transaction();
                    newTransaction.setTransactionId(String.valueOf(UserSession.getInstance().getCurrentTransaction().size()+1));
                    newTransaction.setTransactionTime(Tools.getCurrentTime());
                    newTransaction.setTransactionDescription("Spend of set task ("+taskNameField.getText()+")");
                    newTransaction.setTransactionAmount(-Integer.parseInt(taskPayField.getText()));
                    newTransaction.setTransactionMemberName(cu.getString("username"));
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

    /**
     * Check if the task name and pay are valid.
     * @param taskNameField taskname field
     * @param taskPayField taskpay field
     * @return true if the task name and pay are valid, false otherwise
     */
    public static boolean CheckSetTask(JTextField taskNameField, JTextField taskPayField) {
        String taskName = taskNameField.getText();
        String taskPay = taskPayField.getText();
        JSONObject cu = UserSession.getInstance().getCurrentUser();
        JSONObject at = cu.getJSONObject("accountType");
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
            int currentUserBalance = at.getInteger("current");
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
