package com.seleniummaster.cubecart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FunctionLibrary {
    private WebDriver driver;

    //open browser function and navigate to the testing site
    public void openBrowser(String siteName) {
        System.setProperty("webdriver.chrome.driver", "c:\\webdriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get(siteName);
    }

    public void Login(String userName, String userPassword) {
        WebElement usernameTextBox = driver.findElement(By.name("username"));
        usernameTextBox.sendKeys(userName);
        WebElement passwordTextBox = driver.findElement(By.id("password"));
        passwordTextBox.sendKeys(userPassword);
        WebElement loginButton = driver.findElement(By.id("login"));
        loginButton.click();
    }

    public String addNewInventoryCategory(String categoryName, String parentCategory) {
        WebElement categoriesLink = wait(driver.findElement(By.id("nav_categories")));
        categoriesLink.click();
        WebElement addCategoryTab = wait(driver.findElement(By.linkText("Add Category")));
        addCategoryTab.click();
        WebElement statusCheckbox = wait(driver.findElement(By.cssSelector("div>span>img[class='checkbox']")));
        statusCheckbox.click();
        WebElement visibleCheckbox = wait(driver.findElement(By.cssSelector("div>span>img[rel='#visible']")));
        visibleCheckbox.click();
        WebElement categoryNameTextBox = wait(driver.findElement(By.xpath("//input[@id='name']")));
        categoryNameTextBox.sendKeys(categoryName);
        WebElement parentCategoryList = wait(driver.findElement(By.xpath("//select[@id='parent']")));
        Select categorySelect = new Select(parentCategoryList);
        categorySelect.selectByVisibleText(parentCategory);
        WebElement saveButton = wait(driver.findElement(By.id("cat_save")));
        saveButton.click();
        return categoryName;
    }

    public boolean verifyCategoryName(String categoryName) {
        WebElement successMessageElement = wait(driver.findElement(By.cssSelector("div.success")));
        boolean messageStatus = successMessageElement.getText().contains("Category");
        if (messageStatus){
            System.out.println("Category created successfuly.");
        }else{
            System.out.println("category didn't created");
        }
        String pageInformation = driver.getPageSource();
        boolean categoryExist = pageInformation.contains(categoryName);
        if (messageStatus && categoryExist) {
            System.out.println("A new category get added");
            return true;
        } else {
            System.out.println("Saving category failed");
            return false;
        }
    }

    public boolean Logout() {
        WebElement logoutIcon = wait(driver.findElement(By.cssSelector("i.fa.fa-sign-out")));
        logoutIcon.click();
        WebElement login = wait(driver.findElement(By.xpath("//input[@id='login']")));
        if (login.isDisplayed()){
            System.out.println("Logout successfully.");
            driver.close();
            driver.quit();
            return true;
        }
        else {
            System.out.println("Logout Failed.");
            driver.close();
            driver.quit();
            return false;
        }
    }

    public WebElement wait(WebElement webElement) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        return webDriverWait.until(ExpectedConditions.visibilityOf(webElement));
    }

}
