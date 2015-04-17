

package com.heiduc.business.decorators;

import java.util.ArrayList;
import java.util.List;

public class TreeItemDecorator<T> {

	private T entity;
	private List<TreeItemDecorator<T>> children;
	private TreeItemDecorator<T> parent;
	
	public TreeItemDecorator(T anEntity, TreeItemDecorator<T> aParent) {
		entity = anEntity;
		children = new ArrayList<TreeItemDecorator<T>>();
		parent = aParent;
	}

	public TreeItemDecorator(T anEntity, TreeItemDecorator<T> aParent, 
			List<TreeItemDecorator<T>> aChildren) {
		entity = anEntity;
		children = aChildren;
		parent = aParent;
	}
	
	public T getEntity() {
		return entity;
	}
	
	public List<TreeItemDecorator<T>> getChildren() {
		return children;
	}
	
	public boolean isHasChildren() {
		return children != null && children.size() > 0;
	}

	public void setChildren(List<TreeItemDecorator<T>> list) {
		children = list;
	}

	public TreeItemDecorator<T> getParent() {
		return parent;
	}

	public void setParent(TreeItemDecorator<T> parent) {
		this.parent = parent;
	}
	
	public TreeItemDecorator<T> find(final T entity) {
		if (getEntity().equals(entity)) {
			return this;
		}
		TreeItemDecorator<T> result = null;
		for (TreeItemDecorator<T> child : getChildren()) {
			result = child.find(entity);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	
}
