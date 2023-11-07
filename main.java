import javafx.application.Application;
import java.util.Random;                   // for random number
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.geometry.*;

public class main extends Application {
	public static void main(String args[]){
		launch(args);
	}

	Scene scene1, scene2;
	int numAttempts=0;

	Random random=new Random();

	int randInt=random.nextInt(50)+1;     // generating a random number within the range 1 to 50

	@Override
	public void start(Stage mainStage){
		mainStage.setTitle("Number Guessing Game");          // the window shows the name of the game as its title

		Label numLabel=new Label("Enter a number: ");        // a label that contains the text given in Label()

		TextField numberInput=new TextField();          // create an input field where the user can enter his/her number

		Button guess=new Button("Check");        // a check button for checking whether the entered number is the actual number

		Label attemptLabel=new Label();   // label displaying the number of attempts
		attemptLabel.setText("Attempts: "+numAttempts);       // displays the number of attempts made by the user

		guess.setOnAction(e -> {                      // actions to perform when the check button is clicked
			int errCode=checkNum(numberInput);

			if(numAttempts>9){
				gameOver();
				closeWindow(mainStage);
			}

			else{
				if(errCode==-2){
					errorScene();        // invalid input
				}

				else if(errCode<0){
					numberStatus(errCode);
					numAttempts++;
					attemptLabel.setText("Attempts: "+numAttempts);   // update the number of attempts
				}

				else if(errCode>0){
					numberStatus(errCode);
					numAttempts++;
					attemptLabel.setText("Attempts: "+numAttempts);
				}

				else{
					numberStatus(errCode);
					numAttempts++;
					attemptLabel.setText("Attempts: "+numAttempts);
					closeWindow(mainStage);
				}
			}
		});


		GridPane layout1=new GridPane();
		layout1.setPadding(new Insets(10, 10, 10, 10));     // setting the padding between the labels, input fields, buttons and the window border
		layout1.setVgap(8); // setting vertical padding between labels, input fields and buttons
		layout1.setHgap(10); // setting horizontal padding between labels, input fields and buttons

		// adding the labels, input fields and buttons to the layout
		layout1.setConstraints(numLabel, 0, 0);       // it's a grid, so we can position the labels, input fields and buttons based on the column value and row value(column value first, then the row value)
		layout1.setConstraints(numberInput, 1, 0);
		layout1.setConstraints(guess, 0, 1);
		layout1.setConstraints(attemptLabel, 3, 0);
		layout1.getChildren().addAll(numLabel, numberInput, guess, attemptLabel);

		// putting the layout into the scene with specified dimensions(width x height)
		scene1=new Scene(layout1, 500, 250);

		// setting the scene in the main window(the window that will be displayed) so that labels, input fields and buttons are visible to the user
		mainStage.setScene(scene1);

		mainStage.show();
	}

	private int checkNum(TextField numInp){
		try{
			// checking if the input is actually a number or a string
			int num=Integer.parseInt(numInp.getText()); // getText() gets the input given by the user

			if(num<randInt){
				return -1;
			}

			else if(num>randInt){
				return 1;
			}

			else{
				return 0;
			}
		}
		catch(NumberFormatException e){     // if the input is string
			return -2;
		}
	}

	private void errorScene(){        // error window which will be displayed if the user enters a string
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);    // this window will have high priority, as long as this window is open, user cannot do anything on the main window until he/she presses the OK button on this window and it cannot be resized
		window.setTitle("ERROR");
		window.setMinWidth(350);

		Label errLabel=new Label("INVALID INPUT! PLEASE ENTER AN INTEGER!");

		Button closeButton=new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox errLayout=new VBox(10);
		errLayout.setPadding(new Insets(10, 10, 10, 10));
		errLayout.getChildren().addAll(errLabel, closeButton);
		errLayout.setAlignment(Pos.CENTER);  // position the label and the button at the center of the window

		scene2=new Scene(errLayout);

		window.setScene(scene2);
		window.showAndWait();       // show the window and wait until the user gives his/her response
	}

	private void numberStatus(int code){     // this window is also just like the error window above, but it will display the status of user input(whether the number entered by the user is correct or not)
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Status");
		window.setMinWidth(350);

		Label statusLabel=new Label();

		if(code<0)
			statusLabel.setText("Your number is smaller than the actual number");
		else if(code>0)
			statusLabel.setText("Your number is greater than the actual number");
		else
			statusLabel.setText("HURRAY! You guessed the number.");

		Button closeButton=new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox statusLayout=new VBox(10);
		statusLayout.setPadding(new Insets(10, 10, 10, 10));
		statusLayout.getChildren().addAll(statusLabel, closeButton);
		statusLayout.setAlignment(Pos.CENTER);

		scene2=new Scene(statusLayout);

		window.setScene(scene2);
		window.showAndWait();
	}

	private void closeWindow(Stage mainWin){                 // close the window when the user enters the correct number
		Button closeButton=new Button("Close");

		closeButton.setOnAction(e -> mainWin.close());

		VBox closeLayout=new VBox(10);
		closeLayout.setAlignment(Pos.CENTER);
		closeLayout.getChildren().addAll(closeButton);

		scene2=new Scene(closeLayout, 500, 250);

		mainWin.setScene(scene2);
		mainWin.show();
	}

	private void gameOver(){            // when maximum attempts have been made by the user and no more attempts are possible then display this window
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Status");
		window.setMinWidth(350);

		Label statusLabel=new Label();
		statusLabel.setText("You have reached the maximum attempt limit.");

		Button closeButton=new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox statusLayout=new VBox(10);
		statusLayout.setPadding(new Insets(10, 10, 10, 10));
		statusLayout.getChildren().addAll(statusLabel, closeButton);
		statusLayout.setAlignment(Pos.CENTER);

		scene2=new Scene(statusLayout);

		window.setScene(scene2);
		window.showAndWait();
	}
}
