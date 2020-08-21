package com.changgou.oauth.util;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.*;
import java.security.cert.Certificate;

/**
 * 从证书中导出公钥私钥
 */
public class ExportCert {
    //导出证书 base64格式
    public static void exportCert(KeyStore keystore, String alias, String exportFile) throws Exception {
        Certificate cert = keystore.getCertificate(alias);
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(cert.getEncoded());
        encoded = encoded.replace("\r\n", "");
        FileWriter fw = new FileWriter(exportFile);
        fw.write("-----BEGIN CERTIFICATE-----");
        fw.write(encoded);
        fw.write("-----END CERTIFICATE-----");
        fw.close();
    }

    //得到KeyPair
    public static KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) {
        try {
            Key key = keystore.getKey(alias, password);
            if (key instanceof PrivateKey) {
                Certificate cert = keystore.getCertificate(alias);
                PublicKey publicKey = cert.getPublicKey();
                return new KeyPair(publicKey, (PrivateKey) key);
            }
        } catch (UnrecoverableKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyStoreException e) {
        }
        return null;
    }

    //导出私钥
    public static void exportPrivateKey(PrivateKey privateKey,String exportFile) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(privateKey.getEncoded());
        encoded = encoded.replace("\r\n", "");
        FileWriter fw = new FileWriter(exportFile);
        fw.write("-----BEGIN PRIVATE KEY-----");
        fw.write(encoded);
        fw.write("-----END PRIVATE KEY-----");
        fw.close();
    }

    //导出公钥
    public static void exportPublicKey(PublicKey publicKey,String exportFile) throws Exception {
        BASE64Encoder encoder = new BASE64Encoder();
        String encoded = encoder.encode(publicKey.getEncoded());
        encoded = encoded.replace("\r\n", "");
        FileWriter fw = new FileWriter(exportFile);
        fw.write("-----BEGIN PUBLIC KEY-----");
        fw.write(encoded);
        fw.write("-----END PUBLIC KEY-----");
        fw.close();
    }

    public static void main(String args[]) throws Exception {

        String keyStoreType = "JKS";
        String keystoreFile = "C:/Users/Administrator/Desktop/changgou.jks";
        String password = "changgou";
        String alias = "changgou";

        KeyStore keystore = KeyStore.getInstance(keyStoreType);
        keystore.load(new FileInputStream(new File(keystoreFile)), password.toCharArray());

        String exportCertFile = "C:/Users/Administrator/Desktop/cms.cer";
        String exportPrivateFile = "C:/Users/Administrator/Desktop/cmsPrivateKey.txt";
        String exportPublicFile = "C:/Users/Administrator/Desktop/cmsPublicKey.txt";

        ExportCert.exportCert(keystore, alias, exportCertFile);
        KeyPair keyPair = ExportCert.getKeyPair(keystore, alias, password.toCharArray());
        ExportCert.exportPrivateKey(keyPair.getPrivate(), exportPrivateFile);
        ExportCert.exportPublicKey(keyPair.getPublic(), exportPublicFile);

        System.out.println("OK");
    }
}
