package Homework7;

import java.util.Objects;

abstract class BaseProduct {
    private String name;
    private int basePrice;

    public BaseProduct(String name, int basePrice) {
        setName(name);
        setBasePrice(basePrice);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("Имя не может быть короче 3 символов");
        }
        if (name.matches("\\d+")) {
            throw new IllegalArgumentException("Имя не может содержать только цифры");
        }
        this.name = name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(int basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Цена не может быть отрицательной или нулевой");
        }
        this.basePrice = basePrice;
    }

    public abstract int getPrice();

    @Override
    public String toString() {
        return name + " = " + getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseProduct that = (BaseProduct) o;
        return basePrice == that.basePrice && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, basePrice);
    }
}
