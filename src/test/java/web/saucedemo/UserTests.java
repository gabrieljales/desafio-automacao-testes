package web.saucedemo;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class UserTests extends BaseSeleniumWebTestSetup {

    @Test
    @Description("Teste de Login, adição e remoção de itens no carrinho")
    public void testLoginAndManageCart() throws InterruptedException {
        Login("standard_user", "secret_sauce");

        // Adicionando itens ao carrinho
        addItemToCart("sauce-labs-backpack");
        addItemToCart("sauce-labs-onesie");
        addItemToCart("sauce-labs-fleece-jacket");
        Thread.sleep(1000);

        // Removendo item do carrinho
        removeItemFromCart("sauce-labs-backpack");
        Thread.sleep(1000);

        // Valida se o item foi adicionado
        Assertions.assertEquals(2, getCartItemCount(), "Deveria haver 2 itens no carrinho.");
    }

    @Test
    @Description("Teste de Login e Checkout dos itens adicionados ao carrinho")
    public void testLoginAndCartCheckout() throws InterruptedException {
        Login("standard_user", "secret_sauce");

        // Adicionando itens ao carrinho
        addItemToCart("sauce-labs-backpack");
        addItemToCart("sauce-labs-onesie");
        Thread.sleep(1000);

        // Realiza checkout
        cartCheckout("Standard", "User", "9999-9999");
        Thread.sleep(1000);
    }

    @Step("Realizando login com username: {userName} e senha: {password}")
    private void Login(String userName, String password) {
        openPage("https://www.saucedemo.com/");

        // Preenche campo nome de usuário
        WebElement userNameInput = browser.findElement(By.id("user-name"));
        userNameInput.sendKeys(userName);
        captureScreenshot();

        // Preenche campo de senha
        WebElement passwordInput = browser.findElement(By.id("password"));
        passwordInput.sendKeys(password);
        captureScreenshot();

        // Clicaa no input login-button
        WebElement loginButton = browser.findElement(By.id("login-button"));
        loginButton.click();
        captureScreenshot();
    }

    @Step("Obter contagem de itens no carrinho")
    private int getCartItemCount() {
        // Tenta encontrar o contador de itens do carrinho
        List<WebElement> cartBadge = browser.findElements(By.cssSelector("[data-test='shopping-cart-badge']"));

        if (!cartBadge.isEmpty()) {
            return Integer.parseInt(cartBadge.get(0).getText());
        } else {
            return 0;
        }
    }


    @Step("Adicionando o item {itemId} no carrinho")
    private void addItemToCart(String itemName) {
        String addButtonId = "add-to-cart-" + itemName;

        WebElement addButton = browser.findElement(By.id(addButtonId));
        addButton.click();

        // Validando se o item foi adicionado
        int itemCount = getCartItemCount();
        System.out.println("Itens no carrinho após adicionar " + itemName + ": " + itemCount);
        captureScreenshot();
    }


    @Step("Removendo o item {itemId} do carrinho")
    private void removeItemFromCart(String itemName) {
        String removeButtonId = "remove-" + itemName;

        WebElement removeButton = browser.findElement(By.id(removeButtonId));
        removeButton.click();

        // Valida se o item foi removido
        int itemCount = getCartItemCount();
        System.out.println("Itens no carrinho após remover " + itemName + ": " + itemCount);
        captureScreenshot();
    }

    @Step("Acessar o carrinho")
    private void accessCart() {
        // Encontra o elemento do carrinho pelo atributo data-test e clica nele
        WebElement cartIcon = browser.findElement(By.cssSelector("[data-test='shopping-cart-link']"));
        cartIcon.click();

        // Valida direcionamento para a página do carrinho pela URL
        String currentUrl = browser.getCurrentUrl();
        Assertions.assertEquals("https://www.saucedemo.com/cart.html", currentUrl, "A URL não corresponde à página do carrinho");

        // Valida se o título da página é "Your Cart"
        WebElement pageTitle = browser.findElement(By.cssSelector("[data-test='title']"));
        Assertions.assertEquals("Your Cart", pageTitle.getText(), "O título da página não é 'Your Cart'");
        captureScreenshot();
    }

    @Step("Realiza checkout")
    private void cartCheckout(String firstName, String lastName, String postalCode) {
        // Acessa o carrinho
        accessCart();

        // Clica no botão de checkout
        WebElement checkoutButton = browser.findElement(By.id("checkout"));
        checkoutButton.click();
        captureScreenshot();

        // Preenche o campo "first name"
        WebElement firstNameInput = browser.findElement(By.id("first-name"));
        firstNameInput.sendKeys(firstName);

        // Preenche o campo "last name"
        WebElement lastNameInput = browser.findElement(By.id("last-name"));
        lastNameInput.sendKeys(lastName);

        // Preenche o campo "postal code"
        WebElement postalCodeInput = browser.findElement(By.id("postal-code"));
        postalCodeInput.sendKeys(postalCode);
        captureScreenshot();

        // Clica no botão "continue" para continuar o checkout
        WebElement continueButton = browser.findElement(By.id("continue"));
        continueButton.click();
        captureScreenshot();

        // Clica no botão "finish" para finalizar o pedido
        WebElement finishButton = browser.findElement(By.id("finish"));
        finishButton.click();

        // Verifica a mensagem de sucesso
        WebElement successMessage = browser.findElement(By.cssSelector("[data-test='complete-header']"));
        Assertions.assertEquals("Thank you for your order!", successMessage.getText(), "A mensagem de sucesso não foi exibida corretamente!");
        captureScreenshot();
    }
}


