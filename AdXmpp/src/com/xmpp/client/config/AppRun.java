package com.xmpp.client.config;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

import com.xmpp.client.db.DataDao;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.security.KeyPairGeneratorSpec;

public class AppRun extends Application {
	public static DataDao dataDao;
	public static SharedPreferences sp;
	public static KeyStore keyStore;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		dataDao = new DataDao(getApplicationContext());
		sp = getSharedPreferences(InfoConfig.APP_SET, Context.MODE_PRIVATE);
		try {
			keyStore = KeyStore.getInstance(InfoConfig.KEY_STORE);
			keyStore.load(null);
			if (!keyStore.containsAlias(InfoConfig.PAIR_KEY_ALIAS)) {
				Calendar start = Calendar.getInstance();
				Calendar end = Calendar.getInstance();
				end.add(Calendar.YEAR, 1);
				KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(this)
						.setAlias(InfoConfig.PAIR_KEY_ALIAS)
						.setSubject(new X500Principal("CN=androidsdk, O=Android Authority"))
						.setSerialNumber(BigInteger.ONE)
						.setStartDate(start.getTime())
						.setEndDate(end.getTime())
						.build();
				KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
				generator.initialize(spec);
				generator.generateKeyPair();
			}
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
