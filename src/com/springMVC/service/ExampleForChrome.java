package com.springMVC.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
public class ExampleForChrome {
	
	
	public static void main(String[] args) throws Exception {
		
		testGTA();
	}
	
	public static void testGTA() throws Exception {
        // 设置 chrome 的路径
        System.setProperty("webdriver.chrome.driver",
                "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome
        @SuppressWarnings("deprecation")
        ChromeDriverService service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File("E:/software/chromedriver_win32/chromedriver.exe"))
        .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        WebDriver driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        // 让浏览器访问 Baidu
        driver.get("http://192.168.1.236/memberportal/#/user/login");
        // 获取 网页的 title
        System.out.println(" Page title is: " + driver.getTitle());
        
        Thread.currentThread().sleep(1500);
//        WebDriverWait wait = new WebDriverWait(driver, 10000);
        driver.findElement(By.xpath("//*[@id=\"user\"]/section/div/form/div[2]/input")).sendKeys("0000034");
        driver.findElement(By.xpath("//*[@id=\"user\"]/section/div/form/div[3]/input")).sendKeys("a1234567");
        driver.findElement(By.xpath("//*[@id=\"user\"]/section/div/form/div[6]/div[2]/button")).click();

        Thread.currentThread().sleep(1000);
        driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[1]/ul/li[3]/a")).click();
        Thread.currentThread().sleep(1000);
        driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/ul/li[4]/div/a")).click();
        Thread.currentThread().sleep(1000);
        driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/div/ul/li[1]/div/a")).click();
//        Thread.currentThread().sleep(3000);
//        WebElement selector=driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/div[1]/div/div/ul/li/select"));
//        Select select = new Select(selector);
//        select.selectByIndex(1);
        
        //click RESERVE
        Thread.currentThread().sleep(3500);
        driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/div[3]/div/div[2]/ul/li[2]/button")).click();
        //select date
        Thread.currentThread().sleep(2000);
        WebElement de=driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div[2]/section/div[1]/div/div/ul/li[4]/span/div/input"));
        de.sendKeys("Mar 24,2016");
        System.out.println(de.toString()+" date "+de.getText());
       
        //select THERAPIST
        Thread.currentThread().sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/div[2]/div[1]/ul/li[2]/input[3]")).click();
        Thread.currentThread().sleep(2000);
        driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/div[2]/div[2]/table/tbody/tr[1]/td[2]/ul/li[1]/img")).click();
        
        
        Thread.currentThread().sleep(2000);
//        WebElement e= driver.findElement(By.className("deep-green-bg.timeslots-js.border-left"));  //deep-green-bg timeslots-js border-left 
      List<WebElement> allInputs = driver.findElements(By.tagName("a"));  
      System.out.println(allInputs.size()+ " eallInputs "+allInputs.size());
      int i=0;
      for(WebElement e: allInputs){
            if (e.getAttribute("class").equals("deep-green-bg timeslots-js border-left")){          	  
          	  e.click();
          	  System.out.println(e.getAttribute("class")+ " e "+e.getTagName());  //打印出每个文本框里的值
          	  break;
            }
      }
        //click BOOK
//      Thread.currentThread().sleep(1000);
      driver.findElement(By.xpath("/html/body/div/div[3]/div/div/div[2]/section/div[2]/div[4]/div/button")).click();
      
        // 关闭浏览器
//        driver.quit();
//        // 关闭 ChromeDriver 接口
//        service.stop();
    }
	
	
    public static void testBaidu() throws Exception {
        // 设置 chrome 的路径
        System.setProperty("webdriver.chrome.driver",
                "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe");
        // 创建一个 ChromeDriver 的接口，用于连接 Chrome
        @SuppressWarnings("deprecation")
        ChromeDriverService service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File("E:/software/chromedriver_win32/chromedriver.exe"))
        .usingAnyFreePort().build();
        service.start();
        // 创建一个 Chrome 的浏览器实例
        WebDriver driver = new RemoteWebDriver(service.getUrl(), DesiredCapabilities.chrome());
        // 让浏览器访问 Baidu
        driver.get("http://mail.163.com/");
        // 获取 网页的 title
        System.out.println(" Page title is: " + driver.getTitle());
        // 通过 id 找到 input 的 DOM
//        WebElement element = driver.findElement(By.id("kw"));
//        // 输入关键字
//        element.sendKeys("zTree");
//        // 提交 input 所在的 form
//        element.submit();
        
        driver.findElement(By.id("idInput")).clear();
        driver.findElement(By.id("idInput")).sendKeys("szuqsg@163.com");

        driver.findElement(By.id("pwdInput")).clear();
        driver.findElement(By.id("pwdInput")).sendKeys("abc");

        driver.findElement(By.id("loginBtn")).click();
        // 通过判断 title 内容等待搜索页面加载完毕，间隔秒
        WebDriverWait wait = new WebDriverWait(driver, 2000);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kw")));
        
//        (new WebDriverWait(driver, 10)).until(new ExpectedCondition() {
////        	@Override
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().startsWith("ztree");
//            }
//        });
        driver.findElement(By.id("_mail_component_142_142")).click();
        
        // 显示搜索结果页面的 title
        System.out.println(" Page title is: " + driver.getTitle());
        // 关闭浏览器
//        driver.quit();
//        // 关闭 ChromeDriver 接口
//        service.stop();
    }


//    List<WebElement> allInputs = driver.findElements(By.tagName("input"));
//    List<WebElement> textallInputs = driver.findElements(By.tagName("button"));    
//    System.out.println(allInputs.size()+ " eallInputs "+textallInputs.size());
//    int i=0;
//    for(WebElement e: allInputs){
//          if (e.getAttribute("class").equals("rightWidth1 ng-pristine ng-untouched ng-valid")){
//        	  if(i==0){
//        		  e.sendKeys("0000034");
//        	  }else if(i==1){
//        		  e.sendKeys("a1234567");
//        	  }
//        	  i++;
//        	  System.out.println(e.getAttribute("class")+ " e "+e.getTagName());  //打印出每个文本框里的值
//          }
//    }
//    
//    int j=0;
//    for(WebElement e2: textallInputs){
//    	 if (e2.getAttribute("class").equals("btn mid sm-yellow font-size-14 font-constantia btnLogin")){
//    		 System.out.println(e2.getAttribute("class")+ " e2 "+e2.getClass());  //打印出每个文本框里的值
//    		 e2.click();
//    	 }
//}
    
    //how to use By.tagName, id, name
//    http://www.cnblogs.com/qingchunjun/p/4208159.html
    //how to get element info in the page
//    http://www.cnblogs.com/tobecrazy/p/4570494.html
    //java to selenium
//    http://blog.sina.com.cn/s/blog_62ea758a0101ltub.html
    
}

