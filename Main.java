import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Main extends JFrame {

    // CSV file paths
    private static final String BOOK_FILE = "books.csv";
    private static final String STAFF_FILE = "staff.csv";
    private static final String ALLOCATION_FILE = "allocation.csv";

    private JTable booksTable, staffTable, allocationTable;
    private JTextField bookTitleField, authorField, publisherField, bookIdField;
    private JTextField staffNameField, staffIdField, roleField;
    private JTextField bookIdAllocationField, MemberIdAllocationField;


    public Main() {
        setTitle("Library Management Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Create UI components
        createUI();

        // Load data from CSV files
        loadBooksFromCSV();
        loadStaffFromCSV();
        loadAllocationFromCSV();
    }

    private void createUI() {
        // Books panel
        JPanel booksPanel = createBooksPanel();

        // Staff panel
        JPanel staffPanel = createStaffPanel();

        // Allocation panel
        JPanel allocationPanel = createAllocationPanel();

        // Admin panel
        JPanel adminPanel = createAdminPanel();

        // Add panels to the frame
        setLayout(new GridLayout(2, 2, 10, 10));
        add(booksPanel);
        add(staffPanel);
        add(allocationPanel);
        add(adminPanel);
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Books"));

        // Books table
        booksTable = new JTable();
        JScrollPane booksScrollPane = new JScrollPane(booksTable);
        panel.add(booksScrollPane, BorderLayout.CENTER);

        // Books input fields
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        bookTitleField = new JTextField();
        authorField = new JTextField();
        publisherField = new JTextField();
        bookIdField = new JTextField();
        inputPanel.add(new JLabel("Book Title:"));
        inputPanel.add(bookTitleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Publisher:"));
        inputPanel.add(publisherField);
        inputPanel.add(new JLabel("Book ID:"));
        inputPanel.add(bookIdField);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Books actions
        JPanel actionsPanel = new JPanel();
        JButton addBookButton = new JButton("Add Book");
        JButton removeBookButton = new JButton("Remove Book");
        addBookButton.addActionListener(e -> addBook());
        removeBookButton.addActionListener(e -> removeBook());
        actionsPanel.add(addBookButton);
        actionsPanel.add(removeBookButton);
        panel.add(actionsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createStaffPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Staff"));

        // Staff table
        staffTable = new JTable();
        JScrollPane staffScrollPane = new JScrollPane(staffTable);
        panel.add(staffScrollPane, BorderLayout.CENTER);

        // Staff input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        staffNameField = new JTextField();
        staffIdField = new JTextField();
        roleField = new JTextField();
        inputPanel.add(new JLabel("Staff Name:"));
        inputPanel.add(staffNameField);
        inputPanel.add(new JLabel("Staff ID:"));
        inputPanel.add(staffIdField);
        inputPanel.add(new JLabel("Role:"));
        inputPanel.add(roleField);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Staff actions
        JPanel actionsPanel = new JPanel();
        JButton addStaffButton = new JButton("Add Staff");
        JButton removeStaffButton = new JButton("Remove Staff");
        addStaffButton.addActionListener(e -> addStaff());
        removeStaffButton.addActionListener(e -> removeStaff());
        actionsPanel.add(addStaffButton);
        actionsPanel.add(removeStaffButton);
        panel.add(actionsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAllocationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Allocation"));

        // Allocation table
        allocationTable = new JTable();
        JScrollPane allocationScrollPane = new JScrollPane(allocationTable);
        panel.add(allocationScrollPane, BorderLayout.CENTER);

        // Allocation input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bookIdAllocationField = new JTextField();
        MemberIdAllocationField = new JTextField();
        inputPanel.add(new JLabel("Book ID:"));
        inputPanel.add(bookIdAllocationField);
        inputPanel.add(new JLabel("Staff  ID:"));
        inputPanel.add(MemberIdAllocationField);
        panel.add(inputPanel, BorderLayout.NORTH);

        // Allocation actions
        JPanel actionsPanel = new JPanel();
        JButton allocateBookButton = new JButton("Allocate Book");
        JButton deallocateBookButton = new JButton("Deallocate Book");
        allocateBookButton.addActionListener(e -> allocateBook());
        deallocateBookButton.addActionListener(e -> deallocateBook());
        actionsPanel.add(allocateBookButton);
        actionsPanel.add(deallocateBookButton);
        panel.add(actionsPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Admin"));

        // Admin actions
        JPanel actionsPanel = new JPanel();
        JButton saveChangesButton = new JButton("Save Changes");
        JButton refreshDataButton = new JButton("Refresh Data");
        saveChangesButton.addActionListener(e -> saveDataToCSV());
        refreshDataButton.addActionListener(e -> refreshData());
        actionsPanel.add(saveChangesButton);
        actionsPanel.add(refreshDataButton);
        panel.add(actionsPanel, BorderLayout.NORTH);

        return panel;
    }

    private void addBook() {
        String bookTitle = bookTitleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        String bookId = bookIdField.getText();

        DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
        model.addRow(new Object[]{bookTitle, author, publisher, bookId});

        bookTitleField.setText("");
        authorField.setText("");
        publisherField.setText("");
        bookIdField.setText("");
    }

    private void removeBook() {
        int selectedRow = booksTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) booksTable.getModel();
            model.removeRow(selectedRow);
        }
    }

    private void addStaff() {
        String staffName = staffNameField.getText();
        String staffId = staffIdField.getText();
        String role = roleField.getText();

        DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
        model.addRow(new Object[]{staffName, staffId, role});
        writeStaffToCSV(staffName, staffId, role);  // Immediately write to CSV

        staffNameField.setText("");
        staffIdField.setText("");
        roleField.setText("");
    }

    private void removeStaff() {
        int selectedRow = staffTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) staffTable.getModel();
            model.removeRow(selectedRow);
        }
    }

    private void allocateBook() {
        String bookId = bookIdAllocationField.getText();
        String memberId = MemberIdAllocationField.getText();

        DefaultTableModel model = (DefaultTableModel) allocationTable.getModel();
        model.addRow(new Object[]{bookId, memberId});

        bookIdAllocationField.setText("");
        MemberIdAllocationField.setText("");
    }

    private void deallocateBook() {
        int selectedRow = allocationTable.getSelectedRow();
        if (selectedRow != -1) {
            DefaultTableModel model = (DefaultTableModel) allocationTable.getModel();
            model.removeRow(selectedRow);
        }
    }

    private void refreshData() {
        loadBooksFromCSV();
        loadStaffFromCSV();
        loadAllocationFromCSV();
    }

    private void loadBooksFromCSV() {
        try {
            ArrayList<String[]> bookData = new ArrayList<>();
            File file = new File(BOOK_FILE);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    bookData.add(data);
                }
                br.close();
            }

            DefaultTableModel model = new DefaultTableModel(new String[]{"Title", "Author", "Publisher", "ID"}, 0);
            bookData.forEach(model::addRow);
            booksTable.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStaffFromCSV() {
        try {
            ArrayList<String[]> staffData = new ArrayList<>();
            File file = new File(STAFF_FILE);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    staffData.add(data);
                }
                br.close();
            }

            DefaultTableModel model = new DefaultTableModel(new String[]{"Name", "ID", "Role"}, 0);
            staffData.forEach(model::addRow);
            staffTable.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAllocationFromCSV() {
        try {
            ArrayList<String[]> allocationData = new ArrayList<>();
            File file = new File(ALLOCATION_FILE);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    allocationData.add(data);
                }
                br.close();
            }

            DefaultTableModel model = new DefaultTableModel(new String[]{"Book ID", "Staff ID"}, 0);
            allocationData.forEach(model::addRow);
            allocationTable.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveDataToCSV() {
        try {
            FileWriter bookWriter = new FileWriter(BOOK_FILE);
            FileWriter staffWriter = new FileWriter(STAFF_FILE);
            FileWriter allocationWriter = new FileWriter(ALLOCATION_FILE);

            DefaultTableModel bookModel = (DefaultTableModel) booksTable.getModel();
            for (int i = 0; i < bookModel.getRowCount(); i++) {
                bookWriter.write(bookModel.getValueAt(i, 0) + "," +
                        bookModel.getValueAt(i, 1) + "," +
                        bookModel.getValueAt(i, 2) + "," +
                        bookModel.getValueAt(i, 3) + "\n");
            }
            bookWriter.close();

            DefaultTableModel staffModel = (DefaultTableModel) staffTable.getModel();
            for (int i = 0; i < staffModel.getRowCount(); i++) {
                staffWriter.write(staffModel.getValueAt(i, 0) + "," +
                        staffModel.getValueAt(i, 1) + "," +
                        staffModel.getValueAt(i, 2) + "\n");
            }
            staffWriter.close();

            DefaultTableModel allocationModel = (DefaultTableModel) allocationTable.getModel();
            for (int i = 0; i < allocationModel.getRowCount(); i++) {
                allocationWriter.write(allocationModel.getValueAt(i, 0) + "," +
                        allocationModel.getValueAt(i, 1) + "\n");
            }
            allocationWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeStaffToCSV(String name, String id, String role) {
        try (FileWriter fw = new FileWriter(STAFF_FILE, true)) {
            fw.write(name + "," + id + "," + role + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
            Main app = new Main();
            app.setVisible(true);
        });
    }
}