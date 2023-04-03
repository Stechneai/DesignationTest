package designationMaster.designationMaster;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import designationMasterPageObject.DesignationMasterPage;

public class DesignationMasterTC extends BaseClass {

	DesignationMasterPage dm;
	@Test(priority = 1)
	public void tcLogin() throws FileNotFoundException, IOException, Exception {
		dm = new DesignationMasterPage(driver);
		callURL();
		Thread.sleep(3000);
		dm.setUname().sendKeys(p.toReadDataFromPropertyFile("Uname"));
		Thread.sleep(3000);
		dm.setPwd().sendKeys(p.toReadDataFromPropertyFile("pwd"));
		Thread.sleep(3000);
		dm.signInbtn().click();
		generateScreenShot(driver, "loggedin", "DD-MM-YYYY");
	}

	@Test(priority = 2)
	public void tcmaster() throws InterruptedException {
		dm.selectMaster().click();
		dm.designation().click();
		Thread.sleep(3000);
	}
	
//	@Test(priority = 3)
//	public void tcAdd() throws InterruptedException, IOException {
//		dm.designationAdd().click();
//		Thread.sleep(3000);
//		dm.nameDesignation().sendKeys("senior developer2");
//		dm.remarkEnter().sendKeys("Test");
//		dm.statusDeactive().click();
//		dm.addDesign().click();
//		generateScreenShot(driver, "addDesignation", "DD-MM-YYYY");
//	}
//
//	@Test(priority = 4)
//	public void tcEdit() throws InterruptedException, IOException {
//		dm.editRecord().click();
//		Thread.sleep(3000);
//		dm.nameDesignation().clear();
//		Thread.sleep(3000);
//		dm.nameDesignation().sendKeys("new tester");
//		dm.remarkEnter().sendKeys("Test");
//		dm.statusDeactive().click();
//		dm.addDesign().click();
//		generateScreenShot(driver, "editDesignation", "DD-MM-YYYY");
//	}
//
//	@Test(priority = 5)
//	public void tcduplicate() throws InterruptedException, IOException {
//		try {
//			dm.designationAdd().click();
//			Thread.sleep(3000);
//			dm.nameDesignation().sendKeys("senior developer1");
//			dm.remarkEnter().sendKeys("Test");
//			dm.statusActive().click();
//			dm.updateRecord().click();
//			generateScreenShot(driver, "duplicateAdd", "DD-MM-YYYY");
//		} catch (Exception e) {
//			e.getMessage();
//		}
//	}
//	@Test(priority = 6)
//	public void tcUser() throws InterruptedException, IOException {
//		dm.userClick().click();
//		dm.userAdd().click();
//		Thread.sleep(3000);
//		dm.userdesign().click();
//		generateScreenShot(driver, "designdata", "DD-MM-YYYY");
//	}
//	@Test(priority = 7)
//	public void tcsearch() throws InterruptedException, IOException {
//		dm.searchtext().sendKeys("developer");
//		dm.searchButton().click();
//		generateScreenShot(driver, "search", "DD-MM-YYYY");
//		Thread.sleep(3000);
//		dm.resetbutton().click();
//		
//	}
//	@Test(priority = 8)
//	public void tcMenuManage() throws InterruptedException, IOException {
//		dm.menuManageClick();
//		dm.manageMenuClick();
//		generateScreenShot(driver, "search", "DD-MM-YYYY");
//		Thread.sleep(3000);
//	}
	@Test(priority = 1)
	public void tc2() throws Exception {
		Assert.assertTrue(dm.selectMaster().isDisplayed());
		Assert.assertTrue(dm.selectMaster().isEnabled());
		dm.selectMaster().click();
		Thread.sleep(2000);
		generateAshot(driver, "menu_Present", "DD-MM-YYYY");
	}

	
	@Test(priority = 2)
	public void tc3() throws Exception {
		String s = "Designation Master";
		String s1 = dm.designation().getText();
		Assert.assertEquals(s, s1);
		generateAshot(driver, "Designation MasterTC03", "DD-MM-YYYY");
	}
	
	@Test(priority = 3)
	public void tc4() throws Exception {
		dm.designationAdd().click();
		String s = "Add Designation";
		String s1 = dm.designationAdd().getText();
		Assert.assertEquals(s, s1);
		generateAshot(driver, "Designation MasterTC04", "DD-MM-YYYY");
	}
	
