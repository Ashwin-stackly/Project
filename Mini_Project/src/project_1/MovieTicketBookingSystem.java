package project_1;
import java.util.Scanner;

public class MovieTicketBookingSystem {

    static Scanner sc = new Scanner(System.in);

    // Movie details
    static String[] movies = {
            "Master",
            "Leo",
            "Theri",
            "Gilli"
    };

    static boolean[] movieAvailable = {
            true,
            true,
            true,
            false
    };
    static boolean[][][] seats = new boolean[4][5][8];

    static String[][][] customerNames = new String[4][5][8];

    static double weekdayPrice = 150.0;
    static double weekendPrice = 200.0;

    static double[] totalRating = new double[4];
    static int[] ratingCount = new int[4];


    public static void main(String[] args) {

        int choice;

        do {

            System.out.println("\n======================================");
            System.out.println("      MOVIE TICKET BOOKING SYSTEM");
            System.out.println("======================================");

            System.out.println("1. Show Movie Availability");
            System.out.println("2. Show Available Seats");
            System.out.println("3. Book Ticket");
            System.out.println("4. Cancel Ticket");
            System.out.println("5. View Booking Details");
            System.out.println("6. Rate Movie");
            System.out.println("7. Show Movie Rating");
            System.out.println("8. Exit");

            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showMovieAvailability();
                    break;

                case 2:
                    showAvailableSeats();
                    break;

                case 3:
                    bookTicket();
                    break;

                case 4:
                    cancelTicket();
                    break;

                case 5:
                    viewBookingDetails();
                    break;

                case 6:
                    rateMovie();
                    break;

                case 7:
                    showMovieRating();
                    break;

                case 8:
                    System.out.println(
                            "\nThank you for using Movie Ticket Booking System!"
                    );
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 8);

        sc.close();
    }


    // 1. SHOW MOVIE AVAILABILITY

    static void showMovieAvailability() {

        System.out.println("\n========== MOVIE AVAILABILITY ==========");

        for (int i = 0; i < movies.length; i++) {

            System.out.print(movies[i]);

            if (movieAvailable[i]) {
                System.out.println(" - Available");
            } else {
                System.out.println(" - Not Available");
            }
        }

        System.out.println("========================================");
    }


    // 2. SHOW AVAILABLE SEATS

    static void showAvailableSeats() {

        showMovieAvailability();

        System.out.print("\nEnter movie name: ");
        String movieName = sc.next();

        int movieIndex = findMovie(movieName);

        if (movieIndex == -1) {
            System.out.println("Movie not found!");
            return;
        }

        if (!movieAvailable[movieIndex]) {
            System.out.println("Sorry! This movie is not available.");
            return;
        }

        displaySeats(movieIndex);
    }


    // DISPLAY SEATS


    static void displaySeats(int movieIndex) {

        System.out.println("\n========== SEAT LAYOUT ==========");

        System.out.println("Movie: " + movies[movieIndex]);

        System.out.println();

        System.out.println(
                "       1   2   3   4   5   6   7   8"
        );

        for (int row = 0; row < seats[movieIndex].length; row++) {

            System.out.print("Row " + (row + 1) + " ");

            for (int seat = 0;
                 seat < seats[movieIndex][row].length;
                 seat++) {

                if (seats[movieIndex][row][seat]) {

                    System.out.print("[X] ");

                } else {

                    System.out.print("[O] ");
                }
            }

            System.out.println();
        }

        System.out.println();

        System.out.println("O = Available");
        System.out.println("X = Booked");
    }

    // 3. BOOK TICKET


