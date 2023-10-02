package or.user.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.xl.ExcelManager;

public class Add_User {

	public static final String title = "UAM_Manitor";
	private static final String SHEET_NAME = Config.getEnvironment();
	private static final Actions clickAt = null;

	@FindBy(id = "grid-email")
	private WebElement email_InputName;

	@FindBy(id = "grid-password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Sign In')]")
	public WebElement button_Login;

	@FindBy(id = "sidenav-item-3")
	public WebElement text_User;

	@FindBy(id = "add-new-record-button")
	public WebElement button_AddNewRecord;

	@FindBy(id = "grid-group-name")
	public WebElement inputText_UserName;

	@FindBy(name = "userEmailAddress")
	public WebElement input_Email;

	@FindBy(name = "userContact")
	public WebElement input_Contact;

	@FindBy(name = "password")
	public WebElement input_Pass;

	@FindBy(name = "confirmPassword")
	public WebElement input_ConfirmPass;

	@FindBy(name = "addressLine1")
	public WebElement input_Address;

	@FindBy(name = "addressLine2")
	public WebElement input_Address2;

	@FindBy(name = "postcode")
	public WebElement input_Postcode;

	@FindBy(name = "city")
	public WebElement input_City;

	@FindBy(name = "(//input[contains(@type,'checkbox')][contains(@name,'select-roles')])[1]")
	public WebElement input_Checkbox;

	@FindBy(name = "//h3[contains(.,'New User')]")
	public WebElement text_UserForm;

	@FindBy(xpath = "//button[contains(.,'Save Changes')]")
	public WebElement button_SaveChange;

	@FindBy(xpath = "//strong[contains(.,'User Group Creation.')]")
	public WebElement successMsg_Content;

	@FindBy(xpath = "//h3[contains(.,'Available Roles')]")
	public WebElement verify_Text;

	@FindBy(xpath = "//h5[contains(.,'Client Assets')]")
	public WebElement verify_CheckBoxText;

	private WebPage com = new WebPage();
	private WebDriver driver;
	private By d;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	// Creating default constructor and add access modifier for using class
	public Add_User() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public Add_User load_App_URL() {
		
		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void add_User(String usertype, WebElement we) {

		WebPage com = new WebPage();

		// now we are using Config class to get a file path or sheet name.
		ExcelManager excel = new ExcelManager(Config.getCredentialsFilePath(), Config.getEnvironment());

		com.get(excel.getValue(16, "url"));

		com.waitForElementTobe_Visible(email_InputName);

		int rows = excel.getRowCount();

		for (int i = 0; i < rows; i++) {

			// System.out.println(i);

			String deta = excel.getValue(i, "user type");

			if (deta.equals(usertype)) {

				// System.out.println(i);

				String username = excel.getValue(i, "username");
				String password = excel.getValue(i, "password");
				com.wait(10);

				com.sendKeys("email", email_InputName, username);
				com.sendKeys("pwd", text_Password, password);
				com.click(button_Login);

				com.wait(10);
				com.click(text_User, "Click on user Groups");
				com.wait(10);
				com.click(button_AddNewRecord, "Add new button");

				com.waitForElementsTobe_Present(text_UserForm, 10);
				com.sendKeys(inputText_UserName, "Test");
				com.sendKeys(input_Email, "Test@gmail.com");
				com.sendKeys(input_Contact, "0812234565");
				com.sendKeys(input_Pass, "Apol@1993");
				com.sendKeys(input_ConfirmPass, "Apol@1993");
				com.sendKeys(input_Address, "Melbourne");
				com.sendKeys(input_Address2, "Melbourne");
				com.sendKeys(input_Postcode, "2300");

				com.sendKeys(input_City, "2300");

				com.isElementPresent(verify_Text);

				com.isElementPresent(verify_CheckBoxText);
				
				com.wait(5);

				com.click(input_Checkbox);
				

				// com.click(button_SaveChange);
				// com.wait(5);
				// com.isElementPresent(successMsg_Content, "Successfull msg");

			}

		}

	}

}
