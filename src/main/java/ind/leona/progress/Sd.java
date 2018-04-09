package ind.leona.progress;

import java.awt.TextArea;
import java.io.IOException;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import ind.leona.utils.ByteUtils;
import ind.leona.utils.CommUtils;
import ind.leona.utils.Global;
import ind.leona.utils.SmartDevice;

public class Sd {
	private static final Logger log = LoggerFactory.getLogger(Licence.class);
	public static SerialPort sp;

	public static void deal() throws Exception {
		String mac = Global.SdPanel.tfMac.getText();
		int baud = 115200;
		final TextArea ta = Global.SdPanel.ta;

		final String portName = (String) Global.SdPanel.cbPort.getSelectedItem();
		SerialPort tp = Global.PortMap.get(portName);
		if (tp != null) {
			tp.close();
		}

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			log.info("Error: Port is currently in use");
			ta.append("Error: Port is currently in use." + System.lineSeparator());
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

		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			String s = null;
			StringBuffer sb = new StringBuffer();
			while ((len = sp.getInputStream().read(buffer)) > -1) {
				if (len > 0) {
					s = new String(buffer, 0, len);
					System.out.print(s);
					sb.append(s);
					if (sb.toString().contains("7687 START")) {
						ta.append("进入SD检测模式" + System.lineSeparator());
						break;
					}

				}
				Thread.sleep(10);
			}
		} catch (IOException e) {
			return;
		}

		try {
			log.info("BEGIN");
			SmartDevice sdReq = new SmartDevice();
			sdReq.data = new byte[] { 0x06, 0x10 };
			sdReq.opcode = (byte) 0x01;
			byte[] req = sdReq.buildData();
			sp.getOutputStream().write(req);
			log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
			// ta.append("SEND:" + ByteUtils.byteArrayToHexString(req) + System.lineSeparator());

			byte[] recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			// ta.append("RECV:" + ByteUtils.byteArrayToHexString(recv) + System.lineSeparator());

			SmartDevice input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				sdReq.opcode = 0x01;
				sdReq.data = new byte[] { 0x05, 0x10 };
				req = sdReq.buildData();
				sp.getOutputStream().write(req);
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				// ta.append("SEND:" + ByteUtils.byteArrayToHexString(req) + System.lineSeparator());

			} else {
				log.info("FAILD! input.opcode:" + String.format("%x", input.opcode));
				ta.append("失败!" + System.lineSeparator());
				return;
			}

			recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			// ta.append("RECV:" + ByteUtils.byteArrayToHexString(recv) + System.lineSeparator());
			input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				sdReq.data = new byte[] { 0x06, 0x11 };
				sdReq.opcode = (byte) 0x01;
				req = sdReq.buildData();
				sp.getOutputStream().write(req);
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				// ta.append("SEND:" + ByteUtils.byteArrayToHexString(req) + System.lineSeparator());
				String rt = "";
				switch (input.data[2]) {
				case 0:
					rt = "没插SD";
					break;
				case 1:
					rt = "不能打开SD";
					break;
				case 2:
					rt = "不能写";
					break;
				case 3:
					rt = "不能读";
					break;
				case 4:
					rt = "读错误";
					break;
				case 5:
					rt = "成功";
					break;
				default:

				}
				ta.append("结果:" + rt + System.lineSeparator());
				log.info("END.");
				return;
			} else {
				log.info("FAILD! input.opcode:" + String.format("%x", input.opcode));
				ta.append("失败!" + System.lineSeparator());
				return;
			}
		} catch (Exception e) {
			// System.err.println(e.getMessage());
		} finally {
			sp.close();
			Global.PortMap.remove(portName);
		}
	}

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

		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			String s = null;
			StringBuffer sb = new StringBuffer();
			while ((len = sp.getInputStream().read(buffer)) > -1) {
				if (len > 0) {
					s = new String(buffer, 0, len);
					System.out.print(s);
					sb.append(s);
					if (sb.toString().contains("7687 START")) {
						break;
					}

				}
				Thread.sleep(10);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			log.info("BEGIN");
			SmartDevice sdReq = new SmartDevice();
			sdReq.data = new byte[] { 0x06, 0x10 };
			sdReq.opcode = (byte) 0x01;
			byte[] req = sdReq.buildData();
			sp.getOutputStream().write(req);
			log.info("SEND:" + ByteUtils.byteArrayToHexString(req));

			byte[] recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));

			SmartDevice input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				sdReq.opcode = 0x01;
				sdReq.data = new byte[] { 0x05, 0x10 };
				req = sdReq.buildData();
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				sp.getOutputStream().write(req);
			} else {
				log.info("FAILD! input.opcode:" + String.format("%x", input.opcode));
				return;
			}

			recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				sdReq.data = new byte[] { 0x06, 0x11 };
				sdReq.opcode = (byte) 0x01;
				req = sdReq.buildData();
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				sp.getOutputStream().write(req);
				log.info("END.");
			} else {
				log.info("FAILD! input.opcode:" + String.format("%x", input.opcode));
			}
		} catch (Exception e) {
			// System.err.println(e.getMessage());
		} finally {
			sp.close();
		}
	}
}
