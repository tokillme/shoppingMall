package tw.com.stchanga.service;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import tw.com.stchanga.dao.OrderDao;
import tw.com.stchanga.dao.ProductDao;
import tw.com.stchanga.dao.UserDao;
import tw.com.stchanga.dto.BuyItem;
import tw.com.stchanga.dto.CreateOrderRequest;
import tw.com.stchanga.dto.OrderQueryParams;
import tw.com.stchanga.model.Order;
import tw.com.stchanga.model.OrderItem;
import tw.com.stchanga.model.Product;
import tw.com.stchanga.model.User;

@Component
public class OrderServiceImpl implements OrderService{
	
	private static final Logger log=LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private UserDao userDao;
	
	
	//Change more than two tables with @Transactional
	@Transactional
	@Override
	public Integer createOrder(Integer userId,CreateOrderRequest createOrderRequest) {
		
		//check user exist orNOT
		User user=userDao.getUserById(userId);
		if(user==null) {
			log.warn("該 userId{} 不存在",userId);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		
		
		int totalAmount=0;
		List<OrderItem> orderItemList=new ArrayList<>();
		
		for(BuyItem buyItem : createOrderRequest.getBuyItemList()) {
			Product product=productDao.getProductById(buyItem.getProductId());
			
			//check product exist orNot and stock
			if (product==null) {
				log.warn("商品 {} 不存在",buyItem.getProductId());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}else if (product.getStock()<buyItem.getQuantity()) {
				log.warn("商品 {} 庫存數量不足，無法購買，剩餘庫存 {} ，預購買數量 {}",product.getProductId(),product.getStock(),buyItem.getQuantity());
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			//check product in stock
			productDao.updateStock(product.getProductId(),product.getStock()-buyItem.getQuantity());
			
			
			
			//Calculate the price
			int amount=buyItem.getQuantity()*product.getPrice();
			totalAmount=totalAmount+amount;
			
			//convert BuyItem to OrderItem
			OrderItem orderItem=new OrderItem();
			orderItem.setProductId(buyItem.getProductId());
			orderItem.setQuantity(buyItem.getQuantity());
			orderItem.setAmount(amount);
			
			orderItemList.add(orderItem);
		}
		
		//create order
		Integer orderId=orderDao.createOrder(userId,totalAmount);
		
		orderDao.createOrderItems(orderId, orderItemList);
		return orderId;
		
	}


	@Override
	public Order getOrderById(Integer orderId) {
		Order order=orderDao.getOrderById(orderId);
		
		List<OrderItem> orderItemList=orderDao.getOrderItemsByOrderId(orderId);
		
		order.setOrderItemList(orderItemList);
		
		return order;
	}


	@Override
	public Integer countOrder(OrderQueryParams orderQueryParams) {
		
		return orderDao.countOrder(orderQueryParams);
	}


	@Override
	public List<Order> getOrders(OrderQueryParams orderQueryParams) {
		List<Order> orderList=orderDao.getOrders(orderQueryParams);
		
		for(Order order:orderList) {
			List<OrderItem> orderItemList= orderDao.getOrderItemsByOrderId(order.getOrderId());
			
			order.setOrderItemList(orderItemList);
			
		}
		
		
		return orderList;
	}
}
