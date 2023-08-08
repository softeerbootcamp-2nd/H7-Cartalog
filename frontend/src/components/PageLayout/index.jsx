import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Header from '../Header';

function Layout() {
  const [state, setState] = useState({
    page: null,
    trim: null,
    price: null,
    powerTrain: null,
    bodyType: null,
    wd: null,
    exteriorColor: null,
    interiorColor: null,
    option: null,
  });

  return (
    <>
      <Header />
      <Outlet state={state} setState={setState} />
    </>
  );
}

export default Layout;
