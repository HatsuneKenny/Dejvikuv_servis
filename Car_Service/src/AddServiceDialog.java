import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddServiceDialog extends JDialog {
    private JTextField descriptionField;
    private JTextField costField;
    private JTextField dateField;
    private JComboBox<String> statusComboBox;
    private JTextField mechanicField;

    public AddServiceDialog(JFrame parent) {
        super(parent, Messages.get("dialog.addService.title"), true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel(Messages.get("label.description") + ":"));
        descriptionField = new JTextField();
        panel.add(descriptionField);
        panel.add(new JLabel(Messages.get("label.cost") + ":"));
        costField = new JTextField();
        panel.add(costField);
        panel.add(new JLabel(Messages.get("label.date") + ":"));
        dateField = new JTextField();
        panel.add(dateField);
        panel.add(new JLabel(Messages.get("label.status") + ":"));
        statusComboBox = new JComboBox<>(new String[]{Messages.get("status.completed"), Messages.get("status.inProgress")});
        panel.add(statusComboBox);
        panel.add(new JLabel(Messages.get("label.mechanic") + ":"));
        mechanicField = new JTextField();
        panel.add(mechanicField);

        JButton addButton = new JButton(Messages.get("button.add"));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    addService();
                } else {
                    JOptionPane.showMessageDialog(AddServiceDialog.this, Messages.get("error.fillAllFields"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
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
        return !descriptionField.getText().trim().isEmpty() && !costField.getText().trim().isEmpty() && !dateField.getText().trim().isEmpty() && !mechanicField.getText().trim().isEmpty();
    }

    private void addService() {
        String description = descriptionField.getText();
        String cost = costField.getText();
        String date = dateField.getText();
        String status = (String) statusComboBox.getSelectedItem();
        String mechanic = mechanicField.getText();
        List<String> services = DataManager.loadServices();
        services.add(description + "," + cost + "," + date + "," + status + "," + mechanic);
        DataManager.saveServices(services);
        dispose();
    }
}
