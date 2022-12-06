package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageObjects 
{
	
	public HomePageObjects(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
//	@FindBy(xpath="//*[text()='Login']")
	@FindBy(partialLinkText="Login")
	public WebElement lnkLogin;
	
	@FindBy(xpath="//input[@value='Log In']")
	public WebElement btnLogIn;
	
	@FindBy(id="username")
	public WebElement txtUserName;
	
	@FindBy(id="password")
	public WebElement txtUserPass;
	
	@FindBy(xpath="//*[text()='Logout']")
	public WebElement lnkLogout;
	
	@FindBy(partialLinkText = "Electronics")
	public WebElement lnkElectronics;
	
	@FindBy(partialLinkText = "Kitchen Items")
	public WebElement lnkKitchenItems;
	
	@FindBy(partialLinkText = "Sports")
	public WebElement lnkSports;
	
	@FindBy(partialLinkText = " ECommerce")
	public WebElement lnkHome;
	
	@FindBy(xpath = "//input[@placeholder='Search by name...']")
	public WebElement txtSearchProductName;	
	
	@FindBy(xpath = "//div[@class='product-price fw-bold']")
	public WebElement lblProductPrice;
	
	@FindBy(xpath = "//*[@alt='Item Pic']")
	public WebElement lnkProducts;
	
	@FindBy(xpath = "//*[text()='Add To Cart']")
	public WebElement btnAddToCart;
	
	@FindBy(partialLinkText = "Cart")
	public WebElement lnkCart;
	
	@FindBy(xpath = "//*[@Class='product-name']")
	public WebElement lblProductName;
	
	@FindBy(xpath="//*[@class='order-details-product-data ']//div[1]")
	public WebElement lblProductNameCart;
	
	@FindBy(xpath="//*[@class='order-details-product-data ']//div[2]")
	public WebElement lblProductPriceCart;
	
	@FindBy(xpath="//div[text()='Remove']")
	public WebElement btnRemove;
	
	@FindBy(xpath="//div[contains(text(),'Your cart is empty')]")
	public WebElement msgEmptyCart;
	
	@FindBy(partialLinkText="Checkout")
	public WebElement btnCheckout;
	
	@FindBy(xpath="//*[text()='Confirm Payment']")
	public WebElement btnConfirmPayment;
	
	@FindBy(xpath="//div[@class='confirm-payment-success-msg']")
	public WebElement lblConfirmPayment;
	
	@FindBy(xpath="//*[text()='Total']//following::div[1]")
	public WebElement lblTotalCartAmount;
	
	@FindBy(partialLinkText="Go To Cart")
	public WebElement btnGoToCart;
}
