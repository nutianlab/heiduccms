package org.heiduc.api.datastore;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Entity implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3146926183481472170L;
	private final Key key;
	private final Map propertyMap;

	public Entity(Key key) {
		this.key = key;
		propertyMap = new HashMap();
	}

	public Entity(String kind) {
		this(kind, (Key) null);
	}

	public Entity(String kind, Key parent) {
		this(new Key(kind, parent));
	}

	public Key getKey() {
		return key;
	}

	public void setProperty(String propertyName, Object value) {
		// DataTypeUtils.checkSupportedValue(propertyName, value);
		propertyMap.put(propertyName, value);
	}

	public void setUnindexedProperty(String propertyName, Object value) {
		// DataTypeUtils.checkSupportedValue(propertyName, value);
		// propertyMap.put(propertyName, new UnindexedValue(value));
		propertyMap.put(propertyName, value);
	}

	public void removeProperty(String propertyName) {
		propertyMap.remove(propertyName);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Entity) {
			Entity otherEntity = (Entity) object;
			return key.equals(otherEntity.key);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append((new StringBuilder()).append("<Entity [").append(key).append("]:\n").toString());
		java.util.Map.Entry entry;
		for (Iterator i$ = propertyMap.entrySet().iterator(); i$.hasNext(); buffer.append((new StringBuilder()).append("\t").append((String) entry.getKey()).append(" = ").append(entry.getValue())
				.append("\n").toString()))
			entry = (java.util.Map.Entry) i$.next();

		buffer.append(">\n");
		return buffer.toString();
	}

	public Object getProperty(String propertyName) {
		return unwrapValue(propertyMap.get(propertyName));
		// return propertyMap.get(propertyName);
	}

	public Map getProperties() {
		Map properties = new HashMap(propertyMap.size());
		java.util.Map.Entry entry;
		for (Iterator i$ = propertyMap.entrySet().iterator(); i$.hasNext(); properties.put(entry.getKey(), unwrapValue(entry.getValue())))
			entry = (java.util.Map.Entry) i$.next();

		return Collections.unmodifiableMap(properties);
	}

	static Object unwrapValue(Object obj) {
		/*
		 * if (obj instanceof UnindexedValue) return ((UnindexedValue)
		 * obj).getValue(); else
		 */
		return obj;
	}

	Map getPropertyMap() {
		return propertyMap;
	}

	/*
	 * static final class UnindexedValue implements Serializable {
	 *//**
		 * 
		 */
	/*
	 * private static final long serialVersionUID = 7701997879076042650L;
	 * 
	 * public Object getValue() { return value; }
	 * 
	 * public boolean equals(Object that) { if (that instanceof UnindexedValue)
	 * { UnindexedValue uv = (UnindexedValue) that; return value != null ?
	 * value.equals(uv.value) : uv.value == null; } else { return false; } }
	 * 
	 * public int hashCode() { return value != null ? value.hashCode() : 0; }
	 * 
	 * public String toString() { return (new
	 * StringBuilder()).append(value).append(" (unindexed)") .toString(); }
	 * 
	 * private final Object value;
	 * 
	 * UnindexedValue(Object value) { this.value = value; } }
	 */

}
