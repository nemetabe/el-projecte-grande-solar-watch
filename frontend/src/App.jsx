import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import NavBar from './components/layout/NavBar';
//import { timePeriods, skyGradients } from './data/backgroundGradients';

function App() {
  localStorage.removeItem("favCity");
  localStorage.removeItem("curCity");
  
  const [currentTextColor, setCurrentTextColor] = useState('#2F4F4F');
  const [currentGradient, setCurrentGradient] = useState(
    'linear-gradient(to bottom, #5ee6f9, #76d3f0, #8ac0df, #95afc8, #989faf, #9e9cac, #a399a6, #a696a0, #bf9ba0, #d1a296, #d6b089, #c9c285)'
  );
  // const [currentGradientName, setCurrentGradientName] = useState('Noon - Classic Bright');
  //
  // const handleGradientChange = (gradient) => {
    //   setCurrentGradient(gradient.gradientCss);
    //   setCurrentGradientName(gradient.name);
    //   setCurrentTextColor(gradient.textColor);
    // };
    // dark
    // background-image: linear-gradient(to top, #000000, #000000, #010000, #010001, #010001, #040305, #070708, #0a0a0c, #111112, #151517, #1a1a1b, #1e1e20);
    //light
    // background-image: linear-gradient(to top, #1d222b, #313b48, #455766, #597485, #6e93a5, #7ba7b5, #89bbc5, #9ad0d3, #abddd5, #c1e9d7, #d9f4da, #f2ffe0);
    
//'linear-gradient(to bottom, #87CEEB 0%, #FFFFFF 5%, #FFD700 100%)'
//'linear-gradient(to bottom, #845EC2 0%, #D65DB1 5%, #FF6F91 23%, #FF9671 50%, #FFC75F 83%, #F9F871 100%)'

//   background-image: linear-gradient(to right top, #1d222b, #313b48, #455766, #597485, #6e93a5, #72a8b8, #77bdc9, #7fd2d6, #81e0d2, #93edc6, #b1f8b5, #d9ffa3);
//   background-image: linear-gradient(to left top, #1d222b, #313b48, #455766, #597485, #6e93a5, #72a8b8, #77bdc9, #7fd2d6, #81e0d2, #93edc6, #b1f8b5, #d9ffa3);

//   background-image: linear-gradient(to top, #161818, #002a37, #003968, #00439b, #003dbe, #1938bd, #2832bc, #332cbb, #4239a3, #4c448a, #524d70, #555555);
//   background-image: linear-gradient(to top, #161818, #00222b, #002a49, #002d69, #00277d, #00217b, #001b78, #001575, #071962, #111b4f, #171b3c, #1a1b28);

//   background-image: linear-gradient(to right top, #0b0b50, #541559, #842e5e, #ab4f64, #c9766d, #d08d6d, #d1a574, #cfbd84, #b9c78f, #a3cfa2, #90d5ba, #85d8d3);
//   background-image: radial-gradient(circle, #5ee6f9, #76d3f0, #8ac0df, #95afc8, #989faf, #9e9cac, #a399a6, #a696a0, #bf9ba0, #d1a296, #d6b089, #c9c285);
//   background-image: linear-gradient(to bottom, #5ee6f9, #76d3f0, #8ac0df, #95afc8, #989faf, #9e9cac, #a399a6, #a696a0, #bf9ba0, #d1a296, #d6b089, #c9c285);
//   background-image: linear-gradient(to bottom, #2289b4, #5e83c6, #9d76c2, #cf66a4, #e86074, #ef685c, #ee7643, #e58727, #ed9222, #f49e1b, #faaa10, #ffb700);
//   background-image: linear-gradient(to bottom, #15314d, #333566, #623170, #921f67, #b8004b, #c80e3b, #d32726, #d93f00, #e34d00, #ec5b00, #f66900, #ff7600);


  return (
    <div
        className="bg-cover bg-center w-full bg-no-repeat h-screen w-screen min-h-screen font-inter antialiased flex "
      style={{
        backgroundImage: currentGradient,
        backgroundRepeat: 'no-repeat',
        color: currentTextColor,
      }}
    >
      <NavBar
        textColor={currentTextColor}
      /> 
      <main className="flex-grow flex items-center justify-center w-full p4 md:p-8 overflow-screen">
        <Outlet />
      </main>
    </div>
  );
}

export default App;