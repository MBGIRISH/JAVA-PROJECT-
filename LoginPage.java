
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        // Setup UI components
        setTitle("Login");
        setSize(300, 200);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 165, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        add(loginButton);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Call method to load user data from CSV and check credentials
        if (loadUserData(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            dispose(); // Close the login window
            new Main().setVisible(true); // Open the main application
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.");
        }
    }

    // Method to load user data and check credentials
    private boolean loadUserData(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials.length == 2 && credentials[0].equals(username) && credentials[1].equals(password)) {
                    return true; // Valid user found
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error reading user data.");
        }
        return false; // No matching credentials found
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}
