package tw.com.stchanga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import tw.com.stchanga.constant.ProductCategory;
import tw.com.stchanga.dao.ProductDao;
import tw.com.stchanga.dto.ProductQueryParams;
import tw.com.stchanga.dto.ProductRequest;
import tw.com.stchanga.model.Product;

@Component
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	
	@Override
	public List<Product> getProducts(ProductQueryParams productQueryParams) {
		
		return productDao.getProducts(productQueryParams);
	}
	
	@Override
	public Product getProductById(Integer productId) {
		return productDao.getProductById(productId);
	}

	@Override
	public Integer createProduct(ProductRequest productRequest) {
		
		return productDao.createProduct(productRequest);
	}

	@Override
	public void updateProduct(Integer productId, ProductRequest productRequest) {
		
		productDao.updateProduct(productId,productRequest);
		
		
	}

	@Override
	public void deleteProduct(Integer productId) {
		// TODO Auto-generated method stub
		productDao.deleteProduct(productId);
	}

	@Override
	public Integer countProduct(ProductQueryParams productQueryParams) {

		return productDao.countProduct(productQueryParams);
	}

	

}
