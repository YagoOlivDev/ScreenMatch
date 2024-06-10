package ProjectSpring.Screenmatch.Services;

import net.suuft.libretranslate.Language;
import net.suuft.libretranslate.Translator;

public class QueryLibreTranslate
{
    public static String obterTraducao(String texto)
    {
        var resposta = Translator.translate(Language.ENGLISH, Language.PORTUGUESE, texto);
        return resposta;
    }
}

