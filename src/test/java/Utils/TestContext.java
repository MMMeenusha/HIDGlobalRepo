package Utils;

import java.io.IOException;
import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Pages.HIDGlobalAssesmentPage;
import Tests.Hooks;

public class TestContext {
	
	public WebDriver driver;
	
	//utils
	public Basepage bp;
	public TestData td;
	public sql sql;
	
	//Test
	public Hooks hooks;

	//Pages
	public HIDGlobalAssesmentPage hid;
	
	
	//extent	
	public ExtentTest HIDGlobal_test;	
	public static ExtentSparkReporter HIDGlobal_extentReporter;
	public static ExtentReports HIDGlobal_extent;
	
	
	public TestContext() throws IOException, JDOMException
	{		
		this.hooks = new Hooks(this);
		this.td = new TestData();
		this.sql = new sql();
		
		//utils
		bp = PageFactory.initElements(hooks.launchDriver(), Basepage.class);
		
		hid=new HIDGlobalAssesmentPage(hooks.launchDriver());
	}
	

}
