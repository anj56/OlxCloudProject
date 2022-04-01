package com.zensar.olx.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.olx.bean.AdvertisemantStatus;
import com.zensar.olx.service.AdvertisementStatusService;

@RestController
public class AdvertisementStatusController {
	
	@Autowired
	AdvertisementStatusService service;
	
	
	@PostMapping("/advertise/addStatus")
	public AdvertisemantStatus addAdvertisemantStatus(@RequestBody     AdvertisemantStatus advertisemantStatus) {
		return this.service.addAdvertisementStatus(advertisemantStatus);
	}
	
	@GetMapping("advertise/getAllStatus")
   public List<AdvertisemantStatus>getAllAdvertisemantStatus(){
	   return this.service.getAllSatus();
   }
	
	@GetMapping("advertise/status/{id}")
	public AdvertisemantStatus findAdvertisementStatusById(@PathVariable(name="id") int id) {
		return this.service.findAdvertisementStatus(id);
	}
}
