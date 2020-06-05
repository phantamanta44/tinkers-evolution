# general

## tool traits

* energized: the equipment uses forge energy before durability
    * higher levels -> higher energy capacity
* disassembling: vein miner
* photosynthetic: repairs in the sun
* cascading: destroys all gravitating blocks in a column
* blasting: breaking blocks and fully-charged hits create explosions

## weapon traits

* chain lightning: fully-charged attacks have a chance of arcing to nearby targets for reduced damage
* battle furor: fully-charged hits grant increasing amounts of attack damage for a short duration
* fleet of foot: fully-charged hits grant a speed boost for a short duration
* mortal wounds: hit enemies have reduced healing for a short duration
* staggering: fully-charged hits root enemies for a short duration
* deadly precision: critical strikes deal increased damage
* relentless: hit enemies have reduced i-frames
* ruination: fully-charged hits deal a percentage of enemies' current health as damage
* executor: fully-charged hits deal a percentage of enemies' missing health as damage
* juggernaut: deal increased damage based on own current health with diminishing returns
* opportunist: deal increased damage to enemies with debuffs
* force of impact: deal increased damage with higher velocity
* thundergod's wrath: fully-charged hits strike lightning on full-health enemies
* aftershock: fully-charged hits deal a fixed amount of bonus magical damage
    * higher levels -> more bonus damage
* corrupting: fully-charged hits inflict increasing levels of wither for a short duration
* piezoelectric: fully-charged hits charge energetic items in the inventory
* crystalline: do more damage at higher durability
* culling: do more damage to enemies with less max health than the user
* shredding: does increased damage to enemy armour
* overwhelm: does increased damage to armoured targets
* vampiric: heals for a portion of damage dealt
* sundering: enemies are inflicted with weakness
* rejuvenating: hit entities are granted regeneration for a duration
* luminiferous: hit entities are inflicted with glowing for a short duration

## armour traits

* reactive: taking hits grants increasing amounts of damage reduction for a short duration
* second wind: taking hits grants regeneration for a short duration
* divine grace: increases received healing
* counterstrike: incoming hits have a chance to be reflected at the attacker
* shadowstep: grants invisibility in low light levels
* strength of will: taking a hit at full health grants immortality for a short duration
* spectral: incoming hits have a chance to be ignored entirely
* aspect of the phoenix: saves the wearer from death and full heals, but breaks the armour piece
* bulwark: all damage is reduced by one heart but does at least one heart of damage
* radiant: attacking enemies are briefly blinded
* celestial: grants creative flight
* gale force: increases speed of creative flight
* hearth's embrace: when set aflame, the wearer is extinguished and granted regen for a short duration
* megaflip: exposions deal no damage but increased knockback
* stifling: attacking enemies are inflicted with weakness for a short duration
* thundergod's favour: immune to lightning damage
* chilling touch: attacking enemies are inflicted with slowness for a short duration
* stonebound: increased armour effectiveness at lower durability

## modifiers

* fluxed: the equipment uses forge energy before durability
    * higher levels -> larger energy buffer
    * EXCLUDE ELECTRIC ITEMS

## materials

* *TODO: all sorts of funky alloys*

## other stuff

* should probably add celestial to draconic evolution materials
* should probably let draconic flux caps apply fluxed
* tuberous flux capacitor :D

# integration modules

## actually additions

### materials

* black quartz
    * tools: depth digger + jagged
    * armour: subterranean + rough
* restonia crystal
    * tools: crystalline + piezoelectric (head)
    * armour: mundane
* palis crystal
    * tools: crystalline + well-established
    * armour: ambitious
* diamantine crystal
    * tools: crystalline + aftershock
    * armour: shielding
* void crystal
    * tools: crystalline + cheapskate
    * armour: cheapskate
* emeraldic crystal
    * tools: crystalline + cold-blooded (head) + momentum (extra)
    * armour: vengeful
* enori crystal
    * tools: crystalline + magnetic
    * armour: heavy + magnetic

### other ideas

* some sort of rainbow crystal alloy
* batteries should apply fluxed

