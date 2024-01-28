import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AddDeletePlayers extends JFrame {
    private JPanel JPanel;
    private JList<String> playerList;
    private DefaultListModel<String> listModel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateField;
    private JTextField positionField;
    private JTextField numberField;
    private JButton editBtn;
    private JButton addBtn;
    private JButton backBtn;
    private JButton deleteBtn;
    private JTextPane infoTextPane;

    public AddDeletePlayers() {
        super("Dodaj/Usuń Zawodnika");
        this.setContentPane(this.JPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);

        listModel = new DefaultListModel<>();
        playerList.setModel(listModel);

        populatePlayerList();
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openMenuWindow();
            }
        });

        editBtn.setEnabled(false);
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pobierz dane z pól tekstowych
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dateOfBirth = dateField.getText();
                String position = positionField.getText();
                String number = numberField.getText();

                String selectedPlayerNumber = playerList.getSelectedValue().split("\\. ")[0];

                List<String> allPlayersData = readPlayersFromFile();
                for (int i = 0; i < allPlayersData.size(); i++) {
                    String[] playerInfo = allPlayersData.get(i).split(":");
                    if (playerInfo[4].equals(selectedPlayerNumber)) {
                        allPlayersData.set(i, firstName + ":" + lastName + ":" + dateOfBirth + ":" + position + ":" + number);
                        break;
                    }
                }
                if (numberAlreadyExists(number)) {
                    showNumberTakenMessage();
                    return;
                }
                saveDataToFile(allPlayersData);
                populatePlayerList();
                editBtn.setEnabled(false);

                clearTextFields();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dateOfBirth = dateField.getText();
                String position = positionField.getText();
                String number = numberField.getText();
                String newPlayerData = firstName + ":" + lastName + ":" + dateOfBirth + ":" + position + ":" + number;
                if (playerAlreadyExists(firstName, lastName)) {
                    JOptionPane.showMessageDialog(AddDeletePlayers.this, "Zawodnik o podanych danych już istnieje.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (numberAlreadyExists(number)) {
                    showNumberTakenMessage();
                    return;
                }
                savePlayerToFile(newPlayerData);
                populatePlayerList();
                clearTextFields();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPlayer = playerList.getSelectedValue();
                if (selectedPlayer != null) {
                    String[] playerData = selectedPlayer.split("\\. ");
                    String selectedPlayerNumber = playerData[0];
                    List<String> allPlayersData = readPlayersFromFile();
                    int indexToRemove = -1;
                    for (int i = 0; i < allPlayersData.size(); i++) {
                        String[] playerInfo = allPlayersData.get(i).split(":");
                        if (playerInfo[4].equals(selectedPlayerNumber)) {
                            indexToRemove = i;
                            break;
                        }
                    }
                    if (indexToRemove != -1) {
                        allPlayersData.remove(indexToRemove);
                        listModel.removeElement(selectedPlayer);
                        saveDataToFile(allPlayersData);
                        clearTextFields();
                    }
                }
            }
        });

    }

    private void populatePlayerList() {
        listModel.clear();

        List<String> playersData = readPlayersFromFile();

        playersData.sort(Comparator.comparingInt(player -> Integer.parseInt(player.split(":")[4])));

        for (String playerData : playersData) {
            String[] playerInfo = playerData.split(":");
            String numerNaKoszulce = playerInfo[4];
            String imieINazwisko = playerInfo[0] + " " + playerInfo[1];
            String pozycja = playerInfo[3];
            listModel.addElement(numerNaKoszulce + ". " + imieINazwisko + " - " + pozycja);
        }

        playerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedPlayer = playerList.getSelectedValue();
                if (selectedPlayer != null) {
                    String[] playerData = selectedPlayer.split("\\. "); // Dzielimy po ". " aby otrzymać tylko dane
                    String selectedPlayerNumber = playerData[0];
                    for (String player : playersData) {
                        String[] playerInfo = player.split(":");
                        String numerNaKoszulce = playerInfo[4];
                        if (numerNaKoszulce.equals(selectedPlayerNumber)) {
                            firstNameField.setText(playerInfo[0]);
                            lastNameField.setText(playerInfo[1]);
                            dateField.setText(playerInfo[2]);
                            positionField.setText(playerInfo[3]);
                            numberField.setText(playerInfo[4]);
                            editBtn.setEnabled(true);
                            break;
                        }
                    }
                } else {
                    editBtn.setEnabled(false);
                }
            }
        });
    }

    private List<String> readPlayersFromFile() {
        List<String> playersData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Projekt/resources/zawodnicy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                playersData.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return playersData;
    }

    private void saveDataToFile(List<String> playersData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Projekt/resources/zawodnicy.txt"))) {
            for (String playerData : playersData) {
                writer.println(playerData);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private boolean playerAlreadyExists(String firstName, String lastName) {
        List<String> allPlayersData = readPlayersFromFile();
        for (String player : allPlayersData) {
            String[] playerInfo = player.split(":");
            if (playerInfo[0].equals(firstName) && playerInfo[1].equals(lastName)) {
                return true;
            }
        }
        return false;
    }
    private boolean numberAlreadyExists(String number) {
        List<String> allPlayersData = readPlayersFromFile();
        for (String player : allPlayersData) {
            String[] playerInfo = player.split(":");
            if (playerInfo[4].equals(number)) {
                return true;
            }
        }
        return false;
    }
    private void showNumberTakenMessage() {
        JOptionPane.showMessageDialog(AddDeletePlayers.this, "Ten numer jest już zajęty!", "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    private void savePlayerToFile(String playerData) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Projekt/resources/zawodnicy.txt", true))) {
            writer.println(playerData);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void clearTextFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        dateField.setText("");
        positionField.setText("");
        numberField.setText("");
    }
    private void openMenuWindow() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddDeletePlayers frame = new AddDeletePlayers();
            frame.setVisible(true);
        });
    }
}