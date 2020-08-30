package com.geekbrains.home.productsUtils;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "product", propOrder = {
        "id",
        "title",
        "price",
        "description"
})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @XmlElement(required = true)
    private String title;


    @Column(name = "price")
    @XmlElement(required = true)
    private BigDecimal price;

    @Column(name = "description")
    @XmlElement(required = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Category category;


    public Product(Long id, String title, BigDecimal price, Category category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
