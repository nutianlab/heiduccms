

package com.heiduc.rest;

//@Authorized
public class DummyRest {

	// /rest/dummy
	public String apply() {
		return "Hello World!";
	}
	
	// /rest/dummy?id=1
	public String apply(@Var("id") Long id) {
		return "Hello World !" + id;
	}

	// /rest/dummy/open/1 
	// /rest/dummy/open?id=1
	public String open(@Var("id") Long id) {
		return "Hello " + id;
	}
	
	// /rest/dummy/test
	public String test() {
		throw new IllegalAccessError();
	}
	
	// /rest/dummy/secure
	@Authorized
	public String secure() {
		return "secret";
	}
}
