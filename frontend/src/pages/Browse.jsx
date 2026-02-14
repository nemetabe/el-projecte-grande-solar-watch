import React, { useState, useEffect } from "react";
import dayjs from "dayjs";
import DateSelector from "../components/DateSelector.jsx";
import { fetchData } from "../utils.js";
import { Box } from '@mui/material';
import SolarReportCard from "../components/SolarReportCard.jsx";
import Skeleton from '@mui/material/Skeleton';
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import {CityNameCard} from "../components/CityNameCard.jsx";
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import {SolarCard} from "../components/SolarCard.jsx";
import Container from "@mui/material/Container";

const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: '#fff',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
    color: (theme.vars ?? theme).palette.text.secondary,
    ...theme.applyStyles('dark', {
        backgroundColor: '#1A2027',
    }),
}));

const Browse = () => {
  const [isLoading, setIsLoading] = useState(false);
  const [submitted, setSubmitted] = useState(0);
  const [saved, setSaved] = useState(0);
  const [cityId, setCityId] = useState(null)
  const [currentDate, setCurrentDate] = useState(dayjs());
  const [currentCity, setCurrentCity] = useState("Budapest");
  const [solarTimes, setSolarTimes] = useState(null);
  const [history, setHistory] = useState([]);
  const [error, setError] = useState(null);


  useEffect(() => {
    fetchSolarData(currentDate, currentCity);
  }, []);

  useEffect(() => {
    fetchSolarData(currentDate, currentCity);
    
  }, [submitted]);

    const getFormattedUrl =(date, city) =>{
        const formattedDate = dayjs(date).format("YYYY-MM-DD");
        return `solar?date=${formattedDate}&city=${city}`;
    }

    const handleHistory = (temp, prev) =>{
        if (!temp || !temp.city) return prev;

        const isDuplicate = prev.some(item =>
            item?.city?.id === temp.city.id ||
            (item?.city?.city === temp.city.city && item?.city?.country === temp.city.country)
        );

        if (isDuplicate) return prev;

        return [temp, ...prev];
    }

    function getErrorMessage(error) {
        console.error("Failed to fetch solar data:", error);

        let errorMessage = "Failed to load solar data: ";
        if (error.message !== null && error.message.includes("JSON")) {
            errorMessage += "Server response format error.";
        } else if (error.message.includes("HTTP error")) {
            errorMessage += "Server error occurred.";
        } else {
            errorMessage += "Please try again.";
        }
        return errorMessage;
    }

    const fetchSolarData = async (date, city)=>  {
    setIsLoading(true);
    setError(null);

        try {

            const data = await fetchData(getFormattedUrl(date, city));
            const temp = solarTimes;

            if (!data || typeof data !== 'object') {
                throw new Error("Invalid response data structure");
            }

            setSolarTimes(data);
            setHistory(prev => {
                return handleHistory(temp, prev);
            });
    } catch (error) {
            setError(getErrorMessage(error));
      setSolarTimes(null);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(()=> {
    if(cityId !== null && solarTimes !== null){
      saveCity(cityId, solarTimes.id)
    }
  }, [saved])

  const saveCity = async (cityId, solarTimesId) => {
    await fetchData(`members/${localStorage.getItem("memberId")}/saved-cities`, "POST", {cityId:cityId, solarTimesId: solarTimesId},
      localStorage.getItem("solAndRJwt"))
  }

  const handleSave = (cityId) =>{
      setCityId(cityId);
      setSaved(prev => prev+1)
  }

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

  return (<Stack direction={"row"} spacing={1} sx={{
      justifyContent: "space-between",
          alignItems: "flex-start" ,
          minWidth: 0,
        overflow: "hidden"}}
      >
<Item>
    <Box sx={{
        borderRadius: 2,
        boxShadow: 2}}
    >
        {error && (
          <div className="m1 text-red-400 text-lg p3">
            {error}
          </div>
        )}
    </Box>
</Item>
          <Item>
          <Box sx={{
          p: 7,
          borderRadius: 2,
          boxShadow: 2,
      }}>

      {solarTimes !== null && (solarTimes.city.id !== null)&& (
          <SolarCard
              solarTimes={solarTimes}
              onSave={handleSave}
              solarCityId={solarTimes.city.id}/>
      )}
      </Box>
          </Item>

          <Item>
          <Box sx={{
          p: 7,
          borderRadius: 2,
          boxShadow: 2,
          }}>
              {isLoading && (
                  <div>
                      <Skeleton animation="wave" variant="circular" width={40} height={40} />
                      <Skeleton animation="wave" variant="rounded" width={40} height={40} />
                  </div>
              )}
      <form className="" onSubmit={handleSubmit}>
          <label htmlFor="cityName" className="m5">
              <h8> select a city</h8>
            <input
              id="cityName"
              className=""
              type="text"
              value={currentCity}
              onChange={e => handleCityNameChange(e.target.value)}
              required={true}
              />
          </label>


          <DateSelector
            date={currentDate}
            onDateChange={handleDateChange}
            />


        <button
          type="submit"
          disabled={isLoading}
          className="bg-blue-600 mb-5 pb-5 bb-4 hover:bg-blue-700 disabled:bg-gray-500 text-white px-6 py-2 rounded transition-colors"
          >
          {isLoading ? "Loading..." : "Let's go!"}
        </button>
      </form>
    </Box>
          </Item>

          <Item>

          {history && history.length > 0 ? (
            <Stack
                direction="column-reverse"
                spacing={2}
               sx={{
                   justifyContent: "flex-end",
                   alignItems: "",
                   minWidth: 0,
                   overflow: "hidden"
               }}>
                {history.map((search) => (
                    search?.city && (
                        <Item key={search.city.id}>
                            <CityNameCard city={search.city.name} country={search.city.country} id={search.city.id} />
                        </Item>
                    )
                ))}
            </Stack>
        ) : (
           <h7>
                No search history found. Start by saving some cities from the Browse page.
           </h7>

        )}
          </Item>
    </Stack>
  );
};

export default Browse;