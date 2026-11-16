# Vendored SheetJS

The admin console vendors the official SheetJS Community Edition `xlsx` 0.20.3 package tarball because the public npm registry still serves the older 0.18.5 release. Vendoring follows the [official SheetJS installation guidance](https://docs.sheetjs.com/docs/getting-started/installation/frameworks/).

- Source: `https://cdn.sheetjs.com/xlsx-0.20.3/xlsx-0.20.3.tgz`
- Version: `0.20.3`
- SHA-256: `8DC73FC3B00203E72D176E85B50938627C7B086E607C682E8D3C22C02BB99FE8`
- Package license: Apache-2.0, included as `package/LICENSE` in the tarball

Verify the vendored file on Windows with:

```powershell
Get-FileHash frontend/admin/vendor/xlsx-0.20.3.tgz -Algorithm SHA256
```

The admin WeChat browser client is also vendored from `wechat-jssdk` 5.1.0's `lib/client.js`. The admin only uses this client module; bundling the whole server-oriented npm package also pulled in an unused, vulnerable Mongoose dependency. Its MIT license is included as `wechat-jssdk-MIT-LICENSE`.
