package delta.common.utils.text.ansel;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;

/**
 * A Charset to decode the GEDCOM character encoding ANSEL. <br>
 * Mainly taken from http://lcweb2.loc.gov/cocoon/codetables/45.html
 * @author sabre
 */
public class AnselCharset extends Charset
{
  private static final char[] ANSEL_CHARS=new char[256];

  private static final String[][] CONVERSION_TABLE= {
  /* E1 */{"AEIOUaeiou","ÀÈÌÒÙàèìòù"},
  /* E2 */{"ACEILNORSUYZaceilnorsuyz","ÁĆÉÍĹŃÓŔŚÚÝŹáćéíĺńóŕśúýź"},
  /* E3 */{"ACEGHIJOSUWYaceghijosuwy","ÂĈÊĜĤÎĴÔŜÛŴŶâĉêĝĥîĵôŝûŵŷ"},
  /* E4 */{"AINOUainou","ÃĨÑÕŨãĩñõũ"},
  /* E5 */{"AEIOUaeiou","ĀĒĪŌŪāēīōū"},
  /* E6 */{"AGUagu","ĂĞŬăğŭ"},
  /* E7 */{"CEGIZcegiz","ĊĖĠİŻċėġıż"},
  /* E8 */{"AEIOUYaeiouy","ÄËÏÖÜŸäëïöüÿ"},
  /* E9 */null,
  /* EA */{"AUau","ÅŮåů"},
  /* EB */{"CGKLNRSTcgklnrst","ÇĢĶĻŅŖŞŢçģķļņŗşţ"},
  /* EC */null,
  /* ED */{"OUou","ŐŰőű"},
  /* EE */{"AEIUaeiu","ĄĘĮŲąęįų"},
  /* EF */{"CDELNRSTZcdelnrstz","ČĎĚĽŇŘŠŤŽčďěľňřšťž"},
  /* F0 */{"c","ç"}};

