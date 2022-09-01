package notesProgram.ui;

import javax.swing.*;

import notesProgram.info.Information;
import notesProgram.manager.NotesManager;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * GUI for notes program
 * 
 * @author Isaiah Benzoni
 */
public class NotesGUI extends JFrame implements ActionListener, WindowListener {
	
	/** serial version UID */
	private static final long serialVersionUID = 1L;
	/** title of program */
	private static final String TITLE = "Notes Program";
	
	/** notes manager */
	private NotesManager notesManager;
	/** menu bar for general menu */
	private JMenuBar generalProgramMenuBar;
	/** general menu eg save*/
	private JMenu generalProgramMenu;
	/** save menu item */
	private JMenuItem Save;
	/** load menu item */
	private JMenuItem Load;
	/** quit menu item */
	private JMenuItem Quit;
	/** dimension for screen size */
	private	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	/** subjects panel */
	private SubjectsPanel subjectsPanel;
	/** note types and selection panel */
	private NoteTypesAndSelectionPanel noteTypesAndSelectionPanel;
	/** input panel */
	private InputPanel inputPanel;
	/** display panel */
	private DisplayPanel displayPanel;
	
	/** type of current note */
	private String currentNoteType;
	/** name of current note */
	private String currentNoteName;
	
	/** screen width */
	double screenWidth = screenSize.getWidth();
	/** screen height */
	double screenHeight = screenSize.getHeight();
	
	/** tabbed pane to separate sections of notes program */
	private JTabbedPane jTabbedPane;
	/** panel for display section */
	private JPanel jPanel1;
	/** panel for input and selection section */
	private JPanel jPanel2;
	
	/** file name for autosave */
	private String fileName;
	
	/**
	 * creates new notes manager and calls method to start GUI
	 */
	public NotesGUI() {
		
		notesManager = new NotesManager();
		
		initializeGUI();
		
	}
	
	/**
	 * starts GUI and sets a lot of values
	 */
	public void initializeGUI() {
		
		setSize((int) screenWidth / 7 * 6, (int) screenHeight / 7 * 6);
		setLocation((int) screenWidth / 12, (int) screenHeight / 12);
		setTitle(TITLE);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		generalProgramMenuBar = new JMenuBar();
		generalProgramMenu = new JMenu("File");
		generalProgramMenuBar.add(generalProgramMenu);
		Save = new JMenuItem("Save");
		Load = new JMenuItem("Load");
		Quit = new JMenuItem("Quit");
		generalProgramMenu.add(Save);
		generalProgramMenu.addSeparator();
		generalProgramMenu.add(Load);
		generalProgramMenu.addSeparator();
		generalProgramMenu.add(Quit);
		setJMenuBar(generalProgramMenuBar);
		
		jTabbedPane = new JTabbedPane();
		jPanel1 = new JPanel(new FlowLayout());
		jPanel2 = new JPanel(new GridBagLayout());
		
		Save.addActionListener(this);
		Load.addActionListener(this);
		Quit.addActionListener(this);
		
		Container c = getContentPane();
		//c.setLayout(new GridBagLayout());
		//setLayout(null);
		//(...).setBounds(x, y, width, height)
		
		displayPanel = new DisplayPanel();
		jPanel1.add(displayPanel);
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		subjectsPanel = new SubjectsPanel();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		jPanel2.add(subjectsPanel, constraints);
		
		noteTypesAndSelectionPanel = new NoteTypesAndSelectionPanel();
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.anchor = GridBagConstraints.NORTH;
		jPanel2.add(noteTypesAndSelectionPanel, constraints);
		noteTypesAndSelectionPanel.updateCurrentNote();
		
		inputPanel = new InputPanel();
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridwidth = 2;
		constraints.gridheight = 3;
		constraints.anchor = GridBagConstraints.LINE_START;
		jPanel2.add(inputPanel, constraints);
		
		jTabbedPane.addTab("Display", jPanel1);
		jTabbedPane.addTab("Input and Selection", jPanel2);
		c.add(jTabbedPane);
		
		noteTypesAndSelectionPanel.updateNoteTypeDisplay();
		
		addWindowListener(this);
		setVisible(true);
	}
	
