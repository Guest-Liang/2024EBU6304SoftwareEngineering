package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

import components.*;

/**
 * The TransactionPages class provides a panel for the user to view transactions.
 * Title : TransactionPages.java
 * Description:
 * The class provides a panel for the user to view transactions.
 */
public class TransactionPages extends JPanel {
    public TransactionPages(){
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        JSONArray transactionData = UserSession.getInstance().getCurrentTransaction();

        // Create column names
        String[] columnNames = {"Id", "Description", "Amount", "Time", "MemberName"};

        Object[][] data = TransactionDatatoTable(transactionData, columnNames);
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getTableHeader().setReorderingAllowed(false); // Disable column reordering
        table.setFillsViewportHeight(false); // Disable auto resizing
        table.setPreferredScrollableViewportSize(new Dimension(800, 250));
        // Disable column resizing
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setResizable(false);
        }
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel lblTitle = new JLabel("Transaction Pages");
        Tools.setLabelProperties(lblTitle);
        lblTitle.setFont(Tools.DEFAULT_TITLE_FONT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        bgPanel.add(lblTitle, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridy += 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        bgPanel.add(scrollPane, gbc);

        JSONObject CU = UserSession.getInstance().getCurrentUser();
        JButton backButton;
        if (CU.getBoolean("isParent")) {
            backButton = Tools.BackButton(this, new ParentPages());
        } else {
            backButton = Tools.BackButton(this, new ChildPages());
        }
        backButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridy += 1;
        gbc.gridwidth = 1;
        bgPanel.add(backButton, gbc);

        JButton exitButton = Tools.ExitButton();
        exitButton.setFont(Tools.DEFAULT_BUTTON_FONT);
        gbc.gridx += 1;
        bgPanel.add(exitButton, gbc);

    }

    /**
     * Convert transaction data to table
     * @param transactionData
     * @param columnNames
     * @return
     */
    private Object[][] TransactionDatatoTable(JSONArray transactionData, String[] columnNames) {
        Object[][] data = new Object[transactionData.size()][columnNames.length];
        for (int i = 0; i < transactionData.size(); i++) {
            JSONObject transaction = transactionData.getJSONObject(i);
            data[i][0] = transaction.getString("transactionId");
            data[i][1] = transaction.getString("transactionDescription");
            data[i][2] = transaction.getString("transactionAmount");
            data[i][3] = transaction.getString("transactionTime");
            data[i][4] = transaction.getString("transactionMemberName");
        }
        return data;
    }

}
