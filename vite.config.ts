import {
  UserConfigFn
} from 'vite';
import { overrideVaadinConfig } from './vite.generated';
// @ts-ignore
import { modules } from './frontend/bundle/modules.json';

import { posix as path } from 'path';
const modulesDirectory = path.join(__dirname, 'node_modules');

function getExports(id: string): string[] {
  const exportsSet = new Set<string>();
  for (const e of modules[id].exports) {
    if (e.indexOf('__@__') >= 0) {
      const [namespace, sourceId] = e.split('__@__');
      if (namespace.length) {
        exportsSet.add(namespace);
      } else {
        getExports(sourceId).forEach((e) => exportsSet.add(e));
      }
    } else {
      exportsSet.add(e);
    }
  }
  return Array.from(exportsSet);
}

const customConfig: UserConfigFn = (env) => ({
  // Here you can add custom Vite parameters
  // https://vitejs.dev/config/
  optimizeDeps: {
    exclude: Object.keys(modules),
  },
  plugins: [
    {
      name: 'vaadin-bundle',
      apply: 'serve',
      enforce: 'pre',
      load(rawId) {
        const [path, params] = rawId.split('?');
        if (!path.startsWith(modulesDirectory)) return;

        const id = path.substring(modulesDirectory.length + 1);
        if (!modules[id]) return;
        const exports = getExports(id);

        const cacheSuffix = params ? `?${params}` : '';
        const bundlePath = `/VAADIN/frontend/bundle/theme__lumo.js${cacheSuffix}`;

        return `import { init as VaadinBundleInit, get as VaadinBundleGet } from '${bundlePath}';
await VaadinBundleInit('default');
const { ${exports.join(', ')} } = (await VaadinBundleGet('./node_modules/${id}'))();
export { ${exports.map((binding) => `${binding} as ${binding}`).join(', ')} };
`;
      }
    }
  ],
});

export default overrideVaadinConfig(customConfig);
