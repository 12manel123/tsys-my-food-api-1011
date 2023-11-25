package com.myfood.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Menu;

@Repository
public interface IMenuDAO extends JpaRepository<Menu, Long> {
	
	List<Menu> findAllByVisibleIsTrue();

}