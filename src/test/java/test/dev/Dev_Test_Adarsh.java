package test.dev;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import or.user.common.Add_User;
import or.user.common.deviceType;
import or.user.common.Login_Invalid_Uam;
import or.user.common.UAM_Login;
import or.user.common.add_Groups;
import or.user.common.already_RegisterUser;
import or.user.common.create_NewUserPermissionDetail;
import or.user.common.delet_DeviceType;
import or.user.common.register_NewUser;
import or.user.common.update_User;

public class Dev_Test_Adarsh {

	private static final WebElement as = null;
	private static final String front = null;
	private static final long timeOutInSec = 0;
	private WebElement sd;
	private String Front;

	@Test(description = "Scenario, Login a valid credential and verify Dashboard text", groups = { "func only" })
	public void loginMyUser11() {

		UAM_Login ul = new UAM_Login();
		ul.performLogin("Log_Uam");

	}

	@Test(description = "", groups = { "" })

	public void newUserpermission() {
		create_NewUserPermissionDetail cnup = new create_NewUserPermissionDetail();
		cnup.performLogin("Log_Uam");

	}

	@Test(description = "Scenario, verify update user UI", groups = { "Verify only" })

	private void addUserGroup() {

		add_Groups ag = new add_Groups();
		ag.performLogin("Log_Uam");
	}

	@Test(description = "Scenario, invalid login", groups = { "func only" })
	private void login_Invalid() {

		Login_Invalid_Uam liu = new Login_Invalid_Uam();

		liu.performInvalidLogin("Inv_Log");

	}

	@Test(description = "Scenario, verify a mail confirmation msg for new register user", groups = { "func only" })
	public void addNewUser() {

		Add_User au = new Add_User();
		au.add_NewUserPer("Log_Uam", sd);

	}

	@Test(description = "Scenario, verify a user already exist validation email,username", groups = { "func only" })
	public void userAlredyExist() {

		already_RegisterUser aru = new already_RegisterUser();

		aru.verify_AlreadyExistUserVali("Already_Reg");

	}

	@Test(description = "Scenerio, verify update functionality for a user.", groups = { "Func only" })
	public void user_Update() {

		update_User uu = new update_User();
		uu.verify_UserUpdateValidation("Log_Uam", sd);

	}

	@Test(description = "", groups = { "" })
	private void add_DeviceType() {

		deviceType dt = new deviceType();
		dt.add_DeviceType_Verify("Log_Uam");

	}

	@Test(description = "Scenerio, remove Device type and verify", groups = { "Func only" })
	private void remove_DeviceType() {

		delet_DeviceType ddt = new delet_DeviceType();
		ddt.delete_DeviceType_Verify("Log_Uam");
	}

	@Test(description = "Scenerio, Update Device type and verify", groups = { "Func only " })
	private void update_DeviceType() {

		delet_DeviceType ddt = new delet_DeviceType();
		ddt.update("Log_Uam");
	}

	@Test(description = "Scenerio, Update Device type and verify", groups = { "Func only " })
	private void deviceType_Inactive() {

		deviceType dt = new deviceType();
		dt.inActive_DeviceType("Log_Uam");
	}

	@Test(description = "Scenerio, Update Device type and verify", groups = { "Func only " })
	private void verify_ActiveDeviceType() {

		deviceType dt = new deviceType();
		dt.active_DeviceType("Log_Uam");
	}
}
