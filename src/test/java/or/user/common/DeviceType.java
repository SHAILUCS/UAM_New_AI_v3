package or.user.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import com.xl.ExcelManager;

public class DeviceType {

	public static final String title = "add_DeviceType";
	private static final String SHEET_NAME = Config.getEnvironment();

	@FindBy(id = "grid-email")
	private WebElement email_InputName;

	@FindBy(xpath = "//button[contains(.,'User10')]")
	public WebElement user_Name;

	@FindBy(id = "grid-password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Sign In')]")
	public WebElement button_Login;

	@FindBy(xpath = "//button[contains(@class,'fixed top-[4rem] 3xl:top-24 mt-3 3xl:mt-0 z-20 ease-in duration-200 inline-block left-[5px] s-R5nWV-p4g4tN')]")
	public WebElement side_MenuBar;

	@FindBy(id = "sidenav-item-5")
	public WebElement device_Type;

	@FindBy(xpath = "//button[contains(.,'Add New Record')]")
	public WebElement button_Add_DeviceType;

	@FindBy(id = "grid-group-name")
	public WebElement input_Name;

	@FindBy(id = "grid-group-version")
	public WebElement input_Version;

	@FindBy(xpath = "(//div[contains(@class,'relative w-6/12 mb-4 px-2')])//option[contains(.,'No')]")
	public WebElement dropdown_Prototype;

	@FindBy(xpath = "(//div[contains(@class,'relative w-6/12 mb-4 px-2')])//option[contains(.,'In-Active')]")
	public WebElement dropdown_Status;

	@FindBy(id = "grid-group-description")
	public WebElement box_Description;

	@FindBy(xpath = "//button[contains(.,'Save Changes')]")
	public WebElement button_Submit;
	
	@FindBy(xpath ="//strong[contains(.,'Device Type Creation.')]")
	public WebElement success_Message;

	private WebPage com;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public DeviceType() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public DeviceType load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO("Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void add_DeviceType_Verify(String usertype) {

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

				com.click(side_MenuBar, "side bar icon");
				com.wait(5);

				com.click(device_Type, "Device type option");
				com.wait(5);

				com.click(button_Add_DeviceType, "Add device type button");

				com.sendKeys("Name", input_Name, "Test1");
				com.sendKeys("Version", input_Version, "565");

				com.click(dropdown_Prototype, "prototype dropdown");
				com.click(dropdown_Status, "prototype dropdown");
				com.sendKeys("Description", box_Description, "Test");
				com.click(button_Submit, "Submit Button");
				com.wait(5);
				com.isElementPresent(success_Message);

			}

		}

	}

}
