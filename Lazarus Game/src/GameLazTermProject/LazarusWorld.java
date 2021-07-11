package GameLazTermProject;


import GameLazTermProject.Game.GameBox;
import GameLazTermProject.Game.GameObject;
import GameLazTermProject.Game.GameWorld;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;


public class LazarusWorld extends GameWorld {
    
    
    public BufferedImage[] SpriteStandLazarus;
    public BufferedImage[] SpriteAfraidLazarus;
    public BufferedImage[] SpriteSquishedLazarus;
    public BufferedImage[] SpriteLeftLazarus;
    public BufferedImage[] SpriteRightLazarus;
    public BufferedImage[] SpriteJumpLeftLazarus;
    public BufferedImage[] SpriteJumpRightLazarus;
    public BufferedImage[] SpriteWall;
    public BufferedImage[] SpriteCard;
    public BufferedImage[] SpriteMetal;
    public BufferedImage[] SpriteStone;
    public BufferedImage[] SpriteWood;
    public BufferedImage[] SpriteStop;

    public int[] userKeysPressed = { KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
    private static HashMap<String, Image> sprites;     
    private ArrayList<LazarusObject> arrayLazarus = new ArrayList<>();
    private ArrayList<GameBox> arrayBox = new ArrayList<>();
    private ArrayList<endGameStopButton> arrayStop = new ArrayList<>();

    private  int backgroundWidth = 656, backgroundHeight = 520;

    private boolean gameTitleDisplay = true;

    public int speed = 5;
    private int level = 1;
   
    
    
    protected BufferedImage endImage = new BufferedImage(backgroundWidth,backgroundHeight,BufferedImage.TYPE_INT_RGB); 

    @Override
    public void init() {

        setFrameWidth(backgroundWidth);
        setFrameHeight(backgroundHeight);
        setFocusable(true);
        setBackground(Color.gray);

        sprites = new HashMap<String, Image>();

        try {
                               
            loadImageSprites();
          
            SpriteWall = convertToSprite(convertToBuffered(sprites.get("Wall")), 40, 1);
            SpriteCard = convertToSprite(convertToBuffered(sprites.get("CardBox")), 40, 1);
            SpriteMetal = convertToSprite(convertToBuffered(sprites.get("MetalBox")), 40, 1);
            SpriteStone = convertToSprite(convertToBuffered(sprites.get("StoneBox")), 40, 1);
            SpriteWood = convertToSprite(convertToBuffered(sprites.get("WoodBox")), 40, 1);
            SpriteStop = convertToSprite(convertToBuffered(sprites.get("Button")), 40, 1);
            SpriteStandLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusStand")), 40, 1);
            SpriteAfraidLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusAfraid")), 40, 10);
            SpriteSquishedLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusSquished")), 40, 11);
            SpriteLeftLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusLeft")), 80, 7);
            SpriteRightLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusRight")), 80, 7);
            SpriteJumpLeftLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusJumpLeft")), 80, 7);
            SpriteJumpRightLazarus = convertToSprite(convertToBuffered(sprites.get("LazarusJumpRight")), 80, 7);
            
            arrayLazarus.add(new LazarusObject(SpriteStandLazarus, SpriteLeftLazarus, SpriteRightLazarus, SpriteJumpLeftLazarus, SpriteJumpRightLazarus,
                    SpriteSquishedLazarus, SpriteAfraidLazarus, userKeysPressed,speed));


            loadGameMap("/resource/level0.txt", 40, 40);

            selectRandomBox();
            BufferedImage[][] BoxKind = {SpriteCard, SpriteWood, SpriteStone, SpriteMetal};
            int rand = ThreadLocalRandom.current().nextInt(0, 4);            
            arrayBox.add(new GameBox(BoxKind[rand], arrayLazarus.get(0).getCoordinateX(), 0, speed, (char)(rand + 49), true));
            notificationEvent = new Notification();
            notificationEvent.addObserver(arrayLazarus.get(0));
            KeyControl key = new KeyControl();
            addKeyListener(key);

        } catch (Exception e) {
            System.out.println("Error - Incorrect file name for sprite: " + e);
        }
    }

    //loads the images
    private void loadImageSprites() {


        sprites.put("background", getSprite("/resource/Background.png"));
        sprites.put("title", getSprite("/resource/Title.png"));
        sprites.put("CardBox", getSprite("/resource/CardBox.png"));
        sprites.put("MetalBox", getSprite("/resource/MetalBox.png"));
        sprites.put("StoneBox", getSprite("/resource/StoneBox.png"));
        sprites.put("WoodBox", getSprite("/resource/WoodBox.png"));
        sprites.put("Wall", getSprite("/resource/Wall.png"));
        sprites.put("Button", getSprite("/resource/Button.png"));
        sprites.put("LazarusStand", getSprite("/resource/Lazarus_stand.png"));
        sprites.put("LazarusAfraid", getSprite("/resource/Lazarus_afraid.png"));
        sprites.put("LazarusLeft", getSprite("/resource/Lazarus_left.png"));
        sprites.put("LazarusRight", getSprite("/resource/Lazarus_right.png"));
        sprites.put("LazarusJumpLeft", getSprite("/resource/Lazarus_jump_left.png"));
        sprites.put("LazarusJumpRight", getSprite("/resource/Lazarus_jump_right.png"));
        sprites.put("LazarusSquished", getSprite("/resource/Lazarus_squished.png"));

    }

  
    public void selectRandomBox() {

        BufferedImage[][] BoxKind = {SpriteCard, SpriteWood, SpriteStone, SpriteMetal};
        int random = ThreadLocalRandom.current().nextInt(0, 4);
        arrayBox.add(new GameBox(BoxKind[random], 0, 440, speed, (char)(random + 49), false));
    }

    public void currentBoxDrop() {
        if (!arrayLazarus.get(0).endGameSucceed()) {
            if (!arrayBox.get(arrayBox.size() - 2).DroppingBox()) {
                arrayBox.get(arrayBox.size() - 1).setCoordinateXY(arrayLazarus.get(0).getCoordinateX() - arrayLazarus.get(0).getCoordinateX() % 40, 0);
                arrayBox.get(arrayBox.size() - 1).getBoxDropping(true);
                selectRandomBox();
            }
        }
    }

    
    public boolean collided(GameObject gameObject1, GameObject gameObject2, char type) {
        if (gameObject1.rectangleBox.intersects(gameObject2.rectangleBox)) {
            gameObject1.collideSet(type);
            return true;
        }
        return false;
    }

    public void stopInitialized() {
        if (!arrayLazarus.get(0).endGameSucceed()) {
            for (int i = 0; i < arrayStop.size(); i++) {
                if (collided(arrayLazarus.get(0), arrayStop.get(i), ' ')) {
                    arrayBox.clear();
                    arrayStop.clear();
                    try {                       
                        loadGameMap("/resource/level" + level + ".txt", 40, 40); 
                    } catch (Exception e) {
                        System.out.println("Error: Incorrect file name for level: " + e);
                    }
                    selectRandomBox();
                    BufferedImage[][] BoxKind = {SpriteCard, SpriteWood, SpriteStone, SpriteMetal};
                    int rand = ThreadLocalRandom.current().nextInt(0, 4);                    
                    arrayBox.add(new GameBox(BoxKind[rand], arrayLazarus.get(0).getCoordinateX(), 0, speed, (char)(rand + 49), true));
                    level++;
                }
            }
        }
    }

    public void objectandBox() {
        if (!arrayLazarus.get(0).endGameSucceed()) {
            for (int i = 0; i < arrayBox.size(); i++) {
                if (arrayBox.get(i).getDDisplay()) {
                    if (collided(arrayLazarus.get(0), arrayBox.get(i), '0')) {
                        arrayLazarus.get(0).update();
                    }
                }
            }
        }
    }

    public void boxandBox() {
        for (int i = 0; i < arrayBox.size(); i++) {
            for (int j = 0; j < arrayBox.size(); j++) {
                if (j != i && arrayBox.get(j).getDDisplay()) {
                    if (arrayBox.get(i).DroppingBox()) {
                        if (arrayBox.get(j).getKindOfBox() > arrayBox.get(i).getKindOfBox() || arrayBox.get(j).getKindOfBox() == '0' ||
                                arrayBox.get(i).getKindOfBox() == arrayBox.get(j).getKindOfBox()) {
                            if (collided(arrayBox.get(i), arrayBox.get(j), '0')) {

                                arrayBox.get(i).update();
                                if (gameTitleDisplay) {
                                    gameTitleDisplay = false;
                                }
                            }
                        
                        } else if (collided(arrayBox.get(i), arrayBox.get(j), ' ')){

                            arrayBox.get(j).setDDisplay(false);
                            
                        }
                    }
                }
            }
        }
    }

    public void boxAndObject() {
        if (!arrayLazarus.get(0).endGameSucceed()) {
            for (int i = 0; i < arrayBox.size(); i++) {
                if (arrayBox.get(i).DroppingBox() && !arrayLazarus.get(0).endGameSucceed()) {
                    if (collided(arrayLazarus.get(0), arrayBox.get(i), '1')) {

                        arrayLazarus.get(0).update();
                    }
                }
            }
        }
    }

    
    
    public void loadGameMap(String fileName, int xCoordinate, int yCoordinate) throws Exception {
        int x = 0;
        int y = 0;
        try {
            InputStream inputs1 = getClass().getResourceAsStream(fileName);
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputs1));
            int temp = reader1.read();
            while (temp > -1) {
                while (temp != '\n') {
                    if (temp == ' ') {
                       x += xCoordinate;
                    }
                    else if(temp == 's' ){
                       arrayStop.add(new endGameStopButton(SpriteStop, x, y));
                       x += xCoordinate;
                    }
                    else if(temp == 'x'){
                        arrayBox.add(new GameBox(SpriteWall, x, y, speed, '0', false));
                        x += xCoordinate;
                    }
                    else if(temp == 'l'){
                        arrayLazarus.get(0).setCoordinateXY(x,y);
                        x += xCoordinate;
                    }                    
                    temp = reader1.read();
                }
                temp = reader1.read();
                x = 0;
                y += yCoordinate;
                if (temp == ' ') {
                    temp = reader1.read();
                }
            }
        } catch (IOException e) {
            System.out.println("Error in loading game map: " + e);
        }
    }


    public void displayWall() {
        for (int i = 0; i < arrayBox.size(); i++) {
            arrayBox.get(i).draw(graphicsGame, this);
        }
    }

    public void displayStop() {
        for (int i = 0; i < arrayStop.size(); i++) {
            arrayStop.get(i).draw(graphicsGame, this);
        }
    }

    public void displayBackground(){

        graphicsGame.drawImage(sprites.get("background"), 0, 0, this);
    }


    
    public void draw() {
        displayBackground();
        if (level > 3) {   // game won         
           graphicsGame.setColor(Color.white);
           graphicsGame.setFont(new Font("Times", 0, 60));
           graphicsGame.drawString("\t\tYou Win!", 200, 400);
           
        } else {           
            stopInitialized();
            boxAndObject();
            objectandBox();
            boxandBox();
            currentBoxDrop();
            displayWall();
            displayStop();
            arrayLazarus.get(0).draw(graphicsGame, this);
            if (gameTitleDisplay) {
                graphicsGame.drawImage(sprites.get("title"), 70, 0, this);
            }
        }
    }

   @Override
    public void paint(Graphics graphiccs1) {
        BufferedImage bufferImage1 = (BufferedImage) createImage(backgroundWidth, backgroundHeight);
        graphicsGame = bufferImage1.createGraphics();
        draw();
        graphicsGame.dispose();
        graphiccs1.drawImage(bufferImage1, 0, 0, this);
    } 

    
}


