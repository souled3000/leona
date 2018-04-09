package ind.leona.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import ind.leona.utils.CommUtils;

public class CbPortFocusListener implements FocusListener {

	public void focusLost(FocusEvent e) {

	}

	public void focusGained(FocusEvent e) {
		JComboBox<String> o = (JComboBox<String>) e.getSource();
		int n = o.getItemCount();
		String bigString = "";
		List<String> listString = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			bigString += (String) o.getItemAt(i);
			listString.add(o.getItemAt(i));
		}
		List<String> ports = CommUtils.ports();

		if (ports.size() != listString.size()) {
			CommUtils.synCombo(o, ports);
			return;
		}
		for (String s : ports) {
			if (s.indexOf(bigString) < 0) {
				CommUtils.synCombo(o, ports);
				return;
			}
		}
		for (String s : listString) {
			if (!ports.contains(s)) {
				CommUtils.synCombo(o, ports);
				return;
			}
		}
	}
}
