package me.practice.awsjavasdk;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

/**
 * Application Starting point
 *
 */
public class App {
	public static void main(String[] args) throws InvalidKeySpecException, IOException {
		String s3ObjectKey = "/s3/objectkey/path";
		System.out.println("CloudFront Signed URL: " + AWSCloudService.getCloudfrontSignedURL(s3ObjectKey));
		System.out.println("S3 Signed URL: " + AWSCloudService.getS3SignedURL(s3ObjectKey));
		AWSCloudService.saveS3Object(s3ObjectKey);
	}
}
