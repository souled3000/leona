package ind.lchj.leona.ui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import gnu.io.CommPortIdentifier;

public class MainWindow {

	public static void main(String[] args) throws Exception {
		// f5();
		fa();
		// ls();
	}

	public static void fa() {
		JFrame f = new JFrame("MW");
		f.setSize(300, 300);
		final JTextField tf = new JTextField();
		f.add(tf);
		tf.getDocument().addDocumentListener(new DocumentListener() {

			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}

			public void insertUpdate(DocumentEvent e) {
				try {
					final String mac = e.getDocument().getText(e.getOffset(), e.getLength());
					// e.getDocument().remove(0, e.getOffset());
					System.out.println(mac + "  " + e.getOffset());
					final DocumentEvent ee = e;
					new Thread(new Runnable() {
						public void run() {
							try {
								 ee.getDocument().remove(0, ee.getOffset());
//								tf.setText(mac);
								System.out.println(1);
							} catch (Exception e) {

							}
						}

					}).start();
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		f.setVisible(true);

	}

	public static void f() {
		JFrame f = new JFrame("MW");
		JFrame f2 = new JFrame("MW2");
		JTextArea j1 = new JTextArea();
		f.add(j1);
		f2.add(j1);
		f.setSize(300, 300);
		f2.setSize(300, 300);
		f.setVisible(true);
		f2.setVisible(true);
	}

	public static void f2() {
		GridBagLayout gridbag = new GridBagLayout();
		JFrame f = new JFrame();
		f.setLayout(gridbag);

		// JButton jbutton = new JButton("BUTTON");
		// gridbag.setConstraints(jbutton, c);
		// f.add(jbutton);

		TextArea ta = new TextArea();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		// c.anchor= GridBagConstraints.NORTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		f.add(ta, c);

		GridBagConstraints cc = (GridBagConstraints) c.clone();
		cc.gridx = 0;
		cc.gridy = 1;
		cc.gridwidth = 1;
		cc.gridheight = 1;
		JButton b = new JButton("BUT");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("but");
			}
		});
		f.add(b, cc);

		GridBagConstraints ccc = (GridBagConstraints) c.clone();
		ccc.gridx = 1;
		ccc.gridy = 1;
		ccc.gridwidth = 1;
		ccc.gridheight = 1;
		JButton b1 = new JButton("BUT2");
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("but2");
			}
		});
		f.add(b1, ccc);

		f.setSize(300, 300);
		f.setVisible(true);
	}

	public static void f4() {
		GridBagLayout gridbag = new GridBagLayout();
		JFrame f = new JFrame();
		f.setLayout(gridbag);

		// JButton jbutton = new JButton("BUTTON");
		// gridbag.setConstraints(jbutton, c);
		// f.add(jbutton);

		TextArea ta = new TextArea();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		// c.anchor= GridBagConstraints.NORTH;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		f.add(ta, c);

		TextArea ta2 = new TextArea();
		GridBagConstraints cccc = new GridBagConstraints();
		// c.anchor= GridBagConstraints.NORTH;
		cccc.fill = GridBagConstraints.BOTH;
		cccc.weightx = 1.0;
		cccc.weighty = 1.0;
		cccc.gridx = 1;
		cccc.gridy = 0;
		cccc.gridwidth = 1;
		cccc.gridheight = 1;
		f.add(ta2, cccc);

		GridBagConstraints cc = (GridBagConstraints) c.clone();
		cc.gridx = 0;
		cc.gridy = 1;
		cc.gridwidth = 1;
		cc.gridheight = 2;
		// cc.insets = new Insets(0, 0, 0, 0);
		// cc.fill = GridBagConstraints.HORIZONTAL;
		cc.weightx = 0.1;
		cc.weighty = 0.1;
		JButton b = new JButton("BUT");
		b.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("but");
			}
		});
		f.add(b, cc);

		GridBagConstraints ccc = (GridBagConstraints) c.clone();
		ccc.gridx = 1;
		ccc.gridy = 1;
		ccc.gridwidth = 1;
		ccc.gridheight = 2;
		// ccc.insets = new Insets(10, 10, 10, 10);
		// ccc.fill = GridBagConstraints.HORIZONTAL;
		ccc.weightx = 0.1;
		ccc.weighty = 0.1;
		JButton b1 = new JButton("BUT2");

		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("but2");
			}
		});
		f.add(b1, ccc);

		f.setSize(700, 500);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setVisible(true);
	}

	public static void f3() {
		JFrame frame = new JFrame("MW");
		BorderLayout bl = new BorderLayout();
		frame.setLayout(bl);
		TextArea ta = new TextArea();
		ta.setRows(50);
		bl.addLayoutComponent(ta, BorderLayout.NORTH);
		JButton b = new JButton("But");
		bl.addLayoutComponent(b, BorderLayout.SOUTH);
		frame.add(ta);
		frame.add(b);
		frame.setSize(300, 500);
		frame.setVisible(true);
	}

	public static void tabbed() throws Exception {
		JFrame main = new JFrame("VISION UTITITY");
		JTabbedPane jtp = new JTabbedPane();
		jtp.addTab("Licence", gen());
		jtp.addTab("SD", gen());
		jtp.addTab("VIDEO", gen());
		main.add(jtp);
		main.setSize(900, 500);
		main.setVisible(true);
		main.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	public static void ls() throws Exception {
		// 获得当前所有可用串口
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		// 将可用串口名添加到List并返回该List
		while (portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();
			System.out.println(portName);
		}
	}

	public static void f5() throws Exception {
		JLabel portLabel = new JLabel("Serial Port:");
		JComboBox portCb = new JComboBox(new String[] { "", "COM1" });
		JLabel macLabel = new JLabel("MAC:");
		JTextField macTxt = new JTextField();
		macTxt.setColumns(20);

		JLabel chipLabel = new JLabel("Chip:");
		JComboBox chipCb = new JComboBox(new String[] { "", "7687" });
		TextArea ta = new TextArea();

		JFrame f = new JFrame();
		f.setLayout(new GridBagLayout());

		GridBagConstraints plgbc = new GridBagConstraints();
		plgbc.weightx = 0;
		plgbc.weighty = 0;
		plgbc.insets = new Insets(0, 10, 0, 10);
		plgbc.fill = GridBagConstraints.BOTH;
		plgbc.gridx = 0;
		plgbc.gridy = 0;
		f.add(portLabel, plgbc);

		GridBagConstraints pcgbc = (GridBagConstraints) plgbc.clone();
		pcgbc.gridx = 1;
		f.add(portCb, pcgbc);

		GridBagConstraints mlgbc = (GridBagConstraints) plgbc.clone();
		mlgbc.gridx = 2;
		f.add(macLabel, mlgbc);

		GridBagConstraints mtgbc = (GridBagConstraints) plgbc.clone();
		mtgbc.gridx = 3;
		f.add(macTxt, mtgbc);

		GridBagConstraints clgbc = (GridBagConstraints) plgbc.clone();
		clgbc.gridx = 4;
		f.add(chipLabel, clgbc);

		GridBagConstraints ccgbc = (GridBagConstraints) plgbc.clone();
		ccgbc.gridx = 5;
		f.add(chipCb, ccgbc);

		GridBagConstraints obc = (GridBagConstraints) plgbc.clone();
		obc.gridx = 6;
		f.add(new JLabel(), obc);

		GridBagConstraints tagbc = new GridBagConstraints();
		tagbc.gridx = 0;
		tagbc.gridy = 1;
		tagbc.fill = GridBagConstraints.BOTH;
		tagbc.gridwidth = 7;
		tagbc.weightx = 1;
		tagbc.weighty = 1;
		f.add(ta, tagbc);
		macTxt.grabFocus();

		f.setSize(700, 500);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setVisible(true);
		// macTxt.requestFocus();

	}

	public static JPanel gen() throws Exception {
		JLabel portLabel = new JLabel("Serial Port:");
		JComboBox portCb = new JComboBox(new String[] { "", "COM1" });
		JLabel macLabel = new JLabel("MAC:");
		JTextField macTxt = new JTextField();
		macTxt.setColumns(20);

		JLabel chipLabel = new JLabel("Chip:");
		JComboBox chipCb = new JComboBox(new String[] { "", "7687" });
		TextArea ta = new TextArea();

		JPanel f = new JPanel();
		f.setLayout(new GridBagLayout());

		GridBagConstraints plgbc = new GridBagConstraints();
		plgbc.weightx = 0;
		plgbc.weighty = 0;
		plgbc.insets = new Insets(5, 10, 5, 10);
		plgbc.fill = GridBagConstraints.BOTH;
		plgbc.gridx = 0;
		plgbc.gridy = 0;
		f.add(portLabel, plgbc);

		GridBagConstraints pcgbc = (GridBagConstraints) plgbc.clone();
		pcgbc.gridx = 1;
		f.add(portCb, pcgbc);

		GridBagConstraints mlgbc = (GridBagConstraints) plgbc.clone();
		mlgbc.gridx = 2;
		f.add(macLabel, mlgbc);

		GridBagConstraints mtgbc = (GridBagConstraints) plgbc.clone();
		mtgbc.gridx = 3;
		f.add(macTxt, mtgbc);

		GridBagConstraints clgbc = (GridBagConstraints) plgbc.clone();
		clgbc.gridx = 4;
		f.add(chipLabel, clgbc);

		GridBagConstraints ccgbc = (GridBagConstraints) plgbc.clone();
		ccgbc.gridx = 5;
		f.add(chipCb, ccgbc);

		GridBagConstraints obc = (GridBagConstraints) plgbc.clone();
		obc.gridx = 6;
		f.add(new JButton("GO"), obc);

		GridBagConstraints lbc = (GridBagConstraints) plgbc.clone();
		lbc.gridx = 7;
		f.add(new JLabel(), lbc);

		GridBagConstraints tagbc = new GridBagConstraints();
		tagbc.gridx = 0;
		tagbc.gridy = 1;
		tagbc.fill = GridBagConstraints.BOTH;
		tagbc.gridwidth = 8;
		tagbc.weightx = 1;
		tagbc.weighty = 1;
		f.add(ta, tagbc);
		macTxt.grabFocus();
		return f;
	}
}
