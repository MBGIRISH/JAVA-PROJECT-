
import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignupPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public SignupPage() {
        // Setup UI components
        setTitle("Signup");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        JButton signupButton = new JButton("Signup");
        signupButton.setBounds(10, 80, 80, 25);
        add(signupButton);

        signupButton.addActionListener(e -> signup());

        setVisible(true);
    }

    private void signup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (saveUserData(username, password)) {
            JOptionPane.showMessageDialog(this, "Signup successful!");
            dispose(); // Close the signup window
            new LoginPage(); // Open the login page
        } else {
            JOptionPane.showMessageDialog(this, "Error saving user data.");
        }
    }

    // Method to save user data to CSV
    private boolean saveUserData(String username, String password) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.csv", true))) {
            writer.write(username + "," + password);
            writer.newLine();
            return true; // Success
        } catch (IOException ex) {
            ex.printStackTrace();
            return false; // Failure
        }
    }

    public static void main(String[] args) {
        new SignupPage();
    }
}