	/**
	 * starting point for program, starts GUI
	 * @param args unsure
	 */
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		NotesGUI notesGUI = new NotesGUI();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//JFileChooser jFileChooser = new JFileChooser("./");
		if (e.getSource() == Load) {
			try {
				fileName = getFileName(true);
				notesManager.loadNotes(new File(fileName));
				subjectsPanel.updateSubjectsMenu();
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(NotesGUI.this, "Unable to load file." + iae.getMessage());
			} catch (IllegalStateException ise) {
				
			} catch (NoSuchElementException exception) {
				notesManager.saveNotes(new File(fileName));
			}
		} else if (e.getSource() == Save) {
			try {
				//System.out.println("here");
				notesManager.saveNotes(new File(getFileName(false)));
				//System.out.println("here");
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(NotesGUI.this, "Unable to save file.");
			} catch (IllegalStateException ise) {
				
			}
		} else {
			try {
				notesManager.saveNotes(new File(getFileName(false)));
				System.exit(0);
			} catch (IllegalArgumentException iae) {
				JOptionPane.showMessageDialog(NotesGUI.this, "Unable to save file.");
			} catch (IllegalStateException ise) {
				
			}
		}
	}
	
	/**
	 * this method was made using structure found in a CSC216 gui class
	 * 
	 * gets selected file's name
	 * 
	 * @param load load if true, save if false
	 * @return absolute path of file
	 */
	private String getFileName(boolean load) {
		JFileChooser jFileChooser = new JFileChooser("/");// use "/" for root dir
		int returnVal = Integer.MIN_VALUE;
		if (load) {
			returnVal = jFileChooser.showOpenDialog(this);
		} else {
			//System.out.println("here");
			returnVal = jFileChooser.showSaveDialog(this);
			//System.out.println("here");
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//System.out.println("here");
			throw new IllegalStateException();
		}
		//System.out.println("here");
		File selectedFile = jFileChooser.getSelectedFile();
		//System.out.println(selectedFile.getAbsolutePath());
		return selectedFile.getAbsolutePath();
	}
	
	/**
	 * menu for managing subject related information
	 * 
	 * @author Isaiah Benzoni
	 */
	private class SubjectsPanel extends JPanel implements ActionListener {
		
		/** serial version UID */
		private static final long serialVersionUID = 1L;
		/** title of subjects menu */
		private JLabel menuTitle;
		/** menu of subjects */
		private JComboBox<String> subjectMenu;
		/** button to add subject */
		private JButton addSubjectButton;
		/** button to remove subject */
		private JButton removeSubjectButton;
		/** area to write a new subject name */
		private JTextField newSubjectArea;
		/** panel for add and remove buttons */
		private JPanel buttonArea;
		
		/**
		 * sets up subjects panel
		 */
		public SubjectsPanel() {
			
			this.setLayout(new GridBagLayout());
			menuTitle = new JLabel("Subjects/Topics Menu");
			subjectMenu = new JComboBox<String>();
			subjectMenu.setPreferredSize(new Dimension(250, 20));
			addSubjectButton = new JButton("add subject");
			removeSubjectButton = new JButton("remove subject");
			newSubjectArea = new JTextField(25);
			buttonArea = new JPanel(new GridLayout(1, 2));
			buttonArea.add(addSubjectButton);
			buttonArea.add(removeSubjectButton);
			
			addSubjectButton.addActionListener(this);
			removeSubjectButton.addActionListener(this);
			subjectMenu.addActionListener(this);
			
			GridBagConstraints constraints = new GridBagConstraints();
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(newSubjectArea, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(buttonArea, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(menuTitle, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 3;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(subjectMenu, constraints);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == addSubjectButton) {
				if (!("".equals(newSubjectArea.getText())) && !(notesManager.getSubjects().contains(newSubjectArea.getText()))) {
					notesManager.addSubject(newSubjectArea.getText());
					updateSubjectsMenu();
				}
				noteTypesAndSelectionPanel.updateNotesSelection();
				newSubjectArea.setText("");
			} else if (e.getSource() == removeSubjectButton) {
				int choice = JOptionPane.showConfirmDialog(NotesGUI.this, "Confirm subject and assiciated notes removal?");
				if (choice == 0) {
					notesManager.removeSubject((String) subjectMenu.getSelectedItem());
					subjectMenu.removeItem(subjectMenu.getSelectedItem());
					if (!(subjectMenu.getSelectedItem() == null)) {
						notesManager.setCurrentSubject((String) subjectMenu.getSelectedItem());
					} else {
						notesManager.setCurrentSubject("");
					}
				
					noteTypesAndSelectionPanel.updateNotesSelection();
				}
			} else {
				if (!(subjectMenu.getSelectedItem() == null) && !"".equals(subjectMenu.getSelectedItem())) {
					notesManager.setCurrentSubject((String) subjectMenu.getSelectedItem());
					noteTypesAndSelectionPanel.updateNotesSelection();
				}
				//System.out.println(notesManager.getCurrentSubject()); //TODO pop up to confirm remove subject & remove subject removes associated notes
			}//TODO error/exception handling and desktop icon, io load not functioning properly, other
			//started error handling - check illegal state exceptions "ise"
			displayPanel.updateDisplayedInfo();
			
		}
		
		/**
		 * updates subject menu
		 */
		public void updateSubjectsMenu() {
			for (int i = subjectMenu.getItemCount(); i > 0; i--) {
				subjectMenu.removeItem(subjectMenu.getItemAt(i - 1));
			}
			//System.out.println(subjectMenu.getItemCount());
			for (int i = 0; i < notesManager.getSubjects().size(); i++) {
				subjectMenu.addItem(notesManager.getSubjects().get(i));
			}
			try {
				notesManager.setCurrentSubject((String) subjectMenu.getSelectedItem());
			} catch (IllegalArgumentException exception) {
				notesManager.setCurrentSubject("");
			}
			newSubjectArea.setText("");
		}
		
	}
	
	/**
	 * panel for selecting note type and individual note
	 *
	 * @author Isaiah Benzoni
	 */
	private class NoteTypesAndSelectionPanel extends JPanel implements ActionListener {

		/** serial version UID */
		private static final long serialVersionUID = 1L;
		/** menu title label */
		private JLabel typeMenuTitle;
		/** menu for note types */
		private JComboBox<String> noteTypeMenu;
		
		/** label for note selection menu */
		private JLabel noteSelectionMenuLabel;
		
		/** menu for selecting individual notes */
		private JComboBox<String> noteSelectionMenu;
		/** button to remove a note */
		private JButton removeButton;
		
		/** area to input name of searched note */
		private JTextArea noteSearchArea;
		/** button to trigger search for note */
		private JButton searchButton;
		
		/**
		 * sets up panel
		 */
		public NoteTypesAndSelectionPanel() {
			
			this.setLayout(new GridBagLayout());
			typeMenuTitle = new JLabel("Note Types");
			noteTypeMenu = new JComboBox<String>();
			noteTypeMenu.addItem("VOCAB");
			noteTypeMenu.addItem("EXAMPLE");
			noteTypeMenu.addItem("IMAGE");
			noteTypeMenu.addItem("MISC");
			
			noteSelectionMenuLabel = new JLabel("note selection");
			noteSelectionMenu = new JComboBox<String>();
			noteSelectionMenu.setPreferredSize(new Dimension(200, 20));
			
			removeButton = new JButton("remove note");
			noteSearchArea = new JTextArea();
			noteSearchArea.setPreferredSize(new Dimension(200, 20));
			noteSearchArea.setBorder(BorderFactory.createLineBorder(Color.black));
			searchButton = new JButton("search");
			
			noteTypeMenu.addActionListener(this);
			noteSelectionMenu.addActionListener(this);
			removeButton.addActionListener(this);
			searchButton.addActionListener(this);
			
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(typeMenuTitle, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(noteTypeMenu, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(noteSelectionMenuLabel, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 3;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(noteSearchArea, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 4;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(searchButton, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 5;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(removeButton, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 6;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(noteSelectionMenu, constraints);
			
			currentNoteType = noteTypeMenu.getSelectedItem().toString();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == noteTypeMenu) {
				currentNoteType = noteTypeMenu.getSelectedItem().toString();
				inputPanel.updateContentInput();
				displayPanel.updateDisplayedSetup();
				updateNotesSelection();
			} else if (e.getSource() == noteSelectionMenu) {
				updateCurrentNote();
			} else if (e.getSource() == removeButton){
				int choice = JOptionPane.showConfirmDialog(NotesGUI.this, "Confirm note removal?");
				if (choice == 0) {
					inputPanel.removeCurrentNote();
					updateNotesSelection();
				}
			} else {
				for (int i = 0; i < noteSelectionMenu.getItemCount(); i++) {
					if (noteSearchArea.getText().equalsIgnoreCase(noteSelectionMenu.getItemAt(i))) {
						noteSelectionMenu.setSelectedIndex(i);
					}
				}
				noteSearchArea.setText("");
			}
			displayPanel.updateDisplayedInfo();
			updateCurrentNote();
			
		}
		
		/**
		 * updates current note
		 */
		public void updateCurrentNote() {
			if (!(noteSelectionMenu.getSelectedItem() == null)) {
				currentNoteName = noteSelectionMenu.getSelectedItem().toString();
			} else {
				currentNoteName = "";
			}
		}
		
		/**
		 * updates notes selection
		 */
		public void updateNotesSelection() {
			for (int i = noteSelectionMenu.getItemCount(); i > 0; i--) {
				noteSelectionMenu.removeItem(noteSelectionMenu.getItemAt(i - 1));
			}
			if ("VOCAB".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getVocabList().size(); i++) {
					if(notesManager.getCurrentSubject().equals(notesManager.getVocabList().get(i).getSubject())) {
						noteSelectionMenu.addItem(notesManager.getVocabList().get(i).getTitle());
					}
				}
			} if ("IMAGE".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getImageList().size(); i++) {
					if(notesManager.getCurrentSubject().equals(notesManager.getImageList().get(i).getSubject())) {
						noteSelectionMenu.addItem(notesManager.getImageList().get(i).getTitle());
					}
				}
			} if ("MISC".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getMiscList().size(); i++) {
					if(notesManager.getCurrentSubject().equals(notesManager.getMiscList().get(i).getSubject())) {
						noteSelectionMenu.addItem(notesManager.getMiscList().get(i).getTitle());
					}
				}
			} if ("EXAMPLE".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getTextExampleList().size(); i++) {
					if(notesManager.getCurrentSubject().equals(notesManager.getTextExampleList().get(i).getSubject())) {
						noteSelectionMenu.addItem(notesManager.getTextExampleList().get(i).getTitle());
					}
				}
			}
		}
		
		/**
		 * updates display for current note type
		 */
		public void updateNoteTypeDisplay() {
			currentNoteType = noteTypeMenu.getSelectedItem().toString();
			inputPanel.updateContentInput();
			displayPanel.updateDisplayedSetup();
			updateNotesSelection();
		}
		
	}
	
	/**
	 * panel to input information for new notes
	 * 
	 * @author Isaiah Benzoni
	 */
	private class InputPanel extends JPanel implements ActionListener {
		
		/** serial version UID */
		private static final long serialVersionUID = 1L;
		/** input label */
		private JLabel inputLabel;
		/** panel to set up layout for image input */
		private JPanel imageInputLayout;
		/** vocab input panel */
		private JPanel vocabInputLayout;
		/** misc input panel */
		private JPanel miscInputLayout;
		/** text example input panel */
		private JPanel textExampleInputLayout;
		
		/** title text input area */
		private JTextArea titleTextInput;
		/** title input label */
		private JLabel titleInputLabel;
		/** title text input scroll */
		private JScrollPane titleTextInputScroll;
		
		/** image path input area */
		private JTextArea imagePath;
		/** image file button for input */
		private JButton imageFileButton;
		/** image path scroll */
		private JScrollPane imagePathScroll;
		
		/** definition input */
		private JTextArea definitionTextInput;
		/** additional info input */
		private JTextArea additionalInfoTextInput;
		/** definition label */
		private JLabel definitionInputLabel;
		/** additional info label */
		private JLabel additionalInfoInputLabel;
		/** definition input scroll */
		private JScrollPane definitionTextInputScroll;
		/** additional info input scroll */
		private JScrollPane additionalInfoTextInputScroll;
		/** clear additional info button */
		private JButton clearAdditionalInfoButton;
		
		/** contents input */
		private JTextArea contentsTextInput;
		/** contents input label */
		private JLabel contentsInputLabel;
		/** contents input scroll */
		private JScrollPane contentsTextInputScroll;
		
		/** preface input */
		private JTextArea prefaceTextInput;
		/** example contents input */
		private JTextArea exampleContentsTextInput;
		/** preface label */
		private JLabel prefaceInputLabel;
		/** example contents label */
		private JLabel exampleContentsInputLabel;
		/** preface input scroll */
		private JScrollPane prefaceTextInputScroll;
		/** example contents scroll */
		private JScrollPane exampleContentsTextInputScroll;
		
		/** confirm button */
		private JButton confirmButton;
		
		/** edit or new toggle */
		private JComboBox<String> editOrNewToggle;
		
		/** header and toggle panel */
		private JPanel headerAndToggle;
		
		/** panel to hold space, helps orient other pieces correctly */
		private JPanel placeHolder;
		
		/** image file */
		private File imageFile;
		
		/**
		 * sets initial field values
		 */
		public InputPanel() {
			
			placeHolder = new JPanel();
			
			this.setLayout(new BorderLayout());
			headerAndToggle = new JPanel(new GridLayout(1, 2));
			inputLabel = new JLabel("Content Input");
			imageInputLayout = new JPanel(new GridLayout(0, 2, 5, 5));
			vocabInputLayout = new JPanel(new GridLayout(0, 2, 5, 5));
			miscInputLayout = new JPanel(new GridLayout(0, 2, 5, 5));
			textExampleInputLayout = new JPanel(new GridLayout(0, 2, 5, 5));
			
			editOrNewToggle = new JComboBox<String>();
			editOrNewToggle.addItem("NEW");
			editOrNewToggle.addItem("EDIT");
			headerAndToggle.add(inputLabel);
			headerAndToggle.add(editOrNewToggle);
			
			titleTextInput = new JTextArea();
			titleTextInput.setPreferredSize(new Dimension(300, 110));
			titleTextInput.setBorder(BorderFactory.createLineBorder(Color.black));
			titleInputLabel = new JLabel("title input");
			confirmButton = new JButton("confirm input");
			titleTextInput.setLineWrap(true);
			titleTextInput.setWrapStyleWord(true);
			titleTextInputScroll = new JScrollPane(titleTextInput);
			
			imagePath = new JTextArea("file path: undetermined");
			imagePath.setBorder(BorderFactory.createLineBorder(Color.black));
			imagePath.setLineWrap(true);
			imagePath.setWrapStyleWord(true);
			imagePath.setHighlighter(null);
			imagePath.setEditable(false);
			imagePathScroll = new JScrollPane(imagePath);
			imagePathScroll.setPreferredSize(new Dimension(100, 25));
			imageFileButton = new JButton("image file");
			imageInputLayout.add(imageFileButton);
			imageInputLayout.add(imagePathScroll);
			imageInputLayout.setName("imageInputLayout");
			
			clearAdditionalInfoButton = new JButton("clear additional info");
			definitionInputLabel = new JLabel("definition/header input");
			additionalInfoInputLabel = new JLabel("additional info input");
			definitionTextInput = new JTextArea(5, 20);
			additionalInfoTextInput = new JTextArea(5, 20);
			definitionTextInput.setBorder(BorderFactory.createLineBorder(Color.black));
			additionalInfoTextInput.setBorder(BorderFactory.createLineBorder(Color.black));
			definitionTextInput.setLineWrap(true);
			additionalInfoTextInput.setLineWrap(true);
			definitionTextInput.setWrapStyleWord(true);
			additionalInfoTextInput.setWrapStyleWord(true);
			additionalInfoTextInputScroll = new JScrollPane(additionalInfoTextInput);
			definitionTextInputScroll = new JScrollPane(definitionTextInput);
			vocabInputLayout.add(definitionInputLabel);
			vocabInputLayout.add(additionalInfoInputLabel);
			vocabInputLayout.add(definitionTextInputScroll);
			vocabInputLayout.add(additionalInfoTextInputScroll);
			vocabInputLayout.setName("vocabInputLayout");
			
			contentsInputLabel = new JLabel("contents input");
			contentsTextInput = new JTextArea(5, 20);
			contentsTextInput.setBorder(BorderFactory.createLineBorder(Color.black));
			contentsTextInput.setLineWrap(true);
			contentsTextInput.setWrapStyleWord(true);
			contentsTextInputScroll = new JScrollPane(contentsTextInput);
			miscInputLayout.add(contentsInputLabel);
			miscInputLayout.add(new JPanel());
			miscInputLayout.add(contentsTextInputScroll);
			miscInputLayout.add(new JPanel());
			miscInputLayout.setName("miscInputLayout");
			
			prefaceInputLabel = new JLabel("preface input");
			exampleContentsInputLabel = new JLabel("example contents input");
			prefaceTextInput = new JTextArea(5, 20);
			exampleContentsTextInput = new JTextArea(5, 20);
			prefaceTextInput.setBorder(BorderFactory.createLineBorder(Color.black));
			exampleContentsTextInput.setBorder(BorderFactory.createLineBorder(Color.black));
			prefaceTextInput.setLineWrap(true);
			exampleContentsTextInput.setLineWrap(true);
			prefaceTextInput.setWrapStyleWord(true);
			exampleContentsTextInput.setWrapStyleWord(true);
			prefaceTextInputScroll = new JScrollPane(prefaceTextInput);
			exampleContentsTextInputScroll = new JScrollPane(exampleContentsTextInput);
			textExampleInputLayout.add(exampleContentsInputLabel);
			textExampleInputLayout.add(prefaceInputLabel);
			textExampleInputLayout.add(exampleContentsTextInputScroll);
			textExampleInputLayout.add(prefaceTextInputScroll);
			textExampleInputLayout.setName("textInputLayout");
			textExampleInputLayout.add(titleInputLabel);
			textExampleInputLayout.add(placeHolder);
			textExampleInputLayout.add(titleTextInputScroll);
			textExampleInputLayout.add(confirmButton);
			
			confirmButton.addActionListener(this);
			imageFileButton.addActionListener(this);
			clearAdditionalInfoButton.addActionListener(this);
			
			this.add(headerAndToggle, BorderLayout.NORTH);
			this.add(textExampleInputLayout, BorderLayout.SOUTH);
			
		}
		
		/**
		 * update content input section
		 */
		public void updateContentInput() {
			
			BorderLayout layout = (BorderLayout) this.getLayout();
			this.remove(layout.getLayoutComponent(BorderLayout.SOUTH));
			if ("VOCAB".equals(currentNoteType)) {
				vocabInputLayout.add(titleInputLabel);
				vocabInputLayout.add(placeHolder);
				vocabInputLayout.add(titleTextInputScroll);
				vocabInputLayout.add(confirmButton);
				vocabInputLayout.add(clearAdditionalInfoButton);
				this.add(vocabInputLayout, BorderLayout.SOUTH);
			}
			if ("IMAGE".equals(currentNoteType)) {
				imageInputLayout.add(titleInputLabel);
				imageInputLayout.add(placeHolder);
				imageInputLayout.add(titleTextInputScroll);
				imageInputLayout.add(confirmButton);
				this.add(imageInputLayout, BorderLayout.SOUTH);
			}
			if ("MISC".equals(currentNoteType)) {
				miscInputLayout.add(confirmButton);
				this.add(miscInputLayout, BorderLayout.SOUTH);
			}
			if ("EXAMPLE".equals(currentNoteType)) {
				textExampleInputLayout.add(titleInputLabel);
				textExampleInputLayout.add(placeHolder);
				textExampleInputLayout.add(titleTextInputScroll);
				textExampleInputLayout.add(confirmButton);
				this.add(textExampleInputLayout, BorderLayout.SOUTH);
			}
			this.revalidate();
			this.repaint();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == confirmButton) {
				if ("NEW".equals(editOrNewToggle.getSelectedItem())) {
					if ("VOCAB".equals(currentNoteType)) {
						try {
							notesManager.addToVocabList(titleTextInput.getText(), definitionTextInput.getText());
							for (int i = 0; i < notesManager.getVocabList().size(); i++) {
								if (titleTextInput.getText().equals(notesManager.getVocabList().get(i).getTitle()) && !"".equals(additionalInfoTextInput.getText())) {
									if (notesManager.getCurrentSubject().equals(notesManager.getVocabList().get(i).getSubject())) {	
										notesManager.getVocabList().get(i).addAdditionalInfo(additionalInfoTextInput.getText());
									}
								}
							}
							titleTextInput.setText("");
							definitionTextInput.setText("");
							additionalInfoTextInput.setText("");
						} catch (IllegalArgumentException iae) {
							JOptionPane.showMessageDialog(NotesGUI.this, "requires subject, title, definition");
						}
					} else if ("MISC".equals(currentNoteType)) {
						try {
							notesManager.addToMiscList(contentsTextInput.getText());
							contentsTextInput.setText("");
						} catch (IllegalArgumentException iae) {
							JOptionPane.showMessageDialog(NotesGUI.this, "requires subject, contents");
						}
					} else if ("EXAMPLE".equals(currentNoteType)) {
						try {
							notesManager.addToTextExampleList(titleTextInput.getText(), prefaceTextInput.getText(), exampleContentsTextInput.getText());
							titleTextInput.setText("");
							prefaceTextInput.setText("");
							exampleContentsTextInput.setText("");
						} catch (IllegalArgumentException iae) {
							JOptionPane.showMessageDialog(NotesGUI.this, "requires subject, title, example contents");
						}
					} else if ("IMAGE".equals(currentNoteType)) {
						try {
							notesManager.addToImageList(titleTextInput.getText(), imageFile);
							titleTextInput.setText("");
							imagePath.setText("file path: undetermined");
							imageFile = null;
						} catch (IllegalArgumentException iae) {
							JOptionPane.showMessageDialog(NotesGUI.this, "requires subject, title, image");
						}
					}
					noteTypesAndSelectionPanel.updateCurrentNote();
					noteTypesAndSelectionPanel.updateNotesSelection();
				} else {
					int choice = JOptionPane.showConfirmDialog(NotesGUI.this, "Confirm edit?");
					if (choice == 0) {
						if ("VOCAB".equals(currentNoteType)) {
							for (int i = 0; i < notesManager.getVocabList().size(); i++) {
								if (currentNoteName.equals(notesManager.getVocabList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getVocabList().get(i).getSubject())) {
									if (!"".equals(titleTextInput.getText())) {
										notesManager.getVocabList().get(i).setTitle(titleTextInput.getText());
										noteTypesAndSelectionPanel.updateNotesSelection();
									} if (!"".equals(definitionTextInput.getText())) {
										notesManager.getVocabList().get(i).setDefinition(definitionTextInput.getText());
									} if (!"".equals(additionalInfoTextInput.getText())) {
										notesManager.getVocabList().get(i).addAdditionalInfo(additionalInfoTextInput.getText());
									}
								}
							}
							titleTextInput.setText("");
							definitionTextInput.setText("");
							additionalInfoTextInput.setText("");
						} else if ("MISC".equals(currentNoteType)) {
							for (int i = 0; i < notesManager.getMiscList().size(); i++) {
								if (currentNoteName.equals(notesManager.getMiscList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getMiscList().get(i).getSubject())) {
									if (!"".equals(contentsTextInput.getText())) {
										notesManager.getMiscList().get(i).setContents(contentsTextInput.getText());
									}
								}
							}
							contentsTextInput.setText("");
						} else if ("EXAMPLE".equals(currentNoteType)) {
							for (int i = 0; i < notesManager.getTextExampleList().size(); i++) {
								if (currentNoteName.equals(notesManager.getTextExampleList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getTextExampleList().get(i).getSubject())) {
									if (!"".equals(titleTextInput.getText())) {
										notesManager.getTextExampleList().get(i).setTitle(titleTextInput.getText());
										noteTypesAndSelectionPanel.updateNotesSelection();
									}
									if (!"".equals(prefaceTextInput.getText())) {
										notesManager.getTextExampleList().get(i).setPreface(prefaceTextInput.getText());
									}
									if (!"".equals(exampleContentsTextInput.getText())) {
										notesManager.getTextExampleList().get(i).setContents(exampleContentsTextInput.getText());
									} 
								}
							}
							titleTextInput.setText("");
							prefaceTextInput.setText("");
							exampleContentsTextInput.setText("");
						} else if ("IMAGE".equals(currentNoteType)) {
							for (int i = 0; i < notesManager.getImageList().size(); i++) {
								if (currentNoteName.equals(notesManager.getImageList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getImageList().get(i).getSubject())) {
									if (!"".equals(titleTextInput.getText())) {
										notesManager.getImageList().get(i).setTitle(titleTextInput.getText());
										noteTypesAndSelectionPanel.updateNotesSelection();
									} if (!(imageFile == null)) {
										notesManager.getImageList().get(i).setImage(imageFile);
									}
								}
							}
							titleTextInput.setText("");
							imagePath.setText("file path: undetermined");
						}
						noteTypesAndSelectionPanel.updateCurrentNote();
						//noteTypesAndSelectionPanel.updateNotesSelection();
					}
				}
			} else if (e.getSource() == imageFileButton) {
				JFileChooser jFileChooser = new JFileChooser("C:");//navigate to /Users/ifben/Documents/images
				int returnVal = Integer.MIN_VALUE;
				returnVal = jFileChooser.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					imageFile = jFileChooser.getSelectedFile();
					imagePath.setText("file path: " + imageFile.getAbsolutePath());
				}
			} else {
				int choice = JOptionPane.showConfirmDialog(NotesGUI.this, "Confirm additional info removal?");
				if (choice == 0) {
					for (int i = 0; i < notesManager.getVocabList().size(); i++) {
						if (currentNoteName.equals(notesManager.getVocabList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getVocabList().get(i).getSubject())) {
							while(!(notesManager.getVocabList().get(i).getAdditionalInfo()[0] == null)) {
								notesManager.getVocabList().get(i).removeAdditionalInfo(0);
							}
						}
					}
				}
			}
			//System.out.println("here");
			displayPanel.updateDisplayedInfo();
			
		}
		
		/**
		 * removes current note from list
		 */
		public void removeCurrentNote() {
			
			if ("VOCAB".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getVocabList().size(); i++) {
					if (currentNoteName.equals(notesManager.getVocabList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getVocabList().get(i).getSubject())) {
						notesManager.getVocabList().remove(notesManager.getVocabList().get(i));
					}
				}
			} else if ("MISC".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getMiscList().size(); i++) {
					if (currentNoteName.equals(notesManager.getMiscList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getMiscList().get(i).getSubject())) {
						notesManager.getMiscList().remove(notesManager.getMiscList().get(i));
					}
				}
				notesManager.miscListRemoveUpdate();
			} else if ("EXAMPLE".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getTextExampleList().size(); i++) {
					if (currentNoteName.equals(notesManager.getTextExampleList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getTextExampleList().get(i).getSubject())) {
						notesManager.getTextExampleList().remove(notesManager.getTextExampleList().get(i));
					}
				}
			} else if ("IMAGE".equals(currentNoteType)) {
				for (int i = 0; i < notesManager.getImageList().size(); i++) {
					if (currentNoteName.equals(notesManager.getImageList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getImageList().get(i).getSubject())) {
						notesManager.getImageList().remove(notesManager.getImageList().get(i));
					}
				}
			}
			noteTypesAndSelectionPanel.updateCurrentNote();
			noteTypesAndSelectionPanel.updateNotesSelection();
			
		}
		
	}
	
	/**
	 * panel for displaying info
	 * 
	 * @author Isaiah Benzoni
	 */
	public class DisplayPanel extends JPanel{
		
		/** serial version UID */
		private static final long serialVersionUID = 1L;
		
		/** title display area */
		private JTextArea titleDisplay;
		/** content display area one */
		private JTextArea contentDisplay1;
		/** content display area two */
		private JTextArea contentDisplay2;
		/** title display scroll */
		private JScrollPane titleDisplayScroll;
		/** scroll one */
		private JScrollPane JScrollPane1;
		/**scroll two  */
		private JScrollPane JScrollPane2;
		/** title label */
		private JLabel titleLabel;
		/** additional text label */
		private JLabel additionalTextLabel;
		/** main text label */
		private JLabel mainTextLabel;

		/** image holding label */
		private JLabel imageHoldingComponent;
		/** image icon */
		private ImageIcon icon;
		
		/**
		 * sets up display
		 */
		public DisplayPanel() {
			
			this.setLayout(new GridBagLayout());
			titleDisplay = new JTextArea();
			titleDisplay.setPreferredSize(new Dimension(350, 20));
			titleDisplayScroll = new JScrollPane(titleDisplay);
			contentDisplay1 = new JTextArea(35, 50);
			contentDisplay2 = new JTextArea(35, 50);
			JScrollPane1 = new JScrollPane(contentDisplay1);
			JScrollPane2 = new JScrollPane(contentDisplay2);
			
			titleDisplay.setEditable(false);
			titleDisplay.setHighlighter(null);
			titleDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
			contentDisplay1.setEditable(false);
			contentDisplay1.setBorder(BorderFactory.createLineBorder(Color.black));
			contentDisplay1.setLineWrap(true);
			contentDisplay1.setWrapStyleWord(true);
			contentDisplay2.setEditable(false);
			contentDisplay2.setBorder(BorderFactory.createLineBorder(Color.black));
			contentDisplay2.setLineWrap(true);
			contentDisplay2.setWrapStyleWord(true);
			
			titleLabel = new JLabel("Title: ");
			additionalTextLabel = new JLabel("preface");
			mainTextLabel = new JLabel("example contents");
			
			imageHoldingComponent = new JLabel();//default is flow layout
			
			GridBagConstraints constraints = new GridBagConstraints();
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(titleLabel, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 1;
			constraints.gridy = 0;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(titleDisplayScroll, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 0;
			constraints.gridy = 1;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(additionalTextLabel, constraints);
			
			constraints = new GridBagConstraints();
			constraints.gridx = 1;
			constraints.gridy = 1;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(mainTextLabel, constraints);
			
			constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 5, 5, 5);
			constraints.gridx = 0;
			constraints.gridy = 2;
			constraints.weightx = 1;
			constraints.weighty = 1;
			this.add(JScrollPane1, constraints);
			
			constraints = new GridBagConstraints();
			constraints.insets = new Insets(5, 5, 5, 5);
			constraints.gridx = 1;
			constraints.gridy = 2;
			constraints.weightx = 1;
			constraints.weighty = 1;
			constraints.gridwidth = 2;
			constraints.weighty = 2;
			this.add(JScrollPane2, constraints);
			
		}
		
		/**
		 * updates display setup
		 */
		public void updateDisplayedSetup() {
			
			GridBagLayout layout = (GridBagLayout) this.getLayout();
			layout.removeLayoutComponent(JScrollPane2);
			layout.removeLayoutComponent(imageHoldingComponent);
			imageHoldingComponent.setVisible(false);
			JScrollPane2.setVisible(false);
			if ("VOCAB".equals(currentNoteType)) {
				additionalTextLabel.setText("additional info");
				mainTextLabel.setText("definition/header");
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.insets = new Insets(5, 5, 5, 5);
				constraints.gridx = 1;
				constraints.gridy = 2;
				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.gridwidth = 2;
				constraints.weighty = 2;
				this.add(JScrollPane2, constraints);
				JScrollPane2.setVisible(true);
			}
			if ("IMAGE".equals(currentNoteType)) {
				additionalTextLabel.setText("-");
				mainTextLabel.setText("-");
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.insets = new Insets(5, 5, 5, 5);
				constraints.gridx = 1;
				constraints.gridy = 2;
				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.gridwidth = 2;
				constraints.weighty = 2;
				this.add(imageHoldingComponent, constraints);
				imageHoldingComponent.setVisible(true);
			}
			if ("MISC".equals(currentNoteType)) {
				additionalTextLabel.setText("-");
				mainTextLabel.setText("content");
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.insets = new Insets(5, 5, 5, 5);
				constraints.gridx = 1;
				constraints.gridy = 2;
				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.gridwidth = 2;
				constraints.weighty = 2;
				this.add(JScrollPane2, constraints);
				JScrollPane2.setVisible(true);
			}
			if ("EXAMPLE".equals(currentNoteType)) {
				additionalTextLabel.setText("preface");
				mainTextLabel.setText("example content");
				GridBagConstraints constraints = new GridBagConstraints();
				constraints.insets = new Insets(5, 5, 5, 5);
				constraints.gridx = 1;
				constraints.gridy = 2;
				constraints.weightx = 1;
				constraints.weighty = 1;
				constraints.gridwidth = 2;
				constraints.weighty = 2;
				this.add(JScrollPane2, constraints);
				JScrollPane2.setVisible(true);
			}
			this.revalidate();
			this.repaint();
			
		}
		
		/**
		 * updates diaplay info
		 */
		public void updateDisplayedInfo() {
			
			titleDisplay.setText("");
			contentDisplay1.setText("");
			contentDisplay2.setText("");
			if ("IMAGE".equals(currentNoteType)) {
				if (!"".equals(currentNoteName)) {
					for (int i = 0; i < notesManager.getImageList().size(); i++) {
						if (currentNoteName.equals(notesManager.getImageList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getImageList().get(i).getSubject())) {
							double maxWidth = screenWidth / 2;
							double maxHeight = screenHeight / 2;
							double heightRatio;
							double widthRatio;
							double useRatio;
							double imageHeight = notesManager.getImageList().get(i).getImage().getHeight();
							double imageWidth = notesManager.getImageList().get(i).getImage().getWidth();
							icon = new ImageIcon(notesManager.getImageList().get(i).getImage());
							heightRatio = maxHeight / imageHeight;
							widthRatio = maxWidth / imageWidth;
							if (heightRatio < widthRatio) {
								useRatio = heightRatio;
							} else if (widthRatio < heightRatio) {
								useRatio = widthRatio;
							} else {
								useRatio = heightRatio;
							}
							Image image = icon.getImage();
							Image scaledImage = image.getScaledInstance((int)(imageWidth * useRatio), (int)(imageHeight * useRatio), java.awt.Image.SCALE_SMOOTH);
							icon = new ImageIcon(scaledImage);
							imageHoldingComponent.setIcon(icon);
							titleDisplay.setText(notesManager.getImageList().get(i).getTitle());
						}
					}
				} else {
					icon = new ImageIcon();
					imageHoldingComponent.setIcon(null);
					titleDisplay.setText("");
				}
			} if ("VOCAB".equals(currentNoteType)) {
				if (!"".equals(currentNoteName)) {
					for (int i = 0; i < notesManager.getVocabList().size(); i++) {
						if (currentNoteName.equals(notesManager.getVocabList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getVocabList().get(i).getSubject())) {
							contentDisplay2.setText(notesManager.getVocabList().get(i).getDefinition());
							String addInfoString = "";
							for (int j = 0; j < notesManager.getVocabList().get(i).getAdditionalInfo().length; j++) {
								if (!(notesManager.getVocabList().get(i).getAdditionalInfo()[j] == null)) {
									addInfoString += notesManager.getVocabList().get(i).getAdditionalInfo()[j] + "\n\n";
								}
							}
							contentDisplay1.setText(addInfoString);
							titleDisplay.setText(notesManager.getVocabList().get(i).getTitle());
						}
					}
				} else {
					titleDisplay.setText("");
					contentDisplay1.setText("");
					contentDisplay2.setText("");
				}
			} if ("EXAMPLE".equals(currentNoteType)) {
				if (!"".equals(currentNoteName)) {
					for (int i = 0; i < notesManager.getTextExampleList().size(); i++) {
						if (currentNoteName.equals(notesManager.getTextExampleList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getTextExampleList().get(i).getSubject())) {
							titleDisplay.setText(notesManager.getTextExampleList().get(i).getTitle());
							contentDisplay1.setText(notesManager.getTextExampleList().get(i).getPreface());
							contentDisplay2.setText(notesManager.getTextExampleList().get(i).getContents());
						}
					}
				} else {
					titleDisplay.setText("");
					contentDisplay1.setText("");
					contentDisplay2.setText("");
				}
			} if ("MISC".equals(currentNoteType)) {
				if (!"".equals(currentNoteName)) {
					for (int i = 0; i < notesManager.getMiscList().size(); i++) {
						if (currentNoteName.equals(notesManager.getMiscList().get(i).getTitle()) && notesManager.getCurrentSubject().equals(notesManager.getMiscList().get(i).getSubject())) {
							titleDisplay.setText(notesManager.getMiscList().get(i).getTitle());
							contentDisplay2.setText(notesManager.getMiscList().get(i).getContents());
						}
					}
				} else {
					titleDisplay.setText("");
					contentDisplay1.setText("");
					contentDisplay2.setText("");
				}
			}
			this.revalidate();
			this.repaint();
			this.repaint();
			
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		//System.out.println(fileName);
		if (fileName != null) {
			notesManager.saveNotes(new File(fileName));
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
