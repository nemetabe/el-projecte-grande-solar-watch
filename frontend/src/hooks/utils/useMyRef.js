import { useEffect, useRef } from 'react';

export function useMyRef(externalRef = null, callback = null) {
  const internalRef = useRef(null); // owned by the component
  const callbackRef = useRef(callback); // stable callback reference

  // ensures latest callback without re-running effects
  useEffect(() => {
    callbackRef.current = callback;
  }, [callback]);

  // runs callback once the DOM is ready
  useEffect(() => {
    const element = internalRef.current;
    if (element && callbackRef.current) {
      callbackRef.current(element);
    }
  }, []);

  // forwards ref support
  useEffect(() => {
    if (externalRef && typeof externalRef === 'object' && 'current' in externalRef) {
      externalRef.current = internalRef.current;
    }
  }, [externalRef]);

  return internalRef;
}