package com.onefin.ewallet.common.base.config;

import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.security.cert.CertificateException;

/*
okhttp version used 3.8.1
*/
public class OkHttpUtil {

  private Logger LOGGER = LoggerFactory.getLogger(OkHttpUtil.class.getName());

  private OkHttpClient client = null;
  private boolean ignoreSslCertificate = false;

  public OkHttpClient getClient() {
    return client;
  }

  public OkHttpUtil(boolean ignoreCertificate) throws Exception {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();

    LOGGER.info("Initialising httpUtil with default configuration");
    if (ignoreCertificate) {
      ignoreSslCertificate = true;
      builder = configureToIgnoreCertificate(builder);
    }

    //Other application specific configuration

    client = builder.build();
  }

  //Setting testMode configuration. If set as testMode, the connection will skip certification check
  private OkHttpClient.Builder configureToIgnoreCertificate(OkHttpClient.Builder builder) {
    LOGGER.warn("Ignore Ssl Certificate");
    try {

      // Create a trust manager that does not validate certificate chains
      final TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
          @Override
          public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
            throws CertificateException {
          }

          @Override
          public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
            throws CertificateException {
          }

          @Override
          public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
          }
        }
      };

      // Install the all-trusting trust manager
      final SSLContext sslContext = SSLContext.getInstance("SSL");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
      // Create an ssl socket factory with our all-trusting manager
      final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

      builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
      builder.hostnameVerifier(new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
          return true;
        }
      });
    } catch (Exception e) {
      LOGGER.warn("Exception while configuring IgnoreSslCertificate" + e, e);
    }
    return builder;
  }

}
