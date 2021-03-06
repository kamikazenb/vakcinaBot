import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedInputStream;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class App {

    private JButton bStartStop;
    private JPanel panel1;
    private JTextField tfZiadatelMeno;
    private JTextField tfZiadatelPriezvisko;
    private JTextField tfZiadatelRodne;
    private JTextField tfZiadatelTC;
    private JComboBox cbPoistovna;
    private JTextField tfZiadatelMail;
    private JTextField tfICEmeno;
    private JTextField tfICEpriezvisko;
    private JTextField tfICEtc;
    private JTextField tfICEmail;
    private JTextField tfZiadatelMesto;
    private JTextField tfZiadatelUlicaNazov;
    private JTextField tfZiadatelUlicaCislo;
    private JTextField tfZiadatelPSC;
    private JComboBox cbSkupina;
    private JComboBox cbPreferovanyKraj;
    protected JSpinner sObnovenie;
    private JLabel lInfo;
    private JTextField tfDriver;
    WebDriver driver;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("App");
        jFrame.setContentPane(new App().panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setTitle("VakcinaBot v" + "1.2");
        App app = new App();

    }

    public App() {
        init();
    }

    public void init() {
        bStartStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    driver.quit();
                } catch (Exception ex) {

                }
                playSound();
                startBot();
            }
        });

        sObnovenie.setModel(new SpinnerNumberModel(5, 5, 200, 1));
        String[] kraje = {"", "Banskobystrick??", "Bratislavsk??", "Ko??ick??", "Nitriansky", "Pre??ovsk??", "Tren??iansky",
                "Trnavsk??", "??ilinsk??", "Nezaraden??"};
        for (String s : kraje) {
            cbPreferovanyKraj.addItem(s);
        }
        String[] poistovne = {"", "D??vera", "V??ZP", "Union", "E?? poistenec", "Cudzinec"};
        for (String s : poistovne) {
            cbPoistovna.addItem(s);
        }
        cbSkupina.addItem("");
        cbSkupina.addItem("Osoba 60-70");
        cbSkupina.addItem("Osoba 70+");
    }

    public boolean isFreePlace(String region) {
        List<WebElement> elements = driver.findElements(By.className("btn"));
        java.util.Iterator<WebElement> i = elements.iterator();
        while (i.hasNext()) {
            WebElement element = i.next();
            if (element.isDisplayed()) {
                String str = element.getText();
                if (str.contains((CharSequence) region)) {
                    System.out.println(str);
                    str = str.replaceAll("\\D+", "");
                    if (Integer.parseInt(str) > 0) {
                        System.out.println(str);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public void fillForm() {
        driver.findElement(By.name("user_name")).sendKeys(tfZiadatelMeno.getText());
        driver.findElement(By.name("last_name")).sendKeys(tfZiadatelPriezvisko.getText());

        driver.findElement(By.name("phone")).sendKeys(tfZiadatelTC.getText());
        driver.findElement(By.name("email")).sendKeys(tfZiadatelMail.getText());
        addNumberValues("birthNumber", tfZiadatelRodne.getText());
        driver.findElement(By.name("user_name_ice")).sendKeys(tfICEmeno.getText());
        ;
        driver.findElement(By.name("last_name_ice")).sendKeys(tfICEpriezvisko.getText());
        ;
        driver.findElement(By.name("phone_ice")).sendKeys(tfICEtc.getText());
        driver.findElement(By.name("email_ice")).sendKeys(tfZiadatelMail.getText());

        driver.findElement(By.name("street")).sendKeys(tfZiadatelUlicaNazov.getText());
        driver.findElement(By.name("streetNumm")).sendKeys(tfZiadatelUlicaCislo.getText());
        driver.findElement(By.name("zip")).sendKeys(tfZiadatelPSC.getText());

        try {
            Select poistovna = new Select(driver.findElement(By.name("company")));
            poistovna.selectByVisibleText(cbPoistovna.getSelectedItem().toString());
        } catch (Exception e) {
            System.out.println("exception in poistovna:" + e.toString());
        }

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.name("patientForm")));
        js.executeScript("window.scrollBy(0,-50)");
    }

    public void addNumberValues(String elementPosition, String text){
        try {
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(By.name(elementPosition))).click();
            for (int i = text.length()-1; i >= 0; i--) {
                Thread.sleep(30);
                action.sendKeys(Keys.HOME).perform();
                action.sendKeys(String.valueOf(text.charAt(i))).perform();
            }
        } catch (Exception e) {
            System.out.println("exception in number:" + e.toString());
        }
    }

    public void startBot() {
        System.setProperty("webdriver.gecko.driver", tfDriver.getText());
        driver = new FirefoxDriver();
        driver.get("https://www.old.korona.gov.sk/covid-19-vaccination-form.php");
        boolean refresh = true;
        try {
            while (refresh) {
                try {
                    new WebDriverWait(driver, 2).until(
                            webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
                    Thread.sleep(300);
                } catch (Exception e) {
                    System.out.println("exception in wait:" + e.toString());
                }

                if (isFreePlace(cbPreferovanyKraj.getSelectedItem().toString())) {
                    refresh = false;
                }
                fillForm();
                if (refresh) {
                    Thread.sleep(Duration.ofSeconds((Integer) sObnovenie.getValue()).toMillis());
                    driver.navigate().refresh();
                }
            }
            playSound();


        } catch (Exception e) {
            driver.quit();
            lInfo.setText("chyba");
        }
    }

    public void playSound() {
        try {
            AudioFormat format;
            DataLine.Info info;
            Clip clip;
            InputStream is = getClass().getResourceAsStream("notification.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));

            format = audioInputStream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(audioInputStream);
            clip.start();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
