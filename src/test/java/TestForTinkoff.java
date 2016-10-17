/**
 * Created by Frei on 17.10.2016.
 */




import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.testng.xml.*;


import java.util.List;

import static com.thoughtworks.selenium.SeleneseTestNgHelper.assertEquals;


public class TestForTinkoff {
    WebDriver selenium;

    @BeforeClass(alwaysRun = true)
    public void setupBeforeSuite(ITestContext context) {
       System.setProperty("webdriver.chrome.driver", "C:\\driver\\chromedriver.exe");
       selenium = new ChromeDriver();
    }

    @AfterClass(alwaysRun = true)
    public void setupAfterSuite() {
        selenium.quit();
    }

    @Test(description="Launches the market")
    public void launchSite() throws InterruptedException {
        selenium.get("https://market.yandex.ru");
        assertEquals(selenium.getTitle(), "Яндекс.Маркет — выбор и покупка товаров из проверенных интернет-магазинов");
//        System.out.println(selenium.getTitle());
    }

    @Test(description = "Click catalog")
    public void catalog() throws InterruptedException {
        selenium.findElement(By.id("index-headline-id-tab-1")).click();
        selenium.getTitle();
        assertEquals(selenium.getCurrentUrl(), "https://market.yandex.ru/?tab=catalog");
        selenium.findElement(By.xpath("//*[contains(text(),'Статьи и подборки')]"));
        selenium.findElement(By.xpath("//*[contains(text(),'Вас также могут заинтересовать')]"));
        selenium.findElement(By.xpath("//*[contains(text(),'Популярные товары')]"));
    }

    @Test(description = "Go to phones")
    public void phones() {
        int i = 0;
        int j = 0;
        selenium.findElement(By.partialLinkText("Мобильные телефоны")).click();
        List<WebElement> lists = selenium.findElements(By.className("top-3-models"));
        System.out.println(lists);
        for (WebElement top3 : lists) {
            i++;
            System.out.println(top3);
            List<WebElement> lists_t3 = top3.findElements(By.className("top-3-models__model"));
            System.out.println(lists_t3);
            for (WebElement top3_it : lists_t3) {
                j++;
                System.out.println(top3_it.findElement(By.xpath(".//a")).getText());
//                почему-то не выходит получить текст, но проверку что их 3 сделал
            }
        }
        assertEquals(i,2);
        assertEquals(j,6);
    }

    @Test(description = "Search")
    public void search() throws InterruptedException {
        selenium.findElement(By.partialLinkText("Расширенный поиск")).click();
        selenium.findElement(By.xpath("//*[contains(text(),'Мобильные телефоны')]"));
        selenium.findElement(By.className("n-filter-panel-aside__content"));
        selenium.findElement(By.id("glf-pricefrom-var")).sendKeys("5125");
        selenium.findElement(By.id("glf-priceto-var")).sendKeys("10123");
        assertEquals(selenium.findElement(By.id("glf-pricefrom-var")).getAttribute("value"), 5125);
        assertEquals(selenium.findElement(By.id("glf-priceto-var")).getAttribute("value"), 10123);
    }

    @Test(description = "set active Кликнуть на чекбокс \"В продаже\"")
    public void isactive() {
        WebElement onstock = selenium.findElement(By.id("glf-onstock-select"));
        if ( !onstock.isSelected() )
        {
            onstock.click();
        }
        assertEquals(onstock.isSelected(), true);
    }




}