## advanced solars

### materials

* sunnarium
    * tools: photosynthetic + luminiferous
    * armour: photosynthetic + radiant

### other ideas

* solar equipment modifiers

## app eng

### materials

* sky stone
    * tools: crumbling (head) + stonebound
    * armour: alien (core) + stonebound
* certus quartz
    * tools: crystalline + lightweight
    * armour: lightweight
* fluix
    * tools: crystalline + shocking
    * armour: voltaic
* fluix steel (lazy ae2)
    * tools: piezoelectric (head) + magnetic
    * armour: steady + magnetic

## astral sorcery

### traits

* astral: equipment can be attuned in an attunement altar for additional effects
* attuned: equipment is more effective at night while attuned constellation is present + gets a bonus effect
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

* aquamarine
    * tools: astral (head) + crystalline
    * armour: astral (core) + absorbent
* starmetal
    * tools: astral (head) + unnatural
    * armour: astral (core) + magnetic

### other ideas

* rock crystal material that depends on crystal quality somehow?

## avaritia

### traits

* condensing: killed enemies have a very tiny chance of dropping a pile of neutrons
* infinitum: equipment is unbreakable
* omnipotence: equipment can destroy any block (?) and strike any entity

### armour traits

* null almighty: additively stacking 25% resistance to all damage
* eternity: grants immortality

### materials

* crystal matrix
    * tools: crystalline + aftershock 3 + insatiable
    * armour: rough + strength of will + prideful
* neutronium
    * tools: condensing (head) + dense + heavy
    * armour: reactive + dense + heavy
* infinity metal
    * tools: omnipotence (core) + infinitum
    * armour: null almighty (core) + eternity (core) + celestial (core) + gale force + infinitum

## blood magic

### traits

* crystalys: drops weak blood shards
* bloodbound: indestructable, but consumes lp
* sentient: speed/damage proportional to demon will + grants the effects of pure wills
* willful: consumes demon will instead of durability if possible 

### armour traits

* sentient: defense is proportional to demon will

### materials

* lifeblood (infuse blood into coag matrix)
    * tools: crystalys (core) + vampiric (extra) + bloodbound
    * armour: divine grace (core) + bulwark (plates/trim) + bloodbound
* sentient metal (forge tartaric gem + coag matrix)
    * tools: sentient (head) + willful
    * armour: sentient (core) + willful

### other ideas

* some kind of "sanguine steel" alloy

## botania

### traits

* mana-infused: repairs self with mana from inventory
* aura siphon: some damage dealt is converted to mana in the inventory
* voice of the fae: fully-charged swings have a chance to summon pixies
* gaia's wrath: fully-charged swings consume mana to emit a damaging mana burst

### armour traits

* mana affinity: provides a mana cost discount
    * higher levels -> higher discount
* aura-infused: generates mana in the inventory over time
* voice of the fae: has a chance to summon pixies when hit

### materials

* livingrock
    * tools: sundering (head) + stonebound
    * armour: stifling (core) + stonebound
* livingwood
    * tools: writable + ecological
    * armour: writable + ecological
    * suitable for arrow shaft
* dreamwood
    * tools: aura siphon + ecological
    * armour: aura-infused + ecological
    * suitable for arrow shaft
* manasteel
    * tools: momentum + mana-infused
    * armour: lightweight + mana-infused + mana affinity
* terrasteel
    * tools: staggering (head) + gaia's wrath (extra) + mortal wounds + mana-infused
    * armour: strength of will (core) + second wind (plates/trim) + heavy + mana-infused + mana affinity 2
* elementium
    * tools: voice of the fae (head) + opportunist (extra) + cascading + mana-infused
    * armour: voice of the fae (core) + divine grace (plates/trim) + shielding + mana-infused + mana affinity
* mana string
    * bowstring: mana-infused

### other ideas

* terra helmet wills as armour mods
* alloy that does vitreous pickaxe stuff
* alloys that do starcaller and thundercaller stuff

## embers

### traits

* ember-aspected: uses ember before durability

### materials

* caminite
    * tools: crude + stonebound
    * armour: mundane + stonebound
