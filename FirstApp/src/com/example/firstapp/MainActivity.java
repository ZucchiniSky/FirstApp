package com.example.firstapp;

import java.util.Random;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private int diff;
	
	private Boolean ended = false;
	
	private int winner = 0;

	private class seekBarListener implements SeekBar.OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        	diff = progress;
        }

        public void onStartTrackingTouch(SeekBar seekBar) {}

        public void onStopTrackingTouch(SeekBar seekBar) {}

	}
	
	private int[][] tictac_board = {{0,0,0},{0,0,0},{0,0,0}};
	
	private ImageButton[][] buttons = new ImageButton[3][3];
	
	private TextView winText;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        winText = (TextView) findViewById(R.id.winText);
        SeekBar diffBar = (SeekBar) findViewById(R.id.seekBar1);
        diff = diffBar.getProgress();
        diffBar.setOnSeekBarChangeListener(new seekBarListener());
    	buttons[0][0] = (ImageButton) findViewById(R.id.ImageButton11);
    	buttons[0][1] = (ImageButton) findViewById(R.id.ImageButton12);
    	buttons[0][2] = (ImageButton) findViewById(R.id.ImageButton13);
    	buttons[1][0] = (ImageButton) findViewById(R.id.ImageButton21);
    	buttons[1][1] = (ImageButton) findViewById(R.id.ImageButton22);
    	buttons[1][2] = (ImageButton) findViewById(R.id.ImageButton23);
    	buttons[2][0] = (ImageButton) findViewById(R.id.ImageButton31);
    	buttons[2][1] = (ImageButton) findViewById(R.id.ImageButton32);
    	buttons[2][2] = (ImageButton) findViewById(R.id.ImageButton33);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void updateImages()
    {
    	for (int i = 0; i < 3; i++)
    	{
    		for (int j = 0; j < 3; j++)
    		{
    			if (tictac_board[i][j] == 0)
    			{
    				buttons[i][j].setImageResource(R.drawable.tictac_blank);
    			} else if (tictac_board[i][j] == 1)
    			{
    				buttons[i][j].setImageResource(R.drawable.tictac_x);
    			} else if (tictac_board[i][j] == 2)
    			{
    				buttons[i][j].setImageResource(R.drawable.tictac_o);
    			}
    		}
    	}
    	if (ended)
    	{
    		if (winner == 0)
    		{
    			winText.setText("Stalemate.\nClick New to play again!");
    		} else if (winner == 1)
    		{
    			winText.setText("You won!\nClick New to play again!");
    		} else if (winner == 2)
    		{
    			winText.setText("You lost.\nClick New to play again!");
    		}
    	} else {
    		winText.setText("");
    	}
    }
    
    // when new button is pressed
    public void new_game(View view) {
        for (int i = 0; i < 3; i++)
        {
        	for (int j = 0; j < 3; j++)
        	{
        		tictac_board[i][j] = 0;
        	}
        }
        winner = 0;
        ended = false;
        Random generator = new Random();
		int r;
		r = generator.nextInt(2);
		if (r == 0)
			makeMove();
        updateImages();
        if (r == 0)
        	winText.setText("Computer moves first.\nMake your move!");
        else
			winText.setText("Make your move!");
    }
    
    // when a button on the board is pressed
    public void board_click(View view) {
    	if (ended) return;
    	for (int i = 0; i < 3; i++)
    	{
    		for (int j = 0; j < 3; j++)
    		{
    			if (view.getId() == buttons[i][j].getId())
    			{
    				if (tictac_board[i][j] == 0)
    				{
    					tictac_board[i][j] = 1;
    					if (playerWon() == 1)
    					{
    						winner = 1;
    						ended = true;
    					}
    					if (boardFull())
    					{
    						ended = true;
    					}
    					makeMove();
    					if (playerWon() == 2)
    					{
    						winner = 2;
    						ended = true;
    					}
    					if (boardFull())
    					{
    						ended = true;
    					}
    					updateImages();
    				}
    				return;
    			}
    		}
    	}
    }
    
    // returns true if board is full
    // returns false if there is at least one open space on the board
    private Boolean boardFull()
    {
    	for (int i = 0; i < 3; i++)
    	{
    		for (int j = 0; j < 3; j++)
    		{
    			if (tictac_board[i][j] == 0)
    			{
    				return false;
    			}
    		}
    	}
    	return true;
    }
    
    // checks if a player has won
    private int playerWon()
    {
		if (createCoordsAndCheckForThree(0,0,1,0,1)) return 1;
		if (createCoordsAndCheckForThree(0,1,1,0,1)) return 1;
		if (createCoordsAndCheckForThree(0,2,1,0,1)) return 1;
		if (createCoordsAndCheckForThree(0,0,0,1,1)) return 1;
		if (createCoordsAndCheckForThree(1,0,0,1,1)) return 1;
		if (createCoordsAndCheckForThree(2,0,0,1,1)) return 1;
		if (createCoordsAndCheckForThree(0,0,1,1,1)) return 1;
		if (createCoordsAndCheckForThree(2,0,-1,1,1)) return 1;
		if (createCoordsAndCheckForThree(0,0,1,0,2)) return 2;
		if (createCoordsAndCheckForThree(0,1,1,0,2)) return 2;
		if (createCoordsAndCheckForThree(0,2,1,0,2)) return 2;
		if (createCoordsAndCheckForThree(0,0,0,1,2)) return 2;
		if (createCoordsAndCheckForThree(1,0,0,1,2)) return 2;
		if (createCoordsAndCheckForThree(2,0,0,1,2)) return 2;
		if (createCoordsAndCheckForThree(0,0,1,1,2)) return 2;
		if (createCoordsAndCheckForThree(2,0,-1,1,2)) return 2;
		return 0;
    }
    
    private Boolean createCoordsAndCheckForTwo(int x, int y, int x_Off, int y_Off, int player)
    {
    	int[] a = {x,y};
		int[] b = {x+x_Off,y+y_Off};
		int[] c = {x+x_Off*2,y+y_Off*2};
		int[] check = checkForTwo(a,b,c,player);
		if (check[0] != -1)
		{
			tictac_board[check[0]][check[1]] = 2;
			return true;
		}
		return false;
    }
    
    private Boolean createCoordsAndCheckForThree(int x, int y, int x_Off, int y_Off, int player)
    {
    	int[] a = {x,y};
		int[] b = {x+x_Off,y+y_Off};
		int[] c = {x+x_Off*2,y+y_Off*2};
		return checkForThree(a,b,c,player);
    }
    
    // check if two of the three board coordinates are filled with x
    // returns coordinate of the board not filled by x if so or {-1,-1}
    private int[] checkForTwo(int[] a, int[] b, int[] c, int x)
    {
    	int count = 0;
    	int[] remaining = new int[2];
    	if (tictac_board[a[0]][a[1]] == x)
    	{
    		count++;
    	} else {
    		remaining = a;
    	}
    	if (tictac_board[b[0]][b[1]] == x)
    	{
    		count++;
    	} else {
    		remaining = b;
    	}
    	if (tictac_board[c[0]][c[1]] == x)
    	{
    		count++;
    	} else {
    		remaining = c;
    	}
    	if (count > 1 && tictac_board[remaining[0]][remaining[1]] == 0)
    	{
    		return remaining;
    	}
    	remaining[0] = -1;
    	remaining[1] = -1;
    	return remaining;
    }
    
    // checks if all three coordinates are filled with x
    private Boolean checkForThree(int[] a, int[] b, int[] c, int x)
    {
    	int count = 0;
    	if (tictac_board[a[0]][a[1]] == x)
    	{
    		count++;
    	}
    	if (tictac_board[b[0]][b[1]] == x)
    	{
    		count++;
    	}
    	if (tictac_board[c[0]][c[1]] == x)
    	{
    		count++;
    	}
    	return count == 3;
    }
    
    private void makePriorityMove()
    {
    	if (tictac_board[1][1] == 0)
    	{
    		tictac_board[1][1] = 2;
    		return;
    	}
    	for (int i = 0; i < 3; i += 2)
    	{
    		for (int j = 0; j < 3; j += 2)
    		{
    			if (tictac_board[i][j] == 0)
    			{
    				tictac_board[i][j] = 2;
    				return;
    			}
    		}
    	}
    	makeRandomMove();
    }
    
    private void makeRandomMove()
    {
    	Random generator = new Random();
		int r;
		do
		{
			r = generator.nextInt(9);
		} while (tictac_board[r/3][r%3] != 0);
		tictac_board[r/3][r%3] = 2;
		return;
    }
    
    // makes opponent's move
    private void makeMove()
    {
    	if (ended)
    	{
    		return;
    	}
    	if (diff == 0)
    	{
    		makeRandomMove();
    		return;
    	} else if (diff == 1)
    	{
    		if (createCoordsAndCheckForTwo(0,0,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,1,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,2,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(1,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(2,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,1,1)) return;
    		if (createCoordsAndCheckForTwo(2,0,-1,1,1)) return;
    		makeRandomMove();
    		return;
    	} else if (diff == 2)
    	{
    		if (createCoordsAndCheckForTwo(0,0,1,0,2)) return;
    		if (createCoordsAndCheckForTwo(0,1,1,0,2)) return;
    		if (createCoordsAndCheckForTwo(0,2,1,0,2)) return;
    		if (createCoordsAndCheckForTwo(0,0,0,1,2)) return;
    		if (createCoordsAndCheckForTwo(1,0,0,1,2)) return;
    		if (createCoordsAndCheckForTwo(2,0,0,1,2)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,1,2)) return;
    		if (createCoordsAndCheckForTwo(2,0,-1,1,2)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,1,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,2,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(1,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(2,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,1,1)) return;
    		if (createCoordsAndCheckForTwo(2,0,-1,1,1)) return;
    		makeRandomMove();
    		return;
    	} else if (diff == 3)
    	{
    		if (createCoordsAndCheckForTwo(0,0,1,0,2)) return;
    		if (createCoordsAndCheckForTwo(0,1,1,0,2)) return;
    		if (createCoordsAndCheckForTwo(0,2,1,0,2)) return;
    		if (createCoordsAndCheckForTwo(0,0,0,1,2)) return;
    		if (createCoordsAndCheckForTwo(1,0,0,1,2)) return;
    		if (createCoordsAndCheckForTwo(2,0,0,1,2)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,1,2)) return;
    		if (createCoordsAndCheckForTwo(2,0,-1,1,2)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,1,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,2,1,0,1)) return;
    		if (createCoordsAndCheckForTwo(0,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(1,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(2,0,0,1,1)) return;
    		if (createCoordsAndCheckForTwo(0,0,1,1,1)) return;
    		if (createCoordsAndCheckForTwo(2,0,-1,1,1)) return;
    		makePriorityMove();
    		return;
    	}
    }
    
}
