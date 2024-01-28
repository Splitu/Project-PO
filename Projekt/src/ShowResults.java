import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowResults extends JFrame {
    private JPanel JPanel;
    private JButton backBtn;
    private JButton exitBtn;
    private JTextArea textArea1;

    private List<String> results;

    public ShowResults() {
        super("Wyniki");
        this.setIconImage(new ImageIcon("Projekt/resources/ball.png").getImage());
        this.setContentPane(this.JPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        backBtn.addActionListener(e -> {
            dispose();
            openMenuWindow();
        });
        exitBtn.addActionListener(e -> {
            System.exit(0);
        });
        displayResults();
    }

    private void displayResults() {
        results = readResultsFromFile();

        Collections.reverse(results);

        StringBuilder formattedResults = new StringBuilder();

        for (String result : results) {
            String[] parts = result.split(":");
            String date = parts[0];
            String homeTeam = parts[1];
            String awayTeam = parts[2];
            String homeGoals = parts[3];
            String awayGoals = parts[4];

            String formattedResult = date + "\n" + homeTeam + " " + homeGoals + " : " + awayGoals + " " + awayTeam + "\n";
            formattedResults.append(formattedResult).append("\n"); // Dodaj pustą linię po każdym wyniku
        }

        textArea1.setText(formattedResults.toString());
    }

    private List<String> readResultsFromFile() {
        List<String> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Projekt/resources/wyniki.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                results.add(line);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return results;
    }
    private void openMenuWindow() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
    public static void main(String[] args) {
        ShowResults showResults = new ShowResults();
        showResults.setVisible(true);
    }
}
