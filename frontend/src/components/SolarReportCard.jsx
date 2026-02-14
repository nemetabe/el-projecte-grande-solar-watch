import * as React from 'react';
import { styled } from '@mui/material/styles';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Collapse from '@mui/material/Collapse';
import Avatar from '@mui/material/Avatar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import { red, blue } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import SaveIcon from '@mui/icons-material/Save';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import WbSunnyIcon from '@mui/icons-material/WbSunny';
import Brightness3Icon from '@mui/icons-material/Brightness3';
import {useEffect, useState} from 'react';
import {SolarTimeRow} from './SolarTimeRow';

const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme }) => ({
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
    variants: [
        {
            props: ({ expand }) => !expand,
            style: {
                transform: 'rotate(0deg)',
            },
        },
        {
            props: ({ expand }) => !!expand,
            style: {
                transform: 'rotate(180deg)',
            },
        },
    ],
}));



export default function SolarReportCard({ solarTimes, onSave, solarCityId }) {
  const [expanded, setExpanded] =
      useState(false);
  const [isFavorite, setIsFavorite] = useState(false);
  const [cityId, setCityId] = useState(solarCityId);
  const handleExpandClick = () => setExpanded(!expanded);
  const handleFavoriteClick = () => setIsFavorite(!isFavorite);
  const handleSaveClick = () => onSave && onSave(solarCityId);


  if (!solarTimes) return null;

  const { city, date, ...times } = solarTimes;
  const locationName = city ? `${city.name}, ${city.country}` : ( `${localStorage.getItem("favCity").city}, ${localStorage.getItem("favCity").country}`|| 'Unknown Location');
  const dateString = date ? date.join(' / ') : '';

  return (
    <Card sx={{ maxWidth: 500, margin: '2rem auto' , borderRadius: '2rem'}}>
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
        <IconButton
          aria-label="save"
          onClick={handleSaveClick}
          color="primary"
          hidden={onSave === null}
        >
          <SaveIcon />
        </IconButton>
        <ExpandMore
          expand={expanded}
          onClick={handleExpandClick}
          aria-expanded={expanded}
          aria-label="show more"
          sx={{
            transform: expanded ? 'rotate(180deg)' : 'rotate(0deg)',
            marginLeft: 'auto',
            transition: 'transform 0.5s',
          }}
        >
          <ExpandMoreIcon />
        </ExpandMore>
      </CardActions>

      <Collapse in={expanded} timeout="auto" unmountOnExit>
        <CardContent>
          <div className="grid grid-cols-1 gap-2">
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
              {
                    times.timezone ? (
                <div className="col-span-2 mt-2 pt-2 border-t border-gray-200">
                        <Typography variant="h8" className="text-gray-500">
                            <h8> Timezone: {times.timezone}</h8>
                        </Typography>
                </div>
                    ) : (
                        <>
                        </>
                    )}
          </div>
        </CardContent>
      </Collapse>
    </Card>
  );
}