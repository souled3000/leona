package ind.leona.progress;

import java.awt.Dimension;
import java.io.IOException;

/**
 * 文件名：javavcCameraTest.java
 * 描述：调用windows平台的摄像头窗口视频
 * 修改时间：2016年6月13日
 * 修改内容：
 */

import javax.swing.JFrame;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 调用本地摄像头窗口视频
 * 
 * @author eguid
 * @version 2016年6月13日
 * @see javavcCameraTest
 * @since javacv1.2
 */

public class Vedio {
	private static final Logger log = LoggerFactory.getLogger(Vedio.class);

	public static void main2() throws Exception, InterruptedException {
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
		grabber.setFormat("MJPG");
		grabber.setImageHeight(720);
		grabber.setImageWidth(1280);
		grabber.start(); // 开始获取摄像头数据

		JFrame j = new JFrame("");
		CanvasFrame canvas = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());// 新建一个窗口

		// canvas.setCanvasSize(1280, 720);

		log.info("Format: " + grabber.getFormat());

		// canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// canvas.setAlwaysOnTop(true);

		while (true) {
			if (!canvas.isDisplayable()) {// 窗口是否关闭
				grabber.stop();// 停止抓取
				System.exit(2);// 退出
			}
			canvas.showImage(grabber.grab());// 获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像

			Thread.sleep(50);// 50毫秒刷新一次图像
		}

	}

	// public static OpenCVFrameGrabber grabber;
	// public static int dn = 0;
	public static void play() throws Throwable {
		OpenCVFrameGrabber grabber = OpenCVFrameGrabber.createDefault(1);
		// OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);
		grabber.setFormat("MJPG");
		grabber.setImageHeight(720);
		grabber.setImageWidth(1280);
		grabber.start(); // 开始获取摄像头数据
		CanvasFrame canvas = new CanvasFrame("", CanvasFrame.getDefaultGamma() / grabber.getGamma());// 新建一个窗口

		canvas.setCanvasSize(680, 480);

		log.info("Format: " + grabber.getFormat());

		canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.setAlwaysOnTop(true);
		Dimension scr = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		canvas.setLocation(scr.width / 2 - 50, 0);
		int n = 30;
		while (n > 0) {
			n--;
			if (!canvas.isDisplayable()) {// 窗口是否关闭
				// grabber.stop();// 停止抓取
			}
			canvas.showImage(grabber.grab());// 获取摄像头图像并放到窗口上显示， 这里的Frame frame=grabber.grab(); frame是一帧视频图像

			Thread.sleep(50);// 50毫秒刷新一次图像
		}
		grabber.stop();
		grabber.close();
		canvas.dispose();
		// grabber = null;
		log.info("FINISHED");
	}

	public static void main(String[] args) throws Throwable {
		play();
	}
}
