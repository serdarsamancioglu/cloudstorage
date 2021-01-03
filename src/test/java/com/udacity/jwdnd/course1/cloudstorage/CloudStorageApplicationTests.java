package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseUrl;
	private String userName = "testUser";
	private String password = "password12";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + this.port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void test1_1() {

		driver.get(baseUrl + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseUrl + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseUrl + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void test1_2() {
		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);

		signupPage.signUp("Andrew", "Garfield", userName, password);
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName,password);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(webDriver -> webDriver.findElement(By.id("logoutDiv")));
		Assertions.assertEquals("Home", driver.getTitle());

		HomePage homePage = new HomePage(driver);
		homePage.logout();
		wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));

		Assertions.assertEquals("Login", driver.getTitle());

		driver.get(baseUrl + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void test2_1() {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
		String title = "Note";
		String description = "This is a dummy note";
		WebDriverWait wait = new WebDriverWait(driver, 5);
		HomePage homePage = new HomePage(driver);
		sleep(1000);
		homePage.openNotesTab();
		sleep(1000);
		homePage.newNote();
		//wait.until is not working stable and I am getting lots of "element not interactable" exception.
		//that's why I call Thread.sleep
		//wait.until(webDriver -> webDriver.findElement(By.id("inputNoteTitle")));
		sleep(1000);
		homePage.addNewNote(title, description);
		sleep(2000);
		homePage.openNotesTab();
		sleep(2000);
		Assertions.assertTrue(homePage.checkNoteSaved(title, description));
	}

	@Test
	public void test2_2() {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		HomePage homePage = new HomePage(driver);
		sleep(1000);
		homePage.openNotesTab();
		sleep(1000);
		homePage.editNote();
		sleep(1000);
		String title = "Edited Note";
		String description = "This is an edited note";
		homePage.saveNote(title, description);
		sleep(2000);
		homePage.openNotesTab();
		sleep(2000);
		Assertions.assertTrue(homePage.checkNoteSaved(title, description));
	}

	@Test
	public void test2_3() {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		HomePage homePage = new HomePage(driver);
		sleep(1000);
		homePage.openNotesTab();
		sleep(1000);
		homePage.deleteNote();
		sleep(2000);
		homePage.openNotesTab();
		sleep(2000);
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.deleteNote());
	}

	@Test
	public void test3_1() {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
		String url = "tokeninc.com";
		String username = "user1";
		String password = "password1";
		WebDriverWait wait = new WebDriverWait(driver, 5);
		HomePage homePage = new HomePage(driver);
		sleep(1000);
		homePage.openCredentialsTab();
		sleep(1000);
		homePage.newCredential();
		sleep(1000);
		homePage.addNewCredential(url, username, password);
		sleep(2000);
		homePage.openCredentialsTab();
		sleep(2000);
		Assertions.assertTrue(homePage.checkCredentialSaved(url, username, password));
	}

	@Test
	public void test3_2() {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		HomePage homePage = new HomePage(driver);
		sleep(1000);
		homePage.openCredentialsTab();
		sleep(1000);
		String url = homePage.getSavedUrl();
		String username = homePage.getSavedUsername();
		String password = homePage.getSavedPassword();
		homePage.editCredential();
		sleep(1000);
		//verifies that the viewable password is unencrypted
		Assertions.assertNotEquals(homePage.getModalPassword(), password);
		homePage.saveCredential("tokeninc.co", "user2", "password2");
		sleep(2000);
		homePage.openCredentialsTab();
		sleep(2000);
		//verifies that the changes are displayed
		Assertions.assertEquals(homePage.getSavedUrl(), "tokeninc.co");
		Assertions.assertEquals(homePage.getSavedUsername(), "user2");
		Assertions.assertNotEquals(password, homePage.getSavedPassword());
	}

	@Test
	public void test3_3() {
		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(userName, password);
		WebDriverWait wait = new WebDriverWait(driver, 5);
		HomePage homePage = new HomePage(driver);
		sleep(1000);
		homePage.openCredentialsTab();
		sleep(1000);
		homePage.deleteCredential();
		sleep(2000);
		homePage.openCredentialsTab();
		sleep(2000);
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.deleteCredential());
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
