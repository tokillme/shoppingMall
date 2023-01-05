package tw.com.stchanga.service;

import tw.com.stchanga.dto.ProductRequest;
import tw.com.stchanga.model.Product;

public interface ProductService {
	Product getProductById(Integer productId);
	Integer createProduct(ProductRequest productRequest);
}
