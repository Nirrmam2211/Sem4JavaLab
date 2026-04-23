# Experiment-9

## Restaurant JDBC Application

This folder contains a Java JDBC console application that:

- connects to MySQL
- creates the `restaurant_db` database if needed
- creates `Restaurant` and `MenuItem` tables
- inserts sample data
- displays, updates, and deletes records

## Files

- [`RestaurantJDBC.java`](./RestaurantJDBC.java)

## Run in VS Code

1. Open this folder in VS Code.
2. Download the MySQL Connector/J JAR and place it in this folder before running.
3. Compile:

```powershell
javac -cp ".;mysql-connector-j-9.0.0.jar" RestaurantJDBC.java
```

4. Run:

```powershell
java -cp ".;mysql-connector-j-9.0.0.jar" RestaurantJDBC
```

## Database Notes

- MySQL database name: `restaurant_db`
- Username: `root`
- Password: `root`
