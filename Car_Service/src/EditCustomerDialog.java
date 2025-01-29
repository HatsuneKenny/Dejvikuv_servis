import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditCustomerDialog extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private int customerIndex;
    private List<String> customers;

    public EditCustomerDialog(Frame parent, int customerIndex) {
        super(parent, Messages.get("dialog.editCustomer.title"), true);
        this.customerIndex = customerIndex;

        customers = DataManager.loadCustomers();
        String customer = customers.get(customerIndex);
        String[] customerData = customer.split(",");

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel(Messages.get("label.name") + ":"));
        nameField = new JTextField(customerData[0]);
        panel.add(nameField);
        panel.add(new JLabel(Messages.get("label.phone") + ":"));
        phoneField = new JTextField(customerData[1]);
        panel.add(phoneField);
        panel.add(new JLabel(Messages.get("label.email") + ":"));
        emailField = new JTextField(customerData[2]);
        panel.add(emailField);

        JButton saveButton = new JButton(Messages.get("button.save"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    saveCustomer();
                } else {
                    JOptionPane.showMessageDialog(EditCustomerDialog.this, Messages.get("error.fillAllFields"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private boolean validateInput() {
        return !nameField.getText().trim().isEmpty() && !phoneField.getText().trim().isEmpty() && !emailField.getText().trim().isEmpty();
    }

    private void saveCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        customers.set(customerIndex, name + "," + phone + "," + email);
        DataManager.saveCustomers(customers);
        dispose();
    }
}
