import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Login from "../components/auth/Register.jsx";
import Register from "../components/auth/Login.jsx";
import { useAuth } from "../hooks/auth/useAuth";
import "./styles.css";

export default function UserAuthenticationPage() {
  const navigate = useNavigate();
  const { login, register } = useAuth();
  const [type, setType] = useState("signIn");
  const [formData, setFormData] = useState({ username: "", email: "", password: "", favouriteCity: "" });
  const containerClass = `container ${type === "signUp" ? "right-panel-active" : ""}`;

  const onChange = e => { const { id, value } = e.target; setFormData(prev => ({ ...prev, [id]: value })); };
  const handleFormSwitch = nextType => { if (nextType !== type) setType(nextType); };

  const onSubmit = async e => {
    e.preventDefault();
    try {
      if (type === "signUp") {
        await register(formData.username, formData.email, formData.password, formData.favouriteCity);
        await login(formData.username, formData.password);
      } else { 
        await login(formData.username, formData.password); 
      }
      navigate("/account");
    } catch (err) { alert(err.message || "Authentication failed"); }
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
              <p>To visit your places, please login with your personal info</p>
              <button className="ghost" onClick={() => handleFormSwitch("signIn")}>Login</button>
            </div>
            <div className="overlay-panel overlay-right">
              <h5>Don't have an account?</h5>
              <p>Enter your personal details and start your journey with us</p>
              <button className="ghost" onClick={() => handleFormSwitch("signUp")}>Registration</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}