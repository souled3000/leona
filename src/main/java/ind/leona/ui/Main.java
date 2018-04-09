package ind.leona.ui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import gnu.io.SerialPort;
import ind.leona.utils.ByteUtils;
import ind.leona.utils.Global;

public class Main {
	public static void main(String[] args) throws Exception {
		JFrame main = new JFrame("VISION UTITITY");
		JTabbedPane jtp = new JTabbedPane();

		Global.LicencePanel = new LicencePanel();
		Global.SdPanel = new SdPanel();
		Global.VideoPanel = new VideoPanel();

		jtp.addTab("Licence", Global.LicencePanel);
		jtp.addTab("SD", Global.SdPanel);
		jtp.addTab("VIDEO", Global.VideoPanel);

		main.add(jtp);
		main.setSize(900, 500);
		main.setVisible(true);
		main.setExtendedState(JFrame.MAXIMIZED_BOTH);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				for(SerialPort sp:Global.PortMap.values()) {
					sp.close();
				}
				System.exit(0);
			}
		});
	}

	public static void mainx(String[] args) throws Exception {
		String s = new String(ByteUtils.hexStr2Byte("75706461746120746865206C6963656E63652121210D0A"));
		System.out.println(s);
		s = new String(ByteUtils.hexStr2Byte("427573696E657373206F7065726174696F6E3A0D0A"));
		System.out.println(s);

		JFrame j = new JFrame();
		j.setSize(500, 200);
		final JTextField t = new JTextField();
		j.getContentPane().add(t);
		t.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				System.out.println(1);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				final DocumentEvent ee = e;
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (t.getText().length() == 12) {
							System.out.println("DEALING..");
							try {
								ee.getDocument().remove(0, ee.getOffset() + ee.getLength());
							} catch (BadLocationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				System.out.println(3);
			}
		});
		j.setVisible(true);

	}

}
