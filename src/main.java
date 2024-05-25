import javax.swing.*;
import com.alibaba.fastjson.*;

import pages.*;
import components.*;

/**
 * The Main class provides the main function of the program.
 * Title : Main.java
 * Description:
 * The class provides the main function of the program.
 */
public class Main {
    /**
     * The main function of the program.
     * It creates a JFrame and adds the LoginWindow to it.
     * The program reads the user data, task data, and transaction data from the JSON files.
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Welcome to ChildBank");
        frame.setSize(960,540);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
        frame.add(loginWindow);
        frame.setVisible(true);

        JSONArray userData = Tools.ReadFromFile("data/user.json");
        UserSession.getInstance().setCurrentInfo(userData);
        JSONArray taskData = Tools.ReadFromFile("data/task.json");
        UserSession.getInstance().setCurrentTask(taskData);
        JSONArray jsonArray = Tools.ReadFromFile("data/transaction.json");
        UserSession.getInstance().setCurrentTransaction(jsonArray);
    }
}
