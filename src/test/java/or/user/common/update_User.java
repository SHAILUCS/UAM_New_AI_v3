package or.user.common;

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

	@FindBy(id = "name")
	private WebElement input_Name;

	@FindBy(id = "email")
	private WebElement input_Email;

	@FindBy(id = "phone")
	private WebElement input_Phone;

	@FindBy(name = "address1")
	private WebElement input_Address;

	@FindBy(id = "administrative_area_level_1")
	private WebElement input_City;

	@FindBy(id = "postal_code")
	private WebElement input_postalcode;

	@FindBy(id = "country")
	private WebElement country_Dropdown;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	private WebElement button_Submit;

	@FindBy(id = "Project-cancel_btn")
	private WebElement button_Cancel;

	@FindBy(id = "username")
	private WebElement text_Email;

	@FindBy(id = "password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Login')]")
	public WebElement button_Login;

	// @FindBy(id = "submit_form_login")
	// private WebElement button_Login;

	@FindBy(xpath = "//span[contains(text(),'ACCOUNT DETAILS')]")
	private WebElement button_UserAcc;

	@FindBy(xpath = "//strong[@class='alert_cont']")
	private WebElement validation_UpdateUser;

	private WebPage com = new WebPage();

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
		String baseUrl = dataTable.getValue(14, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void verify_UserUpdateValidation(String usertype) {


		// now we are using Config class to get a file path or sheet name.
		ExcelManager excel = new ExcelManager(Config.getCredentialsFilePath(), Config.getEnvironment());

		com.get(excel.getValue(14, "url"));

		com.waitForElementTobe_Visible(text_Email);

		int rows = excel.getRowCount();

		for (int i = 0; i < rows; i++) {

			// System.out.println(i);

			String deta = excel.getValue(i, "user type");

			if (deta.equals(usertype)) {

				// System.out.println(i);

				String username = excel.getValue(i, "username");
				String password = excel.getValue(i, "password");

				com.sendKeys("email", text_Email, username);
				com.sendKeys("pwd", text_Password, password);
				com.click(button_Login);
				com.click(button_UserAcc);

				Reporter.NODE("Update a user");
				com.clear(input_Name);
				com.sendKeys(input_Name, "Advin");

				com.clear(input_Email);
				com.sendKeys(input_Email, "Adarshh@mailinator.com");

				com.clear(input_Phone);
				com.sendKeys(input_Phone, "323232332");

				com.clear(input_Address);
				com.sendKeys(input_Address, "Austrialia");

				com.clear(input_City);
				com.sendKeys(input_City, "Melbourne");

				com.clear(input_postalcode);
				com.sendKeys(input_postalcode, "3000");

				com.sendKeys(country_Dropdown, "verify address");

				com.click(button_Submit, "verify update profile button");

				com.wait(1);
				com.isElementPresent(validation_UpdateUser, "Successfully update msg");

			}

		}

	}

}
