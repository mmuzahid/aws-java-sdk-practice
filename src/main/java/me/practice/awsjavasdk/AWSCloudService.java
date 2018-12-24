package me.practice.awsjavasdk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.spec.InvalidKeySpecException;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudfront.CloudFrontUrlSigner;
import com.amazonaws.services.cloudfront.util.SignerUtils.Protocol;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

/**
 * Class for AWS Services e.g. S3 and CloudFront
 */
public class AWSCloudService {
	public static String getCloudfrontSignedURL(String s3ObjectKey) throws InvalidKeySpecException, IOException {
		Protocol protocol = Protocol.https;
		String distributionDomain = AppConfig.getCloudfrontUrl();
		String privateKeyPath = ClassLoader.getSystemResource(AppConfig.getCloudfrontPrivateKeyFileName()).getPath();

		File privateKeyFile = new File(privateKeyPath);

		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += AppConfig.getCloudfrontUrlExpiration();
		expiration.setTime(expTimeMillis);

		String keyPairId = AppConfig.getCloudfrontKeypairId();
		return CloudFrontUrlSigner.getSignedURLWithCannedPolicy(protocol, distributionDomain, privateKeyFile,
				s3ObjectKey, keyPairId, expiration);
	}

	public static String getS3SignedURL(String s3ObjectKey) {
		String clientRegion = AppConfig.getS3ClientRegion();
		String bucketName = AppConfig.getS3Bucket();

		BasicAWSCredentials awsCreds = new BasicAWSCredentials(AppConfig.getS3AccessKey(), AppConfig.getS3SecretKey());

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += AppConfig.getS3UrlExpiration();
		expiration.setTime(expTimeMillis);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				s3ObjectKey).withMethod(HttpMethod.GET).withExpiration(expiration);
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return url.toString();
	}

}
