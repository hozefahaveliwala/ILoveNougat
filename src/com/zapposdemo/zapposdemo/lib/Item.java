package com.zapposdemo.zapposdemo.lib;

public class Item {

	private String brandName, productName, originalPrice, finalPrice,priceOff;
	private String thumbnail;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}

	public String getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	public String getPriceOff() {
		return priceOff;
	}

	public void setPriceOff(String priceOff) {
		this.priceOff = priceOff;
	}
}
