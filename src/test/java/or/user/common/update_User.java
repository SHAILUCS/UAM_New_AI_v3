package or.user.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.reporting.STATUS;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.xl.ExcelManager;

public class update_User {

	public static final String title = "UpdateUser";
	private static final String SHEET_NAME = Config.getEnvironment();

	@FindBy(name = "userFirstName")
	private WebElement input_Name;

	@FindBy(id = "grid-email")
	private WebElement email_InputName;

	@FindBy(name = "userContact")
	private WebElement input_Phone;

	@FindBy(name = "addressLine1")
	private WebElement input_Address;

	@FindBy(name = "addressLine2")
	private WebElement input_Address2;

	@FindBy(name = "city")
	private WebElement input_City;

	@FindBy(name = "postcode")
	private WebElement input_postalcode;

	@FindBy(id = "country")
	private WebElement country_Dropdown;

	@FindBy(xpath = "//button[contains(.,'Save Changes')]")
	private WebElement button_Submit;

	@FindBy(id = "Project-cancel_btn")
	private WebElement button_Cancel;

	@FindBy(id = "username")
	private WebElement text_Email;

	@FindBy(xpath = "//button[contains(.,'User10')]")
	public WebElement user_Name;

	@FindBy(id = "grid-password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Sign In')]")
	public WebElement button_Login;

	@FindBy(id = "sidenav-item-3")
	public WebElement link_User;

	@FindBy(xpath = "//strong[contains(.,'User Updated.')]")
	public WebElement success_Msg;

	@FindBy(xpath = "//strong[@class='alert_cont']")
	private WebElement validation_UpdateUser;

	@FindBy(xpath = "//button[contains(@class,'fixed top-[4rem] 3xl:top-24 mt-3 3xl:mt-0 z-20 ease-in duration-200 inline-block left-[5px] s-R5nWV-p4g4tN')]")
	public WebElement side_MenuBar;

	private WebPage com = new WebPage();
	private WebDriver driver;
	private By d;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
	}

	// Creating default constructor and add access modifier for using class
	public update_User() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public update_User load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO("Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void verify_UserUpdateValidation(String usertype, WebElement we) {

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

				com.click(side_MenuBar);
				com.wait(5);

				com.click(link_User, "Click on user Groups");
				com.wait(5);

				com.click(user_Name, "Update User name");
				com.wait(5);

				com.clear(input_Name);
				com.sendKeys("Name", input_Name, "User11");

				com.clear(input_Phone);
				com.sendKeys("Ph no", input_Phone, "0878986545");

				com.clear(input_Address);
				com.sendKeys("Address1", input_Address, "201 melbourn");

				com.clear(input_Address2);
				com.sendKeys("Address2", input_Address2, "Test");

				com.clear(input_postalcode);
				com.sendKeys("Post code", input_postalcode, "3000");

				com.clear(input_City);
				com.sendKeys("City", input_City, "sydney");

				com.click(button_Submit, "click to save");
				com.wait(5);
				com.isElementPresent(success_Msg, "Success msg");

			}

		}

	}

}
