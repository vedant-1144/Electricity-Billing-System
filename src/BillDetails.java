import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.*;

public class BillDetails extends JFrame {

    BillDetails(String meter) {
        setSize(700, 650);
        setLocation(400, 150);

        getContentPane().setBackground(Color.WHITE);
        JTable table = new JTable();

        try {
            // Establish database connection
            Connection conn = Connect.getConnection();

            // Query to select bill details for a specific meter number
            String query = "SELECT meter, month, units, totalbill, status FROM bill WHERE meter = ?";
            PreparedStatement statement00 = conn.prepareStatement(query);
            statement00.setString(1, meter);

            // Execute the query
            ResultSet rs = statement00.executeQuery();

            // Populate JTable with the result set
            DefaultTableModel model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.addColumn("Meter No.");
            model.addColumn("Month");
            model.addColumn("Units");
            model.addColumn("Total Bill");
            model.addColumn("Status");

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getString("meter");
                row[1] = rs.getString("month");
                row[2] = rs.getInt("units");
                row[3] = rs.getInt("totalbill");
                row[4] = rs.getString("status");
                model.addRow(row);
            }

            table.setModel(model);
            rs.close();
            statement00.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.CYAN);
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JScrollPane sp = new JScrollPane(table);
        add(sp);

        setVisible(true);
    }

    public static void main(String[] args) {
        new BillDetails("");
    }
}


//        // try {
//        //     Conn c = new Conn();
//        //     String query = "select * from bill where meter_no = '"+meter+"'";
//        //     ResultSet rs = c.executeQuery(query);
//        //     table.setModel(DbUtils.resultSetToTableModel(rs));
//        // } catch (Exception e) {
//        //     e.printStackTrace();
//        // }
//
//        // Static data for the Bill table
//        Object[][] data = {
//            {"123456", "January", 200, 1500, "Paid"},
//            {"123456", "February", 180, 1350, "Paid"},
//            {"123456", "March", 220, 1650, "Paid"},
//            {"789012", "January", 150, 1100, "Unpaid"},
//            {"789012", "February", 170, 1250, "Unpaid"},
//            {"789012", "March", 190, 1400, "Unpaid"}
//        };
//        // Column names
//        String[] columnNames = {"Meter No.", "Month", "Units", "Total Bill", "Status"};
//        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//
//        table.setModel(model);
//        JTableHeader header = table.getTableHeader();
//        header.setBackground(Color.CYAN);
//        header.setForeground(Color.BLACK);
//        header.setFont(new Font("Times New Roman", Font.BOLD, 16));
//
//        JScrollPane sp = new JScrollPane(table);
//        add(sp);
//
//        setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        new BillDetails("");
//    }
//}
