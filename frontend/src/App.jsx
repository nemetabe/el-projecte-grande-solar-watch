import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import { timePeriods, skyGradients } from './data/backgroundGradients';

function App() {
  const [currentGradient, setCurrentGradient] = useState(
    'linear-gradient(to bottom, #87CEEB 0%, #FFFFFF 50%, #FFD700 100%)'
  );
  const [currentGradientName, setCurrentGradientName] = useState('Noon - Classic Bright');
  const [currentTextColor, setCurrentTextColor] = useState('#2F4F4F');

  const handleGradientChange = (gradient) => {
    setCurrentGradient(gradient.gradientCss);
    setCurrentGradientName(gradient.name);
    setCurrentTextColor(gradient.textColor);
  };

  return (
    <div 
      className="bg-cover bg-center bg-no-repeat h-screen w-screen min-h-screen font-inter antialiased flex flex-col"
      style={{ 
        backgroundImage: currentGradient, 
        backgroundRepeat: 'no-repeat', 
        transition: 'background-image 10s ease-in-out',
        color: currentTextColor
      }}
    >
      <Navbar 
        currentGradientName={currentGradientName} 
        skyGradients={skyGradients} 
        onGradientChange={handleGradientChange}
        textColor={currentTextColor}
      />
      <main className="flex-grow flex flex-col items-center justify-center pb-8 w-full p-8">
        <Outlet />
      </main>
    </div>
  );
}

export default App;