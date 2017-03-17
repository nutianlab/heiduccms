package org.heiduc.api.datastore;

import java.io.Serializable;

public class Key implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3158170000818346095L;
	
	private long id;
	private String name;
	private String kind;
	private Key parentKey;

	private Key()
    {
        parentKey = null;
        kind = null;
        id = 0L;
        name = null;
    }
	
	public Key(String kind)
    {
        this(kind, null, 0L);
    }
	
	public Key(String kind, Key parentKey)
    {
        this(kind, parentKey, 0L);
    }
	
	public Key(String kind, Key parentKey, long id)
    {
        this(kind, parentKey, id, null);
    }
    
	public Key(String kind, Key parentKey, String name)
    {
        this(kind, parentKey, 0L, name);
    }

	public Key(String kind, Key parentKey, long id, String name)
    {
        if(kind == null || kind.length() == 0)
            throw new IllegalArgumentException("No kind specified.");
        
        if(name != null)
        {
            if(name.length() == 0)
                throw new IllegalArgumentException("Name may not be empty.");
            if(id != 0L)
                throw new IllegalArgumentException("Id and name may not both be specified at once.");
        }
        this.id = id;
        this.parentKey = parentKey;
        this.name = getString(parentKey, name);
        this.kind = getString(parentKey, kind);
    }

    private static String getString(Key parentKey, String value)
    {
        if(value == null || parentKey == null)
            return value;
        else
            return new String(value);
    }



	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName()
    {
        return name;
    }
	
	public String getKind()
    {
        return kind;
    }

    public Key getParent()
    {
        return parentKey;
    }


}
