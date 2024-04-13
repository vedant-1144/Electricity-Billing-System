package New_Origin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;

public class CalculateBill extends JFrame implements ActionListener{

    JTextField tfunits;
//    tfname, tfaddress, tfstate,  tfemail, tfphone;
    JButton next, cancel;
    JLabel lblname, labeladdress;
    Choice meternumber, cmonth;
    String SelectedMeter;


    CalculateBill() {
        setSize(700, 500);
        setLocation(400, 150);

        JPanel p = new JPanel();
        p.setLayout(null);
        p.setBackground(new Color(173, 216, 230));
        add(p);

        JLabel heading = new JLabel("Calculate Electricity Bill");
        heading.setBounds(100, 10, 400, 25);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 24));
        p.add(heading);

        JLabel lblmeternumber = new JLabel("Meter Number");
        lblmeternumber.setBounds(100, 80, 100, 20);
        p.add(lblmeternumber);

        meternumber = new Choice();

        try {
            Connection connection = Connect.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from Consumer");
            while(rs.next()) {
                meternumber.add(rs.getString("meter_number"));
            }

            rs.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        meternumber.addItemListener(this::itemStateChanged);

        meternumber.setBounds(240, 80, 200, 20);
        p.add(meternumber);

        JLabel lblmeterno = new JLabel("Name");
        lblmeterno.setBounds(100, 120, 100, 20);
        p.add(lblmeterno);

        lblname = new JLabel();
        String selectedMeter = meternumber.getSelectedItem(); // Get the selected meter number
        try {
            Connection connection = Connect.getConnection();
            String query = "SELECT name FROM Consumer WHERE meter_number = ?";
            PreparedStatement po = connection.prepareStatement(query);
            po.setString(1, selectedMeter);
            ResultSet rs = po.executeQuery();
            if (rs.next()) {
                lblname.setText(rs.getString("name")); // Set the label text with the retrieved name
            }
            rs.close(); // Close the ResultSet
            po.close(); // Close the PreparedStatement
            connection.close(); // Close the Connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lblname.setBounds(240, 120, 100, 20);
        p.add(lblname);


        JLabel lbladdress = new JLabel("Address");
        lbladdress.setBounds(100, 160, 100, 20);
        p.add(lbladdress);

        labeladdress = new JLabel();
        try {
            Connection connection = Connect.getConnection();
            String query = "SELECT address FROM Consumer WHERE meter_number = ?";
            PreparedStatement po = connection.prepareStatement(query);
            po.setString(1, selectedMeter);
            ResultSet rs = po.executeQuery();
            if (rs.next()) {
                labeladdress.setText(rs.getString("address")); // Set the label text with the retrieved address
            }
            rs.close(); // Close the ResultSet
            po.close(); // Close the PreparedStatement
            connection.close(); // Close the Connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
        labeladdress.setBounds(240, 160, 200, 20);
        p.add(labeladdress);

        JLabel lblcity = new JLabel("Units Consumed");
        lblcity.setBounds(100, 200, 100, 20);
        p.add(lblcity);

        tfunits = new JTextField();
        tfunits.setBounds(240, 200, 200, 20);
        p.add(tfunits);

        JLabel lblstate = new JLabel("Month");
        lblstate.setBounds(100, 240, 100, 20);
        p.add(lblstate);

        cmonth = new Choice();
        cmonth.setBounds(240, 240, 200, 20);
        cmonth.add("January");
        cmonth.add("February");
        cmonth.add("March");
        cmonth.add("April");
        cmonth.add("May");
        cmonth.add("June");
        cmonth.add("July");
        cmonth.add("August");
        cmonth.add("September");
        cmonth.add("October");
        cmonth.add("November");
        cmonth.add("December");
        p.add(cmonth);

        next = new JButton("Submit");
        next.setBounds(120, 350, 100,25);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        p.add(next);

        cancel = new JButton("Cancel");
        cancel.setBounds(250, 350, 100,25);
        cancel.setForeground(Color.BLACK);
        cancel.addActionListener(this);
        p.add(cancel);

        setLayout(new BorderLayout());

        add(p, "Center");

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/hicon2.jpg"));
        Image i2 = i1.getImage().getScaledInstance(150, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        add(image, "West");

        getContentPane().setBackground(Color.WHITE);

        setVisible(true);
    }
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == meternumber && e.getStateChange() == ItemEvent.SELECTED) {
            String selectedMeter = meternumber.getSelectedItem();
            try {
                Connection connection = Connect.getConnection();
                String query = "SELECT name, address FROM Consumer WHERE meter_number = ?";
                PreparedStatement po = connection.prepareStatement(query);
                po.setString(1, selectedMeter);
                ResultSet rs = po.executeQuery();
                if (rs.next()) {
                    lblname.setText(rs.getString("name"));
                    labeladdress.setText(rs.getString("address"));
                } else {
                    lblname.setText("");
                    labeladdress.setText("");
                }
                rs.close();
                po.close();
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent ae) {
            if (ae.getSource() == next) {
                String meter = meternumber.getSelectedItem();
                String units = tfunits.getText();
                String month = cmonth.getSelectedItem();

                int totalbill = 0;
                int unit_consumed = Integer.parseInt(units);

                try {
                    Connection connection = Connect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("select * from tax");
                    ResultSet rs = preparedStatement.executeQuery();
                    while(rs.next()) {
                        totalbill += unit_consumed * Integer.parseInt(rs.getString("cost_per_unit"));
                        totalbill += Integer.parseInt(rs.getString("meter_rent"));
                        totalbill += Integer.parseInt(rs.getString("service_charge"));
                        totalbill += Integer.parseInt(rs.getString("service_tax"));
                        totalbill += Integer.parseInt(rs.getString("swacch_bharat_cess"));
                        totalbill += Integer.parseInt(rs.getString("fixed_tax"));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    Connection connection = Connect.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement("insert into Bill values(bill_id_sequence.nextval,?, ?, ?, ?, ?)");
                    preparedStatement.setString(1, meter);
                    preparedStatement.setString(2, month);
                    preparedStatement.setString(3, units);
                    preparedStatement.setInt(4, totalbill);
                    preparedStatement.setString(5, "Not Paid");

                    preparedStatement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Customer Bill Updated Successfully");
                    setVisible(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                setVisible(false);
            }
        }

    public static void main(String[] args) {
        new CalculateBill();
    }
}