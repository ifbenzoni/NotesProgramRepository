package notesProgram.info;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import notesProgram.info.Information.Category;


/**
 * tests Vocab class
 * 
 * @author Isaiah Benzoni
 */
public class VocabTest {
	
	/** definition constant */
	private static final String DEFINITION = "definition";
	
	/** definition title */
	private static final String TITLE = "title";
	
	/** definition subject */
	private static final String SUBJECT = "math";
	
	/**
	 * tests Vocab.Vocab()
	 */
	@Test
	public void testInformationConstructor() {
		Vocab vocab = new Vocab(TITLE, SUBJECT, DEFINITION);
		assertEquals(Category.VOCAB, vocab.getCategory());
		assertEquals("vocab", vocab.getCategoryString());
		assertEquals(TITLE, vocab.getTitle());
		assertEquals(SUBJECT, vocab.getSubject());
		assertEquals(DEFINITION, vocab.getDefinition());
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Vocab(null, SUBJECT, DEFINITION));
		assertThrows(IllegalArgumentException.class, () -> new Vocab("", SUBJECT, DEFINITION));
		assertThrows(IllegalArgumentException.class, () -> new Vocab(TITLE, null, DEFINITION));
		Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Vocab(TITLE, "", DEFINITION));
		assertThrows(IllegalArgumentException.class, () -> new Vocab(TITLE, SUBJECT, null));
		assertThrows(IllegalArgumentException.class, () -> new Vocab(TITLE, SUBJECT, ""));
		assertEquals("Invalid string input.", e.getMessage());
		assertEquals("Invalid string input.", e2.getMessage());
	}
	
	/**
	 * tests Vocab.addAdditionalInfo()
	 */
	@Test
	public void testAddAdditionalInfo() {
		Vocab vocab = new Vocab(TITLE, SUBJECT, DEFINITION);
		for (int i = 0; i < 10; i++) {
			vocab.addAdditionalInfo(Integer.toString(i));
		}
		vocab.addAdditionalInfo("info");
		assertEquals("0", vocab.getAdditionalInfo()[0]);
		assertEquals("info", vocab.getAdditionalInfo()[10]);
	}
	
	/**
	 * tests Vocab.removeAdditionalInfo()
	 */
	@Test
	public void testRemoveAdditionalInfo() {
		Vocab vocab = new Vocab(TITLE, SUBJECT, DEFINITION);
		for (int i = 0; i < 10; i++) {
			vocab.addAdditionalInfo(Integer.toString(i));
		}
		vocab.addAdditionalInfo("info");
		vocab.removeAdditionalInfo(1);
		vocab.removeAdditionalInfo(7);
		assertEquals(null, vocab.getAdditionalInfo()[10]);
		assertEquals("info", vocab.getAdditionalInfo()[8]);
		assertEquals("0", vocab.getAdditionalInfo()[0]);
		assertEquals("2", vocab.getAdditionalInfo()[1]);
	}
	
	/**
	 * test Vocab.editDefinition()
	 */
	@Test
	public void testEditDefinition() {
		Vocab vocab = new Vocab(TITLE, SUBJECT, DEFINITION);
		vocab.setDefinition("This is a longer definition.");
		assertEquals("This is a longer definition.", vocab.getDefinition());
	}
	
	/**
	 * test Vocab.toString()
	 */
	@Test
	public void testToString() {
		Vocab vocab = new Vocab(TITLE, SUBJECT, DEFINITION);
		vocab.addAdditionalInfo("extra info");
		vocab.addAdditionalInfo("more info");
		String formattedString = "";
		formattedString += "!" + "vocab" + "\n" + "@" + TITLE + "\n" + "#" + SUBJECT + "\n";
		formattedString += "=" + DEFINITION + "\n";
		for (int i = 0; i < vocab.getAdditionalInfo().length; i++) {
			if (vocab.getAdditionalInfo()[i] != null) {
				formattedString += "-" + vocab.getAdditionalInfo()[i] + "\n";
			}
		}
		assertEquals(formattedString, vocab.toString());
	}
	
	/**
	 * test Infomation.compareTo()()
	 */
	@Test
	public void testCompareTo() {
		Vocab vocab1 = new Vocab("a", SUBJECT, DEFINITION);
		Vocab vocab2 = new Vocab("ba", SUBJECT, DEFINITION);
		Vocab vocab3 = new Vocab("cba", SUBJECT, DEFINITION);
		assertEquals(-1, vocab1.compareTo(vocab2));
		assertEquals(-2, vocab1.compareTo(vocab3));
		assertEquals(2, vocab3.compareTo(vocab1));
	}

}
