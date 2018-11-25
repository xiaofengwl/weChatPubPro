package com.xfwl.token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class NetWorkUtil {
	public String getHttpsResponse(String reqUrl, String requestMethod) {
		String result = "";
		try {
			URL url = new URL(reqUrl);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			TrustManager[] tm = { this.xtm };
			SSLContext ctx = SSLContext.getInstance("TLS");
			ctx.init(null, tm, null);

			con.setSSLSocketFactory(ctx.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
			con.setDoInput(true);

			con.setDoOutput(false);
			con.setUseCaches(false);
			if ((requestMethod != null) && (!requestMethod.equals(""))) {
				con.setRequestMethod(requestMethod);
			} else {
				con.setRequestMethod("GET");
			}
			InputStream is = con.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine;
			while ((inputLine = bufferReader.readLine()) != null) {
				result = result + inputLine + "\n";
			}
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	X509TrustManager xtm = new X509TrustManager() {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}
	};
}
