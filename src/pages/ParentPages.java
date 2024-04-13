package pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.alibaba.fastjson.*;
import components.*;

public class ParentPages extends JPanel {
    public ParentPages() {
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

        JButton btnShowBalance = new JButton("show balance");
        gbc.gridy = 1;
        bgPanel.add(btnShowBalance, gbc);

        JButton btnSetTask = new JButton("Set Task");
        gbc.gridx = 1;
        bgPanel.add(btnSetTask, gbc);

        JButton btnBack = Tools.BackButton(this, new LoginWindow());
        gbc.gridx = 0;
        gbc.gridy = 2;
        bgPanel.add(btnBack, gbc);

        JButton btnExit = Tools.ExitButton();
        gbc.gridx = 1;
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
