import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ForgotPassword extends JFrame implements ActionListener{

    JButton search, retrieve, back;
    ForgotPassword() {
        super("Forgot Password");
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
    
        JLabel lblusername = new JLabel("Username");
        lblusername.setBounds(250, 20, 100, 20);
        add(lblusername);
    
        JTextField username = new JTextField();
        username.setBounds(350, 20, 150, 20);
        add(username);
    
        search = new JButton("Search");
        search.setBounds(510, 20, 80, 20);
        search.addActionListener(this);
        add(search);
    
        JLabel lblquestion = new JLabel("Security Question");
        lblquestion.setBounds(250, 60, 130, 20);
        add(lblquestion);
    
        JTextField question = new JTextField();
        question.setBounds(360, 60, 150, 20);
        add(question);
    
        JLabel lblanswer = new JLabel("Answer");
        lblanswer.setBounds(250, 100, 100, 20);
        add(lblanswer);
    
        JTextField answer = new JTextField();
        answer.setBounds(350, 100, 150, 20);
        add(answer);

        JLabel lblpassword = new JLabel("Password");
        lblpassword.setBounds(250, 140, 100, 20);
        add(lblpassword);
    
        JTextField password = new JTextField();
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
        if(ae.getSource() == search) {
            //Search for the question and display its answer in the text
        } else if(ae.getSource() == retrieve) {
            //Retrieve the password associated with the question from the database
        } else if(ae.getSource() == back) {
            setVisible(false);
            new Login();
        }
    }
    public static void main(String[] args) {
        new ForgotPassword();
    }
}
