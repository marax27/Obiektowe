package Crypto;

public interface Algorithm {
    public String crypt(String plain_word);
    public String decrypt(String cipher_word);
}
