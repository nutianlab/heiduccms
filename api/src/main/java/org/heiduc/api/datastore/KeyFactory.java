package org.heiduc.api.datastore;

public class KeyFactory {

	public static Key createKey(String kind, Long id) {
		return createKey(null, kind, id);
	}
	
	public static Key createKey(Key parent, String kind, long id)
    {
        if(id == 0L)
            throw new IllegalArgumentException("id cannot be zero");
        else
            return new Key(kind, parent, id);
    }


}
