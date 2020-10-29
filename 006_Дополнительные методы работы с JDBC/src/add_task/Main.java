// Получите автоматически генерируемые ключи с таблицы Books, выполнив необходимые изменения в методах,
// которые выполняют вставки записей.

package add_task;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Задаем параметры подключения
        String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Создаем подключение и устанавливаем значения для параметров type и concurrency
        try (Connection connection = DriverManager.getConnection(url, userName, password);
             Statement statement = connection.createStatement()) {
            ResultSet rs = null;
            try {
                // Получаем результирующий набор
                rs = statement.executeQuery("SELECT * FROM books");
                statement.executeUpdate("INSERT INTO books (name, price) VALUES ('Master i Margarita', 50), ('Voyna i mir', 60.4)", Statement.RETURN_GENERATED_KEYS);

                int autoInc = -1;

                rs = statement.getGeneratedKeys();
                while (rs.next()) {
                    autoInc = rs.getInt(1);
                    System.out.println("Key returned from getGeneratedKeys(): " + autoInc);
                }
            } catch (SQLException exc) {
                exc.printStackTrace();
            } finally {
                if (rs != null)
                    rs.close();
                else
                    System.err.println("Ошибка чтения данных с БД!");
            }
        }
    }
}
