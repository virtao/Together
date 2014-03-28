package org.virtao.togetherdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Utils {
	public static String formatString(String format, Object... args) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ps.format(format, args);
		return baos.toString();
	}
}
