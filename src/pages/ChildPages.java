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
 */
public class ChildPages extends JPanel {
    private JButton btnShowBalance;
    private JButton btnSavingGoal;
    private JButton btnWithdraw;
    private JButton btnTransaction;
    private JButton btnDeposit;
    private JButton btnExit;

    public ChildPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components

        JLabel lblTitle = new JLabel("Children Page");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        bgPanel.add(lblTitle, gbc);

        JLabel label = new JLabel("Choose a item");
        Tools.setLabelProperties(label);
        gbc.gridy += 1;
        bgPanel.add(label, gbc);
    
        btnShowBalance = new JButton("show balance");
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnShowBalance, gbc);
    
        btnSavingGoal = new JButton("saving goal");
        gbc.gridx += 1;
        bgPanel.add(btnSavingGoal, gbc);
    
        btnWithdraw = new JButton("Withdrawal");
        gbc.gridx += 1;
        bgPanel.add(btnWithdraw, gbc);
    
        btnDeposit = new JButton("deposit");
        gbc.gridx += 1;
        bgPanel.add(btnDeposit, gbc);

        btnTransaction = new JButton("Transaction");
        gbc.gridx += 1;
        bgPanel.add(btnTransaction, gbc);

        JButton btnBack = Tools.BackButton(this, new LoginWindow());
        gbc.gridx = 0;
        gbc.gridy += 1;
        bgPanel.add(btnBack, gbc);
        
        btnExit = Tools.ExitButton();
        gbc.gridx += 1;
        bgPanel.add(btnExit, gbc);

        btnShowBalance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JSONObject CU = UserSession.getInstance().getCurrentUser();
                JSONObject AT = CU.getJSONObject("accountType");
                String message = "Your balance is: " 
                    + "\ncurrent: " + AT.getString("current") 
                    + "\nsaving: " + AT.getString("saving");
                JOptionPane.showMessageDialog(null, message);
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
                JSONObject AT = CU.getJSONObject("accountType");
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

        btnDeposit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new DepositPages(), getParent());
            }
        });

        btnTransaction.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tools.RefreshPages(new TransactionPages(), getParent());
            }
        });
    }
}