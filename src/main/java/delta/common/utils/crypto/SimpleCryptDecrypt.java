package delta.common.utils.crypto;

import delta.common.utils.misc.HexString2BufferCodec;

public class SimpleCryptDecrypt
{
  /**
   * Crypt the specified string.
   * @param valueToCrypt_p String to crypt.
   * @return The crypted buffer or null if encryption problem.
   */
  public byte[] crypt(String valueToCrypt_p)
  {
    return valueToCrypt_p.getBytes();
  }

  /**
   * Decrypt the specified buffer.
   * @param cryptedValue_p The buffer to decrypt.
   * @return The decrypted string or null if decryption problem.
   */
  public String decrypt(byte[] cryptedValue_p)
  {
    return new String(cryptedValue_p);
  }

  /**
   * Crypt/decrypt method.
   * @param args_p the command-line arguments
   */
  public static void main(String[] args_p)
  {
    if(args_p.length==2)
    {
      SimpleCryptDecrypt crypt=new SimpleCryptDecrypt();
      String command_l=args_p[0];
      String value_l=args_p[1];
      if(command_l.equals("C"))
      {
        byte[] crypted_l=crypt.crypt(value_l);
        String printableBuffer_l=HexString2BufferCodec.bufferToString(crypted_l);
        System.out.println("["+value_l+"]->["+printableBuffer_l+"]");
      }
      else
      {
        if(command_l.equals("D"))
        {
          byte[] buffer_l=HexString2BufferCodec.stringToBuffer(value_l);
          String decodedString_l=crypt.decrypt(buffer_l);
          System.out.println("["+value_l+"]->["+decodedString_l+"]");
        }
      }
    }
  }
}
