import { useLocalStorage } from "../state/useLocalStorage";
import { useCallback, useState } from "react";
import { loginMember, registerMember } from "../../api/membersApi";

export function useAuth() {
  const [memberId, setMemberId] = useLocalStorage("memberId", null);
  const [jwt, setJwt] = useLocalStorage("solAndRJwt", null);
  const [, setUsername] = useLocalStorage("username", "");
  const [, setFavCity] = useLocalStorage("favCity", "");
  const [, setCurCity] = useLocalStorage("curCity", "");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleLoginSuccess = useCallback((data) => {
    if (!data) return;
    if (data.jwt) setJwt(data.jwt);
    if (data.username) setUsername(data.username);
    if (data.memberId) setMemberId(data.memberId);
    if (data.favCity) { setFavCity(data.favCity); setCurCity(data.favCity); }
  }, [setJwt, setUsername, setMemberId, setFavCity, setCurCity]);

  const login = useCallback(async (username, password) => {
    setLoading(true); setError(null);
    try {
      const data = await loginMember({ username, password });
      handleLoginSuccess(data);
      return data;
    } catch (err) {
      setError(err?.message || "Login failed");
      throw err;
    } finally {
      setLoading(false);
    }
  }, [handleLoginSuccess]);

  const register = useCallback(async (username, email, password, favouriteCity) => {
    setLoading(true); setError(null);
    try {
      return await registerMember({ username, email, password, favouriteCity });
    } catch (err) {
      setError(err?.message || "Registration failed");
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const logout = useCallback(() => { setMemberId(null); setJwt(null); }, [setMemberId, setJwt]);

  return { memberId, jwt, isAuthenticated: Boolean(memberId && jwt), login, register, logout, loading, error };
}