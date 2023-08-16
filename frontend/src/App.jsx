import { useRef, useState, useEffect, cloneElement } from 'react';
import { Route, Routes, useNavigate, useLocation } from 'react-router-dom';
import { useData } from './utils/Context';
import './App.css';
import InteriorColor from './pages/InteriorColorPage';
import TrimSelect from './pages/TrimSelectPage';
import ModelType from './pages/ModelTypePage';
import ExteriorColor from './pages/ExteriorColorPage';
import OptionPicker from './pages/OptionPickerPage';
import Estimation from './pages/EstimationPage';
import Header from './components/Header';

function App() {
  const { movePage, clonePage } = useData();
  const nextContentRef = useRef(null);
  const nowContentRef = useRef(null);

  return (
    <div>
      <Header />
      <div ref={nextContentRef} className={movePage.nextContentRef}>
        <Routes>
          <Route>
            <Route path="/" element={<TrimSelect />} />
            <Route path="/modelType" element={<ModelType />} />
            <Route path="/exteriorColor" element={<ExteriorColor />} />
            <Route path="/interiorColor" element={<InteriorColor />} />
            <Route path="/optionPicker" element={<OptionPicker />} />
            <Route path="/estimation" element={<Estimation />} />
          </Route>
        </Routes>
      </div>
      <div ref={nowContentRef} className={movePage.nowContentRef}>
        {clonePage[movePage.clonePage]}
      </div>
    </div>
  );
}

export default App;
