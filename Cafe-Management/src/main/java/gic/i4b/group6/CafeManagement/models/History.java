package gic.i4b.group6.CafeManagement.models;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "history")
public class History {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer order_num;
    private Integer table_num;
    private Float price;
    private Date order_date;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrder_num() {
        return order_num;
    }
    public void setOrder_num(Integer order_num) {
        this.order_num = order_num;
    }
    public Integer getTable_num() {
        return table_num;
    }
    public void setTable_num(Integer table_num) {
        this.table_num = table_num;
    }
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }
    public Date getOrder_date() {
        return order_date;
    }
    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    
}
