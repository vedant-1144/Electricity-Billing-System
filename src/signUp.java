import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class signUp extends JFrame implements ActionListener {
    JButton create, back;
    Choice accountType;
    JTextField meter;
    JTextField username;
    JTextField name;
    JTextField password;
    JTextField Code;

    signUp() {
        setBounds(450, 150, 700, 400);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 650, 300);
        panel.setBorder(new TitledBorder(new LineBorder(new Color(173, 216, 230), 2), "Create-Account", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setForeground(new Color(34, 139, 34));
        add(panel);

        //Labels :-
        JLabel heading = new JLabel("Create Account As");
        heading.setBounds(100, 50, 140, 20);
        heading.setForeground(Color.GRAY);
        heading.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(heading);

        accountType = new Choice();
        accountType.add("Admin");
        accountType.add("Customer");
        accountType.setBounds(260, 50, 140, 20);
        panel.add(accountType);

        JLabel lblmeter = new JLabel("Meter Number");
        lblmeter.setBounds(100, 90, 140, 20);
        lblmeter.setForeground(Color.GRAY);
        lblmeter.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblmeter);

        meter = new JTextField();
        meter.setBounds(260, 90, 150, 20);
        panel.add(meter);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(100, 130, 140, 20);
        lblusername.setForeground(Color.GRAY);
        lblusername.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblusername);

        username = new JTextField();
        username.setBounds(260, 130, 150, 20);
        panel.add(username);

        JLabel lblname = new JLabel("Name");
        lblname.setBounds(100, 170, 140, 20);
        lblname.setForeground(Color.GRAY);
        lblname.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblname);

        name = new JTextField();
        name.setBounds(260, 170, 150, 20);
        panel.add(name);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(100, 210, 140, 20);
        lblpassword.setForeground(Color.GRAY);
        lblpassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        panel.add(lblpassword);

        password = new JTextField();
        password.setBounds(260, 210, 150, 20);
        panel.add(password);

        String accTyp = accountType.getSelectedItem();
        if(accTyp.equals("Admin")) {
            JLabel lblCode = new JLabel("Password");
            lblpassword.setBounds(100, 210, 140, 20);
            lblpassword.setForeground(Color.GRAY);
            lblpassword.setFont(new Font("Tahoma", Font.BOLD, 14));
            panel.add(lblpassword);

            Code = new JTextField();
            password.setBounds(260, 210, 150, 20);
            panel.add(password);
        }

        //Buttons :-
        create = new JButton("Create");
        // create.setBackground(Color.BLACK);
        create.setForeground(Color.BLACK);
        create.setBounds(140, 260, 120, 25);
        create.addActionListener(this);
        panel.add(create);

        back = new JButton("Back");
        // create.setBackground(Color.BLACK);
        back.setForeground(Color.BLACK);
        back.setBounds(300, 260, 120, 25);
        back.addActionListener(this);
        panel.add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/signUpImage.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(415, 30, 250, 250);
        panel.add(image);

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == create) {
            // Register user in the database
            String accType = accountType.getSelectedItem();
            String metNum = meter.getText();
            String user = username.getText();
            String nm = name.getText();
            String pass = password.getText();

            registerUser(accType, metNum, user, nm, pass);
            setVisible(false);
            new Login();
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    // Method to register user in the database
    private void registerUser(String accountType, String meterNumber, String username, String name, String password) {
        // Perform database registration logic here
        // For example, you can use the Connect class to establish a connection and execute an insert query
        // You can use PreparedStatement to prevent SQL injection

        // Example code:
        try {
            Connection connection = Connect.getConnection();
            String query = "INSERT INTO users (account_type, meter_number, username, name, password) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, accountType);
                statement.setString(2, meterNumber);
                statement.setString(3, username);
                statement.setString(4, name);
                statement.setString(5, password);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "User registered successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to register user!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to register user!");
        }
    }

    public static void main(String[] args) {
        new signUp();
    }
}

//    public void actionPerformed(ActionEvent ae) {
//        if(ae.getSource() == create) {
//            // Register user in the database
//            String accountType = accountType.getSelectedItem();
//            String meterNumber = meter.getText();
//            String Username = username.getText();
//            String name = name.getText();
//            String password = password.getText();
//
//            // Perform registration logic here using the provided information
//            // For example, you can call a method to insert this data into the database
//            registerUser(accountType, meterNumber, username, name, password);
//        } else if(ae.getSource() == back) {
//            setVisible(false);
//            new Login();
//        }
//    }
//    public static void main(String[] args) {
//        new signUp();
//    }
//}
//
//
//// Method to register user in the database
//private void registerUser(String accountType, String meterNumber, String username, String name, String password) {
//    // Perform database registration logic here
//    // For example, you can use the Connect class to establish a connection and execute an insert query
//    // You can use PreparedStatement to prevent SQL injection
//
//    // Example code:
//    try {
//        Connection connection = Connect.getConnection();
//        String query = "INSERT INTO users (account_type, meter_number, username, name, password) VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setString(1, accountType);
//            statement.setString(2, meterNumber);
//            statement.setString(3, username);
//            statement.setString(4, name);
//            statement.setString(5, password);
//            int rowsInserted = statement.executeUpdate();
//            if (rowsInserted > 0) {
//                JOptionPane.showMessageDialog(this, "User registered successfully!");
//            } else {
//                JOptionPane.showMessageDialog(this, "Failed to register user!");
//            }
//        }
//    } catch (SQLException ex) {
//        ex.printStackTrace();
//        JOptionPane.showMessageDialog(this, "Failed to register user!");
//    }
//}
//}