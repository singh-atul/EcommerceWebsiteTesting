package objectManager;

import org.openqa.selenium.WebDriver;

import pageObjects.HomePageObjects;

public class PageObjectManager 
{	
	private WebDriver driver;
	private HomePageObjects homePageObjects;
	
	public PageObjectManager(WebDriver driver)
	{
		this.driver=driver;
	}

	public HomePageObjects getHomePageObjects()
	{
		return (homePageObjects==null) ? homePageObjects = new HomePageObjects(driver): homePageObjects;
	}
}
