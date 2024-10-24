import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTask;
import businesslogic.kitchenTask.KitchenTaskEventReceiver;
import businesslogic.kitchenTask.KitchenTaskManager;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.recipe.Procedure;
import businesslogic.recipe.Recipe;
import businesslogic.turn.Turn;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Test_2b {

    public static void main(String[] args) throws UseCaseLogicException {

        //Load fogli. OK
        ArrayList<SummarySheet> summaryList = SummarySheet.loadSummarySheets();
        for (SummarySheet sm: summaryList) {
            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
        }
        CatERing.getInstance().getKitchenTaskManager().chooseSummarySheet(summaryList.get(1));
        System.out.println("-------------");
        SummarySheet.loadTasks();
        ArrayList<KitchenTask> kitchenTaskArrayList = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTasks();
        for (KitchenTask t: kitchenTaskArrayList) {
            System.out.println(t.getId() + " "+ t.getProcedure().toString());
        }
//
//
//        //Load Task
//        ArrayList<KitchenTask> kitchenTaskArrayList1 = SummarySheet.loadTasks();
//        for (int i = 0; i < kitchenTaskArrayList1.size(); i++) {
//            System.out.println(kitchenTaskArrayList1.get(i));
//        }
//
//        //Elimino task dal foglio

        CatERing.getInstance().getKitchenTaskManager().removeTask(kitchenTaskArrayList.get(0));
//
//
//
        System.out.println("--------------");
        kitchenTaskArrayList = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTasks();
        for (KitchenTask t: kitchenTaskArrayList) {
            System.out.println(t.getId() + " "+ t.getProcedure().toString());
        }



//        ObservableList<ServiceInfo> services = ServiceInfo.loadServiceInfoForEvent(1);
//        ServiceInfo service = services.get(0);
//        CatERing.getInstance().getKitchenTaskManager().generateSummarySheet("testSummary2",service);
//        CatERing.getInstance().getKitchenTaskManager().addTask(new Procedure(new Recipe("ragù")));
//        CatERing.getInstance().getKitchenTaskManager().addTask(new Procedure(new Recipe("panna")));
//        System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().toString());
    }


}
