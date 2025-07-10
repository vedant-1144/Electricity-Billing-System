package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class NewCustomer extends JFrame implements ActionListener {

    JTextField tfname, tfaddress, tfstate, tfmeter, tfcity, tfemail, tfphone;
    JButton next, cancel;

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

        tfmeter = new JTextField();
        tfmeter.setBounds(240, 120, 100, 20);
        p.add(tfmeter);

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
        next.setBounds(120, 390, 100, 25);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        p.add(next);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 390, 100, 25);
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
            String meter = tfmeter.getText();
            String address = tfaddress.getText();
            String city = tfcity.getText();
            String state = tfstate.getText();
            String email = tfemail.getText();
            String phone = tfphone.getText();

            if (!isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(null, "Invalid Phone Number");
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(null, "Invalid Email");
                return;
            }

//            if (isMeterNumberRegisteredInUsers(meter)) {
//                //if the meter number is already allocated in users
//                JOptionPane.showMessageDialog(null, "Meter Number already allocated in users with different details");
//
//                if (!areMatchingDetails(name, meter)) {
//                    // matching details are not found
//                    JOptionPane.showMessageDialog(null, "Meter Number already allocated in users with different details");
//                    return;
//                }
//                else {
//                    name = getNameFromUsers(meter);
//                }
//            } else
                if (isMeterNumberRegisteredInConsumer(meter)) {
                // If the meter number is already registered in consumer, show error message and return
                JOptionPane.showMessageDialog(null, "Meter Number already registered");
                return;
            }

            String query = "INSERT INTO Consumer VALUES (?, ?, ?, ?, ?, ?, ?)";

                    try (Connection connection = Connect.getConnection();
                         PreparedStatement statement = connection.prepareStatement(query)) {

                        statement.setString(1, meter);
                        statement.setString(2, name);
                        statement.setString(3, address);
                        statement.setString(4, city);
                        statement.setString(5, state);
                        statement.setString(6, email);
                        statement.setString(7, phone);

                        int rowsAffected = statement.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Customer Details Added Successfully");
                            setVisible(false);
                            new MeterInfo(meter);
                            new signUp(name, meter);
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

            private String getNameFromUsers(String meter) {
                // Retrieve the name associated with the given meter number from the users table
                String query = "SELECT name FROM users WHERE meter_number = ?";
                try (Connection connection = Connect.getConnection();
                     PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, meter);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            return resultSet.getString("name");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }


            private boolean areMatchingDetails(String name, String meter) {
        // Check if the details for the given meter number match the provided name
        String query = "SELECT COUNT(*) FROM users WHERE meter_number = ? AND name = ?";
        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meter);
            statement.setString(2, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isMeterNumberRegisteredInUsers(String meter) {
        // Check if meter number is already registered in users
        String query = "SELECT COUNT(*) FROM users WHERE meter_number = ?";
        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isMeterNumberRegisteredInConsumer(String meter) {
        // Check if meter number is already registered in consumer
        String query = "SELECT COUNT(*) FROM Consumer WHERE meter_number = ?";
        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private boolean isValidPhoneNumber(String phone) {
        // Phone number validation
        return Pattern.matches("\\d{10}", phone);
    }

    private boolean isValidEmail(String email) {
        // Email validation
        return Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", email);
    }

    private boolean isMeterNumberRegistered(String meter) {
        // Check if meter number is already registered
        String query = "SELECT COUNT(*) FROM Consumer WHERE meter_number = ?";
        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, meter);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        new NewCustomer();
    }
}
