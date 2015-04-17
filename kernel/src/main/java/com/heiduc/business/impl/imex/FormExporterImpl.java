

package com.heiduc.business.impl.imex;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.imex.FormExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.enums.FieldType;
import com.heiduc.utils.XmlUtil;

public class FormExporterImpl extends AbstractExporter 
		implements FormExporter {

	public FormExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	@Override
	public String createFormsXML() {
		Document doc = DocumentHelper.createDocument();
		Element formsElement = doc.addElement("forms");
		createFormsXML(formsElement);
		return doc.asXML();
	}

	private void createFormsXML(Element formsElement) {
		createFormConfigXML(formsElement);
		List<FormEntity> list = getDao().getFormDao().select();
		for (FormEntity form : list) {
			createFormXML(formsElement, form);
		}
	}
	
	private void createFormConfigXML(Element formsElement) {
		FormConfigEntity config = getDao().getFormConfigDao().getConfig();
		Element configElement = formsElement.addElement("form-config");
		Element formTemplateElement = configElement.addElement("formTemplate");
		formTemplateElement.setText(config.getFormTemplate());
		Element formLetterElement = configElement.addElement("letterTemplate");
		formLetterElement.setText(config.getLetterTemplate());
	}

	private void createFormXML(Element formsElement, final FormEntity form) {
		Element formElement = formsElement.addElement("form");
		formElement.addAttribute("name", form.getName());
		formElement.addAttribute("title", form.getTitle());
		formElement.addAttribute("email", form.getEmail());
		formElement.addAttribute("letterSubject", form.getLetterSubject());
		formElement.addAttribute("sendButtonTitle", form.getSendButtonTitle());
		formElement.addAttribute("showResetButton", String.valueOf(
				form.isShowResetButton()));
		formElement.addAttribute("enableCaptcha", String.valueOf(
				form.isEnableCaptcha()));
		formElement.addAttribute("enableSave", String.valueOf(
				form.isEnableSave()));
		formElement.addAttribute("resetButtonTitle", form.getResetButtonTitle());
		List<FieldEntity> fields = getDao().getFieldDao().getByForm(form);
		for (FieldEntity field : fields) {
			createFieldXML(formElement, field);
		}
	}
	
	public void readForms(Element formsElement) throws DaoTaskException {
		for (Iterator<Element> i = formsElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("form")) {
            	String name = element.attributeValue("name");
            	String title = element.attributeValue("title");
            	String email = element.attributeValue("email");
            	String letterSubject = element.attributeValue("letterSubject");
            	FormEntity form = new FormEntity(name, email, title, 
            			letterSubject);
            	form.setSendButtonTitle(element.attributeValue("sendButtonTitle"));
            	form.setShowResetButton(XmlUtil.readBooleanAttr(element, 
            			"showResetButton", false));
            	form.setEnableCaptcha(XmlUtil.readBooleanAttr(element, 
            			"enableCaptcha", false));
            	form.setEnableSave(XmlUtil.readBooleanAttr(element, 
            			"enableSave", false));
            	form.setResetButtonTitle(element.attributeValue("resetButtonTitle"));
            	getDaoTaskAdapter().formSave(form);
            	readFields(element, form);
            }
            if (element.getName().equals("form-config")) {
            	readFormConfig(element);
            }
		}		
	}

	private void createFieldXML(Element formElement, FieldEntity field) {
		Element fieldElement = formElement.addElement("field");
		fieldElement.addAttribute("name", field.getName());
		fieldElement.addAttribute("title", field.getTitle());
		fieldElement.addAttribute("fieldType", field.getFieldType().name());
		fieldElement.addAttribute("mandatory", String.valueOf(field.isMandatory()));
		fieldElement.addAttribute("values", field.getValues());
		fieldElement.addAttribute("defaultValue", field.getDefaultValue());
		fieldElement.addAttribute("height", String.valueOf(field.getHeight()));
		fieldElement.addAttribute("width", String.valueOf(field.getWidth()));
		fieldElement.addAttribute("index", String.valueOf(field.getIndex()));
	}
	
	public void readFields(Element formElement, FormEntity form) 
			throws DaoTaskException {
		for (Iterator<Element> i = formElement.elementIterator(); i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("field")) {
            	FieldEntity field = new FieldEntity();
            	field.setFormId(form.getId());
            	field.setName(element.attributeValue("name"));
            	field.setTitle(element.attributeValue("title"));
            	try {
            	    field.setFieldType(FieldType.valueOf(element
            	    		.attributeValue("fieldType")));
            	}
            	catch (Exception e) {
            		field.setFieldType(FieldType.TEXT);
            	}
            	field.setMandatory(XmlUtil.readBooleanAttr(element, 
            			"mandatory", false));
            	field.setValues(element.attributeValue("values"));
            	field.setDefaultValue(element.attributeValue("defaultValue"));
            	field.setHeight(XmlUtil.readIntAttr(element, "height", 0));
            	field.setWidth(XmlUtil.readIntAttr(element, "width", 20));
            	field.setIndex(XmlUtil.readIntAttr(element, "index", 0));
            	getDaoTaskAdapter().fieldSave(field);
            }
		}		
	}

	public void readFormConfig(Element configElement) throws DaoTaskException {
		FormConfigEntity config = getDao().getFormConfigDao().getConfig();
		for (Iterator<Element> i = configElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("formTemplate")) {
            	config.setFormTemplate(element.getText());
            }
            if (element.getName().equals("letterTemplate")) {
            	config.setLetterTemplate(element.getText());
            }
		}
		getDaoTaskAdapter().formConfigSave(config);
	}
	
	/**
	 * Read and import data from _forms.xml file.
	 * @param xml - _forms.xml content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	public void readFormsFile(String xml) throws DocumentException, 
			DaoTaskException {
		Document doc = DocumentHelper.parseText(xml);
		readForms(doc.getRootElement());
	}
}
