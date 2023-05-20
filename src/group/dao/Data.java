package group.dao;

import group.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static group.model.Contacts.contactList;
import static group.model.Countries.countriesList;
import static group.model.Customers.customerList;
import static group.model.FirstLevelDivisions.divisionList;
import static group.model.Users.usersList;
import static group.model.Appointments.apptsList;


public class Data {
    private String sql;


    /**
     * This function creates Users objects, populates each object with username and password data,
     * and adds each Users object to the usersList variable.
     * */
    public static void populateUsers() throws SQLException {
        String sql = "SELECT User_ID, User_Name, Password FROM users";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Users newUser = new Users();
            newUser.setUserID(rs.getInt(1));
            newUser.setUsername(rs.getString(2));
            newUser.setPassword(rs.getString(3));
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

    public static void populateCustomers() throws SQLException {
        String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID FROM client_schedule.customers";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Customers customer = new Customers();
            customer.setCustomerID(rs.getInt(1));
            customer.setCustomerName(rs.getString(2));
            customer.setAddress(rs.getString(3));
            customer.setPostalCode(rs.getString(4));
            customer.setPhoneNumber(rs.getString(5));
            customer.setDivisionID(rs.getInt(6));
            customerList.add(customer);
        }
    }

    public static int getNextCustomerID() throws SQLException {
        String sql = "SELECT max(Customer_ID) as Customer_ID FROM client_schedule.customers";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1) + 1;
    }

    public static void populateCountries() throws SQLException {
        String sql = "SELECT Country_ID, Country FROM client_schedule.countries";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Countries country = new Countries();
            country.setCountryID(rs.getInt(1));
            country.setCountryName(rs.getString(2));
            countriesList.add(country);
        }
    }

    public static void populateFirstLevelDivisions() throws SQLException {
        String sql = "SELECT Division_ID, Division, Country_ID FROM client_schedule.first_level_divisions";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            FirstLevelDivisions fld = new FirstLevelDivisions();
            fld.setDivisionID(rs.getInt(1));
            fld.setDivisionName(rs.getString(2));
            fld.setCountryID(rs.getInt(3));
            divisionList.add(fld);
        }
    }

    public static void addApptToDB(Appointments appt) throws SQLException {
        String sql = "INSERT INTO client_schedule.appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ps.setInt(1, appt.getAppointmentID());
        ps.setString(2, appt.getTitle());
        ps.setString(3, appt.getDescription());
        ps.setString(4, appt.getLocation());
        ps.setString(5, appt.getType());
        ps.setTimestamp(6, appt.getStartDateTime());
        ps.setTimestamp(7, appt.getEndDateTime());
        ps.setInt(8, appt.getCustomerID());
        ps.setInt(9, appt.getUserID());
        ps.setInt(10, appt.getContactID());
        ps.executeUpdate();
    }

    public static void updateApptToDB(Appointments appt) throws SQLException {
        String sql = "UPDATE client_schedule.appointments SET " +
                "Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ?" +
                " WHERE appointments.Appointment_ID = ?;";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ps.setString(1, appt.getTitle());
        ps.setString(2, appt.getDescription());
        ps.setString(3, appt.getLocation());
        ps.setString(4, appt.getType());
        ps.setTimestamp(5, appt.getStartDateTime());
        ps.setTimestamp(6, appt.getEndDateTime());
        ps.setInt(7, appt.getCustomerID());
        ps.setInt(8, appt.getUserID());
        ps.setInt(9, appt.getContactID());
        ps.setInt(10, appt.getAppointmentID());
        ps.executeUpdate();
    }

    public static void deleteApptFromDB (Appointments appt) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ps.setInt(1, appt.getAppointmentID());
        ps.executeUpdate();
    }

    public static void addCustomerToDB (Customers customer) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ps.setInt(1, customer.getCustomerID());
        ps.setString(2, customer.getCustomerName());
        ps.setString(3, customer.getAddress());
        ps.setString(4, customer.getPostalCode());
        ps.setString(5, customer.getPhoneNumber());
        ps.setInt(6, customer.getDivisionID());
        ps.executeUpdate();
    }

    public static void updateCustomerFromDB (Customers customer) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET " +
                "Customer_Name = ?, " +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Division_ID = ? " +
                "WHERE customers.Customer_ID = ?;";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ps.setString(1, customer.getCustomerName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhoneNumber());
        ps.setInt(5, customer.getDivisionID());
        ps.setInt(6, customer.getCustomerID());
        ps.executeUpdate();
    }

    public static void deleteCustomerFromDB (Customers customer) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.makePreparedStatement(sql, JDBC.getConnection());
        ps.setInt(1, customer.getCustomerID());
        ps.executeUpdate();
    }

}


