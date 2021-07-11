package GameLazTermProject.Game;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;


public class GameBox extends GameObject implements Observer {
    char kindOfBox;
    int positionX;
    int positionY;
    boolean boxDropping;

    public GameBox(BufferedImage[] sprite1) {

        super(sprite1);
    }

    public GameBox(BufferedImage[] sprite, int x, int y, int speed, char kindOfBox, boolean drop) {

        super(sprite);
        setCoordinateXY(x,y);

        positionX = x;
        positionY = y;

        this.speed = speed;
        this.kindOfBox = kindOfBox;
        this.boxDropping = drop;
    }
        
  
    
    @Override
     public void collided() {
        if (kindofCollision == '0') {
                boxDropping = false;
                yCoordinateSet(positionY);
        }
        else if(kindofCollision == '1') {
            tfDisplay = false;
        }     
    }

    public void drop() {
        positionY = y;
        yCoordinateSet(y + speed);
    }

  
    
    public boolean getBoxDropping(boolean tf1) {

        return boxDropping = tf1;
    }

    public boolean DroppingBox() {

        return boxDropping;
    }

    
    public char getKindOfBox() {

        return kindOfBox;
    }

    public void update() {

        collided();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public void draw(Graphics graphics2d, ImageObserver obs) {

        if (tfDisplay) {

            graphics2d.drawImage(sprite[frame], x, y, obs);

            if (boxDropping) {
                drop();
            }
        }
    }
}
