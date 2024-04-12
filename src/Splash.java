import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame implements Runnable{
    Thread t;

    Splash() {
        // ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/elect.jpg"));
        // Image i2 = i1.getImage().getScaledInstance(730, 550, Image.SCALE_DEFAULT);
        // ImageIcon i3 = new ImageIcon(i2);
        // JLabel image = new JLabel(i3);
        // add(image);

        ImageIcon gif = new ImageIcon(ClassLoader.getSystemResource("icon/india.gif"));
        Image gif2 = gif.getImage().getScaledInstance(710, 550, Image.SCALE_DEFAULT);
        JLabel img = new JLabel(new ImageIcon(gif2));
        add(img);

        // int x = 1;
        // for (int i = 2; i < 600; i+=4, x+=1) {
        //     setSize(i+x,i);
        //     setLocation(700-(i+x)/2, 400-(i/2));
        // }
        t = new Thread(this);
        t.start();

        setBounds(500, 200, 800, 560);
        setVisible(true);
    }

    public void run() {
        try {
            Thread.sleep(3000);
            new Login();
            setVisible(false);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Splash();
    }
}