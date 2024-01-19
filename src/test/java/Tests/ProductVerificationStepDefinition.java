package Tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Pages.ProductVerficiationPage;
import Utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ProductVerificationStepDefinition {
	
	WebDriver driver;
	List ItemToAdd = new ArrayList();
	HashMap productList = new HashMap();
	public TestContext testcontext;
	
	public ProductVerificationStepDefinition(TestContext testcontext) throws JDOMException, IOException
	{
		this.testcontext = testcontext;
	}
	
	@Given("Log in to application with username and password")
	public void log_in_to_application_with_username_and_password() throws Exception {
		System.out.println(testcontext.objXMLReader.getText("TotalItems"));
		System.out.println(testcontext.sql.connect_DB(testcontext.objXMLReader.getText("Query")));
		testcontext.pp.loginApplication();
	}
	
	@Given("Get the list of items to be added for checkout from Database")
	public void get_the_list_of_items_to_be_added_for_checkout_from_database() throws IOException {
		ItemToAdd = testcontext.pp.getProducts();
	}
	
	@Given("Add the products to cart and verify the text shown as remove")
	public void add_the_products_to_cart_and_verify_the_text_shown_as_remove() throws Exception {
		productList = testcontext.pp.VerifyAddAndRemoveItems(ItemToAdd);
	}
	
	@Then("GoTo cart and verify product and price are matched")
	public void go_to_cart_and_verify_product_and_price_are_matched() throws Exception {
		testcontext.pp.verifyproductpriceonCheckout(productList);
	}
	
}
