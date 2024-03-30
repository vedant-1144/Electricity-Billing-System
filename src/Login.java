import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Login extends JFrame implements ActionListener{

    JButton login, signup, cancel;
    Login() {
        super("Login Page");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(300,20,100,20);
        add(lblusername);

        JTextField username = new JTextField();
        username.setBounds(400,20,150,20);
        add(username);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(300,60,100,20);
        add(lblpassword);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(400, 60, 150, 20);
        add(passwordField);

        ImageIcon eye1 = new ImageIcon(ClassLoader.getSystemResource("icon/eye.png"));
        Image eye2 = eye1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        JToggleButton showPasswordButton = new JToggleButton(new ImageIcon(eye2));
        showPasswordButton.setBounds(555, 60, 16, 16);
        showPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordButton.isSelected()) {
                    passwordField.setEchoChar((char) 0); // Show password
                } else {
                    passwordField.setEchoChar('\u2022'); // Hide password
                }
            }
        });
        add(showPasswordButton);

        JLabel logginginas = new JLabel("Logging in as");
        logginginas.setBounds(300,100,100,20);
        add(logginginas);

        Choice loggingin = new Choice();
        loggingin.add("Admin");
        loggingin.add("Customer");
        loggingin.setBounds(400,100,150,20);
        add(loggingin);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/login.png"));
        Image i2 = i1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        login = new JButton("Login", new ImageIcon(i2));
        login.setBounds(330,160,100,20);
        login.addActionListener(this);
        add(login);

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/cancel.jpg"));
        Image i4 = i3.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        cancel = new JButton("Cancel", new ImageIcon(i4));
        cancel.setBounds(450,160,100,20);
        cancel.addActionListener(this);
        add(cancel);

        ImageIcon i5 = new ImageIcon(ClassLoader.getSystemResource("icon/signup.png"));
        Image i6 = i5.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        signup = new JButton("Sign Up", new ImageIcon(i6));
        signup.setBounds(330,200,100,20);
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
                new ForgotPassword();
            }
        });
        add(lblforgot);

        ImageIcon i9 = new ImageIcon(ClassLoader.getSystemResource("icon/second.jpg"));
        Image i10 = i9.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        ImageIcon i11 = new ImageIcon(i10);
        JLabel image = new JLabel(i11);
        image.setBounds(0,0,300,300);
        add(image);

        setSize(650,320);
        setLocation(400,200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == login) {
            //Authenticate the User
        } else if(ae.getSource() == signup) {
            setVisible(false);
            new signUp();
        } else if(ae.getSource() == cancel) {
            setVisible(false);
        }
    }
    public static void main(String[] args) {
        new Login();
    }
}
