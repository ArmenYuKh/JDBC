// Создайте новую базу данных с названием BOOKS_DB c помощью плагина Database средствами
// языка SQL. Проверьте соединение с СУБД из плагина Database.
// Внесите соответствующие изменения в переменные параметров подключения в Java-коде.
// Запустите этот код на выполнение.

package add_task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Задаем параметры подключения: URL, имя пользователя и пароль
        String url = "jdbc:mysql://localhost:3306/books_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "root";

//        Регистриуем драйвер с помощью статического инициализатора
        Class.forName("com.mysql.cj.jdbc.Driver");

//        Создаем подключение, передавая в getConnection() параметры
        try(Connection conn = DriverManager.getConnection(url, userName, password)) {
            System.out.println("Connection successful");
        }
    }
}