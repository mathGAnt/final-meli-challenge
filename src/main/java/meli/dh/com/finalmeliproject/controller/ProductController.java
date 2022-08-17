package meli.dh.com.finalmeliproject.controller;

import com.sun.istack.Nullable;
import meli.dh.com.finalmeliproject.dto.ProductBatchDTO;

import meli.dh.com.finalmeliproject.dto.ProductDTO;
import meli.dh.com.finalmeliproject.dto.ProductFilterDTO;
import meli.dh.com.finalmeliproject.dto.ProductsBatchFilter;
import meli.dh.com.finalmeliproject.dto.shoppingCart.RequestShoppingCartDto;
import meli.dh.com.finalmeliproject.dto.shoppingCart.ResponseShoppingCartDto;
import meli.dh.com.finalmeliproject.model.Product;
import meli.dh.com.finalmeliproject.model.ShoppingCart;

import meli.dh.com.finalmeliproject.model.*;

import meli.dh.com.finalmeliproject.service.batch.IBatchService;
import meli.dh.com.finalmeliproject.service.product.IProductService;
import meli.dh.com.finalmeliproject.service.product.ProductService;
import meli.dh.com.finalmeliproject.service.shoppingCart.IShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products")
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IShoppingCartService iShoppingCartService;

    @Autowired
    private ProductService service;

    @Autowired
    private IBatchService batchService;

    /***
     * Retorna todos os produtos
     * @return todos os produtos disponíveis
     */
    @GetMapping
    public ResponseEntity<List<Product>> findProductsList(){
        return new ResponseEntity<>(productService.findAllProducts(),
                HttpStatus.OK);
    }

    @GetMapping("/check-stock/{id}")
    public ResponseEntity<Integer> checkStock(@PathVariable String id){
        return new ResponseEntity<>(productService.checkStock(id),
                HttpStatus.OK);
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<Product>> listingProductsByCategory (@RequestParam @Nullable String category){
        List<Product> productsByCategory = productService.findProductsByCategory(category);
        return ResponseEntity.ok().body(productsByCategory);
    }


    @GetMapping("/list/{id}")
    public ResponseEntity<List<ProductBatchDTO>> allProductsPerBatch(@PathVariable String id){
        return ResponseEntity.ok().body(service.allProductsPerBatch(id));
    }

    @GetMapping("/list/filter")
    public ResponseEntity<List<Product>> filterProductsPerBatch(@RequestParam String productId, @RequestParam String order){
        return ResponseEntity.ok().body(service.filterProductsByBatch(productId, order));
    }

    @PostMapping("/orders")
    public ResponseEntity<ResponseShoppingCartDto> shoppingCart(@RequestBody RequestShoppingCartDto request){
        return new ResponseEntity<>(iShoppingCartService.shoppingCart(request.getPurchaseOrder()),
                HttpStatus.CREATED);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ShoppingCart> findAllShoppingCartProducts(@PathVariable long id) {
        return ResponseEntity.ok().body(iShoppingCartService.findShoppingCartProductsById(id));
    }

    @PutMapping("/orders")
    public ResponseEntity<PurchaseOrder> editShoppingCart(@RequestParam long orderId) {
        return new ResponseEntity<>(iShoppingCartService.editShoppingCart(orderId),
                HttpStatus.CREATED);
    }

    @GetMapping("/due-date")
    public ResponseEntity<List<ProductsBatchFilter>> findByDueDate(@RequestParam @Nullable int amountDay, String categoryName) {
        return new ResponseEntity<>(productService.findFilter(amountDay, categoryName), HttpStatus.OK);
    }

    @GetMapping("/due-date/all")
    public ResponseEntity<List<Batch>> findByBatchsDueDate(@RequestParam @Nullable int amountDay, String categoryName) {
        return new ResponseEntity<>(batchService.findByDueDate(categoryName, amountDay), HttpStatus.OK);
    }

    @GetMapping("/by-category/order-by")
    public ResponseEntity<List<ProductFilterDTO>> listingProductsByCategoryOrderingByPrice (@RequestParam String category, String order){
        List<ProductFilterDTO> productList;
        switch(order){
            case "asc":
                productList = productService.filterByCategoryAndOrderByPriceASC(category);
                break;
            case "desc":
                productList = productService.filterByCategoryAndOrderByPriceDESC(category);
                break;
            default:
                productList = productService.findProductsByCategory(category).stream().map(ProductFilterDTO::new).collect(Collectors.toList());
        }

        return ResponseEntity.ok().body(productList);
    }

    @GetMapping("/by-category/price-range")
    public ResponseEntity<List<ProductFilterDTO>> listingProductsByCategoryWithPriceRange (@RequestParam String category, double minPrice, double maxPrice){
        return ResponseEntity.ok().body(productService.findAllProductsByCategoryWithPriceRange(category, minPrice, maxPrice));
    }

    @GetMapping("/order-by")
    public ResponseEntity<List<ProductFilterDTO>> findProductsListAndOrderByPrice(@RequestParam String option){
        return ResponseEntity.ok().body(productService.findAllProductsAndOrderByPrice(option));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductFilterDTO>> listingAllProductsWithPriceRange (@RequestParam double minPrice, double maxPrice){
        return ResponseEntity.ok().body(productService.findAllProductsByPriceRange(minPrice, maxPrice));
    }


}
