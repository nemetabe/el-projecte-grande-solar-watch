import React, { useState, useEffect } from 'react';
import { SolarTimeRow } from './SolarTimeRow';
import { useLocalStorage } from '../../hooks/state/useLocalStorage';

import Brightness3Icon from '@mui/icons-material/Brightness3';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { blue } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import SaveIcon from '@mui/icons-material/Save';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import WbSunnyIcon from '@mui/icons-material/WbSunny';

export function SolarCard({ solarTimes, onSave, solarCityId }) {
  const [isFavorite, setIsFavorite] = useState(false);
  const [favCity] = useLocalStorage('favCity', null);

  useEffect(() => {
    if (favCity && solarTimes?.city?.id === favCity.id) {
      setIsFavorite(true);
    }
  }, [favCity, solarTimes]);
  
  const handleFavoriteClick = () => {
    setIsFavorite(!isFavorite);
  };
  
  const handleSaveClick = () => {
    if (solarCityId) {
      onSave(solarCityId);
    }
  };

  if (!solarTimes) return null;

  const { city, date, ...times } = solarTimes;
  const locationName = city 
    ? `${city.name}, ${city.country}` 
    : (favCity ? `${favCity.city}, ${favCity.country}` : 'Unknown Location');
  const dateString = Array.isArray(date) ? date.join(' / ') : date || '';

  return (
    <Card 
      sx={{ 
        width: 400,
        background: 0, 
        boxShadow: 0,
        padding: '0.5rem', 
        borderRadius: '3rem',
      }}
    >
      <CardHeader
        avatar={
          <Avatar sx={{ bgcolor: blue[500] }} aria-label="solar-report">
            <WbSunnyIcon />
          </Avatar>
        }
        title={locationName}
        subheader={dateString}
      />

      <CardContent>
        <div className="grid grid-cols-1 gap-4 mb-4">
          <SolarTimeRow
            icon={<Brightness3Icon />}
            label="Night End"
            time={times.nightEnd}
          />
          <SolarTimeRow
            icon={<WbSunnyIcon />}
            label="First Light"
            time={times.firstLight}
          />
          <SolarTimeRow
            icon={<AccessTimeIcon />}
            label="Dawn"
            time={times.dawn}
          />
          <SolarTimeRow
            icon={<WbSunnyIcon />}
            label="Sunrise"
            time={times.sunrise}
          />
          <SolarTimeRow
            icon={<WbSunnyIcon />}
            label="Solar Noon"
            time={times.solarNoon}
          />
          <SolarTimeRow
            icon={<WbSunnyIcon />}
            label="Sunset"
            time={times.sunset}
          />
          <SolarTimeRow
            icon={<AccessTimeIcon />}
            label="Dusk"
            time={times.dusk}
          />
          <SolarTimeRow
            icon={<WbSunnyIcon />}
            label="Last Light"
            time={times.lastLight}
          />
          <SolarTimeRow
            icon={<Brightness3Icon />}
            label="Night Begin"
            time={times.nightBegin}
          />
          {times.timezone && (
            <div className="col-span-2 mt-2 pt-2 border-t border-gray-200">
              <Typography variant="body2" className="text-gray-500">
                Timezone: {times.timezone}
              </Typography>
            </div>
          )}
        </div>
      </CardContent>

      <CardActions disableSpacing>
        <IconButton
          aria-label="add to favorites"
          onClick={handleFavoriteClick}
          color={isFavorite ? 'error' : 'default'}
        >
          <FavoriteIcon />
        </IconButton>
        {onSave && (
          <IconButton
            aria-label="save"
            onClick={() => handleSaveClick()}
            color="primary"
          >
            <SaveIcon />
          </IconButton>
        )}
      </CardActions>
    </Card>
  );
}

