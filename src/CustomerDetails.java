import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import java.awt.event.*;

public class CustomerDetails extends JFrame implements ActionListener {
    JTable table;
    JButton print;

    CustomerDetails() {
        super("Customer Details");

        setSize(1200, 650);
        setLocation(200, 150);

        // Sample static data
        Object[][] data = {
            {"Rajesh Kumar", "123456", "12 Gandhi Nagar", "Delhi", "Delhi", "rajesh@example.com", "9876543210"},
            {"Priya Patel", "789012", "45 Patel Lane", "Mumbai", "Maharashtra", "priya@example.com", "9876543210"},
            {"Amit Singh", "345678", "78 Singh Vihar", "Kolkata", "West Bengal", "amit@example.com", "9876543210"},
            {"Deepika Sharma", "901234", "34 Sharma Colony", "Chennai", "Tamil Nadu", "deepika@example.com", "9876543210"},
            {"Manish Gupta", "567890", "67 Gupta Street", "Bangalore", "Karnataka", "manish@example.com", "9876543210"}
        };
        // Column names
        String[] columnNames = {"Name", "Meter No.", "Address", "City", "State", "Email", "Phone"};

        // Create table model with static data
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.CYAN);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JScrollPane sp = new JScrollPane(table);
        add(sp);

        // Add print button
        print = new JButton("Print");
        print.addActionListener(this);
        add(print, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            table.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerDetails();
    }
}
