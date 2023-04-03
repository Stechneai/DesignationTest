package designationMasterPageObject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DesignationMasterPage {

	public DesignationMasterPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	JavascriptExecutor js;
	WebDriver driver;

	@FindBy(id = "email")
	private WebElement userName;

	public WebElement setUname() {
		return userName;
	}

	@FindBy(id = "password")
	private WebElement pwdName;

	public WebElement setPwd() {
		return pwdName;
	}

	@FindBy(xpath = "//button[text()='SIGN IN']")
	private WebElement signIn;

	public WebElement signInbtn() {
		return signIn;
	}

	@FindBy(xpath = "//span[text()='Master']")
	private WebElement master;

	public WebElement selectMaster() {
		return master;
	}

	@FindBy(xpath = "//span[text()='Designation Master']")
	private WebElement designationMaster;

	public WebElement designation() {
		return designationMaster;
	}

// Add designation
	@FindBy(xpath = "//button[text()='Add Designation']")
	private WebElement addDesignation;

	public WebElement designationAdd() {
		return addDesignation;
	}

	@FindBy(id = "designation")
	private WebElement designationName;

	public WebElement nameDesignation() {
		return designationName;
	}

	@FindBy(id = "remark")
	private WebElement remark;

	public WebElement remarkEnter() {
		return remark;
	}

	@FindBy(id = "is_active_1")
	private WebElement isActive;

	public WebElement statusActive() {
		return isActive;
	}

	@FindBy(id = "is_active_0")
	private WebElement isDeactivate;

	public WebElement statusDeactive() {
		return isDeactivate;
	}

	@FindBy(xpath = "//button[text()='Add']")
	private WebElement add;

	public WebElement addDesign() {
		return add;
	}

	@FindBy(xpath = "//button[text()='Cancel']")
	private WebElement cancel;

	public WebElement cancelBtn() {
		return cancel;
	}

//	Edit
	@FindBy(xpath = "//*[@id=\"cell-1-82\"]/div/button")
	private WebElement edit;

	public WebElement editRecord() {
		return edit;
	}

	@FindBy(xpath = "//button[text()='Update']")
	private WebElement update;

	public WebElement updateRecord() {
		return update;
	}
//	user master
	@FindBy(xpath = "//span[text()='User Master']")
	private WebElement userMaster;

	public WebElement userClick() {
		return userMaster;
	}

	@FindBy(xpath = "//a[text()='Add User']")
	private WebElement addUser;

	public WebElement userAdd() {
		return addUser;
	}
	@FindBy(id = "designation_id")private WebElement dedignIduser;
	public WebElement userdesign() {
		return dedignIduser;
	}
//	search
	@FindBy(xpath = "//input[@placeholder='Search....']")
	private WebElement searchBox;

	public WebElement searchtext() {
		return searchBox;
	}

	@FindBy(xpath = "//button[text()=' Search']")
	private WebElement searchbtn;

	public WebElement searchButton() {
		return searchbtn;
	}

	@FindBy(xpath = "//button[text()=' Reset']")
	private WebElement resetbtn;

	public WebElement resetbutton() {
		return resetbtn;
	}

//	menu management
//	Menu management
	@FindBy(xpath = "//span[text()='Menu Management']")
	private WebElement menuManage;

	public WebElement menuManageClick() {
		return menuManage;
	}

	@FindBy(xpath = "//span[text()='Manage Menu']")
	private WebElement manageMenu;

	public WebElement manageMenuClick() {
		return manageMenu;
	}
}
