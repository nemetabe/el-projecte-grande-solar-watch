import React, {useEffect, useState} from 'react';
import Skeleton from '@mui/material/Skeleton';
import {Link} from "react-router-dom";

export default function Home() {
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setLoading(false);
        }, 40);

        return () => clearTimeout(timer);
    }, []);

    return (
        <div className="text-center h-screen">
            {loading ? (
                <Skeleton
                    variant="circular"
                    width={100}
                    color="primary"
                    height={100}
                    animation="wave"
                    className="mx-auto"
                />
            ) : (
                <div>
                <h1
                    className="font-flavors text-8xl"
                >
                    SoL  &  R
                </h1>
                <Link to={"/browse"}
                         onMouseEnter={(e) => e.target.style.color = "#ff8282"}
                         onMouseLeave={(e) => e.target.style.color = "#000"}
                         className="text-lg font-semibold transition-colors duration-300"

                >
                    <h2
                    className="font-flavors text-1xl"
                >
                    YOUR SOLAR CALENDAR
                </h2>
                </Link>
                </div>
            )}
        </div>
    );
}