	@Test(priority = 4)
	public void tc6() throws Exception {
		WebElement addButton = driver.findElement(By.xpath("//button[text()='Add']"));
		Assert.assertTrue(addButton.isDisplayed());
		generateAshot(driver, "DispladdBtn", "DD-MM-YYYY");
	}
	@Test(priority = 5)
	public void tc7() throws Exception {
		WebElement CanButton = driver.findElement(By.xpath("//button[text()='Cancel']"));
		Assert.assertTrue(CanButton.isDisplayed());
		generateAshot(driver, "addBtn", "DD-MM-YYYY");
	}
	@Test(priority = 6)
	public void tc8() throws Exception {
		WebElement addButton = driver.findElement(By.xpath("//button[text()='Add']"));
		addButton.click();
		Thread.sleep(1000);
		generateAshot(driver, "MandatoryaddBtn", "DD-MM-YYYY");
	}
	@Test(priority = 7)
	public void tc9() throws Exception {
		WebElement addButton = driver.findElement(By.xpath("//button[text()='Add']"));
		addButton.click();
		Thread.sleep(1000);
		generateAshot(driver, "MandatoryaddBtn", "DD-MM-YYYY");
	}
	@Test(priority = 8)
	public void tc10() throws Exception {
		WebElement addButton = driver.findElement(By.xpath("//button[text()='Add']"));
		String ct = "T@estingx57687hffsfdn cdt";
		dm.nameDesignation().sendKeys(ct);
		System.out.println("CT " + ct);
		Thread.sleep(3000);
		String get = dm.nameDesignation().getText();
		System.out.println("get " + get);
		if (get.matches("[A-Za-z]")) {
			Assert.assertFalse(true);
		} else {
			Assert.assertTrue(true);
			addButton.click();
	}
		generateAshot(driver, "desig_fieldcheck", "DD-MM-YYYY");
	}
	@Test(priority = 8)
	public void tc11() throws Exception {
		WebElement addButton = driver.findElement(By.xpath("//button[text()='Add']"));
		String ct = "T@estingx57687hffsfdn cdt";
		dm.remarkEnter().sendKeys(ct);
		System.out.println("CT " + ct);
		Thread.sleep(3000);
		String get = dm.remarkEnter().getText();
		System.out.println("get " + get);
		if (get.matches("^[A-Za-z0-9]+$")) {
			Assert.assertTrue(true);
		} else {
			Assert.assertFalse(true);
			addButton.click();
	}
		generateAshot(driver, "desig_fieldcheck", "DD-MM-YYYY");
	}
	
	@Test(priority = 8)
	public void tc14() throws Exception {
		dm.cancelBtn().click();
		String s = "Designation Master";
		String s1 = dm.designation().getText();
		Assert.assertEquals(s, s1);
		generateAshot(driver, "tc14", "DD-MM-YYYY");
	}
	@Test(priority = 9)
	public void tc15() throws Exception {
	dm.designationAdd().sendKeys("xyz");
	dm.remarkEnter().sendKeys("abcs123$");
	dm.addDesign();
	generateAshot(driver, "tc15recrdCheck", "DD-MM-YYYY");
	}
	
	@Test(priority = 11)
	public void tc18() throws Exception {
	dm.editRecord().click();
	String s = "Edit Designation";
	String s1 = dm.designation().getText();
	Assert.assertEquals(s, s1);
	generateAshot(driver, "tc18", "DD-MM-YYYY");
	}
	
	@Test(priority = 12)
	public void tc19() throws Exception {
		dm.editRecord().click();
		generateAshot(driver, "tc19recrdCheck", "DD-MM-YYYY");
	}

	@Test(priority = 13)
	public void tc20() throws Exception {
		dm.editRecord().click();
		driver.findElement(By.id("designation")).clear();
		dm.updateRecord().click();
		generateAshot(driver, "tc20recrdupdateCheck", "DD-MM-YYYY");
	}
	
	@Test(priority = 13)
	public void tc21() throws Exception {
		dm.editRecord().click();
		dm.statusDeactive().click();
		dm.updateRecord().click();
		generateAshot(driver, "tc21", "DD-MM-YYYY");
	}

	@Test(priority = 13)
	public void tc22() throws Exception {
		dm.editRecord().click();
		driver.findElement(By.id("designation")).sendKeys("dsgdfddd");
		dm.updateRecord().click();
		generateAshot(driver, "tc22", "DD-MM-YYYY");
	}
	
	@Test(priority = 13)
	public void tc23() throws Exception {
		dm.cancelBtn().click();
		String s = "Designation Master";
		String s1 = dm.designation().getText();
		Assert.assertEquals(s, s1);
		generateAshot(driver, "21", "DD-MM-YYYY");
	}
	
	@Test(priority = 13)
	public void tc24() throws Exception {
		dm.editRecord().click();
		WebElement desg= driver.findElement(By.id("designation"));
		desg.clear();
		desg.sendKeys("testng");
		dm.updateRecord().click();
		generateAshot(driver, "TC24", "DD-MM-YYYY");
	}
	
	@Test(priority = 13)
	public void tc25() throws Exception {
		WebElement srbtn= driver.findElement(By.className("sc-kDTinF itMblB"));
		srbtn.click();
		generateAshot(driver, "TC25", "DD-MM-YYYY");
	}
	
	@Test(priority = 13)
	public void tc26() throws Exception {
		WebElement srbtn= driver.findElement(By.className("sc-kDTinF gZLpRl"));
		srbtn.click();
		generateAshot(driver, "TC25", "DD-MM-YYYY");
	}


}
		
	


