package com.example.freatnor.content_providers_lab.models;

/**
 * Created by Jonathan Taylor on 8/14/16.
 */
public class StockItem {
    private String Symbol;
    private String Name;
    private String Exchange;
    private String quantity;

    public String getSymbol() {
        return Symbol;
    }

    public void setSymbol(String symbol) {
        Symbol = symbol;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getExchange() {
        return Exchange;
    }

    public void setExchange(String exchange) {
        Exchange = exchange;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
