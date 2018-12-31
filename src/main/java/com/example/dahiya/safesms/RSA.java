package com.example.dahiya.safesms;



import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;

public class RSA
{
//    private BigInteger p;
//    private BigInteger q;
//    private BigInteger N;
//    private BigInteger phi;
//    private BigInteger e;
//    private BigInteger d;
//    private int        bitlength = 100;
//    private Random     r;
//
//    public RSA()
//    {
//        r = new Random();
//        p = BigInteger.probablePrime(bitlength, r);
//        q = BigInteger.probablePrime(bitlength, r);
//        N = p.multiply(q);
//        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
//        e = BigInteger.probablePrime(bitlength / 2, r);
//        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
//        {
//            e.add(BigInteger.ONE);
//        }
//        d = e.modInverse(phi);
//        System.out.println("p: " + p);
//    }
//
//    public RSA(BigInteger e, BigInteger d, BigInteger N)
//    {
//        this.e = e;
//        this.d = d;
//        this.N = N;
//    }

    @SuppressWarnings("deprecation")
//    public static void main(String[] args) throws IOException
//    {
//        RSA rsa = new RSA();
//        DataInputStream in = new DataInputStream(System.in);
//        String teststring;
//        System.out.println("Enter the plain text:");
//        teststring = "abcd";
//        System.out.println("Encrypting String: " + teststring);
//        System.out.println("String in Bytes: "
//                + bytesToString(teststring.getBytes()));
//        // encrypt
//        byte[] encrypted = rsa.encrypt(teststring.getBytes());
//        // decrypt
//        byte[] decrypted = rsa.decrypt(encrypted);
//        System.out.println("Decrypting Bytes: " + bytesToString(decrypted));
//        System.out.println("Decrypted String: " + new String(decrypted));
//    }

//    private static String bytesToString(byte[] encrypted)
//    {
//        String test = "";
//        for (byte b : encrypted)
//        {
//            test += Byte.toString(b);
//        }
//        return test;
//    }

    // Encrypt message
    public byte[] encrypt(byte[] message,String N,String E)
    {
        BigInteger n=new BigInteger(N);
        BigInteger e=new BigInteger(E);
        return (new BigInteger(message)).modPow(e, n).toByteArray();
    }

    // Decrypt message
//    public byte[] decrypt(byte[] message,String N,String D)
    public byte[] decrypt(byte[] message,String N,String D)  {
        BigInteger n=new BigInteger(N);
        BigInteger d=new BigInteger(D);
//        Log.d("check",(new String(message,"ISO-8859-1")));
        Log.d("d_n",D+"  "+N);
        return (new BigInteger(message)).modPow(d, n).toByteArray();

        //Log.d("check",(new String(b,"ISO-8859-1")));
        //return b;
    }
}
