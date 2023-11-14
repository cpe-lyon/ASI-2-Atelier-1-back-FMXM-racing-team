package com.fxmxracingteam.storeservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fxmxracingteam.storelib.dto.StoreOrderDTO;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;
import com.fxmxracingteam.storeservice.service.StoreService;

@CrossOrigin
@RestController
public class StoreRestController {

	@Autowired
	private StoreService storeService;

	@RequestMapping(method = RequestMethod.POST, value = "/store/buy")
	private boolean buyCard(@RequestBody StoreOrderDTO order) {
		return storeService.buyCard(order.getUser_id(), order.getCard_id());

	}

	@RequestMapping(method = RequestMethod.POST, value = "/store/sell")
	private boolean sellCard(@RequestBody StoreOrderDTO order) {
		return storeService.sellCard(order.getUser_id(), order.getCard_id());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/store/transaction")
	private List<StoreTransactionJPA> getTransactions() {
		return storeService.getAllTransactions();
	}

	
}
