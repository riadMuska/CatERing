package businesslogic.turn;

import java.util.ArrayList;

public class TurnManager {
    private ArrayList<Turn> turnTable;
    public TurnManager(){
        turnTable = new ArrayList<>();
    }

    public ArrayList<Turn> getTurns(){
        return this.turnTable;
    }

    public void addTurn(Turn t){
        turnTable.add(t);
    }

    public void removeTurn(Turn t){
        turnTable.remove(t);
    }

    public void setStatusSaturated(Turn t, boolean saturated){
        t.setStatusSaturated(saturated);
    }
}
