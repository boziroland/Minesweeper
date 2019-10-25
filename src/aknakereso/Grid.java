package aknakereso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;

/**
 * A játék és logikája ebben az osztályban kerül megvalósításra.
 */
public class Grid implements MouseListener {

    /**
     * A pálya mérete.
     */
    private int dim;
    /**
     * Az aknák száma a pályán.
     */
    private int mineCount;
    /**
     * A felderített mezők száma.
     */
    private int clickedBlocks;
    /**
     * Erre kerül a blokkok mátrixa.
     */
    private JPanel panel;
    /**
     * A blokkok mátrixa.
     */
    private Block matrix[][];

    /**
     * Ennek a változónak a segítségével zárjuk be az ablakot játék végekor.
     */
    private GameGUI usedGUI;
    /**
     * Az akna ikonja.
     */
    private ImageIcon mineIcon;
    /**
     * A zászló ikonja.
     */
    private ImageIcon flagIcon;

    /**
     * Az időzítő.
     */
    private Timer timer;
    /**
     * Az időzítő threadje.
     */
    private Thread t;
    /**
     * A toplista melyet frissítünk győzelem esetén.
     */
    private List<Toplist> alist;

    /**
     * Az osztály konstruktora, itt történik a változók inicializálása és a
     * valamint az időzítő threadjének indítása.
     * @param g A {@link Grid#usedGUI} változó inicialiására szolgál.
     * @param diff A játék nehézsége.
     * @param list A toplista.
     * @param minePic Az akna ikonja.
     * @param flagPic A zászló ikonja.
     */
    public Grid(GameGUI g, Difficulty diff, List<Toplist> list, ImageIcon minePic, ImageIcon flagPic) {
        usedGUI = g;
        clickedBlocks = 0;
        alist = list;
        mineIcon = minePic;
        flagIcon = flagPic;

        //System.out.println("SIZE: " + alist.size());
        switch (diff) {
            case EASY:
                dim = 8;
                mineCount = 3; //10
                break;
            case MEDIUM:
                dim = 16;
                mineCount = 40;
                break;
            case HARD:
                dim = 24;
                mineCount = 99;
                break;
        }

        matrix = new Block[dim][dim];

        timer = new Timer();
        t = new Thread(timer);
        t.start();
    }

