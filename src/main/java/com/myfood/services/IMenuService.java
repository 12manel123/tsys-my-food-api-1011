package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.Menu;

public interface IMenuService {

    List<Menu> getAllMenus();

    Optional<Menu> getOneMenu(Long id);

    Menu createMenu(Menu entity);

    Menu updateMenu(Menu entity);

    void deleteMenu(Long id);
	
}
