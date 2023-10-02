package or.user.common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.reporting.STATUS;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.xl.ExcelManager;

public class func_StubTower {

	public static final String title = "UAM_Manitor";
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

	@FindBy(name = "col_3")
	private WebElement name_Img;

	@FindBy(id = "heading3")
	private WebElement dropdown_Tower;

	@FindBy(xpath = "(//span[contains(@class,'col text-uppercase')][contains(text(),'Display')])[4]")
	private WebElement button_Display;

	@FindBy(id = "AppTitle")
	private WebElement verify_AppTitle;

	@FindBy(xpath = "//h5[contains(text(),'Bookmark - Upper Yarra Dam')]")
	private WebElement verify_MainTitle;

	@FindBy(xpath = "//a[contains(@class,'vcenter')]")
	private WebElement verify_MenuIcon;

	@FindBy(xpath = "//label[contains(text(),'Show ')]")
	private WebElement verify_Entries;

	@FindBy(xpath = "//th[contains(text(),'Id')]")
	private WebElement verifyUI_Id;

	@FindBy(xpath = "//th[contains(text(),'Image Name')]")
	private WebElement verifyUI_ImageName;

	@FindBy(xpath = "//th[contains(text(),'Flag')]")
	private WebElement verify_Flag;

	@FindBy(xpath = "//th[contains(text(),'Date')]")
	private WebElement verifyUI_Date;

	@FindBy(xpath = "//th[contains(text(),'Action')]")
	private WebElement verify_Action;

	@FindBy(xpath = "/html/body/div[5]/img")
	private WebElement clicksdsad_Canvas;;

	@FindBy(id = "DataTables_Table_0_previous")
	private WebElement verify_PreviousPagi;

	@FindBy(xpath = "//a[contains(text(),'Next')]")
	private WebElement verify_NextPagi;

	@FindBy(xpath = "//button[contains(text(),'Starboard')]")
	private WebElement button_Starboard;
	
	@FindBy(xpath="//span[@title='Download']")
	private WebElement button_Download;

	private WebPage com;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public func_StubTower() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public func_StubTower load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void verify_TowerFunc(String usertype) {

		WebPage com = new WebPage();

		// now we are using Config class to get a file path or sheet name.
		ExcelManager excel = new ExcelManager(Config.getCredentialsFilePath(), Config.getEnvironment());

		com.get(excel.getValue(13, "url"));

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
				com.click(dropdown_Tower);
				com.click(button_Display);

				com.waitForElementsTobe_NotVisible(clicksdsad_Canvas, 60);
				com.doubleClick(name_Img);
				com.wait(5);
				
				com.click(button_Download);
				
				com.wait(5);

				

			}

		}

	}

}
