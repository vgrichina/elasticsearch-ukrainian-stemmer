package com.componentix.nlp.stemmer.uk.elasticsearch;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.core.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.analysis.miscellaneous.*;
import org.apache.lucene.analysis.util.StopwordAnalyzerBase;
import org.apache.lucene.util.Version;
import org.elasticsearch.ElasticSearchIllegalArgumentException;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.inject.assistedinject.Assisted;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.Index;
import org.elasticsearch.index.analysis.AbstractIndexAnalyzerProvider;
import org.elasticsearch.index.settings.IndexSettings;

import java.io.IOException;
import java.io.Reader;

public class UkrainianStemmerAnalyzerProvider extends AbstractIndexAnalyzerProvider<Analyzer> {

    private final Analyzer analyzer = new StopwordAnalyzerBase(Version.LUCENE_40) {
        @Override
        protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
            final Tokenizer source = new  StandardTokenizer(matchVersion, reader);
            TokenStream result = new LowerCaseFilter(matchVersion, source);
            result = new StopFilter(matchVersion, result, stopwords);

            // TODO: use stemExclusionSet
            /*
            if (!stemExclusionSet.isEmpty()) result = new KeywordMarkerFilter(
                result, stemExclusionSet);
            */

            // TODO: Create stemmer
            // result = new SnowballFilter(result, new org.tartarus.snowball.ext.RussianStemmer());
            return new TokenStreamComponents(source, result);
        }
    };


    @Inject
    public UkrainianStemmerAnalyzerProvider(Index index, @IndexSettings Settings indexSettings,
                                             @Assisted String name, @Assisted Settings settings) {
        super(index, indexSettings, name, settings);
    }

    @Override
    public Analyzer get() {
        return this.analyzer;
    }
}
