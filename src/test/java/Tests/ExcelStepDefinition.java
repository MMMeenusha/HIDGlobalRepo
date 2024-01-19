package Tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;

import Utils.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ExcelStepDefinition {

	WebDriver driver;
	public TestContext testcontext;
	String uname;
	String pword;
	
	
	public ExcelStepDefinition(TestContext testcontext) throws JDOMException, IOException
	{
		this.testcontext = testcontext;
	}
	
	@Given("Read Data From Excel To get Username")
	public void read_data_from_excel_to_get_username(List<Object> data) throws NumberFormatException, Exception {		
		uname = testcontext.e.ReadExcel(data);		
	}
	
	@Given("Read Data From Excel To get Password")
	public void read_data_from_excel_to_get_password(List<Object> data) throws NumberFormatException, Exception {
		pword = testcontext.e.ReadExcel(data);
	}
	
	@Then("Login To application with provided Username and Password")
	public void login_to_application_with_provided_username_and_password() throws Exception {
		testcontext.e.loginApplication(uname, pword);
	}
	
	@Given("Write Data to Excel for Username")
	public void write_data_to_excel_for_username(List<Object> data) throws NumberFormatException, Exception {
		testcontext.e.WriteExcel(data);
	}
	
	@Given("Write Data to Excel for Password")
	public void write_data_to_excel_for_password(List<Object> data) throws NumberFormatException, Exception {
		testcontext.e.WriteExcel(data);
	}

	@Given("Read Data from Excel to get Ages")
	public void read_data_from_excel_to_get_ages(List<Object> data) throws NumberFormatException, Exception {
		testcontext.e.ReadLoopExcel(data);	
	}
}
