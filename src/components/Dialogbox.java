package components;

import java.awt.*;
import java.awt.event.*;

/**
 * The Dialogbox class provides a simple Dialogbox, user can defined their own Dialogbox.
 * Title : Dialogbox.java
 * Description:
 * The class has a constructor that creates a Dialogbox with the title and tips to show.
 * @author Liang Zheyu
 * @version 0.0.1
 */
public class Dialogbox
{
    public Dialogbox(String title, String tips)
    {
        Dialog dialog = new Dialog(new Frame(), title, true);
        dialog.setLayout(new FlowLayout());
        Label label;
        label = new Label(tips);
        dialog.add(label);
        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent we) { dialog.dispose(); } });
        dialog.setSize(200, 100);
        dialog.setVisible(true);
    }
}
