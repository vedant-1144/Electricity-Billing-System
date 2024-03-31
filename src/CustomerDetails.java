import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;

public class CustomerDetails extends JFrame implements ActionListener {
    Choice meternumber, cmonth;
    JTable table;
    JButton print;

    CustomerDetails() {
        super("Customer Details");

        setSize(1200, 650);
        setLocation(200, 150);

        table = new JTable();

        try {
            Conn c = new Conn();
            ResultSet rs = c.executeQuery("SELECT * FROM Customer");
            // Convert ResultSet to TableModel
            DefaultTableModel model = new DefaultTableModel();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            // Add columns to the model
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                model.addColumn(metaData.getColumnLabel(columnIndex));
            }
            // Add rows to the model
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                model.addRow(row);
            }
            // Set the model to your table
            table.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane sp = new JScrollPane(table);
        add(sp);

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
