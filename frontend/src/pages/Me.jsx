import React, { useEffect, useState } from "react";
import SolarReportCard from "../components/solar/SolarReportCard.jsx";
import ErrorPage from "../components/ErrorPage.jsx";
import {CityNameCard} from "../components/browser/CityNameCard.jsx";

import Typography from "@mui/material/Typography";
import Skeleton from "@mui/material/Skeleton";
import Container from "@mui/material/Container";
import Stack from '@mui/material/Stack';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import "../index.css";

// const Item = styled(Paper)(({ theme }) => ({
//     backgroundColor: '#fff',
//     ...theme.typography.body2,
//     padding: theme.spacing(1),
//     textAlign: 'center',
//     color: (theme.vars ?? theme).palette.text.secondary,
//     ...theme.applyStyles('dark', {
//         backgroundColor: '#1A2027',
//     }),
// }));

// const MePage = () => {
//     const [savedCities, setSavedCities] = useState([]);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState(null);

//     const fetchSavedCities = async () => {
//     try {
//         const memberId = localStorage.getItem("memberId");
//         const token = localStorage.getItem("solAndRJwt")?.replace(/^"|"$/g, "");

//         if (!memberId || !token) {
//         throw new Error("Please log in to view saved cities");
//         }

//         console.log('Fetching saved cities for member:', memberId);
        
//         const cities = await fetchData(
//         `members/${memberId}/saved-cities`, 
//         "GET", 
//         null,
//         token
//         );
        
//         console.log('Received cities data:', cities);

//         setSavedCities(
//         Array.isArray(cities)
//             ? cities.map(c => {
//                 const cityObj = typeof c.city === "object" && c.city !== null ? c.city : {};
//                 return {
//                 id: c.id,
//                 name: cityObj.name ?? c.city ?? "Unknown",
//                 country: c.country ?? cityObj.country ?? "Unknown",
//                 };
//             })
//             : []
//         );

//     } catch (err) {
//         console.error("Error fetching saved cities:", err);
//         const message = err?.message ?? (typeof err === "string" ? err : "Unknown error");
//         setError("Failed to load saved cities: " + message);
//     } finally {
//         setLoading(false);
//     }
//     }

//     useEffect(() => {
//         fetchSavedCities()
//     }, [])

//     if (loading) {
//         return (
//             <Container maxWidth="md" sx={{ py: 4 }}>
//                 <Skeleton variant="rectangular" width={210} height={118} />
//             </Container>
//         );
//     }

//     if (error) {
//         return (
//             <ErrorPage error={error} />
//         );
//     }




//   return (<Container maxWidth="md" sx={{ py: 4 }}>
//           <Typography variant="h2" gutterBottom>
//               <h2> My Saved Cities</h2>
//           </Typography>

//                   <Stack direction="row-reverse"
//                          spacing={2}
//                          sx={{
//                              justifyContent: "flex-end",
//                              alignItems: "center",
//                              overflow: "scroll"
//                          }}>
//                       {savedCities && savedCities.length > 0 ? (
//                           savedCities.map((saved) => (
//                               <Item key={saved.id}>
//                                   <CityNameCard
//                                       city={saved.name || saved.city}
//                                       country={saved.country}
//                                       id={saved.id}
//                                   />
//                               </Item>
//                           ))
//                       ) : (
//                           <Typography variant="h6" color="textSecondary" align="center" mt={4}>
//                               No saved cities found. Start by saving some cities from the Browse page.
//                           </Typography>
//                       )}
//                   </Stack>
//       </Container>
//   );
// }

// export default MePage;


// import React, { useEffect, useState } from "react";
// import Container from "@mui/material/Container";
// import Typography from "@mui/material/Typography";
// import Skeleton from "@mui/material/Skeleton";
// import Stack from "@mui/material/Stack";
// import Paper from "@mui/material/Paper";

// import { styled } from "@mui/material/styles";

// import ErrorPage from "../components/ErrorPage";
// import { CityNameCard } from "../components/browser/CityNameCard";

// const Item = styled(Paper)(({ theme }) => ({
//     padding: theme.spacing(1),
//     textAlign: "center",
// }));

// export default function MePage() {

//     const [savedCities, setSavedCities] = useState([]);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState(null);

//     useEffect(() => {

//         async function load() {

//             try {

//                 const memberId = localStorage.getItem("memberId");
//                 const token = localStorage.getItem("solAndRJwt");

//                 if (!memberId || !token) {
//                     throw new Error("Please log in");
//                 }

//                 const cities =
//                     await fetchSavedCitiesForMember(memberId, token);

//                 setSavedCities(cities);

//             } catch (err) {

//                 if (err.message === "Unauthorized") {

//                     window.location.href = "/login";
//                     return;
//                 }

//                 setError(err.message);

//             } finally {
//                 setLoading(false);
//             }
//         }

//         load();

//     }, []);

//     if (loading) {
//         return (
//             <Container sx={{ py: 4 }}>
//                 <Skeleton variant="rectangular" height={120} />
//             </Container>
//         );
//     }

//     if (error) {
//         return <ErrorPage error={error} />;
//     }

//     return (

//         <Container sx={{ py: 4 }}>

//             <Typography variant="h4" gutterBottom>
//                 My Saved Cities
//             </Typography>

//             {savedCities.length === 0 ? (

//                 <Typography>
//                     No saved cities yet.
//                 </Typography>

//             ) : (

//                 <Stack
//                     direction="row"
//                     spacing={2}
//                     sx={{ overflowX: "auto" }}
//                 >

//                     {savedCities.map(city => (

//                         <Item key={city.id}>

//                             <CityNameCard
//                                 id={city.id}
//                                 city={city.name}
//                                 country={city.country}
//                             />

//                         </Item>

//                     ))}

//                 </Stack>

//             )}

//         </Container>
//     );
// }
import { useMemberSavedCities }
    from "../hooks/data/useMemberSavedCities";

export default function Me() {

    const {
        cities,
        loading,
        error
    } = useMemberSavedCities();

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (

        <>
            {cities.map(city => (
                <div key={city.id}>
                    {city.name}, {city.country}
                </div>
            ))}
        </>
    );
}