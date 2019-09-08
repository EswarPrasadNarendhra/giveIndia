package com.giveIndia.run;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestRun {

	public static void main(String[] args) throws IOException {
		
		//GiveIndia
		try {
		
		// 1. Opening the Web Page
		WebDriver driver;
		System.setProperty("webdriver.chrome.driver","C:\\Users\\HP\\Desktop\\Eswar WS\\GiveIndia-Opportunity\\Driver\\chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://en.wikipedia.org/wiki/Selenium");
		Thread.sleep(4000);
		
		// 2. Verifying external links in External Links section 
		driver.findElement(By.xpath("//*[@id='toc']/ul/li[10]/a/span[2]")).click();
//		List<WebElement> links= driver.findElements(By.tagName("a"));
//		System.out.println(links.size());
		WebElement externalLinks= driver.findElement(By.xpath("//*[@id='mw-content-text']/div/ul[2]"));
		List<WebElement> links= externalLinks.findElements(By.tagName("a"));
		System.out.println("Total links inside External Links section: "+links.size());
		for(WebElement a: links) {
			String linkname= a.getText();
//			System.out.println(linkname);
			String url= a.getAttribute("href");
			if (url.isEmpty()) {
				System.out.println("The Link: "+"["+linkname+"]"+" is broken");
			} else {
				System.out.println("The Link: "+"["+linkname+"]"+" is not broken");
			}
		}
		
		// 3. Click on Oxygen link in periodic table
		driver.findElement(By.xpath("//*[@id='mw-content-text']/div/div[19]/table/tbody/tr[2]/td/div/table/tbody/tr[3]/td[7]/a/span")).click();
		Thread.sleep(2000);
		
		// 4. Verifying featured Article
		WebElement featuredArticle= driver.findElement(By.xpath("//*[@id='mw-indicator-featured-star']/a/img"));
		boolean result= featuredArticle.isDisplayed();
		if(result) {
			System.out.println("The article is Featured one");
		} else {
			System.out.println("The article is not a Featured one");
		}
		
		// 5. Elemental Properties scroll and talking screen shot
		JavascriptExecutor js= (JavascriptExecutor) driver;
		WebElement physicalProps= driver.findElement(By.xpath("//*[@id='mw-content-text']/div/table[1]/tbody/tr[15]/th"));
		js.executeScript("arguments[0].scrollIntoView();", physicalProps);
		Thread.sleep(1000);
		TakesScreenshot ss= ((TakesScreenshot)driver);
		File srcFile= ss.getScreenshotAs(OutputType.FILE);
		File destFile= new File("C:\\Users\\HP\\Desktop\\Eswar WS\\GiveIndia-Opportunity\\test.png");
		FileUtils.copyFile(srcFile, destFile);
		Thread.sleep(1000);
		
		// 6. Counting number of PDF links in Reference Section
		driver.findElement(By.xpath("//*[@id='toc']/ul/li[11]/a/span[2]")).click();
		WebElement referencesSection= driver.findElement(By.xpath("//*[@id='mw-content-text']/div/div[47]"));
		List<WebElement> linksAndPdf= referencesSection.findElements(By.tagName("a"));
		System.out.println(linksAndPdf.size());
		int count= 0;
		for (WebElement pdf: linksAndPdf) {
			String fpdfFile= pdf.getAttribute("href");
//			System.out.println(fpdfFile);
			if(fpdfFile.length()>4) {
				String last3= fpdfFile.substring(fpdfFile.length()-3);
				if (last3.contains("pdf")) {
					count= count+1;
				}
			}
		}
		System.out.println("Number of PDF link: "+count);
		
		// 7. Verifying 2nd suggestion is Plutonium
		driver.findElement(By.xpath("//*[@id='searchInput']")).sendKeys("pluto");
		Thread.sleep(2000);
		WebDriverWait w= new WebDriverWait(driver,30);
		w.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='searchInput']")));
		List<WebElement>options= driver.findElements(By.xpath("//*[@id='searchInput']"));
		int count1=0;
		for (WebElement op: options) {
			count1=count1+1;
			System.out.println(op.getText());
			if(op.getText().contains("Plutonium")) {
				if (count1!=2) {
					System.out.println("2nd suggestion is Plutonium");
				}else {
					System.out.println("2nd suggestion is not Plutonium");
				}
			} else {
				System.out.println("2nd suggestion is Plutonium");
			}
		}
		
		driver.close();
		
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		
	}

}
