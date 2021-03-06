package com.cyberninja.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cyberninja.model.entity.enumerated.ProviderContract;

@Entity
@Table(name = "PROVIDERS")
public class Provider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7495685278131718878L;

	private Long id;

	private String name;

	private LocalDate creationDate;

	private ProviderContract contract;

	private Double profits;

	private List<Product> products;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PROVIDER_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CREATION_DATE")
	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	@Column(name = "CONTRACT", nullable = false)
	@Enumerated(EnumType.STRING)
	public ProviderContract getContract() {
		return contract;
	}

	public void setContract(ProviderContract contract) {
		this.contract = contract;
	}

	@Column(name = "PROFITS", nullable = false)
	public Double getProfits() {
		return profits;
	}

	public void setProfits(Double profits) {
		this.profits = profits;
	}

	@OneToMany(mappedBy = "provider", cascade = CascadeType.ALL)
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
