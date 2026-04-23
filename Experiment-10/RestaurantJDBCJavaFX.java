import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantJDBCJavaFX extends Application {

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

    public static class Restaurant {
        private int id;
        private String name;
        private String address;

        public Restaurant(int id, String name, String address) {
            this.id = id;
            this.name = name;
            this.address = address;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }
    }

    public static class MenuItem {
        private int id;
        private String name;
        private double price;
        private int resId;

        public MenuItem(int id, String name, double price, int resId) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.resId = resId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public int getResId() {
            return resId;
        }
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Restaurant JDBC JavaFX CRUD");
        showMainMenu(stage);
        stage.show();
    }

    private void showMainMenu(Stage stage) {
        Label title = new Label("Restaurant and MenuItem CRUD");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Button insertBtn = new Button("Insert");
        Button selectBtn = new Button("Select");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");

        insertBtn.setOnAction(e -> showInsertMenu(stage));
        selectBtn.setOnAction(e -> showSelectMenu(stage));
        updateBtn.setOnAction(e -> showUpdateMenu(stage));
        deleteBtn.setOnAction(e -> showDeleteMenu(stage));

        VBox root = new VBox(16, title, insertBtn, selectBtn, updateBtn, deleteBtn);
        root.setPadding(new Insets(24));
        root.setAlignment(Pos.CENTER_LEFT);
        stage.setScene(new Scene(root, 420, 280));
    }

    private void showInsertMenu(Stage stage) {
        Label title = new Label("Insert Data Into:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showInsertRestaurant(stage));
        menuItemBtn.setOnAction(e -> showInsertMenuItem(stage));
        backBtn.setOnAction(e -> showMainMenu(stage));

        HBox buttons = new HBox(10, restaurantBtn, menuItemBtn, backBtn);
        VBox root = new VBox(16, title, buttons);
        root.setPadding(new Insets(24));
        stage.setScene(new Scene(root, 520, 180));
    }

    private void showInsertRestaurant(Stage stage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(24));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField addressField = new TextField();

        Button saveBtn = new Button("Save");
        Button backBtn = new Button("Back");

        form.add(new Label("Id:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Address:"), 0, 2);
        form.add(addressField, 1, 2);
        form.add(saveBtn, 0, 3);
        form.add(backBtn, 1, 3);

        saveBtn.setOnAction(e -> {
            String sql = "INSERT INTO Restaurant (Id, Name, Address) VALUES (?, ?, ?)";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.setString(2, nameField.getText());
                ps.setString(3, addressField.getText());
                ps.executeUpdate();
                showAlert("Success", "Restaurant inserted successfully.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showInsertMenu(stage));
        stage.setScene(new Scene(form, 520, 240));
    }

    private void showInsertMenuItem(Stage stage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(24));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField resIdField = new TextField();

        Button saveBtn = new Button("Save");
        Button backBtn = new Button("Back");

        form.add(new Label("Id:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("Price:"), 0, 2);
        form.add(priceField, 1, 2);
        form.add(new Label("ResId:"), 0, 3);
        form.add(resIdField, 1, 3);
        form.add(saveBtn, 0, 4);
        form.add(backBtn, 1, 4);

        saveBtn.setOnAction(e -> {
            String sql = "INSERT INTO MenuItem (Id, Name, Price, ResId) VALUES (?, ?, ?, ?)";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.setString(2, nameField.getText());
                ps.setDouble(3, Double.parseDouble(priceField.getText()));
                ps.setInt(4, Integer.parseInt(resIdField.getText()));
                ps.executeUpdate();
                showAlert("Success", "MenuItem inserted successfully.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showInsertMenu(stage));
        stage.setScene(new Scene(form, 520, 280));
    }

    private void showSelectMenu(Stage stage) {
        Label title = new Label("Select Data From:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showRestaurantTable(stage, "Restaurant Records", "SELECT * FROM Restaurant ORDER BY Id"));
        menuItemBtn.setOnAction(e -> showMenuItemTable(stage, "MenuItem Records", "SELECT * FROM MenuItem ORDER BY Id"));
        backBtn.setOnAction(e -> showMainMenu(stage));

        HBox buttons = new HBox(10, restaurantBtn, menuItemBtn, backBtn);
        VBox root = new VBox(16, title, buttons);
        root.setPadding(new Insets(24));
        stage.setScene(new Scene(root, 520, 180));
    }

    private void showUpdateMenu(Stage stage) {
        Label title = new Label("Update Data In:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showUpdateRestaurant(stage));
        menuItemBtn.setOnAction(e -> showUpdateMenuItem(stage));
        backBtn.setOnAction(e -> showMainMenu(stage));

        HBox buttons = new HBox(10, restaurantBtn, menuItemBtn, backBtn);
        VBox root = new VBox(16, title, buttons);
        root.setPadding(new Insets(24));
        stage.setScene(new Scene(root, 520, 180));
    }

    private void showDeleteMenu(Stage stage) {
        Label title = new Label("Delete Data From:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showDeleteRestaurant(stage));
        menuItemBtn.setOnAction(e -> showDeleteMenuItem(stage));
        backBtn.setOnAction(e -> showMainMenu(stage));

        HBox buttons = new HBox(10, restaurantBtn, menuItemBtn, backBtn);
        VBox root = new VBox(16, title, buttons);
        root.setPadding(new Insets(24));
        stage.setScene(new Scene(root, 520, 180));
    }

    private void showUpdateRestaurant(Stage stage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(24));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField addressField = new TextField();
        Button updateBtn = new Button("Update");
        Button backBtn = new Button("Back");

        form.add(new Label("Id to update:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("New Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("New Address:"), 0, 2);
        form.add(addressField, 1, 2);
        form.add(updateBtn, 0, 3);
        form.add(backBtn, 1, 3);

        updateBtn.setOnAction(e -> {
            String sql = "UPDATE Restaurant SET Name = ?, Address = ? WHERE Id = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nameField.getText());
                ps.setString(2, addressField.getText());
                ps.setInt(3, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " restaurant row(s) updated.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showUpdateMenu(stage));
        stage.setScene(new Scene(form, 520, 240));
    }

    private void showUpdateMenuItem(Stage stage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(24));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField resIdField = new TextField();
        Button updateBtn = new Button("Update");
        Button backBtn = new Button("Back");

        form.add(new Label("Id to update:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(new Label("New Name:"), 0, 1);
        form.add(nameField, 1, 1);
        form.add(new Label("New Price:"), 0, 2);
        form.add(priceField, 1, 2);
        form.add(new Label("New ResId:"), 0, 3);
        form.add(resIdField, 1, 3);
        form.add(updateBtn, 0, 4);
        form.add(backBtn, 1, 4);

        updateBtn.setOnAction(e -> {
            String sql = "UPDATE MenuItem SET Name = ?, Price = ?, ResId = ? WHERE Id = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nameField.getText());
                ps.setDouble(2, Double.parseDouble(priceField.getText()));
                ps.setInt(3, Integer.parseInt(resIdField.getText()));
                ps.setInt(4, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " menu item row(s) updated.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showUpdateMenu(stage));
        stage.setScene(new Scene(form, 520, 280));
    }

    private void showDeleteRestaurant(Stage stage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(24));

        TextField idField = new TextField();
        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back");

        form.add(new Label("Id to delete:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(deleteBtn, 0, 1);
        form.add(backBtn, 1, 1);

        deleteBtn.setOnAction(e -> {
            String sql = "DELETE FROM Restaurant WHERE Id = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " restaurant row(s) deleted.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showDeleteMenu(stage));
        stage.setScene(new Scene(form, 420, 180));
    }

    private void showDeleteMenuItem(Stage stage) {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(24));

        TextField idField = new TextField();
        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back");

        form.add(new Label("Id to delete:"), 0, 0);
        form.add(idField, 1, 0);
        form.add(deleteBtn, 0, 1);
        form.add(backBtn, 1, 1);

        deleteBtn.setOnAction(e -> {
            String sql = "DELETE FROM MenuItem WHERE Id = ?";
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " menu item row(s) deleted.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showDeleteMenu(stage));
        stage.setScene(new Scene(form, 420, 180));
    }

    private void showRestaurantTable(Stage stage, String title, String sql) {
        TableView<Restaurant> table = new TableView<>();
        TableColumn<Restaurant, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Restaurant, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Restaurant, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        table.getColumns().addAll(idCol, nameCol, addressCol);

        ObservableList<Restaurant> rows = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                rows.add(new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address")));
            }
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
        table.setItems(rows);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showSelectMenu(stage));
        VBox root = new VBox(12, new Label(title), table, backBtn);
        root.setPadding(new Insets(24));
        stage.setScene(new Scene(root, 700, 420));
    }

    private void showMenuItemTable(Stage stage, String title, String sql) {
        TableView<MenuItem> table = new TableView<>();
        TableColumn<MenuItem, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<MenuItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<MenuItem, Integer> resIdCol = new TableColumn<>("ResId");
        resIdCol.setCellValueFactory(new PropertyValueFactory<>("resId"));
        table.getColumns().addAll(idCol, nameCol, priceCol, resIdCol);

        ObservableList<MenuItem> rows = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                rows.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"), rs.getDouble("Price"), rs.getInt("ResId")));
            }
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
        table.setItems(rows);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showSelectMenu(stage));
        VBox root = new VBox(12, new Label(title), table, backBtn);
        root.setPadding(new Insets(24));
        stage.setScene(new Scene(root, 760, 420));
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
