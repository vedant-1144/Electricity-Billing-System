package New_Origin;

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
    JTextField code; // Changed variable name to code

    signUp(String nam, String met) {

        if (nam.isEmpty() && met.isEmpty()) {
            // Initialize default values
            nam = "";
            met = "";
        }
        setBounds(450, 150, 700, 450);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(30, 30, 650, 350);
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
        if (accTyp.equals("Admin")) {
            JLabel lblCode = new JLabel("Code"); // Changed label text
            lblCode.setBounds(100, 250, 140, 20); // Adjusted bounds
            lblCode.setForeground(Color.GRAY);
            lblCode.setFont(new Font("Tahoma", Font.BOLD, 14));
            panel.add(lblCode);

            code = new JTextField(); // Changed variable name
            code.setBounds(260, 250, 150, 20); // Adjusted bounds
            panel.add(code);
        }

        //Buttons :-
        create = new JButton("Create");
        create.setForeground(Color.BLACK);
        create.setBounds(140, 290, 120, 25); // Adjusted bounds
        create.addActionListener(this);
        panel.add(create);

        back = new JButton("Back");
        back.setForeground(Color.BLACK);
        back.setBounds(300, 290, 120, 25); // Adjusted bounds
        back.addActionListener(this);
        panel.add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/signUpImage.png"));
        Image i2 = i1.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(415, 30, 250, 250);
        panel.add(image);

        name.setText(nam);
        meter.setText(met);

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
            String cd = code.getText(); // Changed variable name

            registerUser(accType, metNum, user, nm, pass, cd); // Passed code as an argument
            setVisible(false);
            new Login();
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    // Method to register user in the database
    private void registerUser(String accountType, String meterNumber, String username, String name, String password, String code) {
        try {
            Connection connection = Connect.getConnection();
            String query = "INSERT INTO Users (user_id,role, meter_number, username, name, password) VALUES (user_id_seq.nextval,?, ?, ?, ?, ?)";
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
    public static void main(String[] args){
        new signUp(" ","");
    }
}

