package or.user.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.selenium.ReactTable;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.xl.ExcelManager;

import or.common.UamCommon;

public class already_RegisterUser {

	public static final String title = "Already_Reg";
	private static final String SHEET_NAME = Config.getEnvironment();

	@FindBy(id = "//a[contains(.,'Register')]")
	private WebElement input_Register;

	@FindBy(xpath = "//h6[contains(.,'Sign up with your credentails and basic info.')]")
	private WebElement text_Heading;

	@FindBy(id = "grid-fullname")
	private WebElement input_Fullname;

	@FindBy(id = "grid-email")
	private WebElement input_Email;

	@FindBy(xpath = "//p[contains(.,'Email address already in use.')]")
	private WebElement validation_AlreadyEmail;

	@FindBy(id = "grid-password")
	private WebElement input_Pass;

	@FindBy(id = "grid-confirm-password")
	private WebElement input_RePass;

	@FindBy(id = "grid-addressLine1")
	private WebElement input_Address;

	@FindBy(id = "grid-addressLine2")
	private WebElement input_Address2;

	@FindBy(id = "grid-postcode")
	private WebElement input_postalcode;

	@FindBy(id = "grid-city")
	private WebElement grid_City;

	@FindBy(xpath = "//button[contains(.,'Create Account')]")
	private WebElement button_Submit;

	@FindBy(id = "customCheckLogin")
	private WebElement checkBox;

	private WebPage com;
	ReactTable rt;
	private UamCommon comm;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public already_RegisterUser() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public already_RegisterUser load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(17, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void verify_AlreadyExistUserVali(String usertype) {

		WebPage com = new WebPage();

		// now we are using Config class to get a file path or sheet name.
		ExcelManager excel = new ExcelManager(Config.getCredentialsFilePath(), Config.getEnvironment());

		com.get(excel.getValue(17, "url"));

		int rows = excel.getRowCount();

		for (int i = 0; i < rows; i++) {

			// System.out.println(i);

			String deta = excel.getValue(i, "user type");

			if (deta.equals(usertype)) {

				com.wait(5);
				com.sendKeys(input_Fullname, "Adarsh");
				com.sendKeys(input_Email, "adarsh@uamtec.com");
				com.wait(5);
				com.isElementPresent(validation_AlreadyEmail, "already exist email validation");

			}

		}

	}

}
