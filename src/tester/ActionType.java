package tester;


public enum ActionType {
   MOVE_MOUSE_TO(1), CLICK_TO_LINK(2), CLICK_TO_BUTTON(3), CLICK_TO_IMAGE(4), TYPE_TO_BOX(5), SELECT_OPTION(6), ENABLE_RADIO(7);

    private ActionType(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

}
