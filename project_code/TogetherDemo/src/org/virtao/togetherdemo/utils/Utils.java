package org.virtao.togetherdemo.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import org.virtao.togetherdemo.graphics.BokehPoint;
import org.virtao.togetherdemo.graphics.Color;

public class Utils {
	public static String formatString(String format, Object... args) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ps.format(format, args);
		return baos.toString();
	}

	private static Random random = new Random();

	public static BokehPoint randomBokeh() {
		BokehPoint ret = new BokehPoint(random.nextInt(1600) - 200,
				random.nextInt(1600) - 200, random.nextInt(1600) - 200,
				random.nextInt(1600) - 200, random.nextInt(9000) + 1000, 50 * random.nextFloat(), new Color(
						random.nextInt(256), random.nextInt(256),
						random.nextInt(256), random.nextInt(256)), new Color(
						random.nextInt(256), random.nextInt(256),
						random.nextInt(256), random.nextInt(256)));
		return ret;
	}
}
