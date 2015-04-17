package org.heiduc.api.taskqueue;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public final class QueueConstants {
	private static final int MAX_QUEUE_NAME_LENGTH = 100;
	private static final int MAX_TASK_NAME_LENGTH = 500;
	private static final int MAX_TASK_TAG_LENGTH = 500;
	private static final int MAX_PUSH_TASK_SIZE_BYTES = 102400;
	private static final int MAX_PULL_TASK_SIZE_BYTES = 1048576;
	private static final int MAX_TRANSACTIONAL_REQUEST_SIZE_BYTES = 1048576;
	private static final int MAX_TASKS_PER_ADD = 100;
	private static final int MAX_URL_LENGTH = 2083;
	private static final long MAX_ETA_DELTA_MILLIS = 2592000000L;
	private static final long MAX_LEASE_MILLIS = 604800000L;
	private static final long MAX_TASKS_PER_LEASE = 1000L;
	private static final int MAX_TASKS_PER_DELETE = 1000;
	public static final String TASK_NAME_REGEX = "[a-zA-Z\\d_-]{1,"
			+ maxTaskNameLength() + "}";
	public static final Pattern TASK_NAME_PATTERN = Pattern
			.compile(TASK_NAME_REGEX);

	public static final String QUEUE_NAME_REGEX = "[a-zA-Z\\d-]{1,"
			+ maxQueueNameLength() + "}";
	public static final Pattern QUEUE_NAME_PATTERN = Pattern
			.compile(QUEUE_NAME_REGEX);

	public static long maxLease(TimeUnit unit) {
		return unit.convert(604800000L, TimeUnit.MILLISECONDS);
	}

	public static long maxLeaseCount() {
		return 1000L;
	}

	public static int maxQueueNameLength() {
		return 100;
	}

	public static int maxTaskNameLength() {
		return 500;
	}

	public static int maxTaskTagLength() {
		return 500;
	}

	public static int maxPushTaskSizeBytes() {
		return 102400;
	}

	public static int maxPullTaskSizeBytes() {
		return 1048576;
	}

	static int maxTransactionalRequestSizeBytes() {
		return 1048576;
	}

	public static int maxTasksPerAdd() {
		return 100;
	}

	public static int maxUrlLength() {
		return 2083;
	}

	public static long getMaxEtaDeltaMillis() {
		return 2592000000L;
	}
}
