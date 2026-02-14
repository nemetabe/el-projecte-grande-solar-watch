import React from 'react';

function Register({handleSubmit, handleChange, formData}) {


  return (
    <div className="form-container sign-up-container">
      <form onSubmit={handleSubmit}>
        <h5>Create an account</h5>
        <div className="social-container">
          <a href="#" className="social">
            <i className="fab fa-facebook-f" />
          </a>
          <a href="#" className="social">
            <i className="fab fa-google-plus-g" />
          </a>
          <a href="#" className="social">
            <i className="fab fa-linkedin-in" />
          </a>
        </div>
        <span>or fill out this to registrate</span>
        <input
          type="input"
          name="username"
          id="username"
          value={formData.username}
          onChange={handleChange}
          placeholder="Username"
        />
        <input
          type="email"
          id="email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          placeholder="Email"
        />
        <input
          type="password"
          name="password"          
          id="password"
          value={formData.password}
          onChange={handleChange}
          placeholder="Password"
        />
        <button>Sign Up</button>
      </form>
    </div>
  );
}

export default Register;
