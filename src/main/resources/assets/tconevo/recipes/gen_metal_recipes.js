const fs = require('fs');

const MOD_ID = 'tconevo';
const ITEM_METAL = `${MOD_ID}:metal`;
const BLOCK_METAL = `${MOD_ID}:metal_block`;
// must be listed in the same order as they're defined in ItemMetal.Type
const METALS = [
  { key: 'wyvern_metal', oreName: 'WyvernMetal' },
  { key: 'draconic_metal', oreName: 'DraconicMetal' },
  { key: 'chaotic_metal', oreName: 'ChaoticMetal' }
];

const MetalForm = {
  INGOT: 0,
  DUST: 1,
  NUGGET: 2,
  PLATE: 3,
  GEAR: 4
}

function getMetalItem(typeNdx, form, count) {
  return { item: ITEM_METAL, count, data: typeNdx * 5 + form };
}

function getMetalBlock(typeNdx, count) {
  return { item: BLOCK_METAL, count, data: typeNdx };
}

function writeShaped(recipeName, pattern, key, result) {
  const recipe = { type: 'forge:ore_shaped', pattern, key: {}, result };
  for (const [k, ore] of Object.entries(key)) {
    recipe.key[k] = { type: 'forge:ore_dict', ore };
  }
  fs.writeFileSync(`${recipeName}.json`, JSON.stringify(recipe, null, 2));
}

function writeShapeless(recipeName, ingredients, result) {
  const recipe = { type: 'forge:ore_shapeless', ingredients: [], result };
  for (const ore of ingredients) {
    recipe.ingredients.push({ type: 'forge:ore_dict', ore });
  }
  fs.writeFileSync(`${recipeName}.json`, JSON.stringify(recipe, null, 2));
}

for (let typeNdx = 0; typeNdx < METALS.length; typeNdx++) {
  const { key, oreName } = METALS[typeNdx];
  // ingot <-> nugget
  writeShapeless(`metal_nugget_split_${key}`, [`ingot${oreName}`], getMetalItem(typeNdx, MetalForm.NUGGET, 9));
  writeShaped(`metal_nugget_join_${key}`, ['nnn', 'nnn', 'nnn'], { n: `nugget${oreName}` }, getMetalItem(typeNdx, MetalForm.INGOT, 1));
  // ingot <-> block
  writeShapeless(`metal_block_split_${key}`, [`block${oreName}`], getMetalItem(typeNdx, MetalForm.INGOT, 9));
  writeShaped(`metal_block_join_${key}`, ['iii', 'iii', 'iii'], { i: `ingot${oreName}` }, getMetalBlock(typeNdx, 1));
  // ingot -> gear
  writeShaped(`metal_gear_${key}`, [' i ', 'i i', ' i '], { i: `ingot${oreName}` }, getMetalItem(typeNdx, MetalForm.GEAR, 1));
}
