package ind.leona.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import gnu.io.SerialPort;
import ind.leona.ui.LicencePanel;
import ind.leona.ui.SdPanel;
import ind.leona.ui.VideoPanel;

public class Global {
	public static Timer GT=new Timer();
	public static String[] CHIPS = new String[] { "7687" };
	public static LicencePanel LicencePanel;
	public static SdPanel SdPanel;
	public static VideoPanel VideoPanel;
	public static Map<String,SerialPort> PortMap= new HashMap<String,SerialPort>();
}
