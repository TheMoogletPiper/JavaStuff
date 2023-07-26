package wikiFacts;

import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.simple.JSONObject;

public class WhatsappSender {
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	static void sendMsg(String msg){
		
		System.out.println("About to send a message!");
		
		//Create client
		CloseableHttpClient cHttpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("https://graph.facebook.com/v17.0/102722509546221/messages");
		
		//Add header
		httpPost.addHeader("Authorization", "Bearer YOUR_TOKEN");
		httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
		
		//Add body
		JSONObject jsonBodyText = new JSONObject();
		jsonBodyText.put("preview_url", "false");
		jsonBodyText.put("body", msg.toString());
		
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("messaging_product", "whatsapp");
		jsonBody.put("recipient_type", "individual");
		jsonBody.put("to", "YOUR_NUMBER");
		jsonBody.put("type", "text");
		jsonBody.put("text", jsonBodyText.toString());
		httpPost.setEntity(new StringEntity(jsonBody.toString()));
		
		//Execute and get response
		try {
			HttpResponse httpResponse = cHttpClient.execute(httpPost);
			System.out.println("Request finished: Code " + httpResponse.getCode());
			System.out.println("Message: " + httpResponse.getReasonPhrase());
			cHttpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
        

}
