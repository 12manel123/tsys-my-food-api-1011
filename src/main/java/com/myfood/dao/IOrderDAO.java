package com.myfood.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Order;

@Repository
public interface IOrderDAO extends JpaRepository<Order, Long> {
	/**
     * Retrieve all orders that are not marked as done.
     *
     * @return List of Order entities that are not marked as done.
     */
	List<Order> findAllByMakedIsFalse();

    /**
     * Retrieve all orders for a specific user, ordered by actual date in descending order.
     *
     * @param userId The ID of the user for whom orders are retrieved.
     * @return List of Order entities for the specified user, ordered by actual date in descending order.
     */
	List<Order> findAllByUserIdOrderByActualDateDesc(Long id);
	

}
