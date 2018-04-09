package ind.leona.progress;

import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import ind.leona.utils.ByteUtils;
import ind.leona.utils.CommUtils;
import ind.leona.utils.Global;
import ind.leona.utils.XModem;

public class Licence {
	private static final Logger log = LoggerFactory.getLogger(Licence.class);

	public static void main(String[] args) throws Exception {
		wl7687();
	}

	public static void wl7687() throws Exception {
		int baud = 115200;
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM4");
		if (portIdentifier.isCurrentlyOwned()) {
			log.info("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open("Peephole", 2000);

			if (commPort instanceof SerialPort) {
				sp = (SerialPort) commPort;
				sp.setSerialPortParams(baud, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			} else {
				log.info("Error: Only serial ports are handled by this example.");
			}
		}
		log.info("SERIAL BaudRate:" + sp.getBaudRate());

		BufferedReader br = new BufferedReader(new InputStreamReader(sp.getInputStream()));
		while (true) {
			try {
				String s = br.readLine();
				// log.info(s);
				if (s.equals("Business operation:")) {
					// Thread.sleep(1500);
					sp.getOutputStream().write("Licence\r".getBytes());
					log.info(ByteUtils.byteArrayToHexString("Licence\r".getBytes()));
				}
				if (s.equals("update the licence!!!")) {
					char x = 0;
					while ((x = (char) sp.getInputStream().read()) > -1) {
						if (x == 'C') {
							break;
						}
					}
					XModem xModem = new XModem(sp.getInputStream(), sp.getOutputStream(), new PrintWriter(System.err));
					// xModem.send("C:\\Users\\juliana\\Desktop\\leona-x86\\licence\\0000007E56506F92-AES.bin");
					String ss = CommUtils.buildLicensePath("007E56506F92");
					System.out.println(ss);
					xModem.send(ss);
					log.info("Finished.");
				}
				if (s.equals("Code auth fault")) {
					log.info("FAIL");
					sp.close();
					return;
				}
				if (s.equals("Code auth OK")) {
					log.info("SUCC");
					sp.close();
					return;
				}
			} catch (Exception e) {
				// System.err.println(e.getMessage());
			}
		}
	}

	public static void deal() throws Exception {
		String mac = Global.LicencePanel.tfMac.getText();
		int baud = 115200;
		TextArea ta = Global.LicencePanel.ta;

		String portName = (String) Global.LicencePanel.cbPort.getSelectedItem();
		SerialPort tp = Global.PortMap.get(portName);
		if (tp != null) {
			tp.close();
		}

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			log.info("Error: Port is currently in use");
			ta.append("Error: The port is currently in use." + System.lineSeparator());

		} else {
			CommPort commPort = portIdentifier.open("Peephole", 2000);

			if (commPort instanceof SerialPort) {
				sp = (SerialPort) commPort;
				sp.setSerialPortParams(baud, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
				Global.PortMap.put(portName, sp);
			} else {
				log.info("Error: Only serial ports are handled by this example.");
				ta.append("Error: Only serial ports are handled by this example." + System.lineSeparator());
			}
		}
		log.info("SERIAL BaudRate:" + sp.getBaudRate());

		final BufferedReader br = new BufferedReader(new InputStreamReader(sp.getInputStream()));


		


		while (true) {
			try {
				TimerTask tt = new TimerTask() {
					@Override
					public void run() {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
				Global.GT.schedule(tt, 10 * 1000);
				String s = CommUtils.readLine(br);
				tt.cancel();
				Global.GT.purge();
				
				if (s.equals("Business operation:")) {
					ta.append("进入Licence烧写模式" + System.lineSeparator());
					sp.getOutputStream().write("Licence\r".getBytes());
					log.info(ByteUtils.byteArrayToHexString("Licence\r".getBytes()));
				}
				if (s.equals("update the licence!!!")) {
					char x = 0;
					while ((x = (char) sp.getInputStream().read()) > -1) {
						if (x == 'C') {
							break;
						}
					}
					ta.append("烧写中..." + System.lineSeparator());
					XModem xModem = new XModem(sp.getInputStream(), sp.getOutputStream(), new PrintWriter(System.err));
					xModem.send(CommUtils.buildLicensePath(mac));
					log.info("Done");
				}
				if (s.equals("Code auth fault")) {
					ta.append("Licence烧写失败" + System.lineSeparator());
					log.info("FAIL");
					break;
				}
				if (s.equals("Code auth OK")) {
					ta.append("Licence烧写完成" + System.lineSeparator());
					log.info("SUCC");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		sp.close();
		Global.PortMap.remove(portName);
	}

	public static SerialPort sp;
}
