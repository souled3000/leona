package ind.leona.ui;

import java.awt.EventQueue;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import ind.leona.progress.Licence;
import ind.leona.utils.CommUtils;
import ind.leona.utils.Global;
import ind.leona.utils.ShowUtils;

public class LicenceMacListener implements DocumentListener {

	public void removeUpdate(DocumentEvent e) {

	}

	public void insertUpdate(DocumentEvent e) {
		final DocumentEvent ee = e;
		try {
			String mac = e.getDocument().getText(0, e.getOffset() + e.getLength());
			if (CommUtils.isMac(mac)) {
				if (!CommUtils.isFileExists(mac)) {
					ShowUtils.errorMessage("未找到Licence文件");
				} else {
					Global.LicencePanel.ta.setText("");
					Global.LicencePanel.ta.append("当前MAC:" + mac + System.lineSeparator());
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								Global.LicencePanel.tfMac.grabFocus();
								Licence.deal();
								ee.getDocument().remove(0, ee.getOffset()+ee.getLength());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					});
				}
			}
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public void changedUpdate(DocumentEvent e) {

	}
}