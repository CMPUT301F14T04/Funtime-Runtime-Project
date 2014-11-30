package ca.ualberta.cs.funtime_runtime.elastic;

/**
* 
* Class inherited from CMPUT 301 AndroidElasticSearch lab code
* Need to modify to fit funtime_runtime project
* TODO
* @author Pranjali Pokharel
*
*/

public class SearchResponse<T>
{
	private int took;
	private boolean timed_out;
	private Shard _shards;
	private Hits<T> hits;
	
	public SearchResponse() {}

	public int getTook() {
		return took;
	}

	public void setTook(int took) {
		this.took = took;
	}

	public boolean isTimed_out() {
		return timed_out;
	}

	public void setTimed_out(boolean timed_out) {
		this.timed_out = timed_out;
	}

	public Shard get_shards() {
		return _shards;
	}

	public void set_shards(Shard _shards) {
		this._shards = _shards;
	}

	public Hits<T> getHits() {
		return hits;
	}

	public void setHits(Hits<T> hits) {
		this.hits = hits;
	}	   
	
}
