package com.myfood.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.ListOrder;

@Repository
public interface IListOrderDAO extends JpaRepository<ListOrder, Long> {
	
    Optional<ListOrder> findByOrderIdAndMenuId(Long orderId, Long menuId);

    Optional<ListOrder> findByOrderIdAndDishId(Long orderId, Long dishId);
    
	// TODO Falta Pages para el BackOffice
    
}
