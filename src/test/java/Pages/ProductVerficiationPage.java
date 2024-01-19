package Pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Objects.ProductVerificationObject;
import Tests.Hooks;
import Tests.ProductVerificationStepDefinition;
import Utils.Basepage;
import Utils.TestContext;
import Utils.TestData;
import Utils.XMLReader;
import Utils.sql;

public class ProductVerficiationPage{

	public WebDriver driver;

	public TestData td;
	XMLReader xml;
	ProductVerificationObject po;
	sql sql;
	Basepage b;
	
	public ExtentTest test;

	public ProductVerficiationPage(WebDriver driver) throws IOException, JDOMException {
		this.driver = driver;
		this.td = new TestData();
		this.xml = new XMLReader();
		this.sql = new sql();
		//this.test = test;
		this.po = PageFactory.initElements(driver, ProductVerificationObject.class);
		this.b = PageFactory.initElements(driver, Basepage.class);
		
		//this.psd=new ProductStepDefinition(testcontext);
		//this.xml = psd.testcontext.objXMLReader;
		//objXMLReader.loadTestDataXML(System.getProperty("user.dir") + "\\src\\test\\resources\\TestData\\XMLData\\practise.xml");
	}
	
	public void loginApplication() throws Exception
	{
		b.loginApplication(driver, test);
	}
	
	public List getProducts() throws IOException
	{
		try {
			xml.loadXML("practise", "AddTheProduct");
			List ItemToAdd = new ArrayList();
			//String query = ProductStepDefinition.testcontext.objXMLReader.getText("Query");
			String query = xml.getText("Query");
			List<List<Object>> result = sql.connect_DB(query);			
			for(int i=0;i<result.size();i++)
			{
				ItemToAdd.add(result.get(i).get(1).toString());
			}
			System.out.println("ItemToAdd - "+ItemToAdd);
			
			test.pass("Item To add for checkout - " +ItemToAdd);
			return ItemToAdd;
		}catch(Exception e) {
			//String dest = captureScreenshot(driver,"Login screen");
			//test.fail("Items cannot be added due to " +e.getMessage(), MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			Assert.assertEquals(false, true);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap VerifyAddAndRemoveItems(List ItemToAdd) throws Exception {
		HashMap productList = new HashMap();
		for (int i = 0; i < ItemToAdd.size(); i++) {
			WebElement add = driver
					.findElement(By.xpath(po.productsToAdd.replace("dynamic", ItemToAdd.get(i).toString())));
			add.click();
			String price = driver.findElement(By.xpath(po.productsPrice.replace("dynamic", ItemToAdd.get(i).toString())))
					.getText();
			productList.put(ItemToAdd.get(i).toString(), price);
			WebElement remove = driver
					.findElement(By.xpath(po.productsToRemove.replace("dynamic", ItemToAdd.get(i).toString())));
			String GetTextValue = remove.getText();
			if (GetTextValue.equalsIgnoreCase("remove")) {
				test.pass(ItemToAdd.get(i).toString() + " Item added and value showing for the product now is REMOVE");
				System.out.println(" Item added and value showing for the product now is REMOVE");
			} else {
				System.out.println(" Item added and value showing for the product now is not REMOVE");
			}
		}
		return productList;
	}
	
	public void verifyproductpriceonCheckout(HashMap productList)
	{
		double pricee=0;
		po.cartButton.click();
		if(po.cartCount.getText().equals(""+productList.size()))
		{
			for(int i=0;i<productList.size();i++)
			{
				String y = Integer.toString(i+1);
				String productname = driver.findElement(By.xpath(po.cartProductsListValue.replace("dynamic", y))).getText();
				String price = productList.get(productname).toString();
				String productprice = driver.findElement(By.xpath(po.cartProductsPriceListValue.replace("dynamic", y))).getText();
				if(price.equals(productprice))
				{
					test.pass("products and price are matched");
					pricee = pricee+ Double.parseDouble((productprice).replace("$", ""));
				}else {
					test.fail("products and price are not matched");
				}
			}
		}
	}

}
