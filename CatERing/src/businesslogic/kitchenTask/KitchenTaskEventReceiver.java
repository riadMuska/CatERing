package businesslogic.kitchenTask;

public interface KitchenTaskEventReceiver {
    public void updateSummaryGenerated(SummarySheet s);


    public void updateSummaryDeleted(SummarySheet s);

//    public void updateSummaryRipristinated(SummarySheet s);

    public void updateTaskAdded(SummarySheet s, KitchenTask t);

    public void updateTaskDeleted(SummarySheet s, KitchenTask t);

    public void updateTasksRearranged(SummarySheet s, int position);

    public void updateSaturatedSatuta(KitchenTask t);

    public void updateDetailSet(SummarySheet s,KitchenTask t);
}
