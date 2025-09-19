package Homework7;

class Product extends BaseProduct {
    public Product(String name, int price) {
        super(name, price);
    }

    @Override
    public int getPrice() {
        return getBasePrice();
    }
}
