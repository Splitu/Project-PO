import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PasswordCheck extends JFrame {
    private JPanel JPanel1;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JButton exitBtn;

    public static void main(String[] args) {
        PasswordCheck passwordCheck = new PasswordCheck();
        passwordCheck.setVisible(true);
    }

    public PasswordCheck() {
        super("Team Manager App");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String password = new String(passwordField.getPassword());

                if (checkCredentials(login, password)) {
                    // Jeśli dane logowania są poprawne, otwórz nowe okno "Menu" i zamknij obecne
                    openMenuWindow();
                    dispose(); // Zamknij okno logowania
                } else {
                    // Jeśli dane logowania są niepoprawne, wyświetl komunikat
                    JOptionPane.showMessageDialog(null, "Błędny login lub hasło!");
                }
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zamknij aplikację po kliknięciu przycisku "Wyjście"
                System.exit(0);
            }
        });
    }

    // Metoda sprawdzająca dane logowania
    private boolean checkCredentials(String login, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("Projekt/resources/użytkownicy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Zakładając, że plik tekstowy ma format: imię:nazwisko:login:hasło
                String[] parts = line.split(":");
                if (parts.length == 4) {
                    String storedLogin = parts[2];
                    String storedPassword = parts[3];
                    if (login.equals(storedLogin) && password.equals(storedPassword)) {
                        System.out.println(storedLogin);
                        System.out.println(storedPassword);
                        return true; // Znaleziono pasujące dane logowania
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; // Nie znaleziono pasujących danych logowania
    }

    // Metoda otwierająca okno "Menu"
    private void openMenuWindow() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}