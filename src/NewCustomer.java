import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.awt.event.*;

public class NewCustomer extends JFrame implements ActionListener{

    JTextField tfname, tfaddress, tfstate, tfcity, tfemail, tfphone;
    JButton next, cancel;
    JLabel lblmeter;
    NewCustomer() {
        setSize(700, 500);
        setLocation(400, 200);
        
        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);
        
        JLabel heading = new JLabel("New Customer");
        heading.setBounds(180, 10, 200, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);
        
        JLabel lblname = new JLabel("Customer Name");
        lblname.setBounds(100, 80, 100, 20);
        p.add(lblname);
        
        tfname = new JTextField();
        tfname.setBounds(240, 80, 200, 20);
        p.add(tfname);
        
        JLabel lblmeterno = new JLabel("Meter Number");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);
        
        lblmeter = new JLabel("");
        lblmeter.setBounds(240, 120, 100, 20);
        p.add(lblmeter);
        
        Random ran = new Random();
        long number = ran.nextLong() % 1000000;
        lblmeter.setText("" + Math.abs(number));
        
        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);
        
        tfaddress = new JTextField();
        tfaddress.setBounds(240, 160, 200, 20);
        p.add(tfaddress);
        
        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);
        
        tfcity = new JTextField();
        tfcity.setBounds(240, 200, 200, 20);
        p.add(tfcity);
        
        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(100, 240, 100, 20);
        p.add(lblstate);
        
        tfstate = new JTextField();
        tfstate.setBounds(240, 240, 200, 20);
        p.add(tfstate);
        
        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(100, 280, 100, 20);
        p.add(lblemail);
        
        tfemail = new JTextField();
        tfemail.setBounds(240, 280, 200, 20);
        p.add(tfemail);
        
        JLabel lblphone = new JLabel("Phone Number");
        lblphone.setBounds(100, 320, 100, 20);
        p.add(lblphone);
        
        tfphone = new JTextField();
        tfphone.setBounds(240, 320, 200, 20);
        p.add(tfphone);
        
        next = new JButton("Next");
        next.setBounds(120, 390, 100,25);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        p.add(next);
        
        cancel = new JButton("Cancel");
        cancel.setBounds(250, 390, 100,25);
        cancel.setForeground(Color.BLACK);
        cancel.addActionListener(this);
        p.add(cancel);
        
        setLayout(new BorderLayout());
        
        add(p, "Center");
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon1.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");
        
        getContentPane().setBackground(Color.WHITE);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            String name = tfname.getText();
            String meter = lblmeter.getText();
            String address = tfaddress.getText();
            String city = tfcity.getText();
            String state = tfstate.getText();
            String email = tfemail.getText();
            String phone = tfphone.getText();

            String query1 = "INSERT INTO consumer VALUES (?, ?, ?, ?, ?, ?, ?)";
            String query2 = "INSERT INTO users VALUES (?, '', ?, '', '')";

            try (Connection connection = Connect.getConnection();
                 PreparedStatement statement1 = connection.prepareStatement(query1);
                 PreparedStatement statement2 = connection.prepareStatement(query2)) {

                // Set parameters for the first query
                statement1.setString(1, name);
                statement1.setString(2, meter);
                statement1.setString(3, address);
                statement1.setString(4, city);
                statement1.setString(5, state);
                statement1.setString(6, email);
                statement1.setString(7, phone);

                // Execute the first query
                int rowsAffected1 = statement1.executeUpdate();
                if (rowsAffected1 > 0) {
                    // Set parameters for the second query
                    statement2.setString(1, meter);
                    statement2.setString(2, name);

                    // Execute the second query
                    int rowsAffected2 = statement2.executeUpdate();
                    if (rowsAffected2 > 0) {
                        JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                        setVisible(false);
                        // new frame
                        new signUp();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add customer details");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to add customer details");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to add customer details");
            }
        } else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new NewCustomer();
    }
}
