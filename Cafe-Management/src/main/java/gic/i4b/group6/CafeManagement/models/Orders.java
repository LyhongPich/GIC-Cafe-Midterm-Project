package gic.i4b.group6.CafeManagement.models;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = {
    "drink_id",
    "size_id",
    "addon_id",
    "table_id"
}))
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "drink_id")
    private Drinks drinks;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Sizes sizes;

    @ManyToOne
    @JoinColumn(name = "addon_id")
    private Addons addons;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Tables tables;

    private Date local_date_time;
    private Integer quantity;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<Invoices> invoices;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Drinks getDrinks() {
        return drinks;
    }

    public void setDrinks(Drinks drinks) {
        this.drinks = drinks;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public Addons getAddons() {
        return addons;
    }

    public void setAddons(Addons addons) {
        this.addons = addons;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public Date getLocalDateTime() {
        return local_date_time;
    }

    public void setLocalDateTime(Date local_date_time) {
        this.local_date_time = local_date_time;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Invoices> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoices> invoices) {
        this.invoices = invoices;
    }

}
