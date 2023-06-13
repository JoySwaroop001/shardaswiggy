package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import java.util.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Map<String, Double>> RESTAURANTS;

    static {
        RESTAURANTS = new HashMap<>();

        // Indian Restaurant menu
        Map<String, Double> indianMenu = new HashMap<>();
        indianMenu.put("Butter Chicken", 12.99);
        indianMenu.put("Biryani", 10.99);
        indianMenu.put("Paneer Tikka", 8.99);
        RESTAURANTS.put("Indian Restaurant", indianMenu);

        // Chinese Restaurant menu
        Map<String, Double> chineseMenu = new HashMap<>();
        chineseMenu.put("Fried Rice", 9.99);
        chineseMenu.put("Sweet and Sour Chicken", 11.99);
        chineseMenu.put("Kung Pao Shrimp", 13.99);
        RESTAURANTS.put("Chinese Restaurant", chineseMenu);
    }

    private static final Map<String, Double> CART = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Food Ordering System");
            System.out.println("1. Select restaurant");
            System.out.println("2. Select food");
            System.out.println("3. View cart");
            System.out.println("4. Bill");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    selectRestaurant(scanner);
                    break;
                case 2:
                    selectFood(scanner);
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    generateBill();
                    break;
                case 5:
                    System.out.println("Thank you for using the Food Ordering System. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        }
    }

    private static void selectRestaurant(Scanner scanner) {
        System.out.println("Select a restaurant:");
        int index = 1;
        for (String restaurant : RESTAURANTS.keySet()) {
            System.out.println(index + ". " + restaurant);
            index++;
        }

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > RESTAURANTS.size()) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String restaurantName = "";
        int counter = 1;
        for (String restaurant : RESTAURANTS.keySet()) {
            if (counter == choice) {
                restaurantName = restaurant;
                break;
            }
            counter++;
        }

        System.out.println("You selected: " + restaurantName);
    }

    private static void selectFood(Scanner scanner) {
        if (RESTAURANTS.isEmpty()) {
            System.out.println("No restaurant selected. Please select a restaurant first.");
            return;
        }

        System.out.println("Select a food item:");
        int index = 1;
        for (String restaurant : RESTAURANTS.keySet()) {
            System.out.println(restaurant);
            Map<String, Double> menu = RESTAURANTS.get(restaurant);
            for (String foodItem : menu.keySet()) {
                System.out.println(index + ". " + foodItem + " - " + formatPrice(menu.get(foodItem)));
                index++;
            }
        }

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > index - 1) {
            System.out.println("Invalid choice. Please try again.");
            return;
        }

        String foodItemName = "";
        int counter = 1;
        for (String restaurant : RESTAURANTS.keySet()) {
            Map<String, Double> menu = RESTAURANTS.get(restaurant);
            for (String foodItem : menu.keySet()) {
                if (counter == choice) {
                    foodItemName = foodItem;
                    break;
                }
                counter++;
            }
            if (!foodItemName.isEmpty()) {
                break;
            }
        }

        System.out.println("You selected: " + foodItemName);
        addToCart(foodItemName);
    }

    private static void addToCart(String foodItem) {
        for (String restaurant : RESTAURANTS.keySet()) {
            Map<String, Double> menu = RESTAURANTS.get(restaurant);
            if (menu.containsKey(foodItem)) {
                double price = menu.get(foodItem);
                if (CART.containsKey(foodItem)) {
                    CART.put(foodItem, CART.get(foodItem) + price);
                } else {
                    CART.put(foodItem, price);
                }
                break;
            }
        }
    }

    private static void viewCart() {
        if (CART.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Items in your cart:");
            for (String foodItem : CART.keySet()) {
                System.out.println("- " + foodItem + " - " + formatPrice(CART.get(foodItem)));
            }
        }
    }

    private static void generateBill() {
        if (CART.isEmpty()) {
            System.out.println("Your cart is empty. No bill to generate.");
            return;
        }

        double totalBill = 0.0;

        System.out.println("Bill:");
        for (String foodItem : CART.keySet()) {
            double price = CART.get(foodItem);
            System.out.println(foodItem + " - " + formatPrice(price));
            totalBill += price;
        }

        System.out.println("Total bill amount: " + formatPrice(totalBill));
        CART.clear();
    }

    private static String formatPrice(double price) {
        NumberFormat currencyFormatter = DecimalFormat.getCurrencyInstance(new Locale("en", "IN"));
        currencyFormatter.setCurrency(Currency.getInstance("INR"));
        return currencyFormatter.format(price);
    }
}
