package ca.ualberta.cs.funtime_runtime.classes;

import java.io.Serializable;


public class OfflineAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7000605490592377467L;
	
	int answerId;
	int parentQuestionId;
	
	public OfflineAnswer(int id, int parentId) 	{
		answerId = id;
		parentQuestionId = parentId;
	}
	
	public int getAnswerId() {
		return answerId;
	}
	
	public int getParentQuestionId() {
		return parentQuestionId;
	}
	
    public boolean equals(Object obj) {
    	if (!(obj instanceof OfflineAnswer)) {
             return false;
    	}
    	OfflineAnswer other = (OfflineAnswer) obj;
    	return ( ((Integer) (other.getAnswerId()) ).equals( (Integer) (this.getAnswerId()) ));
    }
	
}
