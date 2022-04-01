package com.zensar.olx.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zensar.olx.bean.AdvertisemantStatus;
import com.zensar.olx.db.AdvertisementStatusDAO;

@Service
public class AdvertisementStatusService {

	@Autowired
	AdvertisementStatusDAO dao;

	public AdvertisemantStatus addAdvertisementStatus(AdvertisemantStatus advertisemantStatus) {

		return this.dao.save(advertisemantStatus);
	}

	public List<AdvertisemantStatus> getAllSatus() {
		return this.dao.findAll();
	}

	public AdvertisemantStatus findAdvertisementStatus(int id) {
		Optional<AdvertisemantStatus> optional;
		optional = this.dao.findById(id);
		if (optional.isPresent())
			return optional.get();
		else
			return null;
	}

}
