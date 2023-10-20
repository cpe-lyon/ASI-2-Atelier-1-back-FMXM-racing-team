package com.fxmxracingteam.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fxmxracingteam.cardservice.jpa.CardJPA;

@Repository
public interface CardRepository extends JpaRepository<CardJPA, Integer> {

}
