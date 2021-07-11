package GameLazTermProject;

import javax.swing.*;
import java.awt.event.WindowAdapter;


public class MainStartOfGame {
    public static void main(String argv[]) {

        final LazarusWorld lazarusWorldNewGame = new LazarusWorld();

        lazarusWorldNewGame.init();

        JFrame jframe1 = new JFrame("Get Lazarus out of the Pit");


        jframe1.addWindowListener(new WindowAdapter() {});
        jframe1.getContentPane().add("Center", lazarusWorldNewGame);
        jframe1.setSize(lazarusWorldNewGame.getFrameWidth(), lazarusWorldNewGame.getFrameHeight());
        jframe1.setResizable(true);
        jframe1.setVisible(true);

        //start game
        lazarusWorldNewGame.start();
        //exit
        jframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } 
}
