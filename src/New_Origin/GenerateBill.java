package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateBill extends JFrame implements ActionListener {

    String meter;
    JButton bill;
    Choice cmonth;
    JTextArea area;

    GenerateBill(String meter) {
        this.meter = meter;

        setSize(500, 600);
        setLocation(550, 30);

        setLayout(new BorderLayout());

        JPanel panel = new JPanel();

        JLabel heading = new JLabel("Generate Bill");
        JLabel meternumber = new JLabel("Meter Number: " + meter);

        cmonth = new Choice();

        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");

        area = new JTextArea(50, 15);
        area.setText("\n\n\t--------Click on the---------\n\t Generate Bill Button to get\n\tthe bill of the Selected Month");
        area.setFont(new Font("Senserif", Font.ITALIC, 18));

        JScrollPane pane = new JScrollPane(area);

        bill = new JButton("Generate Bill");
        bill.addActionListener(this);

        panel.add(heading);
        panel.add(meternumber);
        panel.add(cmonth);
        add(panel, BorderLayout.NORTH);
        add(pane, BorderLayout.CENTER);
        add(bill, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String month = cmonth.getSelectedItem();
        try {
            Connection connection = Connect.getConnection();
            String query = "SELECT * FROM Consumer WHERE meter_number = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, meter);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                area.setText("\tSST Power Limited\nELECTRICITY BILL GENERATED FOR THE MONTH\n\tOF " + month + ", 2024\n\n\n");
                area.append("\n    Customer Name: " + rs.getString("name"));
                area.append("\n    Meter Number   : " + rs.getString("meter_number"));
                area.append("\n    Address             : " + rs.getString("address"));
                area.append("\n    City                 : " + rs.getString("city"));
                area.append("\n    State                : " + rs.getString("state"));
                area.append("\n    Email                : " + rs.getString("email"));
                area.append("\n    Phone                 : " + rs.getString("phone"));
                area.append("\n---------------------------------------------------\n\n");
            } else {
                area.setText("\nNo customer information found for the meter number " + meter);
                return;
            }

            query = "SELECT * FROM MeterInfo WHERE meter_number= ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, meter);
            rs = statement.executeQuery();

            if (rs.next()) {
                area.append("\n    Meter Location: " + rs.getString("meter_location"));
                area.append("\n    Meter Type:     " + rs.getString("meter_type"));
                area.append("\n    Phase Code:        " + rs.getString("phase_code"));
                area.append("\n    Bill Type:          " + rs.getString("bill_type"));
//                area.append("\n    Days:                " + rs.getString("days"));
                area.append("\n---------------------------------------------------\n\n");
            } else {
                area.append("\nMeter information not available.");
                return;
            }

            query = "SELECT * FROM Tax";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();

            if (rs.next()) {
                area.append("\n    Cost Per Unit: " + rs.getString("cost_per_unit"));
                area.append("\n    Meter Rent: " + rs.getString("meter_rent"));
                area.append("\n    Service Charge: " + rs.getString("service_charge"));
                area.append("\n    Service Tax: " + rs.getString("service_tax"));
                area.append("\n    Swacch Bharat Cess: " + rs.getString("swacch_bharat_cess"));
                area.append("\n    Fixed Tax: " + rs.getString("fixed_tax"));
                area.append("\n\n");
            } else {
                area.append("\nTax information not available.");
                return;
            }

            query = "SELECT * FROM Bill WHERE meter_number = ? AND month = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, meter);
            statement.setString(2, month);
            rs = statement.executeQuery();

            if (rs.next()) {
                area.append("\n    Current Month: " + rs.getString("month"));
                area.append("\n    Units Consumed:     " + rs.getString("units"));
                area.append("\n    Total Charges:        " + rs.getString("totalbill"));
                area.append("\n-------------------------------------------------------\n\n");
                area.append("\n    Total Payable: " + rs.getString("totalbill"));
            } else {
                area.append("\nBill information not available for the selected month.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to generate bill!");
        }
    }

    public static void main(String[] args) {
        new GenerateBill("1");
    }
}
