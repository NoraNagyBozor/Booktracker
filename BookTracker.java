import java.sql.*;
import java.util.Scanner;

public class BookTracker {
    private static final String DB_URL = "jdbc:sqlite:booktracker.db";

    public static void main(String[] args) {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC Driver not found.");
            e.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\nBookTracker Menu:");
            System.out.println("1. Add User");
            System.out.println("2. View Reading Habits of a User");
            System.out.println("3. Update Book Title");
            System.out.println("4. Delete Reading Habit Record");
            System.out.println("5. Get Mean Age of Users");
            System.out.println("6. Count Users Who Read a Specific Book");
            System.out.println("7. Total Pages Read by All Users");
            System.out.println("8. Count Users Who Read More Than One Book");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: addUser(scanner); break;
                case 2: viewReadingHabits(scanner); break;
                case 3: updateBookTitle(scanner); break;
                case 4: deleteReadingHabit(scanner); break;
                case 5: getMeanAge(); break;
                case 6: countUsersWhoReadBook(scanner); break;
                case 7: getTotalPagesRead(); break;
                case 8: countUsersWithMultipleBooks(); break;
                case 9: System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addUser(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userID = scanner.nextInt();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO User (userID, age, gender, name) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, userID);
            stmt.setInt(2, age);
            stmt.setString(3, gender);
            stmt.setString(4, name);
            stmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewReadingHabits(Scanner scanner) {
        System.out.print("Enter User ID: ");
        int userID = scanner.nextInt();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ReadingHabit WHERE userID = ?")) {
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("Habit ID: " + rs.getInt("habitID") +
                        ", Book: " + rs.getString("book") +
                        ", Pages Read: " + rs.getInt("pagesRead") +
                        ", Submission Date: " + rs.getString("submissionMoment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateBookTitle(Scanner scanner) {
        System.out.print("Enter Habit ID: ");
        int habitID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Book Title: ");
        String newTitle = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("UPDATE ReadingHabit SET book = ? WHERE habitID = ?")) {
            stmt.setString(1, newTitle);
            stmt.setInt(2, habitID);
            stmt.executeUpdate();
            System.out.println("Book title updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteReadingHabit(Scanner scanner) {
        System.out.print("Enter Habit ID to delete: ");
        int habitID = scanner.nextInt();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM ReadingHabit WHERE habitID = ?")) {
            stmt.setInt(1, habitID);
            stmt.executeUpdate();
            System.out.println("Record deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getMeanAge() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT AVG(age) FROM User")) {
            if (rs.next()) {
                System.out.println("Mean Age of Users: " + rs.getDouble(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void countUsersWhoReadBook(Scanner scanner) {
        System.out.print("Enter Book Title: ");
        String bookTitle = scanner.nextLine();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(DISTINCT userID) FROM ReadingHabit WHERE book = ?")) {
            stmt.setString(1, bookTitle);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Total Users Who Read This Book: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getTotalPagesRead() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT SUM(pagesRead) FROM ReadingHabit")) {
            if (rs.next()) {
                System.out.println("Total Pages Read by All Users: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void countUsersWithMultipleBooks() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM (SELECT userID FROM ReadingHabit GROUP BY userID HAVING COUNT(DISTINCT book) > 1)")) {
            if (rs.next()) {
                System.out.println("Total Users Who Read More Than One Book: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
