

package com.heiduc.business.impl.imex;

import static com.heiduc.utils.XmlUtil.notNull;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.imex.UserExporter;
import com.heiduc.common.BCrypt;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.UserEntity;
import com.heiduc.enums.UserRole;
import com.heiduc.utils.ParamUtil;

/**
 * @author Alexander Oleynik
 */
public class UserExporterImpl extends AbstractExporter 
		implements UserExporter {

	public UserExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	@Override
	public String createUsersXML() {
		Document doc = DocumentHelper.createDocument();
		Element usersElement = doc.addElement("users");
		createUsersXML(usersElement);
		return doc.asXML();
	}

	private void createUsersXML(Element usersElement) {
		List<UserEntity> list = getDao().getUserDao().select();
		for (UserEntity user : list) {
			createUserXML(usersElement, user);
		}
	}

	private void createUserXML(Element usersElement, final UserEntity user) {
		Element userElement = usersElement.addElement("user");
		userElement.addElement("name").setText(user.getName());
		userElement.addElement("email").setText(user.getEmail());
		userElement.addElement("password").setText(notNull(user.getPassword()));
		userElement.addElement("role").setText(user.getRole().name());
		userElement.addElement("disabled").setText(
				String.valueOf(user.isDisabled()));
	}
	
	public void readUsers(Element usersElement) throws DaoTaskException {
		for (Iterator<Element> i = usersElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("user")) {
            	String email = element.elementText("email");
            	String name = element.elementText("name");
            	boolean disabled = ParamUtil.getBoolean(
            			element.elementText("disabled"), false);
            	String password = element.elementText("password");
            	try {
            		BCrypt.checkpw("test", password);
            	}
            	catch (Exception e) {
            		password = BCrypt.hashpw(password, BCrypt.gensalt());
            	}
            	UserRole role = UserRole.valueOf(element.elementText("role"));
            	UserEntity user = getDao().getUserDao().getByEmail(email);
            	if (user == null) {
            		user = new UserEntity(name, password, email, role);
            	}
            	user.setName(name);
            	user.setPassword(password);
            	user.setRole(role);
            	user.setDisabled(disabled);
            	getDaoTaskAdapter().userSave(user);
            }
		}		
	}
	
	/**
	 * Read and import data from _users.xml.
	 * @param xml - _users.xml content.
	 * @throws DocumentException 
	 * @throws DaoTaskException 
	 */
	public void readUsersFile(String xml) throws DocumentException, 
			DaoTaskException {
		Document doc = DocumentHelper.parseText(xml);
		readUsers(doc.getRootElement());
	}
}
