package org.heiduc.api.datastore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Query implements Serializable {

	private final String kind;
	private final List sortPredicates;
	private final List filterPredicates;
	private Key ancestor;
	private boolean keysOnly;
	private transient String fullTextSearch;

	public Query() {
		this((String) null);
	}

	public Query(String kind) {
		this(kind, null, ((List) (new ArrayList())),
				((List) (new ArrayList())), false, null);
	}

	Query(String kind, Key ancestor, List sortPreds, List filterPreds,
			boolean keysOnly, String fullTextSearch) {
		this.kind = kind;
		this.ancestor = ancestor;
		sortPredicates = sortPreds;
		filterPredicates = filterPreds;
		this.keysOnly = keysOnly;
		this.fullTextSearch = fullTextSearch;
	}

	public Query setKeysOnly() {
		keysOnly = true;
		return this;
	}
	
	public String getKind() {
		return kind;
	}

	public enum FilterOperator {
		LESS_THAN("$lt"), 
		LESS_THAN_OR_EQUAL("$lte"), 
		GREATER_THAN("$gt"), 
		GREATER_THAN_OR_EQUAL("$gte"), 
		EQUAL("$e"), 
		NOT_EQUAL("$ne"), 
		IN("$in");

		private final String shortName;

		private FilterOperator(String shortName) {
			this.shortName = shortName;
		}

		@Override
		public String toString() {
			return shortName;
		}
	}

	public Query addFilter(String propertyName, FilterOperator operator,
			Object value) {
		filterPredicates
				.add(new FilterPredicate(propertyName, operator, value));
		return this;
	}

	public static final class FilterPredicate implements Serializable {

		public String getPropertyName() {
			return propertyName;
		}

		public Query.FilterOperator getOperator() {
			return operator;
		}

		public Object getValue() {
			return value;
		}

		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			FilterPredicate that = (FilterPredicate) o;
			if (operator != that.operator)
				return false;
			if (!propertyName.equals(that.propertyName))
				return false;
			return value == null ? that.value == null : value
					.equals(that.value);
		}

		public int hashCode() {
			int result = propertyName.hashCode();
			result = 31 * result + operator.hashCode();
			result = 31 * result + (value == null ? 0 : value.hashCode());
			return result;
		}

		public String toString() {
			return (new StringBuilder()).append(propertyName).append(" ")
					.append(operator.toString()).append(" ")
					.append(value == null ? "NULL" : value.toString())
					.toString();
		}

		static final long serialVersionUID = 0x6a9a158a961b9c43L;
		private final String propertyName;
		private final Query.FilterOperator operator;
		private final Object value;

		public FilterPredicate(String propertyName,
				Query.FilterOperator operator, Object value) {
			if (propertyName == null)
				throw new NullPointerException("Property name was null");
			if (operator == null)
				throw new NullPointerException("Operator was null");
			if (operator == Query.FilterOperator.IN) {
				if (!(value instanceof Collection)
						&& (value instanceof Iterable)) {
					List newValue = new ArrayList();
					Object val;
					for (Iterator i$ = ((Iterable) value).iterator(); i$
							.hasNext(); newValue.add(val))
						val = i$.next();

					value = newValue;
				}
				// DataTypeUtils.checkSupportedValue(propertyName, value, true,
				// true);
			} else {
				// DataTypeUtils.checkSupportedValue(propertyName, value, false,
				// false);
			}
			this.propertyName = propertyName;
			this.operator = operator;
			this.value = value;
		}
	}
	
	public List getFilterPredicates()
    {
        /**/
		return filterPredicates;
    }

}
