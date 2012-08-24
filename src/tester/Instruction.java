package tester;

import java.util.ArrayList;


public class Instruction {
    private ArrayList<Action> actions;
    private int size;

    public Instruction() {
        size = 0;
        actions = new ArrayList<Action>();
    }

    public Instruction(Instruction instruction) {
        this.size = instruction.size;
        this.actions = new ArrayList<Action>(instruction.actions);
    }

    public int getSize() {
        return size;
    }

    public boolean add(Action action) {
        actions.add(action);
        size++;

        return true;
    }

    @Override
    public String toString() {
        if (actions == null) {
            return "null";
        }

        String result = "";

        if (size == 0) {
            return "";
        }

        for (Action action : actions) {
            result += action.toString();
            result += ", ";
        }

        String tmp = "" + result.charAt(0);
        result = tmp.toUpperCase() + result.substring(1, result.length() - 2) + ';';

        return result;
    }
}
