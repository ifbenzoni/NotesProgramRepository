package notesProgram.manager;

import java.util.Collections;

import notesProgram.info.Image;
import notesProgram.info.Misc;
import notesProgram.info.TextExample;
import notesProgram.info.Vocab;
import notesProgram.io.NotesIO;

import java.io.File;
import java.util.ArrayList;

/**
 * manages information about notes; for use by GUI
 * 
 * @author Isaiah Benzoni
 */
public class NotesManager {
	
	/**
	 * array list of type miscellaneous
	 */
	private ArrayList<Misc> miscList;
	
	/**
	 * array list of image
	 */
	private ArrayList<Image> imageList;
	
	/**
	 * array list of vocabulary
	 */
	private ArrayList<Vocab> vocabList;
	
	/**
	 * array list of text examples
	 */
	private ArrayList<TextExample> textExampleList;
	
	/**
	 * array list of strings
	 */
	private ArrayList<String> subjects;
	
	/**
	 * current subject
	 */
	private String currentSubject;

	/**
	 * sets initial field values
	 */
	public NotesManager() {
		currentSubject = "";
		miscList = new ArrayList<Misc>();
		imageList = new ArrayList<Image>();
		vocabList = new ArrayList<Vocab>();
		textExampleList = new ArrayList<TextExample>();
		subjects = new ArrayList<String>();
	}

	/**
	 * gets misc list
	 * @return misc list
	 */
	public ArrayList<Misc> getMiscList() {
		Collections.sort(miscList);
		return miscList;
	}
	
	/**
	 * adds to misc list
	 * @param contents content input
	 */
	public void addToMiscList(String contents) {
		int miscCount = 1;
		for (int i = 0; i < miscList.size(); i++) {
			if (currentSubject.equals(miscList.get(i).getSubject())) {
				miscCount++;
			}
		}
		Misc misc = new Misc(Integer.toString(miscCount), currentSubject, contents);
		miscList.add(misc);
	}
	
	/**
	 * updates misc titles to be correct after removal
	 */
	public void miscListRemoveUpdate() {
		ArrayList<Misc> tempMiscList = new ArrayList<Misc>();
		for (int i = 0; i < miscList.size(); i++) {
			if (currentSubject.equals(miscList.get(i).getSubject())) {
				tempMiscList.add(miscList.get(i));
			}
		}
		for (int i = miscList.size() - 1; i >= 0; i--) {
			if (currentSubject.equals(miscList.get(i).getSubject())) {
				//System.out.println(miscList.get(i).getTitle());
				miscList.remove(i);
			}
		}
		for (int i = 0; i < tempMiscList.size() - 1; i++) {
			int currentTitleVal = Integer.valueOf(tempMiscList.get(i).getTitle());
			int nextTitleVal = Integer.valueOf(tempMiscList.get(i + 1).getTitle());
			if (i == 0 && currentTitleVal != 1) {
				tempMiscList.get(i).setTitle(String.valueOf(currentTitleVal - 1));
				currentTitleVal -= 1;
			}
			if ((nextTitleVal - currentTitleVal) != 1) {
				tempMiscList.get(i + 1).setTitle(String.valueOf(nextTitleVal - 1));
			}
		}
		for (int i = 0; i < tempMiscList.size(); i++) {
			miscList.add(tempMiscList.get(i));
			//System.out.println(tempMiscList.get(i).getTitle());
		}
	}
	
	/**
	 * gets image list
	 * @return image list
	 */
	public ArrayList<Image> getImageList() {
		Collections.sort(imageList);
		return imageList;
	}
	
	/**
	 * adds image to list
	 * @param title title input
	 * @param imageFile image file input
	 */
	public void addToImageList(String title, File imageFile) {
		Image image = new Image(title, currentSubject, imageFile);
		imageList.add(image);
	}
	
	/**
	 * gets vocab list
	 * @return vocab list
	 */
	public ArrayList<Vocab> getVocabList() {
		Collections.sort(vocabList);
		return vocabList;
	}
	
	/**
	 * adds vocab to list
	 * @param title title input
	 * @param definition defintion input
	 */
	public void addToVocabList(String title, String definition) {
		Vocab vocab = new Vocab(title, currentSubject, definition);
		vocabList.add(vocab);
	}
	
	/**
	 * gets text example list
	 * @return text example list
	 */
	public ArrayList<TextExample> getTextExampleList() {
		Collections.sort(textExampleList);
		return textExampleList;
	}
	
	/**
	 * adds to text example list
	 * @param title title input
	 * @param preface preface input
	 * @param example example input
	 */
	public void addToTextExampleList(String title, String preface, String example) {
		TextExample textExample = new TextExample(title, currentSubject, preface, example);
		textExampleList.add(textExample);
	}
	
	/**
	 * gets subjects list
	 * @return subjects list
	 */
	public ArrayList<String> getSubjects() {
		Collections.sort(subjects, String.CASE_INSENSITIVE_ORDER);
		return subjects;
	}
	
	/**
	 * adds subject to list
	 * @param subject subject input
	 */
	public void addSubject(String subject) {
		if (subject == null || "".equals(subject)) {
			throw new IllegalArgumentException("Invalid string input.");
		}
		subjects.add(subject);
	}
	
	/**
	 * gets current subject
	 * @return current subject
	 */
	public String getCurrentSubject() {
		return currentSubject;
	}
	
	/**
	 * sets current subject
	 * @param subject subject input
	 */
	public void setCurrentSubject(String subject) {
		if (subject == null) {
			throw new IllegalArgumentException("subject cannot be null");
		}
		currentSubject = subject;
	}
	
	/**
	 * loads a notes file
	 * @param file file input
	 */
	public void loadNotes(File file) {
		miscList = NotesIO.readMiscNotes(file);
		vocabList = NotesIO.readVocabNotes(file);
		imageList = NotesIO.readImageNotes(file);
		textExampleList = NotesIO.readTextExampleNotes(file);
		subjects = NotesIO.readSubjects(file);
	}
	
	/**
	 * saves a notes file
	 * @param file file input
	 */
	public void saveNotes(File file) {
		//System.out.println("here");
		//System.out.println(subjects.get(0));
		NotesIO.writeNotesFile(file, miscList, imageList, vocabList, textExampleList, subjects);
	}
	
	/**
	 * removes a subject from list
	 * @param subject subject input
	 */
	public void removeSubject(String subject) {
		subjects.remove(subject);
		for (int i = miscList.size() - 1; i >= 0; i--) {
			if (subject.equals(miscList.get(i).getSubject())) {
				miscList.remove(i);
			}
		}
		for (int i = vocabList.size() - 1; i >= 0; i--) {
			if (subject.equals(vocabList.get(i).getSubject())) {
				vocabList.remove(i);
			}
		}
		for (int i = imageList.size() - 1; i >= 0; i--) {
			if (subject.equals(imageList.get(i).getSubject())) {
				imageList.remove(i);
			}
		}
		for (int i = textExampleList.size() - 1; i >= 0; i--) {
			if (subject.equals(textExampleList.get(i).getSubject())) {
				textExampleList.remove(i);
			}
		}
	}
	
}
