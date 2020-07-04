package First;

import java.awt.Color;

public class Enemy {

    private int health;
    private int damage;
    private int currentXPosition;
    private int currentYPosition;
    private int hitBoxSize;
    private int speed;

    public Color COLOR;

    public Enemy() {
        currentXPosition = (int) (Math.random() * 800);
        currentYPosition = (int) (Math.random() * 600);
        System.out.println(currentXPosition);
        hitBoxSize = (int) (Math.random() * 20 + 10);
        health = (int) (hitBoxSize / 3 + Math.random() * 5);

        COLOR = new Color(230, 100, 100);

        speed = 1;
    }


    public int getX() {
        return currentXPosition;
    }

    public int getY() {
        return currentYPosition;
    }

    public int getHitBoxSize() {
        return hitBoxSize;
    }

    public void takeDamage(int damage) {
        health -= damage;
        COLOR = Color.black;
        Thread t = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                COLOR = new Color(230, 100, 100);

            }
        };
        t.start();


    }

    public boolean shouldDie() {
        return health < 1;
    }

   // public

    public Color getCOLOR() {
        return COLOR;
    }

    public void move(int x, int y) {
        if (x > currentXPosition) {
            currentXPosition += speed;
        } else {
            currentXPosition -= speed;
        }

        if (y > currentYPosition) {
            currentYPosition += speed;
        } else {
            currentYPosition -= speed;
        }
    }

}
























