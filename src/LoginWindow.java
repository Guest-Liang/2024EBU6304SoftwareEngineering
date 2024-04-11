import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.alibaba.fastjson.*;

public class LoginWindow extends Frame implements ActionListener
{
    Label lblUsername;
    TextField txtUsername;
    Label lblPassword;
    TextField txtPassword;
    Button btnLogin;

    public LoginWindow()
    {
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();

        lblUsername = new Label("Username:");
        txtUsername = new TextField(20);
        lblPassword = new Label("Password:");
        txtPassword = new TextField(20);
        txtPassword.setEchoChar('*');
        btnLogin = new Button("Login");

        btnLogin.addActionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblUsername, gbc);

        gbc.gridx = 1;
        add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblPassword, gbc);

        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(btnLogin, gbc);

        setTitle("ChildBank");
        setSize(640, 480);
        setLocationRelativeTo(null);
        setVisible(true);

        addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) { System.exit(0); } });
    }

    public void actionPerformed(ActionEvent ae)
    {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
    
        try {
            // 读取data/user.json文件
            String content = "";
            content = new String(Files.readAllBytes(Paths.get("data/user.json")));
            // System.out.println(content);
            JSONArray jsonArray = JSONArray.parseArray(content);
            boolean loginSuccess = false;
            for (int i = 0; i < jsonArray.size(); i++)
            {
                JSONObject userJson = jsonArray.getJSONObject(i);
                
                // 获取用户名和密码
                String correctUsername = userJson.getString("username");
                String correctPassword = userJson.getString("password");
        
                // 比较用户名和密码
                if (username.equals(correctUsername) && password.equals(correctPassword))
                {
                    loginSuccess = true;
                    break;
                }
            }

            Dialog dialog = new Dialog(new Frame(), "Login Result", true);
            dialog.setLayout(new FlowLayout());
            Label label;
            if (loginSuccess) { label = new Label("login success!"); }
            else { label = new Label("login failed!"); }
            dialog.add(label);
            dialog.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) { dialog.dispose(); } });
            dialog.setSize(200, 100);
            dialog.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        new LoginWindow();
    }
}
