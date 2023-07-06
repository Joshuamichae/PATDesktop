package Client_desktop;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class listbarang extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;

    public listbarang() {
        setTitle("List barang");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();

        // Create the table model
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID Barang");
        tableModel.addColumn("Nama Barang");
        tableModel.addColumn("Harga Barang");
        tableModel.addColumn("Hari Pembuatan");

        JButton backButton = new JButton("Back");
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            dispose(); // Close the ListbarangUI window
        });

        // Fetch data barang from the server
        try {
            URL url = new URL("http://localhost:8000/listbarang");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

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

                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(response);
                JSONArray dataArray = jsonObject.getJSONArray("response");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject listbarang = dataArray.getJSONObject(i);
                    int idbarang = listbarang.getInt("idbarang");
                    String namabarang = listbarang.getString("nama");
                    String hargabarang = listbarang.getString("harga");
                    String pembuatan = listbarang.getString("pembuatan");

                    // Add the data to the table model
                    tableModel.addRow(new Object[]{idbarang, namabarang, hargabarang, pembuatan});
                }
            } else {
                System.out.println("Failed to connect to the server. Response code: " + responseCode);
            }

            connection.disconnect();
        } catch (Exception ex) {
            System.out.println("Error occurred while connecting to the server.");
            ex.printStackTrace();
        }

        // Create the JTable and set the model
        table = new JTable(tableModel);

        // Create a scroll pane and add the table to it
        scrollPane = new JScrollPane(table);

        // Add the scroll pane to the frame
        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new listbarang().setVisible(true);
            }
        });
    }
}
