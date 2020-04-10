package com.srini

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.{IvParameterSpec, SecretKeySpec}
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import java.security.spec.KeySpec
import javax.crypto.spec.PBEKeySpec
import scala.util.Try
import scala.util.Success
import scala.util.Failure

object Converter {
  //You can give any Secret key. If you loose this key then decryption gives wrong result
  val secretKeyString = "boooooooooom!!!!"
  val salt = "ssshhhhhhhhhhh!!!!"
  def decrypt(strToDecrypt : String) : String = {
    val result = Try({
        val iv = Array[Byte]( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 );
        val ivspec = new IvParameterSpec(iv);
         
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = new PBEKeySpec(secretKeyString.toCharArray(), salt.getBytes(), 65536, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = new SecretKeySpec(tmp.getEncoded(), "AES")
         
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec)
        new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    })
    result match {
      case Success(s) => {s}
      case Failure(f) => {throw f}
    }
  }
  
  def encrypt(strToEncrypt : String) : String = {
    val result = Try({
        val iv = Array[Byte]( 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 );
        val ivspec = new IvParameterSpec(iv);
         
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = new PBEKeySpec(secretKeyString.toCharArray(), salt.getBytes(), 65536, 256)
        val tmp = factory.generateSecret(spec)
        val secretKey = new SecretKeySpec(tmp.getEncoded(), "AES")
         
        val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec)
        new String(Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8"))));
    })
    result match {
      case Success(s) => {s}
      case Failure(f) => {throw f}
    }
  }
}