  static
  {
    // The ASCII root
    for(int index=0;index<128;index++)
    {
      ANSEL_CHARS[index]=(char)index;
    }

    // Invalid characters translation
    java.util.Arrays.fill(ANSEL_CHARS,128,ANSEL_CHARS.length,'?');

    // The ANSEL characters
    ANSEL_CHARS[0x88]='\u0098';// NON-SORT BEGIN / START OF STRING
    ANSEL_CHARS[0x89]='\u009C';// NON-SORT END / STRING TERMINATOR
    ANSEL_CHARS[0x8D]='\u009C';// JOINER / ZERO WIDTH JOINER
    ANSEL_CHARS[0x8E]='\u009C';// NON-JOINER / ZERO WIDTH NON-JOINER
    ANSEL_CHARS[0xA1]='\u0141';// UPPERCASE POLISH L / LATIN CAPITAL
    // LETTER L WITH
    // STROKE
    ANSEL_CHARS[0xA2]='\u00D8';// UPPERCASE SCANDINAVIAN O / LATIN
    // CAPITAL LETTER O
    // WITH STROKE
    ANSEL_CHARS[0xA3]='\u0110';// UPPERCASE D WITH CROSSBAR / LATIN
    // CAPITAL LETTER
    // D WITH STROKE
    ANSEL_CHARS[0xA4]='\u00DE';// UPPERCASE ICELANDIC THORN / LATIN
    // CAPITAL LETTER
    // THORN (Icelandic)
    ANSEL_CHARS[0xA5]='\u00C6';// UPPERCASE DIGRAPH AE / LATIN CAPITAL
    // LIGATURE AE
    ANSEL_CHARS[0xA6]='\u0152';// UPPERCASE DIGRAPH OE / LATIN CAPITAL
    // LIGATURE OE
    ANSEL_CHARS[0xA7]='\u02B9';// SOFT SIGN, PRIME / MODIFIER LETTER
    // PRIME
    ANSEL_CHARS[0xA8]='\u00B7';// MIDDLE DOT
    ANSEL_CHARS[0xA9]='\u266D';// MUSIC FLAT SIGN
    ANSEL_CHARS[0xAA]='\u00AE';// PATENT MARK / REGISTERED SIGN
    ANSEL_CHARS[0xAB]='\u00B1';// PLUS OR MINUS / PLUS-MINUS SIGN
    ANSEL_CHARS[0xAC]='\u01A0';// UPPERCASE O-HOOK / LATIN CAPITAL
    // LETTER O WITH
    // HORN
    ANSEL_CHARS[0xAD]='\u01AF';// UPPERCASE U-HOOK / LATIN CAPITAL
    // LETTER U WITH
    // HORN
    ANSEL_CHARS[0xAE]='\u02BC';// ALIF / MODIFIER LETTER APOSTROPHE
    ANSEL_CHARS[0xB0]='\u02BB';// AYN / MODIFIER LETTER TURNED COMMA
    ANSEL_CHARS[0xB1]='\u0142';// LOWERCASE POLISH L / LATIN SMALL
    // LETTER L WITH
    // STROKE
    ANSEL_CHARS[0xB2]='\u00F8';// LOWERCASE SCANDINAVIAN O / LATIN
    // SMALL LETTER O
    // WITH STROKE
    ANSEL_CHARS[0xB3]='\u0111';// LOWERCASE D WITH CROSSBAR / LATIN
    // SMALL LETTER D
    // WITH STROKE
    ANSEL_CHARS[0xB4]='\u00FE';// LOWERCASE ICELANDIC THORN / LATIN
    // SMALL LETTER
    // THORN (Icelandic)
    ANSEL_CHARS[0xB5]='\u00E6';// LOWERCASE DIGRAPH AE / LATIN SMALL
    // LIGATURE AE
    ANSEL_CHARS[0xB6]='\u0153';// LOWERCASE DIGRAPH OE / LATIN SMALL
    // LIGATURE OE
    ANSEL_CHARS[0xB7]='\u02BA';// HARD SIGN, DOUBLE PRIME / MODIFIER
    // LETTER DOUBLE
    // PRIME
    ANSEL_CHARS[0xB8]='\u0131';// LOWERCASE TURKISH I / LATIN SMALL
    // LETTER DOTLESS
    // I
    ANSEL_CHARS[0xB9]='\u00A3';// BRITISH POUND / POUND SIGN
    ANSEL_CHARS[0xBA]='\u00F0';// LOWERCASE ETH / LATIN SMALL LETTER
    // ETH
    // (Icelandic)
    ANSEL_CHARS[0xBC]='\u01A1';// LOWERCASE O-HOOK / LATIN SMALL LETTER
    // O WITH HORN
    ANSEL_CHARS[0xBD]='\u01B0';// LOWERCASE U-HOOK / LATIN SMALL LETTER
    // U WITH HORN
    ANSEL_CHARS[0xC0]='\u00B0';// DEGREE SIGN
    ANSEL_CHARS[0xC1]='\u2113';// SCRIPT SMALL L
    ANSEL_CHARS[0xC2]='\u2117';// SOUND RECORDING COPYRIGHT
    ANSEL_CHARS[0xC3]='\u00A9';// COPYRIGHT SIGN
    ANSEL_CHARS[0xC4]='\u266F';// MUSIC SHARP SIGN
    ANSEL_CHARS[0xC5]='\u00BF';// INVERTED QUESTION MARK
    ANSEL_CHARS[0xC6]='\u00A1';// INVERTED EXCLAMATION MARK
    ANSEL_CHARS[0xC7]='\u00DF';// ESZETT SYMBOL
    ANSEL_CHARS[0xC8]='\u20AC';// EURO SIGN
    ANSEL_CHARS[0xE0]='\u0309';// PSEUDO QUESTION MARK / COMBINING HOOK
    // ABOVE
    ANSEL_CHARS[0xE1]='\u0300';// GRAVE / COMBINING GRAVE ACCENT
    // (Varia)
    ANSEL_CHARS[0xE2]='\u0301';// ACUTE / COMBINING ACUTE ACCENT (Oxia)
    ANSEL_CHARS[0xE3]='\u0302';// CIRCUMFLEX / COMBINING CIRCUMFLEX
    // ACCENT
    ANSEL_CHARS[0xE4]='\u0303';// TILDE / COMBINING TILDE
    ANSEL_CHARS[0xE5]='\u0304';// MACRON / COMBINING MACRON
    ANSEL_CHARS[0xE6]='\u0306';// BREVE / COMBINING BREVE (Vrachy)
    ANSEL_CHARS[0xE7]='\u0307';// SUPERIOR DOT / COMBINING DOT ABOVE
    ANSEL_CHARS[0xE8]='\u0308';// UMLAUT, DIAERESIS / COMBINING
    // DIAERESIS
    // (Dialytika)
    ANSEL_CHARS[0xE9]='\u030C';// HACEK / COMBINING CARON
    ANSEL_CHARS[0xEA]='\u030A';// CIRCLE ABOVE, ANGSTROM / COMBINING
    // RING ABOVE
    ANSEL_CHARS[0xEB]='\u0361';// LIGATURE, FIRST HALF / COMBINING
    // DOUBLE INVERTED
    // BREVEFE20EFB8A0
    ANSEL_CHARS[0xEC]='\uFE21';// LIGATURE, SECOND HALF / COMBINING
    // LIGATURE RIGHT
    // HALFFE21EFB8A1
    ANSEL_CHARS[0xED]='\u0315';// HIGH COMMA, OFF CENTER / COMBINING
    // COMMA ABOVE
    // RIGHT
    ANSEL_CHARS[0xEE]='\u030B';// DOUBLE ACUTE / COMBINING DOUBLE ACUTE
    // ACCENT
    ANSEL_CHARS[0xEF]='\u0310';// CANDRABINDU / COMBINING CANDRABINDU
    ANSEL_CHARS[0xF0]='\u0327';// CEDILLA / COMBINING CEDILLA
    ANSEL_CHARS[0xF1]='\u0328';// RIGHT HOOK, OGONEK / COMBINING OGONEK
    ANSEL_CHARS[0xF2]='\u0323';// DOT BELOW / COMBINING DOT BELOW
    ANSEL_CHARS[0xF3]='\u0324';// DOUBLE DOT BELOW / COMBINING
    // DIAERESIS BELOW
    ANSEL_CHARS[0xF4]='\u0325';// CIRCLE BELOW / COMBINING RING BELOW
    ANSEL_CHARS[0xF5]='\u0333';// DOUBLE UNDERSCORE / COMBINING DOUBLE
    // LOW LINE
    ANSEL_CHARS[0xF6]='\u0332';// UNDERSCORE / COMBINING LOW LINE
    ANSEL_CHARS[0xF7]='\u0326';// LEFT HOOK (COMMA BELOW) / COMBINING
    // COMMA BELOW
    ANSEL_CHARS[0xF8]='\u031C';// RIGHT CEDILLA / COMBINING LEFT HALF
    // RING BELOW
    ANSEL_CHARS[0xF9]='\u032E';// UPADHMANIYA / COMBINING BREVE BELOW
    ANSEL_CHARS[0xFA]='\uFE22';// DOUBLE TILDE, FIRST HALF / COMBINING
    // DOUBLE
    // TILDEFE22EFB8A2
    ANSEL_CHARS[0xFB]='\uFE23';// DOUBLE TILDE, SECOND HALF / COMBINING
    // DOUBLE
    // TILDE RIGHT HALFFE23EFB8A3
    ANSEL_CHARS[0xFE]='\u0313';// HIGH COMMA, CENTERED / COMBINING
    // COMMA ABOVE
    // (Psili)
  }

