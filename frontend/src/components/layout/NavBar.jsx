import React, { useState, useEffect, useRef } from 'react';
import { Link } from 'react-router-dom';

const Navbar = ({
  currentGradientName,
  skyGradients,
  onGradientChange,
  textColor
}) => {
  const [isSkyDropdownOpen, setIsSkyDropdownOpen] = useState(false);
  const [isAuthDropdownOpen, setIsAuthDropdownOpen] = useState(false);

  const skyDropdownRef = useRef(null);
  const authDropdownRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (skyDropdownRef.current && !skyDropdownRef.current.contains(event.target)) {
        setIsSkyDropdownOpen(false);
      }
      if (authDropdownRef.current && !authDropdownRef.current.contains(event.target)) {
        setIsAuthDropdownOpen(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const getHoverColor = (baseColor) => {
    if (baseColor.includes('#F') || baseColor.includes('#E') || baseColor.includes('#D')) {
      return `${baseColor}CC`;
    } else {
      return `${baseColor}DD`;
    }
  };

  const hoverColor = getHoverColor(textColor);

  console.log('skyGradients', skyGradients);
  
  return (
    <nav className="w-full p-4 shadow-lg flex justify-center items-center fixed top-0 left-0 right-0 z-10 rounded-b-lg">
      <div className="flex space-x-6">
        <Link
          to="/"
          className="text-lg font-semibold transition-colors duration-300"
          style={{ 
            color: textColor,
            ':hover': { color: hoverColor }
          }}
          onMouseEnter={(e) => e.target.style.color = hoverColor}
          onMouseLeave={(e) => e.target.style.color = textColor}
        >
          Home
        </Link>
        <Link
          to="/browse"
          className="text-lg font-semibold transition-colors duration-300"
          style={{ 
            color: textColor,
            ':hover': { color: hoverColor }
          }}
          onMouseEnter={(e) => e.target.style.color = hoverColor}
          onMouseLeave={(e) => e.target.style.color = textColor}
        >
          Browse
        </Link>

        {/* Sky Gradients Dropdown */}
        <div className="relative" ref={skyDropdownRef}>
          <button
            onClick={() => setIsSkyDropdownOpen(!isSkyDropdownOpen)}
            className="text-lg font-semibold transition-colors duration-300 bg-transparent border-none cursor-pointer p-0"
            style={{ 
              color: textColor,
              ':hover': { color: hoverColor }
            }}
            onMouseEnter={(e) => e.target.style.color = hoverColor}
            onMouseLeave={(e) => e.target.style.color = textColor}
          >
            Sky Gradients ▼
          </button>
          {isSkyDropdownOpen && (
            <div className="absolute top-full left-1/2 -translate-x-1/2 mt-2 w-[300px] sm:w-[400px] md:w-[600px] bg-white bg-opacity-90 rounded-lg shadow-xl overflow-hidden max-h-[80vh] flex flex-col">
              <h3 className="text-xl font-bold text-gray-800 p-4 border-b border-gray-200 text-center">
                Select a Sky Gradient
              </h3>
              <div className="flex-grow overflow-y-auto p-4 grid grid-cols-1 sm:grid-cols-2 gap-4">
                {skyGradients &&
                  Object.entries(skyGradients).map(([key, gradient]) => (
                    <button
                      key={key}
                      onClick={() => {
                        onGradientChange(gradient);
                        setIsSkyDropdownOpen(false);
                      }}
                      className={`
                        relative flex flex-col items-center justify-center p-2 rounded-lg shadow-md
                        hover:shadow-lg transition-all duration-300 transform hover:-translate-y-1
                        focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500
                        ${currentGradientName === gradient.name
                          ? 'ring-2 ring-blue-500 ring-offset-2 scale-105'
                          : 'bg-gray-100 text-gray-800'}
                      `}
                      style={{ minHeight: '80px' }}
                    >
                      <div
                        className="w-full h-12 rounded-md mb-1 border border-gray-300 shadow-inner"
                        style={{ 
                          backgroundImage: gradient.gradientCss,
                          height: '-webkit-fill-available'
                        }}
                      ></div>
                      <span className="text-xs font-medium text-center">{gradient.name}</span>
                    </button>
                ))}
              </div>
            </div>
          )}
        </div>

        {/* Login/Register Dropdown */}
        <div className="relative" ref={authDropdownRef}>
          <button
            onClick={() => setIsAuthDropdownOpen(!isAuthDropdownOpen)}
            className="text-lg font-semibold transition-colors duration-300 bg-transparent border-none cursor-pointer p-0"
            style={{ 
              color: textColor,
              ':hover': { color: hoverColor }
            }}
            onMouseEnter={(e) => e.target.style.color = hoverColor}
            onMouseLeave={(e) => e.target.style.color = textColor}
          >
            Account ▼
          </button>
          {isAuthDropdownOpen && (
            <div className="absolute top-full right-0 mt-2 w-48 bg-white bg-opacity-90 rounded-lg shadow-xl py-2 flex flex-col">
              <Link
                to="/login"
                onClick={() => setIsAuthDropdownOpen(false)}
                className="px-4 py-2 text-gray-800 hover:bg-gray-100 w-full text-left"
              >
                Login
              </Link>
              <Link
                to="/register"
                onClick={() => setIsAuthDropdownOpen(false)}
                className="px-4 py-2 text-gray-800 hover:bg-gray-100 w-full text-left"
              >
                Register
              </Link>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;