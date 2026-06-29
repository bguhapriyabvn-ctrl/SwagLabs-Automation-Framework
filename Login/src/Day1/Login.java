package Day1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class Login {

	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.saucedemo.com");
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce");
		driver.findElement(By.id("login-button")).click();

		// Verification: Check if login is successful using URL
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("inventory"));
		
		String currentURL = driver.getCurrentUrl();
		System.out.println("Current URL after login: " + currentURL);
		
		// Assert that the URL has changed to the inventory page (indicating successful login)
		if (currentURL.contains("inventory")) {
			System.out.println("Login successful! URL has changed to inventory page.");
		} else {
			System.out.println("Login failed! URL did not change as expected.");
		}
		
		// Pick 3rd item after login and add to cart
		System.out.println("\n--- Adding 3rd item to cart ---");
		
		// Find all "Add to cart" buttons
		List<WebElement> addToCartButtons = driver.findElements(By.xpath("//button[contains(text(), 'Add to cart')]"));
		System.out.println("Total items available: " + addToCartButtons.size());
		
		// Click on the 3rd item's "Add to cart" button (index 2)
		if (addToCartButtons.size() >= 3) {
			WebElement thirdItemButton = addToCartButtons.get(2);
			
			// Get the 3rd item name before adding to cart
			WebElement thirdItem = driver.findElements(By.className("inventory_item")).get(2);
			String itemName = thirdItem.findElement(By.className("inventory_item_name")).getText();
			System.out.println("3rd item selected: " + itemName);
			
			// Click Add to cart button for 3rd item
			thirdItemButton.click();
			System.out.println("Added to cart: " + itemName);
			
			// Wait for cart badge to update
			wait.until(ExpectedConditions.presenceOfElementLocated(By.className("shopping_cart_badge")));
			
			// Verify item was added to cart
			WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
			String cartCount = cartBadge.getText();
			System.out.println("Cart count after adding item: " + cartCount);
			System.out.println("Item successfully added to cart!");
		} else {
			System.out.println("Error: Less than 3 items available!");
		}
		
		driver.quit();
	}

}