package Pages;

import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Objects.ProductCheckoutObject;
import Utils.Basepage;
import Utils.TestData;
import Utils.XMLReader;
import Utils.sql;

public class ExcelPage{
	
	public WebDriver driver;

	public TestData td;
	XMLReader xml;
	//ExcelObject po;
	sql sql;
	Basepage b;
	
	public ExtentTest test;

	public ExcelPage(WebDriver driver) throws IOException, JDOMException {
		this.driver = driver;
		this.td = new TestData();
		this.xml = new XMLReader();
		this.sql = new sql();
		//this.test = test;
		//this.po = PageFactory.initElements(driver, ExcelObject.class);
		this.b = PageFactory.initElements(driver, Basepage.class);
	}

	public String ReadExcel(List<Object> data) throws NumberFormatException, Exception
	{
		return b.ReadDataFromExcel(driver, test, data.get(0).toString(),data.get(1).toString(),Integer.parseInt(data.get(2).toString()),Integer.parseInt(data.get(3).toString()));
	}
	
	public void WriteExcel(List<Object> data) throws NumberFormatException, Exception
	{
		b.WriteDataToExcel(driver, test, data.get(0).toString(),data.get(1).toString(),Integer.parseInt(data.get(2).toString()),Integer.parseInt(data.get(3).toString()),data.get(4).toString());
	}
	
	public void loginApplication(String uname,String pword) throws Exception
	{
		b.loginApplicationFromExcel(driver, uname, pword, test);
	}
	
	public void ReadLoopExcel(List<Object> data) throws NumberFormatException, Exception
	{
		b.ReadLoopDataFromExcel(driver, test, data.get(0).toString(),data.get(1).toString(),Integer.parseInt(data.get(2).toString()),Integer.parseInt(data.get(3).toString()));
	}
}
