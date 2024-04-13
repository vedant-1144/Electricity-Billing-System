import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.*;

public class update extends JFrame implements ActionListener {
    JLabel heading, lblname, name, lblmeternumber, meternumber, lbladdress, city, address, state, email, phone;
    JButton cancel;
    String meter;

    update(String meter) {
        this.meter = meter;
        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + meter);
        String nameText = "";
        String addressText = "";
        String cityText = "";
        String stateText = "";
        String emailText = "";
        String phoneText = "";


        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM consumer WHERE meter = ?");
            ps.setString(1, meter);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nameText = rs.getString("name");
                addressText = rs.getString("address");
                cityText = rs.getString("city");
                stateText = rs.getString("state");
                emailText = rs.getString("email");
                phoneText = rs.getString("phone");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + nameText);
        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + addressText);
        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + stateText);
        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + emailText);
        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + phoneText);

        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Centered layout with 20px horizontal and vertical gap

        heading = new JLabel("VIEW CUSTOMER INFORMATION");
        heading.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(heading);

        lblname = new JLabel("Name:");
        add(lblname);

        name = new JLabel(nameText);
        add(name);

        lblmeternumber = new JLabel("Meter Number:");
        add(lblmeternumber);

        meternumber = new JLabel(meter);
        add(meternumber);

        lbladdress = new JLabel("Address:");
        add(lbladdress);

        address = new JLabel(addressText);
        add(address);

        JLabel lblcity = new JLabel("City:");
        add(lblcity);

        city = new JLabel(cityText);
        add(city);

        JLabel lblstate = new JLabel("State:");
        add(lblstate);

        state = new JLabel(stateText);
        add(state);

        JLabel lblemail = new JLabel("Email:");
        add(lblemail);

        email = new JLabel(emailText);
        add(email);

        JLabel lblphone = new JLabel("Phone:");
        add(lblphone);

        phone = new JLabel(phoneText);
        add(phone);

        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        add(cancel);

        pack(); // Automatically adjust frame size to fit components
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit application on close
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new update("01232");
    }
}
