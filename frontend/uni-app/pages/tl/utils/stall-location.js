export function stallLocationLabel(stall) {
  const address = String(stall?.address || '').trim()
  if (address) return address
  const district = String(stall?.districtName || '').trim()
  const city = String(stall?.cityName || '').trim()
  if (district && city && district !== city) return `${district} · ${city}`
  return district || city || '地区待完善'
}
