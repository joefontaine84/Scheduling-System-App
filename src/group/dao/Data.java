package group.dao;

import group.model.Appointments;
import group.model.Contacts;
import group.model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static group.model.Contacts.contactList;
import static group.model.Users.usersList;
import static group.model.Appointments.apptsList;


public class Data {
    private String sql;


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

    /**
     * This function creates Appointments objects, populates each object with appointmentID, title, description, location, type,
     * start, end, customerID, userID, and contactID parameters, in addition to adding each Appointments object to the apptsList variable.
     * */
    public static void populateAppointments() throws SQLException {
        String sql = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Appointments newAppts = new Appointments();
            newAppts.setAppointmentID(rs.getInt(1));
            newAppts.setTitle(rs.getString(2));
            newAppts.setDescription(rs.getString(3));
            newAppts.setLocation(rs.getString(4));
            newAppts.setType(rs.getString(5));
            newAppts.setStartDateTime(rs.getTimestamp(6));
            newAppts.setEndDateTime(rs.getTimestamp(7));
            newAppts.setCustomerID(rs.getInt(8));
            newAppts.setUserID(rs.getInt(9));
            newAppts.setContactID(rs.getInt(10));
            apptsList.add(newAppts);
        }
    }

    public static void populateContacts () throws SQLException {
        String sql = "SELECT Contact_ID, Contact_Name FROM client_schedule.contacts";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Contacts contact = new Contacts();
            contact.setContactID(rs.getInt(1));
            contact.setName(rs.getString(2));
            contactList.add(contact);
        }
    }


    public static int getNextAppointmentID () throws SQLException {
        String sql = "SELECT max(Appointment_ID) as Appointment_ID from client_schedule.appointments";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) + 1;
    }

}


