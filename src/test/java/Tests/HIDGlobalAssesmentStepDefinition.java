package Tests;

import java.io.IOException;

import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;

import Utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class HIDGlobalAssesmentStepDefinition {
	
	WebDriver driver;
	public TestContext testcontext;
	String response;
	
	public HIDGlobalAssesmentStepDefinition(TestContext testcontext) throws JDOMException, IOException
	{
		this.testcontext = testcontext;
	}
	
	@Given("Navigate to React JS site")
	public void navigate_to_react_js_site() throws Exception {
		testcontext.hid.loginApplication();
	}

	@Then("Get Text from {string} and save it in a file")
	public void get_text_from_and_save_it_in_a_file(String text) throws IOException {
	    testcontext.hid.getDataAndSaveinFile(text);
	}
	
	@Then("Verify Scroll funnctionality and the respected content is Bolded on Right Navigation and Blue color line is seen")
	public void verify_scroll_funnctionality_and_the_respected_content_is_bolded_on_right_navigation_and_blue_color_line_is_seen() throws InterruptedException, IOException {
		testcontext.hid.verifyScroll();
	}
	
	@Given("Get Response for API site")
	public void get_response_for_api_site() {
		response = testcontext.hid.getResponse();
	}

	@Then("Get the count of categories list")
	public void get_the_count_of_categories_list() {
		testcontext.hid.CategaryList(response);
	}
	
	@Then("get the names and geo locations of food category")
	public void get_the_names_and_geo_locations_of_food_category() {
		testcontext.hid.NameAndLocationsForFood(response);
	}
	
	@Given("Get Response for API site using POJO")
	public void get_response_for_api_site_using_pojo() {
		testcontext.hid.getResponseAsClass();
	}
	@Then("Get the count of categories list using POJO")
	public void get_the_count_of_categories_list_using_pojo() {
		testcontext.hid.CategaryListAsPOJO();
	}
	@Then("get the names and geo locations of food category using POJO")
	public void get_the_names_and_geo_locations_of_food_category_using_pojo() {
		testcontext.hid.NameAndLocationsForFoodAsPOJO();
	}
	
}
