import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
//import { timePeriods, skyGradients } from './data/backgroundGradients';

function App() {
  const [currentGradient, setCurrentGradient] = useState(
    'linear-gradient(to bottom, #87CEEB 0%, #FFFFFF 50%, #FFD700 100%)'
  );
  const [currentTextColor, setCurrentTextColor] = useState('#2F4F4F');
  // const [currentGradientName, setCurrentGradientName] = useState('Noon - Classic Bright');
  //
  // const handleGradientChange = (gradient) => {
  //   setCurrentGradient(gradient.gradientCss);
  //   setCurrentGradientName(gradient.name);
  //   setCurrentTextColor(gradient.textColor);
  // };

  return (
    <div
        className="bg-cover bg-center w-full bg-no-repeat h-screen w-screen min-h-screen font-inter antialiased flex "
      style={{
        backgroundImage: currentGradient,
        backgroundRepeat: 'no-repeat',
        color: currentTextColor,
      }}
    >
      <Navbar
        textColor={currentTextColor}
      />
      <main className="flex-grow flex items-center justify-center w-full p4 md:p-8 overflow-hidden">
        <Outlet />
      </main>
    </div>
  );
}

export default App;