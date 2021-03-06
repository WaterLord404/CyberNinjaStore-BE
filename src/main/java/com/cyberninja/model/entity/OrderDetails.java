package com.cyberninja.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cyberninja.model.entity.enumerated.ProductColour;
import com.cyberninja.model.entity.enumerated.ProductSize;

@Entity
@Table(name = "ORDERS_DETAILS")
public class OrderDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7554941645655995147L;

	private Long id;

	private Integer units;

	private ProductColour colour;

	private ProductSize size;

	private Product product;

	private Order order;

	private boolean returned;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_DETAILS_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "UNITS", nullable = false)
	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	@Column(name = "COLOR", nullable = false)
	@Enumerated(EnumType.STRING)
	public ProductColour getColour() {
		return colour;
	}

	public void setColour(ProductColour colour) {
		this.colour = colour;
	}

	@Column(name = "SIZE", nullable = false)
	@Enumerated(EnumType.STRING)
	public ProductSize getSize() {
		return size;
	}

	public void setSize(ProductSize size) {
		this.size = size;
	}

	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID", foreignKey = @ForeignKey(name = "FK_ORDERS_DETAILS__PRODUCT_ID"))
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@ManyToOne
	@JoinColumn(name = "ORDER_ID", foreignKey = @ForeignKey(name = "FK_ORDERS_DETAILS__ORDER_ID"))
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Column(name = "RETURNED", nullable = false)
	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
