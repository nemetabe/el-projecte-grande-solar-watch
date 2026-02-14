import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";


const AccountPage = () => {
  const [username, setUsername] = useState(localStorage.getItem("username"));
  const [isLoggedIn, setIsLoggedIn] = useState(
      localStorage.getItem("solAndRJwt") !== null &&
      (localStorage.getItem("solAndRJwt") !== undefined));
  const navigate = useNavigate();

  return (
    <>
    {!isLoggedIn? (
      <div>
      <h1>Please log in</h1>
      <button onClick={() => {
          localStorage.getItem("favCity") === null && localStorage.setItem("favCity", "Budapest");
          navigate("/user")
          }
      }></button>
        </div>
        ) : (
      <h1>hi {username}!</h1>
    )}
    </>
  )
}

export default AccountPage;