package First;

import java.awt.Color;

/*
 * Represents a missile.
 */
public class Missile {

    public static final int SIZE_X = 6;
    public static final int SIZE_Y = 6;
    public static final int DY = 5;
    public static final Color COLOR = new Color(208, 250, 230);

    private int x;
    private int y;
    private int direction;


    // Constructs a missile
    // effects: missile is positioned at coordinates (x, y)
    public Missile(int x, int y, int z) {
        this.x = x;
        this.y = y;
        direction = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Updates the missile on clock tick
    // modifies: this
    // effects: missile is moved DY units (up the screen)
    public void move() {
        if (direction == 0) {
            y = y - DY;
        } else if (direction == 1) {
            x = x + DY;
        } else if (direction == 2) {
            y = y + DY;
        } else {
            x = x - DY;
        }
    }
}
