package tw.com.stchanga.dao;

import tw.com.stchanga.model.Product;

public interface ProductDao {
	Product getProductById(Integer productId);
}