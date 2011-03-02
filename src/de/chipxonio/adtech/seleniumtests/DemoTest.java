package de.chipxonio.adtech.seleniumtests;

import org.openqa.selenium.By;

import de.chipxonio.adtech.selrunner.tests.TestCase;
import de.chipxonio.adtech.selrunner.tests.TestResult;

public class DemoTest extends TestCase {
	@Override
	public TestResult run() {
		TestResult result = new TestResult();
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
		return result;
	}
}
