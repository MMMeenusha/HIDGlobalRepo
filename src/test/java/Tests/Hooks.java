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
import org.apache.commons.mail.EmailException;
import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestRunner.TestRunner;
import Utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;

public class Hooks{
	
	public WebDriver driver;
	TestContext testcontext;
	public Scenario scenario;
	public TestRunner ter;	
	
	public static String start_time;
	public static String end_time;
	Date date1;
	Date date2;
	public static String reportpath;
	
	public static List<String> classNames = new ArrayList<String>();
	public static HashMap<String, Integer> ClassTCDetails = new HashMap<String, Integer>();
	
	
	
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
		File f = new File(RP);
		f.mkdir();
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
			Integer passedTCCount = 0;
			if((Integer) ClassTCDetails.get(className+"_PassedTC") != null)
			{
				passedTCCount = (Integer) ClassTCDetails.get(className+"_PassedTC");
			}
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
		Hooks.reportpath = this.testcontext.td.getProp("reportPath");
		TestRunner.reportpath = this.testcontext.td.getProp("reportPath");
		TestRunner.mail = this.testcontext.td.getProp("mail");
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
			//testcontext.driver.get(testcontext.td.getProp("url"));					
		}
		return testcontext.driver;
		
	}
	  

	@Before("@Assesment") // for step definitions to recognise
	public void setUpProductVerification(Scenario scenario) throws JDOMException, IOException
	{
		//testcontext.objXMLReader.loadTestDataXML(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\XMLData\\practise.xml");
		//testcontext.objXMLReader.getTCNameFromChildren("AddTheProduct");
		
		String FName = FilenameUtils.getBaseName(scenario.getUri().toString());//to feature file name
		if(FName.equalsIgnoreCase("HIDGlobalAssesment"))
		{	
			if(TestContext.HIDGlobal_extent==null) 
			{
				TestContext.HIDGlobal_extent = new ExtentReports();
				TestContext.HIDGlobal_extentReporter = new ExtentSparkReporter(reportpath+""+FName+".html");
				TestContext.HIDGlobal_extent.attachReporter(TestContext.HIDGlobal_extentReporter);
				TestContext.HIDGlobal_extentReporter.config().setTheme(Theme.DARK);
			}
			testcontext.HIDGlobal_test = TestContext.HIDGlobal_extent.createTest(scenario.getName());
			testcontext.hid.test = this.testcontext.HIDGlobal_test;
		}
	}
	
	@After("@Assesment") // for step definitions to recognise
	public void TearDownProductVerification(Scenario scenario) throws JDOMException, IOException
	{
		if(scenario.getStatus().toString().equalsIgnoreCase("failed"))
		{
			testcontext.HIDGlobal_test.fail(scenario.getName() +" - Failed");	
		}else {
			testcontext.HIDGlobal_test.pass(scenario.getName() +" - completed Succesfully");
		}
		TestContext.HIDGlobal_extent.flush();
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
		}else {
			int count=1;
			if(ClassTCDetails.containsKey(FName+"_PassedTC"))
			{
				count = (int) ClassTCDetails.get(FName+"_PassedTC") + 1;
				ClassTCDetails.put(FName+"_PassedTC", count);
			}else {
				ClassTCDetails.put(FName+"_PassedTC", count);
			}
		}
	}
}
