//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant

package sample;

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class Main extends Application
{
    int y = 4;
    int score = -8;
    Timeline timeline = null;
    long time = 2000;
    Button arr[][];
    Label scoreLabel = new Label("Score: 0");
    Button menu = new Button();
    VBox root = new VBox();
    GridPane grid = new GridPane();
    boolean move = true;

	public void start(Stage primaryStage) throws Exception
	{
		Label title = new Label("Don't Drop D anDa");
		title.setId("title");
		HBox top = new HBox();
		top.setId("top");
		
		scoreLabel.setId("label");
		menu.setId("menu");
		arr = new Button[8][8];
		
		for(int i = 0; i < 8; i++)
			for(int j = 0; j < 8; j++)
			{
				arr[i][j] = new Button();
				arr[i][j].setId("white");
				arr[i][j].setPrefHeight(40);
				arr[i][j].setPrefWidth(40);
				arr[i][j].setPadding(new Insets(0, 0, 0, 0));
				grid.add(arr[i][j], j, i);
			}
		arr[7][4].setId("bowl");
		
		Play(scoreLabel);
		Move();
		
		menu.setOnAction(e -> Menu());
        
		title.setPrefWidth(320);
		title.setMinHeight(50);
		title.setAlignment(Pos.CENTER);
		scoreLabel.setPrefWidth(100);
		HBox.setMargin(scoreLabel, new Insets(10, 0, 0, 20));
		menu.setPrefHeight(40);
		menu.setPrefWidth(40);
		HBox.setMargin(menu, new Insets(5, 0, 5, 155));
		
		top.getChildren().addAll(scoreLabel, menu);
		root.getChildren().addAll(title, top, grid);
        Scene scene = new Scene(root, 308, 413);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Don't Drop D anDa");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	void Play(Label scoreLabel)
	{
		timeline = new Timeline(new KeyFrame( Duration.millis(time), ae ->
		{
			score++;
			
			int i, j, egg = 0;
			
			for(i = 0; i < 8; i++)
				if(arr[6][i].getId().equals("egg"))
				{
					egg = i;
					break;
				}
            for(i = 5; i >= 0; i--)
            	for(j = 0; j < 8; j++)
            		arr[i+1][j].setId(arr[i][j].getId());
            
            FillGreen();
            
            if(!(y == egg || score < 0))
            {
            	score--;
            	arr[7][egg].setId("egg_break");
            	Finish();
            }
            
            else if(score % 5 == 0 && time > 1000)
            {
            	timeline.stop();
            	time -= 50;
            	Play(scoreLabel);
            }
            
            if(score < 0)
            	scoreLabel.setText("Score: 0");
            else
            	scoreLabel.setText("Score: " + Integer.toString(score + 1));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
	}
	
	void FillGreen()
	{
		Random rand = new Random();
		int i;
		
		for(i = 0; i < 8; i++)
			arr[0][i].setId("white");
		i = rand.nextInt(8);
		arr[0][i].setId("egg");
	}
	
	void Move()
	{
			for(int i = 0; i < 8; i++)
				for(int j = 0; j < 8; j++)
			        arr[i][j].setOnKeyPressed(e ->
			        {
			        	if(move)
			        	{
				        	arr[7][y].setId("white");
				        	String temp = e.getCode().toString();
				            switch (temp)
				            {
				                case "LEFT":	if(y!=0)
				                					y--;
				                                break;
				                case "RIGHT": 	if(y!=7)
				                					y++;
				                                break;
				                case "ESCAPE":
				                case "SPACE":	Menu();
				                				break;
				            }
				            arr[7][y].setId("bowl");
			        	}
			        });
			menu.setOnKeyPressed(e ->
			{
				if(move)
				{
					arr[7][y].setId("white");
		        	String temp = e.getCode().toString();
		            switch (temp)
		            {
		                case "LEFT":	if(y!=0)
		                					y--;
		                                break;
		                case "RIGHT": 	if(y!=7)
		                					y++;
		                                break;
		                case "ESCAPE":	Menu();
		                				break;
		            }
		            arr[7][y].setId("bowl");
				}
			});
	}

	void Menu()
	{
		if(timeline.getStatus().toString().equals("RUNNING"))
		{
			timeline.pause();
			root.getChildren().remove(grid);
			move = false;
			Stage menuStage = new Stage();
			VBox menuVBox = new VBox();
			menuVBox.setId("top");
			Label paused = new Label("PAUSED");
			paused.setId("paused");
			Button resume = new Button();
			resume.setId("resume");
			Button restart = new Button();
			restart.setId("restart");
			Button exit = new Button();
			exit.setId("exit");
			
			resume.setOnAction(e ->
			{
				menuStage.close();
				root.getChildren().add(grid);
				timeline.play();
				move = true;
			});
			
			resume.setOnKeyPressed(e ->
			{
				if(e.getCode().toString() == "ESCAPE")
				{
					menuStage.close();
					root.getChildren().add(grid);
					timeline.play();
					move = true;
				}
			});
			
			restart.setOnAction(e ->
			{
				menuStage.close();
				root.getChildren().add(grid);
	        	y = 4;
	        	score = -8;
	        	time = 2000;
	        	scoreLabel.setText("Score: 0");
	        	move = true;
	        	

	        	for(int i = 0; i < 8; i++)
	    			for(int j = 0; j < 8; j++)
	    				arr[i][j].setId("white");
	    		arr[7][4].setId("bowl");
	    		
	    		Play(scoreLabel);
	    		Move();
			});
			
			restart.setOnKeyPressed(e ->
			{
				if(e.getCode().toString() == "ESCAPE")
				{
					menuStage.close();
					root.getChildren().add(grid);
					timeline.play();
					move = true;
				}
			});
			
			exit.setOnAction(e -> System.exit(0));
			
			exit.setOnKeyPressed(e ->
			{
				if(e.getCode().toString() == "ESCAPE")
				{
					menuStage.close();
					root.getChildren().add(grid);
					timeline.play();
					move = true;
				}
			});
			
			menuStage.setOnCloseRequest(e ->
	        {
	        	root.getChildren().add(grid);
				timeline.play();
				move = true;
	        });
			
			menuVBox.setAlignment(Pos.TOP_CENTER);
			VBox.setMargin(resume, new Insets(10, 0, 0, 0));
			VBox.setMargin(restart, new Insets(10, 0, 0, 0));
			VBox.setMargin(exit, new Insets(10, 0, 0, 0));
			
			menuVBox.getChildren().addAll(paused, resume, restart, exit);
			Scene menuScene = new Scene(menuVBox, 200, 300);
			menuScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			menuStage.setTitle("Paused");
			menuStage.setResizable(false);
			menuStage.setAlwaysOnTop(true);
	        menuStage.setScene(menuScene);
	        menuStage.show();
		}
	}
	
	void Finish()
	{
		timeline.stop();
		move = false;
		Stage finishStage = new Stage();
        VBox finishVBox = new VBox();
        finishVBox.setId("top");
        Label gameOver = new Label("DROPPED");
        Label finishScoreLabel = new Label("Score: 0");
        finishScoreLabel.setId("label");
        HBox finishHBox = new HBox();
        gameOver.setId("label");
        Button restart = new Button();
        restart.setId("restart");
        Button exit = new Button();
        exit.setId("exit");
        
        if(score >= 0)
        	finishScoreLabel.setText("Score: " + Integer.toString(score + 1));
        
        restart.setOnAction(e ->
        {
        	finishStage.close();
        	y = 4;
        	score = -8;
        	time = 2000;
        	scoreLabel.setText("Score: 0");
        	move = true;

        	for(int i = 0; i < 8; i++)
    			for(int j = 0; j < 8; j++)
    				arr[i][j].setId("white");
    		arr[7][4].setId("bowl");
    		
    		Play(scoreLabel);
    		Move();
        });
        
        exit.setOnAction(e -> System.exit(0));
        
        finishStage.setOnCloseRequest(e -> Finish());
        
        finishStage.setTitle("Game Over");
        gameOver.setPrefWidth(200);
        gameOver.setAlignment(Pos.CENTER);
        VBox.setMargin(gameOver, new Insets(10, 0, 0, 0));
        finishScoreLabel.setPrefWidth(200);
        finishScoreLabel.setAlignment(Pos.CENTER);

        restart.setPadding(new Insets(0, 0, 0, 0));
        HBox.setMargin(restart, new Insets(10, 0, 0, 25));
        exit.setPadding(new Insets(0, 0, 0, 0));
        HBox.setMargin(exit, new Insets(10, 0, 0, 50));
        
        finishHBox.getChildren().addAll(restart, exit);
        finishVBox.getChildren().addAll(gameOver, finishScoreLabel, finishHBox);
        Scene finishScene = new Scene(finishVBox, 200, 150);
        finishScene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        finishStage.setTitle("Dropped");
        finishStage.setResizable(false);
        finishStage.setAlwaysOnTop(true);
        finishStage.setScene(finishScene);
        finishStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }
}

//Created by Nishant Chaudhary
//https://github.com/ChaudharyNishant
