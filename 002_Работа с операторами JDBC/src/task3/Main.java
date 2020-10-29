// Создайте новый класс с определённым методом main(). В нём создайте коллекцию ArrayList типа
// Abonent с названием abonents.
// Осуществите подключение к БД.
// Выполните команды SQL с текстового файла.
// Выполните запрос на получение результирующего набора, состоящего из всех записей таблицы Phonebook.
// Используя необходимые методы доступа к данным, получите значения всех полей результирующего
// набора для каждой записи. Добавьте в коллекцию abonents новый объект типа класса Abonent и передайте
// значения всех полей одной записи объекта ResultSet в его конструктор.
// Выведите в консоль все элементы коллекции, удовлетворяющие условие id > 3.


package task3;

import task2.Abonent;

import java.util.ArrayList;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        ArrayList<Abonent> abonents = new ArrayList<>();

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
            // Создаем объект ResultSet и пробегаем по результирующему набору,
            // затем передаём данные в конструктор класса Abonent
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * FROM Phonebook");
                while (rs.next()) {
                    int IdPhoneBook = rs.getInt(1);
                    String LastName = rs.getString(2);
                    long Phone = rs.getLong(3);
                    abonents.add(new Abonent(IdPhoneBook, LastName, Phone));
                }

                // Выводим в консоль все элементы коллекции, удовлетворяющие условию id > 3.
                for (Abonent ab : abonents) {
                    if (ab.getId() > 3)
                        System.out.println(ab);
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
