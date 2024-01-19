package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Basepage {
	
	public WebDriver driver;
	TestData td;
	
	public Basepage() throws IOException
	{
		this.driver = driver;
		this.td = new TestData();
	}


	@FindBy(xpath ="//*[@id='user-name']")
	public WebElement uname;
	
	@FindBy(xpath ="//*[@id='password']")
	public WebElement pword;
	
	@FindBy(xpath ="//*[@id='login-button']")
	public WebElement login;	
	
	public void loginApplication(WebDriver driver,ExtentTest test) throws Exception
	{
		try {
			uname.click();
			uname.sendKeys(td.getProp("username"));
			pword.click();
			pword.sendKeys(td.getProp("password"));
			login.click();
			Thread.sleep(1000);
			test.pass("Logged in successfully");
		}catch(Exception e) {
			String dest = captureScreenshot(driver,"Login screen");
			test.fail("Logged in not success", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			Assert.assertEquals(false, true);
		}
	}
	
	public void loginApplicationFromExcel(WebDriver driver,String usname,String psword,ExtentTest test) throws Exception
	{
		try {
			uname.click();
			uname.sendKeys(usname);
			pword.click();
			pword.sendKeys(psword);
			login.click();
			test.pass("Logged in successfully");
		}catch(Exception e) {
			String dest = captureScreenshot(driver,"Login screen");
			test.fail("Logged in not success", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			Assert.assertEquals(false, true);
		}
	}
	
	public String ReadDataFromExcel(WebDriver driver,ExtentTest test, String excelName,String SheetName,int rowNo,int colNo) throws Exception
	{
		try {
			File file =    new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\"+excelName+".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			String data= wb.getSheet(SheetName).getRow(rowNo).getCell(colNo).getStringCellValue();
			test.pass("Data has Read succesfully from Workbook - "+excelName);
			inputStream.close();
			return data;
		}catch(Exception e) {
			String dest = captureScreenshot(driver,"Read Excel");
			test.fail("Issue in Reading Excel", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			Assert.assertEquals(false, true);
			return null;
		}
	}
	
	public void ReadLoopDataFromExcel(WebDriver driver,ExtentTest test, String excelName,String SheetName,int rowNo,int colNo) throws Exception
	{
		try {
			File file =    new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\"+excelName+".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			int rowcount = wb.getSheet(SheetName).getRow(rowNo).getLastCellNum()+1;
			for(int i = rowNo;i<(rowNo+rowcount);i++)
			{
				String key = wb.getSheet(SheetName).getRow(i).getCell(colNo).getStringCellValue();
				XSSFCell cell = wb.getSheet(SheetName).getRow(i).getCell(colNo+1);
				int value_type = cell.getCellType();
				double value = 0;
				if(value_type == cell.CELL_TYPE_NUMERIC)
				{
					value = wb.getSheet(SheetName).getRow(i).getCell(colNo+1).getNumericCellValue();
				}
				test.pass("Age for "+key+" - "+(int)value+"");
			}
			inputStream.close();
		}catch(Exception e) {
			String dest = captureScreenshot(driver,"Read Excel");
			test.fail("Issue in Reading Excel", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			Assert.assertEquals(false, true);
		}
	}
	
	public void WriteDataToExcel(WebDriver driver,ExtentTest test, String excelName,String SheetName,int rowNo,int colNo,String text) throws Exception
	{
		try {
			File file = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\"+excelName+".xlsx"); 
			FileInputStream inputStream = new FileInputStream(file); 
			XSSFWorkbook wb=new XSSFWorkbook(inputStream);
			try{
				wb.createSheet(SheetName);
			}catch(Exception e) {}
			XSSFSheet sheet = wb.getSheet(SheetName);
			XSSFRow newRow = sheet.createRow(rowNo);
			XSSFCell cell = newRow.createCell(colNo);
			cell.setCellValue(text);
			inputStream.close();

			FileOutputStream outputStream = new FileOutputStream(file);
			wb.write(outputStream);
			outputStream.close();
			test.pass("Data has written succesfully in Workbook - "+excelName);
		}catch(Exception e) {
			String dest = captureScreenshot(driver,"Write Excel");
			test.fail("Issue in Writting Excel", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			Assert.assertEquals(false, true);
		}
	}
	
	public String captureScreenshot(WebDriver driver,String name) throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String ssLoc = td.getProp("reportPath")+"screenshots//"+name+".png";
		FileUtils.copyFile(scrFile, new File(ssLoc));
		return ssLoc;
	}
}
