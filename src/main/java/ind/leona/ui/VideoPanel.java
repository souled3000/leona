package ind.leona.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VideoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JComboBox<String> cbPort;
	public JTextField tfMac;
	public JComboBox<String> cbChip;
	public TextArea ta;

	public VideoPanel() throws Exception {
		super();
		JLabel portLabel = new JLabel("Serial Port:");
		cbPort = new JComboBox<String>();
		cbPort.setPreferredSize(new Dimension(80, 0));
		cbPort.addFocusListener(new CbPortFocusListener());

		JLabel macLabel = new JLabel("MAC:");
		tfMac = new JTextField();
		tfMac.setColumns(20);
		tfMac.getDocument().addDocumentListener(new VideoMacListener());

		JLabel chipLabel = new JLabel("Chip:");
		cbChip = new JComboBox<String>(new String[] {"32930"});
		cbChip.setPreferredSize(new Dimension(80, 0));
		ta = new TextArea();
		ta.setFont(new Font("Times New Roman",Font.PLAIN,20));
//		ta.setEditable(false);
		
		this.setLayout(new GridBagLayout());

		GridBagConstraints plgbc = new GridBagConstraints();
		plgbc.weightx = 0;
		plgbc.weighty = 0;
		plgbc.insets = new Insets(5, 10, 5, 10);
		plgbc.fill = GridBagConstraints.BOTH;
		plgbc.gridx = 0;
		plgbc.gridy = 0;
		this.add(portLabel, plgbc);

		GridBagConstraints pcgbc = (GridBagConstraints) plgbc.clone();
		pcgbc.gridx = 1;
		this.add(cbPort, pcgbc);

		GridBagConstraints clgbc = (GridBagConstraints) plgbc.clone();
		clgbc.gridx = 2;
		this.add(chipLabel, clgbc);

		GridBagConstraints ccgbc = (GridBagConstraints) plgbc.clone();
		ccgbc.gridx = 3;
		this.add(cbChip, ccgbc);

		GridBagConstraints mlgbc = (GridBagConstraints) plgbc.clone();
		mlgbc.gridx = 4;
		this.add(macLabel, mlgbc);

		GridBagConstraints mtgbc = (GridBagConstraints) plgbc.clone();
		mtgbc.gridx = 5;
		this.add(tfMac, mtgbc);

		// GridBagConstraints obc = (GridBagConstraints) plgbc.clone();
		// obc.gridx = 6;
		// this.add(new JButton("GO"), obc);

		GridBagConstraints lbc = (GridBagConstraints) plgbc.clone();
		lbc.gridx = 7;
		this.add(new JLabel(), lbc);

		GridBagConstraints tagbc = new GridBagConstraints();
		tagbc.gridx = 0;
		tagbc.gridy = 1;
		tagbc.fill = GridBagConstraints.BOTH;
		tagbc.gridwidth = 8;
		tagbc.weightx = 1;
		tagbc.weighty = 1;
		this.add(ta, tagbc);
	}
}
