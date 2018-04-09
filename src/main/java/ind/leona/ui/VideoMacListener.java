package ind.leona.ui;

import java.awt.EventQueue;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import ind.leona.progress.Sd;
import ind.leona.progress.Video;
import ind.leona.utils.CommUtils;
import ind.leona.utils.Global;
import ind.leona.utils.ShowUtils;

public class VideoMacListener implements DocumentListener {

	public void insertUpdate(DocumentEvent e) {
		final DocumentEvent ee = e;
		try {
			String mac = e.getDocument().getText(0, e.getOffset() + e.getLength());
			if (mac.length() == 12) { // 二维码数据
				if (!CommUtils.isMac(mac)) {
					ShowUtils.errorMessage("MAC不正确");
				} else {
					Global.VideoPanel.ta.setText("");
					Global.VideoPanel.ta.append("当前MAC:" + mac + System.lineSeparator());

					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								Video.deal();
								Global.VideoPanel.tfMac.grabFocus();
								ee.getDocument().remove(0, ee.getOffset() + ee.getLength());
							} catch (Throwable e) {
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

	public void removeUpdate(DocumentEvent e) {
		System.out.println(3);
	}

	public void changedUpdate(DocumentEvent e) {
		System.out.println(2);
	}

}
