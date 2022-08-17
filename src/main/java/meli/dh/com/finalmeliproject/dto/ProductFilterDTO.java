package meli.dh.com.finalmeliproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import meli.dh.com.finalmeliproject.model.Product;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilterDTO {
    private String name;
    private String categoryName;
    private double price;


    public ProductFilterDTO(Product p) {
        this.name = p.getName();
        this.price = p.getPrice();
        this.categoryName = p.getCategory().getCategoryName();
    }




}
