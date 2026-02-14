import React, { useState, useEffect, useRef } from 'react';
import { Link, NavLink } from 'react-router-dom';
import Stack from "@mui/material/Stack";
import { useLocalStorage } from '../../hooks/state/useLocalStorage';

const NavBar = ({
  textColor
}) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [jwt, setJwt] = useLocalStorage("solAndRJwt", localStorage.getItem('solAndRJwt'));
  const [memberId, setMemberId] = useLocalStorage("memberId", "");
  const [username, setUsername] = useLocalStorage("username", "");
  const [favCity, setFavCity] = useLocalStorage("favCity", "");
  const [curCity, setCurCity] = useLocalStorage("curCity", "");
  const [curDate, setCurDate] = useLocalStorage("curDate", "");
  //const [searchHistory, setSearchHistory] = useLocalStorage["searchHistory"]
  const [location, setLocation] = useState("/");

  const getHoverColor = (baseColor) => {
    if (baseColor.includes('#F') || baseColor.includes('#E') || baseColor.includes('#D')) {
      return `${baseColor}CC`;
    } else {
      return `${baseColor}DD`;
    }
  };
  const hasToken = () => {
    console.log("helohelohelo ---> ", jwt);
      return jwt != null &&
          jwt != undefined &&
          jwt !== "";
  }

  useEffect(() => {
    setIsLoggedIn(hasToken());
  }, []);
  
  useEffect(() => {
    setIsLoggedIn(hasToken());
  }, [location, jwt]);

  const logOut = () => {
    setFavCity("oslo")
    setJwt("")
    setMemberId(null)
    setLocation("/user");
    setUsername("user")
    setIsLoggedIn(hasToken())
  }


  const hoverColor = getHoverColor(textColor);
  const navLinkStyle = {
    color: textColor,
    ':hover': { color: hoverColor }
  };

    function getNavLink(to, label, linkStyle, action= null) {
        return (<NavLink to={to} style={linkStyle}
                        onMouseEnter={(e) => e.target.style.color = hoverColor}
                        onMouseLeave={(e) => e.target.style.color = textColor}
                        className="text-lg font-semibold duration-300"
                        onClick={action ? () => action() : () => {setLocation(to)}}
        >
            <h5>
                {label}
            </h5>
        </NavLink>)
    }

    return (
      <div className="space-x-6">
          {getNavLink("/", "Home", navLinkStyle)}
          {//getNavLink("/about", "About", navLinkStyle)
               }
          {getNavLink(`/browse/city/budapest/date/2020-04-12`, "Browse", navLinkStyle)} 
          {/* /${} */}

          {jwt ? (
            <>
              {getNavLink("/user",
                  "Log out",
                  navLinkStyle,
                logOut)
              }
              {getNavLink("/account", "Account", navLinkStyle)}
              {getNavLink("/me", "MY", navLinkStyle)}
            </>
          ):(
            <>
              {getNavLink("/user", "Log in/Register", navLinkStyle)}
            </>
            )} 
      </div>
  );
}

export default NavBar;