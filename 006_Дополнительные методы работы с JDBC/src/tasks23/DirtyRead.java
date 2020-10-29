// Задание 2
// Изучите типы чтения транзакций и смоделируйте их в коде с помощью таблицы Phonebook, созданной в предыдущих домашних заданиях.

// Задание 3
// Примените константы уровней изоляции транзакций к ситуациям, созданным в задании 2.

package tasks23;

import java.sql.*;

public class DirtyRead {
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

            // dirty read
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // чтение разрешено
            stat.executeUpdate("UPDATE phonebook SET Phone = 79261111111 WHERE IdPhoneBook = 1");
            new OtherTransaction().start();
            Thread.sleep(2000);
            conn.rollback();
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

                // dirty read
                conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED); // чтение заблокировано
                ResultSet rs = stat.executeQuery("SELECT * FROM phonebook");
                while (rs.next()) {
                    System.out.println(rs.getString("LastName") + " " + rs.getLong(3));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
