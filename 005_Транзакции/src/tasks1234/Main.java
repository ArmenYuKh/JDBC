// Задание 1
// Создайте новый класс с именем на Ваше усмотрение и определите в нем главный статический метод.
// В методе задайте параметры подключения к БД, в которой хранится таблица Phonebook, а также создайте коллекцию
// типа String (например ArrayList). Выполните подключение к БД.

// Задание 2
// В главном методе создайте несколько строковых переменных, которые хранят в себе команды SQL запросов для
// вставки новых абонентов. Добавьте эти переменные в коллекцию.

// Задание 3
// Создайте новый статический метод, который принимает объект типа Connection и коллекцию с записями новых абонентов.
// В методе получите объект типа Statement. С помощью цикла выполните команды с коллекции групповым обновлением,
// но в контексте единой транзакции. Если в процессе выполнения команд появится ошибка,
// необходимо сделать откат транзакции.

// Задание 4
// Вызовите метод, созданный в задании 3, в main() и передайте в него необходимые параметры.
// Протестируйте работу метода. Для проверки выполнения команд просматривайте таблицу Phonebook с помощью
// плагина Database.


package tasks1234;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ArrayList<String> al = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            String command1 = "INSERT INTO phonebook (LastName, Phone) VALUES ('Стеклов', 9777851454)";
            String command2 = "INSERT INTO phonebook (LastName, Phone) VALUES ('Филатов', 9071823578)";

            al.add(command1);
            al.add(command2);

            addSubscriber(connection, al);
        }
    }

    public static void addSubscriber (Connection connection, ArrayList<String> al) throws SQLException {
        Statement stat = null;
        try {
            stat = connection.createStatement();
            connection.setAutoCommit(false);
            for (String s : al) {
                stat.addBatch(s);
            }
            stat.executeBatch();
            connection.commit();

        } catch (SQLException exc) {
            connection.rollback();
            exc.printStackTrace();
        } finally {
            stat.close();
        }
    }
}
