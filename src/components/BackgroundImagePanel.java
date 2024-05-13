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
 */
public class BackgroundImagePanel extends JPanel
{
    private Image background;

    /**
     * The constructor of the BackgroundImagePanel class.
     * It creates a JPanel with a background image.
     * @param imagePath The path of the image file
     */
    public BackgroundImagePanel(String imagePath)
    {
        try {
            background = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        setLayout(new BorderLayout());
    }
    
    /**
     * The paintComponent method is overridden to draw the background image.
     * @param g The Graphics object
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}
