package com.componentix.nlp.stemmer.uk.elasticsearch;

import org.elasticsearch.index.analysis.AnalysisModule;

public class UkrainianStemmerBinderProcessor extends AnalysisModule.AnalysisBinderProcessor {
    @Override
    public void processAnalyzers(AnalyzersBindings analyzersBindings) {
        analyzersBindings.processAnalyzer("ukrainian", UkrainianStemmerAnalyzerProvider.class);
    }

    @Override
    public void processTokenFilters(TokenFiltersBindings tokenFiltersBindings) {
        tokenFiltersBindings.processTokenFilter("ukrainian", UkrainianStemmerTokenFilterFactory.class);
    }
}
