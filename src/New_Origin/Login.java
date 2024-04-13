package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    JButton login, signup, cancel;
    JTextField username;
    JPasswordField password;
    Choice loggingin;

    Login() {
        super("Login Page");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(300, 20, 100, 20);
        add(lblusername);

        username = new JTextField();
        username.setBounds(400, 20, 150, 20);
        add(username);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(300, 60, 100, 20);
        add(lblpassword);

        password = new JPasswordField();
        password.setBounds(400, 60, 150, 20);
        add(password);

        ImageIcon eye1 = new ImageIcon(ClassLoader.getSystemResource("icon/eye.png"));
        Image eye2 = eye1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ImageIcon eye3 = new ImageIcon(ClassLoader.getSystemResource("icon/eye2.png"));
        Image eye4 = eye3.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);

        JToggleButton show = new JToggleButton(new ImageIcon(eye4));
        show.setBounds(555, 60, 16, 16);
        show.addActionListener(_ -> {
            if (show.isSelected()) {
                password.setEchoChar((char) 0); // Show password
                show.setIcon(new ImageIcon(eye2));
            } else {
                password.setEchoChar('•'); // Hide password
                show.setIcon(new ImageIcon(eye4));
            }
        });
        add(show);

        JLabel logginginas = new JLabel("Logging in as");
        logginginas.setBounds(300, 100, 100, 20);
        add(logginginas);

        loggingin = new Choice();
        loggingin.add("Admin");
        loggingin.add("Customer");
        loggingin.setBounds(400, 100, 150, 20);
        add(loggingin);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i2 = i1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        login = new JButton("Login", new ImageIcon(i2));
        login.setBounds(330, 160, 100, 20);
        login.addActionListener(this);
        add(login);

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/cancel.jpg"));
        Image i4 = i3.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        cancel = new JButton("Cancel", new ImageIcon(i4));
        cancel.setBounds(450, 160, 100, 20);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icon/signup.png"));
        Image i6 = i5.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        signup = new JButton("Sign Up", new ImageIcon(i6));
        signup.setBounds(330, 200, 100, 20);
        signup.addActionListener(this);
        add(signup);

        JLabel lblforgot = new JLabel("Forgot Password ?");
        lblforgot.setBounds(450, 200, 150, 20);
        lblforgot.setForeground(Color.BLUE);
        lblforgot.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblforgot.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
//                new ForgotPassword();
            }
        });
        add(lblforgot);

        ImageIcon i9 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image i10 = i9.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i11 = new ImageIcon(i10);
        JLabel image = new JLabel(i11);
        image.setBounds(0, 0, 300, 300);
        add(image);

        setSize(650, 320);
        setLocation(400, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            // Authenticate the User
            String Username = username.getText();
            String Password = String.valueOf(password.getPassword());
            AuthResult authResult = authenticateUser(Username, Password);
            if (authResult != null) {
                String accountType = authResult.accountType();
                String meterNo = authResult.meterNo();
                if (accountType != null) {
                    if (accountType.equals("Admin")) {
                        JOptionPane.showMessageDialog(null, "Login successful as Admin");
                        JOptionPane.showMessageDialog(null, "Login successful as Meter:" + meterNo);
                        // Open Admin Interface
                        setVisible(false);
                        new Project(accountType, meterNo);
                    } else if (accountType.equals("Customer")) {
                        JOptionPane.showMessageDialog(null, "Login successful as Customer");

                        setVisible(false);
                        new Project(accountType, meterNo);

                    } else {
                        JOptionPane.showMessageDialog(null, "Unknown account type: " + accountType);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        } else if (ae.getSource() == signup) {
            setVisible(false);
            new signUp( "","");
        } else if (ae.getSource() == cancel) {
            setVisible(false);
        }
    }

    private AuthResult authenticateUser(String Username, String Password) {
        // Query the database to check if the username and password match
        String query = "SELECT role, meter_number FROM Users WHERE username = ? AND password = ?";
        try (Connection connection = Connect.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, Username);
            statement.setString(2, Password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String accountType = resultSet.getString("role");
                    String meterNo = resultSet.getString("meter_number");
                    return new AuthResult(accountType, meterNo); // Return account type and meter number
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null; // Authentication failed
    }

    public static void main(String[] args) {
        new Login();
    }

    record AuthResult(String accountType, String meterNo) {
    }
}
