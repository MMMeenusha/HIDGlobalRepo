package Pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Objects.ProductCheckoutObject;
import Objects.ProductVerificationObject;
import Utils.Basepage;
import Utils.TestData;
import Utils.XMLReader;
import Utils.sql;

public class ProductCheckoutPage {

	public WebDriver driver;

	public TestData td;
	XMLReader xml;
	ProductCheckoutObject po;
	sql sql;
	
	public ExtentTest test;

	public ProductCheckoutPage(WebDriver driver) throws IOException, JDOMException {
		this.driver = driver;
		this.td = new TestData();
		this.xml = new XMLReader();
		this.sql = new sql();
		//this.test = test;
		this.po = PageFactory.initElements(driver, ProductCheckoutObject.class);
	}
	
	public int getTotalItemList()
	{
		int size = po.TotalNoOfItems.size();
		test.pass("Item List size are - "+size);
		return size;
	}
	
	public void getItemNames()
	{
		int size = getTotalItemList();
		List Names = new ArrayList();
		for(int i=1;i<=size;i++)
		{
			String itemName = driver.findElement(By.xpath(po.ItemName.replace("dynamic", ""+i))).getText();
			Names.add(itemName);
		}
		test.pass("Item Names are - "+Names);
		//write(test);
	}
	
}
