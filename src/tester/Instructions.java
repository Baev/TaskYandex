package tester;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.PrintWriter;
import java.util.*;

public class Instructions {
    private ArrayList<Instruction> instructions;
    private static HashSet<WebElement> usedLinks = new HashSet<WebElement>();
    private static HashSet<WebElement> visibleLinks = new HashSet<WebElement>();

    public Instructions() {
        instructions = new ArrayList<Instruction>();
    }

    public void findForms(WebDriver driver) {

        List<WebElement> elements = driver.findElements(By.tagName("form"));

        for (WebElement element : elements) {

            LinkedList<List<Action>> form = new LinkedList<List<Action>>();
            form.addAll(findTextElements(element));
            form.addAll(findRadioElements(element));
            form.addAll(findSelectElements(element));

            ArrayList<Integer> size = new ArrayList<Integer>();
            for (List<Action> list : form) {
                size.add(list.size() + 1);
            }

            List<Action> temp = findSubmitElements(element);
            int submitIndex = -1;
            int submitSize = temp.size();
            if (temp.size() > 0) {
                form.add(temp);
                submitIndex = size.size();
                size.add(submitSize);
            }

            TestGetter testGetter = new TestGetter(size);
            testGetter.run();

            if (submitIndex != -1) {
                size.set(submitIndex, submitSize + 1);
            }

            for (int array[] : testGetter.answers) {
                int j = 0;
                Instruction instruction = new Instruction();
                for (List<Action> list : form) {
                    int index = array[j];

                    if (index == size.get(j++)) {
                        continue;
                    }

                    Action tmp = list.get(index - 1);
                    instruction.add(tmp);
                }
                if (instruction.getSize() > 0) {
                    instructions.add(instruction);
                }

            }
        }
    }

    private List<List<Action>> findRadioElements(WebElement element) {
        List<List<Action>> result = new LinkedList<List<Action>>();
        HashMap<String, Integer> radio = new HashMap<String, Integer>();

        List<WebElement> input = element.findElements(By.tagName("input"));
        for (WebElement e : input) {
            String type = e.getAttribute("type");

            if (type.equals("radio")) {
                String name = e.getAttribute("name");
                Action newAction = new Action(ActionType.ENABLE_RADIO, e);
                if (radio.containsKey(name)) {
                    int index = radio.get(name);
                    result.get(index).add(newAction);
                } else {
                    radio.put(name, result.size());
                    List<Action> tmp = new LinkedList<Action>();
                    tmp.add(newAction);
                    result.add(tmp);
                }
            }
        }

        return result;
    }

    private List<List<Action>> findTextElements(WebElement element) {
        List<List<Action>> result = new LinkedList<List<Action>>();
        String badTypes = "#radio #submit #reset #image #hidden #checkbox";

        List<WebElement> input = element.findElements(By.tagName("input"));
        for (WebElement e : input) {
            String type = e.getAttribute("type");
            if (!badTypes.contains(type)) {
                List<Action> tmp = new LinkedList<Action>();
                tmp.add(new Action(ActionType.TYPE_TO_BOX, e));
                result.add(tmp);
            }
        }

        List<WebElement> textarea = element.findElements(By.tagName("textarea"));
        for (WebElement e : textarea) {
            List<Action> tmp = new LinkedList<Action>();
            tmp.add(new Action(ActionType.TYPE_TO_BOX, e));
            result.add(tmp);
        }

        return result;
    }

    private List<List<Action>> findSelectElements(WebElement element) {
        List<List<Action>> result = new LinkedList<List<Action>>();

        List<WebElement> select = element.findElements(By.tagName("select"));
        for (WebElement e : select) {
            Select s = new Select(e);
            List<WebElement> options = s.getOptions();
            List<Action> tmp = new LinkedList<Action>();
            for (WebElement w : options) {
                tmp.add(new Action(ActionType.SELECT_OPTION, w));
            }
            result.add(tmp);
        }

        return result;
    }

