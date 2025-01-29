import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EditServiceDialog extends JDialog {
    private JTextField descriptionField;
    private JTextField costField;
    private JTextField dateField;
    private JComboBox<String> statusComboBox;
    private JTextField mechanicField;
    private int serviceIndex;
    private List<String> services;

    public EditServiceDialog(JFrame parent, int serviceIndex) {
        super(parent, Messages.get("dialog.editService.title"), true);
        this.serviceIndex = serviceIndex;

        services = DataManager.loadServices();
        String service = services.get(serviceIndex);
        String[] serviceData = service.split(",");

        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel(Messages.get("label.description") + ":"));
        descriptionField = new JTextField(serviceData[0]);
        panel.add(descriptionField);
        panel.add(new JLabel(Messages.get("label.cost") + ":"));
        costField = new JTextField(serviceData[1]);
        panel.add(costField);
        panel.add(new JLabel(Messages.get("label.date") + ":"));
        dateField = new JTextField(serviceData[2]);
        panel.add(dateField);
        panel.add(new JLabel(Messages.get("label.status") + ":"));
        statusComboBox = new JComboBox<>(new String[]{Messages.get("status.completed"), Messages.get("status.inProgress")});
        statusComboBox.setSelectedItem(serviceData[3]);
        panel.add(statusComboBox);
        panel.add(new JLabel(Messages.get("label.mechanic") + ":"));
        mechanicField = new JTextField(serviceData[4]);
        panel.add(mechanicField);

        JButton saveButton = new JButton(Messages.get("button.save"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    saveService();
                } else {
                    JOptionPane.showMessageDialog(EditServiceDialog.this, Messages.get("error.fillAllFields"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
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
        return !descriptionField.getText().trim().isEmpty() && !costField.getText().trim().isEmpty() && !dateField.getText().trim().isEmpty() && !mechanicField.getText().trim().isEmpty();
    }

    private void saveService() {
        String description = descriptionField.getText();
        String cost = costField.getText();
        String date = dateField.getText();
        String status = (String) statusComboBox.getSelectedItem();
        String mechanic = mechanicField.getText();
        services.set(serviceIndex, description + "," + cost + "," + date + "," + status + "," + mechanic);
        DataManager.saveServices(services);
        dispose();
    }
}
