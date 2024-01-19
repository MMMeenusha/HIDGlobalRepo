package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.WebDriver;

public class TestData {
	
	//public WebDriver driver;
	
	public Properties prop;
	public FileInputStream inp;
	public PropertiesConfiguration config;

	public TestData() throws IOException 
	{
		//this.driver = driver;
		prop = new Properties();
		inp = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\config.properties");
		prop.load(inp);
	}
	
	public String getProp(String getvalue) throws IOException
	{
		String value = prop.getProperty(getvalue);
		return value;				
	}
	
	public void setProp(String key,String value) throws Exception
	{
		config = new PropertiesConfiguration(System.getProperty("user.dir")+"\\src\\test\\resources\\TestData\\config.properties");
		config.setProperty(key,value);
		config.save();
		
	}
}
