package me.practice.awsjavasdk;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

	private static Properties props;

	static {
		loadProps();
	}

	private static void loadProps() {
		try (InputStream propStream = ClassLoader.getSystemResourceAsStream("app.properties")) {
			props = new Properties();
			props.load(propStream);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Filed to load app.properties");
		}
	}

	public static String getCloudfrontUrl() {
		return props.getProperty("aws.cloudfront.url");
	}

	public static long getCloudfrontUrlExpiration() {
		return Integer.valueOf(props.getProperty("aws.cloudfront.urlexpiration"));
	}

	public static String getCloudfrontKeypairId() {
		return props.getProperty("aws.cloudfront.keypairid");
	}

	public static String getS3ClientRegion() {
		return props.getProperty("aws.s3.clientregion");
	}

	public static String getS3Bucket() {
		return props.getProperty("aws.s3.bucket");
	}

	public static String getCloudfrontPrivateKeyFileName() {
		return props.getProperty("aws.cloudfront.privatekey.filename");
	}

	public static String getS3AccessKey() {
		return props.getProperty("aws.s3.accesskey");
	}

	public static String getS3SecretKey() {
		return props.getProperty("aws.s3.secretkey");
	}

	public static long getS3UrlExpiration() {
		return Integer.valueOf(props.getProperty("aws.s3.urlexpiration"));
	}

}
