package me.practice.awsjavasdk;

import java.io.IOException;
import java.security.spec.InvalidKeySpecException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InvalidKeySpecException, IOException {
		System.out.println("Hello World!" + AppConfig.getCloudfrontUrl());
	}
}
