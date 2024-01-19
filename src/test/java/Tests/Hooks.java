package Tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.poi.hssf.record.common.FeatFormulaErr2;
import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestRunner.TestRunner;
import Utils.TestContext;
import io.cucumber.core.gherkin.Feature;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks{
	
	public WebDriver driver;
	TestContext testcontext;
	public Scenario scenario;
	public TestRunner ter;
	
	/*public ExtentTest ProductVerification_test;
	
	public static ExtentSparkReporter ProductVerification_extentReporter;
	public static ExtentReports ProductVerification_extent;
	
	public ExtentTest ProductCheckout_test;
	
	public static ExtentSparkReporter ProductCheckout_extentReporter;
	public static ExtentReports ProductCheckout_extent;
	*/
	
	
	public static String start_time;
	public static String end_time;
	Date date1;
	Date date2;
	public static String reportpath;
	
	public static List classNames = new ArrayList();
	public static HashMap ClassTCDetails = new HashMap();

	public static List ProductVerification_TotalTC = new ArrayList();
	public static List ProductVerification_PassedTC = new ArrayList();
	public static List ProductVerification_FailedTC = new ArrayList();

	public static List ProductCheckout_TotalTC = new ArrayList();
	public static List ProductCheckout_PassedTC = new ArrayList();
	public static List ProductCheckout_FailedTC = new ArrayList();
	
	public static List Excel_TotalTC = new ArrayList();
	public static List Excel_PassedTC = new ArrayList();
	public static List Excel_FailedTC = new ArrayList();
	
	
	
	public Hooks(TestContext testcontext) throws IOException
	{
		this.testcontext = testcontext;
	}
	
	
	@BeforeAll()
	public static void beforeAll() throws Exception
	{
		start_time = date();		
		System.out.println("BeforeAll");
	}
	
	@AfterAll()
	public static void afterAll() throws Exception
	{
		end_time = date();
		System.out.println("AfterAll");
		createSummaryReport(start_time,end_time,reportpath);
	}
	
	public static void createSummaryReport(String start,String end,String RP) throws IOException, ParseException, EmailException
	{
		File file = new File(RP+"Summary.html");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write("<html>");
		writer.write("<body bgcolor=\"lightblue\">");
		writer.write("<center>");		
		writer.write("<table border=1 cellspacing=6 bgcolor=white>");
		writer.write("<caption><b>Summary Report</b></caption>");
		writer.write("<tr>");
		writer.write("<th rowspan=1>Modules</th> ");
		writer.write("<th colspan=1>Total TCs</th>");
		writer.write("<th colspan=1>Passed TCs</th>");
		writer.write("<th colspan=1>Failed TCs</th>");
		writer.write("</tr>");
		
		//System.out.println("added classes are - "+TestBase.ClassName);
		
		int TTC=0;
		int PTC=0;
		int FTC=0;
		for(int i=0;i<classNames.size();i++)
		{			
			String className = classNames.get(i).toString().trim();
			System.out.println("className - " +className);
			Integer TotalTCCount = (Integer) ClassTCDetails.get(className+"_TotalTC");
			TTC=TTC+TotalTCCount;
			Integer passedTCCount = (Integer) ClassTCDetails.get(className+"_PassedTC");
			PTC=PTC+passedTCCount;
			Integer failedTCCount=0;
			if((Integer) ClassTCDetails.get(className+"_FailedTC") != null)
			{
				failedTCCount = (Integer) ClassTCDetails.get(className+"_FailedTC");
			}
			//Integer failedTCCount = (Integer) ClassTCDetails.get(className+"_FailedTC");
			FTC=FTC+failedTCCount;
			
			writer.write("<tr>");
			writer.write("<th> <a href='"+ "./"+className+".html'><b>"+ className + "</b></a></th>");
			writer.write("<th>"+TotalTCCount+"</th>");
			writer.write("<th>"+passedTCCount+"</th>");
			writer.write("<th>"+failedTCCount+"</th>");
			writer.write("</tr>");
		}
		
		writer.write("<tr>");
		writer.write("<th>Total TC Details</th>");
		writer.write("<th>"+TTC+"</th>");
		writer.write("<th>"+PTC+"</th>");
		writer.write("<th>"+FTC+"</th>");
		writer.write("</tr>");
		
		writer.write("<tr>");
		writer.write("<th colspan=4 height=20> Start Time - "+start+" </th> ");
		writer.write("</tr>");
		
		writer.write("<tr>");
		writer.write("<th colspan=4 height=20> End Time - "+end+" </th> ");
		writer.write("</tr>");
		
		String diff = differenceInTime(start, end);
		
		writer.write("<tr>");
		writer.write("<th colspan=4 height=20> Total Execution Time - "+diff+" </th> ");
		writer.write("</tr>");
		
		writer.write("</table>");
		writer.write("</center>");
		writer.write("</body>");
		writer.write("</html>");
		writer.close();
	}
	
	public static String differenceInTime(String start_time,String end_time) throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date1 = formatter.parse(start_time);
	    Date date2 = formatter.parse(end_time);
	    long difference = date2.getTime() - date1.getTime(); 
	    long diffSeconds = difference / 1000 % 60;
	    long diffMinutes = difference / (60 * 1000) % 60;
	    long diffHours = difference / (60 * 60 * 1000) % 24;
	    long diffDays = difference / (24 * 60 * 60 * 1000);
	    
	    String diff = diffDays + " days, " +diffHours + " hours, " +diffMinutes + " minutes, " +diffSeconds + " seconds.";

	    System.out.print(diff);
		return diff;
	}
	
	public static String date()
	{
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    System.out.println(formatter.format(date)); 
	    String time = formatter.format(date);
		return time;
	}
	
	
	@Before
	public void setUp(Scenario scenario) throws IOException
	{
		launchDriver();			
		this.reportpath = this.testcontext.td.getProp("reportPath");
		this.ter.reportpath = this.testcontext.td.getProp("reportPath");
		this.ter.mail = this.testcontext.td.getProp("mail");
		String FName = FilenameUtils.getBaseName(scenario.getUri().toString());//to feature file name
		if(!classNames.contains(FName))
		{
			classNames.add(FName);
		}
	}
	
	@After
	public void TearDown(Scenario scenario)
	{
		System.out.println(scenario.getName() +" - " + scenario.getStatus());
		testcontext.driver.quit();
		System.out.println(ClassTCDetails);
	}
	
	public WebDriver launchDriver() throws IOException
	{
		if(testcontext.driver==null) 
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\chromedriver\\chromedriver.exe");
			testcontext.driver = new ChromeDriver();
			testcontext.driver.manage().window().maximize();
			testcontext.driver.get(testcontext.td.getProp("url"));					
		}
		return testcontext.driver;
		
	}
	  

	@Before("@ProductVerification") // for step definitions to recognise
	public void setUpProductVerification(Scenario scenario) throws JDOMException, IOException
	{
		testcontext.objXMLReader.loadTestDataXML(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\XMLData\\practise.xml");
		testcontext.objXMLReader.getTCNameFromChildren("AddTheProduct");
		
		String FName = FilenameUtils.getBaseName(scenario.getUri().toString());//to feature file name
		if(FName.equalsIgnoreCase("ProductVerification"))
		{	
			if(TestContext.ProductVerification_extent==null) 
			{
				TestContext.ProductVerification_extent = new ExtentReports();
				TestContext.ProductVerification_extentReporter = new ExtentSparkReporter("C:\\Meenusha\\Reports1\\"+FName+".html");
				TestContext.ProductVerification_extent.attachReporter(TestContext.ProductVerification_extentReporter);
				TestContext.ProductVerification_extentReporter.config().setTheme(Theme.DARK);
				TestContext.ProductVerification_extentReporter.config().setTheme(Theme.DARK);
			}
			testcontext.ProductVerification_test = TestContext.ProductVerification_extent.createTest(scenario.getName());
			testcontext.pp.test = this.testcontext.ProductVerification_test;
		//	ProductVerification_TotalTC.add(scenario.getName());
		}
	}
	
	@After("@ProductVerification") // for step definitions to recognise
	public void TearDownProductVerification(Scenario scenario) throws JDOMException, IOException
	{
		testcontext.ProductVerification_test.pass(scenario.getName() +" - completed Succesfully");
		TestContext.ProductVerification_extent.flush();
		count(scenario);
	}
	
	public void count(Scenario scenario)
	{
		String FName = FilenameUtils.getBaseName(scenario.getUri().toString());
		int count1=1;
		if(ClassTCDetails.containsKey(FName+"_TotalTC"))
		{
			count1 = (int) ClassTCDetails.get(FName+"_TotalTC") + 1;
			ClassTCDetails.put(FName+"_TotalTC", count1);
		}else {
			ClassTCDetails.put(FName+"_TotalTC", count1);
		}
		if(scenario.getStatus().toString().equalsIgnoreCase("failed"))
		{
			int count=1;
			if(ClassTCDetails.containsKey(FName+"_FailedTC"))
			{
				count = (int) ClassTCDetails.get(FName+"_FailedTC") + 1;
				ClassTCDetails.put(FName+"_FailedTC", count);
			}else {
				ClassTCDetails.put(FName+"_FailedTC", count);
			}
			//			Excel_FailedTC.add(scenario.getName());
		}else {
			int count=1;
			if(ClassTCDetails.containsKey(FName+"_PassedTC"))
			{
				count = (int) ClassTCDetails.get(FName+"_PassedTC") + 1;
				ClassTCDetails.put(FName+"_PassedTC", count);
			}else {
				ClassTCDetails.put(FName+"_PassedTC", count);
			}
			//Excel_PassedTC.add(scenario.getName());	
		}
	}
	@Before("@ProductCheckout") // for step definitions to recognise
	public void setUpProductCheckout(Scenario scenario) throws JDOMException, IOException
	{
		testcontext.objXMLReader.loadTestDataXML(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\XMLData\\practise.xml");
		testcontext.objXMLReader.getTCNameFromChildren("AddTheProduct");
		
		String FName = FilenameUtils.getBaseName(scenario.getUri().toString());//to feature file name		
		if(FName.equalsIgnoreCase("ProductCheckout"))
		{	
			if(TestContext.ProductCheckout_extent==null) 
			{
				TestContext.ProductCheckout_extent = new ExtentReports();
				TestContext.ProductCheckout_extentReporter = new ExtentSparkReporter("C:\\Meenusha\\Reports1\\"+FName+".html");
				TestContext.ProductCheckout_extent.attachReporter(TestContext.ProductCheckout_extentReporter);
				TestContext.ProductCheckout_extentReporter.config().setTheme(Theme.DARK);
				TestContext.ProductCheckout_extentReporter.config().setTheme(Theme.DARK);
			}
			testcontext.ProductCheckout_test = TestContext.ProductCheckout_extent.createTest(scenario.getName());
			testcontext.pc.test = this.testcontext.ProductCheckout_test;
			testcontext.pp.test = this.testcontext.ProductCheckout_test;
			//ProductCheckout_TotalTC.add(scenario.getName());
		}
	}
	
	@After("@ProductCheckout") // for step definitions to recognise
	public void TearDownProductCheckout(Scenario scenario) throws JDOMException, IOException
	{
		testcontext.ProductCheckout_test.pass(scenario.getName() +" - completed Succesfully");
		TestContext.ProductCheckout_extent.flush();
		count(scenario);
	}
	
	@Before("@Excel") // for step definitions to recognise
	public void setUpExcel(Scenario scenario) throws JDOMException, IOException
	{
		testcontext.objXMLReader.loadTestDataXML(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\XMLData\\practise.xml");
		testcontext.objXMLReader.getTCNameFromChildren("AddTheProduct");
		
		String FName = FilenameUtils.getBaseName(scenario.getUri().toString());//to feature file name		
		if(FName.equalsIgnoreCase("Excel"))
		{	
			if(TestContext.Excel_extent==null) 
			{
				TestContext.Excel_extent = new ExtentReports();
				TestContext.Excel_extentReporter = new ExtentSparkReporter("C:\\Meenusha\\Reports1\\"+FName+".html");
				TestContext.Excel_extent.attachReporter(TestContext.Excel_extentReporter);
				TestContext.Excel_extentReporter.config().setTheme(Theme.DARK);
				TestContext.Excel_extentReporter.config().setTheme(Theme.DARK);
			}
			testcontext.Excel_test = TestContext.Excel_extent.createTest(scenario.getName());
			testcontext.e.test = this.testcontext.Excel_test;
			testcontext.pp.test = this.testcontext.Excel_test;
			//Excel_TotalTC.add(scenario.getName());
		}
	}
	
	@After("@Excel") // for step definitions to recognise
	public void TearDownExcel(Scenario scenario) throws JDOMException, IOException
	{
		testcontext.Excel_test.pass(scenario.getName() +" - completed Succesfully");
		TestContext.Excel_extent.flush();
		count(scenario);
	}
}
