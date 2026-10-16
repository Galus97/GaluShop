package pl.galushop.GaluShop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE product_id = ?")
@SQLRestriction("is_deleted = false")
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Size(min = 3)
    private String productName;

    @Size(min = 20)
    private String description;

    @Min(1)
    private Double price;

    @NotBlank
    private String category;

    @Min(1)
    private Integer categoryId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}