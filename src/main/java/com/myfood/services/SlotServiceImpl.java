package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfood.dao.ISlotDAO;
import com.myfood.dto.Slot;

@Service
public class SlotServiceImpl implements ISlotService {

    @Autowired
    private ISlotDAO slotDAO;

    @Override
    public List<Slot> getAllSlots() {
        return slotDAO.findAll();
    }

    @Override
    public Optional<Slot> getOneSlot(Long id) {
        return slotDAO.findById(id);
    }

    @Override
    public Slot createSlot(Slot entity) {
        return slotDAO.save(entity);
    }

    @Override
    public Slot updateSlot(Slot entity) {
        return slotDAO.save(entity);
    }

    @Override
    public void deleteSlot(Long id) {
        slotDAO.deleteById(id);
    }
}
