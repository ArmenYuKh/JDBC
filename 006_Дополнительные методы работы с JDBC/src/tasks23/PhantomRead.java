// Задание 2
// Изучите типы чтения транзакций и смоделируйте их в коде с помощью таблицы Phonebook, созданной в предыдущих домашних заданиях.

// Задание 3
// Примените константы уровней изоляции транзакций к ситуациям, созданным в задании 2.

package tasks23;

import java.sql.*;

public class PhantomRead {
    // Задаем параметры подключения, но делаем их статическими
    static String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
    static String userName = "root";
    static String password = "root";

    public static void main(String[] args) throws SQLException, InterruptedException {
        // Создаем подключение и получаем объект типа Statement
        try(Connection conn = DriverManager.getConnection(url, userName, password);
            Statement stat = conn.createStatement()) {
            // Отключаем режим автоматической фиксации результатов выполнения команд SQL
            conn.setAutoCommit(false);

            // phantom read
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // чтение заблокировано
            ResultSet resultSet = stat.executeQuery("SELECT * FROM phonebook WHERE IdPhoneBook > 10");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("LastName") + " " + resultSet.getLong(3));
            }
            new OtherTransaction().start();
            Thread.sleep(2000);

            ResultSet resultSet2 = stat.executeQuery("SELECT * FROM phonebook WHERE IdPhoneBook > 10");
            while (resultSet2.next()) {
                System.out.println(resultSet2.getString("LastName") + " " + resultSet2.getLong(3));
            }
        }
    }

    // Класс, в котором реализуются транзакции для чтения
    static class OtherTransaction extends Thread {
        @Override
        public void run() {
            try (Connection conn = DriverManager.getConnection(url, userName, password);
                 Statement stat = conn.createStatement()) {
                // Отключаем режим автоматической фиксации результатов выполнения команд SQL
                conn.setAutoCommit(false);

                // phantom read
//                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE); // чтение заблокировано
                stat.executeUpdate("INSERT INTO phonebook (LastName, Phone) VALUES ('Третьяков', 79663333333)");
                conn.commit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
