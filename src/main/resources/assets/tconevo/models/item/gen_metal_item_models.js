const fs = require('fs');
const { MetalForm, MOD_ID, METALS } = require('../../constants');

const metalItem = {
  '9s': 'pi',
  archetype: {},
  mutations: {
    type: {},
    form: {}
  }
};

const metalBlock = {
  '9s': 'pi',
  archetype: {
    parent: 'block/cube_all'
  },
  mutations: {
    type: {}
  }
};

for (const { key } of METALS) {
  metalItem.mutations.type[key.toUpperCase()] = {
    textures: {
      ingot: `tconevo:items/metal/ingot/${key}`,
      dust: `tconevo:items/metal/dust/${key}`,
      nugget: `tconevo:items/metal/nugget/${key}`,
      plate: `tconevo:items/metal/plate/${key}`,
      gear: `tconevo:items/metal/gear/${key}`
    }
  };
  metalBlock.mutations.type[key.toUpperCase()] = {
    textures: {
      all: `tconevo:blocks/metal/${key}`
    }
  };
}

for (const key of Object.keys(MetalForm)) {
  metalItem.mutations.form[key] = {
    parent: `tconevo:item/metal_${key.toLowerCase()}`
  }
}

fs.writeFileSync('metal.json', JSON.stringify(metalItem, null, 2));
fs.writeFileSync('metal_block.json', JSON.stringify(metalBlock, null, 2));
