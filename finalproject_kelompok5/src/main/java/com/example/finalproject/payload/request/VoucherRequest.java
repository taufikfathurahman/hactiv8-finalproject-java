package com.example.finalproject.payload.request;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

public class VoucherRequest {
	@ApiModelProperty(hidden = true)
	
	private Long id;
	
	private String voucherName;
	
	private String voucherAmount;
	
	private Long owner;

	public VoucherRequest() {
	}

	public VoucherRequest(Long id, @NotBlank String voucherName, @NotBlank String voucherAmount, @NotBlank Long owner) {
		this.id = id;
		this.voucherName = voucherName;
		this.voucherAmount = voucherAmount;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public String getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(String voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}
	
}
