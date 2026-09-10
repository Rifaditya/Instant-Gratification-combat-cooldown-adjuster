# ⚔️ Combat Cooldown Adjuster

<p align="center">
    <a href="https://www.curseforge.com/minecraft/mc-mods/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://discord.gg/EV99bgAFqb" target="_blank" rel="noopener"><img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Join Discord"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
</p>

**Target Version:** Minecraft 26.1+ (Fabric)
**Dependencies:** Fabric API, DasikLibrary

> **"Stop the wait. Dominate the fight."**

**Combat Cooldown Adjuster** is a precision combat overhaul that hands control back to the player. By decoupling combat cooldowns from rigid vanilla math and replacing them with a **Tick-Based Categorical System**, you can fine-tune your combat experience to match your playstyle perfectly.

---

## 🚀 Core Features

### ⏱️ Categorical Control
Forget generic speed multipliers. Set exact tick delays for every tool type in the game:
- **Swords, Axes, Spears, Pickaxes, Shovels, Hoes.**
- Every category is individually configurable via GameRules.

### ⚡ Swap Agility
Tired of the artificial delay when switching weapons? **Swap Agility** removes the attack cooldown reset on item swap. Master the "Hotbar Combo" and strike the moment your weapon hits your hand.

### 💥 Combat Juice
We've added extra sensory feedback to make combat feel more gratifying:
- **Impact Particles**: `crit` and `enchanted_hit` spawns on target.
- **Dynamic Audio**: Strong attacks feature pitch-shifted audio that reflects your weapon's charge level.

---

## 🛠️ Configuration

Configure your experience in-game using the **Game Rules** menu. No external files required.

**Example Commands:**
- `/gamerule ig:sword_cooldown_ticks 4`
- `/gamerule ig:prevent_item_swap_cooldown true`
- `/gamerule ig:enable_combat_juice true`

<hr>

<h2>☕ Support</h2>

<p>If you enjoy the <strong>Instant Gratification</strong> collection, consider fueling future updates!</p>

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&amp;logo=ko-fi&amp;logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

<blockquote><p><strong>🇮🇩 Indonesian Users:</strong> SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!</p></blockquote>

<blockquote><p><strong>Dedicated Server Hosting Partner:</strong><br>Looking for a high-performance server to host your community or play with friends? Check out <strong>BisectHosting</strong> for 1-click modpack installations, automated backups, and 24/7 dedicated customer support. Use promo code <strong><code>Dasik</code></strong> for 25% off your first month!</p></blockquote>

<h3>💬 Join the Community &amp; Get Support</h3>
<p>Looking for help, want to test early beta builds, or vote on upcoming features? Join our official Discord community!</p>
<p align="center">
  <a href="https://discord.gg/EV99bgAFqb" target="_blank" rel="noopener">
    <img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&amp;logo=discord&amp;logoColor=white" alt="Join Official Discord">
  </a>
</p>

<hr>

<h2>📜 Credits &amp; Modpack Permissions</h2>

<table>
  <thead>
    <tr>
      <th>Property</th>
      <th>Information</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td><strong>Creator / Author</strong></td>
      <td><strong>Dasik</strong> (Rifaditya)</td>
    </tr>
    <tr>
      <td><strong>Community</strong></td>
      <td><a href="https://discord.gg/EV99bgAFqb" target="_blank" rel="noopener">Official Discord</a></td>
    </tr>
    <tr>
      <td><strong>Collection</strong></td>
      <td><a href="https://www.curseforge.com/members/dasikigaijin/projects">Instant Gratification</a></td>
    </tr>
    <tr>
      <td><strong>License</strong></td>
      <td><a href="https://www.gnu.org/licenses/gpl-3.0.html">GNU General Public License v3.0 (GPLv3)</a></td>
    </tr>
    <tr>
      <td><strong>Source Code</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-combat-cooldown-adjuster">GitHub - Rifaditya/Instant-Gratification-combat-cooldown-adjuster</a></td>
    </tr>
    <tr>
      <td><strong>Issue Tracker</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-combat-cooldown-adjuster/issues">GitHub Issues</a></td>
    </tr>
    <tr>
      <td><strong>Documentation / Wiki</strong></td>
      <td><a href="https://github.com/Rifaditya/Instant-Gratification-combat-cooldown-adjuster/wiki">GitHub Wiki</a></td>
    </tr>
  </tbody>
</table>

<blockquote>
  <p><strong>📦 Modpack Permissions &amp; Distribution:</strong><br>
  You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (<strong>CurseForge</strong> or <strong>Modrinth</strong>). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.</p>
  <p><strong>⚖️ License &amp; Fork Guidelines (No Zero-Change Re-uploads):</strong><br>
  This project is open-source under the <strong>GNU GPLv3</strong>. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports&mdash;provided your project remains open-source under GPLv3 with proper attribution.<br>
  <strong>However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.</strong></p>
</blockquote>

<hr>

<p align="center">
  <strong>Made with ❤️ for the Minecraft community</strong><br>
  <em>Part of the Instant Gratification Collection</em>
</p>
