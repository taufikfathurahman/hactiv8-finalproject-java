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
import com.example.finalproject.models.User;
import com.example.finalproject.payload.request.AgencyRequest;
import com.example.finalproject.repository.AgencyRepository;
import com.example.finalproject.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {
	@Autowired
	AgencyRepository agencyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll(){
		List<AgencyRequest> dataArrResult = new ArrayList<>() ;
		for(Agency dataArr : agencyRepository.findAll()){
			dataArrResult.add(new AgencyRequest(dataArr.getId(), dataArr.getCode(), dataArr.getName(), dataArr.getDetails(), dataArr.getOwner().getId()));
		}
        return ResponseEntity.ok(dataArrResult);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAgencyById(@PathVariable(value="id") Long id){
        Agency agency = agencyRepository.findById(id).get();
		if(agency == null) {
			return ResponseEntity.notFound().build();
		} else {
			AgencyRequest dataResult = new AgencyRequest(agency.getId(), agency.getCode(), agency.getName(), agency.getDetails(), agency.getOwner().getId());
            return ResponseEntity.ok(dataResult);
		}
	}
	
	@PostMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAgency(@Valid @RequestBody AgencyRequest agencyRequest) {
		User user = userRepository.findById(agencyRequest.getOwner()).get();
		Agency agency = new Agency(agencyRequest.getCode(), agencyRequest.getDetails(), agencyRequest.getName(), user);
        return ResponseEntity.ok(agencyRepository.save(agency));
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateAgency(@PathVariable(value="id") Long id, @Valid @RequestBody AgencyRequest agencyDetail){
		Agency agency = agencyRepository.findById(id).get();
		User user = userRepository.findById(agencyDetail.getOwner()).get();
		if(agency == null) {
			return ResponseEntity.notFound().build();
		}
        agency.setCode(agencyDetail.getCode());
        agency.setDetails(agencyDetail.getDetails());
        agency.setName(agencyDetail.getName());
        agency.setOwner(user);

        Agency updatedAgency = agencyRepository.save(agency);

        return ResponseEntity.ok(updatedAgency);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteAgency(@PathVariable(value="id") Long id) {
		String result = "";
		try {
            agencyRepository.findById(id).get();
			
			result = "Success delete data with id : " + id;
            agencyRepository.deleteById(id);

            return ResponseEntity.ok(result);
		} catch(Exception e) {
			result = "data with id: " + id + " not found!";
            return ResponseEntity.ok(result);
		}
	}
}
