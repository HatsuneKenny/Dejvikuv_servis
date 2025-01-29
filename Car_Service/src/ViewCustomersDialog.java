import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ViewCustomersDialog extends JDialog {
    private JList<String> customerList;
    private DefaultListModel<String> listModel;
    private JTextField searchField;

    public ViewCustomersDialog(JFrame parent) {
        super(parent, Messages.get("dialog.viewCustomers.title"), true);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        JButton searchButton = new JButton(Messages.get("button.search"));

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterCustomers();
            }
        });

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);
        add(searchPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        List<String> customers = DataManager.loadCustomers();
        for (String customer : customers) {
            listModel.addElement(formatCustomer(customer));
        }

        customerList = new JList<>(listModel);
        add(new JScrollPane(customerList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton(Messages.get("button.edit"));
        JButton deleteButton = new JButton(Messages.get("button.delete"));

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = customerList.getSelectedIndex();
                if (selectedIndex != -1) {
                    new EditCustomerDialog(parent, selectedIndex);
                    reloadCustomers();
                } else {
                    JOptionPane.showMessageDialog(ViewCustomersDialog.this, Messages.get("error.selectItemToEdit"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = customerList.getSelectedIndex();
                if (selectedIndex != -1) {
                    int confirm = JOptionPane.showConfirmDialog(ViewCustomersDialog.this, Messages.get("confirm.delete"), Messages.get("button.confirmation"), JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        deleteCustomer(selectedIndex);
                        reloadCustomers();
                    }
                } else {
                    JOptionPane.showMessageDialog(ViewCustomersDialog.this, Messages.get("error.selectItemToDelete"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void filterCustomers() {
        String searchText = searchField.getText().toLowerCase();
        listModel.clear();
        List<String> customers = DataManager.loadCustomers();
        for (String customer : customers) {
            if (customer.toLowerCase().contains(searchText)) {
                listModel.addElement(formatCustomer(customer));
            }
        }
    }

    private void reloadCustomers() {
        listModel.clear();
        List<String> customers = DataManager.loadCustomers();
        for (String customer : customers) {
            listModel.addElement(formatCustomer(customer));
        }
    }

    private void deleteCustomer(int index) {
        List<String> customers = DataManager.loadCustomers();
        customers.remove(index);
        DataManager.saveCustomers(customers);
    }

    private String formatCustomer(String customer) {
        String[] customerData = customer.split(",");
        return String.format("%s, %s, %s", customerData[0], customerData[1], customerData[2]);
    }
}