  public AnselCharset()
  {
    super("ANSEL",null);
  }

  @Override
  public CharsetDecoder newDecoder()
  {
    return new Decoder(this);
  }

  @Override
  public CharsetEncoder newEncoder()
  {
    throw new UnsupportedOperationException("ANSEL Encoding not supported");
  }

  @Override
  public boolean contains(Charset cs)
  {
    return displayName().equals(cs.displayName());
  }

  @Override
  public boolean canEncode()

  {

    return false;

  }

  static class Decoder extends CharsetDecoder
  {
    private Decoder(Charset cs)
    {
      super(cs,1.0f,1.0f);
    }

    @Override
    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out)
    {
      String[] conversionTable;
      char[] inChars, outChars;
      int length;
      while (in.hasRemaining())
      {
        if (out.hasRemaining())
        {
          int b=in.get()&0xff;
          if ((b>=0xE1)&&(b<=0xF0))
          {
            if (in.hasRemaining())
            {
              byte nextByte=in.get();
              conversionTable=CONVERSION_TABLE[b-0xE1];
              inChars=conversionTable[0].toCharArray();
              outChars=conversionTable[1].toCharArray();
              length=inChars.length;
              for(int i=0;i<length;i++)
              {
                if (nextByte==inChars[i])
                {
                  out.put(outChars[i]);
                }
              }
            }
          }
          else
          {
            out.put(ANSEL_CHARS[b]);
          }
        }
        else
        {
          return CoderResult.OVERFLOW;
        }
      }
      return CoderResult.UNDERFLOW;
    }
  }
}
