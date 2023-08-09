import { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Header from '../Header';
import Footer from '../Footer';

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
      <Footer />
    </>
  );
}

export default Layout;
