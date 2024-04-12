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
 * The class has methods to set properties of components.
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class Tools {
    private static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 20);
    private static final Color DEFAULT_FOREGROUND = Color.WHITE;
    private static final boolean DEFAULT_OPAQUE = true;
    private static final Color DEFAULT_BACKGROUND = new Color(0, 0, 0, 64);

    public static void setLabelProperties(JLabel label) {
        setLabelProperties(label, DEFAULT_FONT, DEFAULT_FOREGROUND, DEFAULT_OPAQUE, DEFAULT_BACKGROUND);
    }

    public static void setLabelProperties(JLabel label, Font font) {
        setLabelProperties(label, font, DEFAULT_FOREGROUND, DEFAULT_OPAQUE, DEFAULT_BACKGROUND);
    }

    public static void setLabelProperties(JLabel label, Font font, Color foreground) {
        setLabelProperties(label, font, foreground, DEFAULT_OPAQUE, DEFAULT_BACKGROUND);
    }

    public static void setLabelProperties(JLabel label, Font font, Color foreground, boolean opaque) {
        setLabelProperties(label, font, foreground, opaque, DEFAULT_BACKGROUND);
    }

    public static void setLabelProperties(JLabel label, Font font, Color foreground, boolean opaque, Color background) {
        label.setFont(font);
        label.setForeground(foreground);
        label.setOpaque(opaque);
        label.setBackground(background);
    }

    public static void RefreshPages(JPanel newPage, Container container)
    {
        container.removeAll();
        container.repaint();
        container.add(newPage);
        container.revalidate();
    }

    /**
     * Create a button that exits the program when clicked.
     * @param void
     * @return JButton
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

    public static void WriteToFile(String filePath, JSONArray jsonArray) throws IOException {
        String jsonString = JSON.toJSONString(jsonArray, SerializerFeature.PrettyFormat);
        Files.write(Paths.get(filePath), jsonString.getBytes());
    }
}
