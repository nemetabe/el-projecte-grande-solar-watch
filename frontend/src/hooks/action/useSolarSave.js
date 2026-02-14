import { useCallback, useState } from "react";
import { useMountedRef } from "../utils/useMountedRef";
import { useAuth } from "../auth/useAuth";
import { saveCityForMember } from "../../services/memberService";

export function useSolarSave() {
  const { memberId, jwt, isAuthenticated } = useAuth();
  const isMounted = useMountedRef();

  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(false);

  const saveCity = useCallback(
    async (cityId, solarTimesId) => {
      if (!isAuthenticated) {
        setError("Not authenticated");
        setSuccess(false);
        return false;
      }

      if (!cityId || !solarTimesId) {
        setError("Missing city or solarTimesId");
        setSuccess(false);
        return false;
      }

      try {
        setSaving(true);
        setError(null);
        setSuccess(false);

        await saveCityForMember(memberId, cityId, solarTimesId, jwt);

        if (!isMounted.current) return false;

        setSuccess(true);
        return true;
      } catch (err) {
        if (isMounted.current) {
          setError(err.message ?? "Failed to save city");
          setSuccess(false);
        }
        return false;
      } finally {
        if (isMounted.current) setSaving(false);
      }
    },
    [memberId, jwt, isAuthenticated, isMounted]
  );

  return {
    saveCity,
    saving,
    error,
    success,
    isAuthenticated,
  };
}