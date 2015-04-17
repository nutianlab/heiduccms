

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.UserEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface UserBusiness {

	List<String> validateBeforeUpdate(final UserEntity User);
	
	void remove(final List<Long> ids);
	
	void forgotPassword(String email);
	
}
