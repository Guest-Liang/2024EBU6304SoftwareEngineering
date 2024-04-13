package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;

import java.awt.*;
import java.awt.event.*;

import components.*;

/**
 * The SavingGoalPages class provides a panel for the user to set a saving goal.
 * Title : SavingGoalPages.java
 * Description:
 * The class provides a panel for the user to set a saving goal.
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class SavingGoalPages extends JPanel{
    /**
     * The constructor of the SavingGoalPages class.
     * It creates a panel for the user to set a saving goal.
     * @param void
     * @return void
     */
    public SavingGoalPages()
    {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components

        // Add a label to display the current goal
        JLabel goalLabel = new JLabel("Current Goal: " + UserSession.getInstance().getCurrentUser().get("SavingGoals") + " Current Balance: " + UserSession.getInstance().getCurrentUser().get("balance"));
        Tools.setLabelProperties(goalLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        bgPanel.add(goalLabel, gbc);

        // Add a button for adjusting the goal
        JButton adjustButton = new JButton("Adjust Goal");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        bgPanel.add(adjustButton, gbc);

        JButton reButton = Tools.BackButton(this, new ChildPages());
        gbc.gridx = 1;
        gbc.gridy = 1;
        bgPanel.add(reButton, gbc);

        // Add a button for exiting
        JButton exitButton = Tools.ExitButton();
        gbc.gridx = 2;
        gbc.gridy = 1;
        bgPanel.add(exitButton, gbc);

        adjustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JSONObject currentUser = UserSession.getInstance().getCurrentUser();
                while (true) {
                    String newGoal = JOptionPane.showInputDialog("Enter the new goal:");
                    if (newGoal == null) { break; } // User clicked the cancel button
                    try {
                        int goal = Integer.parseInt(newGoal);
                        currentUser.put("SavingGoals", goal);
                        goalLabel.setText("Current Goal: " + goal + " Current Balance: " + UserSession.getInstance().getCurrentUser().get("balance"));
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid goal.");
                    }
                }
                UserSession.getInstance().setCurrentUser(currentUser);
                Tools.SaveCU2CI();
                Tools.SaveUserInfo();
            }
        });
    }
}
