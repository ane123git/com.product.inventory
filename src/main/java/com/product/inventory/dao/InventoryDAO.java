package com.product.inventory.dao;

import com.product.inventory.model.Inventory;
import com.product.inventory.model.InventoryList;
import org.springframework.stereotype.Repository;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Repository
public class InventoryDAO
{
    private static InventoryList inventoryList = new InventoryList();
    
    static 
    {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            inventoryList.getinventoryList().add(new Inventory(1,"Prod1", "Shirt", "EACH",new Double("10.0"),format.parse("2022-08-17")));
            inventoryList.getinventoryList().add(new Inventory(2,"Prod1", "Shirt", "EACH",new Double("20.0"),format.parse("2022-08-18")));
            inventoryList.getinventoryList().add(new Inventory(3,"Prod1", "Shirt", "EACH",new Double("20.0"),format.parse("2022-08-25")));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    
    public InventoryList getAllInventory()
    {
        return inventoryList;
    }
    
    public void addInventory(Inventory inventory) {
        inventoryList.getinventoryList().add(inventory);
    }
}
