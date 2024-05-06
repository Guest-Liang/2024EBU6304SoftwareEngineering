package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.alibaba.fastjson.*;

import components.*;

/**
 * The ParentPages class provides a panel for the parent to choose an item.
 * Title : ParentPages.java
 * Description:
 * The class provides a panel for the parent to choose an item.
 */
public class ParentPages extends JPanel {
    public ParentPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components

        JLabel lblTitle = new JLabel("Parent Pages");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(new Font("Arial", Font.PLAIN, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(lblTitle, gbc);

        JLabel label = new JLabel("Choose a item");
        Tools.setLabelProperties(label);
        gbc.gridy += 1;
        bgPanel.add(label, gbc);

        JButton btnShowBalance = new JButton("show balance");
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnShowBalance, gbc);

        JButton btnSetTask = new JButton("Set Task");
        gbc.gridx += 1;
        bgPanel.add(btnSetTask, gbc);

        JButton btnBack = Tools.BackButton(this, new LoginWindow());
        gbc.gridx = 0;
        gbc.gridy += 1;
        bgPanel.add(btnBack, gbc);

        JButton btnExit = Tools.ExitButton();
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

        btnSetTask.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new SetTaskPages(), getParent());
            }
        });

    }
}
