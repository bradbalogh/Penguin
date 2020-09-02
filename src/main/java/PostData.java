import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PostData {

    // here we will post data to the server in json format
    public static void postJson (Integer average, ArrayList<String> IDs) throws IOException {
        // create json object
        JSONObject jsonData = new JSONObject();
        jsonData.put("id", "bradsbalogh@gmail.com");
        jsonData.put("name", "Bradley Balogh");
        jsonData.put("average", average);
        jsonData.put("studentIds", IDs);

        // establishing a connection to the server and posting json data
        String query_url = "http://54.90.99.192:5000/challenge";
        String json = jsonData.toString();
        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            System.out.println("Sending json data to the server...");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            System.out.print("Server Response: ");
            System.out.println(result);
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
