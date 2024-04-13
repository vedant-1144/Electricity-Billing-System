package New_Origin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDetails extends JFrame implements ActionListener {
    JTable table;
    JButton print;

    CustomerDetails() {
        super("Customer Details");

        setSize(1200, 650);
        setLocation(200, 150);

        // Column names
        String[] columnNames = {"Name", "Meter No.", "Address", "City", "State", "Email", "Phone"};

        // Create table model with static data
        DefaultTableModel model = new DefaultTableModel(null, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        fetchData(model);
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

    private void fetchData(DefaultTableModel model){
        String query = "Select name,meter_number,address, city ,state ,email ,phone from Consumer";

        try(
                Connection c = Connect.getConnection();
                PreparedStatement statement = c.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()
        ){
            while(resultSet.next()) {
                String[] row  = {
                        resultSet.getString("name"),
                        resultSet.getString("meter_number"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                };
                model.addRow(row);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
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
