package aknakereso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * A játék (nem a menü) grafikus felülete a felelőssége.
 */
public class GameGUI {
    /**
     * A játék ablaka.
     */
    private JFrame gameFrame;
    /**
     * Ennek segítségével rakjuk az ablakba a játékot.
     */
    private JPanel gamePanel;
    /**
     * A JButton mátrixot tartalmazó osztály.
     */
    private Grid grid;
    /**
     * Az ablak ikonja.
     */
    ImageIcon gameIcon;
    /**
     * Az akna ikonja.
     */
    ImageIcon mineIcon;
    /**
     * A zászló ikonja.
     */
    ImageIcon flagIcon;

    /**
     * Az osztály konstruktora, létrehozza és inicializálja a játék ablakát,
     * valamint itt példányosodik a Grid osztály. Bezárás esetén leállítja az időzítőt.
     * @param diff A nehézség, ez alapján állítja be az ablak méretét valamint továbbadja a Grid konstruktorának.
     * @param list A menüben beolvasott lista, továbbadjuk a Grid konstruktorának
     */
    public GameGUI(Difficulty diff, List<Toplist> list) {
        gameFrame = new JFrame("Battakereső");


        gameIcon = new ImageIcon("images\\mine_43x43.png");
        mineIcon = gameIcon;
        flagIcon = new ImageIcon("images\\flag_43x43.png");
        gameFrame.setIconImage(gameIcon.getImage());

        setWindowSize(diff);
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                grid.getTimer().stop();
            }
        });

        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout());

        grid = new Grid(this, diff, list, mineIcon, flagIcon);//grid konstruktorjának a hívása
        gamePanel.add(grid.addBlocks(), BorderLayout.CENTER);

        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);

    }

    /**
     *  Az ablakméretet állítja be, a nehézség függvényében.
     * @param diff - A nehézség.
     */
    public void setWindowSize(Difficulty diff){
        switch (diff){
            case EASY:
                gameFrame.setSize(400,400);
                break;
            case MEDIUM:
                gameFrame.setSize(800,800);
                break;
            case HARD:
                gameFrame.setSize(1200,1200);
                break;
        }
    }

    /**
     * Ablak bezárása, másik osztályban kerül használatra.
     */
    public void closeWindow() {
        gameFrame.dispose();
    }
/*
    @Override
    public void windowClosing(WindowEvent e) {
        grid.getTimer().stop();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
   */
}
