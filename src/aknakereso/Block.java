package aknakereso;

import javax.swing.*;
import java.awt.*;

/**
 * A mezők osztálya. Tudják a helyüket a mátrixban.
 */
public class Block extends JButton {
    /**
     * A mező x koordinátája.
     */
    private int fX;
    /**
     * A mező y koordinátája.
     */
    private int fY;

    /**
     * Van-e akna az adott blokkon.
     */
    private boolean hasMine;
    /**
     * Felfedett-e már a blokk tartalma.
     */
    private boolean isClicked;
    /**
     * Megjelölt-e a blokk.
     */
    private boolean isRightClicked;
    /**
     * A blokk-kal szomszédos aknák száma.
     */
    private int adjacentMineCount;

    /**
     * Konstruktor, inicializálja a példányt.
     * @param x A mátrixban elfoglalt x koordinátája.
     * @param y A mátrixban elfoglalt y koordinátája.
     * @param getsMine Kap-e aknát a mező.
     */
    public Block(int x, int y, boolean getsMine) {
        fX = x;
        fY = y;

        isClicked = false;
        isRightClicked = false;
        hasMine = getsMine;
        adjacentMineCount = 0;
        setBackground(Color.DARK_GRAY);
    }

    /**
     * Beállítja a {@link Block#adjacentMineCount} változó értékét
     * @param x A mező x koordinátája.
     * @param y A mező y koordinátája
     * @param matrix A bool mátrix, mely megadja, hogy mely mezőkben van akna.
     * @param width A mátrix hossza.
     * @param height A mátrix magassága.
     */
    public void setAdjacentMineCount(int x, int y, boolean matrix[][], int width, int height) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!hasMine && i >= 0 && i < width && j >= 0 && j < height) {
                    if (matrix[i][j]) {
                        adjacentMineCount++;
                    }
                }
            }
        }
    }

    /**
     * A mező mellett lévő aknák számának kiírása. (A mezőn való kattintás esetén hívódik meg.)
     */
    public void updateNameFromModel() {
        setText(String.valueOf(adjacentMineCount));
    }

    /**
     * A mező x koordinátájának visszaadása.
     * @return x koordináta
     */
    public int getfX() {
        return fX;
    }

    /**
     * A mező y koordinátájának visszaadása.
     * @return y koordináta
     */
    public int getfY() {
        return fY;
    }

    /**
     * Annak a visszaadása, hogy van-e akna az adott mezőn.
     * @return igen/nem
     */
    public boolean hasMine() {
        return hasMine;
    }

    /**
     * Annak a visszaadása, hogy kattintva van-e a mező.
     * @return igen/nem
     */
    public boolean isClicked() {
        return isClicked;
    }

    /**
     * A mező {@link Block#isClicked} változójának beállítása.
     * @param clicked Az érték amire beállítjuk.
     */
    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    /**
     * Annak a visszaadása, hogy jobb gombbal kattintva van-e a mező.
     * @return True, ha jobb klikkelve van, egyébként false.
     */
    public boolean isRightClicked() {
        return isRightClicked;
    }

    /**
     * A mező {@link Block#isRightClicked} változójának beállítása.
     * @param rightClicked Az érték amire beállítjuk.
     */
    public void setRightClicked(boolean rightClicked) {
        isRightClicked = rightClicked;
    }

    /**
     * Visszaadja hány akna van a mező szomszédságában.
     * @return A mező szomszédságában lévő aknák száma.
     */
    public int getAdjacentMineCount() {
        return adjacentMineCount;
    }

}
