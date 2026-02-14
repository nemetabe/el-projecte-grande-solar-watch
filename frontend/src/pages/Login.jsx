import React from "react";


function Login({handleSubmit, handleChange, formData}) {
  //   const [state, setState] = React.useState({
  //   username: formData.username,
  //   password: formData.password
  // });

  return (
      <div className="form-container sign-in-container">
        <form onSubmit={handleSubmit}>
          <h5>Welcome back!</h5>
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
          <span>or use your account</span>
          <input
              type="input"
              placeholder="Username"
              id="username"
              name="username"
              value={formData.username}
              onChange={handleChange}
          />
          <input
              type="password"
              id="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleChange}
          />
          <a href="#">Forgot your password?</a>
          <button>Sign In</button>
        </form>
      </div>
  );
}

export default Login;
