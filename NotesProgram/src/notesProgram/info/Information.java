package notesProgram.info;

/**
 * abstract class used as base for information types in notes program
 * 
 * @author Isaiah Benzoni
 */
public abstract class Information implements Comparable<Information> {
	
	/**
	 * type of information
	 */
	private Category category;
	
	/**
	 * title of piece of info
	 */
	private String title;
	
	/**
	 * subject of information
	 */
	private String subject;
	
	/**
	 * sets up options for category values
	 * 
	 * @author Isaiah Benzoni
	 */
	public enum Category { VOCAB, EXAMPLE, IMAGE, MISC }
	
	/**
	 * sets information fields initial values
	 */
	public Information(Category category, String title, String subject) {
		setCategory(category);
		setTitle(title);
		setSubject(subject);
	}
	
	/**
	 * checks string for appropriate values
	 * 
	 * @param s string to check
	 */
	public void checkString(String s) {
		if (s == null || "".equals(s)) {
			throw new IllegalArgumentException("Invalid input, null or blank.");
		}
	}
	
	/**
	 * gets category
	 * @return category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * gets category as string
	 * @return category string
	 */
	public String getCategoryString() {
		if (category == Category.EXAMPLE) {
			return "example";
		} else if (category == Category.IMAGE) {
			return "image";
		} else if (category == Category.MISC) {
			return "misc";
		} else if (category == Category.VOCAB) {
			return "vocab";
		}
		return null;
	}
	
	private void setCategory(Category category) {
		if (category == null) {
			throw new IllegalArgumentException("category cannot be null");
		}
		this.category = category;
	}
	
	/**
	 * gets title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * sets title to new value, checks input using check string method
	 * @param title title to be set
	 */
	public void setTitle(String title) {
		checkString(title);
		this.title = title;
	}
	
	/**
	 * gets subject
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * sets subject to specified value, check input using check string
	 * @param subject subject to be set
	 */
	public void setSubject(String subject) {
		checkString(subject);
		this.subject = subject;
	}
	
	/**
	 * overrides to compare titles of information
	 * 
	 * compares two information titles, returns integer for sorting
	 */
	@Override
	public int compareTo(Information i) {
		return title.compareToIgnoreCase(i.getTitle());
	}
	
	/**
	 * overrides to return formatted category, title, and subject info
	 */
	@Override
	public String toString() {
		return "---" + "\n===\n" + title + "\n===\n" + subject + "\n";
	}

}
