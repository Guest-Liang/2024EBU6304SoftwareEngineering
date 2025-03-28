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
 */
public class SavingGoalPages extends JPanel{
    /**
     * The constructor of the SavingGoalPages class.
     * It creates a panel for the user to set a saving goal.
     */
    public SavingGoalPages()
    {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitle = new JLabel("Saving Goal Pages");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        bgPanel.add(lblTitle, gbc);

        // Add a label to display the current goal
        JSONObject CU = UserSession.getInstance().getCurrentUser();
        String labelText = "<html>Current Goal: " + CU.get("SavingGoals")
                         + "<br>Current Balance: " + CU.getJSONObject("accountType").getString("current") + "</br>"
                         + "<br>Saving Balance: " + CU.getJSONObject("accountType").getString("saving") + "</br>"
                         + "</html>";
        JLabel goalLabel = new JLabel(labelText);
        Tools.setLabelProperties(goalLabel);
        gbc.gridy += 1;
        bgPanel.add(goalLabel, gbc);

        // Add a button for adjusting the goal
        JButton adjustButton = new JButton("Adjust Goal");
        adjustButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(adjustButton, gbc);

        JButton reButton = Tools.BackButton(this, new ChildPages());
        reButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(reButton, gbc);

        // Add a button for exiting
        JButton exitButton = Tools.ExitButton();
        exitButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(exitButton, gbc);

        /**
         * When the Adjust Goal button is clicked, show an input dialog for the user to enter a new saving goal.
         * If the user enters a valid integer, set the new saving goal to the user input and update the goal label text.
         * If the user enters an invalid input, show a message dialog to inform the user of the invalid input.
         * If the user clicks the cancel button, do nothing.
         * Finally, save the user's information.
         */
        adjustButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true) {
                    String newGoal = JOptionPane.showInputDialog("Enter the new goal:");
                    if (newGoal == null) { break; } // User clicked the cancel button
                    try {
                        int goal = Integer.parseInt(newGoal);
                        CU.put("SavingGoals", goal);
                        String labelText = "<html>Current Goal: " + CU.get("SavingGoals")
                                         + "<br>Current Balance: " + CU.getJSONObject("accountType").getString("current") + "</br>"
                                         + "<br>Saving Balance: " + CU.getJSONObject("accountType").getString("saving") + "</br>"
                                         + "</html>";
                        goalLabel.setText(labelText);
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid goal.");
                    }
                }
                UserSession.getInstance().setCurrentUser(CU);
                Tools.SaveCU2CI();
                Tools.SaveUserInfo();
            }
        });
    }
}
