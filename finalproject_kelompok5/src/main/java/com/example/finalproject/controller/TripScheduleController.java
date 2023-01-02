package com.example.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.models.Trip;
import com.example.finalproject.models.TripSchedule;
import com.example.finalproject.payload.request.GetTripScheduleRequest;
import com.example.finalproject.repository.TripRepository;
import com.example.finalproject.repository.TripScheduleRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/tripSchedule")
public class TripScheduleController {
	@Autowired
	TripScheduleRepository tripScheduleRepository;
	
	@Autowired
	TripRepository tripRepository;
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll(){
		List<GetTripScheduleRequest> dataArrResult = new ArrayList<>() ;
		for(TripSchedule dataArr : tripScheduleRepository.findAll()){
			dataArrResult.add(new GetTripScheduleRequest(dataArr.getId(), dataArr.getAvailableSeats(), dataArr.getTripDate(), dataArr.getTripDetail().getId()));
		}
        return ResponseEntity.ok(dataArrResult);
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<?> addTrip(@Valid @RequestBody GetTripScheduleRequest tripScheduleRequest) {
		Trip trip = tripRepository.findById(tripScheduleRequest.getTrip_detail()).get();
		TripSchedule tripSchedule = new TripSchedule(tripScheduleRequest.getTripDate(), tripScheduleRequest.getAvailable_seats(), trip);
        return ResponseEntity.ok(tripScheduleRepository.save(tripSchedule));
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<?> getTripById(@PathVariable(value="id") Long id){
        TripSchedule tripSchedule = tripScheduleRepository.findById(id).get();
		if(tripSchedule == null) {
			return ResponseEntity.notFound().build();
		} else {
			GetTripScheduleRequest dataResult = new GetTripScheduleRequest(tripSchedule.getId(), tripSchedule.getAvailableSeats(), tripSchedule.getTripDate(), tripSchedule.getTripDetail().getId());
            return ResponseEntity.ok(dataResult);
		}
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTrip(@PathVariable(value="id") Long id, @Valid @RequestBody GetTripScheduleRequest tripScheduleDetail){
		Trip trip = tripRepository.findById(tripScheduleDetail.getTrip_detail()).get();
		TripSchedule tripSchedule = tripScheduleRepository.findById(id).get();
		if(tripSchedule == null) {
			return ResponseEntity.notFound().build();
		}
        tripSchedule.setAvailableSeats(tripScheduleDetail.getAvailable_seats());
        tripSchedule.setTripDate(tripScheduleDetail.getTripDate());
        tripSchedule.setTripDetail(trip);

        TripSchedule updatedTripSchedule = tripScheduleRepository.save(tripSchedule);

        return ResponseEntity.ok(updatedTripSchedule);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
	public ResponseEntity<?> deleteTrip(@PathVariable(value="id") Long id) {
		String result = "";
		try {
            tripScheduleRepository.findById(id).get();
			
			result = "Success delete data with id : " + id;
            tripScheduleRepository.deleteById(id);

            return ResponseEntity.ok(result);
		} catch(Exception e) {
			result = "Data with id: " + id + " not found";
            return ResponseEntity.ok(result);
		}
	}
}
