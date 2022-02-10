import {
  UserConfigFn,
  Plugin,
  createLogger,
  mergeConfig
} from 'vite';
import {posix as path} from 'path';
import { readFile } from 'fs/promises';
import { overrideVaadinConfig } from './vite.generated';

function vaadinBundlesPlugin(): Plugin {

  type ExposeInfo = {
    exports: string[];
  };

  type PackageInfo = {
    name: string,
    version: string,
    exposes: Record<string, ExposeInfo>,
  };

  const modulesDirectory = path.join(__dirname, 'node_modules');

  const bundleMap = new Map<string, PackageInfo>();

  let vaadinBundleJsonPath: string | undefined;

  function resolveVaadinBundleJsonPath() {
    try {
      vaadinBundleJsonPath = require.resolve('@vaadin/bundles/vaadin-bundle.json');
    } catch (e: unknown) {
      vaadinBundleJsonPath = undefined;
      if (!(typeof e === 'object' && (e as {code: string}).code === 'MODULE_NOT_FOUND')) {
        throw e;
      }
    }
  }

  function parseModuleId(id: string): { packageName: string, modulePath: string } {
    const [scope, scopedPackageName] = id.split('/', 3);
    const packageName = scope.startsWith('@') ? `${scope}/${scopedPackageName}` : scope;
    const modulePath = `.${id.substring(packageName.length)}`;
    return {
      packageName,
      modulePath
    };
  }

  function getExports(id: string): string[] | undefined {
    const {
      packageName,
      modulePath
    } = parseModuleId(id);
    const packageInfo = bundleMap.get(packageName);
    if (!packageInfo) return;

    const exposeInfo: ExposeInfo = packageInfo.exposes[modulePath];
    if (!exposeInfo) return;

    const exportsSet = new Set<string>();
    for (const e of exposeInfo.exports) {
      if (e.indexOf('__@__') >= 0) {
        const [namespace, sourceId] = e.split('__@__');
        if (namespace.length) {
          exportsSet.add(namespace);
        } else {
          const sourceExports = getExports(sourceId);
          if (sourceExports) {
            sourceExports.forEach((e) => exportsSet.add(e));
          }
        }
      } else {
        exportsSet.add(e);
      }
    }
    return Array.from(exportsSet);
  }

  return {
    name: 'vaadin-bundle',
    enforce: 'pre',
    apply(config, {command}) {
      if (command !== "serve") return false;

      resolveVaadinBundleJsonPath();
      if (vaadinBundleJsonPath === undefined) {
        const logger = createLogger(config.logLevel, {
          allowClearScreen: config.clearScreen,
          customLogger: config.customLogger
        });
        logger.warnOnce('@vaadin/bundles npm package is not found, ' +
          'Vaadin component dependency bundles are disabled.');
        return false;
      }

      return true;
    },
    async config(config) {
      console.log('vaadinBundleJsonPath', vaadinBundleJsonPath);
      const {packages}: { packages: PackageInfo[] } = JSON.parse(
        await readFile(vaadinBundleJsonPath!, {encoding: 'utf8'})
      );
      packages.forEach((packageInfo) =>
        bundleMap.set(packageInfo.name, packageInfo)
      );
      console.log('bundle packages', Array.from(bundleMap.keys()).join(', '));

      return mergeConfig(config, {
        optimizeDeps: {
          include: [
            // Happen to fail dedupe in Vite pre-bundling
            '@polymer/iron-meta',
            '@polymer/iron-meta/iron-meta.js',
          ],
          exclude: [
            // Vaadin bundle
            '@vaadin/bundles',
            ...Array.from(bundleMap.keys()),
            // Known small packages
            '@vaadin/router',
          ]
        }
      });
    },
    load(rawId) {
      const [path, params] = rawId.split('?');
      if (!path.startsWith(modulesDirectory)) return;

      const id = path.substring(modulesDirectory.length + 1);
      const exports = getExports(id);
      if (exports === undefined) return;

      const cacheSuffix = params ? `?${params}` : '';
      const bundlePath = `@vaadin/bundles/vaadin.js${cacheSuffix}`;

      return `import { init as VaadinBundleInit, get as VaadinBundleGet } from '${bundlePath}';
await VaadinBundleInit('default');
const { ${exports.join(', ')} } = (await VaadinBundleGet('./node_modules/${id}'))();
export { ${exports.map((binding) => `${binding} as ${binding}`).join(', ')} };`;
    }
  }
}

const customConfig: UserConfigFn = (env) => ({
  // Here you can add custom Vite parameters
  // https://vitejs.dev/config/
  optimizeDeps: {
    entries: [
      // Pre-scan entrypoints in Vite to avoid reloading on first open
      './generated/vaadin.ts'
    ],
  },
  plugins: [vaadinBundlesPlugin()],
});

export default overrideVaadinConfig(customConfig);
