package scripts;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import context.TestContext;
import dataProvider.ConfigFileReader;
import dataProvider.ReadWriteExcel;
import extentReport.ExtentReport;
import objectManager.DriverManager;
import pageObjects.HomePageObjects;
import utils.Action;
import utils.Logging;
import utils.Utility;

@Listeners(Listner.class)
public class TestCase 
{
	WebDriver driver;
	TestContext testContext;
	Action action;
	ExtentReport extentReport;
	SoftAssert softAssert;
	ReadWriteExcel excel;
	HomePageObjects homePageObjects;
	
	@BeforeSuite
	public void beforeSuite() throws IOException
	{
		driver = DriverManager.getDriver();
		driver.get(ConfigFileReader.getUrl());		
		testContext = new TestContext();
		homePageObjects = testContext.getPageObjectManager().getHomePageObjects();
		action = testContext.getActionObject();
		extentReport = testContext.getExtentReport();
		softAssert = new SoftAssert();
		excel = new ReadWriteExcel();	
		PropertyConfigurator.configure("log4j.properties");
	}
	
	@AfterSuite()
	public void afterSuite() throws IOException
	{		
		softAssert.assertAll();
		extentReport.flush();	
	}
	
	@BeforeMethod()
	public void beforemethod()
	{}
	
@AfterMethod
public void closeTest(ITestResult result) throws IOException {

Logging.info("Ending the Test Case Execution");
//Take the Screenshot Only, If the Test is failed.
// Change the condition , If the screenshot needs to be taken for other status as well

if(ITestResult.FAILURE==result.getStatus()){
System.out.println("Failed Status Check");
extentReport.addScreenshot(driver);
}
if
((driver.findElements(By.xpath("//div[text()=\"Logout\"]")).size()) > 0) {

loginPage.logoutBtn.click();
Logging.info("clicked on Logout Button");
} else {
Logging.info("Logout button is not displayed");
}
}
	
	
  @Test(description="TC001-This test verifies validation message displayed when user login with blank user name and password", priority=1,enabled=true)
  public void tc_1() throws Exception 
  {
	  try
	  {
		  extentReport.createTest("TC001-This test verifies validation message displayed when user login with blank user name and password");
		  action.clickLink(homePageObjects.lnkLogin, "Login link");
		  extentReport.info("Login link clicked");
		  action.clickButton(homePageObjects.btnLogIn, "LogIn");
		  extentReport.info("Login button clicked");
		  if(driver.findElements(By.xpath("//*[contains(text(),'Username and Password are required')]")).size()>0)
		  {
			  extentReport.pass("Username and Password are required validation message is displayed");
			  extentReport.addScreenshot(driver);
			  Logging.info("Username and Password are required validation message is displayed");
			  Logging.endTestCase();
		  }
		  else
		  {
			  extentReport.fail("Username and Password are required validation message is not displayed");
			  extentReport.addScreenshot(driver);
			  Logging.info("Username and Password are required validation message is not displayed");
			  Logging.endTestCase();
		  }
	  }	   
	  catch(Exception e)
	  {
		  System.out.println(e.getMessage());
		  extentReport.fail(e.getMessage());
		  extentReport.addScreenshot(driver);
		  Logging.info(e.getMessage());
		  Logging.endTestCase();
	  }
  }
  
