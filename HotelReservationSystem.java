import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private final int roomNumber;
    private final String category; // e.g., Single, Double, Suite
    private final double rate; // Rate per night
    private boolean isBooked;

    public Room(int roomNumber, String category, double rate) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.rate = rate;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getRate() {
        return rate;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        this.isBooked = true;
    }

    public void releaseRoom() {
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + category + "] - $" + rate + "/night" + 
               (isBooked ? " (Booked)" : " (Available)");
    }
}

class Booking {
    private final int roomNumber;
    private final String guestName;
    private final int nights;
    private final double totalCost;

    public Booking(int roomNumber, String guestName, int nights, double totalCost) {
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.nights = nights;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return """
               Booking Details:
               Guest Name: """ + guestName + "\n" +
               "Room Number: " + roomNumber + "\n" +
               "Nights: " + nights + "\n" +
               "Total Cost: $" + totalCost;
    }
}

public class HotelReservationSystem {
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ArrayList<Booking> bookings = new ArrayList<>();

    // Initialize sample rooms
    public HotelReservationSystem() {
        rooms.add(new Room(101, "Single", 200.0));
        rooms.add(new Room(102, "Double", 350.0));
        rooms.add(new Room(103, "Suite", 500.0));
        rooms.add(new Room(104, "Double", 250.0));
    }

    // Display all rooms
    public void displayRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    // Search for available rooms by category
    public void searchRooms(String category) {
        System.out.println("\nAvailable " + category + " Rooms:");
        boolean found = false;
        for (Room room : rooms) {
            if (room.getCategory().equalsIgnoreCase(category) && !room.isBooked()) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms in this category.");
        }
    }

    // Book a room
    public void bookRoom(int roomNumber, String guestName, int nights) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && !room.isBooked()) {
                double totalCost = nights * room.getRate();
                room.bookRoom();
                bookings.add(new Booking(roomNumber, guestName, nights, totalCost));
                System.out.println("Room " + roomNumber + " booked successfully for " + guestName + ".");
                return;
            }
        }
        System.out.println("Room not available or invalid room number.");
    }

    // View booking details
    public void viewBookingDetails(int roomNumber) {
        for (Booking booking : bookings) {
            if (booking.toString().contains("Room Number: " + roomNumber)) {
                System.out.println(booking);
                return;
            }
        }
        System.out.println("No bookings found for room " + roomNumber + ".");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HotelReservationSystem hotel = new HotelReservationSystem();

        System.out.println("Welcome to the Hotel Reservation System!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Display All Rooms");
            System.out.println("2. Search Rooms by Category");
            System.out.println("3. Book a Room");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> hotel.displayRooms();

                case 2 -> {
                    System.out.print("Enter room category (Single, Double, Suite): ");
                    String category = scanner.nextLine();
                    hotel.searchRooms(category);
                }

                case 3 -> {
                    System.out.print("Enter room number to book: ");
                    int roomNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.nextLine();
                    System.out.print("Enter number of nights: ");
                    int nights = scanner.nextInt();
                    hotel.bookRoom(roomNumber, guestName, nights);
                }

                case 4 -> {
                    System.out.print("Enter room number to view booking details: ");
                    int roomNum = scanner.nextInt();
                    hotel.viewBookingDetails(roomNum);
                }

                case 5 -> {
                    System.out.println("Thank you for using the Hotel Reservation System. Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
