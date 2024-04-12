package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import components.BackgroundImagePanel;
import components.Tools;
import components.UserSession;

import com.alibaba.fastjson.*;

/**
 * The RegisterPages class provides a panel for the user to register an account.
 * Title : RegisterPages.java
 * Description:
 * The class provides a panel for the user to register an account.
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class RegisterPages extends JPanel {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JComboBox<String> accountTypeBox;

    /**
     * The constructor of the RegisterPages class.
     * It creates a panel for the user to register an account.
     * @param void
     * @return void
     */
    public RegisterPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Create and add name label and text field
        JLabel nameLabel = new JLabel("Name:");
        Tools.setLabelProperties(nameLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        bgPanel.add(nameLabel, gbc);
    
        nameField = new JTextField();
        gbc.gridx = 1;
        nameField.setPreferredSize(new Dimension(200, 30));
        bgPanel.add(nameField, gbc);
    
        // Create and add password label and password field
        JLabel passwordLabel = new JLabel("Password:");
        Tools.setLabelProperties(passwordLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        bgPanel.add(passwordLabel, gbc);
    
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        bgPanel.add(passwordField, gbc);
    
        // Create and add account type label and combo box
        JLabel accountTypeLabel = new JLabel("Account Type:");
        Tools.setLabelProperties(accountTypeLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        bgPanel.add(accountTypeLabel, gbc);

        String[] accountTypes = {"current", "saving"};
        accountTypeBox = new JComboBox<>(accountTypes);
        gbc.gridx = 1;
        bgPanel.add(accountTypeBox, gbc);

        JButton btnReturn = Tools.ExitButton();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        bgPanel.add(btnReturn, gbc);

        JButton btnSave = new JButton("Save");
        gbc.gridx = 1;
        bgPanel.add(btnSave, gbc);
        btnSave.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (CheckRegister(nameField, passwordField, accountTypeBox))
                {
                    JSONObject user = new JSONObject();
                    user.put("username", nameField.getText());
                    user.put("password", new String(passwordField.getPassword()));
                    user.put("accountType", (String) accountTypeBox.getSelectedItem());
                    user.put("balance", 0);
                    UserSession.getInstance().getCurrentInfo().add(user);
                    try
                    {
                        Tools.WriteToFile("data/user.json", UserSession.getInstance().getCurrentInfo());
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                    nameField.setText("");
                    passwordField.setText("");
                    JOptionPane.showMessageDialog(null, "Register successful.\nInfo has saved.");
                    // System.out.println(UserSession.getInstance().getCurrentInfo());
                }
            }
        });

    }

    public static boolean CheckRegister(JTextField nameField, JPasswordField passwordField, JComboBox<String> accountTypeBox) {
        String username = nameField.getText();
        String password = new String(passwordField.getPassword());
        // String accountType = (String) accountTypeBox.getSelectedItem();
    
        // Check if username is alphanumeric
        if (!username.matches("[A-Za-z0-9]+")) {
            JOptionPane.showMessageDialog(null, "Username must be alphanumeric.");
            return false;
        }
    
        // Check if password length is not more than 8 and only contains alphanumeric or special characters
        if (password.length() > 8 || !password.matches("[A-Za-z0-9@#$%^&+=.]+")) {
            JOptionPane.showMessageDialog(null, "Password must not exceed 8 characters and should only contain alphanumeric or special characters.");
            return false;
        }
    
        // Check if username already exists
        if (usernameExists(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists.");
            return false;
        }

        return true;
    }

    public static boolean usernameExists(String username) {
        JSONArray currentInfo = UserSession.getInstance().getCurrentInfo();
        for (int i = 0; i < currentInfo.size(); i++) {
            JSONObject user = currentInfo.getJSONObject(i);
            if (user.getString("username").equals(username)) {
                return true;
            }
        }
        return false;
    }
}