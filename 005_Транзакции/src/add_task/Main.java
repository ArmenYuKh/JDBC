// Создайте точку сохранения для отката транзакции в таблицу Vegetables,
// в случае возникновения любых ошибок при выполнении команд. Откат должен происходить до команды создания таблицы.

package add_task;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             Statement stat = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXISTS Vegetables (name VARCHAR(15) NOT NULL, amount INTEGER, price DOUBLE NOT NULL, PRIMARY KEY (name))";
            String command1 = "INSERT INTO Fruit (name, amount, price) VALUES ('Potato', 500, 2.50)";
            String command2 = "INSERT INTO Fruit (name, amount, price) VALUES ('Onion', 300, 2.80)";
            String command3 = "INSERT INTO Fruit (name, amount, price) VALUES ('Carrot', 80, 3.50)";
            String command4 = "INSERT INTO Fruit (name, amount, price) VALUES ('Cucumber', 400, 4.00)";

            connection.setAutoCommit(false);
            stat.executeUpdate(createTable);
            Savepoint spt = connection.setSavepoint();
            stat.executeUpdate(command1);
            stat.executeUpdate(command2);
            stat.executeUpdate(command3);
            stat.executeUpdate(command4);
            connection.rollback(spt);
            connection.commit();
            connection.releaseSavepoint(spt);
        }
    }
}
