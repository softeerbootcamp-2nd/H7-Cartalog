import React, { createContext, useContext, useState, useMemo } from 'react';

const StateContext = createContext();

export function StateProvider({ children }) {
  const stateKeys = [
    'page',
    'trimId',
    'powerTrain',
    'bodyType',
    'wd',
    'exteriorColor',
    'interiorColor',
    'addOption',
  ];

  const initialState = stateKeys.reduce((acc, key) => {
    acc[key] = null;
    return acc;
  }, {});

  const [state, setState] = useState(initialState);

  const contextValue = useMemo(() => {
    return {
      ...state,
      setState,
    };
  }, [state]);

  return <StateContext.Provider value={contextValue}>{children}</StateContext.Provider>;
}

export function useData() {
  return useContext(StateContext);
}
