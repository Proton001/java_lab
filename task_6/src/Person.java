import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Person {
    private String name;
    private int money;
    private List<Product> products;

    public Person(String name, int money) {
        setName(name);
        setMoney(money);
        this.products = new ArrayList<>();
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
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money < 0) {
            throw new IllegalArgumentException("Деньги не могут быть отрицательными");
        }
        this.money = money;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean canAfford(Product product) {
        return money >= product.getPrice();
    }

    public void buyProduct(Product product) {
        if (canAfford(product)) {
            products.add(product);
            money -= product.getPrice();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + " - ");
        if (products.isEmpty()) {
            result.append("Ничего не куплено");
        } else {
            for (int i = 0; i < products.size(); i++) {
                if (i > 0) result.append(", ");
                result.append(products.get(i).getName());
            }
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return money == person.money && Objects.equals(name, person.name) && Objects.equals(products, person.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, money, products);
    }
}
