import React from 'react';

export function CityNameCard({ city, country, date }) {
  return (
    <div style={{
      padding: '12px',
      backgroundColor: '#f3f4f6',
      borderRadius: '8px',
      marginBottom: '8px'
    }}>
      <div style={{ fontWeight: 'bold', fontSize: '16px' }}>
        {city}, {country}
      </div>
      <div style={{ fontSize: '12px', color: '#6b7280', marginTop: '4px' }}>
        {date}
      </div>
    </div>
  );
}

// import * as React from 'react';
// import Card from '@mui/material/Card';
// import CardContent from '@mui/material/CardContent';
// import Button from '@mui/material/Button';
// import CardActionArea from '@mui/material/CardActionArea';
// import CardActions from '@mui/material/CardActions';
// import CardHeader from "@mui/material/CardHeader";
// import Typography from "@mui/material/Typography";
// import {Link} from "react-router-dom";




// export function CityNameCard({ city, country, date, onClick }) {
//     const handleClick = (e) => {
//         e.preventDefault();
//         if (onClick) {
//             onClick({ city, date });
//         }
//     };

//     return (
//         <Card sx={{ width: '100%', mb: 1 }}>
//             <CardActionArea component="div" onClick={handleClick}>
//                 <CardContent sx={{ p: 2 }}>
//                     <Typography variant="subtitle1" component="div" sx={{ fontWeight: 'medium' }}>
//                         {city}
//                         {country && `, ${country}`}
//                     </Typography>
//                     {date && (
//                         <Typography variant="body2" color="text.secondary">
//                           {`${date[0]} /${date[1]} /${date[2]}`}    
//                             {/* {new Date(date).toLocaleDateString()}
//                          */}
//                         </Typography>
//                     )}
//                 </CardContent>
//             </CardActionArea>
//         </Card>
//     );
// }