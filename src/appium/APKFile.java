package appium;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class APKFile {

	//Declare the variables
	private static AndroidDriver<MobileElement> driver;
	private static String path = System.getProperty("user.dir");
	
	@BeforeTest
	public void setup() throws Throwable
	{
		//Setting all capabilities as per the need
		DesiredCapabilities dc = DesiredCapabilities.android();
		dc.setCapability("deviceName", "Moto G5+");
		dc.setCapability("platformVersion", "7.0");
		dc.setCapability("platformName", "Android");
		dc.setCapability("app",path+"//App//ApiDemos.apk");
		
		//Initializing driver, getting URl at runtime
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(url,dc);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/*
	 * Method to swipe vertically on the mobile application
	 */
	public void swipeVertical()
	{
		Dimension d = driver.manage().window().getSize();
		int height = d.getHeight();
		int width = d.getWidth();
		int x = (int) (width*0.50);
		int yStart = (int) (height*0.80);
		int yEnd = (int) (height*0.20);
		
		//Swipe vertically
		driver.swipe(x, yStart, x, yEnd, 500);
	}
	
	/*
	 * Method to swipe horizontally on the mobile application
	 */
	public void swipeHorizontal()
	{
		Dimension d = driver.manage().window().getSize();
		int height = d.getHeight();
		int width = d.getWidth();
		int y = (int) (height*0.20);
		int xStart = (int) (width*0.80);
		int xEnd = (int) (width*0.20);
		
		//Swipe horizontally
		driver.swipe(xStart, y, xEnd, y, 500);
	}
	
	@Test
	public void clickElement()
	{
		driver.findElementByAccessibilityId("Views").click();
		for(int i=0;i<=20;i++)
		{
			try {
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					driver.findElementByAccessibilityId("Tabs").click();
					break;
			} catch (Exception e) {
				swipeVertical();
			}
		}
		if(driver.findElementByAccessibilityId("1. Content By Id").isDisplayed())
		{
			driver.findElementByAccessibilityId("5. Scrollable").click();
			for(int i=0;i<=20;i++)
			{
				try {
						driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
						System.out.println("Yes");
						driver.findElementByXPath("//*[@text='TAB 16']").click();
						break;
				} catch (Exception e) {
					swipeHorizontal();
				}
			}
			if(driver.findElementByXPath("//*[@text='Content for tab with tag Tab 16']").isDisplayed())
			{
				System.out.println("Paased, The required element is detected and the test is successfully completed.");
			}
			else
			{
				System.out.println("Failed to find the element, Content for tab 18.");
			}
		}
		else
		{
			System.out.println("Failed to find the element, Content By ID.");
		}
	}
	
	@AfterTest
	public void wrap()
	{
		//Quit the driver
		driver.quit();
	}
}