    private List<Action> findSubmitElements(WebElement element) {
        List<Action> result = new LinkedList<Action>();

        List<WebElement> inputTags = element.findElements(By.tagName("input"));
        for (WebElement e : inputTags) {
            String tmp = e.getAttribute("type");
            if (tmp.equals("submit") || tmp.equals("reset")) {
                result.add(new Action(ActionType.CLICK_TO_BUTTON, e));
            }
            if (tmp.equals("image")) {
                result.add(new Action(ActionType.CLICK_TO_IMAGE, e));
            }
        }

        List<WebElement> tmp = element.findElements(By.tagName("button"));
        for (WebElement e : tmp) {
            result.add(new Action(ActionType.CLICK_TO_BUTTON, e));
        }

        return result;
    }

    public void findLinks(WebDriver driver) {
        try {
            List<WebElement> tmp = driver.findElements(By.tagName("a"));
            List<WebElement> elements = new LinkedList<WebElement>();
            for (WebElement w : tmp) {
                if (w.isEnabled() && w.isDisplayed()) {
                    elements.add(w);
                    visibleLinks.add(w);
                }
            }

            for (WebElement w : elements) {
                if (w.isEnabled() && w.isDisplayed()) {
                    try {
                        WebElement webElement = w.findElement(By.xpath(".."));
                        Instruction instruction = new Instruction();

                        List<WebElement> list = webElement.findElements(By.xpath("//li"));

                        if (list.size() > 0) {
                            try {
                                processLink(webElement, instruction, new Actions(driver), true);
                                continue;
                            } catch (WebDriverException e) {
                                System.out.print(e.getMessage());
                            }

                            if (!usedLinks.contains(w) && w.getText().length() > 0) {
                                usedLinks.add(w);
                                instruction.add(new Action(ActionType.CLICK_TO_LINK, w));
                                instructions.add(instruction);
                            }

                        }
                    } catch (WebDriverException e) {
                        System.out.print(e.getMessage());
                    }
                }
            }
        } catch (WebDriverException e) {
            System.out.print(e.getMessage());
            throw new LinkFinderException();

        }
    }

    private void processLink(WebElement element, Instruction instruction, Actions actions, boolean f) throws LinkFinderException {
        try {
            if (f) {
                List<WebElement> a = element.findElements(By.xpath("a"));

                for (WebElement w : a) {
                    if (usedLinks.contains(w)) {
                        continue;
                    }
                    if (w.isEnabled() && w.isDisplayed()) {
                        usedLinks.add(w);
                        try {
                            actions.moveToElement(w).perform();
                        } catch (WebDriverException e) {
                            continue;
                        }

                        if (w.getText().length() == 0) {
                            continue;
                        }

                        Action action2 = new Action(ActionType.CLICK_TO_LINK, w);

                        Instruction tmp = new Instruction();
                        if (!visibleLinks.contains(w)) {
                            tmp = new Instruction(instruction);
                        }

                        tmp.add(action2);
                        instructions.add(tmp);

                        Action action1 = new Action(ActionType.CLICK_TO_LINK, w);
                        instruction.add(action1);

                    }
                }
            }

            List<WebElement> elements1 = element.findElements(By.xpath("*"));
            for (WebElement w : elements1) {
                if (w.getTagName().equals("ul") || w.getTagName().equals("ol") || w.getTagName().equals("li")) {
                    processLink(w, new Instruction(instruction), actions, !f);
                }
            }
        } catch (WebDriverException e) {
            System.out.print(e.getMessage());
            throw new LinkFinderException();
        }
    }

    public void write(PrintWriter pw) {
        for (Instruction i : instructions) {
            pw.println(i.toString());
        }
    }

    public int getSize() {
        return instructions.size();
    }

    public void findImages(WebDriver driver) {

        List<WebElement> elements = driver.findElements(By.xpath("//a//img"));
        for (WebElement w : elements) {
            if (!usedLinks.contains(w) && w.isEnabled() && w.isDisplayed()) {
                Instruction instruction = new Instruction();
                instruction.add(new Action(ActionType.CLICK_TO_IMAGE, w));
                instructions.add(instruction);
            }
        }


    }
}