  @Test(description="TC002-This test verifies that user can login and logout with valid credentials",priority=2,enabled=false)
  public void tc_2() throws Exception 
  {
	  try
	  {
		  HSSFSheet sheet = excel.getSheet("TC002");
		  for(int i=1;i<=sheet.getLastRowNum();i++)
		  {
			  extentReport.createTest("TC002-This test verifies that user can login and logout with valid credentials");
			  action.clickLink(homePageObjects.lnkLogin, "Login link");
			  extentReport.info("Login link clicked");
			  action.sendKeys(homePageObjects.txtUserName, sheet.getRow(i).getCell(0).getStringCellValue(), "User Name");
			  extentReport.info("User name entered");
			  action.sendKeys(homePageObjects.txtUserPass, sheet.getRow(i).getCell(1).getStringCellValue(), "User password");
			  extentReport.info("User password entered");
			  action.clickButton(homePageObjects.btnLogIn, "LogIn");
			  extentReport.info("Login button clicked");
			  if(driver.findElements(By.xpath("//*[contains(text(),'Logout')]")).size()>0)
			  {
				  extentReport.pass("Logout link displayed");
				  extentReport.addScreenshot(driver);
				  Logging.info("Logout link displayed");
				  Logging.endTestCase();
			  }
			  else
			  {
				  extentReport.fail("Logout link is not displayed");
				  extentReport.addScreenshot(driver);
				  Logging.info("Logout link is not displayed");
				  Logging.endTestCase();
			  }
			  action.clickLink(homePageObjects.lnkLogout,"Logout");
		  }
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.getMessage());
		  extentReport.fail(e.getMessage());
		  extentReport.addScreenshot(driver);
		  Logging.info(e.getMessage());
		  Logging.endTestCase();
	  }
  }
  
  @Test(description="TC003-This test verifies user can search for a product",priority=3,enabled=false)
  public void tc_3() throws Exception 
  {
	  double expectedPrice,actualPrice; 
	  try
	  {
		  HSSFSheet sheet = excel.getSheet("TC003");
		  for(int i=1;i<=sheet.getLastRowNum();i++)
		  {
			  extentReport.createTest("TC003-This test verifies user can search for a product");
			  switch(sheet.getRow(i).getCell(0).getStringCellValue())
			  {
			  	case "Electronics":
				  action.clickLink(homePageObjects.lnkElectronics, "Electronics");
				  Thread.sleep(3000);
				  extentReport.info("Electronics category clicked");
				  action.sendKeys(homePageObjects.txtSearchProductName, sheet.getRow(i).getCell(1).getStringCellValue(), "Product Name");
				  extentReport.info("Product eneterd for search - "+sheet.getRow(i).getCell(1).getStringCellValue());
				  Thread.sleep(3000);
				  action.clickLink(driver.findElement(By.xpath("//*[@alt='Item Pic']")),"Product");
				  extentReport.info("Product link clicked");
				  Thread.sleep(3000);				  
				  expectedPrice =  sheet.getRow(i).getCell(2).getNumericCellValue();				  
				  actualPrice = Double.parseDouble(homePageObjects.lblProductPrice.getText());
				  if(expectedPrice==actualPrice)
				  {
					  extentReport.pass("Expected Price-"+expectedPrice+" || Actual Price-"+actualPrice);
					  extentReport.addScreenshot(driver);
					  Logging.info("Price matches");
					  Logging.endTestCase();
				  }
				  else
				  {
					  extentReport.fail("Expected Price-"+expectedPrice+" || Actual Price-"+actualPrice);					  
					  extentReport.addScreenshot(driver);
					  Logging.info("Price do not match");
					  Logging.endTestCase();
				  }
				  break;
			  case "Kitchen Items":
				  action.clickLink(homePageObjects.lnkKitchenItems, "Kitchen Items");
				  extentReport.info("Kitchen Items category clicked");
				  Thread.sleep(3000);
				  action.sendKeys(homePageObjects.txtSearchProductName, sheet.getRow(i).getCell(1).getStringCellValue(), "Product Name");
				  extentReport.info("Product eneterd for search - "+sheet.getRow(i).getCell(1).getStringCellValue());
				  Thread.sleep(3000);
				  action.clickLink(driver.findElement(By.xpath("//*[@alt='Item Pic']")),"Product");
				  extentReport.info("Product link clicked");
				  Thread.sleep(3000);				  
				  expectedPrice =  sheet.getRow(i).getCell(2).getNumericCellValue();				  
				  actualPrice = Double.parseDouble(homePageObjects.lblProductPrice.getText());
				  if(expectedPrice==actualPrice)
				  {
					  extentReport.pass("Expected Price-"+expectedPrice+" || Actual Price-"+actualPrice);
					  extentReport.addScreenshot(driver);
					  Logging.info("Price matches");
					  Logging.endTestCase();
				  }
				  else
				  {
					  extentReport.fail("Expected Price-"+expectedPrice+" || Actual Price-"+actualPrice);					  
					  extentReport.addScreenshot(driver);
					  Logging.info("Price do not match");
					  Logging.endTestCase();
				  }
			  		break;
			  case "Sports":
				  action.clickLink(homePageObjects.lnkSports, "Sports");
				  extentReport.info("Sports category clicked");
				  Thread.sleep(3000);
				  action.sendKeys(homePageObjects.txtSearchProductName, sheet.getRow(i).getCell(1).getStringCellValue(), "Product Name");
				  extentReport.info("Product eneterd for search - "+sheet.getRow(i).getCell(1).getStringCellValue());
				  Thread.sleep(3000);
				  action.clickLink(driver.findElement(By.xpath("//*[@alt='Item Pic']")),"Product");
				  extentReport.info("Product link clicked");
				  Thread.sleep(3000);				  
				  expectedPrice =  sheet.getRow(i).getCell(2).getNumericCellValue();				  
				  actualPrice = Double.parseDouble(homePageObjects.lblProductPrice.getText());
				  if(expectedPrice==actualPrice)
				  {
					  extentReport.pass("Expected Price-"+expectedPrice+" || Actual Price-"+actualPrice);
					  extentReport.addScreenshot(driver);
					  Logging.info("Price matches");
					  Logging.endTestCase();
				  }
				  else
				  {
					  extentReport.fail("Expected Price-"+expectedPrice+" || Actual Price-"+actualPrice);					  
					  extentReport.addScreenshot(driver);
					  Logging.info("Price do not match");
					  Logging.endTestCase();
				  }
				  break;
				  default:
					  extentReport.fail("No matching category found");					  
					  extentReport.addScreenshot(driver);
					  Logging.info("No matching category found");
					  Logging.endTestCase();
					  break;					  
			  }			  
			  driver.get(ConfigFileReader.getUrl());		  
		  }		  
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.getMessage());
		  extentReport.fail(e.getMessage());
		  extentReport.addScreenshot(driver);
		  Logging.info(e.getMessage());
		  Logging.endTestCase();
	  }
  }
  
  @Test(description="TC004-This test verifies user can add a product to cart and cart details are correct",priority=4,enabled=false)
  public void tc_4() throws Exception 
  {
	  String prouctName; double priceOnDetailsPage;
	  try
	  {
		  extentReport.createTest("TC004-This test verifies user can add a product to cart and cart details are correct");
		  Utility.login(driver);
		  extentReport.info("User logged in");
		  action.clickLink(homePageObjects.lnkSports, "Sports");
		  extentReport.info("Sports category clicked");
		  action.clickImage(homePageObjects.lnkProducts,"First product");
		  extentReport.info("Fist product clicked from the list");
		  Thread.sleep(3000);
		  priceOnDetailsPage = Double.parseDouble(homePageObjects.lblProductPrice.getText());
		  prouctName = homePageObjects.lblProductName.getText();
		  action.clickButton(homePageObjects.btnAddToCart,"Add To Cart");
		  extentReport.info("Add to cart button clicked");
		  Utility.waitForElementVisible(driver, homePageObjects.btnGoToCart, 30);
		  action.clickLink(homePageObjects.lnkCart,"Cart");
		  extentReport.info("Cart link clicked");
		  if(priceOnDetailsPage==Double.parseDouble(homePageObjects.lblProductPriceCart.getText()) && prouctName.equalsIgnoreCase(homePageObjects.lblProductNameCart.getText()))
		  {
			  Logging.info("Price on detail page -"+priceOnDetailsPage+" | Name on detail page -"+prouctName);
			  Logging.info("Price on Cart -"+Double.parseDouble(homePageObjects.lblProductPriceCart.getText())+" | Name on Cart -"+homePageObjects.lblProductNameCart.getText());
			  Logging.info("Product name and price is same on cart");
			  extentReport.pass("Product name and price is same on cart");
			  extentReport.addScreenshot(driver);				  
			  Logging.endTestCase();
		  }
		  else
		  {
			  Logging.info("Price on detail page -"+priceOnDetailsPage+" | Name on detail page -"+prouctName);
			  Logging.info("Price on Cart -"+Double.parseDouble(homePageObjects.lblProductPriceCart.getText())+" | Name on Cart -"+homePageObjects.lblProductNameCart.getText());
			  Logging.info("Product name and price is not same on cart");
			  extentReport.fail("Product name and price is not same on cart");
			  extentReport.addScreenshot(driver);				  
			  Logging.endTestCase();
		  }		  
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.getMessage());
		  extentReport.fail(e.getMessage());
		  extentReport.addScreenshot(driver);
		  Logging.info(e.getMessage());
		  Logging.endTestCase();
	  }
  }
  
  @Test(description="TC005-This test verifies user can remove product from cart",priority=5,enabled=false)
  public void tc_5() throws Exception 
  {
	  try
	  {
		  extentReport.createTest("TC005-This test verifies user can remove product from cart");
		  Utility.login(driver);
		  extentReport.info("User logged in");
		  action.clickLink(homePageObjects.lnkSports, "Sports");
		  extentReport.info("Sports category clicked");
		  action.clickImage(homePageObjects.lnkProducts,"First product");
		  extentReport.info("Fist product clicked from the list");
		  action.clickButton(homePageObjects.btnAddToCart,"Add To Cart");
		  extentReport.info("Add to cart button clicked");
		  Utility.waitForElementVisible(driver, homePageObjects.btnGoToCart, 30);
		  action.clickLink(homePageObjects.lnkCart,"Cart");
		  extentReport.info("Cart link clicked");
		  action.clickButton(homePageObjects.btnRemove, "Remove");
		  extentReport.info("Remove product button clicked");
		  if(homePageObjects.msgEmptyCart.isDisplayed())
		  {
			  extentReport.pass("Your cart is empty message shown");
			  extentReport.addScreenshot(driver);
			  Logging.info("Your cart is empty message shown");
			  Logging.endTestCase();
		  }
		  else
		  {
			  extentReport.fail("Your cart is empty message is not shown");
			  extentReport.addScreenshot(driver);
			  Logging.info("Your cart is empty message is not shown");
			  Logging.endTestCase();
		  }
		  
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.getMessage());
		  extentReport.fail(e.getMessage());
		  extentReport.addScreenshot(driver);
		  Logging.info(e.getMessage());
		  Logging.endTestCase();
	  }	  
  }
  
  @Test(description="TC006-This test verify user can order product",priority=6,enabled=false)
  public void tc_6() throws Exception 
  {
	  try
	  {		  
		  extentReport.createTest("TC006-This test verify user can order product");
		  Utility.login(driver);
		  extentReport.info("User logged in");
		  action.clickLink(homePageObjects.lnkElectronics, "Electronics");
		  extentReport.info("Electronics category clicked");
		  action.clickImage(homePageObjects.lnkProducts,"First product");
		  Thread.sleep(3000);
		  double priceOnDetailsPageP1 = Double.parseDouble(homePageObjects.lblProductPrice.getText());
		  action.clickButton(homePageObjects.btnAddToCart,"Add To Cart");
		  extentReport.info("Add to cart button clicked");
		  Utility.waitForElementVisible(driver, homePageObjects.btnGoToCart, 30);
//		  Thread.sleep(3000);
		  driver.get(ConfigFileReader.getUrl());
		  action.clickLink(homePageObjects.lnkKitchenItems, "Kitchen Items");
		  extentReport.info("Kitchen Items category clicked");
		  action.clickImage(homePageObjects.lnkProducts,"First product");
		  Thread.sleep(3000);
		  double priceOnDetailsPageP2 = Double.parseDouble(homePageObjects.lblProductPrice.getText());
		  action.clickButton(homePageObjects.btnAddToCart,"Add To Cart");
		  extentReport.info("Add to cart button clicked");
		  Utility.waitForElementVisible(driver, homePageObjects.btnGoToCart, 30);
		  action.clickLink(homePageObjects.lnkCart,"Cart");
		  extentReport.info("Cart link clicked");
		  action.clickButton(homePageObjects.btnCheckout,"Checkout");
		  extentReport.info("Checkout button clicked");
		  action.clickButton(homePageObjects.btnConfirmPayment,"Confirm Payment");
		  extentReport.info("Confirm Payment button clicked");
		  double cartValue = Double.parseDouble(homePageObjects.lblTotalCartAmount.getText());
		  if(homePageObjects.lblConfirmPayment.isDisplayed() && homePageObjects.lblConfirmPayment.getText().equalsIgnoreCase("Order Confirmed") && cartValue==priceOnDetailsPageP1+priceOnDetailsPageP2)
		  {
			  extentReport.pass("Order placed successfully");
			  extentReport.addScreenshot(driver);
			  Logging.info("Order placed successfully");
			  Logging.endTestCase();    
		  }
		  else
		  {
			  extentReport.fail("Order is not placed");
			  extentReport.addScreenshot(driver);
			  Logging.info("Order is not placed");
			  Logging.endTestCase();
		  }
	  }
	  catch(Exception e)
	  {
		  System.out.println(e.getMessage());
		  extentReport.fail(e.getMessage());
		  extentReport.addScreenshot(driver);
		  Logging.info(e.getMessage());
		  Logging.endTestCase();
		  
	  }	  
  }   
}
