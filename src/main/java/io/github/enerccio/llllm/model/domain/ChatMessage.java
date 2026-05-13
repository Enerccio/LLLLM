package io.github.enerccio.llllm.model.domain;

import io.github.enerccio.llllm.model.domain.collections.Role;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(indexes = {
    @Index(name = "mes_is_deleted_idx", columnList = "is_deleted"),
    @Index(name = "mes_user_id_ix", columnList = "userId")
})
public class ChatMessage extends ExtendedContentEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    private CharacterInfo character;

    @ManyToOne(fetch = FetchType.EAGER)
    private Persona persona;

    private Date timestamp;

    private Role role;

    @Lob
    private String data;

    @Lob
    private String corrected;

    @Lob
    private String grammarExplanation;

    @Lob
    private String interestingWords;

    @ManyToOne(fetch = FetchType.EAGER)
    private Resource recording;

    private String modelInfo;

    private Long tokenCount;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public CharacterInfo getCharacter() {
        return character;
    }

    public void setCharacter(CharacterInfo character) {
        this.character = character;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

    public Long getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(Long tokenCount) {
        this.tokenCount = tokenCount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCorrected() {
        return corrected;
    }

    public void setCorrected(String fixed) {
        this.corrected = fixed;
    }

    public String getGrammarExplanation() {
        return grammarExplanation;
    }

    public void setGrammarExplanation(String grammarExplanation) {
        this.grammarExplanation = grammarExplanation;
    }

    public String getInterestingWords() {
        return interestingWords;
    }

    public void setInterestingWords(String interestingWords) {
        this.interestingWords = interestingWords;
    }

    public Resource getRecording() {
        return recording;
    }

    public void setRecording(Resource recording) {
        this.recording = recording;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
