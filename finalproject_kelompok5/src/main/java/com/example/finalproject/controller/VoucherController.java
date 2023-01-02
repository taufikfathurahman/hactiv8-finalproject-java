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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.finalproject.models.Voucher;
import com.example.finalproject.models.User;
import com.example.finalproject.payload.request.VoucherRequest;
import com.example.finalproject.repository.UserRepository;
import com.example.finalproject.repository.VoucherRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {
	@Autowired
	VoucherRepository voucherRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<VoucherRequest> dataArrResult = new ArrayList<>() ;
		for(Voucher dataArr : voucherRepository.findAll()) {
			dataArrResult.add(new VoucherRequest(dataArr.getId(), dataArr.getVoucherName(), dataArr.getVoucherAmount(), dataArr.getOwner().getId()));
		}
		return ResponseEntity.ok(dataArrResult);
	}
	
	@PostMapping("/")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addVoucher(@Valid @RequestBody VoucherRequest voucherRequest) {
		User user = userRepository.findById(voucherRequest.getOwner()).get();
		Voucher voucher = new Voucher(voucherRequest.getVoucherName(), voucherRequest.getVoucherAmount(), user);
        return ResponseEntity.ok(voucherRepository.save(voucher));
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getVoucherById(@PathVariable(value="id") Long id){
        Voucher voucher = voucherRepository.findById(id).get();
		if(voucher == null) {
			return ResponseEntity.notFound().build();
		} else {
			VoucherRequest dataResult = new VoucherRequest(voucher.getId(), 
					voucher.getVoucherName(), 
					voucher.getVoucherAmount(),
					voucher.getOwner().getId());
			
            return ResponseEntity.ok(dataResult);
		}
	}
	
	@GetMapping("/owner")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getVoucherByOwner (@RequestParam(value="owner_user_id") Long owner_user_id){
		List<VoucherRequest> dataArrResult = new ArrayList<>() ;
		for(Voucher dataArr : voucherRepository.findByOwner(owner_user_id)) {
			dataArrResult.add(new VoucherRequest(dataArr.getId(), dataArr.getVoucherName(), dataArr.getVoucherAmount(), dataArr.getOwner().getId()));
		}
		return ResponseEntity.ok(dataArrResult);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateVoucher(@PathVariable(value="id") Long id, @Valid @RequestBody VoucherRequest voucherRequest){
		Voucher voucher = voucherRepository.findById(id).get();
		User user = userRepository.findById(voucherRequest.getOwner()).get();
		if(voucher == null) {
			return ResponseEntity.notFound().build();
		}
		voucher.setVoucherName(voucherRequest.getVoucherName());
		voucher.setVoucherAmount(voucherRequest.getVoucherAmount());
		voucher.setOwner(user);

        Voucher updatedVoucher = voucherRepository.save(voucher);

        return ResponseEntity.ok(updatedVoucher);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteVoucher(@PathVariable(value="id") Long id) {
		String result = "";
		try {
            voucherRepository.findById(id).get();
			
			result = "Success delete data with id : " + id;
            voucherRepository.deleteById(id);

            return ResponseEntity.ok(result);
		} catch(Exception e) {
			result = "data with id: " + id + " not found!";
            return ResponseEntity.ok(result);
		}
	}
}
