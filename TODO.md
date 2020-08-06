# general

## tool traits

* [x] energized: the equipment uses forge energy before durability
    * higher levels -> higher energy capacity
* [x] photosynthetic: repairs in the sun
* [x] cascading: destroys all gravitating blocks in a column
* [x] blasting: breaking blocks and fully-charged hits create explosions
* [x] modifiable: equipment has more modifier slots
    * a clone of writable that avoids the weird quirks of writable
* [x] fertilizing: right clicking consumes a lot of durability to function as bone meal

## weapon traits

* [x] chain lightning: fully-charged attacks have a chance of arcing to nearby targets for reduced damage
* [x] battle furor: fully-charged hits grant increasing amounts of attack damage for a short duration
* [x] fleet of foot: fully-charged hits grant a speed boost for a short duration
* [x] mortal wounds: hit enemies have reduced healing for a short duration
* [x] staggering: fully-charged hits root enemies for a short duration
* [x] deadly precision: critical strikes deal increased damage
* [x] relentless: hit enemies have reduced i-frames
* [x] ruination: fully-charged hits deal a percentage of enemies' current health as damage
* [x] executor: fully-charged hits deal a percentage of enemies' missing health as damage
* [x] juggernaut: deal increased damage based on own current health with diminishing returns
* [x] opportunist: deal increased damage to enemies with debuffs
* [x] force of impact: deal increased damage with higher velocity
* [x] thundergod's wrath: strikes lightning on full-health enemies
* [x] aftershock: fully-charged hits deal a fixed amount of bonus magical damage
    * higher levels -> more bonus damage
* [x] corrupting: fully-charged hits inflict increasing levels of wither for a short duration
* [x] piezoelectric: fully-charged hits charge energetic items in the inventory
* [x] crystalline: do more damage at higher durability
* [x] culling: do more damage to enemies with less max health than the user
* [ ] shredding: does increased damage to enemy armour
* [x] overwhelm: does increased damage to armoured targets
* [x] vampiric: heals for a portion of damage dealt
* [x] sundering: enemies are inflicted with weakness
* [x] rejuvenating: hit entities are granted regeneration for a duration
* [x] luminiferous: hit entities are inflicted with glowing for a short duration

## armour traits

* [x] reactive: taking hits grants increasing amounts of damage reduction for a short duration
* [x] second wind: taking hits grants regeneration for a short duration
* [x] divine grace: increases received healing
* [ ] counterstrike: incoming hits have a chance to be reflected at the attacker
* [x] shadowstep: grants invisibility in low light levels
* [x] strength of will: taking a hit at full health grants immortality for a short duration
* [x] spectral: incoming hits have a chance to be ignored entirely
* [x] aspect of the phoenix: saves the wearer from death and full heals, but breaks the armour piece
* [x] bulwark: all damage is reduced by one heart but does at least one heart of damage
* [x] radiant: attacking enemies are briefly blinded
* [x] celestial: grants creative flight
* [x] gale force: increases speed of creative flight
* [x] hearth's embrace: when set aflame, the wearer is extinguished and granted regen for a short duration
* [ ] megaflip: exposions deal no damage but increased knockback
* [x] stifling: attacking enemies are inflicted with weakness for a short duration
* [x] thundergod's favour: immune to lightning damage
* [x] chilling touch: attacking enemies are inflicted with slowness for a short duration
* [x] stonebound: increased armour effectiveness at lower durability

## modifiers

* [x] fluxed: the equipment uses forge energy before durability
    * capacity is determined by the item used to apply it
    * cannot be applied to items that are already energetic/electric
* [x] photovoltaic: the equipment charges itself in sunlight
    * can only be applied to energetic/electric items
    * generation rate is determined by the item used to apply it
* [ ] disassembling: vein miner

## materials

* silver usable for magic tool parts
* *TODO: all sorts of funky alloys*

## tools

* [x] sceptre: hits things while near or launches projectiles from a distance for magic damage
* [ ] boomerang: throwable thing that comes back
    * homes in on target(s) that are moused over while preparing the throw
    * can pick up one dropped item and bring it back to the thrower
* [ ] trident: just the throwable spear thing from the aquatic update

## other stuff

* [ ] tuberous flux capacitor :D

# integration modules

## actually additions

### materials

* [x] black quartz
    * tools: depth digger + jagged
    * armour: subterranean + rough
* [x] restonia crystal
    * tools: crystalline + piezoelectric (head)
    * armour: mundane
* [x] palis crystal
    * tools: crystalline + well-established
    * armour: ambitious
