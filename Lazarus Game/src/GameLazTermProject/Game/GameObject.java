package GameLazTermProject.Game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


public class GameObject {

    public int x;
    public int y;
    public int speed;
    public Rectangle rectangleBox;
    public BufferedImage[] sprite;
    public boolean tfDisplay = true;
    public int frame = 0;   
    public char kindofCollision = ' ';


   
    public GameObject() {}
       
    //sets the dimensions of the BufferedImage
    public GameObject(BufferedImage[] imageBuffered) {

        sprite = imageBuffered;
        rectangleBox = new Rectangle(sprite[0].getWidth(), sprite[0].getHeight());
    }

    
    //position of coordinate x and y are set
    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;

        rectangleBox = new Rectangle(x, y, 0, 0);
    }

    

    public GameObject(BufferedImage[] imageBuffered, int x, int y) {
        this.x = x;
        this.y = y;
        sprite = imageBuffered;

        rectangleBox = new Rectangle(x, y, sprite[0].getWidth(), sprite[0].getHeight());
    }

    
    //sets collision type
    public void collideSet(char temp) {

        kindofCollision = temp;
    }

    
    public void collided() {

        switch (kindofCollision) {
            default:
                break;
        }
    }

    public void speedSet(int speed) {

        this.speed = speed;
    }

    public int getSpeed() {

        return speed;
    }

    public void setCoordinateXY(int x, int y) {

        this.x = x;
        this.y = y;
        rectangleBox.x = x;
        rectangleBox.y = y;
    }

    public void xCoordinateSet(int x) {

        this.x = x;
        rectangleBox.x = x;
    }

    public int getCoordinateX() {

        return x;
    }

    public void yCoordinateSet(int y) {

        this.y = y;
        this.rectangleBox.y = y;
    }

    public int getCoordinateY() {

        return y;
    }

    public Rectangle getBounds() {

        return rectangleBox.getBounds();
    }

    public void setSprite(BufferedImage[] bufferedImage1) {

        sprite = bufferedImage1;
        rectangleBox.width = sprite[0].getWidth();
        rectangleBox.height = sprite[0].getHeight();
    }

    public void setDDisplay(boolean tf1) {

        tfDisplay = tf1;
    }

    public boolean getDDisplay() {

        return tfDisplay;
    }

    public void setFrame(int tempINt) {

        frame = tempINt;
    }

    public void draw(Graphics graphics2d, ImageObserver obs) {
        if (tfDisplay) {
            graphics2d.drawImage(sprite[frame], x, y, obs);
        }
    }

}

