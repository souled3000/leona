package ind.leona.progress;

import java.awt.TextArea;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import ind.leona.utils.ByteUtils;
import ind.leona.utils.CommUtils;
import ind.leona.utils.Global;
import ind.leona.utils.SmartDevice;

public class Video {
	private static final Logger log = LoggerFactory.getLogger(Video.class);

	public static SerialPort sp;

	public static void deal() throws Throwable {

		String mac = Global.VideoPanel.tfMac.getText();
		int baud = 115200;
		TextArea ta = Global.VideoPanel.ta;

		String portName = (String) Global.VideoPanel.cbPort.getSelectedItem();
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
		log.info("Video SERIAL BaudRate:" + sp.getBaudRate());

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
						ta.append("进入视频测试模式" + System.lineSeparator());
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
			// ta.append("SEND:" + ByteUtils.byteArrayToHexString(req) + System.lineSeparator());

			/** pc <- dev cmd:ac data:01 01 */
			byte[] recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			// ta.append("RECV:" + ByteUtils.byteArrayToHexString(recv) + System.lineSeparator());

			SmartDevice input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				/** pc -> dev cmd:01 data:03 10 */
				sdReq.opcode = 0x01;
				sdReq.data = new byte[] { 0x03, 0x10 };
				req = sdReq.buildData();
				sp.getOutputStream().write(req);
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				// ta.append("SEND:" + ByteUtils.byteArrayToHexString(req) + System.lineSeparator());

			} else {
				log.info("FAILD!! input.opcode:" + String.format("%x", input.opcode));
				ta.append("失败" + System.lineSeparator());
				return;
			}

			/** pc <- dev cmd:ac data:01 03 */
			recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			// ta.append("RECV:" + ByteUtils.byteArrayToHexString(recv) + System.lineSeparator());

			input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {

				// CommUtils.play();
				//				Vedio.play();
				
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Vedio.play();
						}catch(Throwable t ) {
						}
					}
				}).start();

				Thread.sleep(20*1000);
				/** pc -> dev cmd:01 data:03 11 */
				sdReq.data = new byte[] { 0x03, 0x11 };
				sdReq.opcode = (byte) 0x01;
				req = sdReq.buildData();
				sp.getOutputStream().write(req);
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				// ta.append("SEND:" + ByteUtils.byteArrayToHexString(req) + System.lineSeparator());
			} else {
				log.info("FAILD!! input.opcode:" + String.format("%x", input.opcode));
				ta.append("失败" + System.lineSeparator());
				return;
			}

			/** pc <- dev cmd:ac data:01 02 */
			recv = CommUtils.read6D64(sp.getInputStream());
			log.info("RECV:" + ByteUtils.byteArrayToHexString(recv));
			// ta.append("RECV:" + ByteUtils.byteArrayToHexString(recv) + System.lineSeparator());

			input = new SmartDevice();
			input.parseData(recv);
			if (input.opcode == (byte) 0xAC) {
				/** pc -> dev cmd:01 data:01 11 */
				sdReq.data = new byte[] { 0x01, 0x11 };
				sdReq.opcode = (byte) 0x01;
				req = sdReq.buildData();
				sp.getOutputStream().write(req);
				log.info("SEND:" + ByteUtils.byteArrayToHexString(req));
				log.info("OK");
				ta.append("完毕" + System.lineSeparator());
			} else {
				log.info("FAILD!! input.opcode:" + String.format("%x", input.opcode));
				ta.append("失败" + System.lineSeparator());
			}

			return;
		} catch (Exception e) {
			// System.err.println(e.getMessage());
		} finally {
			sp.close();
			Global.PortMap.remove(portName);
		}
	}
}
