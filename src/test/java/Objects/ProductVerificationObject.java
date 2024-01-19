package Objects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductVerificationObject {
	
	@FindBy(xpath ="//*[@id='shopping_cart_container']")
	public WebElement cartButton;
	
	public static final String productsToAdd = "(//*[text()='dynamic']/following::button[text()='Add to cart'])[1]";
	
	public static final String productsPrice = "(//*[text()='dynamic']/following::div[3])[1]";
	
	public static final String productsToRemove = "(//*[text()='dynamic']/following::button)[1]";
	
	public static final String cartProductsListValue = "(//*[@class='inventory_item_name'])[dynamic]";
	
	public static final String cartProductsPriceListValue = "(//*[@class='item_pricebar']/div[1])[dynamic]";
	
	@FindBy(xpath ="(//*[@class='shopping_cart_badge'])")
	public WebElement cartCount;
	
	@FindBy(xpath ="//*[@id='checkout']")
	public WebElement checkout;

	@FindBy(xpath ="//*[@id='first-name']")
	public WebElement firstname;
	
	@FindBy(xpath ="//*[@id='last-name']")
	public WebElement lastname;
	
	@FindBy(xpath ="//*[@id='postal-code']")
	public WebElement zipcode;
	
	@FindBy(xpath ="//*[@id='continue']")
	public WebElement continueCheckout;
	
	@FindBy(xpath ="//*[@class='summary_subtotal_label']")
	public WebElement totalItemPrice;
	
	@FindBy(xpath ="//*[@class='summary_tax_label']")
	public WebElement totalTaxPrice;
	
	@FindBy(xpath ="//*[@class='summary_info_label summary_total_label']")
	public WebElement totalPrice;

}
