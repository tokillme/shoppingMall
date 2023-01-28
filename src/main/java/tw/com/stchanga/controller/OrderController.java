package tw.com.stchanga.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.xdevapi.Schema.CreateCollectionOptions;

import tw.com.stchanga.dto.CreateOrderRequest;
import tw.com.stchanga.dto.OrderQueryParams;
import tw.com.stchanga.model.Order;
import tw.com.stchanga.service.OrderService;
import tw.com.stchanga.util.Page;

@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	
	@GetMapping("/users/{userId}/orders")
	public ResponseEntity<Page<Order>> getOrders(
			@PathVariable Integer userId,
			@RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
			@RequestParam(defaultValue = "0") @Min(0) Integer offset
			){
		OrderQueryParams orderQueryParams=new OrderQueryParams();
		orderQueryParams.setUserId(userId);
		orderQueryParams.setLimit(limit);
		orderQueryParams.setOffset(offset);
		
		//get order list
		List<Order> orderList=orderService.getOrders(orderQueryParams);
		
		//get order total
		Integer count=orderService.countOrder(orderQueryParams);
		
		//paging
		Page<Order> page=new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(count);
		page.setResults(orderList);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
	}
	

	@PostMapping("/users/{userId}/orders")
	public ResponseEntity<?> createOrder(@PathVariable Integer userId,
			                             @RequestBody @Valid CreateOrderRequest createOrderRequest){
		Integer orderId=orderService.createOrder(userId,createOrderRequest);
		
		Order order=orderService.getOrderById(orderId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(order);
	
	}
	
}