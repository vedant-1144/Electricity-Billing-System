package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class MeterInfoViewer extends JFrame implements ItemListener, ActionListener {

    Choice meternumber;
    JLabel lblLocation, lblMeterType, lblCity, lblState;
    JButton btnUpdate, btnClose;
    String selectedMeterNumber;

    MeterInfoViewer() {
        setSize(700, 500);
        setLocationRelativeTo(null); // Center the frame on the screen

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(240, 240, 240)); // Light gray background
        add(p);

        JLabel heading = new JLabel("Meter Information");
        heading.setBounds(100, 10, 500, 40); // Increase width to center the text
        heading.setFont(new Font("Tahoma", Font.BOLD, 24)); // Bold font
        p.add(heading);

        JLabel lblmeternumber = new JLabel("Select Meter Number:");
        lblmeternumber.setBounds(50, 80, 200, 20); // Adjusted position and size
        p.add(lblmeternumber);

        meternumber = new Choice();
        meternumber.addItemListener(this);
        try {
            Connection connection = Connect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT meter_number FROM Consumer");
            while (rs.next()) {
                meternumber.add(rs.getString("meter_number"));
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        meternumber.setBounds(250, 80, 200, 20); // Adjusted position
        p.add(meternumber);

        // Labels to display fetched details
        lblLocation = createLabel("Location:", 50, 130);
        p.add(lblLocation);

        lblMeterType = createLabel("Meter Type:", 50, 160);
        p.add(lblMeterType);

        lblCity = createLabel("Phase Code:", 50, 190);
        p.add(lblCity);

        lblState = createLabel("Bill Type:", 50, 220);
        p.add(lblState);

        btnUpdate = createButton("Update", 200, 300);
        p.add(btnUpdate);

        btnClose = createButton("Close", 350, 300);
        p.add(btnClose);

        btnUpdate.addActionListener(this);
        btnClose.addActionListener(this);

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 120, 20); // Adjusted size
        label.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Font size 14
        return label;
    }

    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 100, 30); // Adjusted size
        return button;
    }

    private void displayMeterDetails(String selectedMeterNumber) {
        this.selectedMeterNumber = selectedMeterNumber;
        // Fetch and display meter details based on the selected meter number
        if (selectedMeterNumber != null) {
            try (Connection connection = Connect.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM MeterInfo WHERE meter_number = ?")) {
                statement.setString(1, selectedMeterNumber);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        lblLocation.setText("Location: " + rs.getString("meter_location"));
                        lblMeterType.setText("Meter Type: " + rs.getString("meter_type"));
                        lblCity.setText("Phase Code: " + rs.getString("phase_code"));
                        lblState.setText("Bill Type: " + rs.getString("bill_type"));
                    } else {
                        // No data found for the selected meter number
                        JOptionPane.showMessageDialog(null, "No data available for the selected meter number.");
                        // Navigate to meterinfo() method with meter number as parameter
                        meterinfo(selectedMeterNumber);
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error occurred while fetching meter details.");
                e.printStackTrace();
            }
        }
    }

    private void meterinfo(String meterNumber) {
        // Implement redirection logic to the meter info page with meter number as parameter
        System.out.println("Redirecting to meter info page with meter number: " + meterNumber);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String selectedMeterNumber = meternumber.getSelectedItem();
            displayMeterDetails(selectedMeterNumber);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnUpdate) {
            if (selectedMeterNumber != null) {
                // Redirect to update page with the selected meter number
                new MeterInfo(selectedMeterNumber);
                // Implement redirection logic here
                System.out.println("Redirect to update page for meter number: " + selectedMeterNumber);
            } else {
                JOptionPane.showMessageDialog(null, "Please select a meter number first.");
            }
        } else if (e.getSource() == btnClose) {
            dispose(); // Close the frame
        }
    }

    public static void main(String[] args) {
        new MeterInfoViewer();
    }
}
