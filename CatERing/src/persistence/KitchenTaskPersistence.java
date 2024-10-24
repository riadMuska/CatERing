package persistence;

import businesslogic.kitchenTask.KitchenTask;
import businesslogic.kitchenTask.KitchenTaskEventReceiver;
import businesslogic.kitchenTask.KitchenTaskManager;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.menu.Menu;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {
    @Override
    public void updateSummaryGenerated(SummarySheet s) {
        SummarySheet.saveSummary(s);
    }

    @Override
    public void updateSummaryDeleted(SummarySheet s) {
        SummarySheet.deleteSummary(s);
    }

    @Override
    public void updateTaskAdded(SummarySheet s, KitchenTask t) {
        SummarySheet.saveTaskAdded(s,t);
    }

    @Override
    public void updateTaskDeleted(SummarySheet s,KitchenTask t) {
        SummarySheet.saveTaskDeleted(s,t);
    }

    @Override
    public void updateTasksRearranged(SummarySheet summary, int position) {
        SummarySheet.saveTaskOrdered(summary,position);
    }

    @Override
    public void updateSaturatedSatuta(KitchenTask t) {

    }

    @Override
    public void updateDetailSet(SummarySheet s, KitchenTask t) {

    }
}
