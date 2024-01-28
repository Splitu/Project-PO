import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowPlayers extends JFrame{
    private JPanel JPanel;
    private JRadioButton gkRadioBtn;
    private JRadioButton defRadioBtn;
    private JRadioButton midRadioBtn;
    private JRadioButton forRadioBtn;
    private JButton backBtn;
    private JButton exitBtn;
    private JTable table1;
    private JButton resetBtn;
    private JScrollPane scrollPane;
    private ButtonGroup buttonGroup1;

    private List<Player> allPlayers;
    private DefaultTableModel tableModel;

    public ShowPlayers() {
        super("Show Players");
        this.setContentPane(this.JPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openMenuWindow();
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gkRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPlayersByPosition("Bramkarz");
            }
        });

        defRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPlayersByPosition("Obrońca");
            }
        });

        midRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPlayersByPosition("Pomocnik");
            }
        });

        forRadioBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPlayersByPosition("Napastnik");
            }
        });

        initializeTable();
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetRadioButtons();
                populateTable(allPlayers);
            }
        });
        buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(gkRadioBtn);
        buttonGroup1.add(defRadioBtn);
        buttonGroup1.add(midRadioBtn);
        buttonGroup1.add(forRadioBtn);
    }

    private void initializeTable() {
        String[] columnNames = {"Numer", "Imię i Nazwisko", "Wiek", "Pozycja"};

        tableModel = new DefaultTableModel(columnNames, 0);
        table1.setModel(tableModel);

        // Read data from file
        allPlayers = readPlayersFromFile();

        // Add all players to the table
        for (Player player : allPlayers) {
            tableModel.addRow(new Object[]{player.getNumber(), player.getFullName(), player.getAge(), player.getPosition()});
        }
    }

    private List<Player> readPlayersFromFile() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Projekt/resources/zawodnicy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(":");
                players.add(new Player(playerData[0], playerData[1], playerData[2], playerData[3], Integer.parseInt(playerData[4])));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return players;
    }

    private void filterPlayersByPosition(String position) {
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        model.setRowCount(0); // Clear the table

        // Add players with the specified position to the table
        for (Player player : allPlayers) {
            if (player.getPosition().equalsIgnoreCase(position)) {
                model.addRow(new Object[]{player.getNumber(), player.getFullName(), player.getAge(), player.getPosition()});
            }
        }
    }
    private void openMenuWindow() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
    private void resetRadioButtons() {
        // Zresetuj wybrane RadioButtony
        buttonGroup1.clearSelection();
    }
    private void populateTable(List<Player> players) {
        tableModel.setRowCount(0); // Wyczyszczenie tabeli
        for (Player player : players) {
            tableModel.addRow(new Object[]{player.getNumber(), player.getFullName(), player.getAge(), player.getPosition()});
        }
    }
    public static void main(String[] args) {
        ShowPlayers showPlayers = new ShowPlayers();
        showPlayers.setVisible(true);
    }
}

