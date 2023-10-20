package com.fxmxracingteam.storeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;

@Repository
public interface StoreTransactionRepository extends JpaRepository<StoreTransactionJPA, Integer> {

}
