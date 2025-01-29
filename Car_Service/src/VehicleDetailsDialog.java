import javax.swing.*;
import java.awt.*;

public class VehicleDetailsDialog extends JDialog {
    public VehicleDetailsDialog(JDialog parent, String vehicleDetails) {
        super(parent, Messages.get("dialog.vehicleDetails.title"), true);
        setLayout(new BorderLayout());

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(7, 2)); // 7 řádků a 2 sloupce pro štítky a hodnoty

        String[] details = vehicleDetails.split(",");

        detailsPanel.add(new JLabel(Messages.get("label.make") + ":"));
        detailsPanel.add(new JLabel(details[0]));

        detailsPanel.add(new JLabel(Messages.get("label.model") + ":"));
        detailsPanel.add(new JLabel(details[1]));

        detailsPanel.add(new JLabel(Messages.get("label.year") + ":"));
        detailsPanel.add(new JLabel(details[2]));

        detailsPanel.add(new JLabel(Messages.get("label.vin") + ":"));
        detailsPanel.add(new JLabel(details[3]));

        detailsPanel.add(new JLabel(Messages.get("label.color") + ":"));
        detailsPanel.add(new JLabel(details[4]));

        detailsPanel.add(new JLabel(Messages.get("label.fuelType") + ":"));
        detailsPanel.add(new JLabel(details[5]));

        detailsPanel.add(new JLabel(Messages.get("label.notes") + ":"));
        detailsPanel.add(new JLabel(details[6]));

        add(detailsPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton(Messages.get("button.close"));
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
