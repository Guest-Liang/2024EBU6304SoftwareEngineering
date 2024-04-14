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
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class ChildPages extends JPanel {
    private JButton btnShowBalance;
    private JButton btnSavingGoal;
    private JButton btnWithdraw;
    private JButton btnDeposit;
    private JButton btnExit;

    public ChildPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components

        JLabel label = new JLabel("Choose a item");
        Tools.setLabelProperties(label);
        gbc.gridx = 0;
        gbc.gridy = 0;
        bgPanel.add(label, gbc);
    
        btnShowBalance = new JButton("show balance");
        // gbc.gridx = 0;
        gbc.gridy = 1;
        bgPanel.add(btnShowBalance, gbc);
    
        btnSavingGoal = new JButton("saving goal");
        gbc.gridx = 1;
        // gbc.gridy = 1;
        bgPanel.add(btnSavingGoal, gbc);
    
        btnWithdraw = new JButton("Withdrawal");
        gbc.gridx = 2;
        // gbc.gridy = 1;
        bgPanel.add(btnWithdraw, gbc);
    
        btnDeposit = new JButton("deposit");
        gbc.gridx = 3;
        // gbc.gridy = 1;
        bgPanel.add(btnDeposit, gbc);

        JButton btnBack = Tools.BackButton(this, new LoginWindow());
        gbc.gridx = 0;
        gbc.gridy = 2;
        bgPanel.add(btnBack, gbc);
        
        btnExit = Tools.ExitButton();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        bgPanel.add(btnExit, gbc);

        btnShowBalance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JSONObject CU = UserSession.getInstance().getCurrentUser();
                JOptionPane.showMessageDialog(null, "Your balance is " + CU.getString("balance"));
            }
        });

        btnSavingGoal.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new SavingGoalPages(), getParent());
            }
        });

        btnWithdraw.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JSONObject CU = UserSession.getInstance().getCurrentUser();
                String balance = CU.getString("balance");
                JOptionPane.showMessageDialog(null, "Your balance is " + balance);
                while(true) {
                    String input = JOptionPane.showInputDialog("Please enter the amount you want to withdraw");
                    if (input == null) { break; } // User clicked the cancel button
                    try {
                        int amount = Integer.parseInt(input);
                        if (amount > Integer.parseInt(balance)) {
                            JOptionPane.showMessageDialog(null, "You don't have enough balance");
                            return;
                        }
                        CU.put("balance", Integer.parseInt(balance) - amount);
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
        });

        btnDeposit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new DepositPages(), getParent());
            }
        });
    }
}