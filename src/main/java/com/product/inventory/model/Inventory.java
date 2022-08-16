package com.product.inventory.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue
    private int id;
    private  String productId;
    private String prodName;
    private String UOM;
    private  Double availQty;
    private java.util.Date availDate;


}
