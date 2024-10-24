import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTask;
import businesslogic.recipe.Procedure;
import businesslogic.recipe.Recipe;
import businesslogic.turn.Turn;
import businesslogic.user.User;

import java.util.Date;

public class Test_5 {
    public static void main(String[] args) throws UseCaseLogicException {
        CatERing.getInstance().getUserManager().fakeLogin("Marinella");
        User u=CatERing.getInstance().getUserManager().getCurrentUser();
        System.out.println(u);
        ServiceInfo s= new ServiceInfo("Cena");
        CatERing.getInstance().getKitchenTaskManager().generateSummarySheet("foglietto",new ServiceInfo("ciao"));
        Procedure p = new Procedure(new Recipe("ragù"));
        CatERing.getInstance().getKitchenTaskManager().addTask(p);
        KitchenTask t = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTask(0);
        Turn turn= new Turn(10.00,11.00,new Date());
        u.setAvailability(turn,"disponibile");
        CatERing.getInstance().getKitchenTaskManager().assignTask(turn,u,t);
        System.out.println(t.toString());

    }
}
