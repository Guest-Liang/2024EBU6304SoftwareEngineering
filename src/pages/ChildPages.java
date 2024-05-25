package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;
import java.awt.*;
import java.awt.event.*;

import components.*;

/**
 * The ChildPages class provides a panel for the child user to choose an item.
 * Title : ChildPages.java
 * Description:
 * The class provides a panel for the child user to choose an item.
 * It includes options to display the balance, saving goal, withdrawal, transaction, deposit, and exit.
 */
public class ChildPages extends JPanel {
    /**
     * ChildPages constructor, initializes the panel layout and buttons.
     * The panel uses BorderLayout layout, the center part is a panel with a background image, using GridBagLayout layout.
     * Get the current user and account type through UserSession.
     */
    public ChildPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        JSONObject CU = UserSession.getInstance().getCurrentUser();
        JSONObject AT = CU.getJSONObject("accountType");

        JLabel lblTitle = new JLabel("Children Page");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        bgPanel.add(lblTitle, gbc);

        String Description = "<html>Some Tips:"
            + "<br>1.Know your income and expenses, create a reasonable budget, and stick to it. " + "</br>"
            + "<br>2.Avoid unnecessary consumption and try to choose cost-effective goods and services. " + "</br>"
            + "<br>3.Set aside a portion of your monthly income as savings to put into a savings account or invest." + "</br>"
            + "<br>4.Long-term planning and savings are required for large purchases. " + "</br>"
            + "<br>5.Develop a habit of saving money, and don't spend all your money on things you don't need. " + "</br>"
            + "</html>";
        JLabel lblDescription = new JLabel(Description); 
        Tools.setLabelProperties(lblDescription);
        gbc.gridy += 1;
        bgPanel.add(lblDescription, gbc);

        JLabel label = new JLabel("Choose an item");
        Tools.setLabelProperties(label);
        gbc.gridy += 1;
        bgPanel.add(label, gbc);
    
        JButton btnShowBalance = new JButton("Show Balance");
        btnShowBalance.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnShowBalance, gbc);
    
        JButton btnSavingGoal = new JButton("Saving Goal");
        btnSavingGoal.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnSavingGoal, gbc);
    
        JButton btnWithdraw = new JButton("Withdrawal");
        btnWithdraw.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnWithdraw, gbc);
    
        JButton btnDeposit = new JButton("Deposit");
        btnDeposit.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnDeposit, gbc);

        JButton btnTransaction = new JButton("Transaction");
        btnTransaction.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnTransaction, gbc);

        JButton btnChangeSaving = new JButton("Change Saving");
        btnChangeSaving.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx = 0;
        gbc.gridy += 1;
        bgPanel.add(btnChangeSaving, gbc);

        JButton btnBack = Tools.BackButton(this, new LoginWindow());
        btnBack.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        bgPanel.add(btnBack, gbc);
        
        JButton btnExit = Tools.ExitButton();
        btnExit.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnExit, gbc);

        /**
         * When the "Show Balance" button is clicked, a dialog box is displayed showing the current account balance.
         */
        btnShowBalance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String message = "Your balance is: " 
                    + "\ncurrent: " + AT.getString("current") 
                    + "\nsaving: " + AT.getString("saving");
                JOptionPane.showMessageDialog(null, message);
            }
        });

        /**
         * When the "Saving Goal" button is clicked, the SavingGoalPages panel is displayed.
         */
        btnSavingGoal.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new SavingGoalPages(), getParent());
            }
        });

        /**
         * When the "Withdrawal" button is clicked, a dialog box is displayed to enter the withdrawal amount.
         * If the withdrawal amount is greater than the current balance, a dialog box is displayed indicating that the balance is insufficient.
         * If the withdrawal is successful, the current balance is updated and a dialog box is displayed indicating that the withdrawal is successful.
         */
        btnWithdraw.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String current = AT.getString("current");
                String message = "Your balance is: " 
                    + "\ncurrent: " + AT.getInteger("current") 
                    + "\nsaving: " + AT.getInteger("saving")
                    + "\nDo you want to withdraw money?";
                int response = JOptionPane.showConfirmDialog(null, message);
                if (response == JOptionPane.YES_OPTION) {
                    while(true) {
                        String input = JOptionPane.showInputDialog("Please enter the amount you want to withdraw");
                        if (input == null) { break; } // User clicked the cancel button
                        try {
                            int amount = Integer.parseInt(input);
                            if (amount > Integer.parseInt(current)) {
                                JOptionPane.showMessageDialog(null, "You don't have enough balance");
                                return;
                            }
                            AT.put("current", Integer.parseInt(current) - amount);
                            CU.put("accountType", AT);
                            JOptionPane.showMessageDialog(null, "Withdrawal successful");

                            // Add a transaction record
                            JSONArray transaction = UserSession.getInstance().getCurrentTransaction();
                            Transaction newTransaction = new Transaction();
                            newTransaction.setTransactionId(String.valueOf(transaction.size() + 1));
                            newTransaction.setTransactionTime(Tools.getCurrentTime());
                            newTransaction.setTransactionAmount(amount);
                            newTransaction.setTransactionDescription("Withdrawal");
                            newTransaction.setTransactionMemberName(CU.getString("username"));
                            transaction.add(Transaction.TransactionData2Json(newTransaction));
                            UserSession.getInstance().setCurrentTransaction(transaction);

                            break;
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.");
                        }
                    }
                    UserSession.getInstance().setCurrentUser(CU);
                    Tools.SaveCU2CI();
                    Tools.SaveUserInfo();
                    Tools.SaveTransactionInfo();
                }
            }
        });

        /**
         * When the "Deposit" button is clicked, the DepositPages panel is displayed.
         */
        btnDeposit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new DepositPages(), getParent());
            }
        });

        /**
         * When the "Transaction" button is clicked, the TransactionPages panel is displayed.
         */
        btnTransaction.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tools.RefreshPages(new TransactionPages(), getParent());
            }
        });

        /**
         * When the "Change Saving" button is clicked, the ChangeSavingPages panel is displayed.
         */
        btnChangeSaving.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tools.RefreshPages(new ChangeSavingPages(), getParent());
            }
        });

    }
}