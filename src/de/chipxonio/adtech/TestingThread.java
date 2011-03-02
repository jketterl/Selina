package de.chipxonio.adtech;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;

import de.chipxonio.adtech.selrunner.browsers.FirefoxBrowser;
import de.chipxonio.adtech.selrunner.hosts.Host;

public class TestingThread extends Thread {
	private Host host;
	private WebDriver driver = null;
	
	public TestingThread(Host host)
	{
		super();
		this.host = host;
	}
	
	public WebDriver getDriver() {
		if (this.driver == null) {
			this.driver = new FirefoxBrowser().getDriver(host);
		}
		return this.driver;
	}

	public void run()
	{
		System.out.println("Starting thread execution");
		try {
			getDriver().get("http://www.chip.de");
			getDriver().findElement(By.xpath("//body/div[@id='header-global']/div[@id='header-wrapper']/div[@id='header-v3']/ul[@id='he-v1-nav']/li[@id='dropDown3']/a[2]")).click();
			for (int i = 1; i <= 5; i++) {
				getDriver().findElement(By.xpath("//div[@id='chip']/div[@class='col478-le']/div[@class='mi-st']/div[@id='dbl_main']/table/tbody/tr[" + i + "]/td/strong/a")).click();
				getDriver().findElement(By.xpath("//div[@id='chip-wrapper']/div[@id='chip']/div[contains(@class, 'col478-le')]/div[@class='dl-faktbox-wrapper']/div[1]/a")).click();
				getDriver().findElement(By.xpath("//div[@id='chip-wrapper']/div[@id='chip']/div[@class='col478-le']/div/div[@class='dl-faktbox-large-wrapper']/div/div[1]/a")).click();
				//for (int k = 1; k <= 3; k++) getDriver().navigate().back();
				//getDriver().executeScript("history.go(-3);");
				getDriver().findElement(By.xpath("//body/div[@id='header-global']/div[@id='header-wrapper']/div[@id='header-v3']/ul[@id='he-v1-nav']/li[@id='dropDown3']/a[2]")).click();
			}
		} catch (WebDriverException e) {
			e.printStackTrace();
			Throwable cause = e.getCause();
			if (cause instanceof ScreenshotException) {
				try {
					File f = new File("/home/jketterl/screenshot.png");
					if (f.exists()) f.delete();
					FileOutputStream file = new FileOutputStream(f);
					file.write(Base64.decodeBase64(((ScreenshotException) cause).getBase64EncodedScreenshot()));
					file.close();
					System.out.println("screenshot has been written");
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("failed writing screenshot");
				}
			} else {
				System.out.println("no screenshot was provided");
			}
		}
		System.out.println("Thread finished");
		getDriver().quit();
	}
}
