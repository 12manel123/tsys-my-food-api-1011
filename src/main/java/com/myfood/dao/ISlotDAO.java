package com.myfood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Slot;

@Repository
public interface ISlotDAO extends JpaRepository<Slot, Long> {

}
