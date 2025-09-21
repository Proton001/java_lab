package Homework9;

import java.util.Objects;

// Класс Автомобиль
class Автомобиль {
    // Поля класса с private модификатором доступа
    private String номер;
    private String модель;
    private String цвет;
    private int пробег;
    private double стоимость;

    // Конструктор
    public Автомобиль(String номер, String модель, String цвет, int пробег, double стоимость) {
        this.номер = номер;
        this.модель = модель;
        this.цвет = цвет;
        this.пробег = пробег;
        this.стоимость = стоимость;
    }

    // Геттеры
    public String getНомер() {
        return номер;
    }

    public String getМодель() {
        return модель;
    }

    public String getЦвет() {
        return цвет;
    }

    public int getПробег() {
        return пробег;
    }

    public double getСтоимость() {
        return стоимость;
    }

    // Сеттеры
    public void setНомер(String номер) {
        this.номер = номер;
    }

    public void setМодель(String модель) {
        this.модель = модель;
    }

    public void setЦвет(String цвет) {
        this.цвет = цвет;
    }

    public void setПробег(int пробег) {
        this.пробег = пробег;
    }

    public void setСтоимость(double стоимость) {
        this.стоимость = стоимость;
    }

    // Переопределение метода toString
    @Override
    public String toString() {
        return "Автомобиль{" +
                "номер='" + номер + '\'' +
                ", модель='" + модель + '\'' +
                ", цвет='" + цвет + '\'' +
                ", пробег=" + пробег +
                ", стоимость=" + стоимость +
                '}';
    }

    // Переопределение equals и hashCode для корректной работы с коллекциями
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Автомобиль that = (Автомобиль) o;
        return Objects.equals(номер, that.номер);
    }

    @Override
    public int hashCode() {
        return Objects.hash(номер);
    }
}
