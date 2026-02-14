import dayjs from 'dayjs';
import { useCallback, useEffect } from 'react';
import { useLocalStorage } from './useLocalStorage';
import { usePrevious } from '../utils/usePrevious';

export function useCitySearch() {
  const [favCity] = useLocalStorage('favCity');
  const [currentCity, setCurrentCity] = useLocalStorage('curCity');
  const [currentDate, setCurrentDate] = useLocalStorage(
    'curDate',
    dayjs().format('YYYY-MM-DD')
  );

  const previousCity = usePrevious(currentCity);
  const previousDate = usePrevious(currentDate);

  // syncs curCity to favCity AFTER mount only if no curCity present
  useEffect(() => {
    if (favCity && !currentCity) {
      setCurrentCity(favCity);
    }
  }, [favCity, currentCity, setCurrentCity]);

  const updateCity = useCallback((city) => {
    if (city && city.trim()) {
      setCurrentCity(city.trim());
    }
  }, [setCurrentCity]);

  const updateDate = useCallback((date) => {
    if (date && dayjs(date).isValid()) {
      setCurrentDate(dayjs(date).format('YYYY-MM-DD'));
    }
  }, [setCurrentDate]);

  const resetToToday = useCallback(() => {
    setCurrentDate(dayjs().format('YYYY-MM-DD'));
  }, [setCurrentDate]);

  return {
    currentCity,
    currentDate,
    previousCity,
    previousDate,
    favCity,
    updateCity,
    updateDate,
    resetToToday,
  };
}