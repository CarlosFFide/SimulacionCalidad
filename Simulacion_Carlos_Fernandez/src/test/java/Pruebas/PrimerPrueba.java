//Autor Carlos Fernandez Herrera

package Pruebas;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PrimerPrueba {

	private WebDriver driver;

	@Before

	public void setUp() {

		ChromeOptions co = new ChromeOptions();

		co.addArguments("--remote-allow-origins=*");
		System.setProperty("webdriver.chrome.drive", "./src/tests/resources/Driver/chromedriver.exe");
		driver = new ChromeDriver(co);
		driver.manage().window().maximize();
		driver.get("https://www.saucedemo.com/v1/");
	}

	public void InicioDeSesion() {
		// metodo reutilizable para iniciar sesion - Este tambien es el Test#1
		WebElement txtuser = driver.findElement(By.id("user-name"));
		WebElement txtpassword = driver.findElement(By.id("password"));
		WebElement btnLogin = driver.findElement(By.id("login-button"));

		if (txtpassword.isDisplayed() && txtpassword.isEnabled()) {

			txtuser.sendKeys("standard_user");
			txtpassword.sendKeys("secret_sauce");
			btnLogin.click();

		}

	}

	@Test

	public void PrimeraPrueba() {

		// Test 1 para inicio de sesion correcto con usuario/pass provistos
		

		InicioDeSesion();

		// Confirma que el inicio fue exitoso comparando el label Products que aparece
		// en la ventana principal post-login, utilizando el metodo de inicio de sesion
		// previamente declarado para reutilizar en pruebas posteriores

		WebElement lbProducts = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[1]/div[3]/div")); 

		String txtProducts = lbProducts.getText();

		assertEquals("Pruebas lb Products", "Products", txtProducts);
	}

	@Test

	public void SegundaPrueba() {
		// Prueba 2 para comprobar que el numero de items en el carrito se refleje de
		// forma correcta dependiendo de la cantidad de items agregados en el boton de
		// carrito en el menu principal
		InicioDeSesion();

		WebElement btnBackPack = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/button"));

		btnBackPack.click();

		WebElement lbCarritoNumeroDeItem = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]/a/span"));

		String NumItems = lbCarritoNumeroDeItem.getText();

		assertEquals("Numero de items en el carrito", "1", NumItems);
	}

	@Test

	public void TercerPrueba() {

		// Prueba 3 para comprobar que el boton de carrito lleva a la pagina del carrito
		// con los items seleccionados y que dichos items sean iguales a los
		// seleccionados en la pagina principal

		InicioDeSesion();

		WebElement lbBackPack = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/a/div"));
		WebElement btnBackPack = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/button"));
		WebElement btnCarrito = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]"));
		String txtBackpack = lbBackPack.getText();

		btnBackPack.click();
		btnCarrito.click();

		WebElement lbYourCart = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]"));

		String txtYourCart = lbYourCart.getText();

		assertEquals("Banner Your Cart", "Your Cart", txtYourCart);
		assertEquals("Label Backpack", "Sauce Labs Backpack", txtBackpack);

	}

	@Test
	public void CuartaPrueba() {
		// Prueba 4 para comparar que el precio del item Backpack sea igual en su
		// descripcion al que se muestra en el menu principal
		InicioDeSesion();

		WebElement btnBackPackInfo = driver.findElement(By.id("item_4_title_link"));
		WebElement txtPriceBackpackMenu = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/div"));
		String txtBackpackPriceMenu = txtPriceBackpackMenu.getText();
		btnBackPackInfo.click();

		WebElement txtPriceBackpackDetail = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div[3]"));
		String txtBackPackPriceDetail = txtPriceBackpackDetail.getText();

		assertEquals("Precio en detalles no coincide con el del menú", txtBackpackPriceMenu, txtBackPackPriceDetail);

	}

	@Test
	public void QuintaPrueba() {

		// prueba 5 para simular una compra completa desde poner el producto en el
		// carrito, aceptar, poner los datos de nombre, apellido y postal; por ultimo
		// finalizar la compra

		InicioDeSesion();

		WebElement lbBackPack = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/a/div"));
		WebElement btnBackPack = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/button"));
		WebElement btnCarrito = driver.findElement(By.xpath("/html/body/div/div[2]/div[1]/div[2]"));
		String txtBackpack = lbBackPack.getText();

		btnBackPack.click();
		btnCarrito.click();

		WebElement btnCheckout = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[2]/a[2]"));
		btnCheckout.click();

		WebElement txtnombre = driver.findElement(By.id("first-name"));
		WebElement txtapellido = driver.findElement(By.id("last-name"));
		WebElement txtpostal = driver.findElement(By.id("postal-code"));

		txtnombre.sendKeys("Usuario");
		txtapellido.sendKeys("Prueba");
		txtpostal.sendKeys("12345");

		WebElement btnContinue = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/form/div[2]/input"));
		btnContinue.click();

		WebElement btnFinish = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[2]/div[8]/a[2]"));
		btnFinish.click();

		WebElement txtFinishPage = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]"));
		String txtFinish = txtFinishPage.getText();

		assertEquals("Banner Finish Compra", "Finish", txtFinish);
	}

	@Test

	public void SextaPrueba() {
		// Prueba 6 para comprobar que la descripcion de la Jacket sea igual en el menu
		// que en la pagina de detalles del producto
		InicioDeSesion();

		WebElement txtdescripcionJacketMenu = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[4]/div[2]/div"));
		WebElement btnJacket = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[4]/div[2]/a"));

		String txtJacketdescripcionMenu = txtdescripcionJacketMenu.getText();

		btnJacket.click();

		WebElement txtdescripcionJacketDetail = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div[2]"));
		String txtJacketDetailDescripcion = txtdescripcionJacketDetail.getText();

		assertEquals("Descripción en detalles no coincide con el menú", txtJacketdescripcionMenu,
				txtJacketDetailDescripcion);

	}

	@Test

	public void SeptimaPrueba() {

		// Prueba 7 confirma la funcionalidad de ir a la pagina de detalles de producto
		// "Onesie" y que el boton de Back redirija a la pagina principal

		InicioDeSesion();

		WebElement btnOnesie = driver
				.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[5]/div[2]/a"));
		btnOnesie.click();

		WebElement btnBack = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/button"));
		btnBack.click();

		WebElement lbProducts = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[1]/div[3]/div"));

		String txtProducts = lbProducts.getText();

		assertEquals("Pruebas lb Products", "Products", txtProducts);
	}
}
