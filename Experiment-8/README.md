# Experiment-8

## Inventory Management System

This Java program demonstrates:

- Singleton pattern through `InventoryManager`
- Adapter pattern through `ProductAdapter`
- Iterator pattern through `returnInventory()`

## File

- [`InventoryManager.java`](./InventoryManager.java)
- [`Inventory-Output.png`](./Inventory-Output.png)

## Run

```powershell
javac InventoryManager.java
java InventoryManagerApp
```

## Output

The program creates products using both `NewProduct` and `LegacyItem`, stores them in a single inventory instance, and iterates through them using a Java `Iterator`.
