import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class AddCustomerDialog extends JDialog {
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;

    public AddCustomerDialog(Frame parent) {
        super(parent, Messages.get("dialog.addCustomer.title"), true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel(Messages.get("label.name") + ":"));
        nameField = new JTextField();
        panel.add(nameField);
        panel.add(new JLabel(Messages.get("label.phone") + ":"));
        phoneField = new JTextField();
        panel.add(phoneField);
        panel.add(new JLabel(Messages.get("label.email") + ":"));
        emailField = new JTextField();
        panel.add(emailField);

        JButton addButton = new JButton(Messages.get("button.add"));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    addCustomer();
                } else {
                    JOptionPane.showMessageDialog(AddCustomerDialog.this, Messages.get("error.fillAllFields"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private boolean validateInput() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z\\s]+$", name)) {
            JOptionPane.showMessageDialog(this, "Name can only contain letters and spaces.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("^[0-9]{9}$", phone)) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 9 digits.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addCustomer() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        List<String> customers = DataManager.loadCustomers();
        customers.add(name + "," + phone + "," + email);
        DataManager.saveCustomers(customers);
        dispose();
    }
}
