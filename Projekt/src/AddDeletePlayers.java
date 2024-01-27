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

        // Inicjalizacja modelu listy
        listModel = new DefaultListModel<>();
        playerList.setModel(listModel);

        // Wypełnianie listy zawodnikami z pliku
        populatePlayerList();

        // Obsługa przycisku "Wróć"
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Zamknij okno dodawania/usuwania zawodników
                // Tutaj dodaj kod, który przeniesie użytkownika do okna menu
            }
        });

        // Obsługa przycisku "Edytuj"
        editBtn.setEnabled(false); // Przycisk "Edytuj" jest wyłączony na początku
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pobierz dane z pól tekstowych
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dateOfBirth = dateField.getText();
                String position = positionField.getText();
                String number = numberField.getText();

                // Pobierz numer wybranego zawodnika
                String selectedPlayerNumber = playerList.getSelectedValue().split("\\. ")[0];

                // Zaktualizuj dane wybranego zawodnika w pliku "zawodnicy.txt"
                List<String> allPlayersData = readPlayersFromFile();
                for (int i = 0; i < allPlayersData.size(); i++) {
                    String[] playerInfo = allPlayersData.get(i).split(":");
                    if (playerInfo[4].equals(selectedPlayerNumber)) {
                        // Znaleziono wybranego zawodnika, zaktualizuj jego dane
                        allPlayersData.set(i, firstName + ":" + lastName + ":" + dateOfBirth + ":" + position + ":" + number);
                        break;
                    }
                }
                if (numberAlreadyExists(number)) {
                    showNumberTakenMessage();
                    return;
                }
                // Zapisz zaktualizowane dane do pliku
                saveDataToFile(allPlayersData);

                // Zaktualizuj listę zawodników w interfejsie
                populatePlayerList();

                // Wyłącz przycisk "Edytuj" po zakończeniu edycji
                editBtn.setEnabled(false);

                clearTextFields();
            }
        });

        // Obsługa przycisku "Dodaj"
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pobierz dane nowego zawodnika
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String dateOfBirth = dateField.getText();
                String position = positionField.getText();
                String number = numberField.getText();

                // Tworzenie danych zawodnika
                String newPlayerData = firstName + ":" + lastName + ":" + dateOfBirth + ":" + position + ":" + number;

                // Sprawdzenie, czy zawodnik o takim imieniu i nazwisku już istnieje
                if (playerAlreadyExists(firstName, lastName)) {
                    JOptionPane.showMessageDialog(AddDeletePlayers.this, "Zawodnik o podanych danych już istnieje.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (numberAlreadyExists(number)) {
                    showNumberTakenMessage();
                    return;
                }
                // Zapisz nowego zawodnika do pliku
                savePlayerToFile(newPlayerData);

                // Odśwież listę zawodników
                populatePlayerList();

                // Wyczyść pola tekstowe po dodaniu zawodnika
                clearTextFields();
            }
        });

        // Obsługa przycisku "Usuń"
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Tutaj dodaj kod obsługi przycisku "Usuń"
            }
        });
    }

    private void populatePlayerList() {
        // Wyczyść istniejące dane z listy
        listModel.clear();

        List<String> playersData = readPlayersFromFile();

        // Sortowanie zawodników według numerów na koszulkach
        playersData.sort(Comparator.comparingInt(player -> Integer.parseInt(player.split(":")[4])));

        // Dodanie posegregowanych zawodników do listy
        for (String playerData : playersData) {
            String[] playerInfo = playerData.split(":");
            String numerNaKoszulce = playerInfo[4];
            String imieINazwisko = playerInfo[0] + " " + playerInfo[1];
            String pozycja = playerInfo[3];
            listModel.addElement(numerNaKoszulce + ". " + imieINazwisko + " - " + pozycja);
        }

        playerList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Pobierz zaznaczony element z listy
                String selectedPlayer = playerList.getSelectedValue();
                if (selectedPlayer != null) {
                    // Rozdziel dane wybranego zawodnika
                    String[] playerData = selectedPlayer.split("\\. "); // Dzielimy po ". " aby otrzymać tylko dane
                    String selectedPlayerNumber = playerData[0];
                    // Iterujemy po zawodnikach w pliku i znajdujemy wybranego zawodnika
                    for (String player : playersData) {
                        String[] playerInfo = player.split(":");
                        String numerNaKoszulce = playerInfo[4];
                        if (numerNaKoszulce.equals(selectedPlayerNumber)) {
                            // Jeśli numer na koszulce pasuje, ustaw dane w polach tekstowych
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
                    // Jeśli nie wybrano zawodnika, wyłącz przycisk "Edytuj"
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

    // Metoda do zapisywania danych do pliku "zawodnicy.txt"
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

    // Metoda do zapisywania nowego zawodnika do pliku
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddDeletePlayers frame = new AddDeletePlayers();
            frame.setVisible(true);
        });
    }
}