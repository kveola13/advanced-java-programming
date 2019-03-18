import java.sql.SQLException;

public class AccountApp {

    public static void main(String[] args) throws SQLException {
        Country country = new Country();
        country.connectToServerAndInsertData();
    }
}