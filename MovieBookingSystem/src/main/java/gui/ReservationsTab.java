package gui;

import java.util.Arrays;
import java.util.List;

import datamodel.Reservation;
import datamodel.CurrentUser;
import datamodel.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReservationsTab {
	@FXML private TableView<Reservation> tableReservations = new TableView<>();
	
	private Database db;
	
	public void setDatabase(Database db) {
		this.db = db;
	}
	
	@SuppressWarnings("unchecked")
	public void initialize() {
		System.out.println("Initializing BookingListTab");
		
		// Create Column ID		
		TableColumn<Reservation, Integer> bookingIdColumn = new TableColumn<>("ID");
		bookingIdColumn.setPrefWidth(75);
		bookingIdColumn.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("bookingId"));

		// Create Column MovieTitle
		TableColumn<Reservation, String> movieTitleColumn = new TableColumn<>("Movie Title");
		movieTitleColumn.setPrefWidth(150);
		movieTitleColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("movieTitle"));
		
		// Create Column Date		
		TableColumn<Reservation, String> performanceDateColumn = new TableColumn<>("Date");
		performanceDateColumn.setPrefWidth(120);
		performanceDateColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("performanceDate"));
		
		// Create Column Theatre Name
		TableColumn<Reservation, String> theatreNameColumn = new TableColumn<>("Theatre Name");
		theatreNameColumn.setPrefWidth(150);
		theatreNameColumn.setCellValueFactory(new PropertyValueFactory<Reservation, String>("theatreName"));
		
		//Insert all columns
		tableReservations.getColumns()
						 .addAll(bookingIdColumn,
								 movieTitleColumn, 
								 performanceDateColumn,
								 theatreNameColumn);
	}
	
	/**
	 * Refresh the table view, by getting and replacing the tables content.
	 */
	public void updateList() {
		/* --- TODO: replace with own code using the database object instead --- */
		System.out.println("Update booking list called.");
		
		List<Reservation> bookings = db.getReservationData(CurrentUser.instance().getCurrentUserId());
		System.out.printf("Number of bookings: %d\n", bookings.size());
		for (Reservation r : bookings) {
			System.out.println(r.getBookingId());
		}
		tableReservations.getItems().setAll(bookings);
	}
}
