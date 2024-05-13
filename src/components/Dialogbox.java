package components;

import java.awt.*;
import java.awt.event.*;

/**
 * The Dialogbox class provides a simple Dialogbox, user can defined their own Dialogbox.
 * 
 * Title : Dialogbox.java
 * 
 * Description:
 * This class includes a constructor that creates a Dialogbox with a specified title and message.
 * 
 * Constructor parameters:
 * - title: The title of the dialog box. It should be a string.
 * - message: The message to be displayed in the dialog box. It should also be a string.
 * 
 * Main methods:
 * - showDialog(): This method displays the dialog box on the screen.
 * - closeDialog(): This method closes the dialog box.
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