* [x] diamantine crystal
    * tools: crystalline + aftershock
    * armour: shielding
* [x] void crystal
    * tools: crystalline + cheapskate
    * armour: cheapskate
* [x] emeraldic crystal
    * tools: crystalline + cold-blooded (head) + momentum (extra)
    * armour: vengeful
* [x] enori crystal
    * tools: crystalline + magnetic
    * armour: heavy + magnetic

### other ideas

* [ ] some sort of rainbow crystal alloy
* [x] batteries should apply fluxed
* [x] solar panel should apply photovoltaic

## advanced solars

### materials

* [x] sunnarium
    * tools: photosynthetic + luminiferous
    * armour: photosynthetic + radiant

### other ideas

* [x] various tiers of solar panels can apply photovoltaic

## app eng

### materials

* [x] sky stone
    * tools: crumbling (head) + stonebound
    * armour: alien (core) + stonebound
* [x] certus quartz
    * tools: crystalline + lightweight
    * armour: lightweight
* [x] fluix
    * tools: crystalline + shocking
    * armour: voltaic
* [x] fluix steel (lazy ae2)
    * tools: piezoelectric (head) + magnetic
    * armour: steady + magnetic

## astral sorcery

### traits

* [x] astral: equipment can be attuned in an attunement altar for additional effects
* [x] attuned: equipment is more effective at night while attuned constellation is present + gets a bonus effect
    * aevitas: tools grant regen after hitting something, armour grants regen after getting hit
    * armara: tools grant resistance after hitting something, armour has increased protection
    * discidia: tools deal more damage, armour reflects a portion of incoming damage
    * evorsio: tools mine faster, armour grants haste
    * vicio: tools grant move speed after hitting something, armour grants move speed after getting hit
    * bootes: tools have silk touch, has a chance of spawning a flare after getting hit
    * fornax: tools ignite enemies, armour grants fire resistance
    * horologium: tools freeze enemies on hit, armour freezes attackers
    * lucerna: tools apply glowing on hit, armour grants night vision
    * mineralis: tools gain fortune, armour grants the block x-ray effect
    * octans: tools mine faster in water, armour grants water breathing
    * pelotrio: tools and armour repair themselves over time
    * probably should implement each attunement as a separate modifier

### materials

* [x] aquamarine
    * tools: astral (head) + crystalline
    * armour: astral (core) + absorbent
* [x] starmetal
    * tools: astral (head) + unnatural
    * armour: astral (core) + magnetic

### other ideas

* [ ] rock crystal material that depends on crystal quality somehow?

## avaritia

### traits

* [x] condensing: killing enemies and breaking blocks has a very tiny chance of dropping a pile of neutrons
* [x] infinitum: equipment is unbreakable
* [x] omnipotence: equipment can destroy any block (?) and strike any entity

### armour traits

* [x] null almighty: additively stacking 25% resistance to all damage
* [x] eternity: grants immortality

### materials

* [x] crystal matrix
    * tools: crystalline + aftershock 3 + insatiable
    * armour: rough + strength of will + prideful
* [x] neutronium
    * tools: condensing (head) + dense + heavy
    * armour: reactive + dense + heavy
* [x] infinity metal
    * tools: omnipotence (head) + infinitum
    * armour: null almighty (core) + eternity (core) + celestial (core) + gale force 3 + infinitum

## betweenlands

*TODO*

## blood magic

### traits

* [x] crystalys: drops weak blood shards
* [x] bloodbound: consumes lp before durability
* [x] sentient: consumes demon will + speed/damage proportional to demon will + grants the effects of pure wills
* [x] willful: killed mobs drop demon will or fill tartaric gems

### armour traits

* [x] soul guard: consumes lp to reduce damage
* [x] sentient: consumes demon will + defense is proportional to demon will
* [x] willful: attacking mobs have a chance of being ensnared

### materials

* [x] bound metal (infuse blood into coag matrix)
    * tools: crystalys (head) + bloodbound
    * armour: soul guard (core) + bloodbound
* [x] sentient metal (forge tartaric gem + coag matrix)
    * tools: sentient (head) + willful
    * armour: sentient (core) + willful

### other ideas

* [ ] some kind of "sanguine steel" alloy
* [ ] maybe allow tablets to be used as modifiers like on bound armour

## botania

### traits

* [x] mana-infused: repairs self with mana from inventory
* [x] aura siphon: some damage dealt is converted to mana in the inventory
* [x] voice of the fae: fully-charged swings have a chance to summon pixies
* [x] gaia's wrath: fully-charged swings consume mana to emit a damaging mana burst

