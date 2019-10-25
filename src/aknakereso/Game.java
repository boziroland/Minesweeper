package aknakereso;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

/**
 * A játék főosztálya
 */
public class Game {
    /**
     * A program main függvénye. Létrehozza, beállítja a fő JFrame-et és átadja
     * a menünek. Amennyiben a programot terminálja a felhasználó, egy
     * WindowListener kiírja a háttértárra a toplista jelenlegi állását.
     *
     */
    public static void main(String args[]) {

        JFrame gameFrame = new JFrame("Battakereső");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setSize(1280, 720);
        gameFrame.setLocationRelativeTo(null);

        MenuGUI menu = new MenuGUI(gameFrame);

        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                List<Toplist> alist = menu.getFo().getList();
                menu.getFo().writeFile(alist);
            }
        });
    }

}
