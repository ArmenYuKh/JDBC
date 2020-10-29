// Создайте и вызовите хранимую процедуру для получения количества всех записей таблицы
// Books, в которых bookId больше 2. Выведите эти записи в консоль.

package add_task;


import java.io.IOException;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        // Задаем параметры подключения
        String url = "jdbc:mysql://localhost:3306/books_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            try (CallableStatement callStat = connection.prepareCall("{CALL booksCount(?)}")) {
                callStat.registerOutParameter(1, Types.INTEGER);
                callStat.execute();
                System.out.println("Количество записей в таблице: " + callStat.getInt(1));

                try (PreparedStatement prepStat = connection.prepareStatement("SELECT * FROM books")) {
                    ResultSet rs = null;
                    try {
                        rs = prepStat.executeQuery("SELECT * FROM Books WHERE bookId > 2");
                        System.out.println("Выводим записи в консоль:");
                        while (rs.next()) {
                            int id = rs.getInt(1);
                            String name = rs.getString(2);
                            double price = rs.getDouble(3);
                            System.out.println("Id = " + id + "; name = " + name + "; price = " + price);
                        }
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                    } finally {
                        if (rs != null) {
                            rs.close();
                        } else {
                            System.err.println("Ошибка чтения данных с БД!");
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}