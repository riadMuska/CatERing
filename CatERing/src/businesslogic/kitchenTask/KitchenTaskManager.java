package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.MenuEventReceiver;
import businesslogic.recipe.Preparation;
import businesslogic.recipe.Procedure;
import businesslogic.turn.Turn;
import businesslogic.turn.TurnManager;
import businesslogic.user.User;

import java.util.ArrayList;

public class KitchenTaskManager {

    private SummarySheet currentSummarySheet;

    public void chooseSummarySheet(SummarySheet summarySheet){
        this.currentSummarySheet = summarySheet;
    }

    public SummarySheet getCurrentSummarySheet(){
        return this.currentSummarySheet;
    }

    private ArrayList<KitchenTaskEventReceiver> eventReceivers;

    public KitchenTaskManager(){
        this.eventReceivers = new ArrayList<>();
    }

    public void generateSummarySheet(String name, ServiceInfo service) throws UseCaseLogicException {
        if(CatERing.getInstance().getUserManager().getCurrentUser()!=null && CatERing.getInstance().getUserManager().getCurrentUser().isChef()){
            SummarySheet summarySheet = new SummarySheet(name, service);
            this.currentSummarySheet = summarySheet;
            this.currentSummarySheet.setOwner(CatERing.getInstance().getUserManager().getCurrentUser());
            this.notifyGenerateSummarySheet(this.currentSummarySheet);
        }else{
            throw new UseCaseLogicException();
        }
    }

    public void deleteSummarySheet(SummarySheet s) throws UseCaseLogicException {
        if(CatERing.getInstance().getUserManager().getCurrentUser()!=null && CatERing.getInstance().getUserManager().getCurrentUser().isChef()){
            this.currentSummarySheet = null;
            this.notifyDeleteSummarySheet(s);
        }else{
            throw new UseCaseLogicException();
        }
    }

    public void notifyGenerateSummarySheet(SummarySheet s){
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummaryGenerated(s);
        }
    }

    public void notifyDeleteSummarySheet(SummarySheet s){
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummaryDeleted(s);
        }
    }

    public void addTask(Procedure p){
        KitchenTask new_task = new KitchenTask(p);
        this.currentSummarySheet.addTask(new_task);
        this.notifyTaskAdded(new_task, currentSummarySheet);
    }

    public void removeTask(KitchenTask task){
        this.currentSummarySheet.deleteTask(task);
        this.notifyTaskDeleted(task, currentSummarySheet);
    }

    public void notifyTaskAdded(KitchenTask task, SummarySheet summary){
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateTaskAdded(summary, task);
        }
    }

    public void notifyTaskDeleted(KitchenTask task, SummarySheet summary){
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateTaskDeleted(summary,task);
        }
    }

    public KitchenTask setDetails(KitchenTask task,int quantity,int timeStimated) throws UseCaseLogicException {
        if(this.currentSummarySheet!=null){
            return task.setDetails(quantity,timeStimated);
        }
        else{
            throw new UseCaseLogicException();
        }
    }

    public void deleteTask(KitchenTask task){
        this.currentSummarySheet.deleteTask(task);
    }

    public void moveTask(KitchenTask task, int position){
        this.currentSummarySheet.moveTask(task,position);
    }

    public ArrayList<Turn> getTurns(){
        return CatERing.getInstance().getTurnManager().getTurns();
    }

    public void addTurn(Turn t){
        CatERing.getInstance().getTurnManager().addTurn(t);
    }

    public void setStatusSaturated(Turn t, boolean saturated){
        CatERing.getInstance().getTurnManager().setStatusSaturated(t,saturated);
    }

    public void assignTask(Turn t, User cook, KitchenTask task) throws UseCaseLogicException {
        if(this.currentSummarySheet!=null){
            if(this.currentSummarySheet.containsTask(task)){
                if(t.getSaturationStatus()){
                    throw new UseCaseLogicException();
                }
                task.assignTurn(t);
                if(cook!=null){
                    if(!(cook.isTypeOfRole("CUOCO"))){
                        throw new UseCaseLogicException();
                    }
                    if(cook.getAvailability(t).equals("disponibile")){
                        task.assignCook(cook);
                    }
                    else{
                        throw new UseCaseLogicException();
                    }
                }
            }
            else {
                throw new UseCaseLogicException();
            }
        }
        else{
            throw new UseCaseLogicException();
        }
    }

    public void moveTaks(KitchenTask t, int position){
        this.currentSummarySheet.moveTask(t,position);
        notifyTasksRearranged(position);
    }

    public void addEventReceiver(KitchenTaskEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    public void removeEventReceiver(KitchenTaskEventReceiver rec) {
        this.eventReceivers.remove(rec);
    }

    private void notifyTasksRearranged(int position) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateTasksRearranged(currentSummarySheet,position);
        }
    }
}
