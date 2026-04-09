package com.opencart.qa.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencart.qa.utils.AppConstants;
import com.opencart.qa.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;
	private Map<String,String> productMap ;
	
	//-------Every page will have constructor
	
	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	private final By header = By.cssSelector("div#content h1");
	private final By images = By.cssSelector("ul.thumbnails img");
	private final By quantity = By.name("quantity");
	private final By addToCart = By.id("button-cart");
	private final By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private final By priceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	
	
	
	
	
	public String getProductHeader() {
		return eleUtil.doGetElementText(header);
	}
	
	public int getProductImagesCount() {
		int imagesCount =  eleUtil.waitForAllElementsVisible(images, AppConstants.MEDIUM_TIME_OUT).size();
		System.out.println("Total no of images for product: " + getProductHeader() + ":" + imagesCount);
		return imagesCount;
	
	}
	//Brand: Apple
	//Product Code: Product 18
	//Reward Points: 800
	//Availability: Out Of Stock
	//-=---------------- These are in the key value pair --------------->
	private void getProductMetaData() {
		List<WebElement> productMetaDataList =  eleUtil.getElements(productMetaData);
		
		for(WebElement e : productMetaDataList) {
			String producMetaText = e.getText();
			String[] productMeta =  producMetaText.split(":");
			String metaKey = productMeta[0].trim();
			String metaValue = productMeta[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	//$2,000.00  //0
	//Ex Tax: $2,000.00 //1---> 0:1
	//----------- here one key value pair is present-------->
	private void getProductPriceData() {
		List<WebElement> priceMetaDataList =  eleUtil.getElements(priceData);
		String productPrice = priceMetaDataList.get(0).getText().trim();
		String productExTaxPrice = 	priceMetaDataList.get(1).getText().split(":")[1].trim(); //only $2000.00
		productMap.put("productprice", productPrice); //productprice is our own key name added
		productMap.put("extaxprice", productExTaxPrice); 
	}
	
	public Map<String, String> getProductInfoData() {
		//productMap = new HashMap<String, String>();
		productMap = new LinkedHashMap<String, String>(); // maintains order
		//for sorting we can use TreeMap
		productMap.put("productname", getProductHeader()); 
		productMap.put("imagescount",String.valueOf(getProductImagesCount())); //converting to string 
		getProductMetaData();
		getProductPriceData();
		System.out.println("product information :\n" + productMap);
		return productMap;
	}
	
}
