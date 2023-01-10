package tw.com.stchanga.dao;

import tw.com.stchanga.dto.ProductRequest;
import tw.com.stchanga.model.Product;

public interface ProductDao {
	Product getProductById(Integer productId);
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId,ProductRequest productRequest);
}
