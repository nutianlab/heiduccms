package org.heiduc.api.datastore;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 3925512509047986460L;
	
	private final Key key;
	
	public EntityNotFoundException(Key key)
    {
        super((new StringBuilder()).append("No entity was found matching the key: ").append(key).toString());
        this.key = key;
    }

    public Key getKey()
    {
        return key;
    }

}
