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
@Table(name = "addons", uniqueConstraints = @UniqueConstraint(columnNames = "addon_name"))
public class Addons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String addon_name;
    private Float addon_price;

    @OneToMany(mappedBy = "addons", cascade = CascadeType.ALL)
    private List<Orders> orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddon_name() {
        return addon_name;
    }

    public void setAddon_name(String addon_name) {
        this.addon_name = addon_name;
    }

    public Float getAddon_price() {
        return addon_price;
    }

    public void setAddon_price(Float addon_price) {
        this.addon_price = addon_price;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    
}
