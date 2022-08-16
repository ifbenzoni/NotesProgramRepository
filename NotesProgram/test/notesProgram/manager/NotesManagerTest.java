package notesProgram.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

public class NotesManagerTest {
	
	@Test
	public void testNotesManager() {
		NotesManager notesManager = new NotesManager();
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> notesManager.addToMiscList("one"));
		assertEquals("Invalid input, null or blank.", e.getMessage());
		
		notesManager.setCurrentSubject("subject one");
		assertEquals("subject one", notesManager.getCurrentSubject());
		
		notesManager.addToMiscList("one");
		notesManager.addToMiscList("two");
		notesManager.addToMiscList("three");
		notesManager.getMiscList().remove(0);
		notesManager.miscListRemoveUpdate();
		
		assertEquals("two", notesManager.getMiscList().get(0).getContents());
		assertEquals("1", notesManager.getMiscList().get(0).getTitle());
		
		File imageFile = new File("./test-files/gray_circle.png");
		notesManager.addToImageList("Image1", imageFile);
		
		assertEquals(1, notesManager.getImageList().size());
		assertEquals("Image1", notesManager.getImageList().get(0).getTitle());
		assertEquals(imageFile.getAbsolutePath(), notesManager.getImageList().get(0).getImagePath());
	}

}
