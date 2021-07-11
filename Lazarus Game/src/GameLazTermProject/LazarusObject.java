package GameLazTermProject;

import GameLazTermProject.Game.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;


public class LazarusObject extends GameObject implements Observer {
         
    int[] userKeyPressed;
    int tempX;
    int tempY;
    int lastX;
    int lastY;
    boolean boxDrop = false;
    boolean jump = false;
    boolean endGame = false;
    char firstDirection = ' ';
    char secondDirection;

  

    BufferedImage[] spriteLeft;
    BufferedImage[] spriteRight;
    BufferedImage[] spriteJumpLeft;
    BufferedImage[] spriteJumpRight;
    BufferedImage[] spriteSquished;
    BufferedImage[] spriteAfraid;

    public LazarusObject(BufferedImage[] sprite0, BufferedImage[] spriteLeft, BufferedImage[] spriteRight,
                         BufferedImage[] spriteJumpLeft, BufferedImage[] spriteJumpRight,
                         BufferedImage[] spriteSquished, BufferedImage[] spriteAfraid, int[] userKeyPress, int speed) {

        super(sprite0);
        
        this.spriteJumpRight = spriteJumpRight;
        this.spriteSquished = spriteSquished;
        this.spriteAfraid = spriteAfraid;
        this.spriteLeft = spriteLeft;
        this.spriteRight = spriteRight;
        this.spriteJumpLeft = spriteJumpLeft;
        
        speedSet(speed);
        this.userKeyPressed = userKeyPress;

    }

    @Override
    public void collided() {
        switch (kindofCollision) {
            case '0':
                if (boxDrop) {
                    boxDrop = false;
                    jump = false;
                    yCoordinateSet(tempY);
                } else if (!jump) {
                    if (secondDirection == '2')
                        setBound(x - 40, y - 40);
                    else if (secondDirection == '3')
                        setBound(x + 40, y - 40);
                    wallJump();
                } else if (jump){
                    jump = false;
                    collidedWithWall();
                }
                break;
            case '1':
               
                endGame = true;
                break;
            default:
                break;
        }
        
        kindofCollision = ' ';
    }

    public void collidedWithWall() {
        frame = 0;
        setCoordinateXY(lastX, lastY + 40);
    }

    public void wallJump() {
        jump = true;
        if (secondDirection == '2')
            jumpLeft();
        else if (secondDirection == '3')
            jumpRight();
    }

    public void moveLeft() {
        firstDirection = '2';
        secondDirection = '2';
        xCoordinateSet(getCoordinateX() - speed);

        if (frame > 7)
            frame = 0;
    }

    public void moveRight() {
        firstDirection = '3';
        secondDirection = '3';

        xCoordinateSet(getCoordinateX() + speed);

        if (frame > 7)
            frame = 0;
    }
    public void jumpLeft() {
        firstDirection = '0';
        secondDirection = '0';
        xCoordinateSet(getCoordinateX() - speed);

        if (frame > 7)
            frame = 0;

    }

    public void jumpRight() {
        firstDirection = '1';
        secondDirection = '1';

        xCoordinateSet(getCoordinateX() + speed);

        if (frame > 7)
            frame = 0;
    }

    

    public void drop() {
        tempY = y;
        yCoordinateSet(y + 40);
    }

  

    public void setBound(int x, int y) {
        rectangleBox.x = x;
        rectangleBox.y = y;
    }



    public boolean endGameSucceed() {

        return endGame;
    }

  

    public void update() {
        if (!endGame) {
            collided();
        }
    }

    @Override
    public void update(Observable o, Object arg){       
        Notification ge = (Notification) arg;
            KeyEvent e = (KeyEvent) ge.eObject;
            int kPressed = e.getKeyCode();            
             if (kPressed == userKeyPressed[0] || kPressed == userKeyPressed[1]) {
                if (frame == 0 && !boxDrop) {
                    if (tfDisplay) {

                        lastX = this.x;
                        lastY = this.y;                        
                        if(kPressed == userKeyPressed[0])
                         moveLeft();
                        if (kPressed == userKeyPressed[1])
                         moveRight();                        
                    }
                }
            }         
       
    }

    @Override
    public void draw(Graphics graphic2d, ImageObserver imageObs) {
        if (tfDisplay) {
            if (endGame) {
                graphic2d.setColor(Color.white);
                graphic2d.setFont(new Font("Calibri", 0, 60));
                graphic2d.drawString("\t\tYou Lose!", 200, 300);
                if (frame != 10) {
                    graphic2d.drawImage(spriteSquished[frame], x, y, imageObs);
                    frame++;   
                } else {
                    frame = 0;
                    setDDisplay(false);
                }
            } else {
                switch (firstDirection) {
                    case '0':
                        if ((this.x % 40) != 0) {
                            graphic2d.drawImage(spriteJumpLeft[frame], x - 20, y - 40, imageObs);
                            jumpLeft();
                            frame++;
                        } else {
                            frame = 0;                            
                            firstDirection = ' ';
                            yCoordinateSet(y - 40);
                            graphic2d.drawImage(sprite[frame], x, y, imageObs);
                            boxDrop = true;
                        }
                        break;
                    case '1':
                        if ((this.x % 40) != 0) {
                            graphic2d.drawImage(spriteJumpRight[frame], x - 20, y - 40, imageObs);
                            jumpRight();
                            frame++;
                        } else {
                            frame = 0;                           
                            firstDirection = ' ';
                            yCoordinateSet(y - 40);
                            graphic2d.drawImage(sprite[frame], x, y, imageObs);
                            boxDrop = true;
                        }
                        break;
                    case '2':
                        if ((this.x % 40) != 0) {
                            graphic2d.drawImage(spriteLeft[frame], x - 20, y - 40, imageObs);
                            moveLeft();
                            frame++;
                        } else {
                            frame = 0;                           
                            firstDirection = ' ';
                            graphic2d.drawImage(sprite[frame], x, y, imageObs);
                            boxDrop = true;
                        }
                        break;
                    case '3':
                        if ((this.x % 40) != 0) {
                            graphic2d.drawImage(spriteRight[frame], x - 20, y - 40, imageObs);
                            moveRight();
                            frame++;
                        } else {
                            frame = 0;                           
                            firstDirection = ' ';
                            graphic2d.drawImage(sprite[frame], x, y, imageObs);
                            boxDrop = true;
                        }
                        break;
                    default:
                        graphic2d.drawImage(sprite[0], x, y, imageObs);
                        break;
                }
                if (boxDrop) {
                    drop();
                }

            }
        }
    }
}


