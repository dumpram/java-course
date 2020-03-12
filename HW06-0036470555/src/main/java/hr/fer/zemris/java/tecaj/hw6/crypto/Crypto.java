package hr.fer.zemris.java.tecaj.hw6.crypto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
     * Main method for Crypto class does actions described in class
     * documentation depending on given arguments from command line. Arguments
     * are: <li>checksha</li> <li>encrypt</li> <li>decrypt</li> <br>
     * For more information check HW06.pdf file.
     * 
     * @param args
     *            input arguments
     * @throws NoSuchAlgorithmException
     *             not bloody likely
     * @throws IOException
     *             Input Output Exception
     * @throws NoSuchPaddingException
     *             If padding isn't right
     * @throws IllegalBlockSizeException
     *             Illegal size of Block
     * @throws InvalidKeyException
     *             in case of invalid key
     * @throws InvalidAlgorithmParameterException
     *             in case of invalid parameters
     * @throws BadPaddingException
     *             If padding is bad
     */
    public static void main(String[] args) throws NoSuchAlgorithmException,
	    IOException, NoSuchPaddingException, IllegalBlockSizeException,
	    InvalidKeyException, InvalidAlgorithmParameterException,
	    BadPaddingException {
	MessageDigest md = MessageDigest.getInstance("SHA1");
	Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
	FileInputStream input;
	FileOutputStream output;
	Scanner scanner = new Scanner(System.in);
	if (args.length == 2) {
	    if (args[0].compareTo("checksha") == 0
		    && Files.exists(Paths.get(args[1]))) {
		String expectedSignature = readExpectedSig(args, scanner);
		input = new FileInputStream(args[1]);
		if ((expectedSignature).compareTo(bytetohex(
			digestSignature(md, input)).toString()) == 0) {
		    System.out
			    .println("Digesting completed. Digest of file1.pdf matches expected digest.");
		} else {
		    System.out
			    .println("Digesting completed. Digest of file1.pdf does not match the expected digest. Digest was: "
				    + bytetohex(digestSignature(md, input)));
		}
	    } else {
		scanner.close();
		throw new IllegalArgumentException(
			"Invalid arguments provided!");
	    }
	} else if (args.length == 3) {
	    if (args[0].compareTo("encrypt") == 0) {
		SecretKey aesKey = readAesKey(scanner);
		AlgorithmParameterSpec paramSpec = readAesParamSpec(scanner);
		aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, paramSpec);
		input = new FileInputStream(args[1]);
		output = new FileOutputStream(args[2]);
		crypter(input, output, aesCipher);
		System.out.println("Encryption completed. Generated file "
			+ Paths.get(args[2]).getFileName() + " based on file "
			+ Paths.get(args[1]).getFileName() + ".");
	    } else if (args[0].compareTo("decrypt") == 0) {
		SecretKey aesKey = readAesKey(scanner);
		AlgorithmParameterSpec paramSpec = readAesParamSpec(scanner);
		aesCipher.init(Cipher.DECRYPT_MODE, aesKey, paramSpec);
		input = new FileInputStream(args[1]);
		output = new FileOutputStream(args[2]);
		crypter(input, output, aesCipher);
		System.out.println("Decryption completed. Generated file "
			+ Paths.get(args[2]).getFileName() + " based on file "
			+ Paths.get(args[1]).getFileName() + ".");
	    }
	} else {
	    scanner.close();
	    throw new IllegalArgumentException("Invalid number of arguments!");
	}
	scanner.close();
    }

    /**
     * Reads expected signature from args with scanner given.
     * 
     * @param args
     *            given arguments
     * @param scanner
     *            given scanner
     * @return string representing expected signature
     */
    private static String readExpectedSig(String[] args, Scanner scanner) {
	System.out.println("Please provide expected sha signature for "
		+ Paths.get(args[1]).getFileName() + ":");
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
    private static AlgorithmParameterSpec readAesParamSpec(Scanner scanner) {
	System.out
		.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
	System.out.print("> ");
	AlgorithmParameterSpec paramSpec = new IvParameterSpec(
		hextobyte(scanner.nextLine()));
	return paramSpec;
    }

    /**
     * Read AES key from given scanner and returns it as SecretKey.
     * 
     * @param scanner
     *            given scanner object
     * @return SecretKey created from key read with scanner
     */
    private static SecretKey readAesKey(Scanner scanner) {
	System.out
		.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits: ");
	System.out.print("> ");
	SecretKey aesKey = new SecretKeySpec(hextobyte(scanner.nextLine()),
		"AES");
	return aesKey;
    }

    /**
     * Method creates signature depending on input parameters. It returns array
     * of bytes representing signature based on certain MessageDigest. Data is
     * read from given file input stream. 4k of data is read per one update.
     * 
     * @param md
     * @param fis
     * @return
     * @throws IOException
     */
    private static byte[] digestSignature(MessageDigest md, FileInputStream fis)
	    throws IOException {
	byte[] dataBytes = new byte[4096];
	int nread = 0;
	while ((nread = fis.read(dataBytes)) != -1) {
	    md.update(dataBytes, 0, nread);
	}
	byte[] mdbytes = md.digest();
	return mdbytes;
    }

    /**
     * Converts array of bytes in Hex digits and stores it in StringBuffer.
     * 
     * @param dataRead
     *            given data in byte array.
     * @return StringBuffer containing hexadecimal representation of given data.
     */
    private static StringBuffer bytetohex(byte[] dataRead) {
	StringBuffer sb = new StringBuffer("");
	for (int i = 0; i < dataRead.length; i++) {
	    sb.append(Integer.toString((dataRead[i] & 0xff) + 0x100, 16)
		    .substring(1));
	}
	return sb;
    }

    /**
     * Converts hexString to byte array.
     * 
     * @param hexString
     *            given hexString.
     * @return byte array which is representation of givne hexString data.
     */
    private static byte[] hextobyte(String hexString) {
	int len = hexString.length();
	byte[] data = new byte[len / 2];
	for (int i = 0; i < len; i += 2) {
	    data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
		    .digit(hexString.charAt(i + 1), 16));
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
    static void crypter(InputStream input, OutputStream output, Cipher aesCipher)
	    throws IOException, IllegalBlockSizeException, BadPaddingException {
	int dataRead;
	byte[] spremnik = new byte[4096];
	byte[] spremnik2 = null;
	while ((dataRead = input.read(spremnik)) != -1) {
	    if (dataRead == spremnik.length) {
		spremnik2 = aesCipher.update(spremnik);
	    } else {
		spremnik2 = aesCipher.doFinal(spremnik, 0, dataRead);
	    }
	    output.write(spremnik2);
	}
    }

}
