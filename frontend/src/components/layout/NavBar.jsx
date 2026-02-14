import React, { useState, useEffect, useRef } from 'react';
import { Link, NavLink } from 'react-router-dom';
import Stack from "@mui/material/Stack";

const Navbar = ({

  textColor
}) => {
  const getHoverColor = (baseColor) => {
    if (baseColor.includes('#F') || baseColor.includes('#E') || baseColor.includes('#D')) {
      return `${baseColor}CC`;
    } else {
      return `${baseColor}DD`;
    }
  };
    const hasToken = () => {
        return localStorage.getItem("solAndRJwt") != null &&
            localStorage.getItem("solAndRJwt") !== undefined &&
            localStorage.getItem("solAndRJwt") !== "";
    }

  const [isLoggedIn, setIsLoggedIn] = useState(hasToken());

    const [location, setLocation] = useState("/");

    useEffect(() => {
        setIsLoggedIn(hasToken());
  }, [location]);


    const logOut = () => {
        localStorage.removeItem("solAndRJwt");
        localStorage.removeItem("username");
        setLocation("/user");

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
                        className="text-lg font-semibold transition-colors duration-300"
                        onClick={action ? action : () => {setLocation(to)}}
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
          {getNavLink("/browse", "Browse", navLinkStyle)}

          {!hasToken()? (
          getNavLink("/user", "Log in/Register", navLinkStyle
          )) : (<>
          {getNavLink("/user",
              "Log out",
              navLinkStyle,
              () => {
             logOut();
          })}
          {getNavLink("/account", "Account", navLinkStyle)}
          {getNavLink("/me", "MY", navLinkStyle)}
          </>
          )}
      </div>
  );
}

export default Navbar;