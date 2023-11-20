package com.myfood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.User;

@Repository
public interface IUserDAO extends JpaRepository<User,Long>{

}
