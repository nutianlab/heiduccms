package org.heiduc.api.datastore;

public class FetchOptions {

//	public static final int DEFAULT_CHUNK_SIZE = 20;

	private Integer limit;
	private Integer offset;
	private Integer prefetchSize;
	private Integer chunkSize;
	// private Cursor startCursor;
	// private Cursor endCursor;
	private Boolean compile;

	private FetchOptions() {
	}

	public FetchOptions chunkSize(int chunkSize) {
		if (chunkSize < 1) {
			throw new IllegalArgumentException(
					"Chunk size must be greater than 0.");
		}
		this.chunkSize = chunkSize;
		return this;
	}

	FetchOptions clearChunkSize() {
		chunkSize = null;
		return this;
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

	public static final class Builder {

		public static FetchOptions withChunkSize(int chunkSize) {
			return withDefaults().chunkSize(chunkSize);
		}

		public static FetchOptions withDefaults() {
			return new FetchOptions();
		}

		private Builder() {
		}

	}

}
