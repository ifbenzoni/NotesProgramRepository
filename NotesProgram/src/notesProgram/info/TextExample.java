package notesProgram.info;

/**
 * text example subclass of information
 * 
 * @author Isaiah Benzoni
 */
public class TextExample extends Information {
	
	/**
	 * preface
	 */
	private String preface;
	
	/**
	 * contents
	 */
	private String contents;
	
	/**
	 * sets initial fields
	 * @param title title input
	 * @param subject subject input
	 * @param preface preface nput
	 * @param contents contents input
	 */
	public TextExample(String title, String subject, String preface, String contents) {
		super(Category.EXAMPLE, title, subject);
		setPreface(preface);
		setContents(contents);
	}
	
	/**
	 * sets preface
	 * @param preface preface input
	 */
	public void setPreface(String preface) {
		if (preface == null) {
			throw new IllegalArgumentException("preface cannot be null");
		}
		this.preface = preface;
	}
	
	/**
	 * gets preface
	 * @return preface
	 */
	public String getPreface() {
		return preface;
	}
	
	/**
	 * sets contents
	 * @param contents contents input
	 */
	public void setContents(String contents) {
		checkString(contents);
		this.contents = contents;
	}
	
	/**
	 * gets contents
	 * @return contents
	 */
	public String getContents() {
		return contents;
	}
	
	@Override
	public String toString() {
		return super.toString() + "===\n" + preface + "\n===\n" + contents + "\n===";
	}

}
