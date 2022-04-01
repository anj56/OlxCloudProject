package com.zensar.olx.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zensar.olx.bean.AdvertisemantStatus;

@Repository
public interface AdvertisementStatusDAO  extends JpaRepository<AdvertisemantStatus, Integer>{

}