* dawnstone
    * tools: overwhelm + ember-aspected
    * armour: heavy + ember-aspected
* antimony (soot)
    * tools: shredding + ember-aspected
    * armour: indomitable + ember-aspected

## ender io

### other ideas

* inventory chargers should apply fluxed
* maybe allow dark steel upgrades to function as modifiers?

## env tech

### materials

* litherite
    * tools: jagged + petramor
    * armour: rough + petravidity
* erodium
    * tools: lightweight + depth digger
    * armour: lightweight + subterranean
* kyronite
    * tools: battle furor
    * armour: infernal (core/plates) + invigorating (trim)
* pladium
    * tools: relentless
    * armour: bulwark (core/plates) + second wind (trim)
* ionite
    * tools: deadly precision + chain lightning
    * armour: shadowstep (core/plates) + gale force (trim)
* aethium
    * tools: executor + corrupting
    * armour: celestial (core/plates) + spectral (trim)
* lonsdaleite
    * tools: writable + crude
    * armour: writable + mundane
* mica
    * tools: writable 2
    * armour: writable 2

## ic2 (and icc?)

### traits

* electric: has an EU buffer that is consumed before durability
    * if possible, should be mutually exclusive with energized/fluxed

### materials

* rubber
    * tools: squeaky (head) + crude (extra)
    * armour: bouncy + thundergod's favour
* advanced alloy
    * tools: dense + force of impact
    * armour: dense + indomitable
* energetic metal (craft energium dust + coag matrix)
    * tools: electric
    * armour: electric
* carbon fiber
    * tools: relentless + lightweight
    * armour: bulwark + lightweight
* iridium
    * tools: overwhelm + momentum
    * armour: steady + reactive
* universal metal (canning machine infuse uu matter + coag matrix)
    * tools: ruination + disassembling + crumbling
    * armour: aspect of the phoenix + prideful

### other stuff

* maybe a solar recharge modifier

## industrial foregoing

### traits

* slimey (pink): has a chance of spawning pink slimes

### materials

* metallic essence (fluid sieve essence into coag matrix)
    * tools: well-established + sundering
    * armour: ambitious + stifling
* meaty (fluid sieve meat into coag matrix)
    * tools: rejuvenating + tasty
    * armour: second wind + tasty
* pink slime
    * tools: slimey (pink)
    * armour: slimey (pink) + bouncy
* pink metal
    * tools: mortal wounds + unnatural
    * armour: divine grace + vengeful
    
### other ideas

* maybe add straw handlers for various tcon fluids

## mekanism

### materials

* osmium
    * tools: dense + stiff
    * armour: dense + heavy
* refined obsidian
    * tools: duritae + force of impact
    * armour: duritae + bulwark
* refined glowstone
    * tools: luminiferous + sharp
    * armour: radiant + indomitable
* hdpe
    * tools: cheap + crude + fleet of foot
    * armour: cheap + mundane + reactive
    * suitable for arrow shaft

### other notes

* energy tablet should apply fluxed (?)

## natura

### materials

* ghostwood
    * tools: fleet of foot + ecological
    * armour: spectral + ecological
    * suitable for arrow shaft + fletching
* bloodwood
    * tools: vampiric + ecological
    * armour: infernal + ecological
    * suitable for arrow shaft
* darkwood
    * tools: sundering + ecological
    * armour: stifling + ecological
    * suitable for arrow shaft
* fusewood
    * tools: blasting + ecological
    * armour: steady + ecological
    * suitable for arrow shaft

## natural pledge

### traits

* lustrebane: applies drab to enemies for a duration
* deafening: applies a status effect that removes the enemy's hearing
* calico: deals bonus damage to creepers

### armour traits

* deafening: attacking enemies are inflicted with deafening

### materials

* iridescent crystal
    * tools: lustrebane (head) + fleet of foot
    * armour: radiant (core) + shadowstep
* thundersteel
    * tools: chain lightning (head) + battle furor
    * armour: indomitable (core) + gale force
