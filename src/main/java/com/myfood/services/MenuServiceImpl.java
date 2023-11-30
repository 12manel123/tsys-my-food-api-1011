package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myfood.dao.IMenuDAO;
import com.myfood.dto.Menu;

@Service
public class MenuServiceImpl implements IMenuService {

    @Autowired
    private IMenuDAO menuDAO;

    @Override
    public List<Menu> getAllMenus() {
        return menuDAO.findAll();
    }

    @Override
    public Optional<Menu> getOneMenu(Long id) {
        return menuDAO.findById(id);
    }

    @Override
    public Menu createMenu(Menu entity) {
        return menuDAO.save(entity);
    }

    @Override
    public Menu updateMenu(Menu entity) {
        return menuDAO.save(entity);
    }

    @Override
    public void deleteMenu(Long id) {
    	menuDAO.deleteById(id);
    }
    @Override
	public Page<Menu> getAllMenuWithPagination(Pageable pageable) {
	    return menuDAO.findAll(pageable);
	}
}
