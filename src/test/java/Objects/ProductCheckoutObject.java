package Objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductCheckoutObject {
	
	@FindBy(xpath ="//*[@class='inventory_item_desc']")
	public List<WebElement> TotalNoOfItems;
	
	public static final String ItemName = "(//*[@class='inventory_item_desc']/preceding::a[1]/div)[dynamic]";

}
