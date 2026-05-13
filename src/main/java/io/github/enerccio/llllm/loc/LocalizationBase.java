package io.github.enerccio.llllm.loc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class LocalizationBase implements Localization {

    private final static Logger log = LoggerFactory.getLogger(LocalizationBase.class);
    private final Map<L, String> messages = new HashMap<>();

    protected abstract void loadMessages();

    public LocalizationBase() {
        loadMessages();

        checkLocalization();
    }

    protected void checkLocalization() {
        L[] keys = L.values();
        StringBuilder missingKeys = new StringBuilder();

        for(L key : keys) {
            if(!messages.containsKey(key)) {
                missingKeys.append(key.name());
                missingKeys.append("\n");
            }
        }

        evaluateMissingLocalization(missingKeys);
    }

    protected void evaluateMissingLocalization(StringBuilder missingKeys) {
        String missing = missingKeys.toString();

        if(!missing.isEmpty()) {
            if(log.isErrorEnabled()) {
                log.error("\n*********** LOCALIZATION IS MISSING! ***********\n"
                        + missing
                        + "\n************************************************\n");
            }
            else {
                System.err.println("\n*********** LOCALIZATION IS MISSING! ***********");
                System.err.println(missing);
                System.err.println("************************************************\n");
            }

            throw new IllegalStateException();
        }
    }

    @Override
    public String getValue(L l) {
        if (messages.containsKey(l)) {
            return messages.get(l);
        } else {
            log.error("Key {} is not localized!", l.name());
            return "NOT LOCALIZED!";
        }
    }

    public void setValue(L l, String caption) {
        messages.put(l, caption);
    }

    @Override
    public L parseValue(String text) {
        for(Map.Entry<L, String> entry : messages.entrySet()) {
            if(entry.getValue().equals(text)) {
                return entry.getKey();
            }
        }

        return null;
    }

    @Override
    public Locale getLocale() {
        return Locale.getDefault();
    }

    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat("dd.MM.yyyy", getLocale());
    }

    @Override
    public DateFormat getHourFormat() {
        return new SimpleDateFormat("HH:mm:ss", getLocale());
    }

    @Override
    public DateFormat getDateHourFormat() {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", getLocale());
    }

    @Override
    public Comparator<String> createNaturalLanguageComparator() {
        return new NaturalOrderComparator(Collator.getInstance(getLocale()));
    }

    @Override
    public Comparator<String> createLocaleComparator() {
        Collator collator = Collator.getInstance(getLocale());
        return collator::compare;
    }

}
