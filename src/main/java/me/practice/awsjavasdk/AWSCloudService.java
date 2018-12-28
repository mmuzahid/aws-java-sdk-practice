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
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

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
		String bucketName = AppConfig.getS3Bucket();
		AmazonS3 s3Client = getS3Client();

		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += AppConfig.getS3UrlExpiration();
		expiration.setTime(expTimeMillis);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName,
				s3ObjectKey).withMethod(HttpMethod.GET).withExpiration(expiration);
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return url.toString();
	}

	private static AmazonS3 getS3Client() {
		String clientRegion = AppConfig.getS3ClientRegion();
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(AppConfig.getS3AccessKey(), AppConfig.getS3SecretKey());

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion)
				.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		return s3Client;
	}

	
	public static void saveS3Object(String s3ObjectKey) throws IOException {
		String bucketName = AppConfig.getS3Bucket();
		AmazonS3 s3Client = getS3Client();

		String destinationFileName = "D:/" + s3ObjectKey.substring(s3ObjectKey.lastIndexOf("/") + 1);
		
		File localFile = new File(destinationFileName);
		ObjectMetadata object = s3Client.getObject(new GetObjectRequest(bucketName, s3ObjectKey), localFile);

	}
	
	public static void getCloudFrontObjectStream() {
		
	}
	
	
}