### armour traits

* [x] mana affinity: provides a mana cost discount
    * higher levels -> higher discount
* [x] aura-infused: generates mana in the inventory over time
* [x] voice of the fae: has a chance to summon pixies when hit

### materials

* [x] livingrock
    * tools: sundering (head) + stonebound
    * armour: stifling (core) + stonebound
* [x] livingwood
    * tools: modifiable + ecological
    * armour: modifiable + ecological
    * suitable for arrow shaft
* [x] dreamwood
    * tools: aura siphon + ecological
    * armour: aura-infused + ecological
    * suitable for arrow shaft
* [x] manasteel
    * tools: momentum + mana-infused
    * armour: lightweight + mana-infused + mana affinity
* [x] terrasteel
    * tools: staggering (head) + gaia's wrath (extra) + mortal wounds + mana-infused
    * armour: strength of will (core) + second wind (plates/trim) + heavy + mana-infused + mana affinity 2
* [x] elementium
    * tools: voice of the fae (head) + opportunist (extra) + cascading + mana-infused
    * armour: voice of the fae (core) + divine grace (plates/trim) + shielding + mana-infused + mana affinity
* [x] mana string
    * bowstring: mana-infused
* [x] mana diamond
    * magic: crystalline + mana-infused
* [x] mana pearl
    * magic: endspeed + mana-infused
* [x] dragonstone
    * magic: voice of the fae + mana-infused

### other ideas

* [x] terra helmet wills as armour mods
* [ ] alloy that does vitreous pickaxe stuff
* [ ] alloys that do starcaller and thundercaller stuff

## draconic evolution

### traits

* [x] evolved: unbreakable, runs on rf, upgradable via fusion crafting
    * arrow damage: increases damage of projectiles
    * arrow speed: increases range of launchers
    * attack aoe: increases aoe of weapons
    * attack damage: increases damage of weapons
    * dig aoe: increases mining aoe of tools
    * dig speed: increases mining speed of tools
    * draw speed: increases draw speed of launchers
    * energy capacity: increases rf buffer
* [x] soul rend: tool has inherent reaping

### armour traits

* [x] evolved: unbreakable, runs on rf, grants energy shield, upgradable via fusion crafting
    * energy capacity: increases rf buffer
    * shield capacity: increases shield point buffer of energy shield
    * shield recovery: speeds up entropy dissipation of energy shield
    * move speed: increases move speed on boots
    * jump boost: increases jump height on leggings

### materials

* [x] draconium
    * tools: soul rend (head) + alien
    * armour: alien
* [x] wyvern metal
    * tools: evolved (head) + soul rend
    * armour: evolved (core)
* [x] draconic metal
    * tools: evolved (head) + soul rend 2
    * armour: evolved (core) + celestial (plates) + gale force (trim)
* [x] chaotic metal
    * tools: evolved (head) + soul rend 3
    * armour: evolved (core) + celestial (plates) + gale force 2 (trim)

### modifiers

* [x] reaping: grants chance for mobs to drop mob souls
* [x] entropic: deals greater entropy to enemy energy shields
* [x] flux burn: burns off energy from opponents' powered armour
* [x] primordial: converts a portion of outgoing damage to chaos damage

### armour modifiers

* [x] chaos resistance: reduces incoming chaos damage
* [x] final guard: saves its wearer from death at a large energy cost

### other stuff

* [x] wyvern/draconic flux capacitors should apply fluxed modifier

## embers

### traits

* [ ] ember-aspected: uses ember before durability

### materials

* [ ] caminite
    * tools: crude + stonebound
    * armour: mundane + stonebound
* [ ] dawnstone
    * tools: overwhelm + ember-aspected
    * armour: heavy + ember-aspected
* [ ] antimony (soot)
    * tools: shredding + ember-aspected
    * armour: indomitable + ember-aspected
* [ ] ember crystal
    * magic: superheat + ember-aspected

## ender io

### materials

* [x] ender crystal
    * magic: endspeed
* [x] pulsating crystal
    * magic: enderference
* [x] vibrant crystal
    * magic: chain lightning
* [x] weather crystal
    * magic: thundergod's wrath

### other ideas

* [x] inventory chargers should apply fluxed
* [x] photovoltaic cells should apply photovoltaic

## env tech

### materials

* [x] litherite
    * tools: jagged + petramor
    * armour: rough + petravidity
* [x] erodium
    * tools: lightweight + depth digger
    * armour: lightweight + subterranean
