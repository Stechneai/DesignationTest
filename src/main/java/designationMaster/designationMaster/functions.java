package designationMaster.designationMaster;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class functions implements autoConstant {
	public static String timestamp = new SimpleDateFormat("dd-MMM-yyyy_HH-mm-ss").format(new java.util.Date());
	public static ExtentHtmlReporter htmlReporter;

	public String concate=".";

	public static ExtentTest test;
	public static ExtentReports report;


	WebDriver driver;

	//read write data
	public class propertyFile
	{

		public String toReadDataFromPropertyFile(String key) throws FileNotFoundException, IOException
		{
			Properties ps= new Properties();
			ps.load(new FileInputStream(datafile));
			return ps.getProperty(key);
		}

		//fetch the data from Excel.
		public String toReadDataFromExcel(int sheet, int row, int cell) throws FileNotFoundException, IOException
		{
			XSSFWorkbook wb = new XSSFWorkbook(currentDir+excelPath);
			XSSFSheet sh = wb.getSheetAt(sheet);

			//	int noOfRows = sh.getPhysicalNumberOfRows();

			//For Numeric value
			try
			{
				int val = (int) sh.getRow(row).getCell(cell).getNumericCellValue();
				String value = Integer.toString(val);
				return value;
				 
			}
			//For Value value
			catch (Exception e) 
			{
				String value = sh.getRow(row).getCell(cell).getStringCellValue();
				return value;
			}
		}

		public void toWriteDataInExcel(int sheet, int row, int cell,String arg) throws FileNotFoundException, IOException, InterruptedException
		{
			FileOutputStream fos = new FileOutputStream(toReadDataFromPropertyFile("sheet"));
			XSSFWorkbook wb = new XSSFWorkbook(toReadDataFromPropertyFile("sheet")); 
			XSSFSheet sh = wb.getSheetAt(sheet); 
			XSSFCell cellValue=sh.getRow(row).createCell(cell);//row index - 0,column index-2
			cellValue.setCellValue(arg);
			wb.write(fos);
			Thread.sleep(3000);			
		}
	}

	//Utilities
	public class webDriverUtilities
	{
		public void toSelectFromDropDown(WebElement ele, String text)
		{
			Select s=new Select(ele);
			s.selectByVisibleText(text);
			//s.selectByValue(text);
		}

		public void toDeselectAllFromDropDown(WebElement ele)
		{
			Select s=new Select(ele);
			s.deselectAll();
		}

		public void toPerformMouseHoverAction(WebDriver driver, WebElement target)
		{ 
			Actions a =new Actions(driver);
			a.moveToElement(target);
		}

		public void toSwitchToFrame(WebDriver driver, int frameNo)
		{
			driver.switchTo().frame(frameNo);
		}

		public void toSwitchBackToFrame(WebDriver driver)
		{
			driver.switchTo().defaultContent();
		}

		public void toAcceptAlertPopup(WebDriver driver)
		{
			driver.switchTo().alert().accept();
		}

		public void toDismissAlertPopup(WebDriver driver)
		{
			driver.switchTo().alert().dismiss();
		}

		public void toSwitchToTabs(WebDriver driver)
		{
			String parent = driver.getWindowHandle();
			Set<String> child = driver.getWindowHandles();
			child.remove(parent);
			for(String b:child)
			{ 
				driver.switchTo().window(b);
			}
		}

		public void toPerformDoubleClick(WebDriver driver, WebElement target)
		{
			Actions a = new Actions(driver);
			a.doubleClick(target).perform();
		}

		public void toPerformScrolling(WebDriver driver, int x, int y)
		{
			JavascriptExecutor js= (JavascriptExecutor) driver;
			js.executeScript("windows.scrollBy("+x+","+y+")");
		}

		public void toCompareWithString(String actual,String expected) {
			Assert.assertEquals(actual, expected);
		}

		public void toPerformDragDrop(WebDriver driver,WebElement source,WebElement target) {
			Actions a=new Actions(driver);
			a.dragAndDrop(source, target).perform();
		}

	}

	//Screenstot
	public static class screenShot {

		public static void takeScreenShot(WebDriver driver, String className, String SSName) throws IOException
		{
			TakesScreenshot ts=(TakesScreenshot)driver;
			File src=ts.getScreenshotAs(OutputType.FILE);
			File dest=new File(currentDir+photo+"SS_"+className+"_"+SSName+"_"+timestamp+".png");
			Files.copy(src, dest);
		} 

		public static void getAshot(WebDriver driver, String className, String SSName) throws IOException
		{ 
			File fis= new File(currentDir+photo+"Ashot_"+className+"_"+SSName+"_"+timestamp+".png");
			Screenshot sh = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
			ImageIO.write(sh.getImage(), "PNG", fis);

		} 
		
	
		public static String extentScreenShot(WebDriver driver,String className, String ssName) throws IOException
		{
			TakesScreenshot ts=(TakesScreenshot)driver;
			File src=ts.getScreenshotAs(OutputType.FILE);
			String path1=currentDir+photo+"SS_"+className+"_"+ssName+"_"+timestamp+".png";
			File destination=new File(path1);
			Files.copy(src, destination);
			return path1;
		}

		public static String extentAshot(WebDriver driver,String className, String ssName) throws IOException
		{	
			String path2 =currentDir+ photo +"Ashot_"+className+"_"+ssName+"_"+timestamp+".png";
			File fis= new File(path2);
			Screenshot sh = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
			ImageIO.write(sh.getImage(), "PNG", fis);
			return path2;

		} 
	}

	public static class ExtentManager
	{

		public static ExtentReports GetExtent() throws Exception, Exception 
		{

			ExtentReports extent;
			String repName = "Test-Automation-Report_" + timestamp + ".html";
			extent = new ExtentReports();

			htmlReporter = new ExtentHtmlReporter(currentDir+ repoPath + repName);
			//htmlReporter.loadXMLConfig(currentDir+xmlFile);
			htmlReporter.config().setDocumentTitle(docTitle); 
			htmlReporter.config().setReportName(ReportName);
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

			extent.attachReporter(htmlReporter);  //Extend report in HTML format will be generated.

			//extent.addSystemInfo("Environment","Environment Name")
			extent.setSystemInfo("Host Name", "Software Testing Material");
			extent.setSystemInfo("Environment", "Automation Testing");
			extent.setSystemInfo("QA Name", "Amreen");
			extent.setSystemInfo("HostName", "Localhost");
			extent.setSystemInfo("OS", "Windows");
			return extent;
		}

		public static ExtentTest createTest(String authName, String category, String className, String desc)
		{
			try 
			{
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				//String className = this.getClass().getSimpleName();
				report = GetExtent();
				test=report.createTest(className,desc);	
				test.assignAuthor(authName);
				test.assignCategory(category);
				//	return test;
			}catch(Exception e) 
			{
				System.out.println(e.toString());
				//return null;
			}
			return test;
		}
		

		
		
	}
}



