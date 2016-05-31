package org.heiduc.api.taskqueue;

import java.util.List;

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
//			System.out.println("执行具体业务");
//			HttpClient client = new HttpClient(new URI(Constants.TASKQUEUE_SERVER+options.getUrl()));
			String url = Constants.TASKQUEUE_SERVER+options.getUrl();
			List<Param> params = options.getParams();
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < params.size(); i++) {
				Param  param = params.get(i);
				String name = param.getURLEncodedName();
				String value = param.getURLEncodedValue();
//				System.out.println("name = "+URLDecoder.decode(name,"UTF-8"));
//				System.out.println("value length = "+URLDecoder.decode(value,"UTF-8").getBytes().length);
				sb.append(name);
				sb.append("=");
				sb.append(value);
				sb.append("&");
			}
//			HttpResponse response = client.sendData(HTTP_METHOD.POST, sb.toString());
//			String data = response.getData();
//			String data = 
			HttpRequest.post(url+"?"+sb.toString());
//			System.out.println("data"+data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
