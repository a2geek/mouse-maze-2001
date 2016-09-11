package a2geek.games.mousemaze2001.images;
import java.awt.*;
import java.awt.image.*;

import a2geek.games.mousemaze2001.images.*;

public class ImageCanvas extends Canvas {
	private Image fImage;
	private int fWidth;
	private int fHeight;
	private Color color;
	public void setColor(Color newColor) {
		color = newColor;
	}

	public ImageCanvas(String filename) {
		fImage = ImageManager.getInstance().getImage(filename);

		if (fImage != null) {
			fWidth = fImage.getWidth(this);
			fHeight = fImage.getHeight(this);
		} else {
			fWidth = 20;
			fHeight = 20;
		}
		setSize(fWidth, fHeight);
	}

	public void paint(Graphics g) {
		paintBackground(g);
		if (fImage != null) {
			g.drawImage(fImage, 0, 0, fWidth, fHeight, this);
		}
	}

	public void paintBackground(java.awt.Graphics g) {
		g.setColor(color);
		g.fillRect(0, 0, getBounds().width, getBounds().height);
	}
}