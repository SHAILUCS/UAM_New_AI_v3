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

public class verify_CommentUiDam {

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

	// @FindBy(id = "submit_form_login")
	// private WebElement button_Login;

	@FindBy(xpath = "//span[contains(text(),'ACCOUNT DETAILS')]")
	private WebElement button_UserAcc;

	@FindBy(xpath = "//strong[@class='alert_cont']")
	private WebElement validation_UpdateUser;

	@FindBy(xpath = "//div[contains(text(),'Upper Yarra Dam')]")
	private WebElement dropdown_Dam;

	@FindBy(xpath = "(//span[contains(@class,'col text-uppercase')][contains(text(),'Comment')])[1]")
	private WebElement click_CommentTab;

	@FindBy(id = "right_top_image_view")
	private WebElement click_Image;

	@FindBy(xpath = "//span[contains(@class,'icon ico-drow')]")
	private WebElement icon_Comment;

	@FindBy(id = "manholeImageViewCanvas_CursorLayer")
	private WebElement click_ImageComment;

	@FindBy(id = "note-name")
	private WebElement input_ObservationTitle;

	@FindBy(id = "note-desc")
	private WebElement input_Description;

	@FindBy(id = "note_save")
	private WebElement button_Save;

	@FindBy(xpath = "//button[contains(text(),'Close')]")
	private WebElement button_Close;

	@FindBy(xpath = "//button[contains(text(),'Drawing Observations')]")
	private WebElement verify_CommentBox;

	@FindBy(xpath = "//div[contains(@class,'col-lg-12 d-flex justify-content-between align-items-center')]")
	private WebElement verify_heading;

	@FindBy(xpath = "//th[contains(text(),'Id')]")
	private WebElement verifyUI_Id;

	@FindBy(xpath = "//th[contains(text(),'Image Name')]")
	private WebElement verifyUI_ImageName;

	@FindBy(xpath = "//th[contains(text(),'Count')]")
	private WebElement verifyUI_Count;

	@FindBy(xpath = "//th[contains(text(),'Comment')]")
	private WebElement verifyUI_Comment;

	@FindBy(xpath = "//th[contains(text(),'Date')]")
	private WebElement verifyUI_Date;

	@FindBy(xpath = "//th[contains(text(),'Action')]")
	private WebElement verifyUI_Action;

	@FindBy(id = "DataTables_Table_0_length")
	private WebElement verifyUI_Entry;

	@FindBy(id = "DataTables_Table_0_filter")
	private WebElement verifyUI_Search;

	@FindBy(id = "DataTables_Table_0_previous")
	private WebElement verify_PreviousPagi;

	@FindBy(id = "DataTables_Table_0_next")
	private WebElement verify_NextPagi;

	private WebPage com;

	public void LoginUSer() {
		PageFactory.initElements(DriverFactory.getDriver(), this);
		WebPage com = new WebPage();
	}

	// Creating default constructor and add access modifier for using class
	public verify_CommentUiDam() {

		// 'this' is for pointing a current class object and null pointer exception are
		// showing when we are not using this Page factory line.
		PageFactory.initElements(DriverFactory.getDriver(), this);

	}

	public verify_CommentUiDam load_App_URL() {

		Reporter.NODE("Loading " + title + " URL");

		ExcelManager dataTable = new ExcelManager(Config.getCredentialsFilePath(), SHEET_NAME);
		String baseUrl = dataTable.getValue(13, "url");
		com.get(baseUrl);

		Reporter.INFO( "Fired up url: " + "<br/><b style='font-size: small;'>" + baseUrl + "</b>");

		return this;
	}

	public void verifyCommentManitor(String usertype) {

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
				com.click(dropdown_Dam);
				com.click(click_CommentTab);

				com.isElementPresent(verify_heading,
						"Comment heading- project dashboard link and location name showing on header");
				com.isElementPresent(verifyUI_Id, "verify Id on table");
				com.isElementPresent(verifyUI_ImageName, "Image name data");
				com.isElementPresent(verifyUI_Count, "count tab is");
				com.isElementPresent(verifyUI_Comment, "Comment tab");
				com.isElementPresent(verifyUI_Date, "Date tab");
				com.isElementPresent(verifyUI_Action, "Action view,comment,download box");
				com.isElementPresent(verifyUI_Entry, "Entries tab is");
				com.isElementPresent(verifyUI_Search, "Search input box");
				com.isElementPresent(verify_PreviousPagi, "Previous pagination");
				com.isElementPresent(verify_NextPagi, "next pagination");
				
			

			}

		}

	}

}
