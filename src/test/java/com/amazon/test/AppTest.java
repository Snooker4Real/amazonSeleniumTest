package com.amazon.test;

import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private WebDriver driver;

    @Before
    public void setUp() throws Exception {
        // On récupére le système d'explotation
        String os = System.getProperty("os.name")
                .toLowerCase()
                .split(" ")[0];
        // générer le chemin du fichier du driver
        String pathMarionette = Paths.get(".").toAbsolutePath().normalize().toString() + "/lib/chromedriver-" + os;

        // enregistre le chemin dans une propriété qui est webdriver.chrome.driver
        // Firefox : webdriver.gecko.driver
        System.setProperty("webdriver.chrome.driver", pathMarionette);

        // options pour mettre le navigateur en pleine écran
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // on créé l'objet ChromeDriver
        driver = new ChromeDriver(options);
        //driver = new FirefoxDriver(options);


        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

    }

    //@Test
    public void testUntitledTestCase() throws Exception {
        // get : ouvre une page web
        driver.get("https://www.amazon.fr/");

        driver.findElement(By.id("sp-cc-accept")).click();
        Thread.sleep(2000);


        // findElement : cherche un élément sur la page
        WebElement search = driver.findElement(By.id("twotabsearchtextbox"));
        // click sur l'élément
        search.click();
        // nettoya le contenu de l'élément
        search.clear();

        // sendKeys : écrit dans un élément
        search.sendKeys("Selenium Testing Tools Cookbook");


//        driver.findElement(By.id("twotabsearchtextbox")).clear();
//        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Selenium Testing Tools Cookbook");


        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);

        WebElement divSearch = driver.findElement(By.id("search"));
        // récupère la liste des imgs contenues dans la div search
        List<WebElement> imgs = divSearch.findElements(By.tagName("img"));
        for (WebElement img : imgs) {
            String alt = img.getAttribute("alt");
            System.out.println(alt);
            if (alt.toLowerCase().contains("Selenium Testing Tools Cookbook".toLowerCase())) {
                img.click();
                break;
            }
        }
        Thread.sleep(2000);

        WebElement tmmSwatches = driver.findElement(By.id("tmmSwatches"));
        List<WebElement> links = tmmSwatches.findElements(By.tagName("a"));
        for (WebElement link : links) {
            if (link.getText().toLowerCase().contains("broché".toLowerCase())) {
                link.click();
                break;
            }
        }
        Thread.sleep(2000);

        driver.findElement(By.id("add-to-cart-button")).click();
        Thread.sleep(1000);

        driver.findElement(By.id("hlb-view-cart-announce")).click();
        Thread.sleep(1000);

        WebElement activeCartViewForm = driver.findElement(By.id("activeCartViewForm"));
        WebElement sup = activeCartViewForm.findElement(By.cssSelector("input[type=\"submit\"]"));
        sup.click();
        Thread.sleep(1000);

        WebElement h1 = driver.findElement(By.tagName("h1"));

        // terminer par un assert
        // assertTrue : vérifie que la valeur est vrai
        assertTrue(h1.getText().toLowerCase().contains("Votre panier Amazon est vide.".toLowerCase()));

        //driver.findElement(By.id("")).click();
        //driver.findElement(By.xpath("//div[@id='search']/div/div/div/span[3]/div[2]/div/div/span/div/div/div[2]/div/h2/a/span")).click();
        //driver.findElement(By.id("add-to-cart-button")).click();
        //driver.findElement(By.id("hlb-view-cart-announce")).click();
        //driver.findElement(By.name("submit.delete.C252527d6-033a-4211-9c8d-7ac00469a1b9")).click();
        //ERROR: Caught exception [unknown command []]
    }


    @After
    public void tearDown() throws Exception {
        // on ferme le driver ouvert
        driver.close();
        driver.quit();
    }


    //@Test
    public void testProductHistory() throws Exception {

        driver.get("https://www.amazon.fr/Selenium-Testing-Tools-Cookbook-English-ebook/dp/B017HP96S2/ref=pd_ybh_a_2?_encoding=UTF8&psc=1&refRID=YNX8TM0BPPDBDKHV0NKG");

        WebElement productTitle = driver.findElement(By.id("productTitle"));
        Thread.sleep(1000);
        String productTitleText = productTitle.getText();
        System.out.println(productTitleText);

        driver.get("https://www.amazon.fr/gp/history/");
        WebElement productTitle2 = driver.findElement(By.className("p13n-sc-truncated"));
        Thread.sleep(1000);
        String productTitleText2 = productTitle2.getText();
        System.out.println(productTitleText2);


        boolean comparison = productTitleText2.substring(0, 10).equals(productTitleText.substring(0, 10));
        if (comparison) {
            System.out.println("Les deux produits sont identiques");
        }

        assertTrue(comparison);

    }


    //@Test
    public void testProductHistory2() throws Exception {

        boolean assertBool = false;
        driver.get("https://www.amazon.fr/gp/product/1784392510");
        Thread.sleep(1000);
        WebElement title = driver.findElement(By.id("productTitle"));
        String titleString = title.getText().toLowerCase();
        Thread.sleep(1000);

        driver.get("https://www.amazon.fr/gp/history/");
        Thread.sleep(1000);

        List<WebElement> historyTitles = driver.findElements(By.className("p13n-sc-truncated"));
        for (WebElement historyTitle : historyTitles) {
            String hStringtitle = historyTitle.getAttribute("title");
            System.out.println(hStringtitle.toUpperCase());
            System.out.println(titleString.toLowerCase());
            if (hStringtitle.toLowerCase().contains(titleString.toLowerCase())) {
                assertBool = true;
                break;
            }
        }

        assertTrue(assertBool);
    }


    //@Test
    public void testLivre() throws Exception {

        //https://www.amazon.fr/TA-T08-D-B-Camera-Handle-Baseplate-Tiltaing/dp/B08NTFZCK5/ref=pd_ybh_a_1?_encoding=UTF8&psc=1&refRID=14KQPT1DXCPG5Y3Z2N1T
        final String URL = "https://www.amazon.fr/Ast%C3%A9rix-Griffon-n%C2%B039-Ren%C3%A9-Goscinny/dp/2864973499?ref_=Oct_d_obs_d_301061&pd_rd_w=2V9N5&pf_rd_p=b6af1c64-bd4d-4f94-ab91-3be864ec4fff&pf_rd_r=PA183NHDJMNP1FGQYP4J&pd_rd_r=8d5f4de4-2165-4e32-9899-d62747a070e4&pd_rd_wg=EVL6r&pd_rd_i=2864973499";
        boolean assertBool = false;
        driver.get(URL);

        List<WebElement> contents = driver.findElements(By.className("a-text-bold"));
        for (WebElement content : contents) {
            if (content.getText().contains("ISBN-10") || content.getText().contains("ISBN-13") || content.getText().contains("ASIN")) {
                System.out.println(content.getText());
                assertBool = true;
                break;
            }
        }

        assertTrue(assertBool);
    }

    //@Test
    public void testPanierIncr() throws InterruptedException {
        //https://www.amazon.fr/TA-T08-D-B-Camera-Handle-Baseplate-Tiltaing/dp/B08NTFZCK5/ref=pd_ybh_a_1?_encoding=UTF8&psc=1&refRID=14KQPT1DXCPG5Y3Z2N1T
        final String URL = "https://www.amazon.fr/Ast%C3%A9rix-Griffon-n%C2%B039-Ren%C3%A9-Goscinny/dp/2864973499?ref_=Oct_d_obs_d_301061&pd_rd_w=2V9N5&pf_rd_p=b6af1c64-bd4d-4f94-ab91-3be864ec4fff&pf_rd_r=PA183NHDJMNP1FGQYP4J&pd_rd_r=8d5f4de4-2165-4e32-9899-d62747a070e4&pd_rd_wg=EVL6r&pd_rd_i=2864973499";

        // Aller sur la page du produit
        driver.get(URL);

        // cliquer sur le bouton "accept-cookies"
        driver.findElement(By.id("sp-cc-accept")).click();
        Thread.sleep(2000);

        // Récupère le nombre de contenu dans le panier
        WebElement countCart0 = driver.findElement(By.id("nav-cart-count"));
        String countCartString0 = countCart0.getText();
        System.out.println(countCartString0);


        // cliquer sur le bouton "Add to cart" : Ajout au panier ✅
        driver.findElement(By.id("add-to-cart-button")).click();
        Thread.sleep(2000);

        // Récupère le nombre de contenu dans le panier
        WebElement countCart1 = driver.findElement(By.id("nav-cart-count"));
        String countCartString1 = countCart1.getText();
        System.out.println(countCartString1);

        // Vérifier que le nombre de contenu dans le panier augmente de 1
        assertEquals(Integer.parseInt(countCartString1), Integer.parseInt(countCartString0) + 1);

    }

    @Test
    public void testLogin() throws InterruptedException {
        // Login URL
        final String URL = "https://www.amazon.fr/ap/signin?openid.pape.max_auth_age=0&openid.return_to=https%3A%2F%2Fwww.amazon.fr%2Fgp%2Fhuc%2Fview.html%3Fie%3DUTF8%26newItems%3DC8d6051cd-1d8d-40fb-8c4e-07bb94a6a799%252C1%26ref_%3Dnav_signin&openid.identity=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.assoc_handle=frflex&openid.mode=checkid_setup&openid.claimed_id=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0%2Fidentifier_select&openid.ns=http%3A%2F%2Fspecs.openid.net%2Fauth%2F2.0&";

        // Aller sur la page de login
        driver.get(URL);
        Thread.sleep(3000);

        // findElement : cherche un élément sur la page
        WebElement connectId = driver.findElement(By.id("ap_email"));
        // click sur l'élément
        connectId.click();
        // nettoya le contenu de l'élément
        connectId.clear();

        // écrit dans le login
        connectId.sendKeys("a");

        Thread.sleep(3000);

        // Clicker sur continuer
        driver.findElement(By.id("continue")).click();
        Thread.sleep(10000);


        // Si le login est false, la div "auth-error-message-box" apparait
        WebElement errorMessage = null;

        try {
            errorMessage = driver.findElement(By.id("auth-error-message-box"));
            System.out.println(errorMessage.getText());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertNotNull(errorMessage);
        //assertTrue(errorMessage.isDisplayed());

    }

    // CORRECTION
    @Test
    public void testProductHistoryCorrect() throws Exception {

        // ouvre la page d'un produit
        driver.get("https://www.amazon.fr/gp/product/1784392510");
        Thread.sleep(1000);

        // récupère le titre du produit
        String titleText = driver.findElement(By.id("productTitle")).getText();

        // ouvre la page history
        driver.get("https://www.amazon.fr/gp/history");
        Thread.sleep(1000);

        // récupère le bloc contenant les produits dans l'historique
        WebElement divAsinList = driver.findElement(By.id("asin_list"));

        // récupère les imgs contenu dans ce bloc
        List<WebElement> imgs = divAsinList.findElements(By.tagName("img"));

        // boucle sur les img qui sont dans ce bloc
        boolean test = false;
        for (WebElement img : imgs) {
            // test si la balise alt contient le titre du produit visité
            String alt = img.getAttribute("alt");
            if (alt.toLowerCase().contains(titleText.toLowerCase())) {
                test = true;
                break;
            }
        }

        // valide le test par un assert
        assertTrue(test);
        Thread.sleep(1000);
    }

    @Test
    public void testProductDetailCorrect() throws Exception {

        // pour indiqué si le test est validé
        boolean found = false;

        // on ouvre la page du produit tester
        driver.get("https://www.amazon.fr/Selenium-Testing-Tools-Cookbook-Second/dp/1784392510");
        Thread.sleep(1000);

        // on cherche la div contenant le détail du produit
        WebElement div = driver.findElement(By.id("detailBullets_feature_div"));

        // on récupère tous les élément qui ont la classe a-text-bold
        List<WebElement> blods = div.findElements(By.className("a-text-bold"));
        for (WebElement blod : blods) {
            // si un des éléments contient le texte recherché on passe founded à true
            if (blod.getText().toUpperCase().contains("ISBN-10") ||
                    blod.getText().toUpperCase().contains("ISBN-13") ||
                    blod.getText().toUpperCase().contains("ASIN")) {
                found = true;
                break;
            }
        }

        // on termine par un assert
        assertTrue(found);

        Thread.sleep(1000);
    }

    @Test
    public void cartIncrementWhenArticleChoosedCorrect() throws InterruptedException {

        // on ouvre la page du produit à testé
        driver.get("https://www.amazon.fr/Selenium-Testing-Tools-Cookbook-Second/dp/1784392510");
        Thread.sleep(1000);

        // on récupère le nombre d'éléments dans le panier avant l'ajout
        int countBefore = Integer.parseInt(driver.findElement(By.id("nav-cart-count")).getText());

        // Permet de recentrer un élément dans la page avant de le cliquer
        WebElement addToCart = driver.findElement(By.id("add-to-cart-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCart);
        Thread.sleep(1000);

        // clic pour ajouter le produit au panier
        addToCart.click();
        Thread.sleep(1000);

        // on récupère le nombre d'éléments dans le panier après l'ajout
        int countAfter = Integer.parseInt(driver.findElement(By.id("nav-cart-count")).getText());

        // on termine par un assert qui vérifier si le nombre d'élément dans le panier
        // avant l'ajout +1 est égal au nombre d'éléments après
        countBefore++;
        assertEquals(countBefore, countAfter);

        Thread.sleep(1000);
    }

    @Test
    public void testErrorLogin() throws InterruptedException {
        // ouvre la page d'acceuil
        driver.get("https://www.amazon.fr/");

        // accept le cookie
        driver.findElement(By.id("sp-cc-accept")).click();
        Thread.sleep(1000);

        // clic sur le lien qui permet d'aller à la page de connexion
        driver.findElement(By.id("nav-link-accountList")).click();
        Thread.sleep(1000);

        // récupère le textbox et envoie un email bidon
        WebElement email = driver.findElement(By.id("ap_email"));
        email.sendKeys("fsdfsdf@test.fr");

        // clic sur le bouton continuer
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        // récupère le bloc d'erreur
        WebElement erreur = null;
        try {
            erreur = driver.findElement(By.id("auth-error-message-box"));
        } catch (Exception e) {
            // TODO: handle exception
        }

        // assert pour vérifier si le bloc existe
        assertNotNull(erreur);

        Thread.sleep(1000);
    }

}


