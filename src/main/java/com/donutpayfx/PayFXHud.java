package com.donutpayfx;

// Intended HUD renderer:
// Register with HudRenderCallback and render:
// 1M -> clean top-right toast
// 5M -> particle burst + payer name
// 10M -> bigger radial fireworks
// 100M -> full-screen JACKPOT banner, rings, shake, sound sequence
// 1B -> multi-stage legendary celebration.
//
// Kept as a dedicated class so the visuals can be expanded without touching
// payment parsing or the persistent leaderboard.