    static void bookTicket() {

        showMovieAvailability();

        System.out.print("\nEnter movie name: ");
        String movieName = sc.next();

        int movieIndex = findMovie(movieName);

        if (movieIndex == -1) {
            System.out.println("Movie not found!");
            return;
        }

        if (!movieAvailable[movieIndex]) {
            System.out.println("Sorry! This movie is not available.");
            return;
        }

        System.out.println(
                "\nSelected Movie: " + movies[movieIndex]
        );

        displaySeats(movieIndex);

        sc.nextLine();

        System.out.print("Enter customer name: ");
        String customerName = sc.nextLine();

        if (customerName.trim().isEmpty()) {

            System.out.println(
                    "Customer name cannot be empty."
            );

            return;
        }

        System.out.print("Enter number of tickets: ");
        int ticketCount = sc.nextInt();

        if (ticketCount <= 0) {

            System.out.println(
                    "Ticket count must be greater than 0."
            );

            return;
        }

        int availableSeats = countAvailableSeats(movieIndex);

        if (ticketCount > availableSeats) {

            System.out.println(
                    "Not enough seats available."
            );

            System.out.println(
                    "Available seats: " + availableSeats
            );

            return;
        }


        // WEEKEND PRICING

        System.out.print(
                "Is today weekend? (yes/no): "
        );

        String day = sc.next();

        double ticketPrice;

        if (day.equalsIgnoreCase("yes")) {

            ticketPrice = weekendPrice;

        } else if (day.equalsIgnoreCase("no")) {

            ticketPrice = weekdayPrice;

        } else {

            System.out.println(
                    "Please enter only yes or no."
            );

            return;
        }


        // Total amount
        double totalAmount =
                ticketPrice * ticketCount;


        // DISCOUNT COUPON

        double discount = 0.0;

        System.out.print(
                "Do you have a discount coupon? (yes/no): "
        );

        String couponChoice = sc.next();

        if (couponChoice.equalsIgnoreCase("yes")) {

            System.out.print("Enter coupon code: ");
            String coupon = sc.next();

            if (coupon.equalsIgnoreCase("MOVIE10")) {

                discount =
                        totalAmount * 10.0 / 100.0;

                System.out.println(
                        "10% discount applied."
                );

            } else if (coupon.equalsIgnoreCase("MOVIE20")) {

                discount =
                        totalAmount * 20.0 / 100.0;

                System.out.println(
                        "20% discount applied."
                );

            } else {

                System.out.println(
                        "Invalid coupon code."
                );
            }

        } else if (!couponChoice.equalsIgnoreCase("no")) {

            System.out.println(
                    "Please enter only yes or no."
            );

            return;
        }


        // Final amount
        double finalAmount =
                totalAmount - discount;


        // SELECT SEATS

        for (int i = 0; i < ticketCount; i++) {

            System.out.println(
                    "\nSelect Seat " + (i + 1)
            );

            displaySeats(movieIndex);

            System.out.print(
                    "Enter row number (1-5): "
            );

            int row = sc.nextInt();

            System.out.print(
                    "Enter seat number (1-8): "
            );

            int seat = sc.nextInt();


            // Check row and seat
            if (row < 1 || row > 5 ||
                    seat < 1 || seat > 8) {

                System.out.println(
                        "Invalid row or seat number."
                );

                i--;
                continue;
            }


            int rowIndex = row - 1;
            int seatIndex = seat - 1;


            // Check whether seat is booked
            if (seats[movieIndex][rowIndex][seatIndex]) {

                System.out.println(
                        "This seat is already booked."
                );

                i--;

            } else {

                seats[movieIndex][rowIndex][seatIndex] = true;

                customerNames[movieIndex][rowIndex][seatIndex]
                        = customerName;

                System.out.println(
                        "Seat " + row + "-" + seat +
                                " booked successfully."
                );
            }
        }

        // BOOKING SUMMARY

        System.out.println("\n======================================");
        System.out.println("           BOOKING SUMMARY");
        System.out.println("======================================");

        System.out.println(
                "Customer Name : " + customerName
        );

        System.out.println(
                "Movie         : " + movies[movieIndex]
        );

        System.out.println(
                "Tickets       : " + ticketCount
        );

        System.out.printf(
                "Ticket Price  : ₹%.2f%n",
                ticketPrice
        );

        System.out.printf(
                "Total Amount  : ₹%.2f%n",
                totalAmount
        );

        System.out.printf(
                "Discount      : ₹%.2f%n",
                discount
        );

        System.out.printf(
                "Final Amount  : ₹%.2f%n",
                finalAmount
        );

        System.out.println("======================================");

        System.out.println(
                "        Booking Successful!"
        );

        System.out.println("======================================");
    }


    // 4. CANCEL TICKET

