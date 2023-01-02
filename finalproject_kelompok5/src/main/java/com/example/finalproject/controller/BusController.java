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
import com.example.finalproject.payload.request.BusRequest;
import com.example.finalproject.repository.AgencyRepository;
import com.example.finalproject.repository.BusRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bus")
public class BusController {
	@Autowired
	BusRepository busRepository;

	@Autowired
	AgencyRepository agencyRepository;
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll(){
		List<BusRequest> dataArrResult = new ArrayList<>() ;
		for(Bus dataArr : busRepository.findAll()){
			dataArrResult.add(new BusRequest(dataArr.getId(), dataArr.getCapacity(), dataArr.getCode(), dataArr.getMake(), dataArr.getAgency().getId()));
		}
        return ResponseEntity.ok(dataArrResult);
	}
	
	@PostMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addBus(@Valid @RequestBody BusRequest busRequest) {
		Agency agency = agencyRepository.findById(busRequest.getAgencyId()).get();
		Bus bus = new Bus(busRequest.getCode(), busRequest.getCapacity(), busRequest.getMake(), agency);
        return ResponseEntity.ok(busRepository.save(bus));
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateBus(@PathVariable(value="id") Long id, @Valid @RequestBody BusRequest busDetail){
		Agency agency = agencyRepository.findById(busDetail.getAgencyId()).get();
		Bus bus = busRepository.findById(id).get();
		if(bus == null) {
			return ResponseEntity.notFound().build();
		}
        bus.setCapacity(busDetail.getCapacity());
        bus.setCode(busDetail.getCode());
        bus.setMake(busDetail.getMake());
        bus.setAgency(agency);

        Bus updatedBus = busRepository.save(bus);

        return ResponseEntity.ok(updatedBus);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteBus(@PathVariable(value="id") Long id) {
		String result = "";
		try {
            busRepository.findById(id).get();
			
			result = "Success delete data with id: " + id;
            busRepository.deleteById(id);

            return ResponseEntity.ok(result);
		} catch(Exception e) {
			result = "Data with id: " + id + " Not Found";
            return ResponseEntity.ok(result);
		}
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getBusById(@PathVariable(value="id") Long id){
        Bus bus = busRepository.findById(id).get();
		if(bus == null) {
			return ResponseEntity.notFound().build();
		} else {
			BusRequest dataResult = new BusRequest(bus.getId(), bus.getCapacity(), bus.getCode(), bus.getMake(), bus.getAgency().getId());
            return ResponseEntity.ok(dataResult);
		}
	}
}
