package core;

import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;

import java.security.Security;
import java.util.Arrays;

public class Encode {
    public static String sha1Digest(String forHash) {
        Security.addProvider(new BouncyCastleProvider());

        byte[] trial = forHash.getBytes();

        // TODO Auto-generated method stub

        //GOST3411Digest examplesha = new GOST3411Digest(); //256-bits
        SHA1Digest examplesha = new SHA1Digest();
        //SHA256Digest examplesha = new SHA256Digest(); //256-bits
        //SHA384Digest examplesha = new SHA384Digest(); //384-bits
        //SHA512Digest examplesha = new SHA512Digest(); //512-bits
        //SHA3Digest examplesha = new SHA3Digest();

        examplesha.update(trial, 0, trial.length);

        byte[] digested = new byte[examplesha.getDigestSize()];
        examplesha.doFinal(digested, 0);

        return new String(Hex.encode(digested));
    }

    public static String toBase64(String toEncode) {
        byte[] stringtoByte = toEncode.getBytes();
        return Arrays.toString(Base64.encode(stringtoByte));
    }
}
