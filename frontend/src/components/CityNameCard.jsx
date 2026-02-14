import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import CardActionArea from '@mui/material/CardActionArea';
import CardActions from '@mui/material/CardActions';
import CardHeader from "@mui/material/CardHeader";
import Typography from "@mui/material/Typography";



export function CityNameCard({city, country, id}) {
    return (
        <Card >
            <CardActionArea>
                <CardContent sx={{minWidth: 300}}>

                    <Typography variant="h7" className="font-medium text-gray-700">
                    <h7>
                        {city}
                    </h7>
                    <h8>
                        , {country}
                    </h8>
                        </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    );
}