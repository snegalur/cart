package com.altavik.cart.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="\"Product\"")
@NamedQuery(name="product.findAll", query="SELECT p FROM Product p")
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({ "id", "description", "category", "price", "created", "updated" })
@JsonIgnoreProperties({"cart"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(notes = "id", example = "1", position = 1)
	@Column(name="id")
	private UUID id;

	@ManyToOne
	@JoinColumn(name="cart_id")
	private Cart cart;

	@NotEmpty(message = "Product description cannot be empty")
	@ApiModelProperty(notes = "description", example = "Apple iPhone 12", position = 2)
	@Column(name="description")
	private String description;

	@NotEmpty(message = "Product category cannot be empty")
	@ApiModelProperty(notes = "category", example = "ELECTRONICS", position = 3)
	@Column(name="category")
	private String category;

	@NotNull(message = "Price cannot be empty")
	@ApiModelProperty(notes = "price", example = "7325.05", position = 4)
	@Column(name="price")
	private BigDecimal price;

	@ApiModelProperty(notes = "created", example = "2021-05-07T20:30:00", position = 5)
	@Column(name="create_ts")
	private Date created;

	@ApiModelProperty(notes = "productId", example = "2021-05-07T20:30:00", position = 6)
	@Column(name="update_ts")
	private Date updated;

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", category='" + category + '\'' +
				", created=" + created +
				", description='" + description + '\'' +
				", price=" + price +
				", updated=" + updated +
				'}';
	}
}