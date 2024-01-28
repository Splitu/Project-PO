import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel JPanel1;
    private JRadioButton addDeleteBtn;
    private JRadioButton showPlayersBtn;
    private JRadioButton addScoreBtn;
    private JRadioButton showResultsBtn;
    private JButton exitBtn;
    private JButton submitBtn;
    private JButton logoutBtn;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }

    public Menu() {
        super("Team Manager App");
        this.setIconImage(new ImageIcon("Projekt/resources/ball.png").getImage());
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 250);
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (addDeleteBtn.isSelected()) {
                    AddDeletePlayers addDeletePlayers = new AddDeletePlayers();
                    addDeletePlayers.setVisible(true);
                } else if(showPlayersBtn.isSelected()) {
                    ShowPlayers showPlayers = new ShowPlayers();
                    showPlayers.setVisible(true);
                } else if(showResultsBtn.isSelected()) {
                    ShowResults showResults = new ShowResults();
                    showResults.setVisible(true);
                } else if(addScoreBtn.isSelected()) {
                    AddScore addScore = new AddScore();
                    addScore.setVisible(true);
                }
                dispose();
            }
        });
        exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        logoutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                openLoginWindow();
            }
        });
    }
    private void openLoginWindow() {
        Login login = new Login();
        login.setVisible(true);
    }
}
