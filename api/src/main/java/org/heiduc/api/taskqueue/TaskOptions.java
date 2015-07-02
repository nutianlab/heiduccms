package org.heiduc.api.taskqueue;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TaskOptions implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6807402550677832771L;
	private String taskName;
	private byte[] payload;
	private HashMap<String, List<String>> headers;
	private Method method;
	private List<Param> params;
	private String url;
	private Long countdownMillis;
	private Long etaMillis;
	// private RetryOptions retryOptions;
	private byte[] tag;

	private TaskOptions() {
		this.method = Method.POST;
		this.headers = new LinkedHashMap<String, List<String>>();
		this.params = new LinkedList<Param>();
	}

	public TaskOptions(TaskOptions options) {
		this.taskName = options.taskName;
		this.method = options.method;
		this.url = options.url;
		this.countdownMillis = options.countdownMillis;
		this.etaMillis = options.etaMillis;
		this.tag = options.tag;

		if (options.getPayload() != null)
			payload(options.getPayload());
		else {
			this.payload = null;
		}
		initializeHeaders(options.getHeaders());
		initializeParams(options.getParams());
	}

	private void initializeHeaders(Map<String, List<String>> headers) {
		this.headers = new LinkedHashMap<String, List<String>>();

		for (Map.Entry<String, List<String>> entry : headers.entrySet())
			this.headers.put(entry.getKey(), new ArrayList<String>(entry.getValue()));
	}

	private void initializeParams(List<Param> params) {
		this.params = new LinkedList<Param>(params);
	}

	Method getMethod() {
		return this.method;
	}

	public TaskOptions taskName(String taskName) {
		if ((taskName != null) && (taskName.length() != 0)) {
			TaskHandle.validateTaskName(taskName);
		}
		this.taskName = taskName;
		return this;
	}

	String getTaskName() {
		return this.taskName;
	}

	byte[] getPayload() {
		return this.payload;
	}

	public TaskOptions payload(byte[] payload) {
		this.payload = ((byte[]) payload.clone());
		return this;
	}

	public TaskOptions url(String url) {
		if (url == null) {
			throw new IllegalArgumentException("null url is not allowed.");
		}
		this.url = url;
		return this;
	}

	TaskOptions param(Param param) {
		this.params.add(param);
		return this;
	}

	public TaskOptions clearParams() {
		this.params.clear();
		return this;
	}

	public TaskOptions param(String name, String value) {
		return param(new StringValueParam(name, value));
	}

	public TaskOptions param(String name, byte[] value) {
		return param(new ByteArrayValueParam(name, value));
	}

	public static final class Builder {
		public static TaskOptions withUrl(String url) {
			return withDefaults().url(url);
		}

		public static TaskOptions withDefaults() {
			return new TaskOptions();
		}

		private Builder() {
		}
	}

	static class ByteArrayValueParam extends TaskOptions.Param {
		private static final long serialVersionUID = 450872030885528392L;
		protected final byte[] value;

		ByteArrayValueParam(String name, byte[] value) {
			super(name);

			if (value == null) {
				throw new IllegalArgumentException("value must not be null");
			}
			this.value = value;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if (!(o instanceof ByteArrayValueParam)) {
				return false;
			}

			ByteArrayValueParam that = (ByteArrayValueParam) o;

			return (Arrays.equals(this.value, that.value)) && (this.name.equals(that.name));
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		public String getURLEncodedValue() throws UnsupportedEncodingException {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < this.value.length; i++) {
				result.append("%");
				char character = Character.toUpperCase(Character.forDigit(this.value[i] >> 4 & 0xF, 16));
				result.append(character);
				character = Character.toUpperCase(Character.forDigit(this.value[i] & 0xF, 16));
				result.append(character);
			}
			return result.toString();
		}
	}

	static class StringValueParam extends TaskOptions.Param {
		private static final long serialVersionUID = -3203392774179779894L;
		protected final String value;

		StringValueParam(String name, String value) {
			super(name);

			if (value == null) {
				throw new IllegalArgumentException("value must not be null");
			}
			this.value = value;
		}

		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if (!(o instanceof StringValueParam)) {
				return false;
			}

			StringValueParam that = (StringValueParam) o;

			return (this.value.equals(that.value)) && (this.name.equals(that.name));
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		public String getURLEncodedValue() throws UnsupportedEncodingException {
			return encodeURLAsUtf8(this.value);
		}
	}

	static abstract class Param implements Serializable {
		protected final String name;

		public Param(String name) {
			if ((name == null) || (name.length() == 0)) {
				throw new IllegalArgumentException("name must not be null or empty");
			}
			this.name = name;
		}

		public int hashCode() {
			return this.name.hashCode();
		}

		protected static String encodeURLAsUtf8(String url) throws UnsupportedEncodingException {
			return URLEncoder.encode(url, "UTF-8");
		}

		public abstract boolean equals(Object paramObject);

		public String getURLEncodedName() throws UnsupportedEncodingException {
			return encodeURLAsUtf8(this.name);
		}

		abstract String getURLEncodedValue() throws UnsupportedEncodingException;
	}

	public static enum RequestMethod {
		GET(1), POST(2), HEAD(3), PUT(4), DELETE(5);

		public static final RequestMethod RequestMethod_MIN = GET;
		public static final RequestMethod RequestMethod_MAX = DELETE;
		private final int value;

		public int getValue() {
			return this.value;
		}

		public static RequestMethod valueOf(int value) {
			switch (value) {
			case 1:
				return GET;
			case 2:
				return POST;
			case 3:
				return HEAD;
			case 4:
				return PUT;
			case 5:
				return DELETE;
			}
			return null;
		}

		private RequestMethod(int v) {
			this.value = v;
		}
	}

	public static enum Method {
		DELETE(RequestMethod.DELETE, false), GET(RequestMethod.GET, false), HEAD(RequestMethod.HEAD, false), POST(RequestMethod.POST, true), PUT(RequestMethod.PUT, true),

		PULL(null, true);

		private boolean isBodyMethod;

		private Method(

		RequestMethod method, boolean isBodyMethod) {
			this.isBodyMethod = isBodyMethod;
		}

		boolean supportsBody() {
			return this.isBodyMethod;
		}
	}

	public HashMap<String, List<String>> getHeaders() {
		return headers;
	}

	public List<Param> getParams() {
		return params;
	}

	public Long getEtaMillis() {
		return etaMillis;
	}

	public String getUrl() {
		return this.url;
	}

}
