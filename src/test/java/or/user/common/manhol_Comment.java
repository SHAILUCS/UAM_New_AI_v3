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

public class manhol_Comment {

	public static final String title = "UAM_Manitor";
	private static final String SHEET_NAME = Config.getEnvironment();
	private static final Actions clickAt = null;

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

	@FindBy(xpath = "//div[contains(text(),'Brooklyn Manhole')]")
	private WebElement dropdown_Manhole;

	@FindBy(xpath = "(//span[contains(@class,'col text-uppercase')][contains(text(),'Display')])[3]")
	private WebElement button_Dispay;

	@FindBy(xpath = "//canvas[@id!='app_right_top_image_grid_CursorLayer']")
	private WebElement click_Canvas;

	@FindBy(xpath = "/html/body/div[5]/img")
	private WebElement clicksdsad_Canvas;

	@FindBy(xpath = "icon ico-drow")
	private WebElement comment_Icon;

	@FindBy(xpath = "//span[@class='icon ico-drow']")
	private WebElement button_Draw;

	private WebPage com;
	private WebDriver driver;
	private By d;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public manhol_Comment() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public manhol_Comment load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO("Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void funct_ManholeComment(String usertype, WebElement we) {

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

				// this method is used wait for a next page and i am also using time for this
				// method.
				com.waitForElementsTobe_NotVisible(clicksdsad_Canvas, 60);
				com.wait(20);

				com.doubleClick(click_Canvas);

				com.wait(4);
				com.click(button_Draw, " Draw button");

				com.wait(5);

			}

		}

	}

}
