package com.product.inventory.controller;

import com.product.inventory.dao.InventoryDAO;
import com.product.inventory.model.Inventory;
import com.product.inventory.model.InventoryList;
import com.product.inventory.model.InventoryRequest;
import com.product.inventory.model.InventoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
public class InventoryController {


    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
    @Autowired
    private InventoryDAO inventoryDAO;


    @GetMapping(path="/getAllInv", produces = "application/json")
    public InventoryList getInventoryList ()
    {

        return inventoryDAO.getAllInventory();
    }


    @PostMapping(path= "/getInvPicture", consumes = "application/json", produces = "application/json")
    public Object getInvPicture(@RequestBody InventoryRequest inventoryRequest)
            throws Exception



    {
        Double sum =0.0;
        Date inpDtd=new SimpleDateFormat("yyyy-MM-dd").parse(inventoryRequest.getReqDate());
        LocalDate today = LocalDate.now();
        LocalDate tenDaysLater = today.plusDays(9);
        java.util.Date tenDaysLaterDate = java.sql.Date.valueOf(tenDaysLater);

        long ltime = inpDtd.getTime()+8*24*60*60*1000;

        Date supplyVisibility = new Date(ltime);

        Date dateNow = new Date();

        logger.debug("inpDtd: "+inpDtd+", today: "+today,", tenDaysLaterDate: "+tenDaysLaterDate+ ", supplyVisibility: "+supplyVisibility);
        /*System.out.println(myDate);
        String a = new SimpleDateFormat("yyyy-MM-dd").parse(myDate);*/

        if (dateNow.after(inpDtd)) {
            logger.error("Error : Input Date is in past");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error : Input Date is in past");
        }
        else if (inpDtd.after(tenDaysLaterDate)) {
            logger.error("Error : Input Date is 10 days or more later");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error : Input Date is 10 days or more later");
        }




        InventoryList inventoryList= inventoryDAO.getAllInventory();

        List<Inventory> inventoryList_1 = inventoryList.getinventoryList();

        sum=inventoryList_1.stream()
                .filter(inventory1 -> {
            Date invDtd = inventory1.getAvailDate();
            if ((supplyVisibility.equals(invDtd)|| supplyVisibility.after(invDtd)) && (inpDtd.equals(invDtd)|| inpDtd.before(invDtd)) ) {
                logger.debug("Valid Inv ");
                return true;
            }
                return false;
                })
                .mapToDouble(Inventory::getAvailQty)
                .sum();


        logger.debug("sumQty "+sum);

        InventoryResponse objResp= new InventoryResponse();
        objResp.setAvailQty(sum.toString());
        objResp.setProdName(inventoryRequest.getProdName());
        objResp.setProductId(inventoryRequest.getProductId());


        return objResp;
    }
}
