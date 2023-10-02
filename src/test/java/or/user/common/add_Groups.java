package or.user.common;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.xl.ExcelManager;

public class add_Groups {

	public static final String title = "Loc_Uam";
	private static final String SHEET_NAME = Config.getEnvironment();

	@FindBy(id = "grid-email")
	private WebElement email_InputName;

	@FindBy(id = "grid-password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Sign In')]")
	public WebElement button_Login;

	@FindBy(id = "sidenav-item-1")
	public WebElement text_USerGroups;

	@FindBy(id = "add-new-record-button")
	public WebElement button_AddNewRecord;

	@FindBy(id = "grid-group-name")
	public WebElement inputText_GroupName;

	@FindBy(id = "grid-group-description")
	public WebElement input_Description;

	@FindBy(name = "stageStatus")
	public WebElement dropdownBox;

	@FindBy(xpath = "(//input[contains(@type,'checkbox')][contains(@name,'select-users')])")
	public WebElement checkbox_UserMembers;
	
	@FindBy(xpath="(//input[contains(@type,'checkbox')][contains(@name,'select-permissions')])[1]")
	public WebElement checkbox_AttachedPermissions;

	@FindBy(xpath = "//button[contains(.,'Save Changes')]")
	public WebElement button_SaveChange;

	@FindBy(xpath = "//strong[contains(.,'User Group Creation.')]")
	public WebElement successMsg_Content;
	
	

	private WebPage com;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public add_Groups() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public add_Groups load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(16, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void performLogin(String usertype) {

		WebPage com = new WebPage();

		// now we are using Constant class to get a file path or sheet name.
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

				com.wait(15);
				com.click(text_USerGroups, "Click on user Groups");
				com.wait(15);
				com.click(button_AddNewRecord, "Add new button");
				com.sendKeys(inputText_GroupName, "Test Grop Name");
				com.sendKeys(input_Description, "Test Description");
				com.click(dropdownBox, "Stage Status");
				com.sendKeys(com.switchTo_ActiveElement(), Keys.DOWN, Keys.ENTER);
				com.click(checkbox_UserMembers, "Available active user checkbox");
				com.click(checkbox_AttachedPermissions, "Attaches Permision");
				com.click(button_SaveChange);
				com.wait(5);
				com.isElementPresent(successMsg_Content, "Successfull msg");

			}

		}

	}
}
