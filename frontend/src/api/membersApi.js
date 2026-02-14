import { apiClient } from "./apiClient";

export async function getSavedCities(memberId, jwt) {
  const data = await apiClient({ path: `members/${memberId}/saved-cities`, jwt });
  if (!Array.isArray(data)) return [];
  return data.map(c => {
    const cityObj = typeof c.city === "object" && c.city !== null ? c.city : {};
    return { id: c.id, name: cityObj.name ?? c.city ?? "Unknown", country: c.country ?? cityObj.country ?? "Unknown" };
  });
}

export async function saveCity(memberId, cityId, solarTimesId, jwt) {
  return apiClient({ path: `members/${memberId}/saved-cities`, method: "POST", body: { cityId, solarTimesId }, jwt });
}

export async function deleteSavedCity(memberId, savedCityId, jwt) {
  return apiClient({ path: `members/${memberId}/saved-cities/${savedCityId}`, method: "DELETE", jwt });
}

export async function registerMember({ username, email, password, favouriteCity }) {
  return apiClient({ path: "members/auth/register", method: "POST", body: { username, email, password, favouriteCity } });
}

export async function loginMember({ username, password }) {
  return apiClient({ path: "members/auth/login", method: "POST", body: { username, password } });
}