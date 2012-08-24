package tester;

import org.openqa.selenium.WebDriverException;

public class LinkFinderException extends WebDriverException {
    @Override
    public String getMessage() {
        return "Some problems with links";
    }
}
