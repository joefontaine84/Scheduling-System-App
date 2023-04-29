package group.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Data {
    private String sql;
    private static ArrayList<Users> usersList = new ArrayList<Users>();

    /**
     * This function creates Users objects, populates each object with username and password data,
     * and adds each Users object to the usersList variable.
     * */
    public static void populateUsers() throws SQLException {
        String sql = "SELECT User_Name, Password FROM users";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Users newUser = new Users();
            newUser.setUsername(rs.getString(1));
            newUser.setPassword(rs.getString(2));
            usersList.add(newUser);
        }


    }
}


