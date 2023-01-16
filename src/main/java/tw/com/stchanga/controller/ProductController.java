package tw.com.stchanga.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.stchanga.constant.ProductCategory;
import tw.com.stchanga.dto.ProductQueryParams;
import tw.com.stchanga.dto.ProductRequest;
import tw.com.stchanga.model.Product;
import tw.com.stchanga.service.ProductService;
import tw.com.stchanga.util.Page;

@Validated
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getProduct(
				//search Filtering
				@RequestParam(required = false) ProductCategory category,
				@RequestParam(required = false) String search,
				
				//Sorting
				@RequestParam(defaultValue = "created_date") String orderBy,
				@RequestParam(defaultValue = "desc") String sort,
				
				//HTML pagination
				@RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,
				@RequestParam(defaultValue = "0") @Min(0) Integer offset
				
			){
		ProductQueryParams productQueryParams=new ProductQueryParams();
		productQueryParams.setCategory(category);
		productQueryParams.setSearch(search);
		productQueryParams.setOrderBy(orderBy);
		productQueryParams.setSort(sort);
		productQueryParams.setLimit(limit);
		productQueryParams.setOffset(offset);
		
		// product list
		List<Product> productList = productService.getProducts(productQueryParams);
		
		// product total
		Integer total=productService.countProduct(productQueryParams);
		
		// paging
		Page<Product> page=new Page<>();
		page.setLimit(limit);
		page.setOffset(offset);
		page.setTotal(total);
		page.setResults(productList);
		
		return ResponseEntity.status(HttpStatus.OK).body(page);
		
	}
	
	
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
		Product product=productService.getProductById(productId);
		
		if (product != null) {
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
		Integer productId=productService.createProduct(productRequest);
		Product product=productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	//productRequest 剛好是前端云許修改，能沿用 商品名稱、價格...
	@PutMapping("/products/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
												 @RequestBody @Valid ProductRequest productRequest){
		
		//檢查 product 是否存在
		Product product=productService.getProductById(productId);
		
		if(product == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		//修改商品數據
		productService.updateProduct(productId,productRequest);
		
		Product updatedProduct=productService.getProductById(productId);
		
		return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
		
		
	}
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
		
		productService.deleteProduct(productId);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}


}
