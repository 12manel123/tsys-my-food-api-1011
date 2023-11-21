package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.Slot;

public interface ISlotService {

    List<Slot> getAllSlots();

    Optional<Slot> getOneSlot(Long id);

    Slot createSlot(Slot entity);

    Slot updateSlot(Slot entity);

    void deleteSlot(Long id);
}
