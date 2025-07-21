import java.util.Random;

// Класс Телевизор
class Television {
    // Приватные поля
    private String brand;
    private double screenSize; // в дюймах
    private int currentChannel;
    private int currentVolume;
    private boolean isOn;

    // Конструктор с параметрами
    public Television(String brand, double screenSize) {
        this.brand = brand;
        this.screenSize = screenSize;
        this.currentChannel = 1;
        this.currentVolume = 50;
        this.isOn = false;
    }

    // Геттеры и сеттеры
    public String getBrand() {
        return brand;
    }

    public double getScreenSize() {
        return screenSize;
    }

    public int getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(int channel) {
        if (isOn && channel > 0) {
            this.currentChannel = channel;
            System.out.println("Канал изменён на " + channel);
        }
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void setCurrentVolume(int volume) {
        if (isOn && volume >= 0 && volume <= 100) {
            this.currentVolume = volume;
            System.out.println("Громкость изменена на " + volume);
        }
    }

    public boolean isOn() {
        return isOn;
    }

    // Методы управления телевизором
    public void turnOn() {
        this.isOn = true;
        System.out.println("Телевизор " + brand + " включён");
    }

    public void turnOff() {
        this.isOn = false;
        System.out.println("Телевизор " + brand + " выключен");
    }

    public void channelUp() {
        if (isOn) {
            currentChannel++;
            System.out.println("Канал увеличен до " + currentChannel);
        }
    }

    public void channelDown() {
        if (isOn && currentChannel > 1) {
            currentChannel--;
            System.out.println("Канал уменьшен до " + currentChannel);
        }
    }

    public void volumeUp() {
        if (isOn && currentVolume < 100) {
            currentVolume++;
            System.out.println("Громкость увеличенa до " + currentVolume);
        }
    }

    public void volumeDown() {
        if (isOn && currentVolume > 0) {
            currentVolume--;
            System.out.println("Громкость уменьшенa до " + currentVolume);
        }
    }

    @Override
    public String toString() {
        return "Телевизор " + brand +
                ", диагональ: " + screenSize + "''" +
                ", состояние: " + (isOn ? "вкл" : "выкл") +
                ", канал: " + currentChannel +
                ", громкость: " + currentVolume;
    }
}

// Класс App для тестирования
class App {
    public static void main(String[] args) {
        // Создаем несколько телевизоров
        Television tv1 = new Television("Samsung", 55.5);
        Television tv2 = new Television("LG", 43.0);
        Television tv3 = new Television("Sony", 65.0);

        // Включаем первый телевизор и тестируем
        tv1.turnOn();
        tv1.setCurrentChannel(5);
        tv1.volumeUp();
        tv1.volumeUp();
        tv1.channelUp();
        System.out.println(tv1);

        // Тестируем второй телевизор
        tv2.turnOn();
        tv2.setCurrentVolume(75);
        tv2.setCurrentChannel(10);
        System.out.println(tv2);
        tv2.turnOff();

        // Создаем телевизор со случайными параметрами
        Random random = new Random();
        String[] brands = {"Samsung", "LG", "Sony", "Panasonic", "Philips", "Toshiba"};
        String randomBrand = brands[random.nextInt(brands.length)];
        double randomSize = 30 + random.nextDouble() * 50; // от 30 до 80 дюймов

        Television randomTv = new Television(randomBrand, randomSize);
        randomTv.turnOn();
        randomTv.setCurrentChannel(1 + random.nextInt(100)); // случайный канал от 1 до 100
        randomTv.setCurrentVolume(random.nextInt(101)); // случайная громкость от 0 до 100
        System.out.println("\nТелевизор со случайными параметрами:");
        System.out.println(randomTv);
    }
}