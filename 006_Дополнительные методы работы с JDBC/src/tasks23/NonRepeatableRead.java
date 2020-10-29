// Задание 2
// Изучите типы чтения транзакций и смоделируйте их в коде с помощью таблицы Phonebook, созданной в предыдущих домашних заданиях.

// Задание 3
// Примените константы уровней изоляции транзакций к ситуациям, созданным в задании 2.

package tasks23;

import java.sql.*;

public class NonRepeatableRead {
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

            // non-repeatable read
//            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // чтение заблокировано (это значение достаточно установить в одной транзакции)
            ResultSet resultSet = stat.executeQuery("SELECT * FROM phonebook");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("LastName") + " " + resultSet.getLong(3));
            }
            new OtherTransaction().start();
            Thread.sleep(2000);

            ResultSet resultSet2 = stat.executeQuery("SELECT * FROM phonebook");
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

                // non-repeatable read
//                conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED); // чтение разрешено
                conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ); // чтение заблокировано
                stat.executeUpdate("UPDATE phonebook SET phone = phone + 1111111 WHERE LastName = 'Сидоров'");
                conn.commit();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
