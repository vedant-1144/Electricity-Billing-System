import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword extends JFrame implements ActionListener{

    JButton search, retrieve, back;
    JTextField username;
    JTextField question;
    JTextField answer;
    JTextField password;
    ForgotPassword() {
        super("Forgot Password");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
    
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(250, 20, 100, 20);
        add(lblusername);
    
        username = new JTextField();
        username.setBounds(350, 20, 150, 20);
        add(username);
    
        search = new JButton("Search");
        search.setBounds(510, 20, 80, 20);
        search.addActionListener(this);
        add(search);
    
        JLabel lblquestion = new JLabel("Security\nQuestion");
        lblquestion.setBounds(250, 60, 130, 20);
        add(lblquestion);
    
        question = new JTextField();
        question.setBounds(360, 60, 150, 20);
        add(question);
    
        JLabel lblanswer = new JLabel("Answer");
        lblanswer.setBounds(250, 100, 100, 20);
        add(lblanswer);
    
        answer = new JTextField();
        answer.setBounds(350, 100, 150, 20);
        add(answer);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(250, 140, 100, 20);
        add(lblpassword);
    
        password = new JTextField();
        password.setBounds(350, 140, 150, 20);
        add(password);
    
        retrieve = new JButton("Retrieve");
        retrieve.setBounds(510, 140, 80, 20);
        retrieve.addActionListener(this);
        add(retrieve);
    
        back = new JButton("Back");
        back.setBounds(350, 190, 80, 20);
        back.addActionListener(this);
        add(back);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/forgot.png"));
        Image i2 = i1.getImage().getScaledInstance(160, 160, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(50,35,160,160);
        add(image);

        setSize(650,280);
        setLocation(400,200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            // Search for the security question based on username
            try {
                Connection conn = Connect.getConnection();
                String usernameText = username.getText();
                String query = "SELECT security_question FROM users WHERE username=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, usernameText);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    question.setText(rs.getString("security_question"));
                } else {
                    JOptionPane.showMessageDialog(this, "User not found!");
                }
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == retrieve) {
            // Retrieve password based on the security question and answer
            try {
                Connection conn = Connect.getConnection();
                String userAnswer = answer.getText();
                String secQuestion = question.getText();
                String query = "SELECT password FROM users WHERE security_question=? AND security_answer=?";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, secQuestion);
                pstmt.setString(2, userAnswer);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    password.setText(rs.getString("password"));
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect answer!");
                }
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}
