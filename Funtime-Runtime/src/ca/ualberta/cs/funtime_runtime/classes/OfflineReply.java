package ca.ualberta.cs.funtime_runtime.classes;

import java.io.Serializable;


public class OfflineReply implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8119496000971294682L;
	int replyId;
	int parentAnswerId;
	int parentQuestionId;
	
	public OfflineReply(int id, int answerId, int questionId) {
		replyId = id;
		parentAnswerId = answerId;
		parentQuestionId = questionId;
	}
	
	public int getReplyId() {
		return replyId;
	}
	
	public int getParentAnswerId() {
		return parentAnswerId;
	}
	
	public int getParentQuestionId() {
		return parentQuestionId;
	}
	
    public boolean equals(Object obj) {
    	if (!(obj instanceof OfflineReply)) {
             return false;
    	}
    	OfflineReply other = (OfflineReply) obj;
    	return ( ((Integer) (other.getReplyId()) ).equals( (Integer) (this.getReplyId()) ));
    }
	
}