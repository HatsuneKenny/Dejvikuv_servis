import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewVehiclesDialog extends JDialog {
    private JFrame parent;
    private JTextField searchField;
    private JButton searchButton, addButton, editButton, deleteButton;
    private JList<String> vehicleList;
    private DefaultListModel<String> vehicleListModel;

    public ViewVehiclesDialog(JFrame parent) {
        super(parent, Messages.get("dialog.viewVehicles.title"), true);
        this.parent = parent;

        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchButton = new JButton(Messages.get("button.search"));
        searchButton.addActionListener(e -> filterVehicles());
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        vehicleListModel = new DefaultListModel<>();
        vehicleList = new JList<>(vehicleListModel);
        loadVehicles();
        add(new JScrollPane(vehicleList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        addButton = new JButton(Messages.get("button.add"));
        editButton = new JButton(Messages.get("button.edit"));
        deleteButton = new JButton(Messages.get("button.delete"));

        addButton.addActionListener(e -> new AddVehicleDialog(parent));
        editButton.addActionListener(e -> editSelectedVehicle());
        deleteButton.addActionListener(e -> deleteSelectedVehicle());

        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadVehicles() {
        List<String> vehicles = DataManager.loadVehicles();
        vehicleListModel.clear();
        for (String vehicle : vehicles) {
            vehicleListModel.addElement(vehicle);
        }
    }

    private void filterVehicles() {
        String query = searchField.getText().toLowerCase();
        List<String> vehicles = DataManager.loadVehicles();
        List<String> filteredVehicles = vehicles.stream()
                .filter(vehicle -> vehicle.toLowerCase().contains(query))
                .collect(Collectors.toList());

        vehicleListModel.clear();
        for (String vehicle : filteredVehicles) {
            vehicleListModel.addElement(vehicle);
        }
    }

    private void editSelectedVehicle() {
        int selectedIndex = vehicleList.getSelectedIndex();
        if (selectedIndex != -1) {
            new EditVehicleDialog(parent, selectedIndex); // Použití parent místo this
            loadVehicles(); // Aktualizace seznamu po úpravě
        } else {
            JOptionPane.showMessageDialog(this, Messages.get("error.noSelection"), Messages.get("error.title"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteSelectedVehicle() {
        int selectedIndex = vehicleList.getSelectedIndex();
        if (selectedIndex != -1) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    Messages.get("confirm.deleteVehicle"),
                    Messages.get("confirm.title"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                List<String> vehicles = DataManager.loadVehicles();
                vehicles.remove(selectedIndex);
                DataManager.saveVehicles(vehicles);
                loadVehicles(); // Aktualizace seznamu po smazání
            }
        } else {
            JOptionPane.showMessageDialog(this, Messages.get("error.noSelection"), Messages.get("error.title"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
