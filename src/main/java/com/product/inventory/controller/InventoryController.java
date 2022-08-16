package com.product.inventory.controller;

import com.product.inventory.dao.InventoryDAO;
import com.product.inventory.model.Inventory;
import com.product.inventory.model.InventoryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class InventoryController {

    @Autowired
    private InventoryDAO inventoryDAO;

    @GetMapping(path="/getAllInv", produces = "application/json")
    public InventoryList getInventoryList ()
    {

        return inventoryDAO.getAllInventory();
    }

    @PostMapping(path= "/", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addInventory(
            @RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
            @RequestBody Inventory inventory)
            throws Exception
    {
        //Generate resource id
        Integer id = inventoryDAO.getAllInventory().getinventoryList().size() + 1;
        inventory.setId(id);

        //add resource
        inventoryDAO.addInventory(inventory);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inventory.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }

    @PostMapping(path= "/getInvPicture", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> getInvPicture(
            @RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
            @RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
            @RequestBody Inventory inventory)
            throws Exception
    {
        //Generate resource id
        Integer id = inventoryDAO.getAllInventory().getinventoryList().size() + 1;
        inventory.setId(id);

        //add resource
        inventoryDAO.addInventory(inventory);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(inventory.getId())
                .toUri();

        //Send location in response
        return ResponseEntity.created(location).build();
    }
}
