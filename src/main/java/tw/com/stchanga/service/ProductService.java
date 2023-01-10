package tw.com.stchanga.service;

import java.util.List;

import tw.com.stchanga.constant.ProductCategory;
import tw.com.stchanga.dto.ProductRequest;
import tw.com.stchanga.model.Product;

public interface ProductService {
	
	
	List<Product> getProducts(ProductCategory category,String search);
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId,ProductRequest productRequest);
	
	void deleteProduct(Integer productId);


}
