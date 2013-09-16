package com.componentix.nlp.stemmer.uk.elasticsearch;

import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

public class UkrainianStemmerPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "ukrainian-stemmer";
    }

    @Override
    public String description() {
        return "Ukrainian stemming support";
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new UkrainianStemmerBinderProcessor());
    }
}
