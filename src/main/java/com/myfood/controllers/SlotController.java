package com.myfood.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Slot;
import com.myfood.dto.SlotUserDTO;
import com.myfood.services.ISlotService;

/**
 * Controller class for handling slot-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on slots, as well as
 * specific operations related to available slots for making orders.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
 */
@RestController
@RequestMapping("api/v1")
public class SlotController {

    @Autowired
    private ISlotService slotService;

    /**
     * Retrieve all slots.
     *
     * @return ResponseEntity containing a list of all slots.
     */
    @GetMapping("/slots")
    public ResponseEntity<List<Slot>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    /**
     * Retrieve a specific slot by its ID.
     *
     * @param id The ID of the slot to retrieve.
     * @return ResponseEntity containing the requested slot or a 404 response if not found.
     */
    @GetMapping("/slot/{id}")
    public ResponseEntity<Slot> getOneSlot(@PathVariable(name = "id") Long id) {
    	if (!slotService.getOneSlot(id).isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
        Optional<Slot> entity = slotService.getOneSlot(id);
        if (entity.isPresent()) {
            return ResponseEntity.ok(entity.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create a new slot.
     *
     * @param entity The slot to be created.
     * @return ResponseEntity containing the created slot.
     */
    @PostMapping("/slot")
    public ResponseEntity<Slot> saveSlot(@RequestBody Slot entity) {
        return ResponseEntity.ok(slotService.createSlot(entity));
    }

    /**
     * Update a slot by its ID.
     *
     * @param id The ID of the slot to update.
     * @param entity The updated slot information.
     * @return ResponseEntity containing the updated slot or a 404 response if not found.
     */
    @PutMapping("/slot/{id}")
    public ResponseEntity<Slot> updateSlot(@PathVariable(name = "id") Long id, @RequestBody Slot entity) {
    	if (!slotService.getOneSlot(id).isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
        Optional<Slot> entityOld = slotService.getOneSlot(id);
        if (entityOld.isPresent()) {
            entity.setId(id);
            return ResponseEntity.ok(slotService.updateSlot(entity));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a slot by its ID.
     *
     * @param id The ID of the slot to delete.
     * @return ResponseEntity indicating the result of the delete operation.
     */
    @DeleteMapping("/slot/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable(name = "id") Long id) {
    	if (!slotService.getOneSlot(id).isPresent()) {
    		return ResponseEntity.notFound().build();
    	}
        Optional<Slot> entity = slotService.getOneSlot(id);
        if (entity.isPresent()) {
            slotService.deleteSlot(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieve all available slots for making orders, including only the time and ID.
     *
     * @return ResponseEntity containing a list of SlotUserDTO representing available slots.
     */
    @GetMapping("/slots/available")
    public ResponseEntity<List<SlotUserDTO>> getAvailableSlots() {
        List<Slot> allSlots = slotService.getAllSlots();
        List<Slot> availableSlots = allSlots.stream().filter(slot -> slot.getActual() < slot.getLimitSlot()).collect(Collectors.toList());
        List<SlotUserDTO> slotId = new ArrayList<>();
        ZoneId madridZone = ZoneId.of("Europe/Madrid");
        LocalDateTime now = LocalDateTime.now(madridZone);
        int currentHour = now.getHour();
        int currentMinute = now.getMinute();
        for (Slot slot2 : availableSlots) {
        	String[] parts = slot2.getTime().split(":");
            int slotHour = Integer.parseInt(parts[0]);
            int slotMinute = Integer.parseInt(parts[1]);
            if(currentHour >= 15){
            	slotId.add(new SlotUserDTO(slot2.getId(),slot2.getTime()));
            }
            else if(currentHour ==slotHour) {
            	if(currentMinute+15 <= slotMinute) {
                	slotId.add(new SlotUserDTO(slot2.getId(),slot2.getTime()));
            	}
            }
            else if(currentHour<slotHour){
            	slotId.add(new SlotUserDTO(slot2.getId(),slot2.getTime()));
            }
        }
        return ResponseEntity.accepted().body(slotId);
    }

    /**
     * Update the 'actual' value of all slots to 0. This function is scheduled to run at 15:00 daily.
     *
     * @return ResponseEntity indicating the result of the update operation.
     */
    @Scheduled(cron = "0 0 15 * * ?", zone = "Europe/Madrid")
    @PutMapping("/slots/actual")
    public ResponseEntity<Slot> updateSlot() {
    	List<Slot> allSlots = slotService.getAllSlots();
    	for (Slot slot : allSlots) {
    		
    		slot.setActual(0);
    		slotService.updateSlot(slot);
			System.out.println("test2"+slot);
		}
        return ResponseEntity.noContent().build();
    }
}
