package com.fxmxracingteam.cardservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fxmxracingteam.cardservice.jpa.CardReferenceJPA;

@Repository
public interface CardReferenceRepository extends JpaRepository<CardReferenceJPA, Integer>{

}
