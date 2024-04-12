//import java.awt.*;
//import javax.swing.*;
//import java.sql.*;
//import java.awt.event.*;
//
//
//public class DepositDetails extends JFrame implements ActionListener{
//
//    Choice meternumber, cmonth;
//    JTable table;
//    JButton search, print;
//
//    DepositDetails(){
//        super("Deposit Details");
//
//        setSize(700, 700);
//        setLocation(400, 100);
//
//        setLayout(null);
//        getContentPane().setBackground(Color.WHITE);
//
//        JLabel lblmeternumber = new JLabel("Search By Meter Number");
//        lblmeternumber.setBounds(20, 20, 150, 20);
//        add(lblmeternumber);
//
//        meternumber = new Choice();
//        meternumber.setBounds(180, 20, 150, 20);
//        add(meternumber);
//
//        // try {
//        //     Conn c  = new Conn();
//        //     ResultSet rs = c.s.executeQuery("select * from customer");
//        //     while(rs.next()) {
//        //         meternumber.add(rs.getString("meter_no"));
//        //     }
//        // } catch (Exception e) {
//        //     e.printStackTrace();
//        // }
//
//        JLabel lblmonth = new JLabel("Search By Month");
//        lblmonth.setBounds(400, 20, 100, 20);
//        add(lblmonth);
//
//        cmonth = new Choice();
//        cmonth.setBounds(520, 20, 150, 20);
//        cmonth.add("January");
//        cmonth.add("February");
//        cmonth.add("March");
//        cmonth.add("April");
//        cmonth.add("May");
//        cmonth.add("June");
//        cmonth.add("July");
//        cmonth.add("August");
//        cmonth.add("September");
//        cmonth.add("October");
//        cmonth.add("November");
//        cmonth.add("December");
//        add(cmonth);
//
//        table = new JTable();
//
//        // try {
//        //     Conn c = new Conn();
//        //     ResultSet rs = c.s.executeQuery("select * from bill");
//
//        //     table.setModel(DbUtils.resultSetToTableModel(rs));
//        // } catch (Exception e) {
//        //     e.printStackTrace();
//        // }
//
//        JScrollPane sp = new JScrollPane(table);
//        sp.setBounds(0, 100, 700, 600);
//        add(sp);
//
//        search = new JButton("Search");
//        search.setBounds(20, 70, 80, 20);
//        search.addActionListener(this);
//        add(search);
//
//        print = new JButton("Print");
//        print.setBounds(120, 70, 80, 20);
//        print.addActionListener(this);
//        add(print);
//
//        loadMeterNumbers(); // Load meter numbers into the choice menu
//        setVisible(true);
//    }
//
//    private void loadMeterNumbers() {
//        try {
//            Connection connection = Connect.getConnection();
//            String query = "SELECT meter_number FROM customer";
//            try (PreparedStatement statement = connection.prepareStatement(query);
//                 ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    meternumber.add(resultSet.getString("meter_number"));
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//    }
//    public void actionPerformed(ActionEvent ae) {
//        if (ae.getSource() == search) {
//            String query = "SELECT * FROM bill WHERE meter = ? AND month = ?";
//            try {
//                Connection connection = Connect.getConnection();
//                try (PreparedStatement statement = connection.prepareStatement(query)) {
//                    statement.setString(1, meternumber.getSelectedItem());
//                    statement.setString(2, cmonth.getSelectedItem());
//                    try (ResultSet resultSet = statement.executeQuery()) {
//                        table.setModel(DbUtils.resultSetToTableModel(resultSet));
//                    }
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } else if (ae.getSource() == print) {
//            try {
//                table.print();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new DepositDetails();
//    }
//}


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DepositDetails extends JFrame implements ActionListener {

    Choice meternumber, cmonth;
    JTable table;
    JButton search, print;

    DepositDetails() {
        super("Deposit Details");

        setSize(700, 700);
        setLocation(400, 100);

        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblmeternumber = new JLabel("Search By Meter Number");
        lblmeternumber.setBounds(20, 20, 150, 20);
        add(lblmeternumber);

        meternumber = new Choice();
        meternumber.setBounds(180, 20, 150, 20);
        add(meternumber);

        JLabel lblmonth = new JLabel("Search By Month");
        lblmonth.setBounds(400, 20, 100, 20);
        add(lblmonth);

        cmonth = new Choice();
        cmonth.setBounds(520, 20, 150, 20);
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
        add(cmonth);

        table = new JTable();
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(0, 100, 700, 600);
        add(sp);

        search = new JButton("Search");
        search.setBounds(20, 70, 80, 20);
        search.addActionListener(this);
        add(search);

        print = new JButton("Print");
        print.setBounds(120, 70, 80, 20);
        print.addActionListener(this);
        add(print);

        setVisible(true);
        loadMeterNumbers();
    }

    private void loadMeterNumbers() {
        try {
            Connection connection = Connect.getConnection();
            String query = "SELECT meter FROM consumer";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String meterNo = resultSet.getString("meter");
                meternumber.add(meterNo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch meter numbers from the database!");
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == search) {
            String meterNo = meternumber.getSelectedItem();
            String month = cmonth.getSelectedItem();
            try {
                Connection connection = Connect.getConnection();
                String query = "SELECT * FROM bill WHERE meter = ? AND month = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, meterNo);
                statement.setString(2, month);
                ResultSet resultSet = statement.executeQuery();
                // Populate table with the result set
                table.setModel(buildTableModel(resultSet));
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to search for deposit details!");
            }
        } else if (ae.getSource() == print) {
            try {
                // Print functionality
                table.print();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to print deposit details!");
            }
        }
    }

    // Method to convert ResultSet to TableModel
    private TableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();

        // Get column names
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int column = 1; column <= columnCount; column++) {
            columnNames[column - 1] = metaData.getColumnName(column);
        }

        // Get data
        Object[][] data = new Object[100][columnCount];
        int row = 0;
        while (resultSet.next()) {
            for (int column = 1; column <= columnCount; column++) {
                data[row][column - 1] = resultSet.getObject(column);
            }
            row++;
        }

        return new DefaultTableModel(data, columnNames);
    }

    public static void main(String[] args) {
        new DepositDetails();
    }
}