* soulroot
    * tools: rejuvenating (head) + ecological
    * armour: second wind (core) + ecological
* hearth ember
    * tools: deadly precision (head) + hellish
    * armour: counterstrike (core) + hearth's embrace
* divine metal (craft divine spirit + coag matrix)
    * tools: ruination + holy
    * armour: aspect of the phoenix + blessed 
* sealing oak
    * tools: deafening + ecological
    * armour: deafening + ecological
* thunderous oak
    * tools: thundergod's wrath + ecological
    * armour: thundergod's favour + ecological
* calico wood
    * tools: calico + ecological
    * armour: megaflip + ecological
* circuitree
    * tools: shocking + ecological
    * armour: voltaic + ecological

### other ideas

* alloys that grant the effects of eclipse/sunmaker/fenris' gear

## project: e

### traits

* eternal density: dealing damage and breaking blocks fills klein stars in the inventory

### armour traits

* superdense: additively-stacking 20% damage reduction
* ultradense: additively-stacking 22.5% damage reduction

### materials

* dark matter
    * tools: eternal density (head) + culling + staggering
    * armour: superdense (core) + infernal (plates/trim) + prideful
* red matter
    * tools: eternal density (head) + juggernaut + overwhelm
    * armour: ultradense (core) + hearth's embrace (plates/trim) + reactive

## psi

### traits

* psionic: slowly repairs over time using psi energy
* socketed: comes with spell sockets (might leave it for rpsideas?)

### materials

* psimetal
    * tools: psionic + socketed + magnetic
    * armour: psionic + magnetic
* psigem
    * tools: psionic + socketed + aftershock
    * armour: psionic + shielding
* ebony psimetal
    * tools: psionic + socketed + battle furor
    * armour: psionic + heavy
* ivory psimetal
    * tools: psionic + socketed + fleet of foot
    * armour: psionic + steady

## redstone arsenal

### materials

* fluxed electrum
    * tools: energized + magnetic 2
    * armour: energized + magnetic 2
* flux crystal
    * tools: energized + shocking
    * armour: energized + voltaic

## redstone repository

### materials

* gelid enderium
    * tools: energized 2 + juggernaut (head)
    * armour: energized 2 + reactive
* gelid gem
    * tools: energized 2 + executor (head)
    * armour: energized 2 + chilling touch
* fluxed string
    * bowstring: aftershock 2
    
### other stuff

* gelid flux capacitor should apply fluxed

## thaumcraft

### traits

* warping: inflicts temporary warp on enemies

### armour traits

* warping: inflicts temporary warp on attackers

### materials

* thaumium
    * tools: writable + opportunist
    * armour: writable + shielding
* void metal
    * tools: ruination + sundering + warping
    * armour: chilling touch + stifling + warping
* primal metal (infuse primordial pearl + salis mundis onto coag matrix)
    * tools: corrupting + culling
    * armour: aspect of the phoenix + gale force

## the one probe

### armour mods

* probing (the one probe): attaches a probe to your armour

## thermal series

### materials

* tin
    * tools: crude
    * armour: mundane
* aluminium
    * tools: lightweight
    * armour: featherweight
* nickel
    * tools: force of impact (head) + magnetic (extra)
    * armour: bulwark (core) + magnetic (plates/trim)
* platinum
    * tools: cold-blooded (head) + deadly precision (extra)
    * armour: prideful (core) + divine grace (plates/trim)
* invar
    * tools: stiff (head) + duritae (extra)
    * armour: steady (core) + duritae (plates/trim)
* constantan
    * tools: aridiculous (head) + freezing (extra)
    * armour: hearth's embrace (core) + chilling touch (plates/trim)
* signalum
    * tools: relentless + aftershock
    * armour: lightweight + invigorating
* lumium
    * tools: luminiferous (head) + opportunist (extra)
    * armour: radiant (core) + indomitable (plates/trim)
* enderium
    * tools: mortal wounds (head) + enderference (extra)
    * armour: vengeful (core) + enderport (plates/trim)

### other stuff

* flux capacitors should apply fluxed
* the magma crucible should be able to do melting recipes... probably
