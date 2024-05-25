package components.test;

import components.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import com.alibaba.fastjson.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;

/**
 * The ToolsTest class contains the unit tests for the Tools class.
 */
public class ToolsTest {

    /**
     * Test the setLabelProperties method.
     */
    @Test
    public void testSetLabelProperties() {
        JLabel label = new JLabel();
        Tools.setLabelProperties(label);

        assertEquals(Tools.DEFAULT_BUTTON_FONT, label.getFont());
        assertEquals(Tools.DEFAULT_FOREGROUND, label.getForeground());
        assertEquals(Tools.DEFAULT_OPAQUE, label.isOpaque());
        assertEquals(Tools.DEFAULT_BACKGROUND, label.getBackground());
    }

    /**
     * Test the setLabelProperties method with font.
     */
    @Test
    public void testSetLabelPropertiesWithFont() {
        JLabel label = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 20);
        Tools.setLabelProperties(label, font);

        assertEquals(font, label.getFont());
        assertEquals(Tools.DEFAULT_FOREGROUND, label.getForeground());
        assertEquals(Tools.DEFAULT_OPAQUE, label.isOpaque());
        assertEquals(Tools.DEFAULT_BACKGROUND, label.getBackground());
    }

    /**
     * Test the setLabelProperties method with font and foreground.
     */
    @Test
    public void testSetLabelPropertiesWithFontAndForeground() {
        JLabel label = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 20);
        Color foreground = Color.BLACK;
        Tools.setLabelProperties(label, font, foreground);

        assertEquals(font, label.getFont());
        assertEquals(foreground, label.getForeground());
        assertEquals(Tools.DEFAULT_OPAQUE, label.isOpaque());
        assertEquals(Tools.DEFAULT_BACKGROUND, label.getBackground());
    }

    /**
     * Test the setLabelProperties method with font, foreground, and opaque.
     */
    @Test
    public void testSetLabelPropertiesWithFontForegroundAndOpaque() {
        JLabel label = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 20);
        Color foreground = Color.BLACK;
        boolean opaque = false;
        Tools.setLabelProperties(label, font, foreground, opaque);

        assertEquals(font, label.getFont());
        assertEquals(foreground, label.getForeground());
        assertEquals(opaque, label.isOpaque());
        assertEquals(Tools.DEFAULT_BACKGROUND, label.getBackground());
    }

    /**
     * Test the setLabelProperties method with font, foreground, opaque, and background.
     */
    @Test
    public void testSetLabelPropertiesWithFontForegroundOpaqueAndBackground() {
        JLabel label = new JLabel();
        Font font = new Font("Arial", Font.PLAIN, 20);
        Color foreground = Color.BLACK;
        boolean opaque = false;
        Color background = new Color(0, 0, 0, 64);
        Tools.setLabelProperties(label, font, foreground, opaque, background);

        assertEquals(font, label.getFont());
        assertEquals(foreground, label.getForeground());
        assertEquals(opaque, label.isOpaque());
        assertEquals(background, label.getBackground());
    }

    /**
     * Test the refreshPages method.
     */
    @Test
    public void testRefreshPages() {
        JPanel newPage = new JPanel();
        Container container = new Container();
        container.add(new JPanel());
        Tools.RefreshPages(newPage, container);

        assertEquals(1, container.getComponentCount());
        assertEquals(newPage, container.getComponent(0));
    }

    /**
     * Test the ExitButton method.
     */
    @Test
    public void testExitButton() {
        JButton btnReturn = Tools.ExitButton();
        assertEquals("Exit", btnReturn.getText());
        assertEquals(SwingConstants.CENTER, btnReturn.getHorizontalAlignment());
        assertEquals(1, btnReturn.getActionListeners().length);
    }
    
    /**
     * Test the BackButton method.
     */
    @Test
    public void testBackButton() {
        Container container = new Container();
        JPanel newPage = new JPanel();
        JButton backButton = Tools.BackButton(container, newPage);

        assertEquals("Back", backButton.getText());
        assertEquals(1, backButton.getActionListeners().length);

        backButton.getActionListeners()[0].actionPerformed(new ActionEvent(backButton, ActionEvent.ACTION_PERFORMED, null));

        assertEquals(1, container.getComponentCount());
        assertEquals(newPage, container.getComponent(0));
    }

    /**
     * Test the WriteToFile method.
     * @throws IOException If an I/O error occurs reading from the stream
     */
    @Test
    public void testWriteToFile() throws IOException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("Test1");
        jsonArray.add("Test2");

        String filePath = "testFile.json";
        Tools.WriteToFile(filePath, jsonArray);

        String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        JSONArray readJsonArray = JSON.parseArray(content);

        assertEquals(jsonArray, readJsonArray);

        // Clean up the test file
        Files.deleteIfExists(Paths.get(filePath));
    }

    /**
     * Test the SaveUserInfo method.
     * @throws IOException If an I/O error occurs reading from the stream
     */
    @Test
    public void testSaveUserInfo() throws IOException {
        // Prepare the UserSession
        UserSession userSession = UserSession.getInstance();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("Test1");
        jsonArray.add("Test2");
        userSession.setCurrentInfo(jsonArray);
    
        // Backup user.json
        Files.copy(Paths.get("data/user.json"), Paths.get("data/user_backup.json"), StandardCopyOption.REPLACE_EXISTING);
    
        // Call the method to test
        Tools.SaveUserInfo();
    
        // Verify the result
        String content = new String(Files.readAllBytes(Paths.get("data/user.json")), StandardCharsets.UTF_8);
        JSONArray readJsonArray = JSON.parseArray(content);
    
        assertEquals(jsonArray, readJsonArray);
    
        // Restore user.json from backup
        Files.copy(Paths.get("data/user_backup.json"), Paths.get("data/user.json"), StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(Paths.get("data/user_backup.json"));
    }

    /**
     * Test the SaveCU2CI method.
     */
    @Test
    public void testSaveCU2CI() {
        // Prepare the UserSession
        UserSession userSession = UserSession.getInstance();
        JSONArray currentInfo = new JSONArray();
        JSONObject user1 = new JSONObject();
        user1.put("username", "user1");
        currentInfo.add(user1);
        JSONObject user2 = new JSONObject();
        user2.put("username", "user2");
        currentInfo.add(user2);
        userSession.setCurrentInfo(currentInfo);
    
        JSONObject currentUser = new JSONObject();
        currentUser.put("username", "user1");
        currentUser.put("email", "user1@test.com");
        userSession.setCurrentUser(currentUser);
    
        // Call the method to test
        Tools.SaveCU2CI();
    
        // Verify the result
        JSONArray updatedCurrentInfo = userSession.getCurrentInfo();
        JSONObject updatedUser1 = updatedCurrentInfo.getJSONObject(0);
        assertEquals("user1", updatedUser1.getString("username"));
        assertEquals("user1@test.com", updatedUser1.getString("email"));
        JSONObject updatedUser2 = updatedCurrentInfo.getJSONObject(1);
        assertEquals("user2", updatedUser2.getString("username"));
        assertFalse(updatedUser2.containsKey("email"));
    }

    /**
     * Test the ReadFromFile method.
     * @throws IOException If an I/O error occurs reading from the stream
     */
    @Test
    public void testReadFromFile() throws IOException {
        // Prepare a test file
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("Test1");
        jsonArray.add("Test2");
        String filePath = "testFile.json";
        Files.writeString(Paths.get(filePath), jsonArray.toJSONString());
    
        // Call the method to test
        JSONArray readJsonArray = Tools.ReadFromFile(filePath);
    
        // Verify the result
        assertEquals(jsonArray, readJsonArray);
    
        // Clean up the test file
        Files.deleteIfExists(Paths.get(filePath));
    }

    /**
     * Test the ReadUserInfo method.
     * @throws IOException If an I/O error occurs reading from the stream
     */
    @Test
    public void testSaveTaskInfo() throws IOException {
        // Prepare the UserSession
        UserSession userSession = UserSession.getInstance();
        JSONArray currentTask = new JSONArray();
        currentTask.add("TestTask1");
        currentTask.add("TestTask2");
        userSession.setCurrentTask(currentTask);
    
        // Backup task.json
        Files.copy(Paths.get("data/task.json"), Paths.get("data/task_backup.json"), StandardCopyOption.REPLACE_EXISTING);
    
        // Call the method to test
        Tools.SaveTaskInfo();
    
        // Verify the result
        String content = new String(Files.readAllBytes(Paths.get("data/task.json")), StandardCharsets.UTF_8);
        JSONArray readTask = JSON.parseArray(content);
    
        assertEquals(currentTask, readTask);
    
        // Restore task.json from backup
        Files.copy(Paths.get("data/task_backup.json"), Paths.get("data/task.json"), StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(Paths.get("data/task_backup.json"));
    }

    /**
     * Test the GetCurrentTime method.
     */
    @Test
    public void testGetCurrentTime() {
        // Call the method to test
        String currentTime = Tools.getCurrentTime();
    
        // Verify the result
        assertNotNull(currentTime);
        assertTrue(currentTime.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}"));
    }

    /**
     * Test the SaveTransactionInfo method.
     * @throws IOException If an I/O error occurs reading from the stream
     */
    @Test
    public void testSaveTransactionInfo() throws IOException {
        // Prepare the UserSession
        UserSession userSession = UserSession.getInstance();
        JSONArray currentTransaction = new JSONArray();
        currentTransaction.add("TestTransaction1");
        currentTransaction.add("TestTransaction2");
        userSession.setCurrentTransaction(currentTransaction);
    
        // Backup transaction.json
        Files.copy(Paths.get("data/transaction.json"), Paths.get("data/transaction_backup.json"), StandardCopyOption.REPLACE_EXISTING);
    
        // Call the method to test
        Tools.SaveTransactionInfo();
    
        // Verify the result
        String content = new String(Files.readAllBytes(Paths.get("data/transaction.json")), StandardCharsets.UTF_8);
        JSONArray readTransaction = JSON.parseArray(content);
    
        assertEquals(currentTransaction, readTransaction);
    
        // Restore transaction.json from backup
        Files.copy(Paths.get("data/transaction_backup.json"), Paths.get("data/transaction.json"), StandardCopyOption.REPLACE_EXISTING);
        Files.deleteIfExists(Paths.get("data/transaction_backup.json"));
    }


}