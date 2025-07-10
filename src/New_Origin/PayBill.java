package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayBill extends JFrame implements ActionListener, ItemListener {

    Choice cmonth;
    JButton pay, back;
    String meter;
    JLabel labelunits, labeltotalbill, labelstatus;

    PayBill(String meter) {
        this.meter = meter;
        setLayout(null);
        setBounds(300, 150, 900, 600);

        JLabel heading = new JLabel("Electricity Bill");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(120, 5, 400, 30);
        add(heading);

        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(35, 80, 200, 20);
        add(lblmeternumber);

        JLabel meternumber = new JLabel("");
        meternumber.setBounds(300, 80, 200, 20);
        add(meternumber);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(35, 140, 200, 20);
        add(lblname);

        JLabel labelname = new JLabel("");
        labelname.setBounds(300, 140, 200, 20);
        add(labelname);

        JLabel lblmonth = new JLabel("Month");
        lblmonth.setBounds(35, 200, 200, 20);
        add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(300, 200, 200, 20);
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
        cmonth.addItemListener(this);
        add(cmonth);

        JLabel lblunits = new JLabel("Units");
        lblunits.setBounds(35, 260, 200, 20);
        add(lblunits);

        labelunits = new JLabel("");
        labelunits.setBounds(300, 260, 200, 20);
        add(labelunits);

        JLabel lbltotalbill = new JLabel("Total Bill");
        lbltotalbill.setBounds(35, 320, 200, 20);
        add(lbltotalbill);

        labeltotalbill = new JLabel("");
        labeltotalbill.setBounds(300, 320, 200, 20);
        add(labeltotalbill);

        JLabel lblstatus = new JLabel("Status");
        lblstatus.setBounds(35, 380, 200, 20);
        add(lblstatus);

        labelstatus = new JLabel("");
        labelstatus.setBounds(300, 380, 200, 20);
        labelstatus.setForeground(Color.RED);
        add(labelstatus);

        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, meter);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                meternumber.setText(meter);
                labelname.setText(rs.getString("name"));
            }

            ps = conn.prepareStatement("SELECT * FROM Bill WHERE meter_number = ? AND month = ?");
            ps.setString(1, meter);
            ps.setString(2, "January"); // Default to January, can be changed by user
            ResultSet rsBill = ps.executeQuery();
            if (rsBill.next()) {
                labelunits.setText(rsBill.getString("units"));
                labeltotalbill.setText(rsBill.getString("totalbill"));
                labelstatus.setText(rsBill.getString("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pay = new JButton("Pay");
        pay.setForeground(Color.BLACK);
        pay.setBounds(100, 460, 100, 25);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setForeground(Color.BLACK);
        back.setBounds(230, 460, 100, 25);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(Color.WHITE);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/bill.png"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(400, 120, 600, 300);
        add(image);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            try {
                Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement("UPDATE Bill SET status = 'Paid' WHERE meter_number = ? AND month = ?");
                ps.setString(1, meter);
                ps.setString(2, cmonth.getSelectedItem());
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Bill paid successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to pay bill.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while paying bill.");
            }

//            try {
//                // Get the amount to be paid
//                String amount = labeltotalbill.getText(); // Assuming labeltotalbill contains the total bill amount
//
//                // Generate Paytm payment link with the amount
//                String paytmURL = generatePaytmPaymentLink(amount);
//
//                // Open the Paytm payment link in a browser window
//                Desktop.getDesktop().browse(new URI(paytmURL));
//            } catch (Exception e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Failed to initiate Paytm payment.");
//            }
//
//        } else {
//            setVisible(false);
//        }
}
////
////    // Method to generate Paytm payment link with the given amount
////    private String generatePaytmPaymentLink(String amount) {
////        // Paytm payment API URL for testing environment
////        String paytmURL = "https://securegw-stage.paytm.in/theia/processTransaction";
////
////        // Replace these placeholders with your actual Paytm test credentials
////        String merchantId = "Pratik29004"; // Use "YOUR_TEST_MID" for testing
////        String orderId = "45651"; // Generate a unique order ID for testing
////
////        // Paytm test environment doesn't require a merchant ID, but you can use any value here
////        String website = "WEBSTAGING"; // For testing, use "WEBSTAGING"
////
////        // Create the payment link with the required parameters
////        String paymentLink = paytmURL + "?ORDER_ID=" + orderId + "&MID=" + merchantId + "&WEBSITE=" + website + "&TXN_AMOUNT=" + amount;
////
////        return paymentLink;
//
//            if (ae.getSource() == pay) {
//                try {
//                    // Get the amount to be paid
//                    String amount = labeltotalbill.getText(); // Assuming labeltotalbill contains the total bill amount
//
//                    // Generate UPI payment link with the amount
//                    String upiURL = generateUpiPaymentLink(amount);
//
//                    // Open the UPI payment link in a browser window
//                    Desktop.getDesktop().browse(new URI(upiURL));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    JOptionPane.showMessageDialog(this, "Failed to initiate UPI payment.");
//                }
//            } else {
//                setVisible(false);
//            }
//        }
//
//// Method to generate UPI payment link with the given amount
//        private String generateUpiPaymentLink(String amount) {
//            // Replace this with your UPI ID
//            String upiId = "pratiksukale2004@okhdfcbank";
//
//            // Create the payment link with the UPI ID and amount
//            String paymentLink = "upi://" + upiId + "?amount=" + amount;
//
//            return paymentLink;
//        }

//        if (ae.getSource() == pay) {
//            new UPI_Pay(meter);
//        }

    }

    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            String month = cmonth.getSelectedItem();
            try {
                Connection conn = Connect.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM Bill WHERE meter_number = ? AND month = ?");
                ps.setString(1, meter);
                ps.setString(2, month);
                ResultSet rsBill = ps.executeQuery();
                if (rsBill.next()) {
                    labelunits.setText(rsBill.getString("units"));
                    labeltotalbill.setText(rsBill.getString("totalbill"));
                    labelstatus.setText(rsBill.getString("status"));
                    if (rsBill.getString("status").equals("Paid")) {
                        pay.setEnabled(false);
                    } else {
                        pay.setEnabled(true);
                    }
                } else {
                    // Clear fields if no bill found for selected month
                    labelunits.setText("Not Available");
                    labeltotalbill.setText("Not Available");
                    labelstatus.setText("Not Available");
                    pay.setEnabled(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error occurred while fetching bill details.");
            }
        }
    }


    public static void main(String[] args) {
        new PayBill("2");
    }
}
