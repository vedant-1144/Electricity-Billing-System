import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class PayBill extends JFrame implements ActionListener{

    Choice cmonth;
    JButton pay, back;
    String meter;
    PayBill(String meter) {
        this.meter = meter;
        setLayout(null);
        setBounds(300, 150, 900, 600);
        
        JLabel heading = new JLabel("Electicity Bill");
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
        add(cmonth);
        
        JLabel lblunits = new JLabel("Units");
        lblunits.setBounds(35, 260, 200, 20);
        add(lblunits);
        
        JLabel labelunits = new JLabel("");
        labelunits.setBounds(300, 260, 200, 20);
        add(labelunits);
        
        JLabel lbltotalbill = new JLabel("Total Bill");
        lbltotalbill.setBounds(35, 320, 200, 20);
        add(lbltotalbill);
        
        JLabel labeltotalbill = new JLabel("");
        labeltotalbill.setBounds(300, 320, 200, 20);
        add(labeltotalbill);
        
        JLabel lblstatus = new JLabel("Status");
        lblstatus.setBounds(35, 380, 200, 20);
        add(lblstatus);
        
        JLabel labelstatus = new JLabel("");
        labelstatus.setBounds(300, 380, 200, 20);
        labelstatus.setForeground(Color.RED);
        add(labelstatus);

        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM consumer WHERE meter = ?");
            ps.setString(1, meter);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                meternumber.setText(meter);
                labelname.setText(rs.getString("name"));
            }

            ps = conn.prepareStatement("SELECT * FROM bill WHERE meter = ? AND month = ?");
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
                PreparedStatement ps = conn.prepareStatement("UPDATE bill SET status = 'Paid' WHERE meter = ? AND month = ?");
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
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new PayBill("");
    }
}