import businesslogic.CatERing;
import businesslogic.turn.Turn;

import java.util.Date;

public class Test_5c_d {
    public static void main(String[] args) {
        Turn turn1 = new Turn(10.00,11.00,new Date());
        CatERing.getInstance().getKitchenTaskManager().addTurn(turn1);
        CatERing.getInstance().getKitchenTaskManager().setStatusSaturated(turn1,false);
        Turn turn2 = new Turn(14.00,19.00, new Date());
        CatERing.getInstance().getKitchenTaskManager().addTurn(turn2);
        CatERing.getInstance().getKitchenTaskManager().setStatusSaturated(turn2,true);
        Turn turn3 = new Turn(17.00,21.00, new Date());
        CatERing.getInstance().getKitchenTaskManager().addTurn(turn3);
        CatERing.getInstance().getKitchenTaskManager().setStatusSaturated(turn3,true);
        System.out.println(CatERing.getInstance().getKitchenTaskManager().getTurns());
    }
}
