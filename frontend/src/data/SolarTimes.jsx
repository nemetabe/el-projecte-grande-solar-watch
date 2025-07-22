import React, {useState} from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';

export default function SolarTimes({sTimes}) {
  const [solarTime, setSolarTime]= useState(sTimes)


  return ( solarTime == undefined &&(
    <div className='container'>
    <Grid container spacing={3}>
      <Grid size={{ xs: 12, sm: 6, md: 4 }}>
        <Card variant="outlined">
          <CardContent>
            <Typography variant="caption">city</Typography>
            <Typography variant="h4">{solarTime.name}</Typography>
            <Typography variant="h4">{solarTime.country}</Typography>
          </CardContent>
        </Card>
      </Grid>
      <Grid size={{ xs: 12, sm: 6, md: 4 }}>
        <Card variant="outlined">
          <CardContent>
            <Typography variant="caption">sunrise</Typography>
            <Typography variant="h3">{solarTime.sr}</Typography>
            <Typography variant="caption">solar noon</Typography>
            <Typography variant="h4">{solarTime.sn}</Typography>
            <Typography variant="caption">sunset</Typography>
            <Typography variant="h3">{solarTime.ss}</Typography>
          </CardContent>
        </Card>
      </Grid>
       <Grid size={{ xs: 12, sm: 6, md: 4 }}>
        <Card variant="outlined">
          <CardContent>
            <Typography variant="caption">date</Typography>
            <Typography variant="h4">{solarTime.date[0]}</Typography>
          </CardContent>
        </Card>
      </Grid>
    </Grid>
    </div>
  ))
}