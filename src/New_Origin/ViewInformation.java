package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ViewInformation extends JFrame implements ActionListener{
    JLabel heading,lblname,name,lblmeternumber,meternumber,lbladdress,city,address,state,email,phone;
    JButton cancel;
    ViewInformation(String mater) {
//        JOptionPane.showMessageDialog(null, "Login successful as Meter:"+mater);
        setBounds(350, 150, 850, 650);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        heading = new JLabel("VIEW CUSTOMER INFORMATION");
        heading.setBounds(250, 0, 500, 40);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(heading);

        lblname = new JLabel("Name");
        lblname.setBounds(70, 80, 100, 20);
        add(lblname);

        name = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                name.setText(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        name.setBounds(250, 80, 100, 20);
        add(name);

        lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(70, 140, 100, 20);
        lblmeternumber.setBackground(Color.GRAY);
        add(lblmeternumber);

        meternumber = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                meternumber.setText(rs.getString("meter_number"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        meternumber.setBounds(250, 140, 100, 20);
        add(meternumber);

         lbladdress = new JLabel("Address");
        lbladdress.setBounds(70, 200, 100, 20);
        add(lbladdress);

        address = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                address.setText(rs.getString("address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        address.setBounds(250, 200, 100, 20);
        add(address);

        JLabel lblcity = new JLabel("City");
        lblcity.setBounds(70, 260, 100, 20);
        add(lblcity);

         city = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                city.setText(rs.getString("city"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        city.setBounds(250, 260, 100, 20);
        add(city);

        JLabel lblstate = new JLabel("State");
        lblstate.setBounds(500, 80, 100, 20);
        add(lblstate);

        state = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                state.setText(rs.getString("state"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        state.setBounds(650, 80, 100, 20);
        add(state);

        JLabel lblemail = new JLabel("Email");
        lblemail.setBounds(500, 140, 100, 20);
        add(lblemail);

         email = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                email.setText(rs.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        email.setBounds(650, 140, 100, 20);
        add(email);
        JLabel lblphone = new JLabel("Phone");
        lblphone.setBounds(500, 200, 100, 20);
        add(lblphone);

         phone = new JLabel("");
        try {
            Connection conn = Connect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Consumer WHERE meter_number = ?");
            ps.setString(1, String.valueOf(mater));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                phone.setText(rs.getString("phone"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        phone.setBounds(650, 200, 100, 20);
        add(phone);

        // try {
        //     Conn c = new Conn();
        //     ResultSet rs = c.s.executeQuery("select * from customer where meter_no = '"+meter+"' ");
        //     while(rs.next()) {
        //         name.setText(rs.getString("name"));
        //         address.setText(rs.getString("address"));
        //         city.setText(rs.getString("city"));
        //         state.setText(rs.getString("state"));
        //         email.setText(rs.getString("email"));
        //         phone.setText(rs.getString("phone"));
        //         meternumber.setText(rs.getString("meter_no"));
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
//        try {
//            Connection conn = Connect.getConnection();
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM consumer WHERE meter = ?");
//            ps.setString(1, String.valueOf(mater));
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                name.setText(rs.getString("name"));
//                address.setText(rs.getString("address"));
//                city.setText(rs.getString("city"));
//                state.setText(rs.getString("state"));
//                email.setText(rs.getString("email"));
//                phone.setText(rs.getString("phone"));
//                meternumber.setText(rs.getString("meter"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        cancel = new JButton("Cancel");
        cancel.setForeground(Color.BLACK);
        cancel.setBounds(350, 340, 100, 25);
        add(cancel);
        cancel.addActionListener(this);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/viewcustomer.jpg"));
        Image i2 = i1.getImage().getScaledInstance(600, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(20, 350, 600, 300);
        add(image);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new ViewInformation("1");
    }
}