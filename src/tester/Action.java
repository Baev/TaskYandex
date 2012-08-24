package tester;

import org.openqa.selenium.WebElement;


public class Action {
    private Integer type;
    private String text;
    private String tagName;
    private String link;
    private String value;
    private String name;
    private static int[] num = {1, 1, 1, 1, 1, 1, 1};

    public Action(ActionType actionType, WebElement w) {
        int i = actionType.getId();
        if (i == ActionType.CLICK_TO_IMAGE.getId()) {
            text = w.getAttribute("alt");
            if (text == null || text.length() == 0) {
                text = w.getAttribute("title");
            }
        } else {
            this.text = w.getText();
        }
        this.link = w.getAttribute("href");
        this.tagName = w.getTagName();
        this.type = i;
        this.name = w.getAttribute("name");
        this.value = w.getAttribute("value");
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    private void updateText() {

        if (!ifString(text)) {
            if (ifString(value)) {
                text = value;
            } else {
                if (ifString(name)) {
                    text = name;
                } else {
                    text = typeToString(type) + String.valueOf(num[type]++);
                }
            }
        }
    }

    private boolean ifString(String s) {
        return (s != null && s.length() > 0);
    }

    public String getLink() {
        return link;
    }

    public String getTagName() {

        return tagName;
    }

    public Integer getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    private String typeToString(int type) {
        switch (type) {
                   case 0:
                       return "Enter";
                   case 1:
                       return "mouse";
                   case 2:
                       return "link";
                   case 3:
                       return "button";
                   case 4:
                       return "image";
                   case 5:
                       return "box";
                   case 6:
                       return "option";
                   case 7:
                       return "radio";
                   default:
                       return "default ";
               }
    }

    @Override
    public String toString() {
        updateText();
        String newText = "\"" + text + "\"";

        if (type == 0) {
            return "press " + typeToString(type);
        }
        if (type == 1) {
            return "move " + typeToString(type) + " to " + newText;
        }
        if (type == 5) {
            return "type to " + typeToString(type) + " " + newText + " some text";
        }
        if (type == 6) {
            return "select " + typeToString(type) + " " + newText;
        }

        return "click to " + typeToString(type) + " " + newText;
    }
}
