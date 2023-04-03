package designationMaster.designationMaster;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class BaseClass extends functions  {
	ChromeOptions ops = new ChromeOptions();
	public static String timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());
	//protected String currentDir = System.getProperty("user.dir");
	//public static String timestamp = String.valueOf(System.currentTimeMillis());

	public functions c = new functions();

	public functions.propertyFile p = c.new propertyFile();
	public functions.webDriverUtilities utilities = c.new webDriverUtilities();
	public loginLogout ll = new loginLogout();
	//	public functions.screenShot ss = c.new screenShot();

	static public WebDriver driver;
	String className = this.getClass().getSimpleName();

	
	@BeforeTest
	@org.testng.annotations.Parameters("browser")
	public void setup(@Optional("chrome") String browser) throws Exception, IllegalStateException
	{	
		ops.addArguments("--remote-allow-origins=*");
		timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());
		report = ExtentManager.GetExtent();
		//		String methodName = Thread.currentThread().getStackTrace()[0].getMethodName();
		test=report.createTest("Setup");	
		if(browser.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(ops);
		//	test.log(Status.INFO, "Chrome browser launched");
			String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp); 
			test.info("Chrome browser launched", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());

		} 

		else if(browser.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		//	test.log(Status.INFO, "Firefox browser launched"); 
			String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp); 
			test.info("Firefox browser launched", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
		}

		else if(browser.equalsIgnoreCase("Edge")){
			//set path to Edge.exe
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			//test.log(Status.INFO, "EdgeDriver browser launched");
			String screenshotpath=concate+concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp); 
			test.info("EdgeDriver browser launched", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
		}

		else
		{
			throw new Exception("Browser is not correct");
		}

		WebDriverManager.chromedriver().setup();
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS); 

	}

	@BeforeClass
	public void open() throws Exception
	{
		test=report.createTest(className);	
		test.assignAuthor(p.toReadDataFromPropertyFile("AuthorName"));
		test.assignCategory(p.toReadDataFromPropertyFile("category"));
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException, Exception 
	{
		Thread.sleep(3000); 
		timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());

		if (result.getStatus() == ITestResult.FAILURE) 
		{
			test.log(Status.FAIL, "CASE FAILED IS " + result.getName()); // to add name in extent report
		//	test.log(Status.FAIL, " " + result.getThrowable().getMessage()+"1"); // to add error/exception in extent report
			test.fail(new RuntimeException(result.getThrowable().getMessage()));
			String screenshotpath=concate+generateAshot(driver, this.getClass().getSimpleName(),timestamp); 
			test.fail(result.getThrowable().getMessage()+"", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
		} 

		else if (result.getStatus() == ITestResult.SKIP)
		{
			//test.skip(new RuntimeException(result.getThrowable().getMessage()));
			test.log(Status.SKIP, "CASE SKIPPED IS " + result.getName());
			String screenshotpath=concate+generateAshot(driver, this.getClass().getSimpleName(),timestamp);

			test.skip(new RuntimeException(result.getThrowable().getMessage())+"", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
		}

		else if (result.getStatus() == ITestResult.SUCCESS) 
		{
			//test.log(Status.PASS, "CASE PASSED IS " + result.getName());
			String screenshotpath=concate+generateAshot(driver, this.getClass().getSimpleName(),timestamp);
			test.pass("CASE PASSED IS " + result.getName(), MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
		} 
	}

	@AfterSuite
	public void cleanUp()
	{
		report.flush();
	}

	//	@AfterTest
	//	public void closeBrowser() throws Exception 
	//	{
	//		//	ll.logoutPage(driver);
	//		report.flush();
	//		driver.close();
	//
	//	}

	public void callURL(String url) throws FileNotFoundException, IOException, Exception
	{
		driver.get(url);
		test.info("Working On : "+url+" URL.");
		Thread.sleep(3000); 
	}

	public void callURL() throws FileNotFoundException, IOException, Exception
	{
		String url=p.toReadDataFromPropertyFile("URL");
		driver.get(url);
		test.info("Working On : "+url+" URL.");
		Thread.sleep(3000); 
	}

	public void onTestSuccess(String msg) throws IOException 
	{
		timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());
		//test.log(Status.PASS, msg);
		test.log(Status.PASS, MarkupHelper.createLabel(msg, ExtentColor.GREEN));
		//String a = screenShot.extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
		String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

		test.pass("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());	
	}

	
	public void generateLog(String msg) throws IOException 
	{
		//test.log(Status.PASS, msg);
		test.log(Status.INFO,msg);
	}

	public void generateLogWithSS(String msg) throws IOException 
	{
		//test.log(Status.INFO,msg);
		String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

		test.info(msg, MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());

	}

	public void generateLogWithAshot(String msg) throws IOException 
	{
		//test.log(Status.INFO,msg);
		String screenshotpath=concate+generateAshot(driver, this.getClass().getSimpleName(),timestamp);

		test.info(msg, MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());

	}
	
	public void onTestFailure(String msg) throws IOException 
	{ 
		timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());
		//test.log(Status.FAIL, msg);
		test.log(Status.FAIL, MarkupHelper.createLabel(msg, ExtentColor.RED));

		//String a = screenShot.extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
		String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

		test.fail("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
	}

	public void onTestSkipped(String msg) throws IOException
	{	
		timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());
		//test.log(Status.SKIP, msg);
		//	test.log(Status.SKIP, tr.getThrowable());		
		test.log(Status.SKIP, MarkupHelper.createLabel(msg, ExtentColor.YELLOW));
		//	String a = screenShot.extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
		String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);
		test.skip("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
	}  

	public void createTest(String msg) throws IOException
	{	
		String msg1 = "<b>"+msg+"</b>";
		test.log(Status.INFO, MarkupHelper.createLabel(msg1, ExtentColor.INDIGO));
	}

	public class loginLogout 
	{
		public void loginPage(WebDriver driver, String uname, String password) throws InterruptedException, Exception
		{		
			timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());

			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			test=report.createTest(className + " :: "+methodName);	

			WebElement username = driver.findElement(By.id("email"));
			username.clear();
			Thread.sleep(1000);
			username.sendKeys(uname);
			Thread.sleep(1000);

			WebElement pwd = driver.findElement(By.id("password"));
			pwd.clear();
			Thread.sleep(1000);

			pwd.sendKeys(password);
			Thread.sleep(1000);

			//	test.info("", MediaEntityBuilder.createScreenCaptureFromBase64String(screenShot.extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName(),timestamp)).build());

			driver.findElement(By.id("login")).click();

			try 
			{

				String msg = driver.findElement(By.xpath("//div[text()='Username does not match !!!']")).getText();
				test.fail(msg);
				test.fail("Uname : " +uname+" Password : "+password);

				String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

				test.fail("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());

				//onTestFailure("Uname : " +uname+ " Password : "+password+ " : "+msg);
			}
			catch (Exception e) 
			{ 
				//	onTestSuccess("Uname : " +uname+ " Password : "+password);
				test.pass("Uname : " +uname+ " Password : "+password);
				//String passSS = extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
				String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

				test.pass("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());

				onTestSuccess("Logged in successfully");
			}
		}

		public void loginPage(WebDriver driver) throws InterruptedException, Exception, IOException
		{
			timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss-SSS").format(new java.util.Date());
			WebElement username = driver.findElement(By.id("email"));

			username.clear();
			Thread.sleep(1000);

			username.sendKeys(p.toReadDataFromPropertyFile("Uname"));
			Thread.sleep(3000);

			WebElement pwd = driver.findElement(By.id("password"));
			pwd.clear();
			Thread.sleep(1000);

			pwd.sendKeys(p.toReadDataFromPropertyFile("pwd"));
			Thread.sleep(3000);

			test.info("Uname : " +p.toReadDataFromPropertyFile("Uname")+ " Password : "+p.toReadDataFromPropertyFile("pwd"));
			//	String infoSS = extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
			String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

			test.info("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());
			driver.findElement(By.xpath("//button[text()='SIGN IN']")).click();
			Thread.sleep(3000);

			onTestSuccess("Logged in successfully");
		}
		public void logoutPage(WebDriver driver) throws InterruptedException, Exception
		{
			driver.findElement(By.xpath("(//img[@src='http://15.207.120.175/NewTicketService/storage/app/Profile/Ashot_dailyStockAdd_dateBranchStock_2022_03_02_06_34_50.jpg'])[1]")).click();
			Thread.sleep(3000); 

			driver.findElement(By.xpath("//button[text()='Signout']")).click();
			Thread.sleep(3000); 
			//String infoSS = extentScreenShot(driver,this.getClass().getSimpleName(),Thread.currentThread().getStackTrace()[1].getMethodName());
			String screenshotpath=concate+generateScreenShot(driver, this.getClass().getSimpleName(),timestamp);

			test.info("", MediaEntityBuilder.createScreenCaptureFromPath(screenshotpath).build());

			onTestSuccess("Logged Out successfully");

		} 

	}
	public static String generateScreenShot(WebDriver driver, String screenshotname,String timestamp) throws IOException 
	{

		TakesScreenshot ts=(TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination=photo+"SS_"+screenshotname+timestamp+".png";

		File finaldestination=new File(destination);

		FileUtils.copyFile(source, finaldestination);
		return destination;
	} 
	
	public static String generateAshot(WebDriver driver, String screenshotname,String timestamp) throws IOException 
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String destination=photo+"SS_"+screenshotname+timestamp+".png";
		
		File fis= new File(destination);
		Screenshot sh = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
		ImageIO.write(sh.getImage(), "PNG", fis);
		return destination;

	}

}