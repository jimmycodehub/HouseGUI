import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import java.text.NumberFormat;
import java.util.*;

public class HouseApplication extends JFrame {
	
	ArrayList<House> houseList = new ArrayList<House>();
	JMenuBar menuBar;
	JMenu listMenu, closeMenu;
	JMenuItem firstItem, nextItem, prevItem, lastItem, editItem, closeApp;
	JButton firstItemButton, nextItemButton, prevItemButton, lastItemButton, editItemButton;
	JLabel houseImageLabel, idLabel, streetLabel, cityLabel, bedroomsLabel, bathroomsLabel, priceLabel, changeLabel, contactNoLabel;
	JTextField idTextField, streetTextField, cityTextField, bedroomsTextField, bathroomsTextField, priceTextField, changeTextField, contactNoTextField;
	String[][] records = { {"1", "113 The Maltings", "Dublin 8", "2", "1", "155500.00", "House1.jpg", "(087) 9011135"},
			   {"2", "78 Newington Lodge", "Dublin 14", "3", "2", "310000.00", "House2.jpg", "(087) 9010580"},
			   {"3", "62 Bohernabreena Road", "Dublin 24", "3", "1", "220000.00", "House3.jpg", "(087) 6023159"},
			   {"4", "18 Castledevitt Park", "Dublin 15", "3", "3", "325000.00", "House4.jpg", "(087) 9010580"},
			   {"5", "40 Dunsawny Road", "Swords", "3", "19", "245000.00", "House5.jpg", "(087) 9011135"}
	};
	int currentItem;
	
	public HouseApplication() {
		super("Estate Agent Application");
		for (int i = 0; i < records.length; i++) {
			houseList.add(new House(Integer.parseInt(records[i][0]), records[i][1], records[i][2], Integer.parseInt(records[i][3]), 
					Integer.parseInt(records[i][4]), Double.parseDouble(records[i][5]), records[i][6], records[i][7]));
		}
		currentItem = 0;
		initComponents();	
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		JPanel displayPanel = new JPanel(new MigLayout());
		
		// Ensures that image is centred in label
		houseImageLabel = new JLabel(new ImageIcon(), SwingConstants.CENTER);
		displayPanel.add(houseImageLabel, "push, grow, span 2, wrap");
		
		idLabel = new JLabel("House ID: ");
		idTextField = new JTextField(3);
		idTextField.setEditable(false);
		
		displayPanel.add(idLabel, "growx, pushx");
		displayPanel.add(idTextField, "growx, pushx, wrap");

		streetLabel = new JLabel("Address Line 1: ");
		streetTextField = new JTextField(15);
		streetTextField.setEditable(false);
		
		displayPanel.add(streetLabel, "growx, pushx");
		displayPanel.add(streetTextField, "growx, pushx, wrap");

		cityLabel = new JLabel("Address Line 2: ");
		cityTextField = new JTextField(15);
		cityTextField.setEditable(false);
		
		displayPanel.add(cityLabel, "growx, pushx");
		displayPanel.add(cityTextField, "growx, pushx, wrap");

		bedroomsLabel = new JLabel("Number of bedrooms: ");
		bedroomsTextField = new JTextField(3);
		bedroomsTextField.setEditable(false);
		
		displayPanel.add(bedroomsLabel, "growx, pushx");
		displayPanel.add(bedroomsTextField, "growx, pushx, wrap");

		bathroomsLabel = new JLabel("Number of bathrooms: ");
		bathroomsTextField = new JTextField(3);
		bathroomsTextField.setEditable(false);
		
		displayPanel.add(bathroomsLabel, "growx, pushx");
		displayPanel.add(bathroomsTextField, "growx, pushx, wrap");

		priceLabel = new JLabel("Price: ");
		priceTextField = new JTextField(10);
		priceTextField.setEditable(false);
		
		displayPanel.add(priceLabel, "growx, pushx");
		displayPanel.add(priceTextField, "growx, pushx, wrap");
		
		changeLabel = new JLabel("Price change: ");
		changeTextField = new JTextField(10);
		changeTextField.setEditable(false);
		
		displayPanel.add(changeLabel, "growx, pushx");
		displayPanel.add(changeTextField, "growx, pushx, wrap");
		
		contactNoLabel = new JLabel("Contact number: ");
		contactNoTextField = new JTextField(15);
		contactNoTextField.setEditable(false);
		
		displayPanel.add(contactNoLabel, "growx, pushx");
		displayPanel.add(contactNoTextField, "growx, pushx, wrap");
		add(displayPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 5));

		firstItemButton = new JButton(new ImageIcon("first.png"));
		nextItemButton = new JButton(new ImageIcon("next.png"));
		editItemButton = new JButton("Edit");
		prevItemButton = new JButton(new ImageIcon("prev.png"));
		lastItemButton = new JButton(new ImageIcon("last.png"));
		
		buttonPanel.add(firstItemButton);
		buttonPanel.add(prevItemButton);
		buttonPanel.add(editItemButton);
		buttonPanel.add(nextItemButton);
		buttonPanel.add(lastItemButton);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(buttonPanel);
		
