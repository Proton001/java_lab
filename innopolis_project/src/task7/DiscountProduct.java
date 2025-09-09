package task7;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

class DiscountProduct extends BaseProduct {
    private int discount;
    private LocalDate discountEndDate;

    public DiscountProduct(String name, int basePrice, int discount, LocalDate discountEndDate) {
        super(name, basePrice);
        setDiscount(discount);
        setDiscountEndDate(discountEndDate);
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Скидка не может быть отрицательной");
        }
        if (discount >= getBasePrice()) {
            throw new IllegalArgumentException("Скидка не может быть больше или равна базовой цене");
        }
        this.discount = discount;
    }

    public LocalDate getDiscountEndDate() {
        return discountEndDate;
    }

    public void setDiscountEndDate(LocalDate discountEndDate) {
        if (discountEndDate == null) {
            throw new IllegalArgumentException("Дата окончания скидки не может быть null");
        }
        this.discountEndDate = discountEndDate;
    }

    public boolean isDiscountActive() {
        return LocalDate.now().isBefore(discountEndDate) || LocalDate.now().isEqual(discountEndDate);
    }

    @Override
    public int getPrice() {
        if (isDiscountActive()) {
            return getBasePrice() - discount;
        }
        return getBasePrice();
    }

    @Override
    public String toString() {
        String priceInfo = getName() + " = " + getPrice();
        if (isDiscountActive()) {
            priceInfo += " (скидка " + discount + " до " + discountEndDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")";
        } else {
            priceInfo += " (базовая цена)";
        }
        return priceInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DiscountProduct that = (DiscountProduct) o;
        return discount == that.discount && Objects.equals(discountEndDate, that.discountEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), discount, discountEndDate);
    }
}
