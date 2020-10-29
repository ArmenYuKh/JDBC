// Задание 1
// Создайте новый класс, выполните подключения к базе данных, в которой хранится таблица Phonebook, созданная ранее.
// Также в главном методе инициализируйте объект этого класса.
// Создайте объект типа Statement и укажите значения для параметров type и concurrency.
// Получите результирующий набор.
//
// Задание 2
// Создайте метод для добавления новой записи в обновляющийся результирующий набор таблицы Phonebook.
// Метод должен принимать объект типа ResultSet, полученный ранее, и значения для новой записи.

// Задание 3
// Создайте метод для вывода всех записей таблицы Phonebook. Метод должен принимать как параметр объект
// типа ResultSet. Каждая строка вывода должна иметь вид: имя_поля1 = значение, имя_поля2 = значение, …,
// имя_поляN = значение. Для этого используйте интерфейс ResultSetMetaData.

// Задание 4
// Добавьте несколько записей в таблицу Phonebook с помощью вызова объектом класса созданных методов.
// Выведите все записи из таблицы в консоль в формате, указанном в задании 3.


package tasks1234;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Создаём объект класса
        Main main = new Main();

        String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, userName, password);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            // Получаем результирующий набор
            ResultSet rs = null;
            try {
                rs = statement.executeQuery("SELECT * FROM phonebook");
                // Вызываем созданные методы
                main.addSubscriber(rs, "Смолов", 9161001234L);
                main.addSubscriber(rs, "Круглов", 9096571211L);
                main.showSubscriber(rs);
            } catch (SQLException exc) {
                exc.printStackTrace();
            } finally {
                if(rs != null)
                    rs.close();
                else
                    System.err.println("Ошибка чтения данных с БД!");
            }
        }
    }

    // метод для добавления новой записи в обновляющийся результирующий набор таблицы Phonebook
    public void addSubscriber (ResultSet rs, String scName, long scPhone) throws SQLException {
        rs.moveToInsertRow();
        rs.updateString(2, scName);
        rs.updateLong(3, scPhone);
        rs.insertRow();
    }

    // Метод для вывода всех записей таблицы Phonebook
    public void showSubscriber (ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        // Выводим строки результирующего набора с помощъю метаданных
        while (rs.next()) {
            for (int i = 1; i <= rsmd.getColumnCount(); ++i) {
                String field = rsmd.getColumnName(i);
                String value = rs.getString(field);
                System.out.print(field + ": " + value + "; ");
            }
            System.out.println("");
        }
    }
}
