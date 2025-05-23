package pages;

import java.awt.*;
import java.awt.event.*;
import com.alibaba.fastjson.*;
import javax.swing.*;
import java.util.*;

import components.*;

/**
 * The LoginWindow class provides a panel for the user to login.
 * Title : LoginWindow.java
 * Description:
 * The class provides a panel for the user to login.
 */
public class LoginWindow extends JPanel {
    /**
     * The constructor of the LoginWindow class.
     * It creates a panel for the user to login.
     * @throws JSONException if the JSON is invalid
     */
    public LoginWindow() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4); // Sets the spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitle = new JLabel("Welcome using ChildBank");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(lblTitle, gbc);

        // Create the components
        JLabel lblUsername = new JLabel("Username:");
        Tools.setLabelProperties(lblUsername);
        JTextField txtUsername = new JTextField(20);
        txtUsername.setFont(Tools.DEFAULT_BUTTON_FONT);
        txtUsername.setPreferredSize(new Dimension(200, 30));
        gbc.gridwidth = 1;
        gbc.gridy += 1;
        bgPanel.add(lblUsername, gbc);
        gbc.gridx += 1;
        bgPanel.add(txtUsername, gbc);

        JLabel lblPassword = new JLabel("Password:");
        Tools.setLabelProperties(lblPassword);
        JPasswordField txtPassword = new JPasswordField(20);
        txtPassword.setFont(Tools.DEFAULT_BUTTON_FONT);
        txtPassword.setPreferredSize(new Dimension(200, 30));
        txtPassword.setEchoChar('*');
        gbc.gridx = 0;
        gbc.gridy += 1;
        bgPanel.add(lblPassword, gbc);
        gbc.gridx += 1;
        bgPanel.add(txtPassword, gbc);

        JButton btnRegister = new JButton("Register");
        btnRegister.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bgPanel.add(btnRegister, gbc);
        /**
         * Register button action listener
         */
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tools.RefreshPages(new RegisterPages(), getParent());
            }
        });

        JButton btnLogin = new JButton("Login");
        btnLogin.setFont(Tools.DEFAULT_BUTTON_FONT);
        /**
         * When the login button is clicked, the program will check the username and password.
         * If the username and password are correct, the program will show a message dialog with "login success!".
         * If the username and password are incorrect, the program will show a message dialog with "login fail!".
         */
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String username = txtUsername.getText();
                char[] passwordChars = txtPassword.getPassword();
                String password = new String(passwordChars);
                Arrays.fill(passwordChars, '0');
                JSONArray jsonArray = UserSession.getInstance().getCurrentInfo();
                // System.out.println(jsonArray);
                boolean loginSuccess = false;
                
                for (int i = 0; i < jsonArray.size(); i++)
                {
                    JSONObject userJson = jsonArray.getJSONObject(i);
                    String correctUsername = userJson.getString("username");
                    String correctPassword = userJson.getString("password");
            
                    if (username.equals(correctUsername) && password.equals(correctPassword))
                    {
                        loginSuccess = true;
                        UserSession.getInstance().setCurrentUser(userJson);
                        break;
                    }
                }

                if (loginSuccess) { 
                    if (UserSession.getInstance().getCurrentUser().getBoolean("isParent")) {
                        JOptionPane.showMessageDialog(null, "login success! As " + username + " (Parent)"); 
                        Tools.RefreshPages(new ParentPages(), getParent());
                    } else {
                        JOptionPane.showMessageDialog(null, "login success! As " + username + " (Child)"); 
                        Tools.RefreshPages(new ChildPages(), getParent());
                    }
                } else { 
                    JOptionPane.showMessageDialog(null, "login fail!"); 
                }
            }
        });
        gbc.gridx += 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        bgPanel.add(btnLogin, gbc);

        JButton btnExit = Tools.ExitButton();
        btnExit.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx = 0;
        gbc.gridy += 1;
        gbc.gridwidth = 2;
        bgPanel.add(btnExit, gbc);

        setVisible(true);
        requestFocus();
        
    }
}
