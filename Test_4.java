import businesslogic.CatERing;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTask;
import businesslogic.recipe.Procedure;
import businesslogic.recipe.Recipe;
import businesslogic.turn.Turn;
import businesslogic.user.User;

import java.util.Date;

public class Test_4 {
    public static void main(String[] args) {
        Turn turn1 = new Turn(10.00,11.00,new Date());
        CatERing.getInstance().getKitchenTaskManager().addTurn(turn1);
        Turn turn2 = new Turn(14.00,19.00,new Date());
        CatERing.getInstance().getKitchenTaskManager().addTurn(turn2);
        Turn turn3 = new Turn(17.00,21.00,new Date());
        CatERing.getInstance().getKitchenTaskManager().addTurn(turn3);
        System.out.println(CatERing.getInstance().getKitchenTaskManager().getTurns());
    }
}
