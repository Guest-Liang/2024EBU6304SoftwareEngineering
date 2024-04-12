import javax.swing.*;
import pages.*;

public class main{
	public static void main(String[] args) {
        JFrame frame = new JFrame("Welcome to ChildBank");
        frame.setSize(960,540);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);
        frame.add(loginWindow);
        frame.setVisible(true);
	}
}
