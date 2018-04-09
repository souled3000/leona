package ind.lchj.leona.ui;

import ind.leona.utils.ByteUtils;

public class ShiShiSan {

	public static void main(String[] args) {
		byte[] ss = ByteUtils.hexStr2Byte("1013");
		String s = new String(ss);
		s="离开教室dlfjaklsdjf"+s;
		System.out.println(s.contains(s));
		System.out.println(s);

	}

}
