package tester;


public enum ActionType {
   MOVE_MOUSE_TO(1), CLICK_TO_LINK(2), CLICK_TO_BUTTON(3), CLICK_TO_IMAGE(4), TYPE_TO_BOX(4), SELECT_OPTION(5), ENABLE_RADIO(6);

    private ActionType(int id) {
        this.id = id;
    }

    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ActionType{" +
                "id=" + id +
                '}';
    }
}

//
//public int ENTER = 0;
//   public int MOUSE = 1;
//   public int LINK = 2;
//   public int BUTTON = 3;
//   public int IMAGE = 4;
//   public int BOX = 5;
//   public int OPTION = 6;
//   public int RADIO = 7;
//
//   public String toString(int type) {
//       switch (type) {
//           case 0:
//               return "Enter";
//           case 1:
//               return "mouse";
//           case 2:
//               return "link";
//           case 3:
//               return "button";
//           case 4:
//               return "image";
//           case 5:
//               return "box";
//           case 6:
//               return "option";
//           case 7:
//               return "radio";
//           default:
//               return "default ";
//       }
//   }


