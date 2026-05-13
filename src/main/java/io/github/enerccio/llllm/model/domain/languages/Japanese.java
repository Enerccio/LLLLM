package io.github.enerccio.llllm.model.domain.languages;

import io.github.enerccio.llllm.model.domain.Language;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;

@Entity
public class Japanese extends Language {

    @Lob
    private String wanikaniApiKey;

    @Lob
    private String noFuriganaList;

    public String getWanikaniApiKey() {
        return wanikaniApiKey;
    }

    public void setWanikaniApiKey(String wanikaniApiKey) {
        this.wanikaniApiKey = wanikaniApiKey;
    }

    public String getNoFuriganaList() {
        return noFuriganaList;
    }

    public void setNoFuriganaList(String noFuriganaList) {
        this.noFuriganaList = noFuriganaList;
    }
}
