import javafx.application.Application;
import java.util.Random;                   // for random number
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

	int randInt=random.nextInt(50)+1;

	@Override
	public void start(Stage mainStage){
		mainStage.setTitle("Number Guessing Game");

		Label numLabel=new Label("Enter a number: ");

		TextField numberInput=new TextField();

		Button guess=new Button("Check");

		Label attemptLabel=new Label();   // displaying the number of attempts
		attemptLabel.setText("Attempts: "+numAttempts);

		guess.setOnAction(e -> {
			int errCode=checkNum(numberInput);

			if(numAttempts>10){
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
					attemptLabel.setText("Attempts: "+numAttempts);
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
		layout1.setPadding(new Insets(10, 10, 10, 10));
		layout1.setVgap(8);
		layout1.setHgap(10);

		layout1.setConstraints(numLabel, 0, 0);
		layout1.setConstraints(numberInput, 1, 0);
		layout1.setConstraints(guess, 0, 1);
		layout1.setConstraints(attemptLabel, 3, 0);

		layout1.getChildren().addAll(numLabel, numberInput, guess, attemptLabel);

		scene1=new Scene(layout1, 500, 250);

		mainStage.setScene(scene1);

		mainStage.show();
	}

	private int checkNum(TextField numInp){
		try{
			int num=Integer.parseInt(numInp.getText());

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
		catch(NumberFormatException e){
			return -2;
		}
	}

	private void errorScene(){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("ERROR");
		window.setMinWidth(250);

		Label errLabel=new Label("INVALID INPUT! PLEASE ENTER AN INTEGER!");

		Button closeButton=new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox errLayout=new VBox();
		errLayout.getChildren().addAll(errLabel, closeButton);
		errLayout.setAlignment(Pos.CENTER);

		scene2=new Scene(errLayout);

		window.setScene(scene2);
		window.showAndWait();
	}

	private void numberStatus(int code){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Status");
		window.setMinWidth(250);

		Label statusLabel=new Label();

		if(code<0)
			statusLabel.setText("Your number is smaller than the actual number");
		else if(code>0)
			statusLabel.setText("Your number is greater than the actual number");
		else
			statusLabel.setText("HURRAY! You guessed the number.");

		Button closeButton=new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox statusLayout=new VBox();
		statusLayout.getChildren().addAll(statusLabel, closeButton);
		statusLayout.setAlignment(Pos.CENTER);

		scene2=new Scene(statusLayout);

		window.setScene(scene2);
		window.showAndWait();
	}

	private void closeWindow(Stage mainWin){
		Button closeButton=new Button("Close");

		closeButton.setOnAction(e -> mainWin.close());

		VBox closeLayout=new VBox(10);
		closeLayout.setAlignment(Pos.CENTER);
		closeLayout.getChildren().addAll(closeButton);

		scene2=new Scene(closeLayout, 500, 250);

		mainWin.setScene(scene2);
		mainWin.show();
	}

	private void gameOver(){
		Stage window=new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Status");
		window.setMinWidth(250);

		Label statusLabel=new Label();
		statusLabel.setText("You have reached the maximum attempt limit.");

		Button closeButton=new Button("OK");
		closeButton.setOnAction(e -> window.close());

		VBox statusLayout=new VBox();
		statusLayout.getChildren().addAll(statusLabel, closeButton);
		statusLayout.setAlignment(Pos.CENTER);

		scene2=new Scene(statusLayout);

		window.setScene(scene2);
		window.showAndWait();
	}
}
