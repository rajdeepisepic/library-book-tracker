package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;


public class LibraryBookTrackerTwo extends Application 
{
	public static int totalBooks = 0;
    public static ArrayList<String> loanBook = new ArrayList<String>();
    public static ArrayList<String> loanAuthor = new ArrayList<String>();
    
	private static TextField userTitleStr = new TextField();
	private static TextField userAuthorStr = new TextField();
	private Button Enter = new Button("Enter");
	private Button checkBooks = new Button("Check total books loaned using this account");
	private Button checkLoans = new Button("Check the current books you have loaned");
	private Button saveUserData = new Button("Save Data In File");

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		int counter = 0;
		File file = new File("output.txt");    
		if (file.createNewFile())
    	{
    		counter = 2;
    	}
		if (!file.createNewFile() && counter == 0)
		{
			if (file.length() == 0)
        	{
        		file.delete();
        		counter = 1;
        	}
		}
    	if (!file.createNewFile() && counter == 0) {
            try 
            {
            	BufferedReader reader = new BufferedReader(new FileReader("output.txt")); 
            	String numBooks = reader.readLine();
            	totalBooks = Integer.parseInt(numBooks);
            	while(reader.readLine() != null)
            	{
            		String nextTitle = reader.readLine();
            		loanBook.add(nextTitle);
            		String nextAuthor = reader.readLine();
            		loanAuthor.add(nextAuthor);
            	}
            } catch (IOException e) {
            	e.printStackTrace();
            }
    	}
		Dialog<String> dialog1 = new Dialog<String>();
		dialog1.setTitle("Current Loans");
		ButtonType type1 = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog1.setContentText("You currently have " + loanBook.size() + " books loaned. These books are: ");
		dialog1.getDialogPane().getButtonTypes().add(type1);
		Dialog<String> dialog2 = new Dialog<String>();
		dialog2.setTitle("Success");
		ButtonType type2 = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog2.setContentText("Succesfully Added!");
		dialog2.getDialogPane().getButtonTypes().add(type2);
		GridPane pane = new GridPane();
		pane.setVgap(12);
		pane.setHgap(12);
		pane.add(new Label("Title"), 1/2, 5);
		pane.add(new Label("Author "), 0, 6);
		pane.add(userTitleStr, 1, 5);
		pane.add(Enter, 1, 7);
		pane.add(new Label("Library Book Tracker"), 0, 0);
		pane.add(userAuthorStr, 1, 6);
		VBox paneForRadioButtons = new VBox(20);
		paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));
		RadioButton returnBook = new RadioButton("Return a book");
	    RadioButton loanBook1 = new RadioButton("Loan a book");
		paneForRadioButtons.getChildren().addAll(returnBook, loanBook1);
		pane.add(paneForRadioButtons, 0, 1);
		ToggleGroup group = new ToggleGroup();
		returnBook.setToggleGroup(group);
		loanBook1.setToggleGroup(group);
		userTitleStr.setAlignment(Pos.BOTTOM_RIGHT);
		userAuthorStr.setAlignment(Pos.BOTTOM_RIGHT);
		GridPane.setHalignment(Enter, HPos.RIGHT);
		pane.add(checkBooks, 1, 0);
		pane.add(checkLoans, 2, 0);
		pane.add(saveUserData, 3, 0);
		GridPane.setHalignment(checkBooks, HPos.RIGHT);
		pane.setPadding(new Insets(10, 10, 10, 10));
		Enter.setOnAction(e -> {
			if (returnBook.isSelected()) {
				returnBook();
			   }
			if (loanBook1.isSelected()) {
				loanBook();
			   }
		});
		checkBooks.setOnAction(e -> {
			checkBooks();
		});
		checkLoans.setOnAction(e -> {
			checkLoans();
		});
		saveUserData.setOnAction(e -> {
			saveData();
		});
		Scene newScene = new Scene(pane);
		primaryStage.setTitle("Library Book Tracker"); 
		primaryStage.setScene(newScene); 
		primaryStage.show();
	}

	static void loanBook()
    {
		String userTitle = userTitleStr.getText();
        if (loanBook.contains(userTitle)) 
        {
        	Dialog<String> dialog = new Dialog<String>();
    		dialog.setTitle("Error");
    		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
    		dialog.setContentText("Sorry, you already have that book loaned.");
    		dialog.getDialogPane().getButtonTypes().add(type);
			dialog.showAndWait();
            return;
        }
        loanBook.add(userTitle);        
		String userAuthor = userAuthorStr.getText();
        loanAuthor.add(userAuthor);
        totalBooks += 1; 
      
    }
	
	static void returnBook()
    {
		String userTitle = userTitleStr.getText();
        if (!loanBook.contains(userTitle)) 
        {
        	Dialog<String> dialog = new Dialog<String>();
    		dialog.setTitle("Error");
    		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
    		dialog.setContentText("Sorry, you haven't loaned that book.");
    		dialog.getDialogPane().getButtonTypes().add(type);
			dialog.showAndWait();
            return;
        }       
        int indexOfBook = loanBook.indexOf(userTitle);
        loanBook.remove(indexOfBook);
        loanAuthor.remove(indexOfBook);
    }
	static void checkBooks()
    {
		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Total Books");
		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.setContentText("You have loaned a total of " + totalBooks + " books using this account.");
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
    }
    static void checkLoans()
    {
    	Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Current Loans");
		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.setContentText("You currently have " + loanBook.size() + " books currently loaned. These books are: \n" + loanBook);
		dialog.getDialogPane().getButtonTypes().add(type);
		dialog.showAndWait();
    }
    static void saveData()
    {
    	File obj = new File("output.txt");
        if(loanBook.isEmpty())
        {
        	Dialog<String> dialog = new Dialog<String>();
    		dialog.setTitle("Error");
    		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
    		dialog.setContentText("You don't have any loaned books currently.");
    		dialog.getDialogPane().getButtonTypes().add(type);
    		dialog.showAndWait();
    		return;
        }
        obj.delete();
        try {
        	BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        	writer.write(Integer.toString(totalBooks));
        	writer.write("\n");
        	writer.write("\n");
        	for (int i = 0; i < loanBook.size(); i++) {
        	    writer.write(loanBook.get(i) + "\n");
        	    writer.write(loanAuthor.get(i) + "\n");
        	}
        	writer.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
}