import { useEffect, useState } from "react";

function resolveInitialValue(initialValue) {
  return typeof initialValue === "function"
    ? initialValue()
    : initialValue;
}

function loadFromLocalStorage(key, initialValue) {
  try {
    const item = localStorage.getItem(key);

    if (!item || item === "undefined") {
      return resolveInitialValue(initialValue);
    }

    return JSON.parse(item);
  } catch (error) {
    console.error(`Error reading localStorage key "${key}":`, error);
    return resolveInitialValue(initialValue);
  }
}

export function useLocalStorage(key, initialValue) {
  const [value, setValue] = useState(() =>
    loadFromLocalStorage(key, initialValue)
  );

  useEffect(() => {
    try {
      if (value === undefined) {
        localStorage.removeItem(key);
      } else {
        localStorage.setItem(key, JSON.stringify(value));
      }
    } catch (error) {
      console.error(`Error writing localStorage key "${key}":`, error);
    }
  }, [key, value]);

  return [value, setValue];
}