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
    /**
     * The constructor of the ParentPages class.
     * It creates a panel for the parent to choose an item.
     * Constructor of the ParentPages class, initializes the panel layout, adds a background image,
     *  sets the user session, adds various buttons and labels, and adds action listeners to the buttons.
     */
    public ParentPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        JSONObject CU = UserSession.getInstance().getCurrentUser();
        JSONObject AT = CU.getJSONObject("accountType");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Sets the spacing between components
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel lblTitle = new JLabel("Parent Pages");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        bgPanel.add(lblTitle, gbc);

        String Intro = "<html>Some Tips:"
            + "<br>Welcome using parent pages, you can use the function below:" + "</br>"
            + "</html>";
        JLabel lblIntro = new JLabel(Intro);
        Tools.setLabelProperties(lblIntro);
        gbc.gridy += 1;
        bgPanel.add(lblIntro, gbc);

        JLabel label = new JLabel("Choose a item");
        Tools.setLabelProperties(label);
        gbc.gridy += 1;
        bgPanel.add(label, gbc);

        JButton btnShowBalance = new JButton("Your balance");
        btnShowBalance.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(btnShowBalance, gbc);

        JButton btnSetTask = new JButton("Set Task");
        btnSetTask.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnSetTask, gbc);

        JButton btnChildInfo = new JButton("Child Info");
        btnChildInfo.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnChildInfo, gbc);

        JButton btnTransaction = new JButton("Transaction");
        btnTransaction.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnTransaction, gbc);

        JButton btnChangeSaving = new JButton("Change Saving");
        btnChangeSaving.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnChangeSaving, gbc);

        JButton btnBack = Tools.BackButton(this, new LoginWindow());
        btnBack.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx = 0;
        gbc.gridy += 1;
        bgPanel.add(btnBack, gbc);

        JButton btnExit = Tools.ExitButton();
        btnExit.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(btnExit, gbc);

        /**
         * When the "Show your balance" button is clicked, a message dialog will show the current balance of the parent.
         */
        btnShowBalance.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String message = "Your balance is: " 
                    + "\ncurrent: " + AT.getString("current") 
                    + "\nsaving: " + AT.getString("saving");
                JOptionPane.showMessageDialog(null, message);
            }
        });

        /**
         * When the "Set Task" button is clicked, the program will refresh the page to the SetTaskPages.
         */
        btnSetTask.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new SetTaskPages(), getParent());
            }
        });

        /**
         * When the "Transaction" button is clicked, the program will refresh the page to the TransactionPages.
         */
        btnTransaction.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Tools.RefreshPages(new TransactionPages(), getParent());
            }
        });

        /**
         * When the "Show child balance" button is clicked, a message dialog will show the current balance of the child.
         * If the child's account does not exist, a message dialog will show "Your child's account is not exist."
         * If the parent has no child, a message dialog will show "You have no child."
         */
        btnChildInfo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String relatives = CU.getString("relatives");
                JSONObject Cchild = null;
                int flag = 0;
                if (relatives == "")
                {
                    JOptionPane.showMessageDialog(null, "You have no child.");
                    return;
                } else {
                    JSONArray CI = UserSession.getInstance().getCurrentInfo();
                    for (int i = 0; i < CI.size(); i++) {
                        JSONObject user = CI.getJSONObject(i);
                        if (user.getString("username").equals(relatives)) {
                            Cchild = user;
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        JOptionPane.showMessageDialog(null, "Your child's account is not exist.");
                        return;
                    } else {
                        JSONObject at = Cchild.getJSONObject("accountType");
                        String message = "Your child's balance is: " 
                        + "\ncurrent: " + at.getInteger("current") 
                        + "\nsaving: " + at.getInteger("saving")
                        + "\nsaving goal: " + Cchild.getInteger("SavingGoals");
                        JOptionPane.showMessageDialog(null, message);                            
                    }
                
                }

            }
        });

        /**
         * When the "Change Saving" button is clicked, the ChangeSavingPages panel is displayed.
         */
        btnChangeSaving.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tools.RefreshPages(new ChangeSavingPages(), getParent());
            }
        });


    }
}
