import javax.swing.*;

public class Menu extends JFrame{
    private JPanel JPanel1;
    private JRadioButton addDeleteBtn;
    private JRadioButton firstTeamBtn;
    private JRadioButton addScoreBtn;
    private JRadioButton showScoreBtn;
    private JButton exitBtn;
    private JButton submitBtn;
    private JButton logoutBtn;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.setVisible(true);
    }

    public Menu() {
        super("Team Manager App");
        this.setContentPane(this.JPanel1);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 200);
    }
}