* [x] kyronite
    * tools: battle furor
    * armour: infernal (core/plates) + invigorating (trim)
* [x] pladium
    * tools: relentless
    * armour: bulwark (core/plates) + second wind (trim)
* [x] ionite
    * tools: deadly precision + chain lightning
    * armour: shadowstep (core/plates) + gale force (trim)
* [x] aethium
    * tools: executor + corrupting
    * armour: celestial (core/plates) + spectral (trim)
* [x] lonsdaleite
    * tools: modifiable + crude
    * armour: modifiable + mundane
* [x] mica
    * tools: modifiable 2
    * armour: modifiable 2

### other ideas

* [x] solar cells can apply photovoltaic

## forestry

### materials

* [x] apatite
    * tools: fertilizing + cheapskate
    * armour: absorbent + cheapskate

### armour modifiers

* [x] apiary affinity: provides protection from bees

### other stuff

* [x] kama functions as a scoop
* [ ] thermionic fabricator can execute casting recipes (is this even possible?)

## gtce

**TODO**

## ic2 (and icc?)

### traits

* [x] electric: has an EU buffer that is consumed before durability
    * if possible, should be mutually exclusive with energized/fluxed

### materials

* [x] rubber
    * tools: squeaky (head) + crude (extra)
    * armour: bouncy + thundergod's favour
* [x] advanced alloy
    * tools: dense + force of impact
    * armour: dense + indomitable
* [x] energetic metal (canning machine infuse energium dust + coag matrix)
    * tools: electric
    * armour: electric
* [x] carbon fiber
    * tools: relentless + lightweight
    * armour: bulwark + lightweight
* [x] iridium
    * tools: overwhelm + momentum
    * armour: steady + reactive
* [x] universal metal (canning machine infuse uu matter + coag matrix)
    * tools: ruination + crumbling
    * armour: aspect of the phoenix

### modifiers

* [x] solar panel can apply photoelectric

## industrial foregoing

### traits

* [x] slimey (pink): has a chance of spawning pink slimes

### materials

* [x] metallic essence (fluid sieve essence into coag matrix)
    * tools: well-established + sundering
    * armour: ambitious + stifling
* [x] meaty (fluid sieve meat into coag matrix)
    * tools: rejuvenating + tasty
    * armour: second wind + tasty
* [x] pink slime
    * tools: slimey (pink)
    * armour: slimey (pink) + bouncy
* [x] pink metal
    * tools: mortal wounds + unnatural
    * armour: divine grace + vengeful

### other ideas

* [ ] maybe add straw handlers for various tcon fluids

## mekanism

### materials

* [x] osmium
    * tools: dense + stiff
    * armour: dense + heavy
* [x] refined obsidian
    * tools: duritae + force of impact
    * armour: duritae + bulwark
* [x] refined glowstone
    * tools: luminiferous + sharp
    * armour: radiant + indomitable
* [x] hdpe
    * tools: cheap + crude + fleet of foot
    * armour: cheap + mundane + reactive
    * suitable for arrow shaft

### other notes

* [x] energy tablet should apply fluxed (?)
* [x] solar generators should apply photovoltaic

## natura

### materials

* [x] ghostwood
    * tools: fleet of foot + ecological
    * armour: spectral + ecological
    * suitable for arrow shaft + fletching
* [x] bloodwood
    * tools: vampiric + ecological
    * armour: infernal + ecological
    * suitable for arrow shaft
* [x] darkwood
    * tools: sundering + ecological
    * armour: stifling + ecological
    * suitable for arrow shaft
* [x] fusewood
    * tools: blasting + ecological
    * armour: steady + ecological
    * suitable for arrow shaft

## natural absorption

### armour modifiers

* [x] absorption: grants additional natural absorption

## natural pledge

### traits

* [ ] lustrebane: applies drab to enemies for a duration
* [ ] deafening: applies a status effect that removes the enemy's hearing
* [ ] calico: deals bonus damage to creepers

### armour traits

* [ ] deafening: attacking enemies are inflicted with deafening

### materials

* [ ] iridescent crystal
    * tools: lustrebane (head) + fleet of foot
    * armour: radiant (core) + shadowstep
* [ ] thundersteel
    * tools: chain lightning (head) + battle furor
    * armour: indomitable (core) + gale force
* [ ] soulroot
    * tools: rejuvenating (head) + ecological
    * armour: second wind (core) + ecological
* [ ] hearth ember
    * tools: deadly precision (head) + hellish
    * armour: counterstrike (core) + hearth's embrace
