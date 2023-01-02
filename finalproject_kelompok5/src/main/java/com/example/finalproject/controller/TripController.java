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

import com.example.finalproject.models.Agency;
import com.example.finalproject.models.Bus;
import com.example.finalproject.models.Stop;
import com.example.finalproject.models.Trip;
import com.example.finalproject.payload.request.TripRequest;
import com.example.finalproject.repository.AgencyRepository;
import com.example.finalproject.repository.BusRepository;
import com.example.finalproject.repository.StopRepository;
import com.example.finalproject.repository.TripRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/trip")
public class TripController {
	@Autowired
	TripRepository tripRepository;

	@Autowired
	AgencyRepository agencyRepository;

	@Autowired
	BusRepository busRepository;

	@Autowired
	StopRepository stopRepository;
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll(){
		List<TripRequest> dataArrResult = new ArrayList<>() ;
		for(Trip dataArr : tripRepository.findAll()){
			dataArrResult.add(new TripRequest(dataArr.getId(), dataArr.getFare(), dataArr.getJourneyTime(), dataArr.getAgency().getId(), dataArr.getBus().getId(), dataArr.getSourceStop().getId(), dataArr.getDestStop().getId()));
		}
        return ResponseEntity.ok(dataArrResult);
	}
	
	@PostMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTrip(@Valid @RequestBody TripRequest tripRequest) {
		Agency agency = agencyRepository.findById(tripRequest.getAgencyid()).get();
		Bus bus = busRepository.findById(tripRequest.getBusId()).get();
		Stop sourceStop = stopRepository.findById(tripRequest.getSourceStopId()).get();
		Stop destStop = stopRepository.findById(tripRequest.getDestStopid()).get();
		Trip trip  = new Trip(tripRequest.getFare(), tripRequest.getJourneyTime(), sourceStop, destStop, bus, agency);
        return ResponseEntity.ok(tripRepository.save(trip));
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTrip(@PathVariable(value="id") Long id, @Valid @RequestBody TripRequest tripDetail){
		Agency agency = agencyRepository.findById(tripDetail.getAgencyid()).get();
		Bus bus = busRepository.findById(tripDetail.getBusId()).get();
		Stop sourceStop = stopRepository.findById(tripDetail.getSourceStopId()).get();
		Stop destStop = stopRepository.findById(tripDetail.getDestStopid()).get();
		Trip trip = tripRepository.findById(id).get();
		if(trip == null) {
			return ResponseEntity.notFound().build();
		}
        trip.setFare(tripDetail.getFare());
        trip.setJourneyTime(tripDetail.getJourneyTime());
        trip.setAgency(agency);
        trip.setBus(bus);
        trip.setDestStop(destStop);
        trip.setSourceStop(sourceStop);

        Trip updatedTrip = tripRepository.save(trip);

        return ResponseEntity.ok(updatedTrip);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTrip(@PathVariable(value="id") Long id) {
		String result = "";
		try {
            tripRepository.findById(id).get();
			
			result = "Success delete data with id: " + id;
            tripRepository.deleteById(id);

            return ResponseEntity.ok(result);
		} catch(Exception e) {
			result = "Data with id: " + id + " not found";
            return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getTripById(@PathVariable(value="id") Long id){
        Trip trip = tripRepository.findById(id).get();
		if(trip == null) {
			return ResponseEntity.notFound().build();
		} else {
			TripRequest dataResult = new TripRequest(trip.getId(), trip.getFare(), trip.getJourneyTime(), trip.getAgency().getId(), trip.getBus().getId(), trip.getSourceStop().getId(), trip.getDestStop().getId());
            return ResponseEntity.ok(dataResult);
		}
	}
}
