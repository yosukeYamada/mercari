package com.example.domain;

public class Category {

	private Integer id;
	private Integer parent;
	private String name;
	private String name_all;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_all() {
		return name_all;
	}

	public void setName_all(String name_all) {
		this.name_all = name_all;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", parent=" + parent + ", name=" + name + ", name_all=" + name_all + "]";
	}

}
