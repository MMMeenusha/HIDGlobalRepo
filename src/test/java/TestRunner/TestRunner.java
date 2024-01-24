package TestRunner;

import java.io.IOException;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features = "src/test/java/Features",
		glue = "Tests",
		dryRun = false,
		tags="@Assesment"
		)
public class TestRunner extends AbstractTestNGCucumberTests{
	
	public static String reportpath="" ;
	public static String mail="" ;
	
	
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
}
