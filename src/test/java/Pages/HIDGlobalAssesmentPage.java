package Pages;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.jdom2.JDOMException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Objects.HIDGlobalAssesmentObject;
import Utils.Basepage;
import Utils.TestData;
import Utils.sql;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class HIDGlobalAssesmentPage{

	public WebDriver driver;

	public TestData td;
	HIDGlobalAssesmentObject obj;
	sql sql;
	Basepage b;
	
	pojo object;
	
	public ExtentTest test;

	public HIDGlobalAssesmentPage(WebDriver driver) throws IOException, JDOMException {
		this.driver = driver;
		this.td = new TestData();
		this.sql = new sql();
		//this.test = test;
		this.obj = PageFactory.initElements(driver, HIDGlobalAssesmentObject.class);
		this.b = PageFactory.initElements(driver, Basepage.class);
		this.object = new pojo();
	}
	
	public void loginApplication() throws Exception
	{
		driver.get(td.getProp("url"));
		test.pass("Navigated to application succesfully");
	}
	
	public void getDataAndSaveinFile(String text) throws IOException
	{		
		clickOnDocs();
		for(int i=0;i<3;i++)
		{	
			try {
			if(text.equalsIgnoreCase("Main Concept")) 
			{
				if(obj.MainConcepts.isDisplayed()) {
					System.out.println(text +" is displayed");
				}else {
					b.explicitWait(driver, obj.MainConcepts);}
			} else {
				if(obj.AdvancedGuides.isDisplayed()) {
					System.out.println(text +" is displayed");
				}else {
					b.explicitWait(driver, obj.AdvancedGuides); }
			}
			int size = getListsize(text);
			writeDatainFile(size,text);
			break;
			}
			catch(Exception e)
			{
				continue;
			}
		}
	}
	
	public int getListsize(String text)
	{
		WebElement element = null; 
		List<WebElement> elementList = null;
		if(text.equalsIgnoreCase("Main Concept")) {
			element = obj.MainConcepts;
			elementList = obj.MainConceptsList;
		}else {
			element = obj.AdvancedGuides;
			elementList = obj.AdvancedGuidesList;
		}
		element.click();
		test.pass("Clicked on "+text+"");
		int size = elementList.size();
		test.pass("Size of "+text+" - "+size);
		return size;
	}
	
	public void clickOnDocs() 
	{
		obj.Docs.click();
		test.pass("Clicked on Docs");
		System.out.println("Clicked on Docs");
	}
	
	public void writeDatainFile(int size,String textt) throws IOException
	{
		String element = null; String fileName = "";
		if(textt.equalsIgnoreCase("Main Concept")) {
			element = HIDGlobalAssesmentObject.MainConceptsListValues;
			fileName = "HIDMainConcepts";
		}else {
			element = HIDGlobalAssesmentObject.AdvancedGuidesListValues;
			fileName = "HIDAdvancedGuides";
		}
		File path = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\"+fileName+".txt");
		FileWriter wr = new FileWriter(path);
		List<String> a = new ArrayList<String>();
		for(int i=1;i<=size;i++)
		{
			String text = driver.findElement(By.xpath(element.replace("dynamic", ""+i))).getText();
			text = text.replaceAll("\\d", "").replace(".","");
			a.add(text);
			wr.write(text+ "\n");			
		}
		test.pass("Values displayed in "+textt+" - " +a);
		test.pass("Values updated in text file succesfully");
		wr.flush();
		wr.close();	
	}

	public void verifyScroll() throws InterruptedException, IOException
	{
		obj.Tutorial.click();
		String dest = b.captureScreenshot(driver,"Tutorials");
		test.pass("Navigated to Tutorials Succesffully", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
		//Thread.sleep(1000);
		int size = obj.TutorialsList.size();
		System.out.println("size is - "+size);
		int count=0;
		for(int i=1;i<size;i++)
		{
			String text ="";
			text = driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialText.replace("dynamic", ""+i))).getText();
			if(i==2&&count==1) // as size gets reduced by 1, after clicking on first element -- thus added this step
			{				
				i--;
			}test.pass("***** Verifying text - "+text +" *****");
			System.out.println(text);
			////Thread.sleep(1000);
			if(text.contains("Help, I"))  //as I'm text is different in both the fields -- thus added this step
			{
				text = "Help, I";
			} 
			int size1 = driver.findElements(By.xpath(HIDGlobalAssesmentObject.TutorialTextinFrameList.replace("dynamic", text))).size();
			if(size1>=2 || text.contains("Help, I"))
			{
				WebElement element = driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextContains.replace("dynamic", text)));
				////Thread.sleep(1000);
				if(element.isDisplayed())
				{
					int lastSize = driver.findElements(By.xpath(HIDGlobalAssesmentObject.TutorialTexts.replace("dynamic", text))).size();
					if(text.contains("Help, I")) 
					{
						element = driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextContains1.replace("dynamic", text).replace("value", ""+size1)));
					}else {
						element = driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextEquals.replace("dynamic", text).replace("value", ""+lastSize)));
					}
					JavascriptExecutor js = (JavascriptExecutor)driver;
					js.executeScript("arguments[0].scrollIntoView(true);", element);
					js.executeScript("arguments[0].click();", element);
					Thread.sleep(1000);
					//WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(60));
					if(text.contains("Help, I")) 
					{
						//wait.until(ExpectedConditions.visibilityOfElementLocated((By) driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextContainsColourCode.replace("dynamic", text).replace("value", ""+size1)))));
						element = driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextContainsColourCode.replace("dynamic", text).replace("value", ""+size1)));
					}else {
						//wait.until(ExpectedConditions.visibilityOfElementLocated((By) driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextEqualsColourCode.replace("dynamic", text).replace("value", ""+lastSize)))));
						element = driver.findElement(By.xpath(HIDGlobalAssesmentObject.TutorialTextEqualsColourCode.replace("dynamic", text).replace("value", ""+lastSize)));
					}
					String fontWeight = element.getCssValue("font-weight");
					boolean isBold = "bold".equals(fontWeight) || "bolder".equals(fontWeight) || Integer.parseInt(fontWeight) >= 700;
					boolean isBluehighlighter = element.getAttribute("class").equals("css-ifgy4z");
					if(isBluehighlighter && isBold)
					{
						dest = b.captureScreenshot(driver,text);
						test.pass("Respected Content for the text is bold on navigation and Blue colour line is seen", MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
						System.out.println("success");
					}
				}
			}
			count++;
		}
	}
	
	public String getResponse()
	{
		RestAssured.baseURI="https://coinmap.org/";
		
		String res = given().log().all().queryParam("limit", "100").
		when().log().all().get("api/v1/venues/").
		then().log().all().statusCode(200).extract().response().asString();
		test.pass("Received Response with status code - 200");
		return res;
		
	}
	
	public pojo getResponseAsClass()
	{
		RestAssured.baseURI="https://coinmap.org/";
		
		object = given().log().all().queryParam("limit", "100").
		when().log().all().get("api/v1/venues/").
		then().log().all().statusCode(200).extract().response().as(pojo.class);
		test.pass("Received Response with status code - 200");
		return object;		
	}
	
	public void CategaryList(String res)
	{
		JsonPath js = new JsonPath(res);
		List<String> cateogary = new ArrayList<String>();
		int size = js.get("venues.size()");
		for(int i=0;i<size;i++)
		{
			String cateogary_name = js.get("venues["+i+"].category");
			cateogary.add(cateogary_name);
		}
		test.pass("Categary list size - " +cateogary.size()+" values - " +cateogary);
		HashSet<String> hs = new HashSet<String>(cateogary);
		test.pass("***** After removing duplicates *****");
		test.pass("Categary list size - " +hs.size()+" values - " +hs);
	}
	
	public void CategaryListAsPOJO()
	{
		int size = object.getVenues().size();
		List<String> cateogary = new ArrayList<String>();
		for(int i=0;i<size;i++)
		{
			String cateogary_name = object.getVenues().get(i).getCategory().toString();
			cateogary.add(cateogary_name);
		}
		test.pass("Categary list size - " +cateogary.size()+" values - " +cateogary);
		HashSet<String> hs = new HashSet<String>(cateogary);
		test.pass("***** After removing duplicates *****");
		test.pass("Categary list size - " +hs.size()+" values - " +hs);
	}
	
	public void NameAndLocationsForFood(String res)
	{
		JsonPath js = new JsonPath(res);
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		
		int size = js.get("venues.size()");
		for(int i=0;i<size;i++)
		{
			String cateogary_name = js.get("venues["+i+"].category");
			if(cateogary_name.equalsIgnoreCase("food"))
			{
				String name = js.get("venues["+i+"].name");
				String location = js.get("venues["+i+"].geolocation_degrees");
				hm.put(js.getInt("venues["+i+"].id"), name +" - " +location);
			}
		}
		
		test.pass("Food categary list size - "+hm.size()+" with id as Key and name and geo locations as values are \n\n" +hm);
	}
	
	public void NameAndLocationsForFoodAsPOJO()
	{
		int size = object.getVenues().size();
		HashMap<Integer, String> hm = new HashMap<Integer, String>();		
		for(int i=0;i<size;i++)
		{
			String cateogary_name = object.getVenues().get(i).getCategory().toString();
			if(cateogary_name.equalsIgnoreCase("food"))
			{
				String name = object.getVenues().get(i).getName().toString();
				String location = object.getVenues().get(i).getGeolocation_degrees().toString();
				hm.put(object.getVenues().get(i).getId(), name +" - " +location);
			}
		}
		
		test.pass("Food categary list size - "+hm.size()+" with id as Key and name and geo locations as values are \n\n" +hm);
	}
}
