package aknakereso;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A fájlműveletek osztálya.
 */
public class FileOperations {

    /**
     * A toplista, melybe beolvasunk.
     */
    List<Toplist> list;
    /**
     * A nehézség, mely eldönti melyik fájlból olvassuk be a toplistát.
     */
    Difficulty diff;
    /**
     * A fájl.
     */
    File f;

    /**
     * Az osztály konstruktora, a nehézség alapján eldönti, hogy  melyik fájl legyen beolvasva.
     * @param difficulty A nehézség.
     */
    public FileOperations(Difficulty difficulty) {
        diff = difficulty;
        list = new ArrayList<Toplist>();

        switch (diff) {
            case EASY:
                f = new File("toplist\\toplist_easy.txt");
                break;
            case MEDIUM:
                f = new File("toplist\\toplist_medium.txt");
                break;
            case HARD:
                f = new File("toplist\\toplist_hard.txt");
                break;
        }
    }

    /**
     * A fájl beolvasása.
     * @return A toplista melybe beolvastunk.
     */
    public List<Toplist> readFile() {
        String tmpStr;
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(f));
            while ((tmpStr = br.readLine()) != null) {
                String[] parts = tmpStr.split(";");
                list.add(new Toplist(parts[0], Integer.parseInt(parts[1])));

            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * A toplista kiírása.
     * @param list A toplista melyet ki akarjuk írni.
     */
    public void writeFile(List<Toplist> list) {

        BufferedWriter bw;
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end of if

        try {
            bw = new BufferedWriter(new FileWriter(f));
            for (Toplist var : list) {
                bw.write(var.getName() + ";" + var.getTime());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * A toplista getterje.
     * @return A toplsita.
     */
    public List<Toplist> getList() {
        return list;
    }
}
