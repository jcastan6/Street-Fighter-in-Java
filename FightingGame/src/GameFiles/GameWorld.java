/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameFiles;

/**
 *
 */

import GameFiles.GameStates.CharSelect;
import com.github.strikerx3.jxinput.XInputDevice;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;

import GameFiles.Characters.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;

public class GameWorld extends JComponent implements Runnable{
    public static boolean debug = false;

    private enum GameState{
        GAMEPLAY,
        CHARSELECT,
        GAMEOVER
    };

    private GameState state = GameState.CHARSELECT;
    private CharSelect charSelect;

    private Thread thread;
    private Image bgImage;
    private KeyboardControl ch1;
    private XinputControl ch1x;
    private KeyboardControl ch2;
    private MoveBuffer buffer1;
    private MoveBuffer buffer2;
    private GameCharacter player1 = null;
    private GameCharacter player2 = null;
    private Camera camera;
    private Sound intro;
    private Sound KO;
    private Sound music;
    private Sound load = new Sound("/load.wav");
    private int sleeptime = 5;

    private XInputDevice[] devices;

    private static final int INITX = 1280 / 8;
    private static final int INITY = 625;

    private Graphics2D g2d;
    private BufferedImage bimg;

    public void init() {
        charSelect = new CharSelect();
        addKeyListener(charSelect);

        setFocusable(true);
        setDoubleBuffered(true);
        setBackground(Color.white);


        bgImage = loadImage("FightingGame/Resources/Sprites/Ken/bg1.png");
        bgImage = bgImage.getScaledInstance(1920, 1080, Image.SCALE_REPLICATE);


        addKeyListener(new DebugControl());
    }

    private Image loadImage(String fileName) {
        return new ImageIcon(fileName).getImage();
    }

    public void updateGame() {
        requestFocus();
        devices[0].poll();
        player1.update();
        player2.update();
        camera.update(player1, player2);

        player1.handleCollision(player2);
        player2.handleCollision(player1);

        if(player1.died()){
            music.stopMusic();
            sleeptime = 60; //slow down game
            if(player2.hasWon()) {
                state = GameState.GAMEOVER;
                KO.run(0);
            }
        }
        else if(player2.died()){
            music.stopMusic();
            sleeptime = 60; //slow down game
            if(player1.hasWon()){
                state = GameState.GAMEOVER;
                KO.run(0);
            }
        }
    }

    public void drawGame(){
        g2d.setColor(Color.black);
        g2d.drawRect(0, 0, 1920, 1080);
        g2d.translate(-2 * (camera.getX()) + 60, -(camera.getY()) - 60);
        g2d.scale(2, 2.1);
        g2d.drawImage(bgImage, 0, 0, null);
        player1.drawImage(g2d);
        player2.drawImage(g2d);

        //draw move buffer to show pressed buttons
        /*
        buffer1.draw(g);
        buffer2.draw(g);
        */

        camera.drawCamera(g2d);

        if(state == GameState.GAMEOVER){
            camera.drawGameOver(g2d);
        }
    }

    public void paint(Graphics g) {
        Dimension windowSize = getSize();
        bimg = (BufferedImage) createImage(windowSize.width, windowSize.height);
        g2d  = (Graphics2D)bimg.getGraphics();

        if(state == GameState.CHARSELECT){
            charSelect.update(this);
            charSelect.draw(g2d);

            if(charSelect.isDone()){
                load.run(0);
                setPlayers(charSelect.getPlayer1(),charSelect.getPlayer2());
            }
        }
        else if(state == GameState.GAMEPLAY) {
            updateGame();
            drawGame();
        }
        else if(state == GameState.GAMEOVER){
            player1.gameoverupdate();
            player2.gameoverupdate();
            camera.update(player1,player2);
            drawGame();
        }

        g.drawImage(bimg,0,0,null);
    }

    public void start() {
        System.out.println();
        thread = new Thread(this);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }

    public void run() {
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
            try {
                thread.sleep(sleeptime);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String argv[]) {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();
        Dimension screenSize = new Dimension(1920,1080);

        final GameWorld game = new GameWorld();
        game.init();

        JFrame f = new JFrame("Game");
        screen.setFullScreenWindow(f);

        f.getContentPane().add("Center", game);
        f.pack();
        f.setSize(screenSize);
        f.setVisible(true);
        f.setIgnoreRepaint(true);
        f.setResizable(false);
        f.createBufferStrategy(2);
        f.requestFocus();

        game.start();
    }

    public void setPlayers(String char1, String char2){
        Class c = null;
        try {
            c = Class.forName("GameFiles.Characters."+char1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            player1 = (GameCharacter) c.getDeclaredConstructor(int.class,int.class, boolean.class).newInstance(4 * INITX, INITY, true);
        } catch (InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        c = null;
        try {
            c = Class.forName("GameFiles.Characters."+char2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            player2 = (GameCharacter) c.getDeclaredConstructor(int.class,int.class, boolean.class).newInstance(8 * INITX, INITY, false);
        } catch (InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        buffer1 = new MoveBuffer();
        buffer2 = new MoveBuffer();

        try {
            devices = XInputDevice.getAllDevices();
        } catch (XInputNotLoadedException e) {
            e.printStackTrace();
        }

        if (devices.length != 0 && devices[0].poll()) {
            ch1x = new XinputControl(player1, buffer2, XInputButton.DPAD_UP, XInputButton.DPAD_DOWN, XInputButton.DPAD_LEFT, XInputButton.DPAD_RIGHT, XInputButton.X, XInputButton.Y, XInputButton.LEFT_SHOULDER, XInputButton.A, XInputButton.B, XInputButton.RIGHT_SHOULDER);
            ch2 = new KeyboardControl(player2, buffer1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L);
            devices[0].addListener(ch1x);
        } else {
            ch1 = new KeyboardControl(player1, buffer2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_U, KeyEvent.VK_I, KeyEvent.VK_O, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L);
            addKeyListener(ch1);
            ch2 = new KeyboardControl(player2, buffer1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_NUMPAD4, KeyEvent.VK_NUMPAD5, KeyEvent.VK_NUMPAD6, KeyEvent.VK_NUMPAD1, KeyEvent.VK_NUMPAD2, KeyEvent.VK_NUMPAD3);
        }

        addKeyListener(ch2);

        camera = new Camera(player1, player2, 1920, 1080);

        music = new Sound("Music/ken2.wav");
        music.run();

        repaint();

        player1.start();
        player2.start();

        state = GameState.GAMEPLAY;

        KO = new Sound("Music/KO.wav");
        intro = new Sound("Music/intro.wav");
        intro.run(0);
    }

}