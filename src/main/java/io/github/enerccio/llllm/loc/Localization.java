package io.github.enerccio.llllm.loc;


import io.github.enerccio.llllm.model.domain.collections.AIType;
import io.github.enerccio.llllm.model.domain.collections.ProtocolType;

import java.text.DateFormat;
import java.util.Comparator;
import java.util.Locale;

public interface Localization {

    String getValue(L l);
    void setValue(L l, String caption);
    L parseValue(String text);
    Locale getLocale();
    DateFormat getDateFormat();
    DateFormat getHourFormat();
    DateFormat getDateHourFormat();
    Comparator<String> createNaturalLanguageComparator();
    Comparator<String> createLocaleComparator();

    L getAIType(AIType aiType);
    L getProtocolType(ProtocolType t);
}
