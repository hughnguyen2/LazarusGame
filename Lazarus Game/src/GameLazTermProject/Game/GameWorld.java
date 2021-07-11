package GameLazTermProject.Game;


import GameLazTermProject.Notification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;


public class GameWorld extends JApplet implements Runnable {
    public Graphics2D graphicsGame;
    public Thread gameThread;
    int width;
    int height;
    public Notification notificationEvent;
   

    @Override
    public void start() {
        gameThread = new Thread(this);

        gameThread.setPriority(Thread.MIN_PRIORITY);

        gameThread.start();
    }

    @Override
    public void run() {

        Thread me = Thread.currentThread();
        while (gameThread == me) {
            repaint();
            try {
                gameThread.sleep(33);

            } catch (InterruptedException e) {
                break;
            }
        }
    } 

    
    //image file is returned
    public Image getSprite(String fileName) {

        URL url = GameWorld.class.getResource(fileName);

        Image image1 = java.awt.Toolkit.getDefaultToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(image1, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
            System.out.println("Cannot get image file: " + e);
        }
        return image1;
    }


    // image becomes buffered iamge
    public BufferedImage convertToBuffered(Image bufferedImage1) {
        int width = bufferedImage1.getWidth(this);
        int height = bufferedImage1.getHeight(this);
        BufferedImage convertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2temp = convertedImage.createGraphics();
        g2temp.drawImage(bufferedImage1, 0, 0, this);
        g2temp.dispose();
        return convertedImage;
    }

    
   // sprite becomes images in coversion
    public BufferedImage[] convertToSprite(BufferedImage bufferedImage1, int xyPixel, int temp) {
        BufferedImage[] converted = new BufferedImage[bufferedImage1.getTileWidth() / xyPixel];
        int width = bufferedImage1.getWidth() / temp;
        int height = bufferedImage1.getHeight();
        int divider = 0;
        for (int i = 0; i < temp; i++) {
            converted[i] = bufferedImage1.getSubimage(divider, 0, width, height);
            divider += xyPixel;
        }
        return converted;
    }

    
    
    public int getFrameWidth() {

        return width;
    }

    public int getFrameHeight() {

        return height;
    }

    public void setFrameWidth(int frameWidth) {

        width = frameWidth;
    }

    public void setFrameHeight(int frameHeight1) {

        height = frameHeight1;
    }

  
    
    public Notification notificationEvent() {

        return this.notificationEvent;
    }

    public class KeyControl extends KeyAdapter {

        public void keyPressed(KeyEvent firstEvent) {

            notificationEvent.setValue(firstEvent);
        }
    }
    
    
}