    static void cancelTicket() {

        showMovieAvailability();

        System.out.print("\nEnter movie name: ");
        String movieName = sc.next();

        int movieIndex = findMovie(movieName);

        if (movieIndex == -1) {

            System.out.println("Movie not found!");

            return;
        }

        if (!movieAvailable[movieIndex]) {

            System.out.println(
                    "This movie is not available."
            );

            return;
        }

        displaySeats(movieIndex);

        System.out.print(
                "\nEnter row number: "
        );

        int row = sc.nextInt();

        System.out.print(
                "Enter seat number: "
        );

        int seat = sc.nextInt();


        if (row < 1 || row > 5 ||
                seat < 1 || seat > 8) {

            System.out.println(
                    "Invalid row or seat number."
            );

            return;
        }


        int rowIndex = row - 1;
        int seatIndex = seat - 1;


        if (!seats[movieIndex][rowIndex][seatIndex]) {

            System.out.println(
                    "This seat is not booked."
            );

            return;
        }


        System.out.println(
                "Customer: " +
                        customerNames[movieIndex][rowIndex][seatIndex]
        );


        System.out.print(
                "Are you sure you want to cancel? (yes/no): "
        );

        String confirm = sc.next();


        if (confirm.equalsIgnoreCase("yes")) {

            seats[movieIndex][rowIndex][seatIndex] = false;

            customerNames[movieIndex][rowIndex][seatIndex] = null;

            System.out.println(
                    "Ticket cancelled successfully."
            );

        } else if (confirm.equalsIgnoreCase("no")) {

            System.out.println(
                    "Cancellation cancelled."
            );

        } else {

            System.out.println(
                    "Please enter only yes or no."
            );
        }
    }

    // 5. VIEW BOOKING DETAILS

    static void viewBookingDetails() {

        System.out.println(
                "\n========== BOOKING DETAILS =========="
        );

        boolean found = false;

        for (int movie = 0; movie < movies.length; movie++) {

            for (int row = 0; row < seats[movie].length; row++) {

                for (int seat = 0;
                     seat < seats[movie][row].length;
                     seat++) {

                    if (seats[movie][row][seat]) {

                        found = true;

                        System.out.println(
                                "Movie    : " + movies[movie]
                        );

                        System.out.println(
                                "Customer : " +
                                        customerNames[movie][row][seat]
                        );

                        System.out.println(
                                "Row      : " + (row + 1)
                        );

                        System.out.println(
                                "Seat     : " + (seat + 1)
                        );

                        System.out.println(
                                "--------------------------------"
                        );
                    }
                }
            }
        }

        if (!found) {

            System.out.println(
                    "No bookings available."
            );
        }
    }


    // 6. RATE MOVIE


    static void rateMovie() {

        showMovieAvailability();

        System.out.print(
                "\nEnter movie name: "
        );

        String movieName = sc.next();

        int movieIndex = findMovie(movieName);

        if (movieIndex == -1) {

            System.out.println("Movie not found!");

            return;
        }

        if (!movieAvailable[movieIndex]) {

            System.out.println(
                    "This movie is not available."
            );

            return;
        }

        System.out.print(
                "Enter rating for " +
                        movies[movieIndex] +
                        " (1-5): "
        );

        double rating = sc.nextDouble();


        if (rating < 1 || rating > 5) {

            System.out.println(
                    "Rating must be between 1 and 5."
            );

            return;
        }


        totalRating[movieIndex] =
                totalRating[movieIndex] + rating;

        ratingCount[movieIndex]++;


        System.out.println(
                "Thank you for rating " +
                        movies[movieIndex] + "!"
        );
    }


    // 7. SHOW MOVIE RATING


    static void showMovieRating() {

        showMovieAvailability();

        System.out.print(
                "\nEnter movie name: "
        );

        String movieName = sc.next();

        int movieIndex = findMovie(movieName);

        if (movieIndex == -1) {

            System.out.println("Movie not found!");

            return;
        }

        if (ratingCount[movieIndex] == 0) {

            System.out.println(
                    "No ratings available for " +
                            movies[movieIndex]
            );

            return;
        }


        double averageRating =
                totalRating[movieIndex] /
                        ratingCount[movieIndex];


        System.out.println(
                "\n========== MOVIE RATING =========="
        );

        System.out.println(
                "Movie: " + movies[movieIndex]
        );

        System.out.printf(
                "Average Rating: %.2f / 5%n",
                averageRating
        );

        System.out.println(
                "Total Ratings: " +
                        ratingCount[movieIndex]
        );
    }


    // COUNT AVAILABLE SEATS

    static int countAvailableSeats(int movieIndex) {

        int count = 0;

        for (int row = 0;
             row < seats[movieIndex].length;
             row++) {

            for (int seat = 0;
                 seat < seats[movieIndex][row].length;
                 seat++) {

                if (!seats[movieIndex][row][seat]) {

                    count++;
                }
            }
        }

        return count;
    }


    // FIND MOVIE

    static int findMovie(String movieName) {

        for (int i = 0; i < movies.length; i++) {

            if (movies[i].equalsIgnoreCase(movieName)) {

                return i;
            }
        }

        return -1;
    }
}
