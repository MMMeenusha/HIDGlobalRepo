package TestRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.jdom2.JDOMException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import Tests.Hooks;
import Utils.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/Features",
		glue = "Tests",
		dryRun = false
		)
public class TestRunner extends AbstractTestNGCucumberTests{
		
	//reports
	//public static ExtentSparkReporter extentReporter;
	//public static ExtentReports extent;
	//public ExtentTest test;
	
	
	public static String reportpath="" ;
	public static String mail="" ;
	
	public int h=5;
	
	public TestRunner() {
		/*System.out.println(TestRunner.extent);
		if(TestRunner.extent==null)
		{
			TestRunner.extent = new ExtentReports();
			TestRunner.extentReporter = new ExtentSparkReporter("C:\\Meenusha\\Reports1\\AutomationReports.html");
		}
		TestRunner.extent.attachReporter(TestRunner.extentReporter);
		TestRunner.extentReporter.config().setTheme(Theme.DARK);*/
	}
	
	
	@Override @DataProvider(parallel = true)
	public Object[][] scenarios(){
		return super.scenarios();

	}
	
	@BeforeSuite()
	public void beforeAll() throws Exception
	{
		System.out.println("BeforeSuite");
	}
	
	@AfterSuite()
	public void afterAll() throws Exception
	{
		System.out.println("AfterSuite");
		if(mail.equalsIgnoreCase("true"))
		{
			mail();
		}
	}
	
	
	public static void mail() throws IOException, EmailException
	{
		
		EmailAttachment attachment = new EmailAttachment();
		
		String path = reportpath+"\\Summary.html";
		attachment.setPath(path);
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("htmlFile");
		attachment.setName("Summary.html");
		MultiPartEmail email = new MultiPartEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator("meenu1005personal@gmail.com", "ydxszuolkowdkvcc"));
		email.setSSLOnConnect(true);
		email.setFrom("meenu1005personal@gmail.com","Myself");
		email.setSubject("Execution in Local");
		StringBuffer sb = new StringBuffer();
		sb.append("Hi All,"+"\r\n");
		sb.append("Please find the attached report for reference,"+"\r\n");
		sb.append("Report Path : - "+path+" "+"\r\n\n");
		sb.append("Thanks"+"\r\n");
		sb.append("Meenusha");
		email.setMsg(sb.toString());
		email.addTo("meenu1005personal@gmail.com");
		email.addCc("meenu1005personal@gmail.com");		
		
		email.attach(attachment);
		email.send();		
		System.out.println("sent sucess");
	}
	
	/*@BeforeSuite
	public void beforesuite() throws IOException, JDOMException
	{
		h=5;
		this.extent = new ExtentReports();
		this.extentReporter = new ExtentSparkReporter("C:\\Meenusha\\Reports1\\AutomationReports.html");
		this.extent.attachReporter(this.extentReporter);
		this.extentReporter.config().setTheme(Theme.DARK);
	}
	
	@AfterSuite
	public void as()
	{
		//this.extent.flush();
	}*/
}
