package notesProgram.info;

/**
 * vocab subclass of information
 * 
 * @author Isaiah Benzoni
 */
public class Vocab extends Information {
	
	/**
	 * definition
	 */
	private String definition;
	
	/**
	 * additional info
	 */
	private String[] additionalInfo;
	
	/**
	 * size of additional info array
	 */
	private int additionalInfoSize;

	/**
	 * sets initial field values
	 * @param category category value to set
	 * @param title title value to set
	 * @param subject subject value to set
	 * @param definition definition value to set
	 */
	public Vocab(String title, String subject, String definition) {
		super(Category.VOCAB, title, subject);
		setDefinition(definition);
		additionalInfo = new String[10];
	}
	
	/**
	 * grows additional info array
	 */
	private void growAdditionalInfoArray() {
		if (additionalInfoSize == additionalInfo.length) {
			String[] tempArray = new String[additionalInfoSize * 2];
			for (int i = 0; i < additionalInfoSize; i++) {
				tempArray[i] = additionalInfo[i];
			}
			additionalInfo = tempArray;
		}
	}
	
	/**
	 * gets definition
	 * @return definition
	 */
	public String getDefinition() {
		return definition;
	}
	
	/**
	 * sets definition of vocab
	 * @param definition definition to set
	 */
	public void setDefinition(String definition) {
		checkString(definition);
		this.definition = definition;
	}
	
	/**
	 * adds additional info
	 * @param info info to add
	 */
	public void addAdditionalInfo(String info) {
		checkString(info);
		growAdditionalInfoArray();
		additionalInfo[additionalInfoSize] = info;
		additionalInfoSize++;
	}
	
	/**
	 * removes additional info
	 * @param idx index to remove info from
	 */
	public void removeAdditionalInfo(int idx) {
		for (int i = idx; i < additionalInfoSize - 1; i++) {
			additionalInfo[i] = additionalInfo[i + 1];
		}
		additionalInfo[additionalInfoSize - 1] = null;
		additionalInfoSize--;
	}
	
	/**
	 * gets additionalInfo
	 * @return additionalInfo
	 */
	public String[] getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * overridden for specific implementation
	 * generates string representation of vocab for saving & reading files
	 */
	@Override
	public String toString() {
		String additionalInfoString = "";
		for (int i = 0; i < additionalInfoSize; i++) {
			additionalInfoString += "\n===\n" + additionalInfo[i];
		}
		return super.toString() + "===\n" + definition + additionalInfoString;
	}

}
