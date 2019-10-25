package aknakereso;

/**
 * Az időmérő osztálya.
 */
public class Timer implements Runnable {
    /**
     * Ennyi ideje fut a játék.
     */
    private int seconds;
    /**
     * Jelzi a játék végét.
     */
    private boolean gameEnded;

    {
        seconds = 0;
        gameEnded = false;
    }

    /**
     * A thread a játék végéig másodpercenként növeli a {@link Timer#seconds} változót,
     * a játék végén pedig megáll és megsemmisül.
     */
    @Override
    public void run() {
        while (!gameEnded) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds++;
            //System.out.println(seconds + " s");
        }
    }

    /**
     * Ezzel állítjuk meg a threadet.
     */
    public void stop() {
        gameEnded = true;
    }

    /**
     *  A másodpercek visszaadása.
     * @return A másodpercek.
     */
    public int getSeconds() {
        return seconds;
    }

}
