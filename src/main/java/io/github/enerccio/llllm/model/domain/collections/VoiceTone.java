package io.github.enerccio.llllm.model.domain.collections;

public enum VoiceTone {
    // --- Natural / Human Baseline Tones ---
    WARM,       // Friendly, comforting, empathetic (e.g., companion, parent, helper)
    CRISP,      // Clear, sharp, professional, articulate (e.g., news anchor, AI assistant, scientist)
    HUSKY,      // Raspy, breathy, textured, intimate (e.g., detective, mysterious figure)
    MELODIC,    // Smooth, lyrical, singsong, rhythmic (e.g., elf, fairy, storyteller)

    // --- Psychological / Status Tones ---
    COMMANDING, // Authoritative, stern, booming, high-status (e.g., military officer, king, villain)
    TIMID,      // Soft, high-pitch variation, hesitant, low-status (e.g., nervous villager, shy child)
    MONOTONE,   // Flat, robotic, devoid of expression (e.g., android, AI system, butler)

    // --- Textured / Fantastical Tones ---
    GRAVELY,    // Rough, harsh, vibrating with friction (e.g., dwarf, monster, battle-hardened soldier)
    HOLLOW      // Eerie, echoey, resonant, ghostly (e.g., spirit, phantom, deep cave entity)
}
