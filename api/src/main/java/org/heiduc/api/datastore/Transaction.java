package org.heiduc.api.datastore;

import java.util.concurrent.Future;

public interface Transaction {

	
	public abstract void commit();

    public abstract Future commitAsync();

    public abstract void rollback();

    public abstract Future rollbackAsync();

    public abstract String getId();

    public abstract boolean isActive();
}