* [ ] divine metal (craft divine spirit + coag matrix)
    * tools: ruination + holy
    * armour: aspect of the phoenix + blessed 
* [ ] sealing oak
    * tools: deafening + ecological
    * armour: deafening + ecological
* [ ] thunderous oak
    * tools: thundergod's wrath + ecological
    * armour: thundergod's favour + ecological
* [ ] calico wood
    * tools: calico + ecological
    * armour: megaflip + ecological
* [ ] circuitree
    * tools: shocking + ecological
    * armour: voltaic + ecological

### other ideas

* [ ] alloys that grant the effects of eclipse/sunmaker/fenris' gear

## project: e

### traits

* [x] eternal density: dealing damage fills klein stars in the inventory
    * higher levels -> higher conversion ratio
    * also allows breaking dark/red matter blocks

### armour traits

* [x] superdense: additively-stacking 15% damage reduction
* [x] ultradense: additively-stacking 20% damage reduction

### materials

* [x] dark matter
    * tools: eternal density (head) + culling + staggering
    * armour: superdense (core) + infernal (plates/trim) + dense
* [x] red matter
    * tools: eternal density 2 (head) + juggernaut + overwhelm
    * armour: ultradense (core) + hearth's embrace (plates/trim) + dense

## psi

### traits

* [ ] psion-infused: slowly repairs over time using psi energy

### materials

* [ ] psimetal
    * tools: psion-infused + magnetic
    * armour: psion-infused + magnetic
* [ ] psigem
    * tools: psion-infused + aftershock
    * armour: psion-infused + shielding
* [ ] ebony psimetal
    * tools: psion-infused + battle furor
    * armour: psion-infused + heavy
* [ ] ivory psimetal
    * tools: psion-infused + relentless
    * armour: psion-infused + steady

### other notes

* no trait/modifier for adding spell sockets because rpsideas already does that

## redstone arsenal

### materials

* [x] fluxed electrum
    * tools: energized + magnetic 2
    * armour: energized + magnetic 2
* [x] flux crystal
    * tools: energized + aftershock
    * armour: energized + shielding

## redstone repository

### materials

* [x] gelid enderium
    * tools: energized 2 + juggernaut
    * armour: energized 2 + chilling touch
* [x] gelid gem
    * tools: energized 2 + aftershock 2
    * armour: energized 2 + reactive
* [x] fluxed string
    * bowstring: aftershock
    
### other stuff

* [x] gelid flux capacitor should apply fluxed

## solar flux reborn

### other stuff

* [x] solar panels can apply photovoltaic modifier

## thaumcraft

### traits

* [x] warping: inflicts temporary warp on enemies

### armour traits

* [x] warping: inflicts temporary warp on attackers

### materials

* [x] thaumium
    * tools: modifiable + opportunist
    * armour: modifiable + shielding
* [x] void metal
    * tools: ruination + sundering + warping
    * armour: chilling touch + stifling + warping
* [x] primal metal (infuse primordial pearl + salis mundis onto coag matrix)
    * tools: corrupting + culling
    * armour: gale force (core/plates) + aspect of the phoenix (trim)
* [x] amber
    * magic: opportunist
* [x] quicksilver
    * magic: modifiable 2

### other stuff

* [x] add thaumonomicon entries for tconevo stuff
* [x] allow infusion enchantment of tinkers' construct tools

## thermal series

### materials

* [x] tin
    * tools: crude
    * armour: mundane
* [x] aluminium
    * tools: lightweight
    * armour: featherweight
* [x] nickel
    * tools: force of impact (head) + magnetic (extra)
    * armour: bulwark (core) + magnetic (plates/trim)
* [x] platinum
    * tools: cold-blooded (head) + deadly precision (extra)
    * armour: prideful (core) + divine grace (plates/trim)
* [x] invar
    * tools: stiff (head) + duritae (extra)
    * armour: steady (core) + duritae (plates/trim)
* [x] constantan
    * tools: aridiculous (head) + freezing (extra)
    * armour: hearth's embrace (core) + chilling touch (plates/trim)
* [x] signalum
    * tools: relentless + aftershock
    * armour: lightweight + invigorating
* [x] lumium
    * tools: luminiferous (head) + opportunist (extra)
    * armour: radiant (core) + indomitable (plates/trim)
* [x] enderium
    * tools: mortal wounds (head) + enderference (extra)
    * armour: vengeful (core) + enderport (plates/trim)

### other stuff

* [x] flux capacitors should apply fluxed
* [x] the magma crucible should be able to do melting recipes... probably
