package aknakereso;

import java.io.Serializable;

/**
 * Szerializálható osztály, a nevet és az időt tárolja.
 */
public class Toplist implements Serializable {
    /**
     * Játékos neve.
     */
    private String name;
    /**
     * Játékos ideje.
     */
    private int time;

    /**
     * Kontruktor, inicializál.
     * @param name Ezt állítjuk be a játékosnévnek.
     * @param time Ezt állítjuk be a játékosidőnek.
     */
    public Toplist(String name, int time) {
        this.name = name;
        this.time = time;
    }

    /**
     * Név visszaadása.
     * @return A név.
     */
    public String getName() {
        return name;
    }

    /**
     * Idő visszaadása.
     * @return Az idő.
     */
    public int getTime() {
        return time;
    }
}
