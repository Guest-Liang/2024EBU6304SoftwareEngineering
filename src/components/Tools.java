package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * The Tools class provides methods to set properties of components.
 * Title : Tools.java
 * Description:
 * This class contains a series of methods that can be used to set properties of various components, such as JLabel, JButton, etc.
 * These methods can set properties of components such as font, color, background, etc., and can also add event listeners such as click events.
 * In addition, this class also provides some utility methods, such as reading and writing files, getting the current time, etc.
 */
public class Tools {
    /**
     * Default button font for components.
     */
    public static final Font DEFAULT_BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);
    /**
     * Default title font for components.
     */
    public static final Font DEFAULT_TITLE_FONT = new Font("Arial", Font.PLAIN, 40);
    /**
     * Default foreground color for components.
     */
    public static final Color DEFAULT_FOREGROUND = Color.WHITE;
    /**
     * Default opaque property for components.
     */
    public static final boolean DEFAULT_OPAQUE = true;
    /**
     * Default background color for components.
     */
    public static final Color DEFAULT_BACKGROUND = new Color(0, 0, 0, 64);

    /**
     * Set the properties of a JButton component.
     * @param label JLabel component that needs to be set
     */
    public static void setLabelProperties(JLabel label) {
        setLabelProperties(label, DEFAULT_BUTTON_FONT, DEFAULT_FOREGROUND, DEFAULT_OPAQUE, DEFAULT_BACKGROUND);
    }

    /**
     * Set the properties of a JButton component.
     * @param label JLabel component that needs to be set
     * @param font Font of the button
     */
    public static void setLabelProperties(JLabel label, Font font) {
        setLabelProperties(label, font, DEFAULT_FOREGROUND, DEFAULT_OPAQUE, DEFAULT_BACKGROUND);
    }

    /**
     * Set the properties of a JButton component.
     * @param label JLabel component that needs to be set
     * @param font Font of the button
     * @param foreground Foreground color of the button
     */
    public static void setLabelProperties(JLabel label, Font font, Color foreground) {
        setLabelProperties(label, font, foreground, DEFAULT_OPAQUE, DEFAULT_BACKGROUND);
    }

    /**
     * Set the properties of a JButton component.
     * @param label JLabel component that needs to be set
     * @param font Font of the button
     * @param foreground Foreground color of the button
     * @param opaque Whether the button is opaque
     */
    public static void setLabelProperties(JLabel label, Font font, Color foreground, boolean opaque) {
        setLabelProperties(label, font, foreground, opaque, DEFAULT_BACKGROUND);
    }

    /**
     * Set the properties of a JLabel component.
     * @param label JLabel component that needs to be set
     * @param font Font of the label
     * @param foreground Foreground color of the label
     * @param opaque Whether the label is opaque
     * @param background Background color of the label
     */
    public static void setLabelProperties(JLabel label, Font font, Color foreground, boolean opaque, Color background) {
        label.setFont(font);
        label.setForeground(foreground);
        label.setOpaque(opaque);
        label.setBackground(background);
    }

    /**
     * Refesh the page, add a new page to the container.
     * @param newPage new page to be added
     * @param container container to add the new page
     */
    public static void RefreshPages(JPanel newPage, Container container)
    {
        container.removeAll();
        container.repaint();
        container.add(newPage);
        container.revalidate();
    }

    /**
     * Create a button that exits the program when clicked.
     * @return JButton that exits the program when clicked
     */
    public static JButton ExitButton() {
        JButton btnReturn = new JButton("Exit");
        btnReturn.setHorizontalAlignment(SwingConstants.CENTER);
        btnReturn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        return btnReturn;
    }

    /**
     * Create a button that returns to the previous page when clicked.
     * Description:
     * This method creates a button that returns to the previous page when clicked.
     * @param container container to add the new page
     * @param newPage new page to be added
     * @return JButton that returns to the previous page when clicked
     */
    public static JButton BackButton(Container container, JPanel newPage) {
        JButton btnReturn = new JButton("Back");
        btnReturn.setHorizontalAlignment(SwingConstants.CENTER);
        btnReturn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                RefreshPages(newPage, container);
            }
        });
        return btnReturn;
    }

    /**
     * Write JSONArray data to a file.
     * @param filePath file path
     * @param jsonArray JSONArray data to be written to the file
     * @throws IOException if an I/O error occurs when writing to the file
     */
    public static void WriteToFile(String filePath, JSONArray jsonArray) throws IOException {
        String jsonString = JSON.toJSONString(jsonArray, SerializerFeature.PrettyFormat);
        Files.write(Paths.get(filePath), jsonString.getBytes());
    }

    /**
     * Saves the current user's information to a file.
     * This method finds the current user in the currentInfo array and replaces it with the updated user information.
     * It then writes the updated currentInfo array back to the file.
     * If the write operation is successful, a success message is displayed.
     * If the write operation fails, an error message is displayed and the exception is printed to the console.
     */
    public static void SaveUserInfo() {
        JSONArray currentInfo = UserSession.getInstance().getCurrentInfo();
        try {
            WriteToFile("data/user.json", currentInfo);
            JOptionPane.showMessageDialog(null, "Success. UserInfo has saved.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "write to file failed!");
        }
    }

    /**
     * Save the current user's information to the current user in the currentInfo array.
     * This method finds the current user in the currentInfo array and replaces it with the updated user information.
     */
    public static void SaveCU2CI() {
        JSONArray currentInfo = UserSession.getInstance().getCurrentInfo();
        JSONObject currentUser = UserSession.getInstance().getCurrentUser();
        for (int i = 0; i < currentInfo.size(); i++) {
            JSONObject user = currentInfo.getJSONObject(i);
            if (user.getString("username").equals(currentUser.getString("username"))) {
                currentInfo.set(i, currentUser);
                break;
            }
        }
    }

    /**
     * Read data from a file and return it as a JSONArray.
     * @param filePath file path
     * @return JSONArray data read from the file
     */
    public static JSONArray ReadFromFile(String filePath) {
        try {
            String jsonString = Files.readString(Paths.get(filePath));
            return JSON.parseArray(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "read from file failed!");
            return new JSONArray();
        }
    }

    /**
     * Saves the current task information to a file.
     * This method writes the currentTask array to the file.
     * If the write operation is successful, a success message is displayed.
     * If the write operation fails, an error message is displayed and the exception is printed to the console.
     */
    public static void SaveTaskInfo() {
        JSONArray currentTask = UserSession.getInstance().getCurrentTask();
        try {
            WriteToFile("data/task.json", currentTask);
            JOptionPane.showMessageDialog(null, "Success. TaskInfo has saved.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "write to file failed!");
        }
    }

    /**
     * Get the current time in the format "yyyy-MM-dd HH:mm:ss".
     * @return String current time in the format "yyyy-MM-dd HH:mm:ss"
     */
    public static String getCurrentTime() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }

    /**
     * Saves the current transaction information to a file.
     * This method writes the currentTransaction array to the file.
     * If the write operation is successful, a success message is displayed.
     * If the write operation fails, an error message is displayed and the exception is printed to the console.
     */
    public static void SaveTransactionInfo() {
        JSONArray currentTransaction = UserSession.getInstance().getCurrentTransaction();
        try {
            WriteToFile("data/transaction.json", currentTransaction);
            JOptionPane.showMessageDialog(null, "Success. TransactionInfo has saved.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "write to file failed!");
        }
    }
}
