import { Route, Routes } from 'react-router-dom';
import InteriorColor from './pages/InteriorColorPage';
import Layout from './components/PageLayout';
import TrimSelect from './pages/TrimSelectPage';
import ModelType from './pages/ModelTypePage';
import ExteriorColor from './pages/ExteriorColorPage';
import AddOption from './pages/AddOptionPage';
import EstimateFinish from './pages/EstimateFinishPage';

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route path="/" element={<TrimSelect />} />
        <Route path="/modelType" element={<ModelType />} />
        <Route path="/exteriorColor" element={<ExteriorColor />} />
        <Route path="/interiorColor" element={<InteriorColor />} />
        <Route path="/addOptions" element={<AddOption />} />
        <Route path="/estimateFinish" element={<EstimateFinish />} />
      </Route>
    </Routes>
  );
}

export default App;
