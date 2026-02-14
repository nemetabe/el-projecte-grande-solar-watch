import { getSavedCities, saveCity as saveCityApi } from "../api/membersApi";

function normalizeSavedCity(cityData) {
  const cityObj = cityData?.city && typeof cityData.city === "object" ? cityData.city : {};
  return {
    id: cityData.id,
    name: cityObj.name ?? cityData.city ?? "Unknown",
    country: cityObj.country ?? cityData.country ?? "Unknown",
  };
}

export async function fetchSavedCitiesForMember(memberId, jwt) {
  if (!memberId) throw new Error("Missing memberId");
  if (!jwt) throw new Error("Missing authentication");

  const cities = await getSavedCities(memberId, jwt);
  if (!Array.isArray(cities)) return [];
  return cities.map(normalizeSavedCity);
}

export async function saveCityForMember(memberId, cityId, solarTimesId, jwt) {
  if (!memberId) throw new Error("Missing memberId");
  if (!jwt) throw new Error("Missing authentication");
  if (!cityId) throw new Error("Missing cityId");
  if (!solarTimesId) throw new Error("Missing solarTimesId");

  return saveCityApi(memberId, cityId, solarTimesId, jwt);
}