package io.github.enerccio.llllm.loc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LocalizationEN extends LocalizationBase {

    @Override
    protected void loadMessages() {
        setValue(L.YES, "Yes");
        setValue(L.NO, "No");
        setValue(L.BUTTON_EXIT, "Exit");
        setValue(L.LABEL_USERNAME, "Uživatelské jméno");
        setValue(L.LABEL_USER_FULLNAME, "Jméno a příjmení");
        setValue(L.LABEL_EMAIL, "Email");
        setValue(L.LABEL_LOGIN, "Login");
        setValue(L.LABEL_LOGOUT, "Logout");
        setValue(L.LABEL_EXIT_APPLICATION, "Close application");
        setValue(L.LABEL_PASSWORD, "Password");
        setValue(L.LABEL_PASSWORD_AGAIN, "Repeat password");
        setValue(L.LABEL_FORGOT_PASSWORD, "Forgotten password?");
        setValue(L.LABEL_SAVE_LOGIN, "Save login");
        setValue(L.LABEL_AUTHENTICATION_FAILED, "Failed to authenticate");
        setValue(L.LABEL_APPLICATION_ERROR, "Application Error");
        setValue(L.INTERNAL_SERVER_ERROR, "Internal Server Error");
        setValue(L.MSG_VALIDATION_FAILED_CANNONT_SAVE, "Cannot save form.");
        setValue(L.MSG_VALIDATION_FAILED_CANNONT_SAVE_EXT, "Cannot save form. Invalid fields: ");
        setValue(L.BUTTON_LOGIN, "Login");
        setValue(L.MSG_AUTHENTICATION_FAILED_INFO, "Invalid credentials.");
        setValue(L.AI_TYPE_OPEN_AI_COMPATIBLE, "OpenAI compatible");
        setValue(L.AITABLE_COLUMN_NAME, "Name");
        setValue(L.AITABLE_COLUMN_TYPE, "Type");
        setValue(L.WORKSPACE_TABS_CHAT, "Chats");
        setValue(L.WORKSPACE_TABS_CHARACTERS, "Characters");
        setValue(L.WORKSPACE_TABS_LOREBOOK, "Lorebook");
        setValue(L.WORKSPACE_TABS_PERSONA, "Persona");
        setValue(L.WORKSPACE_TABS_LOCATIONS, "Locations");
        setValue(L.WORKSPACE_TABS_AI, "AI Providers");
        setValue(L.PROTOCOL_COLUMN_NAME, "Name");
        setValue(L.PROTOCOL_COLUMN_TYPE, "Type");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_NAME, "Name");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_URI, "URI");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_API_KEY, "API Key");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_MODEL, "Model");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_DURATION, "Max chunk duration (seconds)");
        setValue(L.BUTTON_SAVE, "Save");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_MODEL_HEADER, "Open AI Compatible AI Provider");
        setValue(L.LABEL_OK, "OK");
        setValue(L.LABEL_CANCEL, "Cancel");
        setValue(L.AITABLE_SELECT_AI_PROVIDER, "Select AI Provider");
        setValue(L.AITABLE_SELECT_AI_PROVIDER_LABEL, "AI Provider");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_ADDITIONAL_PARAMS, "Additional parameters (JSON):");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_ADDITIONAL_PARAMS_UNPARSEABLE, "Unable to parse additional parameters as valid JSON");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_MAIN, "Main");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_TAB_ADVANCED, "Advanced Settings");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_NEEDS_JAILBREAK, "Needs Jailbreak");
        setValue(L.OPEN_AI_COMPATIBLE_FORM_JAILBREAK, "Jailbreak Prompt");
        setValue(L.WORKSPACE_TABS_SETTINGS, "Settings");
        setValue(L.CHAT_COMPLETION_FORM_MODEL_HEADER, "Chat Completion Preset");
        setValue(L.CHAT_COMPLETION_FORM_TAB_MAIN, "Main");
        setValue(L.CHAT_COMPLETION_FORM_TAB_ADVANCED, "Advanced Settings");
        setValue(L.PROTOCOL_SELECT_AI_PROVIDER, "Select Communication Protocol");
        setValue(L.PROTOCOL_SELECT_AI_PROVIDER_LABEL, "Protocol");
        setValue(L.PROTOCOL_TYPE_OPEN_CHAT_COMPLETION, "Chat Completion");
        setValue(L.PROTOCOL_TYPE_OPEN_CHAT_COMPLETION, "Chat Completion");
        setValue(L.PROTOCOL_BASE_FIELDS_NAME, "Name of the preset");
        setValue(L.PROTOCOL_BASE_FIELDS_MAX_TOKENS, "Max Reply Tokens");
        setValue(L.PROTOCOL_BASE_FIELDS_MAX_CONTEXT, "Max Context Size");
        setValue(L.PROTOCOL_BASE_FIELDS_TEMPERATURE, "Temperature");
        setValue(L.PROTOCOL_BASE_FIELDS_TOP_P, "Top P");
        setValue(L.PROTOCOL_BASE_FIELDS_FREQ, "Frequency Penalty");
        setValue(L.PROTOCOL_BASE_FIELDS_PRES, "Presence Penalty");
        setValue(L.PROTOCOL_BASE_FIELDS_ENABLED, "Enabled");
        setValue(L.AI_DELETE_CONFIRM, "Are you sure you want to delete AI?");
        setValue(L.PROTOCOL_DELETE_CONFIRM, "Are you sure you want to delete Protocol Preset?");
        setValue(L.REASONING_NONE, "None");
        setValue(L.REASONING_LOW, "Low");
        setValue(L.REASONING_MEDIUM, "Medium");
        setValue(L.REASONING_HIGH, "High");
        setValue(L.AI_FORM_REASONING, "Reasoning");
        setValue(L.AI_FORM_REASONING_LEVEL, "Reasoning Effort");
        setValue(L.AI_FORM_INPUT_SAMPLER, "Input sampling rate");
        setValue(L.AI_FORM_INPUT_FORMAT, "Input format");
        setValue(L.AI_FORM_VOICE_MAP, "Voice Map");
    }

    @Override
    public Locale getLocale() {
        return Locale.US;
    }

    @Override
    public DateFormat getDateFormat() {
        return new SimpleDateFormat( "MM.dd.yyyy");
    }

    @Override
    public DateFormat getHourFormat() {
        return new SimpleDateFormat( "HH:mm:ss");
    }

    @Override
    public DateFormat getDateHourFormat() {
        return new SimpleDateFormat( "MM.dd.yyyy HH:mm:ss");
    }
}
