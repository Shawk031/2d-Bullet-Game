package UI;

import com.company.Model.Model.First.GameState;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.Timer;


public class Main extends JFrame {

    private static final int INTERVAL = 20;
    private GameState game;
    private GamePanel gp;

    //comment

    public Main() {
        super("Survival Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new GameState();
        gp = new GamePanel(game);
        game.setGamePanel(gp);
        add(gp);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();

    }




    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update();
                gp.repaint();
            }
        });

        t.start();
    }

    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }


    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}
