// import * as React from 'react';
// import Card from '@mui/material/Card';
// import CardHeader from '@mui/material/CardHeader';
// import CardContent from '@mui/material/CardContent';
// import CardActions from '@mui/material/CardActions';
// import Avatar from '@mui/material/Avatar';
// import IconButton from '@mui/material/IconButton';
// import Typography from '@mui/material/Typography';
// import { red, blue } from '@mui/material/colors';
// import FavoriteIcon from '@mui/icons-material/Favorite';
// import SaveIcon from '@mui/icons-material/Save';
// import AccessTimeIcon from '@mui/icons-material/AccessTime';
// import WbSunnyIcon from '@mui/icons-material/WbSunny';
// import Brightness3Icon from '@mui/icons-material/Brightness3';
// import {useEffect, useState} from 'react';
// import {SolarTimeRow} from './SolarTimeRow';


// export function SolarCard({ solarTimes, onSave, solarCityId }) {
//     const [expanded, setExpanded] =
//         useState(false);
//     const [isFavorite, setIsFavorite] = useState(false);
//     const [cityId, setCityId] = useState(solarCityId);
//     const handleExpandClick = () => setExpanded(!expanded);
//     const handleFavoriteClick = () => setIsFavorite(!isFavorite);
//     const handleSaveClick = () => onSave && onSave(solarCityId);


//     if (!solarTimes) return null;

//     const { city, date, ...times } = solarTimes;
//     const locationName = city ? `${city.name}, ${city.country}` : ( `${localStorage.getItem("favCity").city}, ${localStorage.getItem("favCity").country}`|| 'Unknown Location');
//     const dateString = date ? date.join(' / ') : '';

//     return (
//         <Card sx={{ width: 500, margin: '2rem auto' ,padding: '1rem', borderRadius: '2rem'}}>
//             <CardHeader
//                 avatar={
//                     <Avatar sx={{ bgcolor: blue[500] }} aria-label="solar-report">
//                         <WbSunnyIcon />
//                     </Avatar>
//                 }
//                 title={locationName}
//                 subheader={dateString}
//             />

//             <CardContent>
//                 <div className="grid grid-cols-1 gap-4 mb-4">

//                 <SolarTimeRow
//                     icon={<Brightness3Icon />}
//                     label="Night End"
//                     time={times.nightEnd}
//                 />

//                 <SolarTimeRow
//                     icon={<WbSunnyIcon />}
//                     label="First Light"
//                     time={times.firstLight}
//                 />
//                 <SolarTimeRow
//                     icon={<AccessTimeIcon />}
//                     label="Dawn"
//                     time={times.dawn}
//                 />
//                 <SolarTimeRow
//                     icon={<WbSunnyIcon />}
//                     label="Sunrise"
//                     time={times.sunrise}
//                 />
//                 <SolarTimeRow
//                     icon={<WbSunnyIcon />}
//                     label="Solar Noon"
//                     time={times.solarNoon}
//                 />
//                 <SolarTimeRow
//                     icon={<WbSunnyIcon />}
//                     label="Sunset"
//                     time={times.sunset}
//                 />
//                 <SolarTimeRow
//                     icon={<AccessTimeIcon />}
//                     label="Dusk"
//                     time={times.dusk}
//                 />
//                 <SolarTimeRow
//                     icon={<WbSunnyIcon />}
//                     label="Last Light"
//                     time={times.lastLight}
//                 />
//                 <SolarTimeRow
//                     icon={<Brightness3Icon />}
//                     label="Night Begin"
//                     time={times.nightBegin}
//                 />
//                 {
//                     times.timezone ? (
//                         <div className="col-span-2 mt-2 pt-2 border-t border-gray-200">
//                             <Typography variant="h8" className="text-gray-500">
//                                 <h8> Timezone: {times.timezone}</h8>
//                             </Typography>
//                         </div>
//                     ) : (
//                         <>
//                         </>
//                     )}
//             </div>
//             </CardContent>

//             <CardActions disableSpacing>
//                 <IconButton
//                     aria-label="add to favorites"
//                     onClick={handleFavoriteClick}
//                     color={isFavorite ? 'error' : 'default'}
//                 >
//                     <FavoriteIcon />
//                 </IconButton>
//                 <IconButton
//                     aria-label="save"
//                     onClick={handleSaveClick}
//                     color="primary"
//                     hidden={onSave === null}
//                 >
//                     <SaveIcon />
//                 </IconButton>
//             </CardActions>
//         </Card>
//     );
// }