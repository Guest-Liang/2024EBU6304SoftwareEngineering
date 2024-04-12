package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;

import java.awt.*;
import java.awt.event.*;

import components.BackgroundImagePanel;
import components.Tools;
import components.UserSession;

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
    private JButton btnReturn;

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
        gbc.gridx = 0;
        gbc.gridy = 1;
        bgPanel.add(btnShowBalance, gbc);
    
        btnSavingGoal = new JButton("saving goal");
        gbc.gridx = 1;
        gbc.gridy = 1;
        bgPanel.add(btnSavingGoal, gbc);
    
        btnWithdraw = new JButton("Withdrawal");
        gbc.gridx = 2;
        gbc.gridy = 1;
        bgPanel.add(btnWithdraw, gbc);
    
        btnDeposit = new JButton("deposit");
        gbc.gridx = 3;
        gbc.gridy = 1;
        bgPanel.add(btnDeposit, gbc);

        btnReturn = Tools.ExitButton();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        bgPanel.add(btnReturn, gbc);


        btnShowBalance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JSONObject CU = UserSession.getInstance().getCurrentUser();
                JOptionPane.showMessageDialog(null, "your balance is " + CU.getString("balance"));
            }
        });
    }
}