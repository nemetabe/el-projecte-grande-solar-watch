import React, { useState, useEffect } from "react";
import dayjs from "dayjs";
//import Map from "../components/map/Map.jsx";
import DateSelector from "../components/DateSelector.jsx";
import { fetchData } from "../utils.js";
import { Box } from '@mui/material';


const Browse = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [submitted, setSubmitted] = useState(0);
  const [currentDate, setCurrentDate] = useState(dayjs());
  const [currentCity, setCurrentCity] = useState("Budapest");
  const [solarTimes, setSolarTimes] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchSolarData(currentDate, currentCity);
  }, []);

  useEffect(() => {
    fetchSolarData(currentDate, currentCity);
    
  }, [submitted]);

  const fetchSolarData = async (date, city) => {
    setIsLoading(true);
    setError(null);
    
    try {
      const formattedDate = dayjs(date).format("YYYY-MM-DD");
      const url = `/solar?date=${formattedDate}&city=${city}`;
      console.log("Fetching from URL:", url);
      
      const data = await fetchData(url);
      console.log("Response received:", data);
    
      if (!data || typeof data !== 'object') {
        throw new Error("Invalid response data structure");
      }
      
      setSolarTimes({
        sr: data.sunrise,
        ss: data.sunset,
        name: data.city?.name || "Unknown Location",
        country: data.city?.country || "Unknown Country",
        sn: data.solarNoon,
        date: (data.date[0] + "/" + data.date[1] + "/" + data.date[2])
      });
    } catch (error) {
      console.error("Failed to fetch solar data:", error);
      
      let errorMessage = "Failed to load solar data. ";
      if (error.message.includes("JSON")) {
        errorMessage += "Server response format error.";
      } else if (error.message.includes("HTTP error")) {
        errorMessage += "Server error occurred.";
      } else {
        errorMessage += "Please try again.";
      }
      
      setError(errorMessage);
      setSolarTimes(null);
    } finally {
      setIsLoading(false);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!currentDate || !dayjs(currentDate).isValid()) {
      setError("Please select a valid date");
      return;
    }
    setSubmitted(prev => prev + 1);
  };

  const handleDateChange = (newDate) => {
    setCurrentDate(newDate || dayjs());
  };

  const handleCityNameChange = (name) => {
    setCurrentCity(name);
  };

  return (
    <div className="p4 m2">
      {/* Header Section */}
      <div className="mb-4 mt-4">
        {isLoading && (
          <h3 className="text-white text-2xl">
            Just a moment, your content is loading...
          </h3>
        )}
        
        {error && (
          <div className="m1 text-red-400 text-lg p3">
            {error}
          </div>
        )}
      </div>
      {/* Solar Times Display */}
      {solarTimes !== null && (
        <div className="block p4">
          <h2>{solarTimes.name + "  - " + solarTimes.country}</h2>
          <h3>{solarTimes.date}</h3>
          <h3>sunrise: {solarTimes.sr}</h3>
          <h3>noon: {solarTimes.sn}</h3>
          <h3>sunset: {solarTimes.ss}</h3>
        </div>
      )}    

      {/* Form Section */}
      <Box className="m1">

      <form className="blur-30 p6" onSubmit={handleSubmit}>
        <div className="p6 mb-3">
          <label htmlFor="cityName" className="m2"> select a city
            <input 
              id="cityName" 
              className="w-full px-3 py-2 rounded"
              type="text" 
              value={currentCity} 
              onChange={e => handleCityNameChange(e.target.value)}
              required={true}
              />
          </label>
        </div>

        <div className="p4 m3">
          <DateSelector 
            date={currentDate} 
            onDateChange={handleDateChange} 
            />
        </div>

        <button 
          type="submit" 
          disabled={isLoading}
          className="bg-blue-600 m1 hover:bg-blue-700 disabled:bg-gray-500 text-white px-6 py-2 rounded transition-colors"
          >
          {isLoading ? "Loading..." : "Let's go!"}
        </button>
      </form>
    </Box>
    </div>
  );
};

export default Browse;