package gic.i4b.group6.CafeManagement.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "drinks", uniqueConstraints = {
    @UniqueConstraint(columnNames = "drink_code"),
    @UniqueConstraint(columnNames = "drink_name")
})
public class Drinks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String drink_code;
    private String drink_name;
    private Float prices;
    private String note;

    @Lob
    @Column(length = 1000000)
    private String drink_picture;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Categories categories;

    @OneToMany(mappedBy = "drinks", cascade = CascadeType.ALL)
    private List<Orders> orders;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDrink_code() {
        return drink_code;
    }

    public void setDrink_code(String drink_code) {
        this.drink_code = drink_code;
    }

    public String getDrink_name() {
        return drink_name;
    }

    public void setDrink_name(String drink_name) {
        this.drink_name = drink_name;
    }

    public Float getPrices() {
        return prices;
    }

    public void setPrices(Float prices) {
        this.prices = prices;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDrink_picture() {
        return drink_picture;
    }

    public void setDrink_picture(String drink_picture) {
        this.drink_picture = drink_picture;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }
}
