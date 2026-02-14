import { useRouteError } from "react-router-dom";
import Typography from "@mui/material/Typography";
import {useEffect} from "react";
import {sleep} from "maplibre-gl/src/util/test/util.js";
import app from "../App.jsx";

const ErrorPage = () => {
  const error = useRouteError();
  console.error(error);

    useEffect(() => {
        if (error.status === 401) {
            sleep(1000);
            localStorage.setItem("solAndRJwt", null);
            window.location.href = "/browse";
        }
    }, []);

  return (
    <div id="error-page">
      <h1>Oops!</h1>
        <Typography color="error" align="center" variant="h6" mt={4}>
            {error.message === null && error.statusText === null ? (
             "An unexpected error has occurred."
            ) :(
                error.message || error.statusText
            )
            }

        </Typography>
    </div>
  );
};

export default ErrorPage;
