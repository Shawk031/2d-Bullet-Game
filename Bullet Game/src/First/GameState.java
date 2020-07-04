package First;

import com.company.Model.Model.UI.GamePanel;
import com.sun.xml.internal.stream.events.EndElementEvent;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private List<Missile> missiles;
    private int shotsSince;
    private List<Enemy> enemies;

    private int score;

    private Thread thread;
    boolean firstReloadThread;
    private GamePanel p;

    private boolean gameOver;


    private PlayerCharacter character;

    public GameState() {
        character = new PlayerCharacter();
        missiles = new ArrayList<>();
        enemies = new ArrayList<>();
        shotsSince = 0;
        score = 0;
        firstReloadThread = true;
        gameOver = false;
        setUp();
    }

    public void setGamePanel(GamePanel p) {
        this.p = p;
    }

    public PlayerCharacter getPlayerCharacter() {
        return character;
    }

    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            fireMissile(0);
        } else if (keyCode == KeyEvent.VK_X) {
            System.exit(0);
        } else if (gameOver && keyCode == KeyEvent.VK_R) {
            gameOver = false;
            p.setGameOverFalse();
            setUp();
        } else if (keyCode == 74 || keyCode == 75 || keyCode == 76 || keyCode == 73) {
            fireMissile(keyCode);
        }
        else {
            characterControl(keyCode);
        }
    }

    public int getScore() { return score; }

    // keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT ||
    // keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT ||
    //keyCode == KeyEvent.VK_KP_UP || keyCode == KeyEvent.VK_UP ||
    //keyCode == KeyEvent.VK_KP_DOWN || keyCode == KeyEvent.VK_DOWN ||

    private void characterControl(int keyCode) {
        if (keyCode == 65) {
            character.moveLeft();
        } else if (keyCode == 68) {
            character.moveRight();
        } else if (keyCode == 87) {
            character.moveUp();
        } else if (keyCode == 83) {
            character.moveDown();
        }
    }


    public void update() {
       // character.move();

        moveMissiles();
        if (Math.random() > 0.975) {
            enemies.add(new Enemy());
        }
        checkMissiles();
        checkReload();

        enemiesTakeDamage();
        checkEnemyDeath();
        moveEnemies();
        checkUserDeath();
        checkGameOver();
    }


    public void checkGameOver() {
        if (gameOver) {
            p.setGameOver();
        }
    }


    public void checkReload() {
        if (shotsSince >= character.getAmmo()) {
            if (firstReloadThread) {
                thread = new Thread() {
                    public void run() {
                        System.out.println("Thread Running");
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        shotsSince = 0;
                        return;
                    }
                };
                firstReloadThread = false;
                thread.start();
                if (thread.isAlive()) {
                    p.drawReload();
                }
            }
        } else {
            firstReloadThread = true;
        }
    }



    public int getNumMissiles() {
        return missiles.size();
    }

    public int getNumEnemies() {
        return enemies.size();
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public int getShotsSince() {
        return shotsSince;
    }


    // Fires a missile
    // modifies: this
    // effects:  fires a missile if max number of missiles in play has
    //           not been exceeded, otherwise silently returns
    private void fireMissile(int x) {

        if (shotsSince >= character.getAmmo()) {
            System.out.println("Max Shots Reached!");
            return;
        }

        Missile m;
        if (x == 73) {
            m = new Missile(character.getX(), character.getY(), 0);
        } else if (x == 74) {
            m = new Missile(character.getX(), character.getY(), 3);
        } else if (x == 75) {
            m = new Missile(character.getX(), character.getY(), 2);
        } else if (x == 76 ) {
            m = new Missile(character.getX(), character.getY(), 1);
        } else {
            m = new Missile(character.getX(), character.getY(), character.getDirection());
        }
        shotsSince++;
        missiles.add(m);

    }


    // updates the missiles
    // modifies: this
    // effects: moves the missiles
    private void moveMissiles() {
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).move();
        }
    }


    // Check missiles
    // modifies: this
    // effects:  removes any missile that has traveled off top of screen
    private void checkMissiles() {
        List<Missile> missilesToRemove = new ArrayList<Missile>();

        for (Missile next : missiles) {
            if (next.getY() < 0) {
                missilesToRemove.add(next);
            } else if (next.getY() > 600) {
                missilesToRemove.add(next);
            } else if (next.getX() > 800) {
                missilesToRemove.add(next);
            } else if (next.getX() < 0) {
                missilesToRemove.add(next);
            }
        }
        missiles.removeAll(missilesToRemove);
    }

    private void enemiesTakeDamage() {

        int upperX, lowerX, upperY, lowerY;
        //Missile m;
        List<Missile> shouldRemove = new ArrayList<>();
        for(Enemy x: enemies) {

            upperX = x.getX() + x.getHitBoxSize();
            lowerX = x.getX() - x.getHitBoxSize();
            upperY = x.getY() + x.getHitBoxSize();
            lowerY = x.getY() - x.getHitBoxSize();

            for(Missile m : missiles) {
              //  m = missiles.get(i);
                if (m.getX() > lowerX && m.getX() < upperX && m.getY() < upperY && m.getY() > lowerY) {
                    x.takeDamage(5);
                    System.out.println("an enemy took damage!");
                    shouldRemove.add(m);
                    score += 5;
                  //  missiles.remove(i);
                }
            }
        }

        missiles.removeAll(shouldRemove);

    }

    private void checkEnemyDeath() {

        if(enemies.size() == 0) {
            return;
        }

        for(int i = enemies.size() - 1; i >= 0; i--) {
            if (enemies.get(i).shouldDie()) {
                score += 10;
                score += enemies.get(i).getHitBoxSize() / 2;
                enemies.remove(i);
            }
        }
    }

    private void checkUserDeath() {
        int upperX, lowerX, upperY, lowerY;

        for (Enemy x : enemies) {

            upperX = x.getX() + x.getHitBoxSize();
            lowerX = x.getX() - x.getHitBoxSize();
            upperY = x.getY() + x.getHitBoxSize();
            lowerY = x.getY() - x.getHitBoxSize();

            if (character.getX() > lowerX && character.getX() < upperX && character.getY() < upperY
                    && character.getY() > lowerY) {
                //decrement player health
                //player temp invulnerability
                gameOver = true;
            }
        }
    }

    private void moveEnemies() {
        for(Enemy x : enemies) {
            x.move(character.getX(), character.getY());
        }
    }

    private void setUp() {
        clearEnemies();
        clearMissiles();
        resetPlayer();
    }

    private void clearEnemies() {
        enemies = new ArrayList<>();
    }

    private void clearMissiles() {
        missiles = new ArrayList();
    }

    private void resetPlayer() {
        character.resetX();
        character.resetY();
        shotsSince = 0;
        score = 0;
    }


}
