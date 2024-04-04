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
        // try {
        //     Conn c = new Conn();
        //     String query = "select * from bill where meter_no = '"+meter+"'";
        //     ResultSet rs = c.executeQuery(query);
        //     table.setModel(DbUtils.resultSetToTableModel(rs));
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

        // Static data for the Bill table
        Object[][] data = {
            {"123456", "January", 200, 1500, "Paid"},
            {"123456", "February", 180, 1350, "Paid"},
            {"123456", "March", 220, 1650, "Paid"},
            {"789012", "January", 150, 1100, "Unpaid"},
            {"789012", "February", 170, 1250, "Unpaid"},
            {"789012", "March", 190, 1400, "Unpaid"}
        };
        // Column names
        String[] columnNames = {"Meter No.", "Month", "Units", "Total Bill", "Status"};
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);
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
