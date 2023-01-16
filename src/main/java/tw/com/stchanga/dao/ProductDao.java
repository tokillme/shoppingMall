package tw.com.stchanga.dao;

import java.util.List;

import tw.com.stchanga.constant.ProductCategory;
import tw.com.stchanga.dto.ProductQueryParams;
import tw.com.stchanga.dto.ProductRequest;
import tw.com.stchanga.model.Product;

public interface ProductDao {
	
	Integer countProduct(ProductQueryParams productQueryParams);
	
	List<Product> getProducts(ProductQueryParams productQueryParams);
	
	Product getProductById(Integer productId);
	
	Integer createProduct(ProductRequest productRequest);
	
	void updateProduct(Integer productId,ProductRequest productRequest);
	void deleteProduct(Integer productId);

}
