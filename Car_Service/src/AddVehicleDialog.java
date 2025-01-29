import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class AddVehicleDialog extends JDialog {
    private JTextField makeField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField vinField;
    private JTextField colorField;
    private JTextField fuelTypeField;
    private JTextField notesField;

    public AddVehicleDialog(Frame parent) {
        super(parent, Messages.get("dialog.addVehicle.title"), true);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel(Messages.get("label.make") + ":"));
        makeField = new JTextField();
        panel.add(makeField);
        panel.add(new JLabel(Messages.get("label.model") + ":"));
        modelField = new JTextField();
        panel.add(modelField);
        panel.add(new JLabel(Messages.get("label.year") + ":"));
        yearField = new JTextField();
        panel.add(yearField);
        panel.add(new JLabel(Messages.get("label.vin") + ":"));
        vinField = new JTextField();
        panel.add(vinField);
        panel.add(new JLabel(Messages.get("label.color") + ":"));
        colorField = new JTextField();
        panel.add(colorField);
        panel.add(new JLabel(Messages.get("label.fuelType") + ":"));
        fuelTypeField = new JTextField();
        panel.add(fuelTypeField);
        panel.add(new JLabel(Messages.get("label.notes") + ":"));
        notesField = new JTextField();
        panel.add(notesField);

        JButton addButton = new JButton(Messages.get("button.add"));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    addVehicle();
                } else {
                    JOptionPane.showMessageDialog(AddVehicleDialog.this, Messages.get("error.fillAllFields"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
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
        String make = makeField.getText().trim();
        String model = modelField.getText().trim();
        String year = yearField.getText().trim();
        String vin = vinField.getText().trim();
        String color = colorField.getText().trim();
        String fuelType = fuelTypeField.getText().trim();
        String notes = notesField.getText().trim();

        if (make.isEmpty() || model.isEmpty() || year.isEmpty() || vin.isEmpty() || color.isEmpty() || fuelType.isEmpty() || notes.isEmpty()) {
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z\\s]+$", make)) {
            JOptionPane.showMessageDialog(this, "Make can only contain letters and spaces.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9\\s]+$", model)) {
            JOptionPane.showMessageDialog(this, "Model can only contain letters, numbers, and spaces.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("^\\d{4}$", year)) {
            JOptionPane.showMessageDialog(this, "Year must be a 4-digit number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!Pattern.matches("^[A-HJ-NPR-Z0-9]{17}$", vin)) {
            JOptionPane.showMessageDialog(this, "VIN must be a 17-character alphanumeric string without I, O, or Q.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void addVehicle() {
        String make = makeField.getText();
        String model = modelField.getText();
        String year = yearField.getText();
        String vin = vinField.getText();
        String color = colorField.getText();
        String fuelType = fuelTypeField.getText();
        String notes = notesField.getText();
        List<String> vehicles = DataManager.loadVehicles();
        vehicles.add(make + "," + model + "," + year + "," + vin + "," + color + "," + fuelType + "," + notes);
        DataManager.saveVehicles(vehicles);
        dispose();
    }
}
