package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

import components.*;

/**
 * The TransactionPages class provides a panel for the user to view transactions.
 * Title : TransactionPages.java
 * Description:
 * The class provides a panel for the user to view transactions.
 */
public class TransactionPages extends JPanel {
    /**
     * Constructor of the TransactionPages class, initializes the panel layout,
     * adds a background image, sets the user session, and adds various buttons and labels.
     * It creates a panel for the user to view transactions.
     * @param void
     * @return void
     * @throws JSONException
     * @throws IOException
     */
    public TransactionPages(){
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);

        JSONArray transactionData = UserSession.getInstance().getCurrentTransaction();
        JSONObject CU = UserSession.getInstance().getCurrentUser();

        // Create column names
        String[] columnNames = {"Id", "Description", "Amount", "Time", "MemberName"};

        Object[][] data;
        if (CU.getBooleanValue("isParent")) {
            data = TransactionDatatoTable(transactionData, columnNames, true);
        } else {
            data = TransactionDatatoTable(transactionData, columnNames, false);
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        int[] columnWidths = {50, 250, 100, 250, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]); // Set column width
        }
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

        JLabel lblIntro = new JLabel("");
        if (CU.getBooleanValue("isParent")) {
            lblIntro.setText("Your and your child's transactions");
        } else {
            lblIntro.setText("Your own transactions");
        }
        Tools.setLabelProperties(lblIntro);
        gbc.gridy += 1;
        bgPanel.add(lblIntro, gbc);

        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridy += 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        bgPanel.add(scrollPane, gbc);

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
     * @param transactionData transaction data
     * @param columnNames column names
     * @param childShowAll whether to show all child transactions
     * @return Object[][] data
     */
    private Object[][] TransactionDatatoTable(JSONArray transactionData, String[] columnNames, boolean childShowAll) {
        JSONObject CU = UserSession.getInstance().getCurrentUser();
        String currentUsername = CU.getString("username");
        String childUsername = CU.getString("relatives");

        ArrayList<Object[]> dataList = new ArrayList<>();
        for (int i = 0; i < transactionData.size(); i++) {
            JSONObject transaction = transactionData.getJSONObject(i);
            String transactionMemberName = transaction.getString("transactionMemberName");
            if (transactionMemberName.equals(currentUsername) || 
                (childShowAll && transactionMemberName.equals(childUsername))) {
                Object[] data = new Object[columnNames.length];
                data[0] = transaction.getString("transactionId");
                data[1] = transaction.getString("transactionDescription");
                data[2] = transaction.getString("transactionAmount");
                data[3] = transaction.getString("transactionTime");
                data[4] = transaction.getString("transactionMemberName");
                dataList.add(data);
            }
        }

        Object[][] data = new Object[dataList.size()][columnNames.length];
        for (int i = 0; i < dataList.size(); i++) {
            data[i] = dataList.get(i);
        }

        return data;
    }

}
