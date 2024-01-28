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
                    openMenuWindow();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Błędny login lub hasło!");
                }
            }
        });

        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private boolean checkCredentials(String login, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("Projekt/resources/użytkownicy.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 4) {
                    String storedLogin = parts[2];
                    String storedPassword = parts[3];
                    if (login.equals(storedLogin) && password.equals(storedPassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void openMenuWindow() {
        Menu menu = new Menu();
        menu.setVisible(true);
    }
}