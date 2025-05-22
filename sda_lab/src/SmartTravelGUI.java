import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.*;

class User {
    String name, email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

class Booking {
    static int counter = 1000;
    int bookingId;
    String travelType, destination;
    User user;

    public Booking(String travelType, String destination, User user) {
        this.bookingId = ++counter;
        this.travelType = travelType;
        this.destination = destination;
        this.user = user;
    }

    public String getBookingDetails() {
        return "Booking ID: " + bookingId +
                "\n:User  " + user.name + " | Email: " + user.email +
                "\nTravel Type: " + travelType +
                "\nDestination: " + destination;
    }
}

class BookingManager {
    java.util.List<Booking> bookings = new ArrayList<>(); // Use java.util.List

    public Booking createBooking(String travelType, String destination, User user) {
        Booking booking = new Booking(travelType, destination, user);
        bookings.add(booking);
        return booking;
    }
}

public class SmartTravelGUI extends JFrame implements ActionListener {
    JTextField nameField, emailField, destinationField;
    JComboBox<String> travelTypeBox;
    JButton bookButton;
    JTextArea resultArea;

    BookingManager manager = new BookingManager();

    public SmartTravelGUI() {
        setTitle("Smart Travel Booking");
        setSize(400, 400);
        setLayout(new GridLayout(7, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Travel Type:"));
        String[] travelOptions = {"Bus", "Train", "Flight"};
        travelTypeBox = new JComboBox<>(travelOptions);
        add(travelTypeBox);

        add(new JLabel("Destination:"));
        destinationField = new JTextField();
        add(destinationField);

        bookButton = new JButton("Book Ticket");
        bookButton.addActionListener(this);
        add(bookButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String travelType = (String) travelTypeBox.getSelectedItem();
        String destination = destinationField.getText().trim();

        if (name.isEmpty() || email.isEmpty() || destination.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User(name, email);
        Booking booking = manager.createBooking(travelType, destination, user);
        resultArea.setText("Booking Successful!\n\n" + booking.getBookingDetails());
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SmartTravelGUI::new);
    }
}
