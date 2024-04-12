package components;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The BackgroundImagePanel class provides a JPanel with a background image.
 * Title : BackgroundImagePanel.java
 * Description:
 * The class has a constructor that creates a JPanel with a background image.
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class BackgroundImagePanel extends JPanel
{
    private Image background;

    public BackgroundImagePanel(String imagePath)
    {
        try {
            background = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
    }
    
    // Paint the background image
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
