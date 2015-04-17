

package com.heiduc.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

	private int count;
	private List<Hit> hits;
	
	public SearchResult() {
		hits = new ArrayList<Hit>();
	}
	
	public SearchResult(int count, List<Hit> hits) {
		super();
		this.count = count;
		this.hits = hits;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<Hit> getHits() {
		return hits;
	}
	
	public void setHits(List<Hit> hits) {
		this.hits = hits;
	}
	
}
