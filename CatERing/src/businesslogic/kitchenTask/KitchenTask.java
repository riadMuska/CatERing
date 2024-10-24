package businesslogic.kitchenTask;

import businesslogic.recipe.Procedure;
import businesslogic.recipe.Recipe;
import businesslogic.turn.Turn;
import businesslogic.user.User;

public class KitchenTask {


    private int id;
    private int timeStimated;
    private int quantity;
    private Procedure procedure;
    private Turn turn;

    private User cook;

    public KitchenTask(Procedure p){
        this.procedure=p;
    }

    public User getCook() {
        return cook;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public Turn getTurn() {
        return turn;
    }

    public KitchenTask setDetails(int quantity, int timeStimated){
        this.quantity=quantity;
        this.timeStimated=timeStimated;
        return this;
    }
    public void assignTurn(Turn turn){
        this.turn=turn;
    }
    public void assignCook(User cook){
        this.cook=cook;
    }

    @Override
    public String toString() {
        return "KitchenTask{" +
                "timeStimated=" + timeStimated +
                ", quantity=" + quantity +
                ", procedure=" + procedure +
                ", turn=" + turn +
                ", cook=" + cook +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
