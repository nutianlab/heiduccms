

package com.heiduc.dao;

public class DaoStat {

	private Long getCalls = 0L;
	private Long queryCalls = 0L;
	private Long entityCacheHits = 0L;
	private Long queryCacheHits = 0L;
	
	public DaoStat() {
	}

	public DaoStat clone() {
		DaoStat result = new DaoStat();
		result.getCalls = getCalls;
		result.entityCacheHits = entityCacheHits;
		result.queryCacheHits = queryCacheHits;
		result.queryCalls = queryCalls;
		return result;
	}
	
	public Long getGetCalls() {
		return getCalls;
	}
	
	public void incGetCalls() {
		getCalls++;
	}

	public Long getQueryCalls() {
		return queryCalls;
	}
	
	public void incQueryCalls() {
		queryCalls++;
	}

	public String toString() {
		return "getCalls:" + getCalls + " queryCalls:" + queryCalls; 
	}

	public Long getEntityCacheHits() {
		return entityCacheHits;
	}

	public void incEntityCacheHits() {
		entityCacheHits++;
	}

	public Long getQueryCacheHits() {
		return queryCacheHits;
	}

	public void incQueryCacheHits() {
		queryCacheHits++;
	}
}
