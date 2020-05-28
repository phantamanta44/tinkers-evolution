const fs = require('fs');
const { MOD_ID, METALS } = require('../constants');

const stateDecl = {
  forge_marker: 1,
  defaults: {
    model: 'cube_all'
  },
  variants: {
    type: {}
  }
};

for (const {key} of METALS) {
  stateDecl.variants.type[key] = {
    textures: {
      all: `${MOD_ID}:blocks/metal/${key}`
    }
  }
}

fs.writeFileSync('metal_block.json', JSON.stringify(stateDecl, null, 2));
