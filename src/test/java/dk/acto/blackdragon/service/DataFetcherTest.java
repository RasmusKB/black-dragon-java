package dk.acto.blackdragon.service;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import java.net.URL;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DataFetcherTest {

    @Test(groups = "fetch")
    public void testFetchData(ITestContext context) throws Exception {

        DataFetcher subject = new DataFetcher() {
            @Override
            public String fetchData(URL url) {
				try {
					OkHttpClient client = new OkHttpClient();
					Request request = new Request.Builder().url(url).build();
					try (Response response = client.newCall(request).execute()) {
						return response.body().string();
					}
				} catch(Exception excep) {
					return null;
				}
			}
        };

        String result = subject.fetchData(new URL("https://dragon.acto.dk/test.csv"));
        assertNotNull(result);
        assertEquals(result.length(), 508);
        context.setAttribute("data", result);
    }
}