    /**
     * Itt generálódik le a pálya, valamint a bombák itt kerülnek a megadott
     * mezőkre. A GameGUI osztályban hívódik meg.
     * @return A JPanel melyet rárakunk a GameGUI osztály ablakára.
     */
    public JPanel addBlocks() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(dim, dim));

        Random randRow = new Random();
        Random randColumn = new Random();
        int minesAdded;

        boolean positionsOfMines[][] = new boolean[dim][dim];

        for (minesAdded = 0; minesAdded < mineCount; minesAdded++) {
            int rowOfMine = randRow.nextInt(dim);
            int columnOfMine = randColumn.nextInt(dim);

            if (!positionsOfMines[rowOfMine][columnOfMine]) {
                positionsOfMines[rowOfMine][columnOfMine] = true;
            } else {
                minesAdded--;
            }
        }

        for (int row = 0; row < dim; row++) {
            for (int column = 0; column < dim; column++) {

                matrix[row][column] = new Block(row, column, positionsOfMines[row][column]);
                matrix[row][column].setAdjacentMineCount(row, column, positionsOfMines, dim, dim);

                matrix[row][column].addMouseListener(this);
                //if (positionsOfMines[row][column])
                    //System.out.println(row + " and " + column);

                panel.add(matrix[row][column]);
            }
        }

        return panel;
    }

    /**
     * Bal klikk esetén, ha az adott mezőn van bomba, a játéknak vége,
     * Ha nincs akkor a gomb letiltása és a gomb mellett lévő bombák számának kiírása,
     * kivéve ha 0, akkor a közelben lévő nulla tartalmú mezők felfedése.
     * Ha a mező már jobbklikkelve volt, akkor visszatérés.
     * @param x A gomb x koordinátája.
     * @param y A gomb y koordinátája.
     */
    public void onLeftClick(int x, int y) {
        if (matrix[x][y].isRightClicked()) return;

        matrix[x][y].setBackground(Color.BLACK);

        if (matrix[x][y].hasMine()) {
            matrix[x][y].setBackground(Color.RED);
            matrix[x][y].setIcon(mineIcon);
            matrix[x][y].setDisabledIcon(mineIcon);
            timer.stop();
            JOptionPane.showMessageDialog(null, "You lose! :(");
            usedGUI.closeWindow(); //yikes
        } else if (matrix[x][y].getAdjacentMineCount() != 0) {
            matrix[x][y].updateNameFromModel();
            clickedBlocks++;
        } else revealZeros(x, y, 0);
        matrix[x][y].setClicked(true);
        matrix[x][y].setEnabled(false);

        if (checkIfAllNonMinesClicked()) {
            getNameOnWin();
        }

    }

    /**
     * Jobb klikk esetén az adott mező megjelölése, mégegyzeri jobb klikk
     * esetén a jelölés levétele.
     * @param x A gomb x koordinátája.
     * @param y A gomb y koordinátája.
     */
    public void onRightClick(int x, int y) {
        if (!matrix[x][y].isRightClicked()) {
            matrix[x][y].setRightClicked(true);
            matrix[x][y].setIcon(flagIcon);
        } else {
            matrix[x][y].setRightClicked(false);
            matrix[x][y].setIcon(null);
        }

    }

    /**
     * A 0 tartalmú, valamint a mellettük lévő nem nulla tartalmú mezők
     * felfedése, rekurzívan.
     * @param x A gomb x koordinátája.
     * @param y A gomb y koordinátája.
     * @param previousMineCount A függvény korábbi meghívásakor tartalmazott mezők száma, első meghíváskor 0.
     */
    public void revealZeros(int x, int y, int previousMineCount) {
        if (x < 0 || x > dim - 1 || y < 0 || y > dim - 1 || previousMineCount != 0) return;
        if (!matrix[x][y].isClicked()) {
            matrix[x][y].setClicked(true);
            matrix[x][y].setEnabled(false);
            matrix[x][y].setBackground(Color.BLACK);
            clickedBlocks++;

            if (matrix[x][y].getAdjacentMineCount() != 0)
                matrix[x][y].updateNameFromModel();

            revealZeros(x + 1, y, matrix[x][y].getAdjacentMineCount());
            revealZeros(x - 1, y, matrix[x][y].getAdjacentMineCount());
            revealZeros(x, y - 1, matrix[x][y].getAdjacentMineCount());
            revealZeros(x, y + 1, matrix[x][y].getAdjacentMineCount());
        } else {
            return;
        }
    }

    /**
     * A játékos nevének megkérdezése győzelem esetén, valamint idejének kiírása.
     */
    public void getNameOnWin() {
        int playerTime = timer.getSeconds();
        timer.stop();
        String msg = String.format("You win! :) Your time was %d seconds!\n Please tell me your name!", playerTime);
        String playerName = JOptionPane.showInputDialog(null, msg, "Well done!", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.length() <= 0)
            playerName = "Alapértelmezett Aladár";
        updateToplist(playerName, playerTime);
        usedGUI.closeWindow(); //yikes
        return;

    }

    /**
     * A toplista frissítése győzelem esetén.
     * @param name A játékos neve.
     * @param time A játékos ideje.
     */
    public void updateToplist(String name, int time) {
        int i = 0;
        while (alist.get(i).getTime() < time)
            i++;
        alist.add(i, new Toplist(name, time));
    }

    /**
     * Ellenőrzi, hogy vége van-e már a játéknak, úgy hogy az összes blokk számából kivonja az aknák számát,
     * valamint felfedett blokkokat.
     * @return true, ha vége van, egyébként false
     */
    public boolean checkIfAllNonMinesClicked() {
        //System.out.println(dim * dim - clickedBlocks - mineCount);
        return (dim * dim - clickedBlocks - mineCount) <= 0;
    }

    /**
     * A timer getterje.
     * @return A timer.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Kattintás esetén nézi, hogy jobb, vagy bal klikk volt-e,
     * és meghívja a megfelelő függvényt.
     * @param e A MouseEvent.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Block block = (Block) e.getSource();
        if (e.getButton() == MouseEvent.BUTTON1) { // left click
            this.onLeftClick(block.getfX(), block.getfY());
        }
        if (e.getButton() == MouseEvent.BUTTON3) { //right click
            this.onRightClick(block.getfX(), block.getfY());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
