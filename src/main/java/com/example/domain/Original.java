package com.example.domain;

public class Original {

	private Integer id;
	private String name;
	private String categoryName;
	private String brand;
	private Integer price;
	private String shopping;
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getShopping() {
		return shopping;
	}

	public void setShopping(String shopping) {
		this.shopping = shopping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Original [id=" + id + ", name=" + name + ", categoryName=" + categoryName + ", brand=" + brand
				+ ", price=" + price + ", shopping=" + shopping + ", description=" + description + "]";
	}

}
