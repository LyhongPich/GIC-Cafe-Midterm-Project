package gic.i4b.group6.CafeManagement.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "sizes", uniqueConstraints = @UniqueConstraint(columnNames = "price_name"))
public class Sizes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String price_size;
    private String price_name;

    @OneToMany(mappedBy = "sizes", cascade = CascadeType.ALL)
    private List<Orders> orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice_size() {
        return price_size;
    }

    public void setPrice_size(String price_size) {
        this.price_size = price_size;
    }

    public String getPrice_name() {
        return price_name;
    }

    public void setPrice_name(String price_name) {
        this.price_name = price_name;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    
}
