package appium;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Calculator {

	//Declare the variable
	static AndroidDriver<MobileElement> driver;
	
	@BeforeTest
	public void setup() throws Throwable
	{
		//**We will need to manually start the Appium server first before run**//
		
		//Setting all capabilities as per the need
		DesiredCapabilities dc = DesiredCapabilities.android();
		dc.setCapability(CapabilityType.BROWSER_NAME, "");
		dc.setCapability("deviceName", "Moto G5+");
		dc.setCapability("platformVersion", "7.0");
		dc.setCapability("platformName", "Android");
		
		dc.setCapability("appPackage", "com.android.calculator2");
		dc.setCapability("appActivity", "com.android.calculator2.Calculator");
		
		URL url = new URL("http://0.0.0.0:4723/wd/hub");
		
		//Initializing driver
		driver = new AndroidDriver<MobileElement>(url,dc);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/*
	 * Method to swipe horizontally on the mobile application
	 */
	public void swipeHorizontal()
	{
		Dimension d = driver.manage().window().getSize();
		int height = d.getHeight();
		int width = d.getWidth();
		
		int y = (int) (height*0.50);
		int xStart = (int) (width*0.10);
		int xEnd = (int) (width*0.70);
		
		driver.swipe(xStart, y, xEnd, y, 500);
	}
	
	@Test
	public void calculation()
	{
		//Steps of calculation
		swipeHorizontal();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElementById("com.google.android.calculator:id/digit_3").click();
		driver.findElementById("com.google.android.calculator:id/op_add").click();
		driver.findElementById("com.google.android.calculator:id/digit_2").click();
		driver.findElementById("com.google.android.calculator:id/eq").click();
		
		//Result extraction
		String result = driver.findElementById("com.google.android.calculator:id/result").getText();
		
		//Reporting of the results
		if(result.equals("5"))
		{
			System.out.println("Passed");
		}
		else
		{
			System.out.println("Failed");
		}
	}
	
	@AfterTest
	public void wrap()
	{
		//Quit the driver and exit test
		driver.quit();
	}
}
