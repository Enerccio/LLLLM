package io.github.enerccio.llllm.model.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "chat_is_deleted_idx", columnList = "is_deleted"),
        @Index(name = "chat_user_id_ix", columnList = "userId")
})
public class Chat extends ExtendedContentEntity {

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Persona persona;

    @ManyToOne(fetch = FetchType.EAGER)
    private Chat previousChat;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ChatMessage> messages = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<CharacterInfo> characters = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Lorebook> lorebooks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Location> locationsToPick = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private AI ai;

    @ManyToOne(fetch = FetchType.EAGER)
    private Protocol protocol;

    @ManyToOne(fetch = FetchType.EAGER)
    private AI voiceToText;

    @ManyToOne(fetch = FetchType.EAGER)
    private Protocol voiceToTextProtocol;

    @ManyToOne(fetch = FetchType.EAGER)
    private AI textToSpeech;

    @ManyToOne(fetch = FetchType.EAGER)
    private Protocol textToSpeechProtocol;

    @Lob
    private String chatSummary;

    public Chat getPreviousChat() {
        return previousChat;
    }

    public void setPreviousChat(Chat previousChat) {
        this.previousChat = previousChat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public List<CharacterInfo> getCharacters() {
        return characters;
    }

    public void setCharacters(List<CharacterInfo> characters) {
        this.characters = characters;
    }

    public List<Lorebook> getLorebooks() {
        return lorebooks;
    }

    public void setLorebooks(List<Lorebook> lorebooks) {
        this.lorebooks = lorebooks;
    }

    public List<Location> getLocationsToPick() {
        return locationsToPick;
    }

    public void setLocationsToPick(List<Location> locationsToPick) {
        this.locationsToPick = locationsToPick;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public AI getVoiceToText() {
        return voiceToText;
    }

    public void setVoiceToText(AI voiceToText) {
        this.voiceToText = voiceToText;
    }

    public Protocol getVoiceToTextProtocol() {
        return voiceToTextProtocol;
    }

    public void setVoiceToTextProtocol(Protocol voiceToTextProtocol) {
        this.voiceToTextProtocol = voiceToTextProtocol;
    }

    public AI getTextToSpeech() {
        return textToSpeech;
    }

    public void setTextToSpeech(AI textToSpeech) {
        this.textToSpeech = textToSpeech;
    }

    public Protocol getTextToSpeechProtocol() {
        return textToSpeechProtocol;
    }

    public void setTextToSpeechProtocol(Protocol textToSpeechProtocol) {
        this.textToSpeechProtocol = textToSpeechProtocol;
    }

    public String getChatSummary() {
        return chatSummary;
    }

    public void setChatSummary(String chatSummary) {
        this.chatSummary = chatSummary;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
