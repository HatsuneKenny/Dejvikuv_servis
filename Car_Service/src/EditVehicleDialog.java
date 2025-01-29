import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class EditVehicleDialog extends JDialog {
    private JTextField makeField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField vinField;
    private JTextField colorField;
    private JTextField fuelTypeField;
    private JTextField notesField;
    private int vehicleIndex;

    public EditVehicleDialog(Frame parent, int vehicleIndex) {
        super(parent, Messages.get("dialog.editVehicle.title"), true);
        this.vehicleIndex = vehicleIndex;
        setLayout(new BorderLayout());

        List<String> vehicles = DataManager.loadVehicles();
        String[] vehicleData = vehicles.get(vehicleIndex).split(",");

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel(Messages.get("label.make") + ":"));
        makeField = new JTextField(vehicleData[0]);
        panel.add(makeField);
        panel.add(new JLabel(Messages.get("label.model") + ":"));
        modelField = new JTextField(vehicleData[1]);
        panel.add(modelField);
        panel.add(new JLabel(Messages.get("label.year") + ":"));
        yearField = new JTextField(vehicleData[2]);
        panel.add(yearField);
        panel.add(new JLabel(Messages.get("label.vin") + ":"));
        vinField = new JTextField(vehicleData[3]);
        panel.add(vinField);
        panel.add(new JLabel(Messages.get("label.color") + ":"));
        colorField = new JTextField(vehicleData[4]);
        panel.add(colorField);
        panel.add(new JLabel(Messages.get("label.fuelType") + ":"));
        fuelTypeField = new JTextField(vehicleData[5]);
        panel.add(fuelTypeField);
        panel.add(new JLabel(Messages.get("label.notes") + ":"));
        notesField = new JTextField(vehicleData[6]);
        panel.add(notesField);

        JButton saveButton = new JButton(Messages.get("button.save"));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    saveVehicle();
                } else {
                    JOptionPane.showMessageDialog(EditVehicleDialog.this, Messages.get("error.fillAllFields"), Messages.get("button.error"), JOptionPane.ERROR_MESSAGE);
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

    private void saveVehicle() {
        String make = makeField.getText();
        String model = modelField.getText();
        String year = yearField.getText();
        String vin = vinField.getText();
        String color = colorField.getText();
        String fuelType = fuelTypeField.getText();
        String notes = notesField.getText();

        List<String> vehicles = DataManager.loadVehicles();
        vehicles.set(vehicleIndex, make + "," + model + "," + year + "," + vin + "," + color + "," + fuelType + "," + notes);
        DataManager.saveVehicles(vehicles);
        dispose();
    }
}
