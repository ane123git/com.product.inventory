package com.product.inventory.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {


    private  String productId;
    private String prodName;
    private String UOM;
    private  Double availQty;
    private java.util.Date availDate;


}
