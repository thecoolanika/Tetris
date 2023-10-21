/**
 * ArrowListener establishes the name of the methods that
 * respond to the four arrow keys.
 * 
 * @author Anika Pandey
 * @version 5/09/23
 */
public interface ArrowListener
{
    /**
     * Responses to the up arrow.
     */
    void upPressed();

    /**
     * Responses to the down arrow.
     */
    void downPressed();

    /**
     * Responses to the left arrow.
     */
    void leftPressed();

    /**
     * Responses to the right arrow.
     */
    void rightPressed();
    
    /**
     * Responses to the space.
     */
    void spacePressed();
}
