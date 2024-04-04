import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaymentVerification extends JFrame {
    JTextField captcha;
    JLabel lblcaptcha;
    JButton verify, newCaptcha;
    String captchaCode;

    public PaymentVerification() {
        super("Payment Gateway Verification");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        captchaCode = generateCaptcha();
        lblcaptcha = new JLabel(captchaCode);
        lblcaptcha.setFont(new Font("Arial", Font.BOLD, 30));
        lblcaptcha.setBounds(50, 50, 200, 40);
        add(lblcaptcha);

        captcha = new JTextField(10);
        captcha.setBounds(50, 100, 200, 30);
        add(captcha);

        verify = new JButton("Verify");
        verify.setBounds(50, 150, 100, 30);
        verify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String captchaEntered = captcha.getText();
                if (isValidCaptcha(captchaEntered)) {
                    JOptionPane.showMessageDialog(null, "Verification successful.\nPayment Complete");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid CAPTCHA.\nPlease try again.");
                }
            }
        });
        add(verify);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/icon13.png"));
        Image i2 = i1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        newCaptcha = new JButton("New Captcha", new ImageIcon(i2));
        newCaptcha.setBounds(150, 150, 130, 30);
        newCaptcha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                captchaCode = generateCaptcha();
                lblcaptcha.setText(captchaCode);
            }
        });
        add(newCaptcha);

        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("icon/captcha.png"));
        Image i4 = i3.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        JLabel image = new JLabel(new ImageIcon(i4));
        image.setBounds(300, 20, 150, 150);
        add(image);

        setBounds(500, 300, 500, 250);
        setVisible(true);
    }

    private String generateCaptcha() {
        return String.format("%04d", (int) (Math.random() * 10000));
    }

    private boolean isValidCaptcha(String captchaEntered) {
        return captchaEntered.equals(captchaCode);
    }

    public static void main(String[] args) {
        new PaymentVerification();
    }
}
