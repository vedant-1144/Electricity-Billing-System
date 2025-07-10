package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UPI_Pay extends JFrame implements ActionListener {

    JButton pay, back;
    String meter;

    UPI_Pay(String meter) {
        this.meter = meter;
        setLayout(null);
        setBounds(300, 150, 900, 600);

        JLabel heading = new JLabel("UPI Payment");
        heading.setFont(new Font("Tahoma", Font.BOLD, 24));
        heading.setBounds(120, 5, 400, 30);
        add(heading);

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(35, 80, 200, 20);
        add(lblAmount);

        JLabel lblAmountValue = new JLabel(getAmountFromDatabase(meter)); // Get amount from database
        lblAmountValue.setBounds(300, 80, 200, 20);
        add(lblAmountValue);

        BufferedImage qrCodeImage = generateUpiQrCode("pratiksukale2904@okhdfcbank", lblAmountValue.getText()); // Example UPI ID, replace with actual value
        JLabel qrCodeLabel = new JLabel(new ImageIcon(qrCodeImage));
        qrCodeLabel.setBounds(150, 150, 200, 200);
        add(qrCodeLabel);

        pay = new JButton("Paid");
        pay.setForeground(Color.BLACK);
        pay.setBounds(100, 400, 100, 25);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setForeground(Color.BLACK);
        back.setBounds(230, 400, 100, 25);
        back.addActionListener(this);
        add(back);

        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == pay) {
            JOptionPane.showMessageDialog(this, "Waiting for payment confirmation...");
        } else {
            setVisible(false);
        }
    }

    private BufferedImage generateUpiQrCode(String upiId, String amount) {
        String upiPayload = "upi://" + upiId + "?amount=" + amount;
        // Generate QR code using the upiPayload
        // For simplicity, let's assume it generates a placeholder QR code image
        BufferedImage qrCodeImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = qrCodeImage.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.BLACK);
        g.drawString(upiPayload, 50, 100);
        g.dispose();
        return qrCodeImage;
    }

    private String getAmountFromDatabase(String meter) {
        String amount = "0"; // Default value
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT totalbill FROM Bill WHERE meter_number = ?");
            ps.setString(1, meter);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                amount = rs.getString("totalbill");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    public static void main(String[] args) {
        new UPI_Pay("2");
    }
}
