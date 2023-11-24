package com.myfood.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maked", nullable = false)
    private boolean maked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private Slot slot;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "actual_date")
    private LocalDateTime actualDate;
    
    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<ListOrder> listOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMaked() {
        return maked;
    }

    public void setMaked(boolean maked) {
        this.maked = maked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getActualDate() {
        return actualDate;
    }

    public void setActualDate(LocalDateTime actualDate) {
        this.actualDate = actualDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", maked=" + maked +
                ", userId=" + user +
                ", slot=" + slot +
                ", totalPrice=" + totalPrice +
                ", actualDate=" + actualDate +
                '}';
    }
}
