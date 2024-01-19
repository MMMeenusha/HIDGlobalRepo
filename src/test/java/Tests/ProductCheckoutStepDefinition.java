package Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;

import Utils.TestContext;
import io.cucumber.java.en.Then;

public class ProductCheckoutStepDefinition {
	
	WebDriver driver;
	public TestContext testcontext;
	
	public ProductCheckoutStepDefinition(TestContext testcontext) throws JDOMException, IOException
	{
		this.testcontext = testcontext;
	}

	@Then("Get the Total item list name")
	public void get_the_total_item_list_name() {
		testcontext.pc.getItemNames();
		//testcontext.bp.write(testcontext.ProductCheckout_test);
	}
	
	@Then("Get the Total item list size")
	public void get_the_total_item_list_size() {
		testcontext.pc.getTotalItemList();
	}
}
