package aknakereso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * A menü felhasználói interfészének az osztálya, a menü minimális
 * logikájával együtt.
 *
 */
public class MenuGUI implements ActionListener {
    /**
     * Új játék kezdéséhoz gomb.
     */
    private JButton startGame = new JButton("Start game");
    /**
     * A toplista megtekintéséhez használt gomb.
     */
    private JButton toplist = new JButton("Toplist");
    /**
     * A nehézség beállításához használt gomb.
     */
    private JButton setDifficulty = new JButton("Difficulty");
    /**
     *  A nehézség aktuális értékét tároló enum változó.
     */
    private Difficulty diff;
    /**
     * A toplista fájlbaírásához, valamint az abból való olvasáshoz használt változó.
     */
    private FileOperations fo;
    /**
     * A jelenleg memóriában tárolt toplista.
     */
    private List<Toplist> toplistMenu;

    /**
     * Az osztály konstruktora, beállítja az ikont, a háttéret, a gombméreteket,
     * elhelyezi az ablakon a gombokat, valamint beállítja az alapértelmezett nehézséget,
     * és beolvassa az ehhez a nehézséghez tartozó toplistát.
     * @param menuFrame Az ablak melyen a módosításokat végezni kell.
     */
    public MenuGUI(JFrame menuFrame) {

        ImageIcon gameIcon = new ImageIcon("images\\mine_43x43.png");
        menuFrame.setIconImage(gameIcon.getImage());

        JLabel backgroundLabel = new JLabel(new ImageIcon("images\\minefield.jpg"));
        //JLabel backgroundLabel = new JLabel();

        startGame.setPreferredSize(new Dimension(150, 40));
        toplist.setPreferredSize(new Dimension(150, 40));
        setDifficulty.setPreferredSize(new Dimension(150, 40)); //300 80

        startGame.addActionListener(this);
        toplist.addActionListener(this);
        setDifficulty.addActionListener(this);

        backgroundLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 50, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundLabel.add(startGame, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundLabel.add(toplist, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        backgroundLabel.add(setDifficulty, gbc);

        diff = Difficulty.MEDIUM;
        fo = new FileOperations(diff);
        toplistMenu = fo.readFile();

        menuFrame.setContentPane(backgroundLabel);
        menuFrame.setVisible(true);
    }

    /**
     * Figyeli, hogy melyik gombra kattintott a felhasználó és meghívja a gombhoz
     * illő függvényt. Amennyiben új játékot kezd, ellenőrzi, hogy a lista üresse, amennyiben
     * igen, beolvassa a háttértárról, ezzel elkerülve egy esetleges kivételdobást.
     * @param e Az event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            if (toplistMenu.size() == 0)
                toplistMenu = fo.readFile();
            new GameGUI(diff, toplistMenu);
        } else if (e.getSource() == toplist) {
            createTable();
        } else {
            changeDifficulty();
        }

    }

    /**
     * Amennyiben a felhasználó a toplista gombra kattint,  létrehoz egy új ablakot, belerak
     * egy táblázatot és feltölti a már eltárolt listával.
     *
     */
    public void createTable() {
        JFrame tableFrame = new JFrame();
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.setSize(500, 300);
        tableFrame.setTitle(diff.name() + " mode rankings");
        tableFrame.setLocationRelativeTo(null);

        String[] header = {"Position", "Name", "Time"};
        DefaultTableModel tableModel = new DefaultTableModel(header, 0);
        JTable table = new JTable(tableModel);
        if (table.getRowCount() == 0)
            for (int i = 0; i < toplistMenu.size(); i++) {
                int pos = i + 1;
                String name = toplistMenu.get(i).getName();
                int time = toplistMenu.get(i).getTime();

                Object[] data = {pos, name, time};
                tableModel.addRow(data);
            }
        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    /**
     * Nehézségválasztás esetén hívódik meg, és megváltoztatja a {@link MenuGUI#diff}
     * változó értékét a kívántra. Ezen kívül kicseréli a toplista tartalmát is az adott
     * nehézséghez tartozóra.
     */
    public void changeDifficulty() {
        Object[] options = {"Easy", "Medium", "Hard"};

        JPanel panel = new JPanel();
        panel.add(new JLabel("Pick your difficulty!"));

        int result = JOptionPane.showOptionDialog(null, panel, "Difficulty",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, null);

        switch (result) {
            case JOptionPane.YES_OPTION: //Easy
                diff = Difficulty.EASY;
                changeToplistContents(diff);
                break;
            case JOptionPane.NO_OPTION: //Medium
                diff = Difficulty.MEDIUM;
                changeToplistContents(diff);
                break;
            case JOptionPane.CANCEL_OPTION: //Hard
                diff = Difficulty.HARD;
                changeToplistContents(diff);
                break;
        }
    }

    /**
     * A toplista tartalmának megváltoztatása.
     * @param diff Ettől a változótól függ, hogy melyik nehézséghez tartozó toplistát
     * olvasunk be.
     */
    public void changeToplistContents(Difficulty diff) {
        fo.writeFile(toplistMenu);
        fo = new FileOperations(diff);
        toplistMenu = fo.readFile();

    }

    /**
     * Getter függvény, ahhoz kell, hogy a menüből való váratlan bezárás esetén
     * ki tudjuk írni a toplista tartalmát, tudjuk frissíteni a fájlt.
     * @return A FileOperations példány.
     */
    public FileOperations getFo() {
        return fo;
    }
}