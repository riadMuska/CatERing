package businesslogic.user;

import businesslogic.UseCaseLogicException;
import businesslogic.turn.Turn;
import javafx.collections.FXCollections;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class User {

    private static Map<Integer, User> loadedUsers = FXCollections.observableHashMap();
    public static enum Role {SERVIZIO, CUOCO, CHEF, ORGANIZZATORE};
    private int id;
    private String username;
    private HashMap<Turn,String> availability;
    private Set<Role> roles;

    /*public Set<String> getRoles() {
        Set<String> newroles = null;
        for(Role role: roles){
            newroles.add(role.toString());
        }
        return newroles;
    }*/

    public User(int id) {
        this.id = id;
        username = "";
        this.roles = new HashSet<>();
        this.availability=new HashMap<>();
    }

    public boolean isChef() {
        return roles.contains(Role.CHEF);
    }

    public String getUserName() {
        return username;
    }

    public int getId() {
        return this.id;
    }

    public String toString() {
        String result = username;
        if (roles.size() > 0) {
            result += ": ";

            for (User.Role r : roles) {
                result += r.toString() + " ";
            }
        }
        return result;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static User loadUserById(int uid) {
        if (loadedUsers.containsKey(uid)) return loadedUsers.get(uid);

        User load = new User(0);
        String userQuery = "SELECT * FROM Users WHERE id='"+uid+"'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.username = rs.getString("username");
            }
        });
        if (load.id > 0) {
            loadedUsers.put(load.id, load);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + load.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    String role = rs.getString("role_id");
                    switch (role.charAt(0)) {
                        case 'c':
                            load.roles.add(User.Role.CUOCO);
                            break;
                        case 'h':
                            load.roles.add(User.Role.CHEF);
                            break;
                        case 'o':
                            load.roles.add(User.Role.ORGANIZZATORE);
                            break;
                        case 's':
                            load.roles.add(User.Role.SERVIZIO);
                    }
                }
            });
        }
        return load;
    }

    public static User loadUser(String username) {
        User u = new User(0);
        String userQuery = "SELECT * FROM Users WHERE username='"+username+"'";
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                u.id = rs.getInt("id");
                u.username = rs.getString("username");
            }
        });
        if (u.id > 0) {
            loadedUsers.put(u.id, u);
            String roleQuery = "SELECT * FROM UserRoles WHERE user_id=" + u.id;
            PersistenceManager.executeQuery(roleQuery, new ResultHandler() {
                @Override
                public void handle(ResultSet rs) throws SQLException {
                    String role = rs.getString("role_id");
                    switch (role.charAt(0)) {
                        case 'c':
                            u.roles.add(User.Role.CUOCO);
                            break;
                        case 'h':
                            u.roles.add(User.Role.CHEF);
                            break;
                        case 'o':
                            u.roles.add(User.Role.ORGANIZZATORE);
                            break;
                        case 's':
                            u.roles.add(User.Role.SERVIZIO);
                    }
                }
            });
        }
        return u;
    }

    /*public void setRoles(String[] roles) throws UseCaseLogicException {
        boolean role_founded=false;
        for (String r: roles) {
            for(Staff.Role role: Staff.Role.values()){
                if(!Objects.equals(r, role.toString())){
                    role_founded=true;
                }else{
                    this.roles.add(role.toString());
                }
            }
        }
        if(!role_founded){
            throw new UseCaseLogicException();
        }
    }*/
    public String getAvailability(Turn t){
        return this.availability.get(t);
    }
    public boolean isTypeOfRole(String role_to_check){
        boolean founded=false;
        for(int i=0;i<this.roles.size() && !founded;i++){
            if(this.roles.toArray()[i].toString().equals(role_to_check)){
                founded=true;
            }
        }
        return founded;
    }
    public void setAvailability(Turn t,String availability){
        this.availability.put(t,availability);
    }
}
