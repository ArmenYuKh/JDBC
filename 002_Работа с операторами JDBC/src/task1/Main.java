// Создайте новый командный файл SQL с произвольным именем.
// Создайте новую таблицу с именем Phonebook. Таблица должна содержать поля:
// числовое – IdPhoneBook (первичный ключ), символьное – LastName и числовое – Phone.
// Добавьте в таблицу 5-7 записей.

package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        // Задаем параметры подключения
        String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Создаем подключение, объект Statement и читаем командный файл
        // Scanner используем в связке с BufferedReader для расширения функционала взаимодействия с читаемым файлом
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             BufferedReader sqlFile = new BufferedReader(new FileReader("C:\\Users\\ASSASSIN\\Documents\\Java\\JDBC\\MyHomeWork\\Lesson2\\src\\task1\\Phonebook.sql")); // введите путь к командному файлу
             Scanner scan = new Scanner(sqlFile);
             Statement statement = connection.createStatement()) {

            String line = "";
            while (scan.hasNextLine()) {
                line = scan.nextLine();
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }
                statement.executeUpdate(line);
            }
            // Создаем объект ResultSet и пробегаем по результирующему набору с выводом данных в консоль
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * FROM Phonebook");
                while (rs.next()) {
                    int IdPhoneBook = rs.getInt(1);
                    String LastName = rs.getString(2);
                    long Phone = rs.getLong(3);
                    System.out.println("Id = " + IdPhoneBook + " lastname = " + LastName + " phone = " + Phone);
                }

                // обрабатываем возможные исключения
            } catch (SQLException ex) {
                System.err.println("SQLException message: " + ex.getMessage());
                System.err.println("SQLException SQL state: " + ex.getSQLState());
                System.err.println("SQLException error code: " + ex.getErrorCode());
                // Освобождаем ресурсы затраченные на ResultSet
            } finally {
                if (rs != null) {
                    rs.close();
                } else {
                    System.err.println("Ошибка чтения данных с БД!");
                }
            }
        }
    }
}
