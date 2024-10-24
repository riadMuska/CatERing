package businesslogic.recipe;

import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.user.User;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Procedure {

    private int id;
    private Recipe recipe;
    private Preparation preparation;

    private String description;

    @Override
    public String toString() {
        if(recipe != null){
            return "Recipe: " + this.recipe;
        } else if (preparation != null) {
            return "Preparation: " + this.preparation;
        }
        return  "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Procedure(Recipe recipe){
        this.recipe=recipe;
    }
    public Procedure(Preparation preparation){
        this.preparation=preparation;
    }

    public static ArrayList<Procedure> loadProcedures() {
        ArrayList<Procedure> procedures = new ArrayList<>();
        String query = "SELECT * FROM procedures JOIN recipes ON procedures.recipe=recipes.id JOIN preparation ON preparation.id_preparation=procedures.preparation";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException{
                Procedure p;
                if(rs.getInt("recipes.id")!=-1){
                    Recipe r = new Recipe(rs.getString("recipes.name"));
                    r.setId(rs.getInt("recipes.id"));
                    p=new Procedure(r);
                }
                else{
                    Preparation pr = new Preparation(rs.getString("preparation.name"));
                    pr.setId(rs.getInt("preparation.id_preparation"));
                    p=new Procedure(pr);
                }
                p.setId(rs.getInt("procedures.id_procedure"));
                procedures.add(p);
            }
        });
        return procedures;
    }
}
