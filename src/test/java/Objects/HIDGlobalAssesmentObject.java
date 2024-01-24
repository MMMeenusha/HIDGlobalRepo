package Objects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HIDGlobalAssesmentObject {
	
	@FindBy(xpath ="(//*[text()='Docs'])[1]")
	public WebElement Docs;
	
	@FindBy(xpath ="//*[text()='Main Concepts']")
	public WebElement MainConcepts;
	
	@FindBy(xpath ="//*[@class='css-e8rm5m']")
	public List<WebElement> TutorialsList;
	
	@FindBy(xpath ="//*[text()='Advanced Guides']")
	public WebElement AdvancedGuides;
	
	@FindBy(xpath ="(//*[text()='Advanced Guides']/following::ul)[1]/li")
	public List<WebElement> AdvancedGuidesList;
	
	public static final String MainConceptsListValues = "(//*[text()='Main Concepts']/following::ul)[1]/li[dynamic]/a";

	public static final String AdvancedGuidesListValues = "(//*[text()='Advanced Guides']/following::ul)[1]/li[dynamic]/a";
	
	@FindBy(xpath ="//*[text()='Tutorial']")
	public WebElement Tutorial;
	
	@FindBy(xpath ="(//*[text()='Main Concepts']/following::ul)[1]/li")
	public List<WebElement> MainConceptsList;
	
	public static final String TutorialText = "(//*[@class='css-e8rm5m'])[dynamic]";
	
	public static final String TutorialTextContains = "(//*[contains(text(),'dynamic')])[1]";

	public static final String TutorialTexts = "(//*[text()='dynamic'])";
	
	public static final String TutorialTextContains1 = "(//*[contains(text(),'dynamic')])[value]";
	
	public static final String TutorialTextEquals = "(//*[text()='dynamic'])[value]";
	
	public static final String TutorialTextContainsColourCode = "(//*[contains(text(),'dynamic')])[value]/span";
	
	public static final String TutorialTextEqualsColourCode = "(//*[text()='dynamic'])[value]/span";
	
	public static final String TutorialTextinFrameList = "//*[contains(text(),'dynamic')]";
}
