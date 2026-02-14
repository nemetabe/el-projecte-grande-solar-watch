import { useState, useEffect, useCallback } from "react";
import { useAuth } from "../auth/useAuth";
import { useMountedRef } from "../utils/useMountedRef";
import { fetchSavedCitiesForMember } from "../../services/memberService";

export function useMemberSavedCities() {
    const { memberId, jwt, isAuthenticated } = useAuth();
    const isMounted = useMountedRef();
    const [cities, setCities] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const load = useCallback(async () => {

        if (!isAuthenticated) {

            setCities([]);
            setLoading(false);

            return;
        }

        try {

            setLoading(true);

            const result =
                await fetchSavedCitiesForMember(
                    memberId,
                    jwt
                );

            if (!isMounted.current) return;

            setCities(result);

        } catch (err) {

            if (isMounted.current) {
                setError(err.message);
            }

        } finally {

            if (isMounted.current) {
                setLoading(false);
            }
        }

    }, [memberId, jwt, isAuthenticated, isMounted]);

    useEffect(() => {
        load();
    }, [load]);

    return {

        cities,
        loading,
        error,
        reload: load,
    };
}