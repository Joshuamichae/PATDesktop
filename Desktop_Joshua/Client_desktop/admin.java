package Client_desktop;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class admin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public admin() {
        setTitle("Admin Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        JPanel buttonPanel = new JPanel();

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Register button action listener
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Booking button
                register register = new register();
                register.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try {
                    // Create the JSON object for loginuser data
                    JSONObject loginData = new JSONObject();
                    loginData.put("username", username);
                    loginData.put("password", password);

                    // Create the URL for loginuser endpoint
                    URL url = new URL("http://localhost:8000/admin");

                    // Create the HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Send the loginuser data
                    OutputStream os = connection.getOutputStream();
                    os.write(loginData.toString().getBytes());
                    os.flush();

                    // Check the response code
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        JOptionPane.showMessageDialog(null, "Login Berhasil!");

                        // Open the main menu
                        openMainMenu();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Gagal. Response code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while connecting to the JSON server.");
                    ex.printStackTrace();
                }
            }
        });
    }

    private void openMainMenu() {
        dispose();
        // Create the main menu frame
        JFrame mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainMenuFrame.setSize(400, 300);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setLayout(new GridLayout(5, 1));

        // Create the menu buttons
        JButton listbarangButton = new JButton("List Barang");
        JButton tambahbarangButton = new JButton("Tambah Barang");
        JButton editbarangButton = new JButton("Edit Barang");
        JButton deletebarangButton = new JButton("Delete barang");
        JButton logoutButton = new JButton("Logout");

        // Add the buttons to the main menu frame
        mainMenuFrame.add(listbarangButton);
        mainMenuFrame.add(tambahbarangButton);
        mainMenuFrame.add(editbarangButton);
        mainMenuFrame.add(deletebarangButton);
        mainMenuFrame.add(logoutButton);

        // List barang button action listener
        listbarangButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the List barang button
                listbarang listbarang = new listbarang();
                listbarang.setVisible(true);
            }
        });

        // Tambah barang Button action listener
        tambahbarangButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Tambah barang button
                tambahbarang tambahbarang = new tambahbarang();
                tambahbarang.setVisible(true);
            }
        });

        // Edit barang Button action listener
        editbarangButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Edit barang button
                editbarang editbarang = new editbarang();
                editbarang.setVisible(true);
            }
        });

        // Delete barang Button action listener
        deletebarangButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the action for the Delete barang button
                deletebarang deletebarang = new deletebarang();
                deletebarang.setVisible(true);
            }
        });

        // Logout Button
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainMenuFrame.dispose();
                new admin().setVisible(true);
            }
        });
        // Display the main menu frame
        mainMenuFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }
}
