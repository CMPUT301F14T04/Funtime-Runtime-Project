package ca.ualberta.cs.funtime_runtime;

//import java.util.ArrayList;
import java.util.List;

/**
 * Not implemented yet. 
 * Will be implemented in Part4 
 * @author pranjali
 *
 */

public interface IReplyManager {
	public Reply getReply(int id);
	public void addReply(Reply reply); //pushes (saves) a reply to the server
	public void deleteReply(int id); //deletes a reply from the server
}
