package com.example.bookkeeping.helper;

import com.example.bookkeeping.model.Account;
import lombok.experimental.UtilityClass;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

@UtilityClass
public class PasswordHelper {
    private static final RandomNumberGenerator RANDOM_NUMBER_GENERATOR = new SecureRandomNumberGenerator();
    private static final String ALGORITHM_NAME = "md5";
    private static final int HASH_ITERATIONS = 2;

    public static void encryptPassword(Account user) {
        user.setSalt(RANDOM_NUMBER_GENERATOR.nextBytes().toString());
        String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toString();
        user.setPassword(newPassword);
    }

    public static String getPassword(Account user) {
        return new SimpleHash(ALGORITHM_NAME, user.getPassword(), ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toString();
    }

    public static String makePassword(String password, String salt){
        return new SimpleHash(ALGORITHM_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toString();
    }

    public static void main(String[] args) {
        Account user = new Account();
        user.setUsername("13101611013");
        user.setPassword("123123");
        encryptPassword(user);
        System.out.println(user);
        System.out.println(getPassword(user));
        System.out.println(makePassword("123123", user.getCredentialsSalt()));
    }
}
