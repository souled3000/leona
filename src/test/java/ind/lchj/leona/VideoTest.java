package ind.lchj.leona;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import ind.leona.progress.Vedio;
import ind.leona.progress.Video;
import ind.leona.utils.ByteUtils;
import ind.leona.utils.CommUtils;
import ind.leona.utils.SmartDevice;

public class VideoTest {
	private static final Logger log = LoggerFactory.getLogger(VideoTest.class);
	public static SerialPort sp;

	public static void w2() throws Exception {
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
		byte[] buf = new byte[10];
		for (;;) {
			try {
				int n = sp.getInputStream().read(buf);
				if (n > 0)
					log.info("Readed {} bytes", n);

			} catch (Exception e) {
				System.err.println(e.getMessage());
			} finally {
				sp.close();
			}
		}
	}

	public static void main(String[] args) throws Throwable {
		wl7687();
	}

	public static void x() throws Throwable {
		String shpath = "cmd /c start C:\\Users\\juliana\\Desktop\\leona-x86\\a.bat";
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

	public static void wl7687() throws Throwable {
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
					if (sb.toString().contains("start 32903")) {
						break;
					}

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			log.info("BEGIN");

			/** pc -> dev cmd:01 data:01 10 */
			SmartDevice sdReq = new SmartDevice();
			sdReq.data = new byte[] { 0x01, 0x10 };
			sdReq.opcode = (byte) 0x01;
			byte[] req = sdReq.buildData();
			sp.getOutputStream().write(req);
			log.info("SEND:" + ByteUtils.byteArrayToHexString(req));

			/** pc <- dev cmd:ac data:01 01 */
			byte[] recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			SmartDevice input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				/** pc -> dev cmd:01 data:03 10 */
				sdReq.opcode = 0x01;
				sdReq.data = new byte[] { 0x03, 0x10 };
				req = sdReq.buildData();
				sp.getOutputStream().write(req);
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
			} else {
				log.info("FAILD!! input.opcode:" + String.format("%x", input.opcode));
				return;
			}

			/** pc <- dev cmd:ac data:01 02 */
			recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {

//				Vedio.play();

				Thread.sleep(1000000);
				/** pc -> dev cmd:01 data:03 11 */
				sdReq.data = new byte[] { 0x03, 0x11 };
				sdReq.opcode = (byte) 0x01;
				req = sdReq.buildData();
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				sp.getOutputStream().write(req);
			} else {
				log.info("FAILD!! input.opcode:" + String.format("%x", input.opcode));
			}

			/** pc <- dev cmd:ac data:01 02 */
			recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				/** pc -> dev cmd:01 data:01 11 */
				sdReq.data = new byte[] { 0x01, 0x11 };
				sdReq.opcode = (byte) 0x01;
				req = sdReq.buildData();
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				sp.getOutputStream().write(req);
				log.info("END");
			} else {
				log.info("FAILD!! input.opcode:" + String.format("%x", input.opcode));
			}
			return;
		} catch (Exception e) {
			 System.err.println(e.getMessage());
		} finally {
			sp.close();
			System.exit(0);
		}
	}
}
