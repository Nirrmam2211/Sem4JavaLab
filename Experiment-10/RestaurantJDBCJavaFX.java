import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RestaurantJDBCJavaFX extends Application {

    static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/restaurant_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "sit123";

    public static Connection getConnection() throws Exception {
        Class.forName(DRIVER_CLASS);
        return DriverManager.getConnection(URL, USER, PASS);
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
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Restaurant JDBC CRUD Application");
        showMainScene(primaryStage);
        primaryStage.show();
    }

    private void showMainScene(Stage stage) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));

        Label title = new Label("Select Operation:");
        Button insertBtn = new Button("Insert");
        Button selectBtn = new Button("Select");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");

        insertBtn.setOnAction(e -> showInsertScene(stage));
        selectBtn.setOnAction(e -> showSelectScene(stage));
        updateBtn.setOnAction(e -> showUpdateScene(stage));
        deleteBtn.setOnAction(e -> showDeleteScene(stage));

        mainLayout.getChildren().addAll(title, insertBtn, selectBtn, updateBtn, deleteBtn);
        stage.setScene(new Scene(mainLayout, 400, 300));
    }

    private void showInsertScene(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label label = new Label("Insert into:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showInsertRestaurantScene(stage));
        menuItemBtn.setOnAction(e -> showInsertMenuItemScene(stage));
        backBtn.setOnAction(e -> showMainScene(stage));

        layout.getChildren().addAll(label, restaurantBtn, menuItemBtn, backBtn);
        stage.setScene(new Scene(layout, 400, 300));
    }

    private void showInsertRestaurantScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField addressField = new TextField();
        Button insertBtn = new Button("Insert");
        Button backBtn = new Button("Back");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(insertBtn, 0, 3);
        grid.add(backBtn, 1, 3);

        insertBtn.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO Restaurant VALUES (?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.setString(2, nameField.getText());
                ps.setString(3, addressField.getText());
                ps.executeUpdate();
                showAlert("Success", "Restaurant inserted successfully!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showInsertScene(stage));
        stage.setScene(new Scene(grid, 400, 300));
    }

    private void showInsertMenuItemScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField resIdField = new TextField();
        Button insertBtn = new Button("Insert");
        Button backBtn = new Button("Back");

        grid.add(new Label("ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Price:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("ResId:"), 0, 3);
        grid.add(resIdField, 1, 3);
        grid.add(insertBtn, 0, 4);
        grid.add(backBtn, 1, 4);

        insertBtn.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO MenuItem VALUES (?, ?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                ps.setString(2, nameField.getText());
                ps.setDouble(3, Double.parseDouble(priceField.getText()));
                ps.setInt(4, Integer.parseInt(resIdField.getText()));
                ps.executeUpdate();
                showAlert("Success", "MenuItem inserted successfully!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showInsertScene(stage));
        stage.setScene(new Scene(grid, 400, 300));
    }

    private void showSelectScene(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label label = new Label("Select from:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showSelectRestaurantScene(stage));
        menuItemBtn.setOnAction(e -> showSelectMenuItemScene(stage));
        backBtn.setOnAction(e -> showMainScene(stage));

        layout.getChildren().addAll(label, restaurantBtn, menuItemBtn, backBtn);
        stage.setScene(new Scene(layout, 400, 300));
    }

    private void showSelectRestaurantScene(Stage stage) {
        TableView<Restaurant> table = new TableView<>();
        TableColumn<Restaurant, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Restaurant, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Restaurant, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        table.getColumns().addAll(idCol, nameCol, addressCol);

        ObservableList<Restaurant> data = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Restaurant")) {
            while (rs.next()) {
                data.add(new Restaurant(rs.getInt("Id"), rs.getString("Name"), rs.getString("Address")));
            }
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
        table.setItems(data);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showSelectScene(stage));

        VBox layout = new VBox(10, table, backBtn);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 600, 400));
    }

    private void showSelectMenuItemScene(Stage stage) {
        TableView<MenuItem> table = new TableView<>();
        TableColumn<MenuItem, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<MenuItem, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<MenuItem, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<MenuItem, Integer> resIdCol = new TableColumn<>("ResId");
        resIdCol.setCellValueFactory(new PropertyValueFactory<>("resId"));

        table.getColumns().addAll(idCol, nameCol, priceCol, resIdCol);

        ObservableList<MenuItem> data = FXCollections.observableArrayList();
        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM MenuItem")) {
            while (rs.next()) {
                data.add(new MenuItem(rs.getInt("Id"), rs.getString("Name"), rs.getDouble("Price"), rs.getInt("ResId")));
            }
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
        table.setItems(data);

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> showSelectScene(stage));

        VBox layout = new VBox(10, table, backBtn);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 600, 400));
    }

    private void showUpdateScene(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label label = new Label("Update:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showUpdateRestaurantScene(stage));
        menuItemBtn.setOnAction(e -> showUpdateMenuItemScene(stage));
        backBtn.setOnAction(e -> showMainScene(stage));

        layout.getChildren().addAll(label, restaurantBtn, menuItemBtn, backBtn);
        stage.setScene(new Scene(layout, 400, 300));
    }

    private void showUpdateRestaurantScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField addressField = new TextField();
        Button updateBtn = new Button("Update");
        Button backBtn = new Button("Back");

        grid.add(new Label("ID (to update):"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("New Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("New Address:"), 0, 2);
        grid.add(addressField, 1, 2);
        grid.add(updateBtn, 0, 3);
        grid.add(backBtn, 1, 3);

        updateBtn.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE Restaurant SET Name = ?, Address = ? WHERE Id = ?")) {
                ps.setString(1, nameField.getText());
                ps.setString(2, addressField.getText());
                ps.setInt(3, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " row(s) updated!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showUpdateScene(stage));
        stage.setScene(new Scene(grid, 400, 300));
    }

    private void showUpdateMenuItemScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField priceField = new TextField();
        TextField resIdField = new TextField();
        Button updateBtn = new Button("Update");
        Button backBtn = new Button("Back");

        grid.add(new Label("ID (to update):"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("New Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("New Price:"), 0, 2);
        grid.add(priceField, 1, 2);
        grid.add(new Label("New ResId:"), 0, 3);
        grid.add(resIdField, 1, 3);
        grid.add(updateBtn, 0, 4);
        grid.add(backBtn, 1, 4);

        updateBtn.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("UPDATE MenuItem SET Name = ?, Price = ?, ResId = ? WHERE Id = ?")) {
                ps.setString(1, nameField.getText());
                ps.setDouble(2, Double.parseDouble(priceField.getText()));
                ps.setInt(3, Integer.parseInt(resIdField.getText()));
                ps.setInt(4, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " row(s) updated!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showUpdateScene(stage));
        stage.setScene(new Scene(grid, 400, 300));
    }

    private void showDeleteScene(Stage stage) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label label = new Label("Delete from:");
        Button restaurantBtn = new Button("Restaurant");
        Button menuItemBtn = new Button("MenuItem");
        Button backBtn = new Button("Back");

        restaurantBtn.setOnAction(e -> showDeleteRestaurantScene(stage));
        menuItemBtn.setOnAction(e -> showDeleteMenuItemScene(stage));
        backBtn.setOnAction(e -> showMainScene(stage));

        layout.getChildren().addAll(label, restaurantBtn, menuItemBtn, backBtn);
        stage.setScene(new Scene(layout, 400, 300));
    }

    private void showDeleteRestaurantScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back");

        grid.add(new Label("ID to delete:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(deleteBtn, 0, 1);
        grid.add(backBtn, 1, 1);

        deleteBtn.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM Restaurant WHERE Id = ?")) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " row(s) deleted!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showDeleteScene(stage));
        stage.setScene(new Scene(grid, 400, 300));
    }

    private void showDeleteMenuItemScene(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        TextField idField = new TextField();
        Button deleteBtn = new Button("Delete");
        Button backBtn = new Button("Back");

        grid.add(new Label("ID to delete:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(deleteBtn, 0, 1);
        grid.add(backBtn, 1, 1);

        deleteBtn.setOnAction(e -> {
            try (Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement("DELETE FROM MenuItem WHERE Id = ?")) {
                ps.setInt(1, Integer.parseInt(idField.getText()));
                int rows = ps.executeUpdate();
                showAlert("Success", rows + " row(s) deleted!");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> showDeleteScene(stage));
        stage.setScene(new Scene(grid, 400, 300));
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
