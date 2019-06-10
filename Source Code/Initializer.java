public class Initializer
{
   //hiragana and katakana use the same character sounds
   //initialized here due to eventual kanji implementaion (i.e. kanjiWordArray1, kanjiWordArray2, etc)
   public final String[] basicCharacterArray = {"a","i","u","e","o","ka","ki","ku","ke","ko","sa","shi","su","se","so","ta","chi","tsu","te","to","na","ni","nu","ne","no","ha","hi","fu","he","ho","ma","mi","mu","me","mo","ya","yu","yo","ra","ri","ru","re","ro","wa","wo","n"};
   public final String[] aCharacterArray = {"a","ka","sa","ta","na","ha","ma","ya","ra","wa","n"};
   public final String[] eCharacterArray = {"e","ke","se","te","ne","he","me","re"};
   public final String[] iCharacterArray = {"i","ki","shi","chi","ni","hi","mi","ri"};
   public final String[] oCharacterArray = {"o","ko","so","to","no","ho","mo","yo","ro","wo"};
   public final String[] uCharacterArray = {"u","ku","su","tsu","nu","fu","mu","yu","ru"};
   public final String hiraganaDefaultPath = "HiraganaImages/";
   public final String katakanaDefaultPath = "KatakanaImages/";

//getters for the initialized values
//////////////////////////////////////////////////////////////////////////////
   public String[] getBasicCharArray()
   {
      return basicCharacterArray;
   }
   
   public String[] getACharArray()
   {
      return aCharacterArray;
   }
   
    public String[] getECharArray()
   {
      return eCharacterArray;
   }
   
    public String[] getICharArray()
   {
      return iCharacterArray;
   }
   
    public String[] getOCharArray()
   {
      return oCharacterArray;
   }
   
    public String[] getUCharArray()
   {
      return uCharacterArray;
   }
   
// Image path getters
////////////////////////////////////////////////////////////////////////
   public String getHiraganaImagePath()
   {
      return hiraganaDefaultPath;
   }
   
   public String getKatakanaImagePath()
   {
      return katakanaDefaultPath;
   }
}