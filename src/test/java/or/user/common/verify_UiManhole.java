package or.user.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.config.Config;
import com.reporting.Reporter;
import com.selenium.WebPage;
import com.selenium.webdriver.DriverFactory;
import com.xl.ExcelManager;

public class verify_UiManhole {

	public static final String title = "UAM_Manitor";
	private static final String SHEET_NAME = Config.getEnvironment();

	@FindBy(id = "AppTitle")
	private WebElement link_Submapping;

	@FindBy(id = "username")
	private WebElement text_Email;

	@FindBy(id = "password")
	private WebElement text_Password;

	@FindBy(xpath = "//button[contains(text(),'Login')]")
	public WebElement button_Login;

	@FindBy(xpath = "//div[contains(text(),'Brooklyn Manhole')]")
	private WebElement dropdown_Manhole;

	@FindBy(xpath = "(//span[contains(@class,'col text-uppercase')][contains(text(),'Display')])[3]")
	private WebElement button_Dispay;

	@FindBy(xpath = "//span[contains(.,'Side Camera Captures')]")
	private WebElement text_ImageBox;

	@FindBy(xpath = "//a[contains(.,'front')]")
	private WebElement button_Front;

	@FindBy(xpath = "//a[contains(.,'back')]")
	private WebElement button_back;

	@FindBy(xpath = "//a[contains(.,'left')]")
	private WebElement button_Left;

	@FindBy(xpath = "//a[contains(.,'right')]")
	private WebElement button_Right;

	@FindBy(xpath = "//span[contains(.,'Top/Down Camera Captures')]")
	private WebElement text_Img;
	
	@FindBy(xpath = "//a[contains(.,'Cam Video')]")
	private WebElement button_CamVedio;
	
	@FindBy (xpath = "//button[contains(.,'Client Location Information')]")
	private WebElement tab_ClientLocationInfo;
	
	@FindBy(xpath ="//button[contains(.,'Drawing Observations - 15 record(s)')]")
	private WebElement tab_CommentBox;
	
	@FindBy (xpath="(//div[contains(@class,'heading_depth text-uppercase')][contains(text(),'Depth')])")
	private WebElement text_Depth;
	
	@FindBy (xpath="(//div[contains(@class,'heading_depth text-uppercase')][contains(text(),'Temperature')])")
	private WebElement text_Temp;
	
	@FindBy (xpath="(//div[contains(@class,'heading_depth text-uppercase')][contains(text(),'Pressure')])")
	private WebElement text_Pressure;
	
	@FindBy (xpath="//div[contains(@class,'heading_depth text-uppercase')][contains(text(),'PM2.5')]")
	private WebElement text_PM;
	
	@FindBy (xpath="//h5[contains(.,'Brooklyn Manhole')]")
	private WebElement text_TitleHeading;
	
	@FindBy (xpath="//button[contains(.,'Client Location Information')]")
	private WebElement tab_ClientLocationInfo1;
	
	@FindBy (xpath="//a[@class='vcenter']")
	private WebElement menu_RightSideBar;


	private WebPage com;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public verify_UiManhole() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public verify_UiManhole load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void verifyUIManhole(String usertype) {

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
				com.click(dropdown_Manhole);
				com.click(button_Dispay);

				com.isElementPresent(link_Submapping, "link and location name showing on header");
				com.isElementPresent(text_TitleHeading, "Title Heading");
				com.isElementPresent(menu_RightSideBar, "Side menu");
				com.isElementPresent(text_ImageBox, "Text heading is showing on canvas box");
				com.isElementPresent(button_Front, "Front button ");
				com.isElementPresent(button_back, "Back button");
				com.isElementPresent(button_Left, "Left button");
				com.isElementPresent(button_Right, "Right button");
				com.isElementPresent(text_Img, "image Text");
				com.isElementPresent(tab_ClientLocationInfo1, "Client locaion information tab");
				com.isElementPresent(button_CamVedio, "Camera Vedio Button");
				com.isElementPresent(tab_CommentBox, "comment tab is");
				com.isElementPresent(text_Depth, "Depth text");
				com.isElementPresent(text_Temp, "Temperature text");
				com.isElementPresent(text_Pressure, "Text Pressure");
				com.isElementPresent(text_PM, "Text pm");

			}

		}

	}

}