		add(bottomPanel, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		listMenu = new JMenu("Display");
		firstItem = new JMenuItem("Edit");
		editItem = new JMenuItem("Edit");
		prevItem = new JMenuItem("Previous");
		nextItem = new JMenuItem("Next");
		lastItem = new JMenuItem("Edit");
		
		listMenu.add(editItem);
		listMenu.addSeparator();
		listMenu.add(firstItem);
		listMenu.add(prevItem);
		listMenu.add(nextItem);
		listMenu.add(lastItem);
		
		menuBar.add(listMenu);
		
		closeMenu = new JMenu("Exit");
		closeApp = new JMenuItem("Close");
		
		closeMenu.add(closeApp);
		
		menuBar.add(closeMenu);
		
		displayDetails(currentItem);
		
		// Because each pair of corresponding buttons and menu items have the same functionality, instead
		// of repeating the same code in two locations, we can define an ActionListener object that both
		// components will share by having it added as their action listener.
		
		ActionListener first = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (editItemButton.getText().equals("Save") ) {
					// Here we make sure that any updated values are saved to the record before
					// we display the next record.
					// This behaviour is performed by next, prev and edit, so we move it into a
					// separate method so as to avoid unnecessary repetition of code.
					saveOpenValues();					
				}
				currentItem = 0;
				displayDetails(currentItem);				
			}
		};
		
		ActionListener next = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// No next if at end of list
				if (currentItem != (houseList.size() - 1)) {
					if (editItemButton.getText().equals("Save") ) {
						// Here we make sure that any updated values are saved to the record before
						// we display the next record.
						// This behaviour is performed by next, prev and edit, so we move it into a
						// separate method so as to avoid unnecessary repetition of code.
						saveOpenValues();					
					}
					currentItem++;
					displayDetails(currentItem);
				}
			}
		};

		ActionListener prev = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// No previous if at beginning of list
				if (currentItem != 0) {
					if (editItemButton.getText().equals("Save") ) {
						saveOpenValues();					
					}
					currentItem--;
					displayDetails(currentItem);
				}
			}
		};

		ActionListener last = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (editItemButton.getText().equals("Save") ) {
					// Here we make sure that any updated values are saved to the record before
					// we display the next record.
					// This behaviour is performed by next, prev and edit, so we move it into a
					// separate method so as to avoid unnecessary repetition of code.
					saveOpenValues();					
				}
				currentItem = houseList.size() - 1;
				displayDetails(currentItem);								
			}
		};
		
		ActionListener edit = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Edit") {
					// Allow data to be edited
					editItemButton.setText("Save");
					priceTextField.setEditable(true);
				}
				else if (e.getActionCommand() == "Save") {
					saveOpenValues();
				}
			}
		};
		
		firstItemButton.addActionListener(first);
		firstItem.addActionListener(first);
		
		nextItemButton.addActionListener(next);
		nextItem.addActionListener(next);
		
		prevItemButton.addActionListener(prev);
		prevItem.addActionListener(prev);
		
		lastItemButton.addActionListener(last);
		lastItem.addActionListener(last);
		
		editItemButton.addActionListener(edit);
		editItem.addActionListener(edit);

		closeApp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});	
	}

	private void saveOpenValues() {
		// Save data and revert to other state.
		// Update appearance of button.
		editItemButton.setText("Edit");
		// Try to save items to record.
		try {
			double oldPrice = houseList.get(currentItem).getPrice();
			double newPrice = Double.parseDouble(priceTextField.getText());
			double change = newPrice - oldPrice;
			houseList.get(currentItem).setPrice(newPrice);
			houseList.get(currentItem).setChange(change);
			NumberFormat nf = NumberFormat.getCurrencyInstance();
			priceTextField.setText(nf.format(newPrice));
			changeTextField.setText(nf.format(change));
			if (change > 0.0) 
				changeTextField.setForeground(Color.GREEN);
			else if (change < 0.0)
				changeTextField.setForeground(Color.RED);
			else
				changeTextField.setForeground(Color.BLACK);
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Not a valid value for price");
			// Reset contents of text field.
			priceTextField.setText(houseList.get(currentItem).getPrice() + "");
		}
		// Disable text fields.
		priceTextField.setEditable(false);
		// Display message.
		JOptionPane.showMessageDialog(this, "Record updated");		
	}
	
	public void displayDetails(int currentItem) {
		houseImageLabel.setIcon(new ImageIcon(houseList.get(currentItem).getImageLocation()));
		idTextField.setText(houseList.get(currentItem).getId() + "");
		streetTextField.setText(houseList.get(currentItem).getStreet());
		cityTextField.setText(houseList.get(currentItem).getCity());
		bedroomsTextField.setText(houseList.get(currentItem).getBedrooms() + "");
		bathroomsTextField.setText(houseList.get(currentItem).getBathrooms() + "");
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		priceTextField.setText(nf.format(houseList.get(currentItem).getPrice()));
		double change = houseList.get(currentItem).getChange();
		changeTextField.setText(nf.format(change));
		if (change > 0.0) 
			changeTextField.setForeground(Color.GREEN);
		else if (change < 0.0)
			changeTextField.setForeground(Color.RED);
		else
			changeTextField.setForeground(Color.BLACK);
		contactNoTextField.setText(houseList.get(currentItem).getContactNo());
	}

	
	public static void main(String[] args) {
		HouseApplication ha = new HouseApplication();
		ha.pack();
		ha.setSize(400, 550);
		ha.setVisible(true);
	}

}