import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.user.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Test_1 {
    public static void main(String[] args) throws UseCaseLogicException {
//        //Test creazione nuovo foglio riepilogativo. OK
        CatERing.getInstance().getUserManager().fakeLogin("Marinella");
//        User owner = CatERing.getInstance().getUserManager().getCurrentUser();
//        ObservableList<ServiceInfo> services = ServiceInfo.loadServiceInfoForEvent(1);
//        ServiceInfo service = services.get(0);
//        CatERing.getInstance().getKitchenTaskManager().generateSummarySheet("testSummary2",service);



        //Load di tutti i fogli. OK
        ArrayList<SummarySheet> list = SummarySheet.loadSummarySheets();
        for (SummarySheet sm: list) {
            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
        }

        //Elimino foglio. OK
//        CatERing.getInstance().getKitchenTaskManager().deleteSummarySheet(list.get(0));
//        list = SummarySheet.loadSummarySheets();
//        for (SummarySheet sm: list) {
//            System.out.println(sm.getNome() +" "+ sm.getId() + " "+ sm.getService().toString());
//        }

    }
}
