import React from 'react';
import Typography from '@mui/material/Typography';

export const SolarTimeRow = ({ icon, label, time }) => {
  if (!time) return null;

  return (
    <div className="flex items-center mb-2">
      <span className="mr-2 text-gray-500">{icon}</span>
      <Typography variant="body1" className="font-medium text-gray-700">
        {label}:
      </Typography>
      <Typography variant="body1" className="ml-2 text-gray-900">
        {time}
      </Typography>
    </div>
  );
};
// import Typography from "@mui/material/Typography";

// export const SolarTimeRow = ({ icon, label, time }) => (
//     <div className="flex-start items-center  mb-2">
//         <span className="mr-2 text-gray-500">{icon}</span>
//         <Typography variant="h7" className="font-medium text-gray-700">
//             <h7>
//                 {label}:
//             </h7>
//         </Typography>
//         <Typography variant="h7" className="ml-2 text-gray-900">
//             <h7>
//                 {time}
//             </h7>
//         </Typography>
//     </div>
// );