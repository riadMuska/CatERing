package businesslogic.turn;

import businesslogic.UseCaseLogicException;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.user.User;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Turn {
    private int id;
    private double start_time;
    private double end_time;
    private boolean saturationState=false;
    private Date turnDay;

    @Override
    public String toString() {
        return "Turn{" +
                "day=" + turnDay.toString() +
                "start_time=" + start_time +
                ", end_time=" + end_time +
                ", saturationState=" + saturationState +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStart_time() {
        return start_time;
    }

    public void setStart_time(double start_time) {
        this.start_time = start_time;
    }

    public double getEnd_time() {
        return end_time;
    }

    public void setEnd_time(double end_time) {
        this.end_time = end_time;
    }

    public Date getTurnDay() {
        return turnDay;
    }

    public void setTurnDay(Date turnDay) {
        this.turnDay = turnDay;
    }

    public Turn(double start_time, double end_time, Date day) {
        this.turnDay=day;
        this.start_time = start_time;
        this.end_time = end_time;
    }
    public void setStatusSaturated(boolean state){
        this.saturationState=state;
    }
    public boolean getSaturationStatus(){
        return this.saturationState;
    }

    public static ArrayList<Turn> loadTurn() {
        ArrayList<Turn> turnArrayList = new ArrayList<>();
        String query = "SELECT * FROM turn";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException, UseCaseLogicException {
                Turn turn;
                turn = new Turn(rs.getDouble("start_time"),rs.getDouble("end_time"),rs.getDate("turn_day"));
                turn.setId(rs.getInt("id_turn"));
                if(rs.getInt("saturated") == 0)
                    turn.setStatusSaturated(false);
                else
                    turn.setStatusSaturated(true);
                turnArrayList.add(turn);
            }
        });
        return turnArrayList;
    }

}
