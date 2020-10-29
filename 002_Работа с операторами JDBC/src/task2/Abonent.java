// Создайте класс Abonent и определите конструктор, который будет принимать три параметра: id, name, number.
// Создайте геттер для получения поля id. Переопределите метод toString() для вывода объектов
// типа класса Abonent.

package task2;

public class Abonent {
    int id;
    String name;
    long number;

    public Abonent(int id, String name, long number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Abonent (id = " + id + ", name = " + name + ", number = " + number + ")";
    }
}
