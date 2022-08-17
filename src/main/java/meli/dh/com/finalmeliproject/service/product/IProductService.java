package meli.dh.com.finalmeliproject.service.product;

import meli.dh.com.finalmeliproject.dto.ProductBatchDTO;
import meli.dh.com.finalmeliproject.dto.ProductDTO;
import meli.dh.com.finalmeliproject.dto.ProductFilterDTO;
import meli.dh.com.finalmeliproject.dto.ProductsBatchFilter;
import meli.dh.com.finalmeliproject.model.Product;
import meli.dh.com.finalmeliproject.model.WareHouseProduct;

import java.util.List;

public interface IProductService {
    Product save(Product product);
    List<Product> saveAll(List<Product> products);
    List<Product> findAllProducts();
    int checkStock(String id);
    List<Product> findProductsByCategory(String category);
    List<ProductBatchDTO> allProductsPerBatch(String id);
    List<Product> filterProductsByBatch (String productId, String order);
    WareHouseProduct findByProductId(String id);
    List<ProductsBatchFilter> findFilter(int amountDays, String categoryName);
    List<ProductFilterDTO> filterByCategoryAndOrderByPriceASC(String category);
    List<ProductFilterDTO> filterByCategoryAndOrderByPriceDESC(String category);
    List<ProductFilterDTO> findAllProductsAndOrderByPrice(String option);
    List<ProductFilterDTO> findAllProductsByPriceRange(double minPrice, double maxPrice);
    List<ProductFilterDTO> findAllProductsByCategoryWithPriceRange(String category, double minPrice, double maxPrice);
}
