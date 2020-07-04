package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.company.Model.Model.First.Enemy;
import com.company.Model.Model.First.GameState;
import com.company.Model.Model.First.Missile;
import com.company.Model.Model.First.PlayerCharacter;

/*
 * The panel in which the game is rendered.
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel {

    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";
    private GameState game;

    private boolean displayReload;
    private boolean gameOver;

    // Constructs a game panel
    // effects:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(GameState g) {
        setPreferredSize(new Dimension(GameState.WIDTH, GameState.HEIGHT));
        setBackground(Color.GRAY);
        this.game = g;
        displayReload = false;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

    }

    // Draws the game
    // modifies: g
    // effects:  draws the game onto g
    private void drawGame(Graphics g) {

        if (!gameOver) {
            drawCharacter(g);
            drawMissiles(g);
            drawEnemies(g);

            displayBullets(g);
            displayScore(g);

            if (displayReload) {
                Color savedCol = g.getColor();
                g.setColor(new Color(200, 250, 200));
                g.drawString("RELOADING", 700, 50);
                g.setColor(savedCol);
            }
        } else {
            gameOver(g);
        }


    }

    public void setGameOver() {
        gameOver = true;
    }

    public void setGameOverFalse() {
        gameOver = false;
    }

    public void drawReload() {
        Thread thread = new Thread() {
            public void run() {
                displayReload = true;
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    displayReload = false;
                    System.out.println(displayReload);
                    return;
                }

            }
        };
        thread.start();
    }



    private void displayBullets(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(new Color(200, 250, 200));
        g.drawString("" + (game.getPlayerCharacter().getAmmo() - game.getShotsSince()), 700, 80);
        g.setColor(savedCol);

    }


    private void displayScore(Graphics g) {

        Color savedCol = g.getColor();
        g.setColor(new Color(200, 200, 200));

        g.setFont(new Font("Arial", 20, 17));
        FontMetrics fm = g.getFontMetrics();

        centreString("SCORE", g, fm, 20);
        g.setFont(new Font("Arial", 20, 14));
        fm = g.getFontMetrics();
        centreString("" + game.getScore(), g, fm, 45);

        g.setColor(savedCol);
    }


    // Draw the tank
    // modifies: g
    // effects:  draws the tank onto g
    private void drawCharacter(Graphics g) {
        PlayerCharacter c = game.getPlayerCharacter();
        Color savedCol = g.getColor();
        g.setColor(PlayerCharacter.COLOR);
        g.fillRect(c.getX() - PlayerCharacter.SIZE_X / 2, c.getY()
                - PlayerCharacter.SIZE_Y / 2, PlayerCharacter.SIZE_X, PlayerCharacter.SIZE_Y);
        g.setColor(savedCol);
    }

    // Draws the missiles
    // modifies: g
    // effects:  draws the missiles onto g
    private void drawMissiles(Graphics g) {
        for(Missile next : game.getMissiles()) {
            drawMissile(g, next);
        }
    }

    // Draws a missile
    // modifies: g
    // effects:  draws the missile m onto g
    private void drawMissile(Graphics g, Missile m) {
        Color savedCol = g.getColor();
        g.setColor(Missile.COLOR);
        g.fillOval(m.getX() - Missile.SIZE_X / 2, m.getY() - Missile.SIZE_Y / 2, Missile.SIZE_X, Missile.SIZE_Y);
        g.setColor(savedCol);
    }



    // Draws the missiles
    // modifies: g
    // effects:  draws the missiles onto g
    private void drawEnemies(Graphics g) {
        for(Enemy next : game.getEnemies()) {
            drawEnemy(g, next);
        }
    }

    // Draws a missile
    // modifies: g
    // effects:  draws the missile m onto g
    private void drawEnemy(Graphics g, Enemy e) {
        Color savedCol = g.getColor();
        g.setColor(e.getCOLOR());
        g.fillOval(e.getX() - e.getHitBoxSize() / 2, e.getY() - e.getHitBoxSize() / 2, e.getHitBoxSize(), e.getHitBoxSize());
        g.setColor(savedCol);
    }



    // Draws the "game over" message and replay instructions
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color( 0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString("SCORE : " + game.getScore(), g, fm, GameState.HEIGHT / 3);
        centreString(OVER, g, fm, GameState.HEIGHT / 2);
        centreString(REPLAY, g, fm, GameState.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (GameState.WIDTH - width) / 2, yPos);
    }
}
