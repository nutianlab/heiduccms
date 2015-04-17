

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.FormDataEntity;
import com.heiduc.entity.FormEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface FormDataDao extends BaseDao<FormDataEntity> {

	List<FormDataEntity> getByForm(final FormEntity form);

}
