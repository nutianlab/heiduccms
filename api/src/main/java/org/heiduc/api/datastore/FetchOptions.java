package org.heiduc.api.datastore;

public class FetchOptions {

	public static final int DEFAULT_CHUNK_SIZE = 20;

	private Integer limit;
	private Integer offset;
	private Integer prefetchSize;
	private Integer chunkSize;
	// private Cursor startCursor;
	// private Cursor endCursor;
	private Boolean compile;

	private FetchOptions() {
		
	}
	
	FetchOptions(FetchOptions original) {
		this.limit = original.limit;
		this.offset = original.offset;
		this.prefetchSize = original.prefetchSize;
		this.chunkSize = original.chunkSize;
		// this.startCursor = original.startCursor;
		// this.endCursor = original.endCursor;
		this.compile = original.compile;
	}
	
	public FetchOptions limit(int limit) {
	    if (limit < 0) {
	      throw new IllegalArgumentException("Limit must be non-negative.");
	    }
	    this.limit = Integer.valueOf(limit);
	    return this;
	}
	
	FetchOptions clearLimit() {
	    this.limit = null;
	    return this;
	}

	public FetchOptions chunkSize(int chunkSize) {
		if (chunkSize < 1) {
			throw new IllegalArgumentException("Chunk size must be greater than 0.");
		}
		this.chunkSize = chunkSize;
		return this;
	}

	FetchOptions clearChunkSize() {
		chunkSize = null;
		return this;
	}
	
	public Integer getLimit()
	  {
	    return this.limit;
	  }

	public static final class Builder {

		public static FetchOptions withChunkSize(int chunkSize) {
			return withDefaults().chunkSize(chunkSize);
		}

		public static FetchOptions withDefaults() {
			return new FetchOptions();
		}
		
		public static FetchOptions withLimit(int limit)
	    {
	      return withDefaults().limit(limit);
	    }

		private Builder() {
		}

	}

}
