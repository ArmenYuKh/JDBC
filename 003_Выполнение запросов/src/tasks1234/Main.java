// Задание 1
// Создайте новый класс, выполните подключения к базе данных,
// в которой хранится таблица Phonebook, созданная в прошлом домашнем задании.

// Задание 2
// В новом классе объявите переменные, которые будут инициализироваться текстом,
// полученным с консоли. Эти переменные будут обозначать ID абонента с таблицы и его новое имя.

// Задание 3
// Создайте объект типа PreparedStatement и передайте в метод prepareStatement() строку
// запроса на обновление имени одного из абонентов таблицы Phonebook, ID которого Вы ввели
// в консоль. Используя объект подготовленного запроса свяжите переменные с параметрами
// обновления записи и выполните изменения в таблице.

// Задание 4
// Создайте хранимую процедуру для выборки всех записей с таблицы Phonebook, для проверки
// наличия изменений. Вызовите процедуру и выведите полученный результирующий набор в консоль.

package tasks1234;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner in1 = new Scanner(System.in);
        Scanner in2 = new Scanner(System.in);
        System.out.println("Введите ID абонента:");
        int idPB = in1.nextInt();
        System.out.println("Введите фамилию, на которую хотите заменить фамилию абонента с ID = " + idPB + ":");
        String LName = in2.next();


        String url = "jdbc:mysql://localhost:3306/first_lesson?useLegacyDatetimeCode=false&&serverTimezone=UTC";
        String userName = "root";
        String password = "root";
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement prepStat = null;
            try {
                prepStat = connection.prepareStatement("UPDATE phonebook SET LastName = ? WHERE IdPhoneBook = ?;");
                prepStat.setString(1, LName);
                prepStat.setInt(2, idPB);
                prepStat.execute();

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (prepStat != null) {
                    prepStat.close();
                }
            }

            CallableStatement callStat = null;
            try {
                callStat = connection.prepareCall("{CALL pbrecords}");
                callStat.execute();

                ResultSet rs = null;
                System.out.println("Все записи с таблицы Phonebook:");
                try {
                    rs = callStat.getResultSet();
                    while (rs.next()) {
                        int id = rs.getInt(1);
                        String lastName = rs.getString(2);
                        long phone = rs.getLong(3);
                        System.out.println("Id = " + id + ", lastName = " + lastName + ", phone = " + phone);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (rs != null) {
                        rs.close();
                    } else {
                        System.err.println("Ошибка чтения данных с БД!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (callStat != null) {
                    callStat.close();
                }
            }
        }
    }
}


