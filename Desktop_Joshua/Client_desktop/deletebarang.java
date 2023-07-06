package Client_desktop;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class deletebarang extends JFrame {
    private JTextField namabarangField;
    private JTextField hargabarangField;
    private JTextField idbarangField;
    private JComboBox<String> pembuatanField;

    public deletebarang() {
        setTitle("Delete Barang");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        JPanel buttonPanel = new JPanel();

        JLabel idbarangLabel = new JLabel("ID Barang:");
        idbarangField = new JTextField();
        JLabel namabarangLabel = new JLabel("Nama Barang:");
        namabarangField = new JTextField();
        JLabel hargabarangLabel = new JLabel("Harga Barang:");
        hargabarangField = new JTextField();
        JLabel pembuatanLabel = new JLabel("Hari Pembuatan:");
        pembuatanField = new JComboBox<>(new String[]{"Senin", "Selasa", "Rabu", "Kamis", "Jumat"});

        JButton backButton = new JButton("Back");
        JButton cancelButton = new JButton("Delete");

        panel.add(idbarangLabel);
        panel.add(idbarangField);
        panel.add(namabarangLabel);
        panel.add(namabarangField);
        panel.add(hargabarangLabel);
        panel.add(hargabarangField);
        panel.add(pembuatanLabel);
        panel.add(pembuatanField);
        panel.add(new JLabel());
        buttonPanel.add(backButton);
        buttonPanel.add(cancelButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            dispose(); // Close the DeletebarangUI window
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idbarang = idbarangField.getText();
                String namabarang = namabarangField.getText();
                String hargabarang = hargabarangField.getText();
                String pembuatan = pembuatanField.getSelectedItem().toString();

                try {
                    // Create the JSON object for delete barang data
                    JSONObject cancelData = new JSONObject();
                    cancelData.put("idbarang", idbarang);
                    cancelData.put("nama", namabarang);
                    cancelData.put("harga", hargabarang);
                    cancelData.put("pembuatan", pembuatan);

                    // Create the URL for delete barang endpoint
                    URL url = new URL("http://localhost:8000/hapus");

                    // Create the HttpURLConnection
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // Send the delete barang data
                    OutputStream os = connection.getOutputStream();
                    os.write(cancelData.toString().getBytes());
                    os.flush();

                    // Check the response code
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder responseBuilder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                        }
                        String response = responseBuilder.toString();
                        System.out.println("Server Response: " + response);

                        // Show a message dialog based on the response
                        if (response.equals("Berhasil menghapus data barang.")) {
                            JOptionPane.showMessageDialog(null, "Berhasil menghapus data barang.");
                            opendeletebarangMainMenu();
                        } else {
                            JOptionPane.showMessageDialog(null, "ID atau Nama atau Harga Barang salah. Coba lagi.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to connect to the JSON server. Response code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error occurred while connecting to the JSON server.");
                    ex.printStackTrace();
                }
            }
        });
    }

    private void opendeletebarangMainMenu() {
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new deletebarang().setVisible(true);
            }
        });
    }
}
