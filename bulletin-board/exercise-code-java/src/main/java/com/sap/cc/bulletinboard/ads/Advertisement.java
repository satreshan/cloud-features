package com.sap.cc.bulletinboard.ads;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
public class Advertisement implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String contact;
    private BigDecimal price;
    private String currency;



    private float averageContactRating;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    @Override
    public String toString() {
        return "Advertisement [id=" + id + ", title=" + title + ", contact=" + contact + ", price=" + price + ", currency=" + currency + ", averageContactRating=" + averageContactRating + "]";
    }

    public Advertisement() {
    }

    public Advertisement(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }
    public float getAverageContactRating() {
        return averageContactRating;
    }

    public void setAverageContactRating(float averageContactRating) {
        this.averageContactRating = averageContactRating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
