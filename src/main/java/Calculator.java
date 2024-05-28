import java.util.ArrayList;
import java.util.Scanner;

class BillCalculator {
    private int numberOfPeople;
    private final ArrayList<Item> items;
    private double totalAmount;

    public BillCalculator() {
        this.items = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    // Запросить у пользователя количество людей и проверять правильность ввода
    public void askNumberOfPeople() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("На скольких человек необходимо разделить счёт? ");
            if (scanner.hasNextInt()) {
                int numPeople = scanner.nextInt();
                if (numPeople > 1) {
                    this.numberOfPeople = numPeople;
                    System.out.println("Количество человек: " + numPeople);
                    break;
                } else if (numPeople == 1) {
                    System.out.println("Нет смысла ничего считать и делить на одного человека.");
                } else {
                    System.out.println("Некорректное значение. Введите число больше 1.");
                }
            } else {
                System.out.println("Некорректное значение. Введите число больше 1.");
                scanner.next();
            }
        }
    }

    // Добавление товаров с проверкой на корректность ввода
    public void addItems() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите название товара: ");
            String itemName = scanner.nextLine();

            double itemPrice;
            while (true) {
                System.out.print("Введите стоимость товара (в формате рубли.копейки): ");
                if (scanner.hasNextDouble()) {
                    itemPrice = scanner.nextDouble();
                    scanner.nextLine();
                    if (itemPrice >= 0) {
                        break;
                    } else {
                        System.out.println("Стоимость товара не может быть отрицательной. Пожалуйста, введите положительное значение.");
                    }
                } else {
                    System.out.println("Некорректный формат стоимости. Пожалуйста, введите стоимость в формате рубли.копейки.");
                    scanner.next();
                }
            }

            Item item = new Item(itemName, itemPrice);
            items.add(item);
            totalAmount += itemPrice;
            System.out.println("Товар успешно добавлен!");

            System.out.print("Хотите добавить ещё один товар? (Введите 'Завершить' для завершения): ");
            String userResponse = scanner.nextLine().toLowerCase();

            if (userResponse.equalsIgnoreCase("завершить")) {
                break;
            }
        }

        System.out.println("Добавление товаров завершено.");
    }

    // Печать итогового счета
    public void printBill() {
        System.out.println("Добавленные товары:");
        for (Item item : items) {
            System.out.println("- " + item);
        }

        String totalFormatted = CurrencyFormatter.formatCurrency(totalAmount);
        String perPersonFormatted = CurrencyFormatter.formatCurrency(totalAmount / numberOfPeople);
        String rublesWordTotal = CurrencyFormatter.getRublesWord(totalAmount);
        String rublesWordPerPerson = CurrencyFormatter.getRublesWord(totalAmount / numberOfPeople);

        System.out.println("Общая сумма: " + totalFormatted + " " + rublesWordTotal);
        System.out.println("Сумма на каждого человека: " + perPersonFormatted + " " + rublesWordPerPerson);
    }
}

class Item {
    private final String name;
    private final double price;

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - " + String.format("%.2f", price) + " руб.";
    }
}
class CurrencyFormatter {

    public static String formatCurrency(double amount) {
        return String.format("%.2f", amount);
    }

    public static String getRublesWord(double amount) {
        int intPart = (int) Math.floor(amount);
        if (intPart % 100 >= 11 && intPart % 100 <= 14) {
            return "рублей";
        }
        return switch (intPart % 10) {
            case 1 -> "рубль";
            case 2, 3, 4 -> "рубля";
            default -> "рублей";
        };
    }
}

