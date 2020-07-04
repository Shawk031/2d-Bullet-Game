package First;
import java.awt.Color;


public class PlayerCharacter {

    public static Color COLOR = new Color(100, 255, 100);
    public static int SIZE_X = 20;
    public static int SIZE_Y = 10;

    public static int startingXPosition = 400;
    public static int startingYPosition = 400;

    int currentXPosition;
    int currentYPosition;

    //Direction is a field from 0 - 3. 0 is north, 1 is east, 2 is south, 3 is west.
    //Use an enumeration later to get this one down
    int direction;
    //ArrayList<Items> inventory;
    //Gotta make some stats for the character too.
    int speed;    //speed min = 1, speed max = 10. Speed changes with buffs etc.
    int ammo;


    public PlayerCharacter() {
        currentXPosition = startingXPosition;
        currentYPosition = startingYPosition;
        direction = 0;
        speed = 20;
        ammo = 10;
        //Set both these things to the center of the screen later when I get the UI going.
    }

    public int getDirection() {
        return direction;
    }

    public int getAmmo() { return ammo; }

    public int getX() {
        return currentXPosition;
    }

    public int getY() {
        return currentYPosition;
    }

    public void resetX() { currentXPosition = startingXPosition; }

    public void resetY() {
        currentYPosition = startingYPosition;
    }

    public PlayerCharacter getPlayerCharacter() {
        return this;
    }

    public void moveRight() {
        currentXPosition += speed;
        direction = 1;
        //check if there are any obstacles first.
    }

    public void moveLeft() {
        currentXPosition -= speed;
        direction = 3;
    }

    public void moveUp() {
        currentYPosition -= speed;
        direction = 0;
    }

    public void moveDown() {
        currentYPosition += speed;
        direction = 2;
    }

}



























