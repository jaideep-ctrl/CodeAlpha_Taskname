import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class HotelReservationSystem {
    private List<Room> rooms;
    private List<Reservation> reservations;
    private Scanner scanner;

    public HotelReservationSystem() {
        this.scanner = new Scanner(System.in);
        // Load data from files
        this.rooms = DataHandler.loadRooms();
        this.reservations = DataHandler.loadReservations();
        
        // If no rooms exist (first run), initialize some sample rooms
        if (this.rooms.isEmpty()) {
            initializeRooms();
        }
    }

    private void initializeRooms() {
        rooms.add(new Room(101, "Standard", 100.00, true));
        rooms.add(new Room(102, "Standard", 100.00, true));
        rooms.add(new Room(201, "Deluxe", 150.00, true));
        rooms.add(new Room(202, "Deluxe", 150.00, true));
        rooms.add(new Room(301, "Suite", 250.00, true));
        rooms.add(new Room(302, "Suite", 250.00, true));
        DataHandler.saveRooms(rooms);
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Search for available rooms");
            System.out.println("2. Make a reservation");
            System.out.println("3. Cancel a reservation");
            System.out.println("4. View my bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        searchRooms();
                        break;
                    case 2:
                        makeReservation();
                        break;
                    case 3:
                        cancelReservation();
                        break;
                    case 4:
                        viewBookings();
                        break;
                    case 5:
                        System.out.println("Exiting system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private void searchRooms() {
        System.out.println("\n--- Available Rooms ---");
        boolean found = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No rooms are currently available.");
        }
    }

    private void makeReservation() {
        System.out.println("\n--- Make a Reservation ---");
        System.out.print("Enter room number to book: ");
        int roomNumber = Integer.parseInt(scanner.nextLine());

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room " + roomNumber + " is either not available or does not exist.");
            return;
        }

        System.out.print("Enter your name: ");
        String guestName = scanner.nextLine();
        System.out.print("Enter number of nights: ");
        int nights = Integer.parseInt(scanner.nextLine());

        // Payment Simulation
        double totalCost = selectedRoom.getPricePerNight() * nights;
        System.out.printf("Total cost for your stay is: $%.2f\n", totalCost);
        System.out.print("Proceed with payment? (yes/no): ");
        String paymentChoice = scanner.nextLine();

        if (paymentChoice.equalsIgnoreCase("yes")) {
            String reservationId = UUID.randomUUID().toString().substring(0, 8); // Unique ID
            Reservation newReservation = new Reservation(reservationId, roomNumber, guestName, nights);
            reservations.add(newReservation);
            selectedRoom.setAvailable(false);

            DataHandler.saveRooms(rooms);
            DataHandler.saveReservations(reservations);

            System.out.println("\nReservation successful!");
            System.out.println(newReservation);
        } else {
            System.out.println("Reservation canceled by user.");
        }
    }

    private void cancelReservation() {
        System.out.println("\n--- Cancel a Reservation ---");
        System.out.print("Enter reservation ID: ");
        String reservationId = scanner.nextLine();

        Reservation reservationToRemove = null;
        for (Reservation res : reservations) {
            if (res.getReservationId().equals(reservationId)) {
                reservationToRemove = res;
                break;
            }
        }

        if (reservationToRemove != null) {
            Room associatedRoom = null;
            for (Room room : rooms) {
                if (room.getRoomNumber() == reservationToRemove.getRoomNumber()) {
                    associatedRoom = room;
                    break;
                }
            }

            if (associatedRoom != null) {
                associatedRoom.setAvailable(true);
            }
            reservations.remove(reservationToRemove);

            DataHandler.saveRooms(rooms);
            DataHandler.saveReservations(reservations);
            System.out.println("Reservation " + reservationId + " has been canceled.");
        } else {
            System.out.println("Reservation not found.");
        }
    }

    private void viewBookings() {
        System.out.println("\n--- Your Bookings ---");
        if (reservations.isEmpty()) {
            System.out.println("You have no active reservations.");
            return;
        }
        for (Reservation res : reservations) {
            System.out.println(res);
        }
    }

    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();
        system.run();
    }
}