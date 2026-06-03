package labs5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Payment {
    private String paymentId;
    private String payerName;
    private Cart cart;
    private boolean isProcessed;

    public Payment(String paymentId, String payerName) {
        this.paymentId = paymentId;
        this.payerName = payerName;
        this.cart = this.new Cart();
        this.isProcessed = false;
        System.out.println("[СИСТЕМА] Створено новий платіж #" + paymentId + " для клієнта: " + payerName);
    }

    public Cart getCart() {
        return cart;
    }

    public void processPayment() {
        if (cart.isEmpty()) {
            System.out.println("[ПОМИЛКА] Неможливо провести платіж: кошик порожній!");
            return;
        }
        isProcessed = true;
        System.out.println("[СИСТЕМА] Платіж #" + paymentId + " успішно проведено на суму: " + cart.getTotalPrice() + " грн.");
    }

    public void displayPaymentInfo() {
        System.out.println("\n=== Деталі платежу ===");
        System.out.println("ID платежу: " + paymentId);
        System.out.println("Платник: " + payerName);
        System.out.println("Статус: " + (isProcessed ? "Оплачено" : "Очікує оплати"));
        cart.displayCart();
        System.out.println("======================\n");
    }

    public class Cart {
        private List<String> itemNames;
        private List<Double> itemPrices;

        public Cart() {
            this.itemNames = new ArrayList<>();
            this.itemPrices = new ArrayList<>();
        }

        public void addItem(String name, double price) {
            itemNames.add(name);
            itemPrices.add(price);
            System.out.println("[УСПІХ] Товар '" + name + "' додано до кошика. Ціна: " + price + " грн.");
        }

        public boolean isEmpty() {
            return itemNames.isEmpty();
        }

        public double getTotalPrice() {
            double total = 0;
            for (double price : itemPrices) {
                total += price;
            }
            return total;
        }

        public void displayCart() {
            System.out.println("--- Кошик товарів ---");
            if (isEmpty()) {
                System.out.println("  Кошик порожній.");
            } else {
                for (int i = 0; i < itemNames.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + itemNames.get(i) + " - " + itemPrices.get(i) + " грн.");
                }
                System.out.println("  Загальна сума: " + getTotalPrice() + " грн.");
            }
            System.out.println("---------------------");
        }

        public void searchItem(String keyword) {
            System.out.println("\n[*] Пошук товару за ключовим словом: '" + keyword + "'...");
            boolean found = false;
            for (int i = 0; i < itemNames.size(); i++) {
                if (itemNames.get(i).toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println("[ЗНАЙДЕНО] Товар: " + itemNames.get(i) + " | Ціна: " + itemPrices.get(i) + " грн.");
                    found = true;
                }
            }
            if (!found) {
                System.out.println("[-] Товарів із назвою '" + keyword + "' не знайдено.");
            }
        }
    }
}

public class Lab5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Лабораторна робота №5 (Варіант 2) ===");
        System.out.print("Введіть ваше ім'я (Платник): ");
        String name = scanner.nextLine();

        System.out.print("Введіть ID платежу (наприклад, INV-001): ");
        String id = scanner.nextLine();

        Payment payment = new Payment(id, name);
        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Додати товар до кошика");
            System.out.println("2. Переглянути платіж та кошик");
            System.out.println("3. Знайти товар у кошику");
            System.out.println("4. Провести оплату");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введіть назву товару: ");
                    String itemName = scanner.nextLine();
                    System.out.print("Введіть ціну товару (грн): ");
                    try {
                        double price = Double.parseDouble(scanner.nextLine());
                        payment.getCart().addItem(itemName, price);
                    } catch (NumberFormatException e) {
                        System.out.println("[ПОМИЛКА] Невірний формат ціни! Будь ласка, введіть число (використовуйте крапку для дробових, наприклад 15.5).");
                    }
                    break;
                case "2":
                    payment.displayPaymentInfo();
                    break;
                case "3":
                    System.out.print("Введіть назву товару для пошуку: ");
                    String searchKeyword = scanner.nextLine();
                    payment.getCart().searchItem(searchKeyword);
                    break;
                case "4":
                    payment.processPayment();
                    break;
                case "0":
                    running = false;
                    System.out.println("[СИСТЕМА] Роботу завершено.");
                    break;
                default:
                    System.out.println("[ПОМИЛКА] Невірний вибір. Спробуйте ще раз.");
            }
        }
        scanner.close();
    }
}