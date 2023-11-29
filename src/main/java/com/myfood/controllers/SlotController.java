package com.myfood.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Slot;
import com.myfood.dto.SlotUserDTO;
import com.myfood.services.SlotServiceImpl;

@RestController
@RequestMapping("api/v1")
public class SlotController {

    @Autowired
    private SlotServiceImpl slotService;

    /**
     * Retrieves a list of all available time slots. It's for ADMIN.
     *
     * @return ResponseEntity containing a list of {@link Slot} objects.
     * @apiNote This endpoint retrieves and returns a list of all available time slots.
     * The response includes a ResponseEntity with status 200 (OK) and a body containing
     * the list of {@link Slot} objects representing different time slots. If there are
     * no time slots available, an empty list is returned. This endpoint is intended for
     * administrative purposes to view and manage time slots.
     * @see SlotService#getAllSlots()
     * @see Slot
     */
    @GetMapping("/slots")
    public ResponseEntity<List<Slot>> getAllSlots() {
        return ResponseEntity.ok(slotService.getAllSlots());
    }

    /**
     * Retrieves details of a specific time slot identified by its ID. It's for ADMIN
     *
     * @param id The unique identifier of the time slot.
     * @return ResponseEntity containing the details of the time slot as a {@link Slot} object.
     * @throws DataNotFoundException If the specified time slot does not exist.
     *
     * @apiNote This endpoint retrieves and returns details of a specific time slot identified by its unique ID.
     * If the time slot is found, its details are encapsulated in a ResponseEntity with status 200 (OK), and
     * the body contains the {@link Slot} object representing the time slot. If the time slot does not exist,
     * a ResponseEntity with status 400 (Bad Request) is returned, and an error message indicating that the time slot
     * does not exist is included in the response body. This endpoint is intended for user interfaces to view details
     * of a specific time slot.
     *
     * @see SlotService#getOneSlot(Long)
     * @see Slot
     */
    @GetMapping("/slot/{id}")
    public ResponseEntity<?> getOneSlot(@PathVariable(name = "id") Long id) {
        Optional<Slot> optionalSlot = slotService.getOneSlot(id);
        if (optionalSlot.isEmpty()) {
            return createErrorResponse("The slot not exists", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(optionalSlot.get());
    }

    /**
     * Creates a new time slot based on the provided details. It's for ADMIN.
     *
     * @param entity The time slot details provided in the request body.
     * @return ResponseEntity containing the details of the created time slot as a {@link Slot} object.
     * @apiNote This endpoint creates a new time slot based on the details provided in the request body.
     * If the time slot is successfully created, the details of the created time slot are encapsulated in
     * a ResponseEntity with status 200 (OK), and the body contains the {@link Slot} object representing the
     * newly created time slot. This endpoint is intended for administrative purposes to create new time slots.
     * @see SlotService#createSlot(Slot)
     * @see Slot
     */
    @PostMapping("/slot")
    public ResponseEntity<Slot> saveSlot(@RequestBody Slot entity) {
        return ResponseEntity.ok(slotService.createSlot(entity));
    }

    /**
     * Updates an existing time slot with the provided details. It's for ADMIN.
     *
     * @param id     The identifier of the time slot to be updated.
     * @param entity The updated time slot details provided in the request body.
     * @return ResponseEntity containing a message and the details of the updated time slot as a {@link Slot} object.
     * @apiNote This endpoint updates an existing time slot based on the provided details in the request body.
     * If the time slot is successfully updated, a ResponseEntity with status 200 (OK) is returned,
     * along with a message indicating the successful update and the details of the updated time slot encapsulated
     * in a ResponseEntity with status 200 (OK). If the time slot with the specified ID does not exist,
     * a ResponseEntity with status 400 (Bad Request) is returned, and an error message indicating that the time slot
     * does not exist is included in the response body. This endpoint is intended for administrative purposes
     * to update existing time slots.
     * @see SlotService#getOneSlot(Long)
     * @see SlotService#updateSlot(Slot)
     * @see Slot
     */
    @PutMapping("/slot/{id}")
    public ResponseEntity<?> updateSlot(@PathVariable(name = "id") Long id, @RequestBody Slot entity) {
        Optional<Slot> entityOld = slotService.getOneSlot(id);
        if (entityOld.isPresent()) {
            entity.setId(id);
            return ResponseEntity.ok(slotService.updateSlot(entity));
        } else {
            return createErrorResponse("The slot not exists", HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Deletes an existing time slot based on the provided time slot ID. It's for ADMIN.
     *
     * @param id The identifier of the time slot to be deleted.
     * @return ResponseEntity indicating the success or failure of the delete operation.
     * @apiNote This endpoint deletes an existing time slot with the specified ID.
     * If the time slot is successfully deleted, a ResponseEntity with status 200 (OK) is returned,
     * and the details of the deleted time slot are included in the response body. If the time slot with the
     * specified ID does not exist, a ResponseEntity with status 400 (Bad Request) is returned,
     * and an error message indicating that the time slot does not exist is included in the response body.
     * This endpoint is intended for administrative purposes to delete existing time slots.
     * @see SlotService#getOneSlot(Long)
     * @see SlotService#deleteSlot(Long)
     */
    @DeleteMapping("/slot/{id}")
    public ResponseEntity<?> deleteSlot(@PathVariable(name = "id") Long id) {
        Optional<Slot> entity = slotService.getOneSlot(id);
        if (entity.isPresent()) {
            slotService.deleteSlot(id);
            return ResponseEntity.ok((entity));
        } else {
            return createErrorResponse("The slot not exists", HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Retrieves a list of available time slots for users. It's for USER.
     *
     * @return ResponseEntity containing a list of SlotUserDTO representing available time slots.
     * @apiNote This endpoint retrieves a list of available time slots for users. The availability of a time slot is determined
     * based on the current time and the defined capacity limit for each time slot. If the current time is after 15:00, all time slots
     * are considered available. If the current time is before 15:00, only the time slots starting 15 minutes or more after the
     * current time are considered available. The list includes SlotUserDTO objects with information such as time slot ID and time.
     * @see SlotService#getAllSlots()
     * @see SlotUserDTO
     */
    @GetMapping("/slots/available")
    public ResponseEntity<List<SlotUserDTO>> getAvailableSlots() {
        List<Slot> allSlots = slotService.getAllSlots();
        List<Slot> availableSlots = allSlots.stream().filter(slot -> slot.getActual() < slot.getLimitSlot()).toList();
        List<SlotUserDTO> slotId = new ArrayList<>();
        ZoneId madridZone = ZoneId.of("Europe/Madrid");
        LocalDateTime now = LocalDateTime.now(madridZone);
        int currentHour = now.getHour();
        int currentMinute = now.getMinute();
        for (Slot slot : availableSlots) {
            String[] parts = slot.getTime().split(":");
            int slotHour = Integer.parseInt(parts[0]);
            int slotMinute = Integer.parseInt(parts[1]);
            if (currentHour >= 15 || (currentHour == slotHour && currentMinute + 15 <= slotMinute) || currentHour < slotHour) {
                slotId.add(new SlotUserDTO(slot.getId(), slot.getTime()));
            }
        }
        return ResponseEntity.accepted().body(slotId);
    }

    /**
     * Scheduled task to reset the actual count of slots every day at 15:00. It's for SYSTEM.
     *
     * @return ResponseEntity with status 204 (No Content) after updating the actual count of all slots.
     * @apiNote This scheduled task resets the actual count of all slots to 0 every day at 15:00 (3:00 PM) Madrid time.
     * It is designed to be triggered automatically by the scheduler. After updating the actual count for all slots,
     * a ResponseEntity with status 204 (No Content) is returned to indicate that the operation was successful with no additional content.
     * @see Scheduled
     * @see SlotService#updateSlot(Slot)
     */
    @Scheduled(cron = "0 0 15 * * ?", zone = "Europe/Madrid")
    @PutMapping("/slots/actual")
    public ResponseEntity<Slot> updateSlot() {
    	List<Slot> allSlots = slotService.getAllSlots();
    	for (Slot slot : allSlots) {
    		slot.setActual(0);
    		slotService.updateSlot(slot);
		}
        return ResponseEntity.noContent().build();
    }
    
    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("Message", message);
        return ResponseEntity.status(status).body(responseData);
    }
}
