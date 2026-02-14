import { useState, useCallback } from "react";
import { useMountedRef } from "../utils/useMountedRef";
import { apiClient } from "../../api/apiClient";

function getErrorMessage(error) {
  console.error("Failed to fetch solar data:", error);
  if (!error || typeof error !== "object") return "Failed to load solar data: Unknown error.";
  if (error.message?.includes("JSON")) return "Failed to load solar data: Server response format error.";
  if (error.message?.includes("HTTP")) return "Failed to load solar data: Server error occurred.";
  return "Failed to load solar data: Please try again.";
}

export function useSolarData() {
  const [solarTimes, setSolarTimes] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const isMounted = useMountedRef();

  const fetchSolarData = useCallback(
    async (date, city) => {
      if (!date || !city) return null;

      if (isMounted.current) {
        setIsLoading(true);
        setError(null);
      }

      try {
        const data = await apiClient({ path: `solar?date=${date}&city=${city}` });

        if (!data || typeof data !== "object") {
          throw new Error("Invalid response structure");
        }

        if (isMounted.current) setSolarTimes(data);

        return data;
      } catch (err) {
        if (isMounted.current) {
          setError(getErrorMessage(err));
          setSolarTimes(null);
        }
        return null;
      } finally {
        if (isMounted.current) setIsLoading(false);
      }
    },
    [isMounted]
  );

  return { solarTimes, isLoading, error, fetchSolarData };
}