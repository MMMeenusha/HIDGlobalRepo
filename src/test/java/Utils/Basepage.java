package Utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Basepage {
	
	public WebDriver driver;
	TestData td;
	
	public Basepage(WebDriver driver) throws IOException
	{
		this.driver = driver;
		this.td = new TestData();
	}

	public void explicitWait(WebDriver driver,WebElement element) 
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
		wait.until(ExpectedConditions.invisibilityOf(element));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	
	
	public String captureScreenshot(WebDriver driver,String name) throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		name = name.replaceAll("[^\\w\\s]","");
		String ssLoc = td.getProp("reportPath")+"screenshots//"+name+".png";
		FileUtils.copyFile(scrFile, new File(ssLoc));
		return ssLoc;
	}
}
//screenshot998219646180167820