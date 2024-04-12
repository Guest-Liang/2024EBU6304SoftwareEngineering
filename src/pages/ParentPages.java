package pages;

import javax.swing.*;
import com.alibaba.fastjson.*;

import java.awt.*;
import java.awt.event.*;

import components.*;

public class ParentPages extends JPanel {
    public ParentPages() {
        setLayout(new BorderLayout());
        BackgroundImagePanel bgPanel = new BackgroundImagePanel("data/bg.jpg");
        bgPanel.setLayout(new GridBagLayout());
        add(bgPanel, BorderLayout.CENTER);
    }
}
