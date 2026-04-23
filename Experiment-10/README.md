# Experiment-10

## Restaurant JDBC JavaFX CRUD Application

This experiment extends the JDBC application into a JavaFX menu-driven UI that performs CRUD operations on:

- `Restaurant`
- `MenuItem`

## File

- [`RestaurantJDBCJavaFX.java`](./RestaurantJDBCJavaFX.java)

## Features

- Insert records
- Select records
- Update records
- Delete records
- Menu-driven JavaFX navigation

## Run in VS Code

1. Open the `Experiment-10` folder in VS Code.
2. Make sure you have:
   - Java 17+ installed
   - Maven installed in VS Code
   - MySQL running on `localhost:3306`
3. Update the database credentials in the code if needed.
4. Run from the terminal with Maven:

```powershell
mvn clean javafx:run
```

5. If you prefer manual execution, configure the JavaFX SDK module path in VS Code and compile the root `RestaurantJDBCJavaFX.java` file.

## Database

- Database name: `restaurant_db`
- MySQL username: `root`
- MySQL password: `root`
