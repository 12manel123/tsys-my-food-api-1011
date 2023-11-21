package com.myfood.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "slots")
public class Slot {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "limit_slot", nullable = false)
    private int limitSlot;

    @Column(name = "actual", nullable = false)
    private int actual;
    
    @OneToMany(mappedBy = "slot")
    @JsonIgnore
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLimitSlot() {
        return limitSlot;
    }

    public void setLimitSlot(int limitSlot) {
        this.limitSlot = limitSlot;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }
    
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", limitSlot=" + limitSlot +
                ", actual=" + actual +
                '}';
    }
}
