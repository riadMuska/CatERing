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

public class Test_2 {

    public static void main(String[] args) throws UseCaseLogicException {

        //Load fogli. OK
        ArrayList<SummarySheet> summaryList = SummarySheet.loadSummarySheets();
//        for (SummarySheet sm: summaryList) {
//            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
//        }
        CatERing.getInstance().getKitchenTaskManager().chooseSummarySheet(summaryList.get(1));
        ArrayList<KitchenTask> kitchenTaskArrayList = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTasks();
        for (KitchenTask t: kitchenTaskArrayList) {
            System.out.println(t.getId() + " "+ t.getProcedure().toString());
        }

        //Load Turn
//        ArrayList<Turn> turnArrayList = Turn.loadTurn();
//        for (Turn t: turnArrayList) {
//            System.out.println(t.getId() +" "+ t.getSaturationStatus() + " "+ t.getEnd_time() + " " + t.getStart_time() + " " + t.getTurnDay());
//        }

        //Load Task
        ArrayList<KitchenTask> kitchenTasks = SummarySheet.loadTasks();
        for (int i = 0; i < kitchenTasks.size(); i++) {
            System.out.println(kitchenTasks.get(i));
        }

        //Load Procedures. OK
        ArrayList<Procedure> procedureArrayList = Procedure.loadProcedures();
//        for (Procedure procedure: procedureArrayList) {
//            System.out.println(procedure.toString());
//        }
//        CatERing.getInstance().getKitchenTaskManager().addTask(new Procedure(new Recipe("ragù")));
        CatERing.getInstance().getKitchenTaskManager().addTask(procedureArrayList.get(0));
        kitchenTaskArrayList = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTasks();
        for (KitchenTask t: kitchenTaskArrayList) {
            System.out.println(t.getId() + " "+ t.getProcedure().toString());
        }

        kitchenTasks = SummarySheet.loadTasks();
        for (int i = 0; i < kitchenTasks.size(); i++) {
            System.out.println(kitchenTasks.get(i));
        }



//        ObservableList<ServiceInfo> services = ServiceInfo.loadServiceInfoForEvent(1);
//        ServiceInfo service = services.get(0);
//        CatERing.getInstance().getKitchenTaskManager().generateSummarySheet("testSummary2",service);
//        CatERing.getInstance().getKitchenTaskManager().addTask(new Procedure(new Recipe("ragù")));
//        CatERing.getInstance().getKitchenTaskManager().addTask(new Procedure(new Recipe("panna")));
//        System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().toString());
    }


}
