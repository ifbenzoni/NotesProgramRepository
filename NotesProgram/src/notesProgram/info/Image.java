package notesProgram.info;

import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * image subclass of information
 * 
 * @author Isaiah Benzoni
 */
public class Image extends Information {
	
	/** image */
	private BufferedImage image;
	
	/** height of image */
	private int height;
	
	/** width of image */
	private int width;
	
	/** image path */
	private String imagePath;

	/**
	 * sets initial field values
	 * @param title title input
	 * @param subject subject input
	 * @param file file input
	 */
	public Image(String title, String subject, File file) {
		super(Category.IMAGE, title, subject);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		height = (int) (screenHeight / 3);
		width = (int) (screenWidth / 3);
		try {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("IO problem:" + e + " title: " + title);
		}
		imagePath = file.getAbsolutePath();
	}
	
	/**
	 * overridden to return image path for saving and reading
	 */
	@Override
	public String toString() {
		return super.toString() + "===\n" + imagePath + "\n===";
	}
	
	/**
	 * gets image path
	 * @return image path
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * gets image
	 * @return image
	 */
	public BufferedImage getImage() {
		return image;
	}
	
	/**
	 * sets image
	 * @param file file used for image
	 */
	public void setImage(File file) {
		try {
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println("IO problem:" + e);
		}
	}

}
