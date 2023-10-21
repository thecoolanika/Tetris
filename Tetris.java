import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.*;
import java.lang.*;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**
 * Plays a tetris game with 7 Tetrad types:
 * I, T, O, L, J, S, Z. The up arrow key rotates
 * the current Tetrad; the right, left, and down move
 * the Tetrad right, left, and down one cell respectively;
 * and the space bar brings the Tetrad as low down the 
 * board as possible.
 * 
 * Extra features:  calculates and displays score 
 *                  game over
 *                  displays level
 *                  keeps track of and displays lines cleared
 * 
 * @author Anika Pandey
 * @version 5/09/23
 */
public class Tetris implements ArrowListener
{
    private MyBoundedGrid<Block> grid;
    private BlockDisplay display;
    private boolean end;
    private Tetrad currTetrad;
    private int level = 1;
    private int score = 0;
    private int rowsCleared = 0;
    private int stored = (int)(Math.random()*7);
    private int[] scoreSystem = {0,40,100,300,1200};
    private int rowPoint = 0;
    private static boolean gameOver = false;

    /**
     * Creates new game and plays
     * 
     * @param args information from the command line
     */
    public static void main(String[] args)
    {
        Tetris tetris = new Tetris();
        tetris.play();
        tetris.playSound();
        
    }

    /**
     * Constructor for objects of class Tetris
     */
    public Tetris()
    {
        grid = new MyBoundedGrid(20,10);
        currTetrad = new Tetrad(grid, stored);
        display = new BlockDisplay(grid);
        display.setTitle("tetris");
        display.setArrowListener(this);
        level = 1;
        score = 0;
        display.showBlocks();
    }

    /**
     * Rotates block
     */
    public void upPressed()
    {
        currTetrad.rotate();
        display.showBlocks();
    }

    /**
     * Moves block down one grid.
     */
    public void downPressed()
    {
        currTetrad.translate(1,0);
        display.showBlocks();
    }

    /**
     * Moves block to the left one grid.
     */
    public void leftPressed()
    {
        currTetrad.translate(0,-1);
        display.showBlocks();
    }

    /**
     * Moves block to right one grid.
     */
    public void rightPressed()
    {
        currTetrad.translate(0,1);
        display.showBlocks();
    }

    /**
     * Moves block all the way down the board.
     */
    public void spacePressed()
    {
        while(currTetrad.translate(1,0))
        {
            currTetrad.translate(1,0);
        }
        display.showBlocks();
    }

    /**
     * Plays the Tetris game.
     */
    public void play()
    {
        while(!gameOver) 
        {
            try
            {
                Thread.sleep(1000-(level*100));  
                while(currTetrad.translate(1,0))
                {
                    display.showBlocks();
                    Thread.sleep(1000-(level*100));  
                }
                clearCompletedRows();
                int shape = generateTetradShape();
                currTetrad = new Tetrad(grid,shape);             
            }
            catch(InterruptedException e)
            {
            }
            if(!currTetrad.gameNotOver())
            {
                display.setTitle("GAME OVER");
                display.repaint();
                gameOver = true;
            }
            display.showBlocks();
        }
    }

    /**
     * Chooses a random Tetrad shape that isn't the same type
     * as the last Tetrad.
     */
    private int generateTetradShape()
    {
        int random = (int)(Math.random()*7);
        while(random == stored)
        {
            random = (int)(Math.random()*7);
        }
        stored = random;
        return random;
    }

    /**
     * Checks if a row is completed (tetrads fill every cell).
     * 
     * @param row is the row to check
     */
    private boolean isCompletedRow(int row)
    {
        for (int i = 0; i< grid.getGameCols(); i++)
        {
            if ((grid.get(new Location(row,i))) == null)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Clears a row of all blocks.
     * 
     * @param row is the row to clear
     */
    private void clearRow(int row)
    {
        for(int i = 0; i<grid.getGameCols(); i++)
        {
            Location loc = new Location(row, i);
            grid.get(loc).removeSelfFromGrid();
        }

        for(int i = row -1; i>=0; i--)
        {
            for(int j = 0; j<grid.getGameCols(); j++)
            {
                Location loc = new Location (i, j);
                if(grid.get(loc) != null)
                {
                    Location newLoc = new Location(i+1,j);
                    grid.get(loc).moveTo(newLoc);
                }
            }
        }
        rowPoint++;
        rowsCleared++;
        if(rowsCleared%10 ==0 && level<10)
        {
            level++;
        }
    }

    /**
     * Clears all completed rows.
     */
    private void clearCompletedRows()
    {    
        int row = grid.getGameRows()-1;
        while(row>=0)
        {
            if(isCompletedRow(row))
            {
                clearRow(row);
            }
            else
            {
                row--;
            }
        }
        calculateScore();
    }

    public void playSound() {
    try {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("music.mp3").getAbsoluteFile());
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
    } catch(Exception ex) {
        System.out.println("Error with playing sound.");
        ex.printStackTrace();
    }
}
    /**
     * Calculates the score of the player. The true score is
     * the raw score with bonuses for level or row combinations.
     */
    private void calculateScore()
    {
        int rows = rowPoint;
        score += scoreSystem[rows]*level;
        this.rowPoint = 0;
    }
}