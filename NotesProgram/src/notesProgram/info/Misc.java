package notesProgram.info;

/**
 * misc subclass of information
 * 
 * @author Isaiah Benzoni
 */
public class Misc extends Information {
	
	/**
	 * contents
	 */
	private String contents;
	
	/**
	 * sets initial field
	 * @param title title input
	 * @param subject subject input
	 * @param contents contents input
	 */
	public Misc(String title, String subject, String contents) {
		super(Category.MISC, title, subject);
		setContents(contents);
	}
	
	/**
	 * gets contents
	 * @return contents
	 */
	public String getContents() {
		return contents;
	}
	
	/**
	 * sets contents
	 * @param contents contents input
	 */
	public void setContents(String contents) {
		checkString(contents);
		this.contents = contents;
	}

	@Override
	public String toString() {
		return super.toString() + "===\n" + contents + "\n===";
	}
	
	/**
	 * overrides to compare titles of information
	 * 
	 * compares two misc titles using integers, returns integer for sorting
	 */
	@Override
	public int compareTo(Information i) {
		return Integer.valueOf(super.getTitle()).compareTo(Integer.valueOf(i.getTitle()));
	}

}
