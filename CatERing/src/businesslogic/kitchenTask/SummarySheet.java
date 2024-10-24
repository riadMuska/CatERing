package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.recipe.Preparation;
import businesslogic.recipe.Procedure;
import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.ArrayList;

public class SummarySheet {

    private int id;

    private User owner;

//    private EventInfo event;
    private ServiceInfo service;

    public ServiceInfo getService() {
        return service;
    }

    public void setService(ServiceInfo service) {
        this.service = service;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    private String nome;

    @Override
    public String toString() {
        String s = "SummarySheet{";
        for (KitchenTask t : tasks){
            s += t + ",";
        }
        s += '}';
        return s;
    }

    private ArrayList<KitchenTask>tasks;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SummarySheet(String nome, ServiceInfo s) throws UseCaseLogicException {
            this.nome=nome;
            this.service=s;
            this.tasks=new ArrayList<>();
//            this.tasks = this.loadTasks();
    }

    public void addTask(KitchenTask task){
        this.tasks.add(task);
    }

    public ArrayList<KitchenTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<KitchenTask> tasks) {
        this.tasks = tasks;
    }

    public void deleteTask(KitchenTask task){
        this.tasks.remove(task);
    }

    public void moveTask(KitchenTask task, int position){
        this.tasks.remove(task);
        this.tasks.add(position,task);
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

//    public void setEvent(EventInfo event) {
//        this.event = event;
//    }

    public KitchenTask getTask(int position){
        return this.tasks.get(position);
    }

    public boolean containsTask(KitchenTask t){
        return this.tasks.contains(t);
    }

    public static void saveSummary(SummarySheet s){
        String sumInsert = "INSERT INTO catering.summarysheet (owner, service) VALUES (" +
                s.owner.getId() + ", " +
                s.getService().getId() +
                ");";
        PersistenceManager.executeUpdate(sumInsert);
    }

    public static void deleteSummary(SummarySheet s){
        String sumInsert = "DELETE FROM catering.summarysheet WHERE id_summary = " + s.getId();
        PersistenceManager.executeUpdate(sumInsert);
    }

    public static void saveTaskAssigned(SummarySheet summary, KitchenTask task){
        int idUser;
        if(task.getCook() == null)
            idUser = -1;
        else
            idUser = task.getCook().getId();
        String taskInsert = "INSERT INTO catering.kitchentask (proc,turn,user) VALUES (" + task.getProcedure().getId()
                +", "+ task.getTurn().getId() + ", " + idUser +")";
        int id = PersistenceManager.executeUpdate(taskInsert);
        task.setId(id);
        String sheetTaskInsert = "INSERT INTO catering.kitchentasksheet (task,sheet) VALUES ("+task.getId()+","+summary.getId()+")";
        PersistenceManager.executeUpdate(sheetTaskInsert);
    }

    public static void saveTaskAdded(SummarySheet summary, KitchenTask task){
        String taskInsert = "INSERT INTO catering.kitchentask (proc,turn,user) VALUES (" + task.getProcedure().getId()
                +", -1,-1)";
        int id = PersistenceManager.executeUpdate(taskInsert);
        task.setId(id);
        String sheetTaskInsert = "INSERT INTO catering.kitchentasksheet (task,sheet) VALUES ("+task.getId()+","+summary.getId()+")";
        PersistenceManager.executeUpdate(sheetTaskInsert);
    }

    public static void saveTaskDeleted(SummarySheet summary, KitchenTask task){
        String taskDeleted = "DELETE FROM catering.kitchentasksheet WHERE task = " +task.getId()+" AND sheet= "+ summary.getId() ;
        PersistenceManager.executeUpdate(taskDeleted);
    }

    public static ArrayList<SummarySheet> loadSummarySheets() {
        ArrayList<SummarySheet> summarySheets = new ArrayList<>();
        String query = "SELECT * FROM summarysheet JOIN services ON summarysheet.service = services.id";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                ServiceInfo service = new ServiceInfo(rs.getString("name"));
                SummarySheet summary = new SummarySheet(rs.getString("name"),service);
                summary.setOwner(new User(rs.getInt("owner")));
                summary.setId(rs.getInt("id_summary"));
                summarySheets.add(summary);
            }
        });
        return summarySheets;
    }

//    public static ArrayList<Integer> loadTasks() {
//        ArrayList<Integer> kitchenTaskArrayList = new ArrayList<>();
//        String query = "SELECT * FROM kitchentasksheet JOIN kitchentask ON task = id_task WHERE sheet = " +
//                CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getId();
//        PersistenceManager.executeQuery(query, new ResultHandler() {
//            @Override
//            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
//                kitchenTaskArrayList.add(rs.getInt("task"));
//            }
//        });
////        CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().setTasks(kitchenTaskArrayList);
//        return kitchenTaskArrayList;
//    }


    public static ArrayList<KitchenTask> loadTasks() {
        ArrayList<KitchenTask> kitchenTaskArrayList = new ArrayList<>();
        String query = "SELECT * FROM kitchentasksheet JOIN kitchentask ON task = id_task JOIN procedures ON proc = " +
                "id_procedure JOIN recipes ON id = recipe JOIN preparation " +
                "ON id_preparation = preparation WHERE sheet = " + CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().getId();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                Recipe recipe = null;
                Preparation preparation = null;
                KitchenTask kitchenTask;
                if(rs.getInt("recipes.id")!=-1){
                    recipe = new Recipe(rs.getString("recipes.name"));
                    recipe.setId(rs.getInt("recipes.id"));
                }else{
                    preparation = new Preparation(rs.getString("preparation.name"));
                    preparation.setId(rs.getInt("id_preparation"));
                }
                if(recipe != null){
                    kitchenTask = new KitchenTask(new Procedure(recipe));
                }else{
                    kitchenTask = new KitchenTask(new Procedure(preparation));
                }
                kitchenTask.setId(rs.getInt("id_task"));
                kitchenTaskArrayList.add(kitchenTask);
            }
        });
        CatERing.getInstance().getKitchenTaskManager().getCurrentSummarySheet().setTasks(kitchenTaskArrayList);
        return kitchenTaskArrayList;
    }

    public static void saveTaskOrdered(SummarySheet s,int p){
        String upd = "UPDATE kitchentasksheet SET position = "+p+" WHERE id = "+s.getId();
        PersistenceManager.executeQuery(upd);
    }



}
