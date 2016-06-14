package org.heiduc.api.taskqueue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.heiduc.api.http.HttpRequest;
import org.heiduc.api.taskqueue.TaskOptions.Param;
import org.heiduc.api.util.Constants;


public class TaskThread implements Runnable {

	private TaskOptions options;
	
	public TaskThread(TaskOptions options) {
		super();
		this.options = options;
	}

	@Override
	public void run() {
		try {
			String url = Constants.TASKQUEUE_SERVER+options.getUrl();
			List<Param> params = options.getParams();
			Map<String, String> map = new HashMap<String, String>();
			for (Param param : params) {
				map.put(param.getURLEncodedName(), param.getURLEncodedValue());
			}
			HttpRequest.post(url).form(map, HttpRequest.CHARSET_UTF8).body();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
