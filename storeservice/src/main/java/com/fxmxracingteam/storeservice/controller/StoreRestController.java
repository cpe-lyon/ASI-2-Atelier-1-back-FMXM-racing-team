package com.fxmxracingteam.storeservice.controller;

import java.util.List;

import com.fxmxracingteam.storelib.dto.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fxmxracingteam.storelib.dto.StoreOrderDTO;
import com.fxmxracingteam.storeservice.jpa.StoreTransactionJPA;
import com.fxmxracingteam.storeservice.service.StoreService;

@CrossOrigin
@RestController("/store")
public class StoreRestController {

	private final StoreService storeService;

	public StoreRestController(StoreService storeService) {
		this.storeService = storeService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/buy")
	private boolean buyCard(@RequestBody StoreOrderDTO order) {
		return storeService.buyCard(order.getUser_id(), order.getCard_id());

	}

	@RequestMapping(method = RequestMethod.POST, value = "/sell")
	private boolean sellCard(@RequestBody StoreOrderDTO order) {
		return storeService.sellCard(order.getUser_id(), order.getCard_id());
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction/{storeId}/operation")
	private void updateTransactionOperation(@PathVariable Integer storeId, @RequestParam OperationType operationType, @RequestParam Boolean isSuccessful) {
		storeService.updateTransactionOperation(storeId, operationType, isSuccessful);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/transaction")
	private List<StoreTransactionJPA> getTransactions() {
		return storeService.getAllTransactions();
	}

	
}
