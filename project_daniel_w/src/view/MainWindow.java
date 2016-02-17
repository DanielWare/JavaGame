package view;

import controller.ButtonListener;
import controller.KeyController;
import controller.Main;
import controller.MouseController;
import controller.MouseMovementListener;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

    public static JButton startButton;
    public static JButton quitButton;
    public static JTextField enemyStats;
    public static JTextField powerStats;


    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");

        JPanel southPanel = new JPanel();
        startButton = new JButton("Start");
        southPanel.add(startButton);
        quitButton = new JButton("Quit");
        southPanel.add(quitButton);
        c.add(southPanel, "South");

        ButtonListener buttonListener = new ButtonListener();
        startButton.addActionListener(buttonListener);
        quitButton.addActionListener(buttonListener);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2,1));
        northPanel.add(enemyStats = new JTextField());
        northPanel.add(powerStats = new JTextField());
        enemyStats.setEditable(false);
        powerStats.setEditable(false);
        enemyStats.setFocusable(false);
        powerStats.setFocusable(false);
        c.add(northPanel, "North");
        
        MouseController mouseController = new MouseController();
        Main.gamePanel.addMouseListener(mouseController);

        KeyController keyListener = new KeyController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"
        startButton.setFocusable(false);
        quitButton.setFocusable(false);
        
        MouseMovementListener mouseMovement = new MouseMovementListener();
        Main.gamePanel.addMouseMotionListener(mouseMovement);
        
    }

}
