package com.componentix.nlp.stemmer.uk.elasticsearch;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.tokenattributes.KeywordAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.elasticsearch.ElasticSearchIllegalArgumentException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractTokenFilterFactory;
import org.elasticsearch.index.settings.IndexSettings;

import java.io.IOException;

import com.componentix.nlp.stemmer.uk.Stemmer;

public class UkrainianStemmerTokenFilterFactory extends AbstractTokenFilterFactory {
    private final Stemmer stemmer = new Stemmer();

    @Inject
    public UkrainianStemmerTokenFilterFactory(Index index, @IndexSettings Settings indexSettings, String name, Settings settings) {
        super(index, indexSettings, name, settings);
    }

    @Override
    public TokenStream create(TokenStream tokenStream) {
        return new TokenFilter(tokenStream) {
            private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
            private final KeywordAttribute keywordAttr = addAttribute(KeywordAttribute.class);

            @Override
            public final boolean incrementToken() throws IOException {
                if (!input.incrementToken()) return false;

                if (!keywordAttr.isKeyword()) {
                    String result = stemmer.stem(new String(termAtt.buffer(), 0, termAtt.length()));
                    termAtt.setEmpty().append(result);
                }
                return true;
            }
        };
    }
}
