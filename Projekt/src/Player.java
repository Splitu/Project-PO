import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Player {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String position;
    private int number;

    public Player(String firstName, String lastName, String dateOfBirth, String position, int number) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = String.valueOf(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.position = position;
        this.number = number;
    }


    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getAge() {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(LocalDate.parse(dateOfBirth), currentDate);
        return period.getYears();
    }

    public String getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }
}
