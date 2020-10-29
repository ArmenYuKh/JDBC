// Создайте отдельный метод для добавления новой записи в обновляющийся результирующий набор.
// Метод должен принимать параметры, которые соответствуют полям таблицы Books.
// Проверьте работу метода, добавив в таблицу 1-2 новых записи.


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
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            ResultSet rs = null;
            try {
                // Получаем результирующий набор
                rs = statement.executeQuery("SELECT * FROM books");
                String bookname1 = "Mertvye dushi";
                String bookname2 = "Geroy nashego vremeni";
                double bookprice1 = 30;
                double bookprice2 = 40.6;
                addbook(rs, bookname1, bookprice1);
                addbook(rs, bookname2, bookprice2);
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    double price = rs.getDouble(3);
                    System.out.println("Id = " + id + ", name = " + name + ", price = " + price);
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
    static void addbook (ResultSet rs, String bookname, double bookprice) throws SQLException {
        rs.moveToInsertRow();
        rs.updateString(2, bookname);
        rs.updateDouble(3, bookprice);
        rs.insertRow();
    }
}