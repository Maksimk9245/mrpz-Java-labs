import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Notebook {
    private String ownerName;
    private List<DateRecord> records;

    public Notebook(String ownerName) {
        this.ownerName = ownerName;
        this.records = new ArrayList<>();
        System.out.println("[СИСТЕМА] Створено новий блокнот для користувача: " + ownerName);
    }

    public class DateRecord {
        private String date;
        private List<String> events;

        public DateRecord(String date) {
            this.date = date;
            this.events = new ArrayList<>();
        }

        public void addEvent(String eventDescription) {
            events.add(eventDescription);
            System.out.println("[УСПІХ] Подію додано до дати " + date);
        }

        public String getDate() {
            return date;
        }

        public void displayRecord() {
            System.out.println("  📅 Дата: " + date);
            if (events.isEmpty()) {
                System.out.println("    Подій немає.");
            } else {
                System.out.println("    Події:");
                for (int i = 0; i < events.size(); i++) {
                    System.out.println("      " + (i + 1) + ". " + events.get(i));
                }
            }
        }

        public List<String> getEvents() {
            return events;
        }
    }

    public DateRecord addDateRecord(String date) {
        for (DateRecord record : records) {
            if (record.getDate().equals(date)) {
                return record;
            }
        }

        DateRecord newRecord = this.new DateRecord(date);
        records.add(newRecord);
        System.out.println("[СИСТЕМА] Створено новий запис для дати: " + date);
        return newRecord;
    }

    public void displayNotebook() {
        System.out.println("\n=== Блокнот користувача: " + ownerName + " ===");
        if (records.isEmpty()) {
            System.out.println("Блокнот порожній.");
        } else {
            for (DateRecord record : records) {
                record.displayRecord();
            }
        }
        System.out.println("====================================\n");
    }

    public void searchByDate(String targetDate) {
        System.out.println("\n[*] Пошук подій за датою: " + targetDate + "...");
        boolean found = false;
        for (DateRecord record : records) {
            if (record.getDate().equals(targetDate)) {
                System.out.println("[ЗНАЙДЕНО] Результати пошуку:");
                record.displayRecord();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("[-] Записів на дату " + targetDate + " не знайдено.");
        }
    }

    public void saveToFile(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("=== Блокнот користувача: " + ownerName + " ===");
            if (records.isEmpty()) {
                writer.println("Блокнот порожній.");
            } else {
                for (DateRecord record : records) {
                    writer.println("  Дата: " + record.getDate());
                    if (record.getEvents().isEmpty()) {
                        writer.println("    Подій немає.");
                    } else {
                        writer.println("    Події:");
                        for (int i = 0; i < record.getEvents().size(); i++) {
                            writer.println("      " + (i + 1) + ". " + record.getEvents().get(i));
                        }
                    }
                }
            }
            System.out.println("[УСПІХ] Дані збережено у файл: " + filePath);
        } catch (IOException e) {
            System.out.println("[ПОМИЛКА] Не вдалося зберегти у файл: " + e.getMessage());
        }
    }
}

public class Lab5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Лабораторна робота №5 ===");
        System.out.print("Введіть ім'я власника блокнота: ");
        String name = scanner.nextLine();

        Notebook myNotebook = new Notebook(name);
        boolean running = true;

        while (running) {
            System.out.println("\nОберіть дію:");
            System.out.println("1. Додати подію на певну дату");
            System.out.println("2. Вивести весь блокнот");
            System.out.println("3. Знайти події за датою");
            System.out.println("4. Зберегти у файл");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Введіть дату (наприклад, 12.10.2023): ");
                    String date = scanner.nextLine();
                    Notebook.DateRecord record = myNotebook.addDateRecord(date);

                    System.out.print("Введіть опис події: ");
                    String event = scanner.nextLine();
                    record.addEvent(event);
                    break;
                case "2":
                    myNotebook.displayNotebook();
                    break;
                case "3":
                    System.out.print("Введіть дату для пошуку: ");
                    String searchDate = scanner.nextLine();
                    myNotebook.searchByDate(searchDate);
                    break;
                case "4":
                    System.out.print("Введіть шлях до вихідного файлу: ");
                    String filePath = scanner.nextLine();
                    myNotebook.saveToFile(filePath);
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