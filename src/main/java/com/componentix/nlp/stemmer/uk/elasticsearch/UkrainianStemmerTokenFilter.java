package com.componentix.nlp.stemmer.uk.elasticsearch;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

import com.componentix.nlp.stemmer.uk.Stemmer;

public class UkrainianStemmerTokenFilter extends TokenFilter {
    private final Stemmer stemmer = new Stemmer();

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

    public UkrainianStemmerTokenFilter(TokenStream tokenStream) {
        super(tokenStream);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (!input.incrementToken()) return false;

        if (!keywordAttr.isKeyword()) {
            String result = stemmer.stem(new String(termAtt.buffer(), 0, termAtt.length()));
            termAtt.setEmpty().append(result);
        }
        return true;
    }
}
