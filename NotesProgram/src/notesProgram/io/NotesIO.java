package notesProgram.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import notesProgram.info.Image;
import notesProgram.info.Information.Category;
import notesProgram.info.Misc;
import notesProgram.info.TextExample;
import notesProgram.info.Vocab;

/**
 * IO for notes; saving and loading
 * 
 * @author Isaiah Benzoni
 */
public class NotesIO {
	
	/*
	public static void main(String[] args) {
		//readMiscNotes(new File("test-files/readingTestFile1"));
		readSubjects(new File("test-files/readingTestFile1"));
	}
	*/
	
	/**
	 * read misc notes from saved file
	 * @param file file input
	 * @return array list of misc objects
	 */
	public static ArrayList<Misc> readMiscNotes(File file) {
		ArrayList<Misc> miscList = new ArrayList<Misc>();
		String fileText = "";
		String miscSection = "";
		String title = "";
		String subject = "";
		String contents = "";
		Scanner fileReader;
		try {
			fileReader = new Scanner(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		while(fileReader.hasNextLine()) {
			fileText += fileReader.nextLine() + "\n";
		}
		Scanner miscScanner = new Scanner(fileText);
		//miscScanner.useDelimiter("\\r?\\n?[-MISC_SECTION-]");
		miscScanner.useDelimiter("-MISC_SECTION-");
		miscSection += miscScanner.next();
		/*
		while(miscScanner.hasNext()) {
			miscSection += miscScanner.next();
		}
		*/
		miscSection = miscSection.trim();
		//System.out.println(miscSection);
		Scanner miscInfo = new Scanner(miscSection);
		//miscInfo.useDelimiter("\\r?\\n?[-]");
		miscInfo.useDelimiter("---");
		while(miscInfo.hasNext()) {
			String miscObject = miscInfo.next();
			Scanner miscObjectScanner = new Scanner(miscObject);
			miscObjectScanner.useDelimiter("===");
			miscObjectScanner.next();
			title = miscObjectScanner.next().trim();
			subject = miscObjectScanner.next().trim();
			contents = miscObjectScanner.next().trim();
			Misc misc = new Misc(title, subject, contents);
			miscList.add(misc);
			miscObjectScanner.close();
		}
		fileReader.close();
		miscScanner.close();
		miscInfo.close();
		/*
		for (int i = 0; i < miscList.size(); i++) {
			System.out.println(miscList.get(i).getTitle());
			System.out.println(miscList.get(i).getSubject());
			System.out.println(miscList.get(i).getContents());
			System.out.println();
		}
		*/
		return miscList;
	}
	
	/**
	 * read image notes from saved file
	 * @param file fie input
	 * @return array list of image objects
	 */
	public static ArrayList<Image> readImageNotes(File file) {
		ArrayList<Image> imageList = new ArrayList<Image>();
		String fileText = "";
		String imageSection = "";
		String title = "";
		String subject = "";
		String imagePath = "";
		Scanner fileReader;
		try {
			fileReader = new Scanner(new FileInputStream(file));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		while(fileReader.hasNextLine()) {
			fileText += fileReader.nextLine() + "\n";
		}
		Scanner imageScanner = new Scanner(fileText);
		imageScanner.useDelimiter("-IMAGE_SECTION-");
		imageScanner.next();
		imageSection += imageScanner.next();
		imageSection = imageSection.trim();
		Scanner imageInfo = new Scanner(imageSection);
		imageInfo.useDelimiter("---");
		while(imageInfo.hasNext()) {
			String imageObject = imageInfo.next();
			Scanner imageObjectScanner = new Scanner(imageObject);
			imageObjectScanner.useDelimiter("===");
			imageObjectScanner.next();
			title = imageObjectScanner.next().trim();
			subject = imageObjectScanner.next().trim();
			imagePath = imageObjectScanner.next().trim();
			//System.out.println(imagePath);
			Image image = new Image(title, subject, new File(imagePath));
			imageList.add(image);
			imageObjectScanner.close();
		}
		fileReader.close();
		imageScanner.close();
		imageInfo.close();
		return imageList;
	}

	/**
	 * read vocab notes from saved file
	 * @param file file input
	 * @return array list of vocab objects
	 */
	public static ArrayList<Vocab> readVocabNotes(File file) {
		ArrayList<Vocab> vocabList = new ArrayList<Vocab>();
		String fileText = "";
		String vocabSection = "";
		String title = "";
		String subject = "";
		String definition = "";
		ArrayList<String> additionalInfo = new ArrayList<String>();
		Scanner fileReader;
		try {
			fileReader = new Scanner(new FileInputStream(file));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		while(fileReader.hasNextLine()) {
			fileText += fileReader.nextLine() + "\n";
		}
		Scanner vocabScanner = new Scanner(fileText);
		vocabScanner.useDelimiter("-VOCAB_SECTION-");
		vocabScanner.next();
		vocabSection += vocabScanner.next();
		vocabSection = vocabSection.trim();
		//System.out.println(vocabSection);
		Scanner vocabInfo = new Scanner(vocabSection);
		vocabInfo.useDelimiter("---");
		while(vocabInfo.hasNext()) {
			String vocabObject = vocabInfo.next();
			Scanner vocabObjectScanner = new Scanner(vocabObject);
			vocabObjectScanner.useDelimiter("===");
			vocabObjectScanner.next();
			title = vocabObjectScanner.next().trim();
			subject = vocabObjectScanner.next().trim();
			definition = vocabObjectScanner.next().trim();
			//System.out.println("title: " + title);
			//System.out.println("definition: " + definition);
			Vocab vocab = new Vocab(title, subject, definition);
			while(vocabObjectScanner.hasNext()) {
				vocab.addAdditionalInfo(vocabObjectScanner.next().trim());
			}
			vocabList.add(vocab);
			vocabObjectScanner.close();
		}
		fileReader.close();
		vocabScanner.close();
		vocabInfo.close();
		return vocabList;
	}

	/**
	 * read text example notes from saved file
	 * @param file file input
	 * @return array list of text example objects
	 */
	public static ArrayList<TextExample> readTextExampleNotes(File file) {
		ArrayList<TextExample> textExampleList = new ArrayList<TextExample>();
		String fileText = "";
		String textExampleSection = "";
		String title = "";
		String subject = "";
		String preface = "";
		String contents = "";
		Scanner fileReader;
		try {
			fileReader = new Scanner(new FileInputStream(file));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		while(fileReader.hasNextLine()) {
			fileText += fileReader.nextLine() + "\n";
		}
		Scanner textExampleScanner = new Scanner(fileText);
		textExampleScanner.useDelimiter("-TEXT_EXAMPLE_SECTION-");
		textExampleScanner.next();
		textExampleSection += textExampleScanner.next();
		textExampleSection = textExampleSection.trim();
		//System.out.println(textExampleSection);
		Scanner textExampleInfo = new Scanner(textExampleSection);
		textExampleInfo.useDelimiter("---");
		while(textExampleInfo.hasNext()) {
			String textExampleObject = textExampleInfo.next();
			Scanner textExampleObjectScanner = new Scanner(textExampleObject);
			textExampleObjectScanner.useDelimiter("===");
			textExampleObjectScanner.next();
			title = textExampleObjectScanner.next().trim();
			//System.out.println("title: " + title);
			subject = textExampleObjectScanner.next().trim();
			//System.out.println("subject: " + subject);
			preface = textExampleObjectScanner.next().trim();
			//System.out.println("preface: " + preface);
			contents = textExampleObjectScanner.next().trim();
			//System.out.println("contents: " + contents);
			TextExample textExample = new TextExample(title, subject, preface, contents);
			textExampleList.add(textExample);
			textExampleObjectScanner.close();
		}
		fileReader.close();
		textExampleScanner.close();
		textExampleInfo.close();
		return textExampleList;
	}
	
	/**
	 * read subject notes from saved file
	 * @param file file input
	 * @return array list of subject objects
	 */
	public static ArrayList<String> readSubjects(File file) {
		ArrayList<String> subjects = new ArrayList<String>();
		String fileText = "";
		String subjectsSection = "";
		Scanner fileReader;
		try {
			fileReader = new Scanner(new FileInputStream(file));
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		while(fileReader.hasNextLine()) {
			fileText += fileReader.nextLine() + "\n";
		}
		Scanner subjectsScanner = new Scanner(fileText);
		subjectsScanner.useDelimiter("-SUBJECTS_SECTION-");
		subjectsScanner.next();
		subjectsSection += subjectsScanner.next();
		subjectsSection = subjectsSection.trim();
		//System.out.println(subjectsSection);
		Scanner subjectsInfo = new Scanner(subjectsSection);
		subjectsInfo.useDelimiter("\n");
		while(subjectsInfo.hasNext()) {
			String subjectObject = subjectsInfo.next();
			subjects.add(subjectObject);
		}
		fileReader.close();
		subjectsScanner.close();
		subjectsInfo.close();
		/*
		for (int i = 0; i < subjects.size(); i++) {
			System.out.println(subjects.get(i));
			System.out.println();
		}
		*/
		return subjects;
	}

	/**
	 * writes notes to file
	 * @param file file output
	 */
	public static void writeNotesFile(File file, ArrayList<Misc> miscList, ArrayList<Image> imageList,
			ArrayList<Vocab> vocabList, ArrayList<TextExample> textExampleList, ArrayList<String> subjects) {
		PrintStream fileWriter;
		//System.out.println("here");
		try {
			fileWriter = new PrintStream(file);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
		fileWriter.println("-MISC_SECTION-");
		for (int i = 0; i < miscList.size(); i++) {
			fileWriter.println(miscList.get(i).toString());
		}
		fileWriter.println("-MISC_SECTION-" + "\n");
		
		fileWriter.println("-IMAGE_SECTION-");
		for (int i = 0; i < imageList.size(); i++) {
			fileWriter.println(imageList.get(i).toString());
		}
		fileWriter.println("-IMAGE_SECTION-" + "\n");
		
		fileWriter.println("-VOCAB_SECTION-");
		for (int i = 0; i < vocabList.size(); i++) {
			fileWriter.println(vocabList.get(i).toString());
			//System.out.println(vocabList.get(i).toString());
		}
		fileWriter.println("-VOCAB_SECTION-" + "\n");
		
		fileWriter.println("-TEXT_EXAMPLE_SECTION-");
		for (int i = 0; i < textExampleList.size(); i++) {
			fileWriter.println(textExampleList.get(i).toString());
		}
		fileWriter.println("-TEXT_EXAMPLE_SECTION-" + "\n");
		
		fileWriter.println("-SUBJECTS_SECTION-");
		for (int i = 0; i < subjects.size(); i++) {
			fileWriter.println(subjects.get(i));
		}
		fileWriter.println("-SUBJECTS_SECTION-" + "\n");
		
		fileWriter.close();
	}
	
}
