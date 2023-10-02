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

public class create_NewUserPermissionDetail {

	public static final String title = "Loc_Uam";
	private static final String SHEET_NAME = Config.getEnvironment();

	@FindBy(id = "grid-email")
	private WebElement email_InputName;

	@FindBy(id = "grid-password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Sign In')]")
	public WebElement button_Login;

	@FindBy(id = "sidenav-item-2")
	public WebElement text_UserPermission;

	@FindBy(id = "add-new-record-button")
	public WebElement button_AddNewRecord;

	@FindBy(id = "grid-permission-name")
	public WebElement inputText_PermissionName;

	@FindBy(id = "grid-permission-slug")
	public WebElement input_PermissionSlug;

	@FindBy(xpath = "//button[contains(.,'Save Changes')]")
	public WebElement button_SaveChange;

	@FindBy(xpath = "//strong[contains(.,'User Permission Creation.')]")
	public WebElement successMsg_Content;

	private WebPage com;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public create_NewUserPermissionDetail() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public create_NewUserPermissionDetail load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(16, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void performLogin(String usertype) {

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

				com.wait(20);
				com.click(text_UserPermission, "Click on user permission");
				com.wait(10);
				com.click(button_AddNewRecord, "Click Add new record button");
				com.sendKeys(inputText_PermissionName, "adv sasd");
				com.sendKeys(input_PermissionSlug, "saggv dassd");
				com.click(button_SaveChange);
				//com.isElementPresent(successMsg_Content, "success msg");
				com.waitForElementsTobe_Present(successMsg_Content);

			}

		}

	}
}
