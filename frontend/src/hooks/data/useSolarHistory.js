import { useRef, useCallback } from 'react';
import { useLocalStorage } from '../state/useLocalStorage';

const MAX_HISTORY_ITEMS = 10;

export function useSolarHistory() {
  const [history, setHistory] = useLocalStorage('searchHistory', []);
  const currentSearchRef = useRef(null);

  const isDuplicateSearch = useCallback((search, historyList) => {
    if (!search || !search.city ) return false; 
    return historyList.length > 0 && historyList.some(item => item?.city?.id === search.city.id);
  }, []);

  const addToHistory = useCallback((searchResult) => {
    if (!searchResult || !searchResult.city) {
      console.warn('Invalid search result, not adding to history');
      return;
    }

    currentSearchRef.current = searchResult;
    if (history !== null ){
      if (isDuplicateSearch(searchResult, history)) {
        console.log('Duplicate search, not adding to history');
        return;
      }
      const newHistory = [searchResult, ...history].slice(0, MAX_HISTORY_ITEMS);
      setHistory(newHistory);
    }

    console.log('Added to history:', searchResult.city.name);
  }, [history, isDuplicateSearch, setHistory]);

  const clearHistory = useCallback(() => {
    setHistory([]);
    currentSearchRef.current = null;
  }, [setHistory]);

  const removeFromHistory = useCallback((cityId) => {
    setHistory(prev => prev.filter(item => item?.city?.id !== cityId));
  }, [setHistory]);

  return { history, currentSearch: currentSearchRef.current, addToHistory, clearHistory, removeFromHistory };
}