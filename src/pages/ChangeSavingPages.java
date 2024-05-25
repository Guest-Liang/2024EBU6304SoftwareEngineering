package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;
import java.awt.*;
import java.awt.event.*;

import components.*;

/**
 * The ChangeSavingPages class provides a panel for the user to transfer funds between current and saving accounts.
 * Title : ChangeSavingPages.java
 * Description:
 * The class provides a panel for the user to transfer funds between current and saving accounts.
 */
public class ChangeSavingPages extends JPanel{
    /**
     * The constructor of the ChangeSavingPages class.
     * It creates a panel for the user to transfer funds between current and saving accounts.
     * @param void
     * @return void
     */
    public ChangeSavingPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitle = new JLabel("Change Saving Pages");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        bgPanel.add(lblTitle, gbc);

        // Add a label to display the current balances
        JSONObject CU = UserSession.getInstance().getCurrentUser();
        JSONObject AT = CU.getJSONObject("accountType");
        String labelText = "<html>Current Balance: " + AT.getString("current")
                         + "<br>Saving Balance: " + AT.getString("saving") + "</br>"
                         + "</html>";
        JLabel balanceLabel = new JLabel(labelText);
        Tools.setLabelProperties(balanceLabel);
        gbc.gridy += 1;
        bgPanel.add(balanceLabel, gbc);

        // Add a button for transferring from current to saving
        JButton toSavingButton = new JButton("Current to Saving");
        toSavingButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(toSavingButton, gbc);

        // Add a button for transferring from saving to current
        JButton toCurrentButton = new JButton("Saving to Current");
        toCurrentButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(toCurrentButton, gbc);

        JButton reButton;
        if (CU.getBoolean("isParent")) {
            reButton = Tools.BackButton(this, new ParentPages());
        } else {
            reButton = Tools.BackButton(this, new ChildPages());
        }
        reButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridx = 0;
        bgPanel.add(reButton, gbc);

        // Add a button for exiting
        JButton exitButton = Tools.ExitButton();
        exitButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(exitButton, gbc);

        // Add action listeners for the buttons
        toSavingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true) {
                    String amountStr = JOptionPane.showInputDialog("Enter the amount to transfer from Current to Saving:");
                    if (amountStr == null) { break; } // User clicked the cancel button
                    try {
                        int amount = Integer.parseInt(amountStr);
                        int currentBalance = AT.getInteger("current");
                        int savingBalance = AT.getInteger("saving");
                        if (amount > currentBalance) {
                            JOptionPane.showMessageDialog(null, "You don't have enough balance in Current account.");
                            continue;
                        }
                        AT.put("current", currentBalance - amount);
                        AT.put("saving", savingBalance + amount);
                        CU.put("accountType", AT);
                        String labelText = "<html>Current Balance: " + AT.getString("current")
                                         + "<br>Saving Balance: " + AT.getString("saving") + "</br>"
                                         + "</html>";
                        balanceLabel.setText(labelText);
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.");
                    }
                }
                UserSession.getInstance().setCurrentUser(CU);
                Tools.SaveCU2CI();
                Tools.SaveUserInfo();
            }
        });

        toCurrentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                while (true) {
                    String amountStr = JOptionPane.showInputDialog("Enter the amount to transfer from Saving to Current:");
                    if (amountStr == null) { break; } // User clicked the cancel button
                    try {
                        int amount = Integer.parseInt(amountStr);
                        int currentBalance = AT.getInteger("current");
                        int savingBalance = AT.getInteger("saving");
                        if (amount > savingBalance) {
                            JOptionPane.showMessageDialog(null, "You don't have enough balance in Saving account.");
                            continue;
                        }
                        AT.put("current", currentBalance + amount);
                        AT.put("saving", savingBalance - amount);
                        CU.put("accountType", AT);
                        String labelText = "<html>Current Balance: " + AT.getString("current")
                                         + "<br>Saving Balance: " + AT.getString("saving") + "</br>"
                                         + "</html>";
                        balanceLabel.setText(labelText);
                        break;
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid amount.");
                    }
                }
                UserSession.getInstance().setCurrentUser(CU);
                Tools.SaveCU2CI();
                Tools.SaveUserInfo();
            }
        });
    }
}
