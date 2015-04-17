

package com.heiduc.business.impl.imex;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.heiduc.business.imex.PluginExporter;
import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.PluginEntity;

/**
 * @author Alexander Oleynik
 */
public class PluginExporterImpl extends AbstractExporter 
		implements PluginExporter {

	public PluginExporterImpl(ExporterFactoryImpl factory) {
		super(factory);
	}
	
	@Override
	public String createPluginsXML() {
		Document doc = DocumentHelper.createDocument();
		Element pluginsElement = doc.addElement("plugins");
		createPluginsXML(pluginsElement);
		return doc.asXML();
	}

	private void createPluginsXML(Element pluginsElement) {
		List<PluginEntity> list = getDao().getPluginDao().select();
		for (PluginEntity plugin : list) {
			createPluginXML(pluginsElement, plugin);
		}
	}

	private void createPluginXML(Element pluginsElement, 
			final PluginEntity plugin) {
		Element pluginElement = pluginsElement.addElement("plugin");
		pluginElement.addElement("name").setText(plugin.getName());
		pluginElement.addElement("configData").setText(plugin.getConfigData());
	}
	
	public void readPlugins(Element PluginsElement) throws DaoTaskException {
		for (Iterator<Element> i = PluginsElement.elementIterator(); 
				i.hasNext(); ) {
            Element element = i.next();
            if (element.getName().equals("plugin")) {
            	String name = element.elementText("name");
            	PluginEntity plugin = getDao().getPluginDao().getByName(name);
            	if (plugin == null) {
            		continue;
            	}
            	plugin.setName(name);
            	plugin.setConfigData(element.elementText("configData"));
            	getDaoTaskAdapter().pluginSave(plugin);
            }
		}		
	}
	
	/**
	 * Read and import data from _plugins.xml file.
	 * @param xml - _plugins.xml file content.
	 * @throws DocumentException
	 * @throws DaoTaskException
	 */
	public void readPluginsFile(String xml) throws DocumentException, 
			DaoTaskException {
		Document doc = DocumentHelper.parseText(xml);
		readPlugins(doc.getRootElement());
	}
}
