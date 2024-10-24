package businesslogic.recipe;

public class Preparation {
    private int id;
    private String name;

    public String toString() {
        return name;
    }

    public Preparation(String name){
        this.name=name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
