// +----------------------------------------------------------------------
// | CRMEB [ CRMEB赋能开发者，助力企业发展 ]
// +----------------------------------------------------------------------
// | Copyright (c) 2016~2026 https://www.crmeb.com All rights reserved.
// +----------------------------------------------------------------------
// | Licensed CRMEB并不是自由软件，未经许可不能去掉CRMEB相关版权
// +----------------------------------------------------------------------
// | Author: CRMEB Team <admin@crmeb.com>
// +----------------------------------------------------------------------

import { defineConfig, loadEnv } from 'vite';
import uni from '@dcloudio/vite-plugin-uni';

function normalizeUrl(value) {
	return (value || '').trim().replace(/\/+$/, '');
}

function rejectLoopbackInProduction(value, name, mode) {
	if (!value || mode === 'development') return;
	let hostname;
	let parsed;
	try { parsed = new URL(value); hostname = parsed.hostname.toLowerCase(); }
	catch (error) { throw new Error(`${name} must be an absolute HTTPS URL`); }
	if (parsed.protocol !== 'https:') throw new Error(`${name} must use HTTPS in production builds`);
	if (parsed.username || parsed.password) throw new Error(`${name} must not include credentials`);
	if ((name === 'VITE_TL_API_URL' || name === 'VITE_TEA_YU_API_URL') && (parsed.pathname !== '/' || parsed.search || parsed.hash)) {
		throw new Error(`${name} must be a host-root URL without a path, query, or fragment`);
	}
	if (['localhost', '127.0.0.1', '::1', '[::1]'].includes(hostname)) {
		throw new Error(`${name} cannot use a loopback host in production builds`);
	}
}

export default defineConfig(({ mode }) => {
	const env = loadEnv(mode, process.cwd(), 'VITE_');
	const development = mode === 'development';
	const apiUrl = normalizeUrl(env.VITE_TL_API_URL || env.VITE_TEA_YU_API_URL) || (development ? 'http://127.0.0.1:8081' : '');
	const h5Url = normalizeUrl(env.VITE_TL_H5_URL || env.VITE_TEA_YU_H5_URL) || (development ? 'http://localhost:5173' : '');
	if (!apiUrl) throw new Error('Production build requires VITE_TL_API_URL');
	rejectLoopbackInProduction(apiUrl, 'VITE_TL_API_URL', mode);
	rejectLoopbackInProduction(h5Url, 'VITE_TL_H5_URL', mode);

	return {
		plugins: [uni()],
		define: {
			__TL_API_URL__: JSON.stringify(apiUrl),
			__TL_H5_URL__: JSON.stringify(h5Url),
			__TEAYU_API_URL__: JSON.stringify(apiUrl),
			__TEAYU_H5_URL__: JSON.stringify(h5Url),
		},
	};
});
