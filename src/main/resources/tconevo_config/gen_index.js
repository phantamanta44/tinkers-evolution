const fs = require('fs');

const listing = [];
const queue = ['.'];
while (queue.length > 0) {
  const path = queue.shift();
  for (const entry of fs.readdirSync(path)) {
    const entryPath = `${path}/${entry}`;
    if (fs.statSync(entryPath).isDirectory()) {
      queue.push(entryPath);
    } else if (entryPath !== './index.txt' && entryPath !== './gen_index.js') {
      listing.push(entryPath.substring(2)); // slice off './'
    }
  }
}

fs.writeFileSync('index.txt', listing.join('\n'))
