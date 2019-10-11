package delta.common.utils.configuration;

import delta.common.utils.misc.HexString2BufferCodec;

/**
 * Simple crypt/decrypt methods.
 * @author DAM
 */
public class SimpleCryptDecrypt
{
  /**
   * Crypt the specified string.
   * @param valueToCrypt String to crypt.
   * @return The crypted buffer or null if encryption problem.
   */
  public byte[] crypt(String valueToCrypt)
  {
    return valueToCrypt.getBytes();
  }

  /**
   * Decrypt the specified buffer.
   * @param cryptedValue The buffer to decrypt.
   * @return The decrypted string or <code>null</code> if decryption problem.
   */
  public String decrypt(byte[] cryptedValue)
  {
    return new String(cryptedValue);
  }

  /**
   * Crypt/decrypt method.
   * @param args the command-line arguments
   */
  public static void main(String[] args)
  {
    if(args.length==2)
    {
      SimpleCryptDecrypt crypt=new SimpleCryptDecrypt();
      String command=args[0];
      String value=args[1];
      if(command.equals("C"))
      {
        byte[] crypted=crypt.crypt(value);
        String printableBuffer=HexString2BufferCodec.bufferToString(crypted);
        System.out.println("["+value+"]->["+printableBuffer+"]");
      }
      else
      {
        if(command.equals("D"))
        {
          byte[] buffer=HexString2BufferCodec.stringToBuffer(value);
          String decodedString=crypt.decrypt(buffer);
          System.out.println("["+value+"]->["+decodedString+"]");
        }
      }
    }
  }
}
