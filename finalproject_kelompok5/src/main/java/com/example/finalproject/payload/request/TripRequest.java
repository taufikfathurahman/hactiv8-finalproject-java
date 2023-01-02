package com.example.finalproject.payload.request;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class TripRequest {
	@ApiModelProperty(hidden = true)
	private Long id;
	
	private int fare;
	
	private int journeyTime;
	
	private Long sourceStopId;
	
	private Long destStopid;
	
	private Long busId;
	
	private Long agencyid;
	
	public TripRequest() {
	}

	public TripRequest(Long id, @NotNull int fare, @NotBlank int journeyTime, @NotBlank Long agencyid,
			@NotBlank Long busId, @NotBlank Long sourceStopId, @NotBlank Long destStopid) {
		this.id = id;
		this.fare = fare;
		this.journeyTime = journeyTime;
		this.agencyid = agencyid;
		this.busId = busId;
		this.sourceStopId = sourceStopId;
		this.destStopid = destStopid;
	}

	public int getFare() {
		return fare;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public int getJourneyTime() {
		return journeyTime;
	}

	public void setJourneyTime(int journeyTime) {
		this.journeyTime = journeyTime;
	}

	public Long getSourceStopId() {
		return sourceStopId;
	}

	public void setSourceStopId(Long sourceStopId) {
		this.sourceStopId = sourceStopId;
	}

	public Long getDestStopid() {
		return destStopid;
	}

	public void setDestStopid(Long destStopid) {
		this.destStopid = destStopid;
	}

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public Long getAgencyid() {
		return agencyid;
	}

	public void setAgencyid(Long agencyid) {
		this.agencyid = agencyid;
	}
}
