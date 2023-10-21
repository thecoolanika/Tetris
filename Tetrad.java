import java.awt.Color;
/**
 * Creates a Tetrad of one of seven types:
 * I, T, O, L, J, S, Z
 * The Tetrad can test if it can move forward,
 *  and if it cannot the Tetrad becomes immobile.
 * 
 * @author Anika Pandey
 * @version 5/09/23
 */
public class Tetrad
{
    private Block[] blocks = new Block[4];
    private Color red = new Color(255, 130, 130);
    private Color grey = Color.GRAY;
    private Color blue = new Color(130, 130, 255);
    private Color teal = new Color(130, 236, 255);
    private Color magenta = Color.MAGENTA;
    private Color yellow = new Color(255, 238, 130);
    private Color green = new Color(145, 255, 130);
    private Color[] color = {red, grey, teal, yellow, magenta, blue, green};
    private int randType;
    private String shape;
    private MyBoundedGrid<Block> grid;
    private String[] shapes = {"I", "T", "O", "L", "J", "S", "Z"};
    private boolean canMove; 

    /**
     * Constructor for objects of class Tetrad
     * 
     * @param gr grid
     * 
     * @param num is shape to create
     */
    public Tetrad(MyBoundedGrid<Block> gr,int num)
    {
        Location[] locs = new Location[4];
        randType = num;
        grid = gr;
        int len = blocks.length;
        int mid = grid.getGameCols()/2;
        shape = shapes[randType];
        if(shapes[randType].equals("I"))
        {
            locs[0] = new Location(0,mid);
            locs[1] = new Location(1,mid);
            locs[2] = new Location(2,mid);
            locs[3] = new Location(3,mid);
        }
        else if(shapes[randType].equals("T"))
        {
            locs[0] = new Location(0,mid-1);
            locs[1] = new Location(0,mid);
            locs[2] = new Location(0,mid+1);
            locs[3] = new Location(1,mid);
        }
        else if(shapes[randType].equals("O"))
        {
            locs[0] = new Location(0,mid-1);
            locs[1] = new Location(0,mid);
            locs[2] = new Location(1,mid-1);
            locs[3] = new Location(1,mid);
        }
        else if(shapes[randType].equals("L"))
        {
            locs[0] = new Location(0,mid);
            locs[1] = new Location(1,mid);
            locs[2] = new Location(2,mid);
            locs[3] = new Location(2,mid+1);
        }
        else if(shapes[randType].equals("S"))
        {
            locs[0] = new Location(1,mid-1);
            locs[1] = new Location(1,mid);
            locs[2] = new Location(0,mid);
            locs[3] = new Location(0,mid+1);
        }
        else if(shapes[randType].equals("Z"))
        {
            locs[1] = new Location(0,mid);
            locs[0] = new Location(0,mid-1);
            locs[2] = new Location(1,mid);
            locs[3] = new Location(1,mid+1);
        }
        if(shapes[randType].equals("J"))
        {
            locs[0] = new Location(0,mid);
            locs[1] = new Location(1,mid);
            locs[2] = new Location(2,mid);
            locs[3] = new Location(2,mid-1);
        }
        canMove = addToLocations(grid,locs);
    }

    /**
     * Returns if the Tetrad can move into the next spot.
     * 
     * @return true if tetrad is clear to move forward,otherwise 
     *         false
     */
    public boolean isClear()
    {
        return canMove;
    }

    /**
     * Adds blocks to location on grid.
     * 
     * @param gr grid
     * @param locs location of blocks
     * 
     * @return true if can add to location,otherwise 
     *         false
     */
    private boolean addToLocations(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for(int x=0; x<locs.length;x++)
        {
            if(gr.get(locs[x]) != null)
                return false;
        }
        for(int x = 0; x<blocks.length; x++)
        {
            blocks[x] = new Block();
            blocks[x].setColor(color[randType]);
            blocks[x].putSelfInGrid(gr,locs[x]);
        }
        return true;
    }

    /**
     * Removes old blocks.
     * 
     * @return location of blocks
     */
    private Location[] removeBlocks()
    {
        Location[] loc = new Location[4];
        for(int i=0; i<4; i++)
        {
            Block temp = blocks[i];
            loc[i] = temp.getLocation();
            temp.removeSelfFromGrid();
        }
        return loc;
    }

    /**
     * Checks if an array of given locations is empty.
     * 
     * @return true if area is empty; otherwise 
     *         false
     *          
     * @param gr grid of the game to check if empty
     * @param locs the locations to check for validity
     */
    private boolean areEmpty(MyBoundedGrid<Block> gr, Location[] locs)
    {
        for(int i = 0; i<locs.length; i++)
        {
            if(!(gr.isValid(locs[i])) || (gr.get(locs[i])!=null))
                return false;
        }
        return true;
    }

    /**
     * Translates tetrad to a location on the grid.
     * 
     * @param deltaR rows to move
     * 
     * @param deltaCol columns to move
     * 
     * @return true if tetrad can move, otherwise 
     *         false
     */
    public boolean translate(int deltaR, int deltaCol)
    {
        Location[] oldLocations = removeBlocks();
        Location[] newLocations = new Location[blocks.length];
        for(int i = 0; i<newLocations.length; i++)
        {
            newLocations[i] = new Location(oldLocations[i].getRow()+deltaR,
                oldLocations[i].getCol() + deltaCol);
        }

        if(areEmpty(grid, newLocations))
        {
            addToLocations(grid, newLocations);
            return true;
        }
        else
        {
            addToLocations(grid, oldLocations);
            return false;
        }
    }

    /**
     * Rotates Tetrad when up arrow key is pressed.
     * 
     * @return true if tetrad can rotate, otherwise 
     *         false
     */
    public boolean rotate()
    {
        grid = blocks[0].getGrid();
        Location[] oldLocs = removeBlocks();
        Location[] newLocs = new Location[blocks.length];
        if(!shape.equals("O"))
            for(int i = 0; i<newLocs.length; i++)
            {
                int row = oldLocs[1].getRow()-oldLocs[1].getCol()+oldLocs[i].getCol();
                int col = oldLocs[1].getRow()+oldLocs[1].getCol()-oldLocs[i].getRow();
                newLocs[i] = new Location(row,col);
            }
        else
            newLocs = oldLocs;
        if(areEmpty(grid, newLocs))
        {
            addToLocations(grid, newLocs);
            return true;
        }
        else
        {
            addToLocations(grid, oldLocs);
            return false;
        }
    }

    /**
     * Returns if the grid still has space for a Tetrad
     * 
     * @return clear
     */
    public boolean gameNotOver()
    {
        return canMove;
    }
}