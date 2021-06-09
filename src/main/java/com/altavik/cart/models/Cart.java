package com.altavik.cart.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "Cart" database table.
 * 
 */
@Entity
@Table(name="\"Cart\"")
@NamedQuery(name="cart.findAll", query="SELECT c FROM Cart c")
@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({ "id", "countryCode", "currency", "products", "created", "updated" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="cartId")
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQ")
	@SequenceGenerator(sequenceName = "CART_SEQ", allocationSize = 1, name = "CART_SEQ")
	private Integer cartId;

	@NotEmpty(message = "Country code cannot be empty")
	@ApiModelProperty(notes = "countryCode", example = "US", position = 2)
	@Column(name="country_code")
	private String countryCode;

	@NotEmpty(message = "Currency cannot be empty")
	@ApiModelProperty(notes = "currency", example = "USD", position = 3)
	@Column(name="currency")
	private String currency;

	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Product> products;

	@JsonSetter("created")
	private Date create_ts;

	@JsonSetter("updated")
	private Date update_ts;


	@Override
	public String toString() {
		return "Cart{" +
				"id=" + cartId +
				", countryCode='" + countryCode + '\'' +
				", currency='" + currency + '\'' +
				", products=" + products +
				", created=" + create_ts +
				", updated=" + update_ts +
				'}';
	}
}