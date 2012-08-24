package tester;

import org.openqa.selenium.WebElement;


public class Action {
    private static String defButtonName = "Отправить";
    private static int imageNum = 1;
    private static int boxNum = 1;
    private ActionType type;
    private String text;

    public Action(ActionType actionType, WebElement w) {
        type = actionType;
        switch (type.getId()) {
            case 1:
                text = "move mouse to \"" + w.getText() + "\"";
                break;
            case 2:
                text = "click to link \"" + w.getText() + "\"";
                break;
            case 3:
                String buttonName = w.getAttribute("value");
                if (buttonName == null || buttonName.length() == 0) {
                    buttonName = defButtonName;
                }
                text = "click to button \"" + buttonName + "\"";
                break;
            case 4:
                String imageName = w.getAttribute("alt");
                if (imageName == null || imageName.length() == 0) {
                    imageName = w.getAttribute("title");
                    if (imageName == null || imageName.length() == 0) {
                        imageName = "Image" + Integer.toString(imageNum++);
                    }
                }
                text = "click to image \"" + imageName + "\"";
                break;
            case 5:
                String boxName = w.getAttribute("value");
                if (boxName == null || boxName.length() == 0) {
                    boxName = w.getAttribute("name");
                    if (boxName == null || boxName.length() == 0) {
                        boxName = "TextBox" + Integer.toString(boxNum++);
                    }
                    text = "type to box \"" + boxName + "\" some text";
                }
                break;
            case 6:
                String optionName = w.getText();
                text = "select option \"" + optionName + "\" in select element";
                break;
            case 7:
                String radioName = w.getAttribute("name");
                text = "switch on \"" + radioName + "\"";
                break;
        }

    }

    @Override
    public String toString() {
        return text;
    }
}
