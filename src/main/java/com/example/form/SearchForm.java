package com.example.form;

public class SearchForm {

	private String itemName;
	private String parentCategoryName;
	private String childParentCategoryName;
	private String grandChildCategoryName;
	private String brand;
	
	
	

	public Integer getIntParentCategoryName() {
		if(!(("").equals(parentCategoryName)) && parentCategoryName!=null) {
			return Integer.parseInt(parentCategoryName);
		}
		return null;
	}
	
	public Integer getIntChildParentCategoryName() {
		if(!(("").equals(childParentCategoryName)) && childParentCategoryName != null) {
			return Integer.parseInt(childParentCategoryName);
		}
		return null;
	}
	
	public Integer getIntGrandChildCategoryName() {
		if(!(("").equals(grandChildCategoryName)) && grandChildCategoryName != null) {
			System.out.println(grandChildCategoryName);
			return Integer.parseInt(grandChildCategoryName);
		}
		return null;
	}
	

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getParentCategoryName() {
		return parentCategoryName;
	}

	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}

	public String getChildParentCategoryName() {
		return childParentCategoryName;
	}

	public void setChildParentCategoryName(String childParentCategoryName) {
		this.childParentCategoryName = childParentCategoryName;
	}

	public String getGrandChildCategoryName() {
		return grandChildCategoryName;
	}

	public void setGrandChildCategoryName(String grandChildCategoryName) {
		this.grandChildCategoryName = grandChildCategoryName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "SearchForm [itemName=" + itemName + ", parentCategoryName=" + parentCategoryName
				+ ", childParentCategoryName=" + childParentCategoryName + ", grandChildCategoryName="
				+ grandChildCategoryName + ", brand=" + brand + "]";
	}

}
