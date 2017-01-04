package com.sc.td.common.utils.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ImgCreate {

	public void createImage(String path, String content, String fileName) {
		Random random = new Random();
		Identicon identicon = new Identicon(content,
				new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		try {
			identicon.saveToPath(path);
			Pic pic = new Pic();
			BufferedImage img = pic.loadImageLocal(path + "identicon.jpg");
			pic.writeImageLocal(path + fileName + ".jpg", pic.modifyImage(img, content, 600, 600));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
