import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class RestaurantJDBC {

    private static final String DB_NAME = "restaurant_db";
    private static final String SERVER_URL = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection serverCon = DriverManager.getConnection(SERVER_URL, USER, PASS);
             Statement st = serverCon.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        }
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    private static void createTables(Connection con) throws Exception {
        try (Statement st = con.createStatement()) {
            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Restaurant (" +
                    "Id INT PRIMARY KEY, " +
                    "Name VARCHAR(100) NOT NULL, " +
                    "Address VARCHAR(255) NOT NULL)");

            st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS MenuItem (" +
                    "Id INT PRIMARY KEY, " +
                    "Name VARCHAR(100) NOT NULL, " +
                    "Price DOUBLE NOT NULL, " +
                    "ResId INT NOT NULL, " +
                    "FOREIGN KEY (ResId) REFERENCES Restaurant(Id))");
        }
    }

    private static void clearData(Connection con) throws Exception {
        try (Statement st = con.createStatement()) {
            st.executeUpdate("DELETE FROM MenuItem");
            st.executeUpdate("DELETE FROM Restaurant");
        }
    }

    private static void insertSampleData(Connection con) throws Exception {
        String restaurantSql = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
        String menuSql = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";

        Object[][] restaurants = {
                {1, "Cafe Java", "101 Main St"},
                {2, "Pizza Palace", "202 Oak Ave"},
                {3, "Burger Junction", "303 Pine Rd"},
                {4, "Pasta House", "404 Elm St"},
                {5, "Taco Corner", "505 Maple Dr"}
        };

        Object[][] menuItems = {
                {101, "Coffee", 50.0, 1},
                {102, "Tea", 40.0, 1},
                {103, "Pizza", 250.0, 2},
                {104, "Burger", 80.0, 3},
                {105, "Pasta", 180.0, 4},
                {106, "Taco", 60.0, 5}
        };

        try (PreparedStatement ps = con.prepareStatement(restaurantSql)) {
            for (Object[] row : restaurants) {
                ps.setInt(1, (Integer) row[0]);
                ps.setString(2, (String) row[1]);
                ps.setString(3, (String) row[2]);
                ps.executeUpdate();
            }
        }

        try (PreparedStatement ps = con.prepareStatement(menuSql)) {
            for (Object[] row : menuItems) {
                ps.setInt(1, (Integer) row[0]);
                ps.setString(2, (String) row[1]);
                ps.setDouble(3, (Double) row[2]);
                ps.setInt(4, (Integer) row[3]);
                ps.executeUpdate();
            }
        }
    }

    private static void printRestaurants(Connection con) throws Exception {
        System.out.println("\nRestaurant Table");
        System.out.printf("%-5s %-20s %-25s%n", "ID", "Name", "Address");
        System.out.println("----------------------------------------------------------");

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Restaurant ORDER BY Id")) {
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-25s%n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Address"));
            }
        }
    }

    private static void printMenuItems(Connection con, String title, String sql) throws Exception {
        System.out.println("\n" + title);
        System.out.printf("%-5s %-15s %-10s %-10s%n", "ID", "Name", "Price", "ResId");
        System.out.println("------------------------------------------------");

        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("%-5d %-15s %-10.2f %-10d%n",
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getInt("ResId"));
            }
        }
    }

    private static void updateCheapItems(Connection con) throws Exception {
        String sql = "UPDATE MenuItem SET Price = 200 WHERE Price < 100";
        try (Statement st = con.createStatement()) {
            int rows = st.executeUpdate(sql);
            System.out.println("\nUpdated rows: " + rows);
        }
    }

    private static void deletePizzaItems(Connection con) throws Exception {
        String sql = "DELETE FROM MenuItem WHERE Name LIKE 'P%'";
        try (Statement st = con.createStatement()) {
            int rows = st.executeUpdate(sql);
            System.out.println("\nDeleted rows: " + rows);
        }
    }

    public static void main(String[] args) {
        System.out.println("Starting RestaurantJDBC...");

        try (Connection con = getConnection()) {
            System.out.println("Connected to MySQL successfully.");

            createTables(con);
            clearData(con);
            insertSampleData(con);

            printRestaurants(con);
            printMenuItems(con, "Menu Items After Insert", "SELECT * FROM MenuItem ORDER BY Id");
            printMenuItems(con, "Menu Items Priced Below 100", "SELECT * FROM MenuItem WHERE Price < 100 ORDER BY Id");

            updateCheapItems(con);
            printMenuItems(con, "Menu Items After Update", "SELECT * FROM MenuItem ORDER BY Id");

            deletePizzaItems(con);
            printMenuItems(con, "Menu Items After Delete", "SELECT * FROM MenuItem ORDER BY Id");

            System.out.println("\nProgram completed successfully.");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
