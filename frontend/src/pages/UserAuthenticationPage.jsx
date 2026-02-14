import React, { useState, useEffect } from "react";
import Login from "./Login.jsx";
import Register from "./Register.jsx";
import { useNavigate } from "react-router-dom";
import { fetchData } from "../utils.js";
import "./styles.css";

export default function UserAuthenticationPage() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
  });
  const [type, setType] = useState("signIn");
  const containerClass =
        "container " + (type === "signUp" ? "right-panel-active" : "");
  
  const handleFormSwitch = text => {
        if (text !== type) {
            setType(text);
            return;
        }
  };

  const onChange = (e) => {
    const { id, value } = e.target;

    setFormData((prev) => ({
      ...prev,
      [id]: value,
    }));
  };


const onSubmit = async (e) => {
  e.preventDefault();
  
  try {
    if (type === "signUp") {
      console.log('Starting registration with data:', {
        username: formData.username,
        email: formData.email,
        password: formData.password ? '***' : 'undefined'
      });
      
      const response = await fetchData(
        "members/auth/register",
        "POST",
        {
          username: formData.username,
          email: formData.email,
          password: formData.password
        },
        null,
        true
      );
      
      console.log('Registration response:', response);
      
      if (response) {
        console.log('Registration successful, attempting login...');
        try {
          const loginResponse = await fetchData(
            "members/auth/login",
            "POST",
            {
              username: formData.username,
              password: formData.password
            }
          );
          
          console.log('Login after registration response:', loginResponse);
          
          if (loginResponse) {
              handleLoginSuccess(loginResponse);
          }
        } catch (loginError) {
          console.error('Error during login after registration:', loginError);
          alert('Registration successful but login failed. Please try logging in manually.');
          setType("signIn");
        }
      }
    } else {
      const loginResponse = await fetchData(
        "members/auth/login",
        "POST",
        {
          username: formData.username,
          password: formData.password
        }
      );
      
      if (loginResponse) {
          handleLoginSuccess(loginResponse);
      }
    }
  } catch (error) {
    console.error("Authentication error:", error);
    
    if (error.status === 401) {
      setFormData(prev => ({
        ...prev,
        password: ""
      }));
      alert("Invalid username or password");
    } else if (error.status === 400) {
      alert("Invalid request. Please check your input and try again.");
    } else {
      alert("An error occurred. Please try again later.");
    }
  }
};

const handleLoginSuccess = (response) => {
  if (response.jwt) {
    localStorage.setItem("solAndRJwt", response.jwt);
  }
  if (response.username) {
    localStorage.setItem("username", response.username);
  }
  if (response.memberId) {
    localStorage.setItem("memberId", response.memberId);
  }
  if (response.favCity) {
    localStorage.setItem("favCity", JSON.stringify(response.favCity));
  }
    navigate("/account");
};
    return (
        <div className="userAuthentication">
            <div className={containerClass} id="container">
                <Login handleSubmit={onSubmit} handleChange={onChange} formData={formData}/>
                <Register handleSubmit={onSubmit} handleChange={onChange} formData={formData}/>
                <div className="overlay-container">
                    <div className="overlay">
                        <div className="overlay-panel overlay-left">
                            <h5>Already have an account?</h5>
                            <p>
                                To visit your places, please login with your personal info
                            </p>
                            <button
                                className="ghost"
                                id="signIn"
                                onClick={() => handleFormSwitch("signIn")}
                            >
                                Login
                            </button>
                        </div>
                        <div className="overlay-panel overlay-right">
                            <h5>Don't have an account?</h5>
                            <p>Enter your personal details and start your journey with us</p>
                            <button
                                className="ghost"
                                id="signUp"
                                onClick={() => handleFormSwitch("signUp")}
                            >
                                Registration
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}