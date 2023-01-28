package tw.com.stchanga.dao;

import java.util.List;

import tw.com.stchanga.dto.OrderQueryParams;
import tw.com.stchanga.model.Order;
import tw.com.stchanga.model.OrderItem;

public interface OrderDao {
	
	
	Integer countOrder(OrderQueryParams orderQueryParams);
	
	List<Order> getOrders(OrderQueryParams orderQueryParams);
	
	
	Order getOrderById(Integer orderId);
	
	List<OrderItem> getOrderItemsByOrderId(Integer orderId);
	
	Integer createOrder(Integer userId,Integer totalAmount);

	void createOrderItems(Integer orderId,List<OrderItem> orderItemList);
}
