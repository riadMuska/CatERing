import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTaskManager;
import businesslogic.kitchenTask.SummarySheet;

public class Test_1a {
    public static void main(String[] args) throws UseCaseLogicException {
        ServiceInfo s= new ServiceInfo("Cena");
        CatERing.getInstance().getKitchenTaskManager().generateSummarySheet("prova foglio",s);
        CatERing.getInstance().getKitchenTaskManager().generateSummarySheet("secondo foglio",s);
        System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getNome());
        CatERing.getInstance().getKitchenTaskManager().chooseSummarySheet(new SummarySheet("ciao",s));
        System.out.println(CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getNome());


    }
}
