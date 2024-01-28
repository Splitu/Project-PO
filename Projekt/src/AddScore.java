import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddScore extends JFrame{
    private JPanel JPanel;
    private JButton backBtn;
    private JButton exitBtn;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton sendBtn;

    public AddScore() {
        super("Team Manager App");
        this.setContentPane(this.JPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        backBtn.addActionListener(e -> {
            dispose();
            openMenuWindow();
        });

        exitBtn.addActionListener(e -> {
            System.exit(0);
        });

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String date = textField1.getText();
                String homeTeam = textField2.getText();
                String homeGoals = textField3.getText();
                String awayTeam = textField4.getText();
                String awayGoals = textField5.getText();

                if (date.isEmpty() || homeTeam.isEmpty() || homeGoals.isEmpty() || awayTeam.isEmpty() || awayGoals.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Wypełnij wszystkie pola!", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowy format daty! Wprowadź datę w formacie DD/MM/RRRR.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Integer.parseInt(homeGoals);
                    Integer.parseInt(awayGoals);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Nieprawidłowa liczba goli! Wprowadź liczbę całkowitą dla goli.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String matchResult = date + ":" + homeTeam + ":" + awayTeam + ":" + homeGoals + ":" + awayGoals;

                try (FileWriter writer = new FileWriter("Projekt/resources/wyniki.txt", true)) {
                    writer.write(matchResult + "\n");
                    JOptionPane.showMessageDialog(null, "Wynik meczu został zapisany pomyślnie!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Wystąpił błąd podczas zapisu wyniku meczu.", "Błąd", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    private void openMenuWindow() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }

    public static void main(String[] args) {
        AddScore addScore = new AddScore();
        addScore.setVisible(true);
    }
}
