package tw.com.stchanga.service;

import java.util.List;

import tw.com.stchanga.dto.CreateOrderRequest;
import tw.com.stchanga.dto.OrderQueryParams;
import tw.com.stchanga.model.Order;

public interface OrderService {

	Integer countOrder(OrderQueryParams orderQueryParams);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	
	Order getOrderById(Integer orderId);
	
	Integer createOrder(Integer userId,CreateOrderRequest createOrderRequest);


}
