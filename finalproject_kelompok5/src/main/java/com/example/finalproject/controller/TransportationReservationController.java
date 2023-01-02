package com.example.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.models.Ticket;
import com.example.finalproject.models.Trip;
import com.example.finalproject.models.TripSchedule;
import com.example.finalproject.models.User;
import com.example.finalproject.payload.request.GetTripScheduleRequest;
import com.example.finalproject.payload.request.TicketRequest;
import com.example.finalproject.payload.request.TripRequest;
import com.example.finalproject.repository.StopRepository;
import com.example.finalproject.repository.TicketRepository;
import com.example.finalproject.repository.TripRepository;
import com.example.finalproject.repository.TripScheduleRepository;
import com.example.finalproject.repository.UserRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservation")
public class TransportationReservationController {
	@Autowired
    StopRepository stopRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    TripScheduleRepository tripScheduleRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/stops")
    @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllStops(){
        return ResponseEntity.ok(stopRepository.findAll());
	}

    @GetMapping("/tripsbystops")
    @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTripsByStops(@RequestParam String sourceStop, @RequestParam String destStop) {
    	List<TripRequest> dataArrResult = new ArrayList<>() ;
		for(Trip dataArr : tripRepository.findTripsByStops(sourceStop, destStop)){
			dataArrResult.add(new TripRequest(dataArr.getId(), dataArr.getFare(), dataArr.getJourneyTime(), dataArr.getAgency().getId(), dataArr.getBus().getId(), dataArr.getSourceStop().getId(), dataArr.getDestStop().getId()));
		}
        return ResponseEntity.ok(dataArrResult);
	}
    
    @GetMapping("/tripschedules")
    @PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTripSchedules(@RequestParam String date) {
    	List<GetTripScheduleRequest> dataArrResult = new ArrayList<>() ;
		for(TripSchedule dataArr : tripScheduleRepository.findTripScheduleByDate(date)){
			dataArrResult.add(new GetTripScheduleRequest(dataArr.getId(), dataArr.getAvailableSeats(), dataArr.getTripDate(), dataArr.getTripDetail().getId()));
		}
        return ResponseEntity.ok(dataArrResult);
	}
    
    
    @PostMapping("/bookticket")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> bookTicket(@RequestBody TicketRequest ticketRequest) {
        User user = userRepository.findById(ticketRequest.getPassegerId()).get();
        TripSchedule tripSchedule = tripScheduleRepository.findById(ticketRequest.getTripScheduleId()).get();
        Ticket ticket = new Ticket(ticketRequest.getSeatNumber(), ticketRequest.getCancellable(), ticketRequest.getJourneyDate(), user, tripSchedule);
        return ResponseEntity.ok(ticketRepository.save(ticket));
    }
    
}
