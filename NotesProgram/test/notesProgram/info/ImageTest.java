package notesProgram.info;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

public class ImageTest {
	
	@Test
	public void testImage() {
		File imageFile = new File("./test-files/gray_circle.png");
		Image image = new Image("title", "subject", imageFile);
		
		assertEquals(imageFile.getAbsolutePath(), image.getImagePath());
		assertEquals("---\n===\ntitle\n===\nsubject\n===\n" + imageFile.getAbsolutePath() + "\n===", image.toString());
	}

}
