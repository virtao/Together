package org.virtao.togetherdemo.graphics;

import org.virtao.togetherdemo.utils.Utils;

import android.util.Log;

public class Color {
	private int color;

	/**
	 * Return the alpha component of a color int. This is the same as saying
	 * color >>> 24
	 */
	public static int alpha(int color) {
		return color >>> 24;
	}

	/**
	 * Return the red component of a color int. This is the same as saying
	 * (color >> 16) & 0xFF
	 */
	public static int red(int color) {
		return (color >> 16) & 0xFF;
	}

	/**
	 * Return the green component of a color int. This is the same as saying
	 * (color >> 8) & 0xFF
	 */
	public static int green(int color) {
		return (color >> 8) & 0xFF;
	}

	/**
	 * Return the blue component of a color int. This is the same as saying
	 * color & 0xFF
	 */
	public static int blue(int color) {
		return color & 0xFF;
	}

	/**
	 * Return a color-int from red, green, blue components. The alpha component
	 * is implicity 255 (fully opaque). These component values should be
	 * [0..255], but there is no range check performed, so if they are out of
	 * range, the returned color is undefined.
	 * 
	 * @param red
	 *            Red component [0..255] of the color
	 * @param green
	 *            Green component [0..255] of the color
	 * @param blue
	 *            Blue component [0..255] of the color
	 */
	public static int rgb(int red, int green, int blue) {
		return (0xFF << 24) | (red << 16) | (green << 8) | blue;
	}

	/**
	 * Return a color-int from alpha, red, green, blue components. These
	 * component values should be [0..255], but there is no range check
	 * performed, so if they are out of range, the returned color is undefined.
	 * 
	 * @param alpha
	 *            Alpha component [0..255] of the color
	 * @param red
	 *            Red component [0..255] of the color
	 * @param green
	 *            Green component [0..255] of the color
	 * @param blue
	 *            Blue component [0..255] of the color
	 */
	public static int argb(int alpha, int red, int green, int blue) {
		return (alpha << 24) | (red << 16) | (green << 8) | blue;
	}

	public static void evolution(double ratio, Color retColor,
			Color startColor, Color goalColor) {
		Log.d("Color",
				Utils.formatString(
						"startColor.Alpha = %d, goalColor.Alpha = %d, ratio = %f, startColor.Alpha - goalColor.Alpha = %d",
						startColor.getAlpha(), goalColor.getAlpha(), ratio,
						startColor.getAlpha() - goalColor.getAlpha()));
		retColor.setAlpha(Math.abs((int) ((startColor.getAlpha() - goalColor
				.getAlpha()) * ratio) + startColor.getAlpha()));
		retColor.setRed(Math.abs((int) ((startColor.getRed() - goalColor
				.getRed()) * ratio) + startColor.getRed()));
		retColor.setGreen(Math.abs((int) ((startColor.getGreen() - goalColor
				.getGreen()) * ratio) + startColor.getGreen()));
		retColor.setBlue(Math.abs((int) ((startColor.getBlue() - goalColor
				.getBlue()) * ratio) + startColor.getBlue()));
	}

	public Color(int r, int g, int b) {
		this.color = rgb(r, g, b);
	}

	public Color(int r, int g, int b, int a) {
		this.color = argb(a, r, g, b);
	}

	public Color(int color) {
		this.color = color;
	}

	public int getRed() {
		return red(color);
	}

	public int getGreen() {
		return green(color);

	}

	public int getBlue() {
		return blue(color);
	}

	public int getAlpha() {
		return alpha(color);
	}

	public int getColor() {
		return color;
	}

	public void setRed(int red) {
		this.color = (this.color & 0xFF00FFFF) | (red << 16);
	}

	public void setGreen(int green) {
		this.color = (this.color & 0xFFFF00FF) | (green << 8);
	}

	public void setBlue(int blue) {
		this.color = (this.color & 0xFFFFFF00) | (blue);
	}

	public void setAlpha(int alpha) {
		this.color = (this.color & 0xFFFFFF) | (alpha << 24);
	}

	public void setColor(int r, int g, int b) {
		this.color = rgb(r, g, b);
	}

	public void setColor(int r, int g, int b, int a) {
		this.color = argb(a, r, g, b);
	}

	public void setColor(int color) {
		this.color = color;
	}
}
