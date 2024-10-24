import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.kitchenTask.SummarySheet;

import java.util.ArrayList;

public class Test_1b {
    public static void main(String[] args) throws UseCaseLogicException {
        CatERing.getInstance().getUserManager().fakeLogin("Marinella");
        //Load di tutti i fogli. OK
        ArrayList<SummarySheet> list = SummarySheet.loadSummarySheets();
        for (SummarySheet sm: list) {
            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
        }

        //Elimino foglio. OK
        CatERing.getInstance().getKitchenTaskManager().deleteSummarySheet(list.get(0));
        list = SummarySheet.loadSummarySheets();
        for (SummarySheet sm: list) {
            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
        }
    }

}
