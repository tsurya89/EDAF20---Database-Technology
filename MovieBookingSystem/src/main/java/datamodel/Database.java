package datamodel;

import java.sql.*;
import java.util.*;

/**
 * Database is a class that specifies the interface to the 
 * movie database. Uses JDBC and the MySQL Connector/J driver.
 */
public class Database {
    /** 
     * The database connection.
     */
    private Connection conn;
        
    /**
     * Create the database interface object. Connection to the database
     * is performed later.
     */
    public Database() {
        conn = null;
    }
       
    /* --- TODO: Change this method to fit your choice of DBMS --- */
    /** 
     * Open a connection to the database, using the specified user name
     * and password.
     *
     * @param userName The user name.
     * @param password The user's password.
     * @return true if the connection succeeded, false if the supplied
     * user name and password were not recognized. Returns false also
     * if the JDBC driver isn't found.
     */
    public boolean openConnection(String userName, String password) {
        try {
        	// Connection strings for included DBMS clients:
        	// [MySQL]       jdbc:mysql://[host]/[database]
        	// [PostgreSQL]  jdbc:postgresql://[host]/[database]
        	// [SQLite]      jdbc:sqlite://[filepath]
        	
        	// Use "jdbc:mysql://puccini.cs.lth.se/" + userName if you using our shared server
        	// If outside, this statement will hang until timeout.
            conn = DriverManager.getConnection 
                ("jdbc:mysql://puccini.cs.lth.se/hbg67", userName, password);
        }
        catch (SQLException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
        
    /**
     * Close the connection to the database.
     */
    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e) {
        	e.printStackTrace();
        }
        conn = null;
        
        System.err.println("Database connection closed.");
    }
        
    /**
     * Check if the connection to the database has been established
     *
     * @return true if the connection has been established
     */
    public boolean isConnected() {
        return conn != null;
    }
	
  	public Show getShowData(String mTitle, String mDate) {
		Integer mFreeSeats = 42;
		String mVenue = "Kino 2";
		
		/* --- TODO: add code for database query --- */
        try {
            conn.setAutoCommit(false);
            String getTheaterNameString = "select theaterName, freeSeats from Performance where movieName = ? and performanceDate = ?";
            PreparedStatement getTheaterStatement = conn.prepareStatement(getTheaterNameString);
            getTheaterStatement.setString(1, mTitle);
            getTheaterStatement.setString(2, mDate);
            ResultSet result = getTheaterStatement.executeQuery();
            while (result.next()) {
                mVenue = result.getString("theaterName");
                mFreeSeats = result.getInt("freeSeats");
            }
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
		return new Show(mTitle, mDate, mVenue, mFreeSeats);
	}

    /* --- TODO: insert more own code here --- */
    // Validate a user attempting to login
    public boolean login(String username) {
        String getUsernameString = "select name from Person where username = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement getUsernameStmt = conn.prepareStatement(getUsernameString);
            getUsernameStmt.setString(1, username);
            ResultSet result = getUsernameStmt.executeQuery();
            conn.commit();
            conn.setAutoCommit(true);
            if (!result.next()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return true;
    }

    public List<String> getShowDates(String movieName) {
        List<String> showDates = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            String getDatesString = "select performanceDate from Performance where movieName = ?";
            PreparedStatement getDatesStmt = conn.prepareStatement(getDatesString);
            getDatesStmt.setString(1, movieName);
            ResultSet datesResult = getDatesStmt.executeQuery();
            conn.commit();
            conn.setAutoCommit(true);
            while(datesResult.next()) {
                String currDate = datesResult.getString("performanceDate");
                showDates.add(currDate);
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return showDates;
    }

    public List<Reservation> getReservationData(String username) {
        List<Reservation> reservationsList = new ArrayList<>();
        try {
            conn.setAutoCommit(false);
            String getReservationsString = "select reservationNum, movieName, reservationDate from Reservation where username = ?";
            PreparedStatement getReservationsStatement = conn.prepareStatement(getReservationsString);
            getReservationsStatement.setString(1, username);
            ResultSet reservationsResult = getReservationsStatement.executeQuery();
            conn.commit();
            conn.setAutoCommit(true);
            while (reservationsResult.next()) {
                System.out.println("GETTING RES RESULT");
                int resNum = reservationsResult.getInt("reservationNum");
                String movieName = reservationsResult.getString("movieName");
                String date = reservationsResult.getString("reservationDate");
                Show currentShowInfo = getShowData(movieName, date);
                Reservation currentReservation = new Reservation(resNum, movieName, date, currentShowInfo.getVenue());
                reservationsList.add(currentReservation);
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return reservationsList;
    }

    public List<String> getAllMovies() {
        List<String> movieNamesList = new ArrayList<>();
        try {
            String getMoviesString = "select * from Movie";
            PreparedStatement getMoviesStatement = conn.prepareStatement(getMoviesString);
            ResultSet moviesResult = getMoviesStatement.executeQuery();
            while (moviesResult.next()) {
                String movieName = moviesResult.getString("name");
                movieNamesList.add(movieName);
            }
            
        } catch (SQLException e) {
            try {
                conn.rollback();
                conn.setAutoCommit(true);
                e.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return movieNamesList;
    }
}
