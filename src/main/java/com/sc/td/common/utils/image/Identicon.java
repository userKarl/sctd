package com.sc.td.common.utils.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.imageio.ImageIO;

public class Identicon {
	private int width = 600;
	private int height = 600;
	private BufferedImage bufferedImage;
	private String name;

	private Color BACKGROUND_COLOR = new Color(240, 240, 240);
	private Color BLOCK_COLOR;

	private byte[] hashValues;

	public Identicon(String name, Color blockColor) {
		this.name = name;
		this.hashValues = getHashValues(this.name);
		this.BLOCK_COLOR = blockColor;
		generateIndenticon();
	}

	private void generateIndenticon() {
		createBufferedImage(this.width, this.height);
		setBackgroundColor();
		drawBlocks(getHashValues(this.name));
	}

	private void setBackgroundColor() {
		for (int i = 0; i < this.width; i = i + 1) {
			for (int j = 0; j < this.height; j = j + 1) {

				this.bufferedImage.setRGB(i, j, this.BACKGROUND_COLOR.getRGB());

			}
		}
	}

	private byte[] getHashValues(String name) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.hashValues = md.digest(name.getBytes());

		return this.hashValues;
	}

	private void drawBlocks(byte[] hashValues) {

		for (int j = 50; j < this.height - 50; j = j + 1) {
			int y = (j - 50) / 100;
			for (int i = 50; i < this.width - 50; i = i + 1) {
				int x = (i - 50) / 100;

				if (y <= 2) {
					if (hashValues[x + y * 5] >= 0) {
						this.bufferedImage.setRGB(j, i, this.BLOCK_COLOR.getRGB());
					}
				} else {
					if (hashValues[x + (y - 2) % 2 * 5] >= 0) {
						this.bufferedImage.setRGB(j, i, this.BLOCK_COLOR.getRGB());
					}
				}

			}
		}
	}

	public void saveToPath(String filePath) throws IOException {
		File file = new File(filePath + "identicon.jpg");
		ImageIO.write(this.bufferedImage, "jpg", file);

	}

	private void createBufferedImage(int width, int height) {
		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);

	}

	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}

	public String getName() {
		return this.name;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
