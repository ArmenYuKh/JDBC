// Напишите приложение, которое считывает имя пользователя и пароль для подключения
// к БД по запросу с консоли. Необходимо реализовать проверку введённых данных.
// Если данные введены верно – осуществить подключение к базе данных с сообщением «Connected to DB»,
// иначе – выбросить в консоль сообщение о неудаче с просьбой ввести имя пользователя и пароль ещё раз.

package task2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class TestConnectionConsole {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/books_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Scanner in1 = new Scanner(System.in);
        Scanner in2 = new Scanner(System.in);

        System.out.println("Введите имя пользователя:");
        String userName = in1.next();

        System.out.println("Введите пароль:");
        String password = in2.next();


//        Регистриуем драйвер с помощью статического инициализатора
        Class.forName("com.mysql.cj.jdbc.Driver");


//        Создаем подключение, передавая в getConnection() параметры
        if (!userName.equals("root") || !password.equals("root"))
            System.out.println("Вы ввели неверное имя пользователя и/или пароль. Повторите попытку!");
        else {
            try (Connection conn = DriverManager.getConnection(url, userName, password)) {
                System.out.println("Connected to DB");
            }
        }
    }
}
