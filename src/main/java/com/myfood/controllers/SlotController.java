package com.myfood.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Slot;
import com.myfood.services.ISlotService;

@RestController
@RequestMapping("api/v1")
public class SlotController {

    @Autowired
    private ISlotService slotService;

    @GetMapping("/slots")
    public ResponseEntity<List<Slot>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    @GetMapping("/slot/{id}")
    public ResponseEntity<Slot> getOneSlot(@PathVariable(name = "id") Long id) {
        Optional<Slot> entity = slotService.getOneSlot(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/slot")
    public ResponseEntity<Slot> saveSlot(@RequestBody Slot entity) {
        return ResponseEntity.ok(slotService.createSlot(entity));
    }

    @PutMapping("/slot/{id}")
    public ResponseEntity<Slot> updateSlot(@PathVariable(name = "id") Long id, @RequestBody Slot entity) {
        Optional<Slot> entityOld = slotService.getOneSlot(id);
        if (entityOld.isPresent()) {
            entity.setId(id);
            return ResponseEntity.ok(slotService.updateSlot(entity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/slot/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable(name = "id") Long id) {
        Optional<Slot> entity = slotService.getOneSlot(id);
        if (entity.isPresent()) {
            slotService.deleteSlot(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
