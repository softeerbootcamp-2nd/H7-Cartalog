import { Route, Routes } from 'react-router-dom';
import Interaction from './interaction/Interaction';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Interaction />} />
    </Routes>
  );
}

export default App;
