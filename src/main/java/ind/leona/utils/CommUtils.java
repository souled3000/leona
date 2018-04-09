package ind.leona.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JComboBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.io.CommPortIdentifier;

public class CommUtils {
	private static final Logger log = LoggerFactory.getLogger(CommUtils.class);

	public static void play() throws Throwable {
		String shpath = "cmd /c start .\\a.bat";
		Process ps = Runtime.getRuntime().exec(shpath);
		// ps.waitFor();
		//
		// BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
		// StringBuffer sb = new StringBuffer();
		// String line;
		// while ((line = br.readLine()) != null) {
		// sb.append(line).append("\n");
		// }
		// String result = sb.toString();
		// System.out.println(result);
	}

	public static String readLine(final BufferedReader br) throws Exception {
		for (;;) {
			try {
				return br.readLine();
			} catch (Exception e) {
				if (e.getMessage().equals("Underlying input stream returned zero bytes")) {

				} else {
					throw e;
				}
			}
		}
	}

	public static byte[] read6D64(final InputStream is) throws Exception {
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		Global.GT.schedule(tt, 10000);
		byte[] w = null;
		while (true) {
			int l = is.read();
			if (l != 0x6D) {
				continue;
			} else {
				l = is.read();
				if (l != 0x64) {
					continue;
				} else {
					l = is.read();
					byte[] r = new byte[l];
					is.read(r);
					w = new byte[l + 3];
					w[0] = 0x6d;
					w[1] = 0x64;
					w[2] = (byte) l;
					System.arraycopy(r, 0, w, 3, l);
					break;
				}
			}
		}
		tt.cancel();
		Global.GT.purge();
		return w;
	}

	public static String getMacFromCode(String str) {
		byte[] bytes = Base64Utils.decode(str);
		byte[] mac = new byte[8];
		System.arraycopy(bytes, 0, mac, 0, mac.length);
		return ByteUtils.byteArrayToHexString(mac);
	}

	public static void synCombo(JComboBox<String> box, List<String> items) {
		box.removeAllItems();
		for (String s : items) {
			box.addItem(s);
		}
	}

	public static List<String> ports() {
		// 获得当前所有可用串口
		Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
		ArrayList<String> list = new ArrayList<String>();
		// 将可用串口名添加到List并返回该List
		while (portList.hasMoreElements()) {
			String portName = portList.nextElement().getName();
			list.add(portName);
		}
		log.info("Find Out {} Serial Port", list.size());
		return list;
	}

	/**
	 * 获取License的地址路径
	 * 
	 * @param mac
	 *            MAC地址
	 * @return License的地址路径
	 */
	public static String buildLicensePath(String mac) {
		return "." + File.separator + "licence" + File.separator + "0000" + mac + "-AES.bin";
	}

	/**
	 * 文件是否存在
	 * 
	 * @param mac
	 *            MAC地址
	 * @return 文件是否存在
	 */
	public static boolean isFileExists(String mac) {
		File f = new File("./licence/", "0000" + mac + "-AES.bin");
		try {
			log.info("Licence File: {}", f.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return f.exists();
	}

	public static boolean isMac(String address) {
		return address != null && address.length() == 12;
	}

}
