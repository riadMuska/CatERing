import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTask;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.recipe.Procedure;
import businesslogic.recipe.Recipe;

import java.util.ArrayList;

public class Test_3 {
    public static void main(String[] args) throws UseCaseLogicException {

        ArrayList<SummarySheet> summaryList = SummarySheet.loadSummarySheets();
        for (SummarySheet sm: summaryList) {
            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
        }
        CatERing.getInstance().getKitchenTaskManager().chooseSummarySheet(summaryList.get(1));

        SummarySheet.loadTasks();
        ArrayList<KitchenTask> kitchenTaskArrayList = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTasks();
        for (KitchenTask t: kitchenTaskArrayList) {
            System.out.println(t.getId() + " "+ t.getProcedure().toString());
        }
        CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().moveTask(kitchenTaskArrayList.get(0),1);
        System.out.println("---------");
        kitchenTaskArrayList = CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getTasks();
        for (KitchenTask t: kitchenTaskArrayList) {
            System.out.println(t.getId() + " "+ t.getProcedure().toString());
        }
    }
}
