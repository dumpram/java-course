package hr.fer.zemris.java.tecaj_14.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Class emulates simple encrypter, decrypter and checker of SHA-1 signature. It
 * enables user to encrypt/decrypt given file using AES crypto-algorithm and
 * 128-bit encryption key or calculate and check SHA-1 file digest.
 * 
 * @author Ivan Pavić
 * 
 */
public class Crypto {
	/**
	 * Reads expected signature from args with scanner given.
	 * 
	 * @param args
	 *            given arguments
	 * @param scanner
	 *            given scanner
	 * @return string representing expected signature
	 */
	protected static String readExpectedSig(String[] args, Scanner scanner) {
		System.out.println("Please provide expected sha signature for " + Paths.get(args[1]).getFileName() + ":");
		System.out.print("> ");
		String expectedSignature = scanner.nextLine();
		return expectedSignature;
	}

	/**
	 * Reads initialization vector from scanner and returns it as
	 * AlgorithmParameterSpec.
	 * 
	 * @param scanner
	 *            given scanner object
	 * @return AlgorithmParameterSpec created from initialization vector read
	 *         from scanner.
	 */
	protected static AlgorithmParameterSpec readAesParamSpec(Scanner scanner) {
		System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
		System.out.print("> ");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(scanner.nextLine()));
		return paramSpec;
	}

	/**
	 * Read AES key from given scanner and returns it as SecretKey.
	 * 
	 * @param scanner
	 *            given scanner object
	 * @return SecretKey created from key read with scanner
	 */
	protected static SecretKey readAesKey(Scanner scanner) {
		System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): ");
		System.out.print("> ");
		SecretKey aesKey = new SecretKeySpec(hextobyte(scanner.nextLine()), "AES");
		return aesKey;
	}

	/**
	 * Vraća digest od ulaznog stringa.
	 * 
	 * @param input
	 *            ulazni {@link String}
	 * @return heksadekadski zapis sha1 enkripcije stringa
	 */
	public static String digest(String input) {
		return DigestUtils.sha1Hex(input);
	}

	/**
	 * Converts array of bytes in Hex digits and stores it in StringBuffer.
	 * 
	 * @param dataRead
	 *            given data in byte array.
	 * @return StringBuffer containing hexadecimal representation of given data.
	 */
	protected static StringBuffer bytetohex(byte[] dataRead) {
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < dataRead.length; i++) {
			sb.append(Integer.toString((dataRead[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb;
	}

	/**
	 * Converts hexString to byte array.
	 * 
	 * @param hexString
	 *            given hexString.
	 * @return byte array which is representation of given hexString data.
	 */
	protected static byte[] hextobyte(String hexString) {
		int len = hexString.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(
					hexString.charAt(i + 1), 16));
		}
		return data;
	}
	/**
	 * Method encrypts or decrypts given input stream based on
	 * aesCipher(depending on aesCipher mode. It can be set either to encrypt or
	 * decrypt.
	 * 
	 * @param input
	 *            given input stream
	 * @param output
	 *            given output stream
	 * @param aesCipher
	 *            given cipher to encode or decode data
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	static void crypter(InputStream input, OutputStream output, Cipher aesCipher) throws IOException,
			IllegalBlockSizeException, BadPaddingException {
		int dataRead;
		byte[] spremnik = new byte[4096];
		byte[] spremnik2 = null;
		do {

			dataRead = input.read(spremnik);
			if (dataRead == 4096) {
				spremnik2 = aesCipher.update(spremnik);
				output.write(spremnik2);
			} else if (dataRead != -1) {
				spremnik2 = aesCipher.doFinal(spremnik, 0, dataRead);
				output.write(spremnik2);
			}
		} while (dataRead != -1);
	}
}
